<?php

if (!defined('BASEPATH'))
exit('No direct script access allowed');

class User extends CI_Controller {

	public function __construct() {
		parent::__construct();
		$this->load->model('MY_user');
		$this->load->model('MY_articles');
		session_start();
	}

	public function signup() {
		$data['name'] = '';
		$data['email'] = '';
		$this->load->view('register',$data);
	}

	public function registration_success(){
		$this->load->model('MY_user');
		if (isset($_POST)) {
			$this->load->helper('url');
			$user_name = $_POST['InputName'];
			$email = $_POST['InputEmail'];
			$nameCount = $this->MY_user->isUserNameExist($user_name);
			if($nameCount >0){
				$data['name_error'] = 'User name is already registerd.';
				$data['success']='';
			}
			$emailCount = $this->MY_user->isEmailExist($email);
			if($emailCount > 0){
				$data['email_error'] = 'Email is already registered.';
				$data['success']='';
				
			}

			if($nameCount >0 && $emailCount >0){
				$data['both_error'] = 'User name and email both are registered.';
				$data['name_error']  ='';
				$data['email_error']='';
				$data['success']='';
			}
			$data['name'] = $user_name;
			$data['email'] = $email;
			if($nameCount==0 && $emailCount==0){

					mail($email, 'My Awesome Portal Registration', '<p>Please click on link to complete your registration.</p><br/>
					  			<a href=\"'.$this->config->base_url().'user/new_password/'.$user_name.'\" target="_blank">'.$this->config->base_url().'user/new_password/'.$user_name.'</a>');
					$config = array(
					  'protocol' => 'smtp',
					  'smtp_host' => 'ssl://smtp.gmail.com', //You can use ssl://smtp.live.com for hotmail
					  'smtp_port' => '465', // 587 port for hotmail
					  'smtp_user' => 'email@gmail.com',
					  'smtp_pass' => '',
					  'mailtype' => 'html',
					  'charset' => 'iso-8859-1',
					  'wordwrap' => TRUE
					);
					$this->load->library('email', $config);
					$this->email->initialize($config);
					$this->email->set_newline("\r\n");
					$this->email->from('myawesomeportal@gmail.com', 'My Awesome Portal');
					$this->email->to($email);
					$this->email->subject('News Channel Registration');
					$html = '<p>Please click on link to complete your registration.</p><br/>
					  			<a href=\"'.$this->config->base_url().'user/new_password/'.$user_name.'\" target="_blank">'.$this->config->base_url().'user/new_password/'.$user_name.'</a>';
					$this->email->message($html);
					$send = $this->email->send();
					if (!$send) {
					    $data['success'] = 'Email send is failed. Please try again';
						$data['name'] = '';
						$data['email'] = ''; 
					}else {
					    $data['success'] = 'Email is sent to you. Check your email.';
					    $data_user = array(
							'user_name'	=>	$user_name,
							'user_email'	=> $email
						);
						$user_id = $this->MY_user->create_user($data_user);
						$data['name'] = '';
						$data['email'] = '';
					}
			}
			$this->load->view('register',$data);
		}else{
			$data['both_error'] = '';
			$data['name_error']  ='';
			$data['email_error']='';
			$data['success']='';
			$data['name'] = '';
			$data['email'] = '';
			$this->load->view('register',$data);

		}

	}

	public function new_password($user_name){
			$data['success']='';
			$data['username']=$user_name;
			$this->load->view('newpassword',$data);
	}

	public function set_password($user_name){
		if ($_POST){
			$userInfo = $this->MY_user->get_password($user_name);
			if($userInfo['user_password']){
				redirect(base_url().'news');
			}
			$password = $_POST['InputNewPassword'];
			$data_user = array(
							'user_password'	=>	$password
							);
			$this->MY_user->update_user($user_name,$data_user);
			$data['success']='You can now login.';
			$data['username']=$user_name;
			$_SESSION['set_password']= $user_name;
			$this->load->view('newpassword',$data);
		}else{
			$data['success']='';
			if(isset($_SESSION['set_password'])){
				$data['username']=$_SESSION['set_password'];
			}else{
				session_unset(); 
				session_destroy();
	 			redirect(base_url().'news');
			}
			$this->load->view('newpassword',$data);
		}
	}

	public function signin(){
		$data['username']='';
		$data['password']='';
		$this->load->view('login',$data);
	}
	
	public function login(){
		if ($_POST) {
			$user_name = $_POST['InputUserName'];
			$password = $_POST['InputPassword'];
			$nameCount = $this->MY_user->isUserNameExist($user_name);
			
			if($nameCount ==1){
					$userInfo = $this->MY_user->get_password($user_name);
					if($userInfo['user_password']==$password){
						$data['articles'] = $this->view_user_article($user_name);
						$data['user_name'] = $user_name;
						$_SESSION['user_name'] = $user_name;
						$_SESSION['articles'] = $this->view_user_article($user_name);
						$this->load->view('usermain',$data);
					}else{
						$data['error'] = 'Password is incorrect';
						$this->load->view('login',$data);
					}
			}else{
				$data['error'] = 'User name is incorrect';
				$this->load->view('login',$data);
			}
		}else if(!isset($_SESSION['user_name'])){
			$data['username']='';
			$data['password']='';
			$this->load->view('login',$data);
		}else if(isset($_SESSION['articles'])){
	    	$data['articles']=$_SESSION['articles'] ;
	    	$data['user_name']=$_SESSION['user_name']; 
	    	$this->load->view('usermain',$data);

	    }

	}

	public function view_user_article($user_name){
	   $result = $this->MY_articles->get_user_article($user_name);
		if ($result != false) {
			return $result;
		}else{
			return '';
		}
 	}
 public function view_table(){
	$result = $this->articles->show_all_news();
		if ($result != false) {
			return $result;
		}else{
			return 'Database is empty !';
		}
}

 public function logout(){
 	session_unset();
	session_destroy();
 	redirect(base_url());
 }

 public function delete_user_post($id,$userName){
 	$this->MY_articles->delete_user_article($id);
 	$data['articles'] = $this->view_user_article($userName);
	$this->load->view('usermain',$data);

 }

 public function new_post($user_name){
 		$data['error'] = '';
 		$data['user_name'] = $user_name;
		$this->load->view('user_post',$data);
 }

 public function save_post($user_name){
 	if ($_POST){
 		
 		$title = $_POST['PostTitle'];
 		$text = $_POST['PostText'];
		$new_image_name = 'image_' . date('Y-m-d-H-i-s') . '_' . uniqid() . '.jpg';
		move_uploaded_file($_FILES["PostPhoto"]["tmp_name"], $_SERVER['DOCUMENT_ROOT'] . '/portal/images/'.$new_image_name);
		$data_user = array(
							'title'	=>	$title,
							'text'  => $text,
							'photo' => $new_image_name,
							 'user_name' => $user_name
							);
		$this->MY_articles->create_article($data_user);
		$data['articles'] = $this->view_user_article($user_name);
		$data['user_name'] = $user_name;
		$_SESSION['articles'] = $this->view_user_article($user_name);
		$this->load->view('usermain',$data);



 	}
 }
}

?>