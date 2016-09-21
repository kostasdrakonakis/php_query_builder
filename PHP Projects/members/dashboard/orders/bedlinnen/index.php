<?php 
	require '../../../core/init.php';

if(!Session::exists(Config::get('session/session_name'))){
	Redirect::to(URL::clearQuery(login_url()));
	exit();
}else{
	$customer = new Customer(Session::get(Config::get('session/session_name')));
	$booking = new Booking(Session::get(Config::get('session/session_name')));
  	$lastbooking = $booking->lastBookingData();
  	$property = new Property($lastbooking->Objekt);
  	$room = new Room(2);
  	$bookingDays = $booking->getBookingDays(Session::get(Config::get('session/session_name')), '123456789');
  	$bookingWeeks = $booking->getBookingWeeks(Session::get(Config::get('session/session_name')), '123456789');
  	$bwPriceValue = 0;
    $price = new Price(2);
  	$order = new Order(12345);
    //print_pre($price->data());
    //print_pre($order->data_bedlinnen());
    foreach ($price->data() as $key => $value) {
      if ($value->Titel == 'Bettwäsche' && $value->Pseudonym == 'KOSTEN_+1_BETTWAESCHE') {
        $bedlinnen_price = $value->price;
        $payment_method = $value->payment_type;
        $cost_method = $value->cost_type;
      }
      if ($value->Titel == 'Handtücher' && $value->Pseudonym == 'KOSTEN_+1_HANDTUECHER') {
        $handtowels_price = $value->price;
      }
      if ($value->Titel == 'Kinderbetten' && $value->Pseudonym == 'KOSTEN_+1_KINDERBETTEN') {
        $kidsbed_price = $value->price;
      }
      if ($value->Titel == 'Kinderstühle' && $value->Pseudonym == 'KOSTEN_+1_KINDERSTUEHLE') {
        $babychair_price = $value->price;
      }
      $propertyid = $value->property_id;
    }

    $bwBesitzer = ($property->data()->feld356 == 0) ? '0' : '1';
    $htBesitzer = ($property->data()->feld444 == 0) ? '0' : '1';
    $showBW = ($property->data()->feld228 == 'nein') ? 'nein' : 'ja';
    $showHT = ($property->data()->feld229 == 'nein') ? 'nein' : 'ja';
    $showKB = ($property->data()->feld226 == 'nein') ? 'nein' : 'ja';
    $showKS = ($property->data()->feld267 == 'nein') ? 'nein' : 'ja';

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
            <a href="<?php echo URL::clearQuery(orders_bedlinnen_url()) . '?lang=1' ;?>">
              <img src="<?php echo image_path();?>flags/de.png"/>
            </a>
          </li>
          <!-- End of German Flag -->
          <!-- English Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(orders_bedlinnen_url()) . '?lang=2' ;?>">
              <img src="<?php echo image_path();?>flags/gb.png"/>
            </a>
          </li>
          <!-- End of English Flag -->
          <!-- French Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(orders_bedlinnen_url()) . '?lang=3' ;?>">
              <img src="<?php echo image_path();?>flags/fr.png"/>
            </a>
          </li>
          <!-- End of French Flag -->
          <!-- Dutch Flag -->
          <li class="dropdown messages-menu">
            <a href="<?php echo URL::clearQuery(orders_bedlinnen_url()) . '?lang=4' ;?>">
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
        <?php echo BEDLINNEN; ?>
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
                        echo URL::clearQuery(orders_bedlinnen_url()) . '?lang=' . escape($_GET['lang']);
                      }else{
                        echo URL::clearQuery(orders_bedlinnen_url());
                      }?>"><i class="fa fa-bed"></i> <?php echo BEDLINNEN; ?></a></li>
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
		       	<h4><?php echo BEDLINNEN_DETAILS_MESSAGE;?></h4>
		       	<h4><?php echo BEDLINNEN_DETAILS_SUBMESSAGE;?></h4><br/>
	        </div>
	        <?php if($showBW == 'ja'){?>
		        <form method="post">
              <?php if($bwBesitzer == '0'){?>
		        	<table class="table table-responsive">
		        		<thead>
		        			<tr>
		        				<td><strong><?=DESCRIPTION;?></strong></td>
		        				<td><strong><?=ORDER;?></strong></td>
		        				<td><strong><?=OCCUPANCY;?></strong></td>
		        				<td><strong><?=PRICE;?></strong>&nbsp;  <button type="button" class="btn btn-warning btn-circle" data-toggle="modal" data-target="#myModal"><i class="fa fa-question"></i></button></td>
							        <div class="modal " id="myModal">
							          <div class="modal-dialog">
							            <div class="modal-content">
							              <div class="modal-header">
							                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							                  <span aria-hidden="true">&times;</span></button>
							                <h4 class="modal-title"><?php echo PRICE; ?> FAQ</h4>
							              </div>
							              <div class="modal-body">
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_1; ?></p>
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_2; ?></p>
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_3; ?></p>
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_4; ?></p>
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_5; ?></p>
							                <p><?php echo BEDLINNEN_PRICE_DETAILS_6; ?></p>
							              </div>
							              <div class="modal-footer">
							                <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">Close</button>
							              </div>
							            </div>
							          </div>
							        </div>
		        			</tr>
		        		</thead>
		        		<tbody>
		        				<?php foreach ($room->data() as $key => $value) {
		        				 		$bed = new Bed($value->Zid);
                        //print_pre($bed->data());
		        				 		?>
			        			<tr>
			        				<?php if($bed->count() > 0){ ?>
			        				<td>
                          <?php if ($value->Bezeichnung == 'Schlafzimmer') {?>
                              <u><?=SCHLAFZIMMER;?></u>
                          <?php }elseif($value->Bezeichnung == 'Wohnzimmer'){?>
                              <u><?=WOHNZIMMER;?></u>
                          <?php }?>
			        					
			        				</td>
			        				<?php } ?>
			        			</tr>
			        			<?php foreach ($bed->data() as $key => $value) { ?>
			        			<tr>
			        				
			        				<td style="padding-left:20px;">
                      <?php if ($value->Bezeichnung == 'Einzelbett') {?>
                              <?=EINZELBETT;?><br> <?=$value->strSize;?> cm:
                          <?php }elseif($value->Bezeichnung == 'Doppelbett'){?>
                              <?=DOPPELBETT;?><br> <?=$value->strSize;?> cm:
                          <?php }?>
										  
									</td>
									<td>
										<div class="form-group col-md-8">
										<input type="checkbox" class="flat-red" id="bed_<?=$value->BettID;?>" name="bed_<?=$value->BettID;?>" value="1"> <label for="bed_<?=$value->BettID;?>"><?=ORDER_BEDLINNEN;?></label><br>
										<input type="checkbox" class="flat-red" id="weekly_<?=$value->BettID;?>" name="weekly_<?=$value->BettID;?>" value="1" <?php if($bookingDays < 10){echo "disabled";} ?> /> <label for="weekly_<?=$value->BettID;?>"><?=CHANGE_WEEKLY;?></label>
										</div>
									</td>
									<td>
										<div class="form-group col-md-6">
											<select class="form-control" id="persons_<?=$value->BettID;?>" name="persons_<?=$value->BettID;?>">
												<?
												for( $iPersonen = 0; $iPersonen <= $value->Personen; $iPersonen++ )
												{
												?>
													<option value="<?=$iPersonen;?>"><?=$iPersonen;?> P</option>				
												<?
												}
												?>
											</select>
										</div>
									</td>
									<td>
										<input type="text" id="bwPrice_<?=$value->BettID;?>" name="bwPrice_<?=$value->BettID;?>" value="<?=$bwPriceValue?>" size="2" readonly /> &euro;
									</td>
									
			        		</tr>
                  <?php 
                  if( $value->Typ == 2 && $property->data()->feld228 != 'nein' ) {
                  ?>
                  <tr>
                    <td colspan="4" style="padding-left:10px;"><i><?=ORDER_NOTE;?></i><br><br></td>
                  </tr>
                  <?php
                  }
                  ?>
			        		<?php } ?>
		        			<?php }?>
                  <tr <?=($property->data()->feld228 =='nein'?'style="display:none"':'');?>>
                    <td style="border-top:1px solid #DDDDDD">&nbsp;</td>
                    <td align="left" style="border-top:1px solid #DDDDDD"><br>
                      <strong><?=TOTAL_BEDLINNEN;?></strong>: 
                      </td>
                    <td style="border-top:1px solid #DDDDDD"><br>
                      <input type="text" id="personsTotal"  name="personsTotal" value="0" size="2" readonly> &nbsp;P
                      </td>     
                    <td style=" border-top:1px solid #DDDDDD"><br>
                      <input type="text" id="bwTotalPrice" name="bwTotalPrice" value="0" size="2" readonly> &nbsp;&euro;
                      </td>
                  </tr>
		        		</tbody>
		        	</table>
              <?php }elseif($bwBesitzer == '1'){?>
                <h2><?php echo PROVIDED_BY_LANDLORD; ?></h2>
              <?php }?>
              <?php if ($property->data()->feld229 == 'ja' && $property->data()->feld444 == '0') { ?>
              <table class="table table-responsive">
                <h4><?=TOWELS_NOTE;?></h4>
                <thead <?=(($showHT=='nein'&&$showKB=='nein'&&$showKS=='nein')?'style="display:none"':'');?>>
                  <tr>
                    <td><strong><?=DESCRIPTION;?></strong></td>
                    <td><strong><?=ORDER;?></strong></td>
                    <td><strong><?=NUMBER;?></strong></td>
                    <td><strong><?=PRICE;?></strong></td>
                  </tr>
                </thead>
                <tr <?=($showHT=='nein'?'style="display:none"':'');?>>
                <td><?=TOWELS;?></td>
                <td>
                  <input type="checkbox" class="flat-red" id="handtuecherWeekly" name="handtuecherWeekly" <?php if($bookingDays < 10){echo "disabled";} ?> >
                    <label for="handtuecherWeekly"><?=CHANGE_WEEKLY;?></label>
                  </td>
                <td>
                  <input type="text" class="personalDataInputEd" id="handtuecherAnzahl" size="3" name="handtuecherAnzahl" <?=(($htBesitzer==1)?'disabled':'');?>>
                  </td>
                <td>
                  <input type="text" id="handtuecherPreis" name="handtuecherPreis" size="3" readonly> &nbsp;&euro; 
                </td>       
              </tr>
              <tr <?=($showKB=='nein'?'style="display:none"':'');?>>
                <td><?=COTS;?></td>
                <td></td>
                <td>
                  <input type="text" class="personalDataInputEd" id="kinderbettenAnzahl" size="3" name="kinderbettenAnzahl">
                </td>
                <td>
                  <input type="text" id="kinderbettenPreis" name="kinderbettenPreis" size="3" readonly> &nbsp;&euro; 
                </td>       
              </tr>
              <tr <?=($showKS=='nein'?'style="display:none"':'');?>>
                <td><?=HIGH_CHAIRS;?></td>
                <td></td>
                <td>
                  <input type="text" class="personalDataInputEd" id="kinderstuehleAnzahl" size="3" name="kinderstuehleAnzahl" >
                </td>
                <td>
                  <input type="text" id="kinderstuehlePreis" name="kinderstuehlePreis" size="3" readonly> &nbsp;&euro; 
                </td>       
              </tr>
              <tr <?=(($showHT=='nein'&&$showKB=='nein'&&$showKS=='nein')?'style="display:none"':'');?>>
                <td></td>
                <td><strong><?=FINAL_TOTAL;?> :</strong></td>
                <td><input type="text" id="totalPrice" name="totalPrice" value="0" size="3" readonly> &nbsp;&euro;</td>
              </tr>
              <tr>
                <td colspan="4" style="padding-left:10px;"><i><?=ORDER_TIP_1;?></i><br><br></td>
              </tr>
              <?php if($payment_method =='Bar'){?>
                <tr>
                  <td colspan="4" style="padding-left:10px;"><i><?=ORDER_TIP_2;?></i><br><br></td>
                </tr>
              <?php }?>
              <tr <?=(($showBW=='nein'&&$showHT=='nein'&&$showKB=='nein'&&$showKS=='nein')?'style="display:none"':'');?>>
              <td></td>
              <td><input type="submit" name="procceed_order" value="Update" class="btn btn-primary btn-lg pull right" /></td>
            </tr>
              </table>
              <?php }elseif($property->data()->feld229 == 'nein' && $property->data()->feld444 == '1'){ ?>
                <h2><?=HANDTOWELS_PROVIDED_BY_LANDLORD;?></h2>
              <?php } ?>
		        </form>
	        <?php }elseif($showBW == 'nein'){?>
	        	<h2><?php echo BEDLINNEN_NOT_ORDERABLE; ?></h2>
	        <?php }?>


    </section>
  </div>
  </section>
  </div>
  <?php View::renderFooter('dashboard_footer'); ?>
</div>
<?php View::renderScripts('dashboard_bedlinnen_script'); ?>
</body>
</html>