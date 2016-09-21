<?php

/**
 * 
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Bed{
	private $_db, $_data = array(), $_count ;
	
	public function __construct($room_id = null){
		$this->_db = DB::getInstance();
		if($room_id){
			$this->find($room_id);
		}
	}
	
	public function create($fields = array()){
		if(!$this->_db->insert('Beds', $fields)){
			throw new Exception('There was a problemm while creating a booking');
		}
	}
	
	public function update($fields = array(), $id = null){
		if(!$id){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('Beds', 'ID', $id, $fields)){
			throw new Exception('There was a problemm while updating a booking');
		}
	}
	
	public function find($room_id = null){
		if($room_id){
			$data = $this->_db->join(array('Beds.ID AS bed_id', 'rooms', 'Bezeichnung', 'type', 'strSize', 'Persons'), array('Beds' => 'type', 'Bedtypes' => 'ID'), array(array('rooms', '=', $room_id)));
			if($data->count()){
				$this->_data = $data->results();
				$this->_count = $data->count();
				return true;
			}
		}
		return false;
	}
	
	public function data(){
		return $this->_data;
	}

	public function count(){
		return $this->_count;
	}
	
}

?>