<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Payment{
	private $_db, $_data = array();
	
	public function __construct($cost_id = null){
		$this->_db = PaymentsDB::getInstance();
		if($cost_id){
			$this->find($cost_id);
		}
	}
	
	public function create($fields = array()){
		if(!$this->_db->insert('Booking', $fields)){
			throw new Exception('There was a problemm while creating a booking');
		}
	}
	
	public function update($fields = array(), $id = null){
		if(!$id){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('Booking', 'ID', $id, $fields)){
			throw new Exception('There was a problemm while updating a booking');
		}
	}
	
	public function find($cost_id = null){
		if($cost_id){
			$field = 'cost_id';
			$data = $this->_db->get('Costs', array(array('Date', '=', $cost_id)));
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