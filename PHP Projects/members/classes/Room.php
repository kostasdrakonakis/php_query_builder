<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Room{
	private $_db, $_data = array();
	
	public function __construct($property_id = null){
		$this->_db = DB::getInstance();
		if($property_id){
			$this->find($property_id);
		}
	}
	
	public function create($fields = array()){
		if(!$this->_db->insert('Room', $fields)){
			throw new Exception('There was a problemm while creating a booking');
		}
	}
	
	public function update($fields = array(), $id = null){
		if(!$id){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('Room', 'ID', $id, $fields)){
			throw new Exception('There was a problemm while updating a booking');
		}
	}
	
	public function find($property_id = null){
		if($property_id){
			$field = 'property';
			$data = $this->_db->join(array('Room.ID as Zid, Roomtypen.description'), array('Room' => 'Typ', 'Roomtypen' => 'ID'), array(array($field, '=', $property_id)));
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