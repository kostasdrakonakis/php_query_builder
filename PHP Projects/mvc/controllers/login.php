<?php 

class Login extends Controller{
	
	function __construct(){
		parent::__construct();
	}

	public function index(){
		$this->view->welcome_message = "Welcome Kostas";
		$this->view->render('login/index');
	}

	public function signin(){
		$this->load->model('Login_Model');
		$this->Login_Model->signin();
	}
	
}