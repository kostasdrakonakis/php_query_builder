<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Outbox{

	private $_db, $_data = array(), $_datafirst = array();
	
	function __construct($bookingid = null){
		$this->_db = DB::getInstance();
		if($bookingid){
			$this->find($bookingid);
			$this->findById($bookingid);
		}
	}

	public function create($fields = array()){
		if(!$this->_db->insert('csMessages', $fields)){
			throw new Exception('There was a problemm while creating an outbox email');
		}
	}

	public function find($bookingid = null){
		if($bookingid){
			$field = 'Booking';
			$data = $this->_db->get_ordered('csMessages', array(array($field, '=', $bookingid)), 'Date', 'DESC');
			if($data->count()){
				$this->_data = $data->results();
				return true;
			}
		}
		return false;
	}

	public function findById($bookingid = null){
		if($bookingid){
			$field = 'ID';
			$data = $this->_db->get_ordered('csMessages', array(array($field, '=', $bookingid)), 'Date', 'DESC');
			if($data->count()){
				$this->_datafirst = $data->first();
				return true;
			}
		}
		return false;
	}

	public function data(){
		return $this->_data;
	}

	public function firstresult(){
		return $this->_datafirst;
	}
}