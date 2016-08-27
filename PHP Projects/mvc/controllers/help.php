<?php 

class Help extends Controller{
	
	function __construct(){
		parent::__construct();
	}

	public function index(){
		$this->view->render('help/index');
	}

	public function other(){
		$this->load->model('Help_Model');
		$this->load->model('Login_Model');
		$this->Help_Model->another();
		$this->Login_Model->signin();
	}
	
}