<?php
  require '../../../core/init.php';

  if(!Session::exists(Config::get('session/session_name'))){
  	Redirect::to(URL::clearQuery(login_url()));
  	exit();
  }else{
  	$customer = new Customer(Session::get(Config::get('session/session_name')));
  }
  if (isset($_POST['logout'])) {
    $customer->logout();
    Redirect::to(URL::clearQuery(login_url()));
  }
  if (isset($_POST['savebtn'])) {
      if (Token::check(Input::get('token'))) {
          $purifier = new HTMLPurifier();

          $mobile = $purifier->purify(Input::get('mobile_phone'));
          $passport_number = $purifier->purify(Input::get('passport_number'));
          $issue_authority = $purifier->purify(Input::get('issue_authority'));
          $nationality = $purifier->purify(Input::get('nationality'));
          $place_of_birth = $purifier->purify(Input::get('place_of_birth'));
          $issue_date = $purifier->purify(Input::get('issue_date'));
          $birthday = $purifier->purify(Input::get('birthday'));
          $valid_until = $purifier->purify(Input::get('valid_until'));
          $iban = $purifier->purify(Input::get('iban'));
          $bic_swift = $purifier->purify(Input::get('bic_swift'));
          $account_holder = $purifier->purify(Input::get('account_holder'));
          $bank_name = $purifier->purify(Input::get('bank_name'));
          $bank_address = $purifier->purify(Input::get('bank_address'));
          $bank_city = $purifier->purify(Input::get('bank_city'));
          $identity = Input::get('type');
          $iban_first_chars = substr($iban, 0, 2);
          $bicswift_first_chars = substr($bic_swift, 0, 2);
          $passport_first_chars = substr($passport_number, 0, 2);

          if ($iban_first_chars != 'XX') {
          	$iban_changed = TRUE;
          	$ibanverification = new IBAN($iban);
	        if(!$ibanverification->Verify()) {
	            Session::flash('wrong_iban', 'IBAN has to be in a correct format.');
	            $iban_correct = FALSE;
	        }else{
	        	$iban_correct = TRUE;
	        }
          }else{
          	$iban_changed = FALSE;
          }

          if ($bicswift_first_chars != 'XX') {
          		$swift_changed = TRUE;
          		if (!SwiftBic::validate($bic_swift)) {
          			Session::flash('wrong_swiftbic', 'Swift/BIC has to be valid.');
          			$bic_correct = FALSE;
          		}else{
          			$bic_correct = TRUE;
          		}
          }else{
          		$swift_changed = FALSE;
          }

          if ($passport_first_chars != 'XX') {
          		$passport_number_changed = TRUE;
          }else{
          		$passport_number_changed = FALSE;
          }

          if ($identity === 'Identity Card' || $identity === 'Personalausweis' || $identity === 'Carte d\'identité' || $identity === 'Identiteitskaart') {
          	$identity_type = 'Personalausweis';
          }elseif ($identity === 'Passport' || $identity === 'Reisepass' || $identity === 'Passeport' || $identity === 'Paspoort') {
          	$identity_type = 'Reisepass';
          }

          //if only IBAN is changed
          if($iban_changed && $iban_correct){
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankIBAN' => $iban
	          );
          	$ready_to_submit = TRUE;
          //if only BIC / SWIFT is changed
          }elseif ($swift_changed && $bic_correct) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankBIC' => $bic_swift
	          );
          	$ready_to_submit = TRUE;
          //if only Passport Number is changed
          }elseif ($passport_number_changed) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'PassNummer' => $passport_number
	          );
          	$ready_to_submit = TRUE;
          }
          //If only IBAN and BIC/Swift are changed
          if ($iban_changed && $swift_changed && $iban_correct && $bic_correct) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankIBAN' => $iban,
	          	'BankBIC' => $bic_swift
	          );
          	$ready_to_submit = TRUE;
          //If only IBAN and Passport Number are changed
          }elseif ($iban_changed && $passport_number_changed && $iban_correct) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankIBAN' => $iban,
	          	'PassNummer' => $passport_number
	          );
          	$ready_to_submit = TRUE;
          //If only BIC/Swift and Passport Number are changed
          }elseif ($swift_changed && $passport_number_changed && $bic_correct) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankBIC' => $bic_swift,
	          	'PassNummer' => $passport_number
	          );
          	$ready_to_submit = TRUE;
          }
          //If IBAN and BIC/Swift and Passport Number are changed
          if ($iban_changed && $swift_changed && $passport_number_changed && $bic_correct && $iban_correct) {
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city,
	          	'BankIBAN' => $iban,
	          	'BankBIC' => $bic_swift,
	          	'PassNummer' => $passport_number
	          );
          	$ready_to_submit = TRUE;
          }
          //If nothing changed
          if(!$iban_changed && !$swift_changed && !$passport_number_changed){
          	$data = array(
	          	'MobilNr' => $mobile,
	          	'Ausweistyp' => $identity_type,
	          	'PassAusgestelltVon' => $issue_authority,
	          	'Staatsangehoerigkeit' => $nationality,
	          	'Geburtsort' => $place_of_birth,
	          	'PassAusgestelltAm' => $issue_date,
	          	'Geburtsdatum' => $birthday,
	          	'PassGueltigBis' => $valid_until,
	          	'BankInhaber' => $account_holder,
	          	'BankName' => $bank_name,
	          	'BankStrasse' => $bank_address,
	          	'BankOrt' => $bank_city
	          );
          	$ready_to_submit = TRUE;
          }

          if ($ready_to_submit) {
          	$customer->update($data, $customer->data()->ID);
          	if (isset($_GET['lang'])) {
	           Redirect::to(URL::clearQuery(account_personal_info_url()) . '?lang=' . escape($_GET['lang']));
  	        }else{
  	           Redirect::to(URL::clearQuery(account_personal_info_url()));
  	        }
          }
      }
  }
