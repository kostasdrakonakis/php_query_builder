<?php 
	require '../../core/init.php';

if(!Session::exists(Config::get('session/session_name'))){
	Redirect::to(URL::clearQuery(login_url()));
	exit();
}else{
	$customer = new Customer(Session::get(Config::get('session/session_name')));
	$booking = new Booking(Session::get(Config::get('session/session_name')));
  	$lastbooking = $booking->lastBookingData();
	$fellowtraveller = new FellowTraveller($lastbooking->ID);
	//print_pre($fellowtraveller->data());
}

if (isset($_POST['logout'])) {
	$customer->logout();
	Redirect::to(URL::clearQuery(login_url()));
}

if (isset($_POST['savebtn'])) {
  if (Token::check(Input::get('token'))) {
	foreach ($fellowtraveller->data() as $key => $value) {
		$travellers_submitted_data[] = array(
			'ID' => $value->ID,
			'name' => Input::get('traveller_'.$value->ID.'_name'),
			'surname' => Input::get('traveller_'.$value->ID.'_surname'),
			'birthday' => Input::get('fellow_traveller_'.$value->ID.'_birthdate'),
			'country' => Input::get('country_'.$value->ID.'_selection')
		);
	}

	foreach ($travellers_submitted_data as $key => $value) {

		$data = array(
			'Vorname' => $value['name'],
			'Name' => $value['surname'],
			'Geburtstag' => $value['birthday'],
			'Geburtsland' => $value['country']
		);

		$fellowtraveller->update($data, $value['ID']);

	}

	if (isset($_GET['lang'])) {
   	Redirect::to(URL::clearQuery(fellow_travelers_url()) . '?lang=' . escape($_GET['lang']));
  }else{
    Redirect::to(URL::clearQuery(fellow_travelers_url()));
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
            <a href="<?php echo URL::clearQuery(fellow_travelers_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->
          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(fellow_travelers_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->
          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(fellow_travelers_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->
          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(fellow_travelers_url()) . '?lang=4' ;?>">
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
        <?php echo FELLOW_TRAVELERS; ?>
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
                        echo URL::clearQuery(fellow_travelers_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(fellow_travelers_url());
                      }?>"><i class="fa fa-users"></i> <?php echo FELLOW_TRAVELERS; ?></a></li>
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
        	<div class="box-header">
	       	<span><?php echo PERSONAL_DETAILS_MESSAGE;?></span><br/>
	        <span><?php echo PERSONAL_DETAILS_SUBMESSAGE;?></span><br/>
	        </div>
        	<form method="post" >
        	<?php 
        		$count = 1;
        		foreach ($fellowtraveller->data() as $key => $value) {

        	?>
        	<div class="box box-primary">
	          	<div class="box-header with-border">
	            	<span>Fellow Traveller: <?=$count?></span>
	            	<div class="box-tools pull-right">
	                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	            	</div>
	          	</div>
          		<div class="box-body" >
           			<div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo FIRST_NAME; ?></label>
  					    </div>
  					    <div class="colg-md-3"> 
  					    	<input type="text" name="traveller_<?=$value->ID;?>_name" class="form-control" value="<?=$value->Vorname;?>" id="editable_field"/>
  					    </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label><?php echo LAST_NAME; ?></label>
    						</div>
    						<div class="colg-md-3">
    						    <input type="text" name="traveller_<?=$value->ID;?>_surname" class="form-control" value="<?=$value->Name;?>" id="editable_field"/>
    						</div>
  					</div>
  					<div class="form-group col-lg-6" >
  					  	<div class="colg-md-3"> 
  					    	<label><?php echo BIRTHDAY; ?></label>
  					    </div>
  						<div class="input-group date" id="editable_field">
		                    <div id="editable_field" class="input-group-addon">
		                      <i class="fa fa-calendar"></i>
		                    </div>
		                    <input type="text" name="fellow_traveller_<?=$value->ID;?>_birthdate" class="form-control pull-right" value="<?php if($value->Geburtstag != '0000-00-00'){echo $value->Geburtstag;} ?>" id="reservation<?=$count?>">
	      		        </div>
  					</div>
  					<div class="form-group col-lg-6">
    						<div class="colg-md-3">
    						    <label><?php echo COUNTRY; ?></label>
    						</div>
    						<div class="colg-md-3">
    						    <select name="country_<?=$value->ID;?>_selection" class="form-control" id="editable_field" >
									<option value="AF" <?php if($value->Geburtsland == 'AF'){echo "selected";} ?>>Afghanistan</option>
									<option value="AX" <?php if($value->Geburtsland == 'AX'){echo "selected";} ?>>Åland Islands</option>
									<option value="AL" <?php if($value->Geburtsland == 'AL'){echo "selected";} ?>>Albania</option>
									<option value="DZ" <?php if($value->Geburtsland == 'DZ'){echo "selected";} ?>>Algeria</option>
									<option value="AS" <?php if($value->Geburtsland == 'AS'){echo "selected";} ?>>American Samoa</option>
									<option value="AD" <?php if($value->Geburtsland == 'AD'){echo "selected";} ?>>Andorra</option>
									<option value="AO" <?php if($value->Geburtsland == 'AO'){echo "selected";} ?>>Angola</option>
									<option value="AI" <?php if($value->Geburtsland == 'AI'){echo "selected";} ?>>Anguilla</option>
									<option value="AQ" <?php if($value->Geburtsland == 'AQ'){echo "selected";} ?>>Antarctica</option>
									<option value="AG" <?php if($value->Geburtsland == 'AG'){echo "selected";} ?>>Antigua and Barbuda</option>
									<option value="AR" <?php if($value->Geburtsland == 'AR'){echo "selected";} ?>>Argentina</option>
									<option value="AM" <?php if($value->Geburtsland == 'AM'){echo "selected";} ?>>Armenia</option>
									<option value="AW" <?php if($value->Geburtsland == 'AW'){echo "selected";} ?>>Aruba</option>
									<option value="AU" <?php if($value->Geburtsland == 'AU'){echo "selected";} ?>>Australia</option>
									<option value="AT" <?php if($value->Geburtsland == 'AT'){echo "selected";} ?>>Austria</option>
									<option value="AZ" <?php if($value->Geburtsland == 'AZ'){echo "selected";} ?>>Azerbaijan</option>
									<option value="BS" <?php if($value->Geburtsland == 'BS'){echo "selected";} ?>>Bahamas</option>
									<option value="BH" <?php if($value->Geburtsland == 'BH'){echo "selected";} ?>>Bahrain</option>
									<option value="BD" <?php if($value->Geburtsland == 'BD'){echo "selected";} ?>>Bangladesh</option>
									<option value="BB" <?php if($value->Geburtsland == 'BB'){echo "selected";} ?>>Barbados</option>
									<option value="BY" <?php if($value->Geburtsland == 'BY'){echo "selected";} ?>>Belarus</option>
									<option value="BE" <?php if($value->Geburtsland == 'BE'){echo "selected";} ?>>Belgium</option>
									<option value="BZ" <?php if($value->Geburtsland == 'BZ'){echo "selected";} ?>>Belize</option>
									<option value="BJ" <?php if($value->Geburtsland == 'BJ'){echo "selected";} ?>>Benin</option>
									<option value="BM" <?php if($value->Geburtsland == 'BM'){echo "selected";} ?>>Bermuda</option>
									<option value="BT" <?php if($value->Geburtsland == 'BT'){echo "selected";} ?>>Bhutan</option>
									<option value="BO" <?php if($value->Geburtsland == 'BO'){echo "selected";} ?>>Bolivia, Plurinational State of</option>
									<option value="BQ" <?php if($value->Geburtsland == 'BQ'){echo "selected";} ?>>Bonaire, Sint Eustatius and Saba</option>
									<option value="BA" <?php if($value->Geburtsland == 'BA'){echo "selected";} ?>>Bosnia and Herzegovina</option>
									<option value="BW" <?php if($value->Geburtsland == 'BW'){echo "selected";} ?>>Botswana</option>
									<option value="BV" <?php if($value->Geburtsland == 'BV'){echo "selected";} ?>>Bouvet Island</option>
									<option value="BR" <?php if($value->Geburtsland == 'BR'){echo "selected";} ?>>Brazil</option>
									<option value="IO" <?php if($value->Geburtsland == 'IO'){echo "selected";} ?>>British Indian Ocean Territory</option>
									<option value="BN" <?php if($value->Geburtsland == 'BN'){echo "selected";} ?>>Brunei Darussalam</option>
									<option value="BG" <?php if($value->Geburtsland == 'BG'){echo "selected";} ?>>Bulgaria</option>
									<option value="BF" <?php if($value->Geburtsland == 'BF'){echo "selected";} ?>>Burkina Faso</option>
									<option value="BI" <?php if($value->Geburtsland == 'BI'){echo "selected";} ?>>Burundi</option>
									<option value="KH" <?php if($value->Geburtsland == 'KH'){echo "selected";} ?>>Cambodia</option>
									<option value="CM" <?php if($value->Geburtsland == 'CM'){echo "selected";} ?>>Cameroon</option>
									<option value="CA" <?php if($value->Geburtsland == 'CA'){echo "selected";} ?>>Canada</option>
									<option value="CV" <?php if($value->Geburtsland == 'CV'){echo "selected";} ?>>Cape Verde</option>
									<option value="KY" <?php if($value->Geburtsland == 'KY'){echo "selected";} ?>>Cayman Islands</option>
									<option value="CF" <?php if($value->Geburtsland == 'CF'){echo "selected";} ?>>Central African Republic</option>
									<option value="TD" <?php if($value->Geburtsland == 'TD'){echo "selected";} ?>>Chad</option>
									<option value="CL" <?php if($value->Geburtsland == 'CL'){echo "selected";} ?>>Chile</option>
									<option value="CN" <?php if($value->Geburtsland == 'CN'){echo "selected";} ?>>China</option>
									<option value="CX" <?php if($value->Geburtsland == 'CX'){echo "selected";} ?>>Christmas Island</option>
									<option value="CC" <?php if($value->Geburtsland == 'CC'){echo "selected";} ?>>Cocos (Keeling) Islands</option>
									<option value="CO" <?php if($value->Geburtsland == 'CO'){echo "selected";} ?>>Colombia</option>
									<option value="KM" <?php if($value->Geburtsland == 'KM'){echo "selected";} ?>>Comoros</option>
									<option value="CG" <?php if($value->Geburtsland == 'CG'){echo "selected";} ?>>Congo</option>
									<option value="CD" <?php if($value->Geburtsland == 'CD'){echo "selected";} ?>>Congo, the Democratic Republic of the</option>
									<option value="CK" <?php if($value->Geburtsland == 'CK'){echo "selected";} ?>>Cook Islands</option>
									<option value="CR" <?php if($value->Geburtsland == 'CR'){echo "selected";} ?>>Costa Rica</option>
									<option value="CI" <?php if($value->Geburtsland == 'CI'){echo "selected";} ?>>Côte d'Ivoire</option>
									<option value="HR" <?php if($value->Geburtsland == 'HR'){echo "selected";} ?>>Croatia</option>
									<option value="CU" <?php if($value->Geburtsland == 'CU'){echo "selected";} ?>>Cuba</option>
									<option value="CW" <?php if($value->Geburtsland == 'CW'){echo "selected";} ?>>Curaçao</option>
									<option value="CY" <?php if($value->Geburtsland == 'CY'){echo "selected";} ?>>Cyprus</option>
									<option value="CZ" <?php if($value->Geburtsland == 'CZ'){echo "selected";} ?>>Czech Republic</option>
									<option value="DK" <?php if($value->Geburtsland == 'DK'){echo "selected";} ?>>Denmark</option>
									<option value="DJ" <?php if($value->Geburtsland == 'DJ'){echo "selected";} ?>>Djibouti</option>
									<option value="DM" <?php if($value->Geburtsland == 'DM'){echo "selected";} ?>>Dominica</option>
									<option value="DO" <?php if($value->Geburtsland == 'DO'){echo "selected";} ?>>Dominican Republic</option>
									<option value="EC" <?php if($value->Geburtsland == 'EC'){echo "selected";} ?>>Ecuador</option>
									<option value="EG" <?php if($value->Geburtsland == 'EG'){echo "selected";} ?>>Egypt</option>
									<option value="SV" <?php if($value->Geburtsland == 'SV'){echo "selected";} ?>>El Salvador</option>
									<option value="GQ" <?php if($value->Geburtsland == 'GQ'){echo "selected";} ?>>Equatorial Guinea</option>
									<option value="ER" <?php if($value->Geburtsland == 'ER'){echo "selected";} ?>>Eritrea</option>
									<option value="EE" <?php if($value->Geburtsland == 'EE'){echo "selected";} ?>>Estonia</option>
									<option value="ET" <?php if($value->Geburtsland == 'ET'){echo "selected";} ?>>Ethiopia</option>
									<option value="FK" <?php if($value->Geburtsland == 'FK'){echo "selected";} ?>>Falkland Islands (Malvinas)</option>
									<option value="FO" <?php if($value->Geburtsland == 'FO'){echo "selected";} ?>>Faroe Islands</option>
									<option value="FJ" <?php if($value->Geburtsland == 'FJ'){echo "selected";} ?>>Fiji</option>
									<option value="FI" <?php if($value->Geburtsland == 'FI'){echo "selected";} ?>>Finland</option>
									<option value="FR" <?php if($value->Geburtsland == 'FR'){echo "selected";} ?>>France</option>
									<option value="GF" <?php if($value->Geburtsland == 'GF'){echo "selected";} ?>>French Guiana</option>
									<option value="PF" <?php if($value->Geburtsland == 'PF'){echo "selected";} ?>>French Polynesia</option>
									<option value="TF" <?php if($value->Geburtsland == 'TF'){echo "selected";} ?>>French Southern Territories</option>
									<option value="GA" <?php if($value->Geburtsland == 'GA'){echo "selected";} ?>>Gabon</option>
									<option value="GM" <?php if($value->Geburtsland == 'GM'){echo "selected";} ?>>Gambia</option>
									<option value="GE" <?php if($value->Geburtsland == 'GE'){echo "selected";} ?>>Georgia</option>
									<option value="DE" <?php if($value->Geburtsland == 'DE'){echo "selected";} ?>>Germany</option>
									<option value="GH" <?php if($value->Geburtsland == 'GH'){echo "selected";} ?>>Ghana</option>
									<option value="GI" <?php if($value->Geburtsland == 'GI'){echo "selected";} ?>>Gibraltar</option>
									<option value="GR" <?php if($value->Geburtsland == 'GR'){echo "selected";} ?>>Greece</option>
									<option value="GL" <?php if($value->Geburtsland == 'GL'){echo "selected";} ?>>Greenland</option>
									<option value="GD" <?php if($value->Geburtsland == 'GD'){echo "selected";} ?>>Grenada</option>
									<option value="GP" <?php if($value->Geburtsland == 'GP'){echo "selected";} ?>>Guadeloupe</option>
									<option value="GU" <?php if($value->Geburtsland == 'GU'){echo "selected";} ?>>Guam</option>
									<option value="GT" <?php if($value->Geburtsland == 'GT'){echo "selected";} ?>>Guatemala</option>
									<option value="GG" <?php if($value->Geburtsland == 'GG'){echo "selected";} ?>>Guernsey</option>
									<option value="GN" <?php if($value->Geburtsland == 'GN'){echo "selected";} ?>>Guinea</option>
									<option value="GW" <?php if($value->Geburtsland == 'GW'){echo "selected";} ?>>Guinea-Bissau</option>
									<option value="GY" <?php if($value->Geburtsland == 'GY'){echo "selected";} ?>>Guyana</option>
									<option value="HT" <?php if($value->Geburtsland == 'HT'){echo "selected";} ?>>Haiti</option>
									<option value="HM" <?php if($value->Geburtsland == 'HM'){echo "selected";} ?>>Heard Island and McDonald Islands</option>
									<option value="VA" <?php if($value->Geburtsland == 'VA'){echo "selected";} ?>>Holy See (Vatican City State)</option>
									<option value="HN" <?php if($value->Geburtsland == 'HN'){echo "selected";} ?>>Honduras</option>
									<option value="HK" <?php if($value->Geburtsland == 'HK'){echo "selected";} ?>>Hong Kong</option>
									<option value="HU" <?php if($value->Geburtsland == 'HU'){echo "selected";} ?>>Hungary</option>
									<option value="IS" <?php if($value->Geburtsland == 'IS'){echo "selected";} ?>>Iceland</option>
									<option value="IN" <?php if($value->Geburtsland == 'IN'){echo "selected";} ?>>India</option>
									<option value="ID" <?php if($value->Geburtsland == 'ID'){echo "selected";} ?>>Indonesia</option>
									<option value="IR" <?php if($value->Geburtsland == 'IR'){echo "selected";} ?>>Iran, Islamic Republic of</option>
									<option value="IQ" <?php if($value->Geburtsland == 'IQ'){echo "selected";} ?>>Iraq</option>
									<option value="IE" <?php if($value->Geburtsland == 'IE'){echo "selected";} ?>>Ireland</option>
									<option value="IM" <?php if($value->Geburtsland == 'IM'){echo "selected";} ?>>Isle of Man</option>
									<option value="IL" <?php if($value->Geburtsland == 'IL'){echo "selected";} ?>>Israel</option>
									<option value="IT" <?php if($value->Geburtsland == 'IT'){echo "selected";} ?>>Italy</option>
									<option value="JM" <?php if($value->Geburtsland == 'JM'){echo "selected";} ?>>Jamaica</option>
									<option value="JP" <?php if($value->Geburtsland == 'JP'){echo "selected";} ?>>Japan</option>
									<option value="JE" <?php if($value->Geburtsland == 'JE'){echo "selected";} ?>>Jersey</option>
									<option value="JO" <?php if($value->Geburtsland == 'JO'){echo "selected";} ?>>Jordan</option>
									<option value="KZ" <?php if($value->Geburtsland == 'KZ'){echo "selected";} ?>>Kazakhstan</option>
									<option value="KE" <?php if($value->Geburtsland == 'KE'){echo "selected";} ?>>Kenya</option>
									<option value="KI" <?php if($value->Geburtsland == 'KI'){echo "selected";} ?>>Kiribati</option>
									<option value="KP" <?php if($value->Geburtsland == 'KP'){echo "selected";} ?>>Korea, Democratic People's Republic of</option>
									<option value="KR" <?php if($value->Geburtsland == 'KR'){echo "selected";} ?>>Korea, Republic of</option>
									<option value="KW" <?php if($value->Geburtsland == 'KW'){echo "selected";} ?>>Kuwait</option>
									<option value="KG" <?php if($value->Geburtsland == 'KG'){echo "selected";} ?>>Kyrgyzstan</option>
									<option value="LA" <?php if($value->Geburtsland == 'LA'){echo "selected";} ?>>Lao People's Democratic Republic</option>
									<option value="LV" <?php if($value->Geburtsland == 'LV'){echo "selected";} ?>>Latvia</option>
									<option value="LB" <?php if($value->Geburtsland == 'LB'){echo "selected";} ?>>Lebanon</option>
									<option value="LS" <?php if($value->Geburtsland == 'LS'){echo "selected";} ?>>Lesotho</option>
									<option value="LR" <?php if($value->Geburtsland == 'LR'){echo "selected";} ?>>Liberia</option>
									<option value="LY" <?php if($value->Geburtsland == 'LY'){echo "selected";} ?>>Libya</option>
									<option value="LI" <?php if($value->Geburtsland == 'LI'){echo "selected";} ?>>Liechtenstein</option>
									<option value="LT" <?php if($value->Geburtsland == 'LT'){echo "selected";} ?>>Lithuania</option>
									<option value="LU" <?php if($value->Geburtsland == 'LU'){echo "selected";} ?>>Luxembourg</option>
									<option value="MO" <?php if($value->Geburtsland == 'MO'){echo "selected";} ?>>Macao</option>
									<option value="MK" <?php if($value->Geburtsland == 'MK'){echo "selected";} ?>>Macedonia, the former Yugoslav Republic of</option>
									<option value="MG" <?php if($value->Geburtsland == 'MG'){echo "selected";} ?>>Madagascar</option>
									<option value="MW" <?php if($value->Geburtsland == 'MW'){echo "selected";} ?>>Malawi</option>
									<option value="MY" <?php if($value->Geburtsland == 'MY'){echo "selected";} ?>>Malaysia</option>
									<option value="MV" <?php if($value->Geburtsland == 'MV'){echo "selected";} ?>>Maldives</option>
									<option value="ML" <?php if($value->Geburtsland == 'ML'){echo "selected";} ?>>Mali</option>
									<option value="MT" <?php if($value->Geburtsland == 'MT'){echo "selected";} ?>>Malta</option>
									<option value="MH" <?php if($value->Geburtsland == 'MH'){echo "selected";} ?>>Marshall Islands</option>
									<option value="MQ" <?php if($value->Geburtsland == 'MQ'){echo "selected";} ?>>Martinique</option>
									<option value="MR" <?php if($value->Geburtsland == 'MR'){echo "selected";} ?>>Mauritania</option>
									<option value="MU" <?php if($value->Geburtsland == 'MU'){echo "selected";} ?>>Mauritius</option>
									<option value="YT" <?php if($value->Geburtsland == 'YT'){echo "selected";} ?>>Mayotte</option>
									<option value="MX" <?php if($value->Geburtsland == 'MX'){echo "selected";} ?>>Mexico</option>
									<option value="FM" <?php if($value->Geburtsland == 'FM'){echo "selected";} ?>>Micronesia, Federated States of</option>
									<option value="MD" <?php if($value->Geburtsland == 'MD'){echo "selected";} ?>>Moldova, Republic of</option>
									<option value="MC" <?php if($value->Geburtsland == 'MC'){echo "selected";} ?>>Monaco</option>
									<option value="MN" <?php if($value->Geburtsland == 'MN'){echo "selected";} ?>>Mongolia</option>
									<option value="ME" <?php if($value->Geburtsland == 'ME'){echo "selected";} ?>>Montenegro</option>
									<option value="MS" <?php if($value->Geburtsland == 'MS'){echo "selected";} ?>>Montserrat</option>
									<option value="MA" <?php if($value->Geburtsland == 'MA'){echo "selected";} ?>>Morocco</option>
									<option value="MZ" <?php if($value->Geburtsland == 'MZ'){echo "selected";} ?>>Mozambique</option>
									<option value="MM" <?php if($value->Geburtsland == 'MM'){echo "selected";} ?>>Myanmar</option>
									<option value="NA" <?php if($value->Geburtsland == 'NA'){echo "selected";} ?>>Namibia</option>
									<option value="NR" <?php if($value->Geburtsland == 'NR'){echo "selected";} ?>>Nauru</option>
									<option value="NP" <?php if($value->Geburtsland == 'NP'){echo "selected";} ?>>Nepal</option>
									<option value="NL" <?php if($value->Geburtsland == 'NL'){echo "selected";} ?>>Netherlands</option>
									<option value="NC" <?php if($value->Geburtsland == 'NC'){echo "selected";} ?>>New Caledonia</option>
									<option value="NZ" <?php if($value->Geburtsland == 'NZ'){echo "selected";} ?>>New Zealand</option>
									<option value="NI" <?php if($value->Geburtsland == 'NI'){echo "selected";} ?>>Nicaragua</option>
									<option value="NE" <?php if($value->Geburtsland == 'NE'){echo "selected";} ?>>Niger</option>
									<option value="NG" <?php if($value->Geburtsland == 'NG'){echo "selected";} ?>>Nigeria</option>
									<option value="NU" <?php if($value->Geburtsland == 'NU'){echo "selected";} ?>>Niue</option>
									<option value="NF" <?php if($value->Geburtsland == 'NF'){echo "selected";} ?>>Norfolk Island</option>
									<option value="MP" <?php if($value->Geburtsland == 'MP'){echo "selected";} ?>>Northern Mariana Islands</option>
									<option value="NO" <?php if($value->Geburtsland == 'NO'){echo "selected";} ?>>Norway</option>
									<option value="OM" <?php if($value->Geburtsland == 'OM'){echo "selected";} ?>>Oman</option>
									<option value="PK" <?php if($value->Geburtsland == 'PK'){echo "selected";} ?>>Pakistan</option>
									<option value="PW" <?php if($value->Geburtsland == 'PW'){echo "selected";} ?>>Palau</option>
									<option value="PS" <?php if($value->Geburtsland == 'PS'){echo "selected";} ?>>Palestinian Territory, Occupied</option>
									<option value="PA" <?php if($value->Geburtsland == 'PA'){echo "selected";} ?>>Panama</option>
									<option value="PG" <?php if($value->Geburtsland == 'PG'){echo "selected";} ?>>Papua New Guinea</option>
									<option value="PY" <?php if($value->Geburtsland == 'PY'){echo "selected";} ?>>Paraguay</option>
									<option value="PE" <?php if($value->Geburtsland == 'PE'){echo "selected";} ?>>Peru</option>
									<option value="PH" <?php if($value->Geburtsland == 'PH'){echo "selected";} ?>>Philippines</option>
									<option value="PN" <?php if($value->Geburtsland == 'PN'){echo "selected";} ?>>Pitcairn</option>
									<option value="PL" <?php if($value->Geburtsland == 'PL'){echo "selected";} ?>>Poland</option>
									<option value="PT" <?php if($value->Geburtsland == 'PT'){echo "selected";} ?>>Portugal</option>
									<option value="PR" <?php if($value->Geburtsland == 'PR'){echo "selected";} ?>>Puerto Rico</option>
									<option value="QA" <?php if($value->Geburtsland == 'QA'){echo "selected";} ?>>Qatar</option>
									<option value="RE" <?php if($value->Geburtsland == 'RE'){echo "selected";} ?>>Réunion</option>
									<option value="RO" <?php if($value->Geburtsland == 'RO'){echo "selected";} ?>>Romania</option>
									<option value="RU" <?php if($value->Geburtsland == 'RU'){echo "selected";} ?>>Russian Federation</option>
									<option value="RW" <?php if($value->Geburtsland == 'RW'){echo "selected";} ?>>Rwanda</option>
									<option value="BL" <?php if($value->Geburtsland == 'BL'){echo "selected";} ?>>Saint Barthélemy</option>
									<option value="SH" <?php if($value->Geburtsland == 'SH'){echo "selected";} ?>>Saint Helena, Ascension and Tristan da Cunha</option>
									<option value="KN" <?php if($value->Geburtsland == 'KN'){echo "selected";} ?>>Saint Kitts and Nevis</option>
									<option value="LC" <?php if($value->Geburtsland == 'LC'){echo "selected";} ?>>Saint Lucia</option>
									<option value="MF" <?php if($value->Geburtsland == 'MF'){echo "selected";} ?>>Saint Martin (French part)</option>
									<option value="PM" <?php if($value->Geburtsland == 'PM'){echo "selected";} ?>>Saint Pierre and Miquelon</option>
									<option value="VC" <?php if($value->Geburtsland == 'VC'){echo "selected";} ?>>Saint Vincent and the Grenadines</option>
									<option value="WS" <?php if($value->Geburtsland == 'WS'){echo "selected";} ?>>Samoa</option>
									<option value="SM" <?php if($value->Geburtsland == 'SM'){echo "selected";} ?>>San Marino</option>
									<option value="ST" <?php if($value->Geburtsland == 'ST'){echo "selected";} ?>>Sao Tome and Principe</option>
									<option value="SA" <?php if($value->Geburtsland == 'SA'){echo "selected";} ?>>Saudi Arabia</option>
									<option value="SN" <?php if($value->Geburtsland == 'SN'){echo "selected";} ?>>Senegal</option>
									<option value="RS" <?php if($value->Geburtsland == 'RS'){echo "selected";} ?>>Serbia</option>
									<option value="SC" <?php if($value->Geburtsland == 'SC'){echo "selected";} ?>>Seychelles</option>
									<option value="SL" <?php if($value->Geburtsland == 'SL'){echo "selected";} ?>>Sierra Leone</option>
									<option value="SG" <?php if($value->Geburtsland == 'SG'){echo "selected";} ?>>Singapore</option>
									<option value="SX" <?php if($value->Geburtsland == 'SX'){echo "selected";} ?>>Sint Maarten (Dutch part)</option>
									<option value="SK" <?php if($value->Geburtsland == 'SK'){echo "selected";} ?>>Slovakia</option>
									<option value="SI" <?php if($value->Geburtsland == 'SI'){echo "selected";} ?>>Slovenia</option>
									<option value="SB" <?php if($value->Geburtsland == 'SB'){echo "selected";} ?>>Solomon Islands</option>
									<option value="SO" <?php if($value->Geburtsland == 'SO'){echo "selected";} ?>>Somalia</option>
									<option value="ZA" <?php if($value->Geburtsland == 'ZA'){echo "selected";} ?>>South Africa</option>
									<option value="GS" <?php if($value->Geburtsland == 'GS'){echo "selected";} ?>>South Georgia and the South Sandwich Islands</option>
									<option value="SS" <?php if($value->Geburtsland == 'SS'){echo "selected";} ?>>South Sudan</option>
									<option value="ES" <?php if($value->Geburtsland == 'ES'){echo "selected";} ?>>Spain</option>
									<option value="LK" <?php if($value->Geburtsland == 'LK'){echo "selected";} ?>>Sri Lanka</option>
									<option value="SD" <?php if($value->Geburtsland == 'SD'){echo "selected";} ?>>Sudan</option>
									<option value="SR" <?php if($value->Geburtsland == 'SR'){echo "selected";} ?>>Suriname</option>
									<option value="SJ" <?php if($value->Geburtsland == 'SJ'){echo "selected";} ?>>Svalbard and Jan Mayen</option>
									<option value="SZ" <?php if($value->Geburtsland == 'SZ'){echo "selected";} ?>>Swaziland</option>
									<option value="SE" <?php if($value->Geburtsland == 'SE'){echo "selected";} ?>>Sweden</option>
									<option value="CH" <?php if($value->Geburtsland == 'CH'){echo "selected";} ?>>Switzerland</option>
									<option value="SY" <?php if($value->Geburtsland == 'SY'){echo "selected";} ?>>Syrian Arab Republic</option>
									<option value="TW" <?php if($value->Geburtsland == 'TW'){echo "selected";} ?>>Taiwan, Province of China</option>
									<option value="TJ" <?php if($value->Geburtsland == 'TJ'){echo "selected";} ?>>Tajikistan</option>
									<option value="TZ" <?php if($value->Geburtsland == 'TZ'){echo "selected";} ?>>Tanzania</option>
									<option value="TH" <?php if($value->Geburtsland == 'TH'){echo "selected";} ?>>Thailand</option>
									<option value="TL" <?php if($value->Geburtsland == 'TL'){echo "selected";} ?>>Timor-Leste</option>
									<option value="TG" <?php if($value->Geburtsland == 'TG'){echo "selected";} ?>>Togo</option>
									<option value="TK" <?php if($value->Geburtsland == 'TK'){echo "selected";} ?>>Tokelau</option>
									<option value="TO" <?php if($value->Geburtsland == 'TO'){echo "selected";} ?>>Tonga</option>
									<option value="TT" <?php if($value->Geburtsland == 'TT'){echo "selected";} ?>>Trinidad and Tobago</option>
									<option value="TN" <?php if($value->Geburtsland == 'TN'){echo "selected";} ?>>Tunisia</option>
									<option value="TR" <?php if($value->Geburtsland == 'TR'){echo "selected";} ?>>Turkey</option>
									<option value="TM" <?php if($value->Geburtsland == 'TM'){echo "selected";} ?>>Turkmenistan</option>
									<option value="TC" <?php if($value->Geburtsland == 'TC'){echo "selected";} ?>>Turks and Caicos Islands</option>
									<option value="TV" <?php if($value->Geburtsland == 'TV'){echo "selected";} ?>>Tuvalu</option>
									<option value="UG" <?php if($value->Geburtsland == 'UG'){echo "selected";} ?>>Uganda</option>
									<option value="UA" <?php if($value->Geburtsland == 'UA'){echo "selected";} ?>>Ukraine</option>
									<option value="AE" <?php if($value->Geburtsland == 'AE'){echo "selected";} ?>>United Arab Emirates</option>
									<option value="GB" <?php if($value->Geburtsland == 'GB'){echo "selected";} ?>>United Kingdom</option>
									<option value="US" <?php if($value->Geburtsland == 'US'){echo "selected";} ?>>United States</option>
									<option value="UM" <?php if($value->Geburtsland == 'UM'){echo "selected";} ?>>United States Minor Outlying Islands</option>
									<option value="UY" <?php if($value->Geburtsland == 'UY'){echo "selected";} ?>>Uruguay</option>
									<option value="UZ" <?php if($value->Geburtsland == 'UZ'){echo "selected";} ?>>Uzbekistan</option>
									<option value="VU" <?php if($value->Geburtsland == 'VU'){echo "selected";} ?>>Vanuatu</option>
									<option value="VE" <?php if($value->Geburtsland == 'VE'){echo "selected";} ?>>Venezuela, Bolivarian Republic of</option>
									<option value="VN" <?php if($value->Geburtsland == 'VN'){echo "selected";} ?>>Viet Nam</option>
									<option value="VG" <?php if($value->Geburtsland == 'VG'){echo "selected";} ?>>Virgin Islands, British</option>
									<option value="VI" <?php if($value->Geburtsland == 'VI'){echo "selected";} ?>>Virgin Islands, U.S.</option>
									<option value="WF" <?php if($value->Geburtsland == 'WF'){echo "selected";} ?>>Wallis and Futuna</option>
									<option value="EH" <?php if($value->Geburtsland == 'EH'){echo "selected";} ?>>Western Sahara</option>
									<option value="YE" <?php if($value->Geburtsland == 'YE'){echo "selected";} ?>>Yemen</option>
									<option value="ZM" <?php if($value->Geburtsland == 'ZM'){echo "selected";} ?>>Zambia</option>
									<option value="ZW" <?php if($value->Geburtsland == 'ZW'){echo "selected";} ?>>Zimbabwe</option>
								</select>
    						</div>
  					</div>
          		</div>
			</div>
			<?php $count++; } ?>
			
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