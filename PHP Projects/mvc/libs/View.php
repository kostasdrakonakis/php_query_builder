<?php 

class View{
	
	function __construct(){
		log_message('DEBUG', 'View Class Instanciated!');
	}

	public function render($name){
		require 'views/header.php';
		require 'views/' . $name . '.php';
		require 'views/footer.php';
	}

}