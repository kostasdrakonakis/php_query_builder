<?php 

class Login_Model extends Model{
	
	function __construct(){
		parent::__construct();
		echo "Login Model class instanciated<br/>";
	}

	public function signin(){
		echo "User signin method init.<br/>";
	}

	public function signup(){
		echo "User signup method init.<br/>";
	}

}