?>
<!DOCTYPE html>
<html>
<head>
  <?php View::renderHeader('dashboard_header');?>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
<header class="main-header">
    <!-- Logo -->
    <a href="<?php if (isset($_GET['lang'])) {
                    echo URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']);
                  }else{
                    echo URL::clearQuery(dashboard_url());
                  }?>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>B</b>R</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><img src="<?php echo image_path();?>website/small_blumen_flower.png" />&nbsp;<b>Back</b>Office</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only"><?php echo TOGGLE_NAVIGATION; ?></span>
      </a>
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- German Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(account_personal_info_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->
          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(account_personal_info_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->
          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(account_personal_info_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->
          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(account_personal_info_url()) . '?lang=4' ;?>">
              <img src="<?php echo image_path();?>flags/nl.png"/>
            </a>
          </li>
          <!-- End of Dutch Flag -->
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <?php if($customer->data()->Geschlecht == 'm'){?>
                <img src='<?php echo image_path();?>website/male_profile.png' class='user-image' alt='User Image'>
              <?php }elseif($customer->data()->Geschlecht == 'w'){?>
                <img src="<?php echo image_path();?>website/female_profile.png" class="user-image" alt="User Image">
              <?php }elseif($customer->data()->Geschlecht == 'Familie'){?>
                <img src="<?php echo image_path();?>website/family_profile.png" class="user-image" alt="User Image">
              <?php }?>
              <span class="hidden-xs"><?php echo $customer->data()->Vorname; ?> <?php echo $customer->data()->Name; ?></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <?php if($customer->data()->Geschlecht == 'm'){?>
                  <img src='<?php echo image_path();?>website/male_profile.png' class='user-image' alt='User Image'>
                <?php }elseif($customer->data()->Geschlecht == 'w'){?>
                  <img src="<?php echo image_path();?>website/female_profile.png" class="user-image" alt="User Image">
                <?php }elseif($customer->data()->Geschlecht == 'Familie'){?>
                  <img src="<?php echo image_path();?>website/family_profile.png" class="user-image" alt="User Image">
                <?php }?>
                <p>
                  <?php echo $customer->data()->Vorname; ?> <?php echo $customer->data()->Name; ?>
                  <small><?php echo COUNTRY; ?>: <?php echo convertCountryCodeToCountry($customer->data()->Land); ?></small>
                  <small><?php echo ADDRESS; ?>: <?php echo $customer->data()->Strasse . ", " . $customer->data()->PLZ; ?></small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-6 text-center">
                    <a class="fa fa-calendar-check-o" href="<?php
                            if (isset($_GET['lang'])) {
                              echo URL::clearQuery(bookings_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(bookings_url());
                            }?>"> <?php echo BOOKINGS; ?></a>
                  </div>
                  <div class="col-xs-6 text-center">
                    <a class="fa fa-cc-visa" href="<?php
                            if (isset($_GET['lang'])) {
                              echo URL::clearQuery(payments_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(payments_url());
                            }?>"> <?php echo PAYMENTS; ?></a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="<?php
                            if (isset($_GET['lang'])) {
                              echo URL::clearQuery(account_personal_info_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(account_personal_info_url());
                            }?>" class="btn btn-default btn-flat"><?php echo PROFILE; ?></a>
                </div>
                <div class="pull-right">
                  <form method="post">
                    <input type="hidden" name="token" value="<?php echo Token::generate(); ?>">
                    <button id="logout" name="logout" class="btn btn-default btn-flat"><?php echo LOGOUT; ?></button>
                  </form>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </header>
    <?php
    if (isset($_GET['lang'])) {
      $dashboardlink = URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']);
      $personalinfolink = URL::clearQuery(account_personal_info_url()) . '?lang=' . escape($_GET['lang']);
      $fellowtravellerslink = URL::clearQuery(fellow_travelers_url()) . '?lang=' . escape($_GET['lang']);
      $bookingslink = URL::clearQuery(bookings_url()) . '?lang=' . escape($_GET['lang']);
      $paymentslink = URL::clearQuery(payments_url()) . '?lang=' . escape($_GET['lang']);
      $arrivallink = URL::clearQuery(arrival_url()) . '?lang=' . escape($_GET['lang']);
      $bedlinnenlink = URL::clearQuery(orders_bedlinnen_url()) . '?lang=' . escape($_GET['lang']);
      $contactformlink = URL::clearQuery(contact_url()) . '?lang=' . escape($_GET['lang']);
      $contactmailboxlink = URL::clearQuery(mailbox_inbox_url()) . '?lang=' . escape($_GET['lang']);
      $changepasswordlink = URL::clearQuery(account_change_password_url()) . '?lang=' . escape($_GET['lang']);
      $accountdeletelink = URL::clearQuery(account_delete_url()) . '?lang=' . escape($_GET['lang']);
    }else{
      $dashboardlink = URL::clearQuery(dashboard_url());
      $personalinfolink = URL::clearQuery(account_personal_info_url());
      $fellowtravellerslink = URL::clearQuery(fellow_travelers_url());
      $bookingslink = URL::clearQuery(bookings_url());
      $paymentslink = URL::clearQuery(payments_url());
      $arrivallink = URL::clearQuery(arrival_url());
      $bedlinnenlink = URL::clearQuery(orders_bedlinnen_url());
      $contactformlink = URL::clearQuery(contact_url());
      $contactmailboxlink = URL::clearQuery(mailbox_inbox_url());
      $changepasswordlink = URL::clearQuery(account_change_password_url());
      $accountdeletelink = URL::clearQuery(account_delete_url());
    }
    if($customer->data()->Geschlecht == 'm'){
      $imagelink = image_path() . 'website/male_profile.png';
    }elseif($customer->data()->Geschlecht == 'w'){
      $imagelink = image_path() . 'website/female_profile.png';
    }elseif($customer->data()->Geschlecht == 'Familie'){
      $imagelink = image_path() . 'website/family_profile.png';
    }
    $data = array(
          'image' => $imagelink,
          'Name' => $customer->data()->Vorname,
          'Surname' => $customer->data()->Name,
          'Bookings' => BOOKINGS,
          'DashboardLink' => $dashboardlink,
          'PernalInfoLink' => $personalinfolink,
          'FellowTravelersLink' => $fellowtravellerslink,
          'BookingsLink' => $bookingslink,
          'PaymentsLink' => $paymentslink,
          'ArrivalLink' => $arrivallink,
          'BedLinnenLink' => $bedlinnenlink,
          'ContactFormLink' => $contactformlink,
          'ContactMailboxLink' => $contactmailboxlink,
          'ChangePasswordLink' => $changepasswordlink,
          'DeleteAccountLink' => $accountdeletelink,
          'Payments' => PAYMENTS,
          'Profile' => PROFILE,
          'Logout' => LOGOUT,
          'MyInformation' => MY_INFORMATION,
          'Dashboard' => DASHBOARD,
          'PersonalDetails' => PERSONAL_DETAILS,
          'FellowTravelers' => FELLOW_TRAVELERS,
          'MyArrival' => MY_ARRIVAL,
          'ImportantInformation' => IMPORTANT_INFORMATION,
          'BedlinnenExtras' => BEDLINNEN_EXTRAS,
          'Bedlinnen' => BEDLINNEN,
          'Contact' => CONTACT,
          'ContactUs' => CONTACT_US,
          'Mailbox' => MAILBOX,
          'Account' => ACCOUNT,
          'ChangePassword' => CHANGE_PASSWORD,
          'DeleteAccount' => DELETE_ACCOUNT,
          'CustomerArea' => CUSTOMER_AREA,
      );
  $template = Template::loadMenu('dashboard_menu', $data);
  echo $template;
   ?>
    <section class="content-header">
      <h1>
        <?php echo PERSONAL_DETAILS; ?>
        <small><?php echo CUSTOMER_AREA; ?></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="<?php 
                      if (isset($_GET['lang'])) {
                        echo URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(dashboard_url());
                      }?>"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li><a href="<?php
                      if (isset($_GET['lang'])) {
                        echo URL::clearQuery(account_personal_info_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(account_personal_info_url());
                      }?>"><i class="fa fa-inbox"></i> <?php echo PERSONAL_DETAILS; ?></a></li>
        <li class="active">Overview</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <!-- Small boxes (Stat box) -->
      <div class="row">
      </div>
      <!-- /.row -->
      <!-- Main row -->
      <div class="row">
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-12 connectedSortable">
          <?php
              if (Session::exists('wrong_iban')) {

                $country = strtolower($iban_first_chars);
                if (file_exists(load_iban_example($country))) {
                	$examples = nl2br(file_get_contents(load_iban_example($country)));
                	Message::displayAlertDialog(Session::flash('wrong_iban'), 'Error', 'Correctly formated IBAN\'s supported for your country are: <br/>' . $examples);
                }else{
                	Message::displayAlertDialog(Session::flash('wrong_iban'));
                }

                
              }elseif (Session::exists('wrong_swiftbic')) {
              	Message::displayAlertDialog(Session::flash('wrong_swiftbic'));
              }

          ?>
        	<form method="post">
        	<div class="box box-primary">
          <div class="box-header with-border">
            	<h3 class="box-title"><?php echo PERSONAL_DETAILS_TITLE;?></h3><br/>
            	<span><?php echo PERSONAL_DETAILS_MESSAGE;?></span><br/>
            	<div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            	</div>
          </div>
          <div class="box-body">
            <div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo FIRST_NAME; ?></label>
  					    </div>
  					    <div class="colg-md-3"> 
  					    	<input type="text" class="form-control" value="<?=trim($customer->data()->Vorname);?>" readonly/>
  					    </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label><?php echo LAST_NAME; ?></label>
    						</div>
    						<div class="colg-md-3">
    						    <input type="text" class="form-control" value="<?=trim($customer->data()->Name);?>" readonly/>
    						</div>
  					</div>
  					<div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo ADDRESS; ?></label>
  					    </div>
  						  <div class="colg-md-3"> 
  					    	<input type="text" class="form-control" value="<?=trim($customer->data()->Strasse);?>" readonly/>
  					    </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label><?php echo POSTAL_CODE; ?></label>
    						</div>
    						<div class="colg-md-3">
    						    <input type="text" class="form-control" value="<?=trim($customer->data()->PLZ);?>" readonly/>
    						</div>
  					</div>
  					<div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo PHONE_NUMBER; ?></label>
  					    </div>
  					    <div class="colg-md-3"> 
  					    	<input type="text" class="form-control" value="<?=trim($customer->data()->Telefon1);?>" readonly/>
  					    </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label><?php echo FAX_NUMBER; ?></label>
    						</div>
    						<div class="colg-md-3">
    						    <input type="text" class="form-control" value="<?=trim($customer->data()->Fax1);?>" readonly/>
    						</div>
  					</div>
  					<div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo MOBILE_NUMBER; ?></label>
  					    </div>
  					    <div class="colg-md-3"> 
  					    	<input id="editable_field" name="mobile_phone" type="text" class="form-control" value="<?=trim($customer->data()->MobilNr);?>"/>
  					    </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label>Email</label>
    						</div>
    						<div class="colg-md-3">
    						    <input type="text" class="form-control" value="<?=trim($customer->data()->EMail);?>" readonly/>
    						</div>
  					</div>
          </div>
			  </div>

			<div class="box box-primary">
	            <div class="box-header with-border">
	              	<h3 class="box-title"><?php echo IDENTITY_PASSPORT;?></h3><br/>
	              	<span><?php echo PASSPORT_INFO_MESSAGE;?></span><br/>
	            	  <div class="box-tools pull-right">
		                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	              	</div>
	            </div>
	            <div class="box-body">
  	            <div class="form-group col-lg-6" >
      					  	<div class="colg-md-6" > 
        					    	<label style="padding: 10px;"><input type="radio" name="type" value="<?php echo IDENTITY_CARD;?>" <?php if($customer->data()->Ausweistyp == 'Personalausweis'){ echo "checked";} ?> /> <?php echo IDENTITY_CARD;?></label>
        					    	<label style="padding: 10px;"><input type="radio" name="type" value="<?php echo PASSPORT;?>" <?php if($customer->data()->Ausweistyp == 'Reisepass'){ echo "checked";}?> /> <?php echo PASSPORT;?></label>
      					    </div>
  					    </div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      							<label class="control-label" for="inputWarning"><?php echo NUMBER; ?></label> <font color="blue" class="control-label pull-right" ><i class="fa fa-bell-o"></i><?php echo HELP_PASSPORT_NUMBER_MESSAGE; ?></font>
      						    <input type="text" name="passport_number" class="form-control" value="<?=trim(Input::mask($customer->data()->PassNummer));?>" id="editable_field"/>
      						</div>
      					</div>
      					<div class="form-group col-lg-6" >
      					  	<div class="colg-md-3"> 
      					    	<label><?php echo ISSUE_AUTHORITY; ?></label>
      					    </div>
      						  <div class="colg-md-3"> 
      					    	<input type="text" name="issue_authority" class="form-control" value="<?=trim($customer->data()->PassAusgestelltVon);?>" id="editable_field"/>
      					    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      						    <label><?php echo NATIONALITY; ?></label>
      						</div>
      						<div class="colg-md-3">
      						    <input type="text" name="nationality" class="form-control" value="<?=trim($customer->data()->Staatsangehoerigkeit);?>" id="editable_field"/>
      						</div>
      					</div>
      					<div class="form-group col-lg-6" >
      					  	<div class="colg-md-3"> 
      					    	<label><?php echo PLACE_OF_BIRTH; ?></label>
      					    </div>
      					    <div class="colg-md-3"> 
      					    	<input type="text" name="place_of_birth" class="form-control" value="<?=trim($customer->data()->Geburtsort);?>" id="editable_field"/>
      					    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      						    <label><?php echo ISSUE_DATE; ?></label>
      						</div>
      						<div class="input-group date" id="editable_field">
                    <div id="editable_field" class="input-group-addon">
                      <i class="fa fa-calendar"></i>
                    </div>
                    <input type="text" name="issue_date" class="form-control pull-right" value="<?=trim($customer->data()->PassAusgestelltAm);?>"  id="reservation1">
      		        </div>
      					</div>
      					<div class="form-group col-lg-6" >
      					  	<div class="colg-md-3"> 
      					    	<label><?php echo BIRTHDAY; ?></label>
      					    </div>
      					    <div class="input-group date" >
                        <div id="editable_field" class="input-group-addon">
                          <i class="fa fa-calendar"></i>
                        </div>
                        <input  type="text" name="birthday" class="form-control pull-right" value="<?=trim($customer->data()->Geburtsdatum);?>"  id="reservation2">
                    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      						    <label><?php echo VALID_UNTIL; ?></label>
      						</div>
      						<div class="input-group date" >
                      <div id="editable_field" class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                      </div>
                      <input  type="text" name="valid_until" class="form-control pull-right" value="<?=trim($customer->data()->PassGueltigBis);?>"  id="reservation3">
                  </div>
      					</div>
	            </div>
	        </div>

	        <div class="box box-primary">
	            <div class="box-header with-border">
	              	<h3 class="box-title"><?php echo BANK_INFO_TITLE;?></h3><br/>
	              	<span><?php echo BANK_INFO_MESSAGE;?></span><br/>
	              	<div class="box-tools pull-right">
		                <button type="button"  class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	              	</div>
	            </div>
	            <div class="box-body">
              	<div class="form-group col-lg-6" >
      					    <div class="colg-md-3"> 
      					    	<label class="control-label" for="inputWarning"><?php echo IBAN; ?></label> <font color="blue" class="control-label pull-right" ><i class="fa fa-bell-o"></i><?php echo HELP_PASSPORT_NUMBER_MESSAGE; ?></font>
                      <?php 
                        $ibancountry = substr($customer->data()->BankIBAN, 0, 2);
                        $countrycodelenght = new IBANCountry($ibancountry);

                        $maskcharacters = $countrycodelenght->IBANLength() - 4;

                      ?>
      					    	<input type="text" name="iban" class="form-control" value="<?=trim(Input::mask($customer->data()->BankIBAN, $maskcharacters));?>" id="editable_field"/>
      					    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
                    <label class="control-label" for="inputWarning"><?php echo BIC_SWIFT; ?></label> <font color="blue" class="control-label pull-right" ><i class="fa fa-bell-o"></i><?php echo HELP_PASSPORT_NUMBER_MESSAGE; ?></font>
      						  <input type="text" name="bic_swift" class="form-control" value="<?=trim(Input::mask($customer->data()->BankBIC, 6));?>" id="editable_field"/>
      						</div>
      					</div>
      					<div class="form-group col-lg-6" >
      					  	<div class="colg-md-3"> 
      					    	<label><?php echo ACCOUNT_HOLDER; ?></label>
      					    </div>
      						<div class="colg-md-3"> 
      					    	<input type="text" name="account_holder" class="form-control" value="<?=trim($customer->data()->BankInhaber);?>" id="editable_field"/>
      					    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      						    <label><?php echo BANK_NAME; ?></label>
      						</div>
      						<div class="colg-md-3">
      						    <input type="text" name="bank_name" class="form-control" value="<?=trim($customer->data()->BankName);?>" id="editable_field"/>
      						</div>
      					</div>
      					<div class="form-group col-lg-6" >
      					  	<div class="colg-md-3"> 
      					    	<label><?php echo BANK_ADDRESS; ?></label>
      					    </div>
      					    <div class="colg-md-3"> 
      					    	<input type="text" name="bank_address" class="form-control" value="<?=trim($customer->data()->BankStrasse);?>" id="editable_field"/>
      					    </div>
      					</div>
      					<div class="form-group col-lg-6">
      						<div class="colg-md-3">
      						    <label><?php echo BANK_CITY; ?></label>
      						</div>
      						<div class="colg-md-3">
      						    <input type="text" name="bank_city" class="form-control" value="<?=trim($customer->data()->BankOrt);?>" id="editable_field"/>
      						</div>
      					</div>
	            </div>
	        </div>
			
          <input type="hidden" name="token" value="<?php echo Token::generate(); ?>">
			    <button type="submit" class="btn btn-primary btn-lg pull-right" name="savebtn"><?php echo SAVE; ?></button>
			</form>
    </section>
  </div>
  </section>
  </div>
  <?php View::renderFooter('dashboard_footer'); ?>
</div>
<?php View::renderScripts('dashboard_personal_details_script'); ?>
</body>
</html>


