<?php 

require '../core/init.php';

if(!Session::exists(Config::get('session/session_name'))){
	Redirect::to(URL::clearQuery(login_url()));
	exit();
}else{
	$customer = new Customer(Session::get(Config::get('session/session_name')));
  $booking = new Booking(Session::get(Config::get('session/session_name')));
  $lastbooking = $booking->lastBookingData();
}

  if (isset($_POST['logout'])) {
    if (Token::check(Input::get('token'))) {
        $customer->logout();
        Redirect::to(URL::clearQuery(login_url()));
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
            <a href="<?php echo URL::clearQuery(dashboard_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->

          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(dashboard_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->

          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(dashboard_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->

          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(dashboard_url()) . '?lang=4' ;?>">
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
        <?php echo DASHBOARD; ?>
        <small><?php echo CUSTOMER_AREA; ?></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li class="active">Overview</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <!-- Small boxes (Stat box) -->
      <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <a href="<?php if (isset($_GET['lang'])) {
                              echo URL::clearQuery(account_personal_info_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(account_personal_info_url());
                            }?>">
          <div class="info-box bg-aqua" id="details_widget">
            <span class="info-box-icon"><i class="fa fa-inbox"></i></span>

            <div class="info-box-content">
              <span class="info-box-text"><?php echo PERSONAL_DETAILS;?></span>

              <div class="progress">
                <div class="progress-bar" style="width: 70%"></div>
              </div>
                  <span class="progress-description">
                    70% <?php echo COMPLETED; ?>
                  </span>
            </div>
            <!-- /.info-box-content -->
          </div>
          </a>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <a href="<?php if (isset($_GET['lang'])) {
                              echo URL::clearQuery(arrival_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(arrival_url());
                            }?>">
	          <div class="info-box bg-yellow" id="booking_event_widget">
	            <span class="info-box-icon"><i class="fa fa-calendar"></i></span>

	            <div class="info-box-content">
	              <span class="info-box-text" id="event-text"> </span>
	              <span class="info-box-number" id="days"> </span>

	              <div class="progress">
	                <div class="progress-bar" id="percentage-style"></div>
	              </div>
	                  <span class="progress-description" id="percentage-completed"></span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          </a>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <a href="<?php if (isset($_GET['lang'])) {
                              echo URL::clearQuery(arrival_url()) . '?lang=' . escape($_GET['lang']);
                            }else{
                              echo URL::clearQuery(arrival_url());
                            }?>">
          <div class="info-box bg-green" id="payments_widget">
            <span class="info-box-icon"><i class="fa fa-euro"></i></span>

            <div class="info-box-content">
              <span class="info-box-text" id="payments-title"></span>
              <span class="info-box-number" id="payments-until"></span>

              <div class="progress">
                <div class="progress-bar" style="width: 100%"></div>
              </div>
                  <span class="progress-description" id="payments-days-left"></span>
            </div>
          </div>
          </a>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="info-box" id="booking_contract_widget">
            <span class="info-box-icon bg-red"><i class="fa fa-check-square-o"></i></span>

            <div class="info-box-content">
              <span class="info-box-text"><?php echo BOOKING; ?></span>
              <span class="info-box-number" ><?php echo CONTRACT; ?> : <span id="contract-confirmed"></span> </span>
              <a href="#" class="btn btn-primary" id="contract-not-confirmed" style="display: none;"> <i class="fa fa-arrow-circle-right"></i></a>
            </div>
            
            <!-- /.info-box-content -->
          </div>
        </div>
        <!-- ./col -->
      </div>
      <!-- /.row -->
      <!-- Main row -->
      <div class="row">
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-6 connectedSortable">
        	<h2>Current Bookings</h2>
           <div style="max-height:250px; overflow-y: auto; overflow-x:hidden;">
              <table class="table table-responsive table-striped" >
                <thead>
                  <tr>
                      <th><?php echo PROPERTY; ?></th>
                      <th><?php echo FROM; ?></th>
                      <th><?php echo TO; ?></th>
                      <th><?php echo STATUS; ?></th>
                  </tr>
                </thead>
                <tbody>
                  <form method="post" id="myform">
                     <?php foreach ($booking->data() as $key => $value) {  $property = new Property($value->Objekt);?>
                        <tr>
                            <td>
        						<?php 

        							$days_from_booking = Date::daysDifference($value->BestaetigungFewoDatum, $value->von, true); 
        							$days_left = Date::daysDifference($value->von, date('Y-m-d'), true); 
        							$percentage = 100 - ($days_left/$days_from_booking * 100);
        							if ($value->BestaetigungFewo == 1) {
        								$booking_step1 = TRUE;
        							} else {
        								$booking_step1 = FALSE;
        							}
        							if ($value->BestaetigungKunde == 1) {
        								$booking_step2 = TRUE;
        								$contract_confirmed = TRUE;
        							} else {
        								$booking_step2 = FALSE;
        								$contract_confirmed = FALSE;
        							}

        							

        						?>
                        <label style="width:100%; height: 100%;"><input type="radio" onChange="openBooking('<?=$value->csid?>', 
                              																					'<?=$value->Objekt?>', 
                              																					'<?=$property->data()->feld1;?>', 
                              																					'<?=$days_left;?>', 
                              																					'<?= number_format((float)$percentage, 2, '.', '');?>', 
                              																					'<?=$value->Storno;?>', 
                              																					'<?=$value->Anreisetag;?>',
                              																					'<?=$booking_step1;?>', 
                              																					'<?=$booking_step2;?>', 
                              																					'<?=$contract_confirmed;?>'
                              																					);return false;" name="r3" class="flat-red" id="property_<?=$value->Objekt?>" <?php if($value->Storno == 0){echo "checked";} ?> /> <?=$property->data()->feld1;?></label>
								
                            </td>
                            <td>
                              <label><?=$value->von;?></label>
                            </td>
                            <td>
                              <label><?=$value->bis;?></label></div>
                            </td>
                            <td>
                              <?php if($value->Storno == 1){?>
                                <label><?php echo BOOKING_CANCELLED; ?></label>
                              <?php }else{?>
                                <label></label>
                              <?php }?>
                            </td>

                        </tr>
                     <?php }?>
                  </form>

                </tbody>
                
              </table>
          </div>
        </section>
        <!-- right col -->
      </div>
      <!-- /.row (main row) -->
     
    </section>


    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <?php View::renderFooter('dashboard_footer'); ?>
  
</div>
<!-- ./wrapper -->

<?php View::renderScripts('dashboard_script'); ?>

</body>
</html>


