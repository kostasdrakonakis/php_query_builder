<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Order{
	private $_db, $_data_bedlinnen = array(), $_data_others = array(), $_count ;
	
	public function __construct($booking_id = null){
		$this->_db = DB::getInstance();
		if($booking_id){
			$this->findbedlinnen($booking_id);
			$this->findothers($booking_id);
		}
	}
	
	public function create_bedlinnen($fields = array()){
		if(!$this->_db->insert('OrderBW', $fields)){
			throw new Exception('There was a problemm while creating an order for bedlinnen');
		}
	}

	public function create_others($fields = array()){
		if(!$this->_db->insert('Order', $fields)){
			throw new Exception('There was a problemm while creating an order for handtowels, kidsbed, babychairs');
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
	
	public function findbedlinnen($booking_id = null){
		if($booking_id){
			$field = 'Booking';
			$data = $this->_db->get('OrderBW', array(array($field, '=', $booking_id)));
			if($data->count()){
				$this->_data_bedlinnen = $data->results();
				$this->_count = $data->count();
				return true;
			}
		}
		return false;
	}

	public function findothers($booking_id = null){
		if($booking_id){
			$field = 'Booking';
			$data = $this->_db->get('Order', array(array($field, '=', $booking_id)));

			$data = $this->_db->join(array('Order.Anzahl as number', 'Order.woechtlWechsel as weekly_change', 'Order.Preis as price', 'OrderNumber.Bezeichnung as description'), array('Order' => 'OrderNumber', 'OrderNumber' => 'ID'), array(array($field, '=', $booking_id)));
			if($data->count()){
				$this->_data_others = $data->results();
				return true;
			}
		}
		return false;
	}
	
	public function data_bedlinnen(){
		return $this->_data_bedlinnen;
	}

	public function data_others(){
		return $this->_data_others;
	}

	public function count(){
		return $this->_count;
	}
	
}

?>