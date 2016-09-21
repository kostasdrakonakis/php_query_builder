<footer class="main-footer">
    <div class="pull-right hidden-xs">
      <strong>All rights reserved</strong>
    </div>
    <strong>Copyright &copy; 2016-<?php echo date('Y');?> <a target="blank" href="<?php if (isset($_GET['lang'])) {
											    	$lang = $_GET['lang'];
											    	if ($lang == 1) {
											    		$url = 'http://www.example.de';
											    	}elseif ($lang == 2) {
											    		$url = 'http://www.example.co.uk';
											    	}elseif ($lang == 3) {
											    		$url = 'http://www.example.fr';
											    	}elseif ($lang == 4) {
											    		$url = 'http://www.example.nl';
											    	}
											    }else{
											    	$customer = new Customer(Session::get(Config::get("session/session_name")));
											    	$lang = $customer->data()->Sprache;
											    	if ($lang == 1) {
											    		$url = 'http://www.example.de';
											    	}elseif ($lang == 2) {
											    		$url = 'http://www.example.co.uk';
											    	}elseif ($lang == 5) {
											    		$url = 'http://www.example.fr';
											    	}elseif ($lang == 13) {
											    		$url = 'http://www.example.nl';
											    	}
											    }
    											echo $url;
    											?>
    											">Backend</a></strong>
  </footer>