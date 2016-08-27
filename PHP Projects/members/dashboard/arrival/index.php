<?php
  require '../../core/init.php';

  if(!Session::exists(Config::get('session/session_name'))){
  	Redirect::to(URL::clearQuery(login_url()));
  	exit();
  }else{
  	$customer = new Customer(Session::get(Config::get('session/session_name')));
    if (isset($_GET['lang'])) {
      $customer_language = escape(Input::get('lang'));
      if ($customer_language == '1') {
        $lang = '1';
      }elseif($customer_language == '2'){
        $lang = '2';
      }elseif($customer_language == '3'){
        $lang = '5';
      }elseif($customer_language == '4'){
        $lang = '2';
      }
    }else{
      $customer_language = $customer->data()->Sprache;
      if ($customer_language == '1') {
        $lang = '1';
      }elseif($customer_language == '2'){
        $lang = '2';
      }elseif($customer_language == '5'){
        $lang = '5';
      }elseif($customer_language == '13'){
        $lang = '2';
      }
      
    }
  	$propertyDescription = new PropertyDescription(2, $lang);
    $property = new Property('2');
    $owner = new Owner($property->data()->feld276);
  }
  if (isset($_POST['logout'])) {
    $customer->logout();
    Redirect::to(URL::clearQuery(login_url()));
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
            <a href="<?php echo URL::clearQuery(arrival_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->
          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(arrival_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->
          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(arrival_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->
          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(arrival_url()) . '?lang=4' ;?>">
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
        <?php echo IMPORTANT_INFORMATION; ?>
        <small><?php echo CUSTOMER_AREA; ?></small>
      </h1><br/>
      <button type="button" class="btn btn-primary" onclick="printDiv('printarea')"><i class="fa fa-print"></i> Print</button>
      <ol class="breadcrumb">
        <li><a href="<?php 
                      if (isset($_GET['lang'])) {
                        echo URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(dashboard_url());
                      }?>"><i class="fa fa-dashboard"></i> Dashboard</a></li>
        <li><a href="<?php
                      if (isset($_GET['lang'])) {
                        echo URL::clearQuery(arrival_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(arrival_url());
                      }?>"><i class="fa fa-warning" id="important_info_icon"></i> <span id="important_info_icon"><?php echo IMPORTANT_INFORMATION; ?></span></a></li>
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
			    <div class="box box-primary">
	            <div class="box-header with-border">
	              	<h3 class="box-title"><?php echo IMPORTANT_INFORMATION;?></h3><br/>
	              	<span><?php echo PLEASE_PRINT;?></span><br/>
	            	  <div class="box-tools pull-right">

		                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	              	</div>
	            </div>
	            <div class="box-body">
                <div class="table-responsive">
  	            	<table class="table">
      						  <tbody>
      						    <tr>
      						      <td><a href="<?php echo pdf_path() . 'Karte_Ligurien.pdf';?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MAP_LIGURIA;?></a></td>
                        <td><a href="<?php echo pdf_path() . 'Karte_Imperia.pdf';?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MAP_IMPERIA;?></a></td>
                        <td><a href="<?php echo pdf_path() . 'Karte_DianoMarina.pdf';?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MAP_DIANO_MARINA;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo GENERAL_INFORMATION;?></a></td>
      						    </tr>
      						    <tr>      						      
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo SMALL_TRAVELGUIDE_PONENTE;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info2.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info2.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info2.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info2.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info2.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info2.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info2.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info2.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo SMALL_TRAVELGUIDE_LEVANTE;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo HIKING_TIPS;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MOUNTAIN_BIKE_ROUTES;?></a></td>
      						    </tr>
      						    <tr>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo SHUTTLE_BUS;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo PARKING_LIGURIA;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo BEACHES;?></a></td>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo HOLIDAYS_WITH_DOG;?></a></td>
                      </tr>
                      <tr>
                        <td><a href="<?php 
                                        if (isset($_GET['lang'])) {
                                            $lang = Input::get('lang');
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '3') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '4') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }else{
                                            $lang = $customer->data()->Sprache;
                                            if ($lang == '1') {
                                              echo pdf_path() . 'de/info.pdf';
                                            } elseif($lang == '2') {
                                              echo pdf_path() . 'en/info.pdf';
                                            }elseif($lang == '5') {
                                              echo pdf_path() . 'fr/info.pdf';
                                            }elseif($lang == '13') {
                                              echo pdf_path() . 'nl/info.pdf';
                                            }
                                        }
                                      ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo CYCLE_PATH;?></a></td>
                          <?php 
                            if (isset($_GET['lang'])) {
                              $lang = escape(Input::get('lang'));

                              if ($lang == '1') { 
                                if ($property->data()->feld15 === 'ja') { ?>
                            <td><a href="<?php echo travel_descriptions_path() . $property->data()->feld565; ?>" target="blank" class="btn btn-default btn-sm" id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MY_DIRECTIONS;?></a></td>
                            <?php }
                            }
                          }
                          ?>
      						    </tr>
      						  </tbody>
      						</table>
                </div>
	            </div>
	        </div>
	        <div id="printarea">
		        <div class="box box-primary">
		            <div class="box-header with-border">
		              	<h3 class="box-title"><?php echo MY_DIRECTIONS;?></h3><br/>
		              	<div class="box-tools pull-right">
			                <button type="button"  class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
		              	</div>
		            </div>
		            <div class="box-body">
	                  <div class="col-md-6">
	                      <span style="font-size:20px;"><strong><u><?php echo DIRECTIONS;?></u></strong></span>
	                      <h4><?php echo nl2br($propertyDescription->data()->AnfahrtsbeschreibungDirekt); ?></h4>
	                      <?php if(isset($_GET['lang'])){ 
	                          $lang = escape(Input::get('lang'));
	                          if ($lang == '1') {
	                        ?>
	                        <a href="<?php echo travel_descriptions_path() . $property->data()->feld565; ?>" target="blank" class="btn btn-default btn-sm " id="pdf_button_link"><i class="fa fa-file-pdf-o" id="pdf_link"></i> <?php echo MY_DIRECTIONS;?></a><br/><br/>
	                        <?php } } ?>
	                      <span style="font-size:20px;"><strong><u><?php echo PROPERTY_EXTRA_INFO;?></u></strong></span>
	                      <h4><?=$property->data()->feld353;?> / <?=$owner->data()->Vorname?> <?=$owner->data()->Name?></h4>

	                  </div>
	                  <div class="col-md-6">
	                    <form target="_blank" action="https://maps.google.com/maps">
	                      <input type="hidden" name="daddr" value="<?=$property->data()->feld2;?>"/>
	                      <div class="row">
		                      <div class="col-xs-12">
			                      <div class="form-group">
			                          <label class="control-label" style="margin-left:15px;"><?php echo STARTING_ADDRESS;?> (<?php echo ADDRESS;?>, <?php echo POSTAL_CODE;?>) :</label>
			                      </div>
		                      </div>
	                      </div>
	                      <div class="row">
		                      <div class="col-xs-12">
		                      	<div class="col-xs-12 col-md-9">
		                      		<input type="text" class="form-control" name="saddr" value="<?=$customer->data()->Strasse;?>, <?=$customer->data()->PLZ;?> <?=$customer->data()->Ort;?>, <?=$customer->data()->Land;?>" />		
		                      	</div>
		                      	<div class="col-xs-12 col-md-3">
		                      		<input type="submit" name="submit" class="btn btn-primary" value="<?php echo GET_DIRECTIONS;?>" />
		                      	</div>
		                      </div>
	                      </div>
	                      </form>
	                  </div>
	                  
		            </div>
		        </div>

		        <div class="box box-primary">
		            <div class="box-header with-border">
		              	<h3 class="box-title"><?php echo MY_PROPERTY;?></h3><br/>
		              	<div class="box-tools pull-right">
			                <button type="button"  class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
		              	</div>
		            </div>
		            <div class="box-body">
	                  <div class="col-md-6">
	                      <span style="font-size:20px;"><strong><u>Link:</u></strong></span>&nbsp;&nbsp;&nbsp;<a target="blank" id="pdf_button_link" href="http://www.blumenriviera.co.uk/property/?id=<?=$property->data()->ID?>"><?=$property->data()->feld1?></a><br/><br/>
	                      <span style="font-size:20px;"><strong><u>GPS</u></strong>: <?=$property->data()->feld465?> / <?=$property->data()->feld466?></span><br/><br/>
	                      <h4><?php echo GPS_ADDITIONAL_TIPS;?></h4>
	                  </div>
	                  <div class="col-md-6">
	                    <table id="custom_stripes" class="table table-responsive table-striped">
	                    	<tbody>
            							<?php if(!empty($propertyDescription->data()->Parkplatz)){ ?>
            								<tr>
            									<td><?php echo PARKING_PLACE; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->Parkplatz); ?></td>
            								</tr>
            							<?php } ?>
            							<?php if(!empty($propertyDescription->data()->GashahnOrt)){ ?>
            								<tr>
            									<td><?php echo GAS_TAP; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->GashahnOrt); ?></td>
            								</tr>
            							<?php } ?>
            							<?php if(!empty($propertyDescription->data()->StromzaehlerOrt)){ ?>
            								<tr>
            									<td><?php echo ELECTRICITY_COUNTER; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->StromzaehlerOrt); ?></td>
            								</tr>
            							<?php } ?>
            							<?php if(!empty($propertyDescription->data()->GaszaehlerOrt)){ ?>
            								<tr>
            									<td><?php echo GAS_COUNTER; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->GaszaehlerOrt); ?></td>
            								</tr>
            							<?php } ?>
            							<?php if(!empty($propertyDescription->data()->WasserhaupthahnOrt)){ ?>
            								<tr>
            									<td><?php echo WATER_TAP; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->WasserhaupthahnOrt); ?></td>
            								</tr>
            							<?php } ?>
            							<?php if(!empty($propertyDescription->data()->BesonderheitenHaus)){ ?>
            								<tr>
            									<td><?php echo SPECIALITIES; ?>:</td>
            									<td><?php echo ucwords($propertyDescription->data()->BesonderheitenHaus); ?></td>
            								</tr>
            							<?php } ?>
                        </tbody>	                    	
	                    </table>
	                  </div>
	                  
		            </div>
		        </div>
	        </div>
    </section>
  </div>
  </section>
  </div>
  <?php View::renderFooter('dashboard_footer'); ?>
</div>
<?php View::renderScripts('dashboard_personal_details_script'); ?>
</body>
</html>


