	<meta http-equiv="content-type" content="text/html; charset=<?php echo Config::get('database/character_encoding');?>"> 
	<meta charset="<?php echo Config::get('database/charset_html');?>">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=<?php echo Config::get('html/width');?>, initial-scale=<?php echo Config::get('html/initial_scale');?>, maximum-scale=<?php echo Config::get('html/maximum_scale');?>, user-scalable=<?php echo Config::get('html/user_scalable');?>" name="viewport">
	<?php if(isset($_GET['lang'])){
		$lang = escape($_GET['lang']);
		if ($lang == '1') {?>
<title><?php echo CUSTOMER_AREA; ?> | Dashboard</title>
		<?php }elseif($lang == '2') { ?>
<title><?php echo CUSTOMER_AREA; ?> | Dashboard</title>
		<?php }elseif($lang == '3') { ?>
<title><?php echo CUSTOMER_AREA; ?> | Dashboard</title>
		<?php }elseif($lang == '4') { ?>
<title><?php echo CUSTOMER_AREA; ?> | Dashboard</title>
		<?php }
	}else{ ?>
<title><?php echo CUSTOMER_AREA; ?> | Dashboard</title>	
	<?php }?>

	<link rel="stylesheet" href="<?php echo css_path();?>bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="<?php echo css_path();?>font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="<?php echo css_path();?>ionic/ionicons.min.css">
	<link rel="stylesheet" href="<?php echo css_path();?>skins/_all-skins.min.css">
	<link rel="stylesheet" href="<?php echo css_path();?>icheck/all.css">
	<link rel="stylesheet" href="<?php echo css_path();?>daterangepicker/datepicker.css">
	<link rel="stylesheet" href="<?php echo css_path();?>daterangepicker/daterangepicker.css">
	<link rel="stylesheet" href="<?php echo css_path();?>custom/custom.css">
	<link rel="stylesheet" href="<?php echo css_path();?>wysi/bootstrap3-wysihtml5.min.css">
