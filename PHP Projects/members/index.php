<?php 

	require_once 'core/init.php';

	if (Session::exists(Config::get('session/session_name'))) {
		if (isset($_GET['lang'])) {
			Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']));
		}else{
			Redirect::to(dashboard_url());
		}
	}

	if (isset($_POST['submit'])) {
		if (Token::check(Input::get('token'))) {
			$validate = new Validation();
    		$validation = $validate->check($_POST, array(
    			'email' => array(
    					'required' => true
    				),
    			'password' => array(
    					'required' => true
    				)
    		));
    		if ($validation->passed() && $validation != NULL) {
				try {

					$customer = new Customer();

					$result = $customer->login(Input::get('email'), Input::get('password'));
					if ($result) {
						if (isset($_GET['lang'])) {
							Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=' . escape($_GET['lang']));
						}else{

							switch ($customer->data()->Sprache) {
								case '1':
									Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=1');
									break;
								case '2':
									Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=2');
									break;
								case '5':
									Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=3');
									break;
								case '13':
									Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=4');
									break;
								default:
									Redirect::to(URL::clearQuery(dashboard_url()) . '?lang=2');
									break;
							}
						}
						
					}else{
						Session::flash('login_failure', INCORRECT_CREDENTIALS);
						Redirect::to(login_url());
					}
				} catch (Exception $e) {
					
				}
			}
			
		}
	}
?>
<!DOCTYPE html>
<html lang="en">
<head>
<?php View::renderHeader('dashboard_header');?>
</head>
<body class="hold-transition login-page">
<div class="login-box">
	<h1><a href="http://www.blumenriviera.de" target="blank"><img class="img-responsive" src="<?php echo image_path(); ?>website/brlogo.png"></a></h1>
  <div class="login-logo">
    <a href="<?php echo login_url();?>"><b>Back </b>Office</a>

    <h3 class="login-logo" style="color: #c3bdbd;"><?php echo CUSTOMER_AREA; ?></h3>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
  	<div class="col-md-12 col-md-offset-2 col-xs-offset-2" style="padding: 15px;">
	  	<a href="<?php echo URL::clearQuery(login_url()); ?>?lang=1" id="flaglink"><img src="<?php echo image_path();?>flags/de.png"></a>
	  	<a href="<?php echo URL::clearQuery(login_url()); ?>?lang=2" id="flaglink"><img src="<?php echo image_path();?>flags/gb.png"></a>
	  	<a href="<?php echo URL::clearQuery(login_url()); ?>?lang=3" id="flaglink"><img src="<?php echo image_path();?>flags/fr.png"></a>
	  	<a href="<?php echo URL::clearQuery(login_url()); ?>?lang=4" id="flaglink"><img src="<?php echo image_path();?>flags/nl.png"></a>
  	</div>
    <p class="login-box-msg"><?php echo LOGIN_AREA_MESSAGE; ?></p>

    <form  method="post">
      <div class="form-group has-feedback">
        <input type="email" name="email" class="form-control" placeholder="Email" value="<?php echo Input::get('email'); ?>" required="required">
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="<?php echo PASSWORD; ?>" required="required">
      </div>
      <div class="form-group has-feedback">
        <input type="hidden" class="form-control" name="token" value="<?php echo Token::generate(); ?>">
      </div>
      <div class="row">
        <div class="col-xs-4">
          <button type="submit" name="submit" class="btn btn-primary btn-block btn-flat"><?php echo LOGIN; ?></button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    <br/>
    <a href="<?php echo forgot_url(); ?>"><?php echo FORGOT; ?></a><br>

  </div>
  <!-- /.login-box-body -->
</div>
<?php 	
		if(Session::exists('login_failure')){
			Message::displayErrorMessage(Session::flash('login_failure'));
		}
		if (isset($_POST['submit'])) {
			foreach ($validation->errors() as $key => $value) {
				Message::displayErrorMessage($value);
			}
		}

		
		?>

<!-- /.login-box -->
<?php View::renderScripts('dashboard_script');?>

</body>
</html>