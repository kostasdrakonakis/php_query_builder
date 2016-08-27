<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Customer{
	private $_db, $_data = array(), $_sessionName, $_isLoggedIn;
	
	public function __construct($customer = null){
		$this->_db = DB::getInstance();
		$this->_sessionName = Config::get('session/session_name');
		if(!$customer){
			if(Session::exists($this->_sessionName)){
				$customer = Session::get($this->_sessionName);
				if($this->find($customer)){
					$this->_isLoggedIn = true;
				}
			}
		}else{
			$this->find($customer);
		}
	}
	
	public function create($fields = array()){
		if(!$this->_db->insert('Customer', $fields)){
			throw new Exception('There was a problemm while creating a customer');
		}
	}
	
	public function update($fields = array(), $id = null){
		if(!$id && $this->isLoggedIn()){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('Customer', 'ID', $id, $fields)){
			throw new Exception('There was a problemm while updating a customer');
		}
	}
	
	public function find($customer = null){
		if($customer){
			$field = (is_numeric($customer)) ? 'ID' : 'EMail';
			$data = $this->_db->get('Customer', array(array($field, '=', $customer)));
			if($data->count()){
				$this->_data = $data->first();
				return true;
			}
		}
		return false;
	}
	
	public function login($email = null, $password = null){
		$customer = $this->find($email);
		if($customer){
			if(!empty($this->data()->salt)){
				$pass = Hash::make($password, $this->data()->salt);
				if ($this->data()->Passwort === $pass) {
					Session::put($this->_sessionName, $this->data()->ID);
					return true;
				}
			}
		}
		return false;
	}
	
	public function data(){
		return $this->_data;
	}
	
	public function isLoggedIn(){
		return $this->_isLoggedIn;
	}
	
	public function logout(){
		Session::delete($this->_sessionName);
	}
}

?>