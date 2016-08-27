<?php

/**
 *
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class FellowTraveller{
	private $_db, $_data = array(), $_sessionName, $_isLoggedIn;
	
	public function __construct($customeremail = null){
		$this->_db = DB::getInstance();
		if($customeremail){
			$this->find($customeremail);
		}
	}
	
	public function update($fields = array(), $id = null){
		if(!$id){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('FellowTraveller', 'ID', $id, $fields)){
			throw new Exception('There was a problemm while updating a fellow traveller');
		}
	}
	
	public function find($customerid = null){
		if($customerid){
			$field = 'Buchung';
			$data = $this->_db->get('FellowTraveller', array(array($field, '=', $customerid)));
			if($data->count()){
				$this->_data = $data->results();
				return true;
			}
		}
		return false;
	}
	
	
	public function data(){
		return $this->_data;
	}
}

?>