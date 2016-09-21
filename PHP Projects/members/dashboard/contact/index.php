<?php 
	require '../../core/init.php';

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
$salutation = '';
$template = new Template();
$template->getTemplateFromDB(31);
$template_for_office = $template->data();
$template->getTemplateFromDB(32, $customer->data()->Sprache);
$template_for_customer = $template->data();
$template->getTemplateFromDB(299);
$template_contact_details = $template->data();
$template299 = $template_contact_details->Vorlage;
$message_for_office = str_replace( array(
                        '<k_ID>',
                        '<b_ID>',
                        '<k_Sprache>',
                        '<k_Name>',
                        '<k_Vorname>',
                        '<k_Strasse>',
                        '<k_Land>',
                        '<k_PLZ>',
                        '<k_Ort>',
                        '<k_Telefon1>',
                        '<b_Objekt>',
                        '<b_von>',
                        '<b_bis>',
                        ), 
                        array(
                          $customer->data()->ID,
                          '12345',
                          $customer->data()->Sprache,
                          $customer->data()->Name,
                          $customer->data()->Vorname,
                          $customer->data()->Strasse,
                          $customer->data()->Land,
                          $customer->data()->PLZ,
                          $customer->data()->Ort,
                          $customer->data()->Telefon1,
                          'Test Property 1',
                          '2017-12-09',
                          '2017-12-16',
                        ), 
                        $template_for_office->Vorlage);


if ($customer->data()->Geschlecht == "m" && $customer->data()->Sprache == "1") {
  $salutation = "Sehr geehrter Herr";
} elseif ($customer->data()->Geschlecht == "w" && $customer->data()->Sprache == "1") {
  $salutation = "Sehr geehrte Frau";
}elseif ($customer->data()->Geschlecht == "Familie" && $customer->data()->Sprache == "1") {
  $salutation = "Sehr geehrte Familie";
}elseif ($customer->data()->Geschlecht == "m" && $customer->data()->Sprache == "2") {
  $salutation = "Dear Mr.";
}elseif ($customer->data()->Geschlecht == "w" && $customer->data()->Sprache == "2") {
  $salutation = "Dear Mrs.";
}elseif ($customer->data()->Geschlecht == "Familie" && $customer->data()->Sprache == "2") {
  $salutation = "Dear Family";
}elseif ($customer->data()->Geschlecht == "m" && $customer->data()->Sprache == "5") {
  $salutation = "Cher Monsieur";
}elseif ($customer->data()->Geschlecht == "w" && $customer->data()->Sprache == "5") {
  $salutation = "Cher madame";
}elseif ($customer->data()->Geschlecht == "Familie" && $customer->data()->Sprache == "5") {
  $salutation = "Cher famille";
}elseif ($customer->data()->Geschlecht == "m" && $customer->data()->Sprache == "13") {
  $salutation = "Geachte Heer";
}elseif ($customer->data()->Geschlecht == "w" && $customer->data()->Sprache == "13") {
  $salutation = "Geachte Mevrouw";
}elseif ($customer->data()->Geschlecht == "Familie" && $customer->data()->Sprache == "13") {
  $salutation = "Beste familie";
}


$message_for_customer = str_replace( array(
                        '<anrede>',
                        '<k_Titel>',
                        '<k_Name>',
                        '<t_202>',
                        ), 
                        array(
                          $salutation,
                          $customer->data()->Titel,
                          $customer->data()->Name,
                          $template299,
                        ), 
                        $template_for_customer->Vorlage);

$subject_for_customer = str_replace( array(
                        '<b_Objekt>'
                        ), 
                        array(
                          'Test Property 1',
                        ), 
                        $template_for_customer->Betreff);
//print_pre($subject_for_customer);
//booking id: 28283

if (isset($_POST['sendbtn'])) {
  if (Token::check(Input::get('token'))) {
    $mail = new PHPMailer();
    $mail->setFrom('booking@example.com', 'Member Area');
    $mail->addReplyTo($customer->data()->EMail);
    $mail->addAddress('booking@example.com');
    $mail->isHTML(true);
    $mail->Subject = $customer->data()->Vorname . ' ' . $customer->data()->Name . ' - ' .  Input::get('message_subject');
    $mail->Body    = nl2br($message_for_office) . 'Message From Customer: ' . Input::get('mail_body');
    $mail->send();

    $mail_to_customer = new PHPMailer();
    $mail_to_customer->setFrom('booking@example.com', 'Member Area');
    $mail_to_customer->addReplyTo($customer->data()->EMail);
    $mail_to_customer->addAddress($customer->data()->EMail);
    $mail_to_customer->isHTML(true);
    $mail_to_customer->Subject = $subject_for_customer;
    $mail_to_customer->Body    = nl2br($message_for_customer);
    $mail_to_customer->send();

    $outbox = new Outbox();
    $outbox->create(array(
          'Buchung' => '12345',
          'Betreff' => Input::get('message_subject'),
          'Nachricht' => Input::get('mail_body'),
          'DatumZeit' => Date::getDateTime()
      ));
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
            <a href="<?php echo URL::clearQuery(contact_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->
          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(contact_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->
          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(contact_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->
          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(contact_url()) . '?lang=4' ;?>">
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
                    <input type="hidden" name="token_logout" value="<?php echo Token::generate(); ?>">
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
        	<form method="post">
        	<div class="col-md-12">
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title"><?php echo COMPOSE; ?></h3>
            </div>
            <div class="box-body">
              <div class="form-group">
                <input class="form-control" placeholder="<?php echo SUBJECT; ?>:" name="message_subject">
              </div>
              <div class="form-group">
                    <textarea id="compose-textarea" class="form-control" style="height: 300px" name="mail_body" placeholder="<?php echo MESSAGE; ?>..."></textarea>
              </div>
            </div>
            <div class="box-footer">
              <div class="pull-right">
                <input type="hidden" name="token" value="<?php echo Token::generate(); ?>">
                <button type="submit" class="btn btn-primary" name="sendbtn"><i class="fa fa-envelope-o"></i> <?php echo SEND_BUTTON; ?></button>
              </div>
            </div>
          </div>
        </div>
			</form>
    </section>
  </div>
  </section>
  </div>
  <?php View::renderFooter('dashboard_footer'); ?>
</div>
<?php View::renderScripts('dashboard_compose'); ?>
</body>
</html>