<?php 

class Loader{
	
	function __construct(){
		log_message('DEBUG', 'Loader Class Instanciated!');
	}

	public function model($model){
		$url = isset($_GET['url']) ? $_GET['url'] : null;
		$controller = Controller::getInstance($url[0]);
		$file = 'models/' . $model . '.php';
		if (file_exists($file)) {
			require $file;
			$controller->{$model} = new $model();
			log_message('INFO', 'Model: ' . $model . ' loaded successfully');
			return $controller->{$model};
		}
	}

}