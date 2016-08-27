<?php 

class Controller{

	private static $_instance = null;

	function __construct(){
		log_message('DEBUG', 'Controller Class Instanciated!');
		$this->view = new View();
		$this->load = new Loader();
	}

	public static function getInstance($controller){
		if(!isset(self::$_instance)){
			$file =  'controllers/' . $controller . '.php';
			if (file_exists($file)) {
				self::$_instance = new $controller;
			}else{
				self::$_instance = null;
			}
			
		}
		return self::$_instance;
	}

}