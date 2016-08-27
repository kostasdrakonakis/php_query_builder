<?php 


class App{
	
	function __construct(){
		$url = isset($_GET['url']) ? $_GET['url'] : null;
		$url = rtrim($url, '/');
		$url = explode('/', $url);

		//print_r($url);

		if (empty($url[0])) {
			require 'controllers/index.php';
			$controller = new Index();
			$controller->index();
			return false;
		}
		

		$file =  'controllers/' . $url[0] . '.php';
		try {
			if (file_exists($file)) {
				require $file;
			} else {
				$this->error();
			}
		} catch (Exception $e) {
			die($e->getMessage());	
		}
		
		$controller = Controller::getInstance($url[0]);

		if (isset($url[2])) {
			if (method_exists($controller, $url[1])) {
				$controller->{$url[1]}($url[2]);
			}else{
				$this->error();
			}
			
		}else{
			if (isset($url[1])) {
				if (method_exists($controller, $url[1])) {
					$controller->{$url[1]}();
				}else{
					$this->error();
				}
				
			}elseif($controller != null){
				$controller->index();
			}
		}
	}


	private function error(){
		require 'controllers/error.php';
		$controller = new Error();
		$controller->index();
		return false;
	}
}