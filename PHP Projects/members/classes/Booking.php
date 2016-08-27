<?php

/**
 * 
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Booking{
	private $_db, $_data = array(), $_lastbookingdata = array();
	
	public function __construct($customer = null){
		$this->_db = DB::getInstance();
		if($customer){
			$this->find($customer);
			$this->getLastActiveBooking($customer);
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
	
	public function find($customer = null){
		if($customer){
			$field = (is_numeric($customer)) ? 'customer' : 'property';
			$data = $this->_db->get_ordered('Booking', array(array($field, '=', $customer)), 'arrival_day', 'DESC');
			if($data->count()){
				$this->_data = $data->results();
				return true;
			}
		}
		return false;
	}

	public function getLastActiveBooking($customer_id = null){
		if ($customer_id) {
			$booking_data = $this->_db->get_ordered('Booking', array(array('customer', '=', $customer_id), 
																		array('cancellation', '=', '0')
																	), 'arrival_day', 'ASC');
			if($booking_data->count()){
				$this->_lastbookingdata = $booking_data->first();
				return true;
			}
		}
		return false;
	}

	public function getBookingDays($customer_id = null, $csid){
		if ($customer_id) {
			$booking_data = $this->_db->get_ordered('Booking', array(array('customer', '=', $customer_id), 
																		array('cancellation', '=', '0'),
																		array('csid', '=', $csid),
																	));
			$booking_days = Date::daysDifference($booking_data->first()->Abreisetag, $booking_data->first()->Anreisetag, false);
			return $booking_days;
		}
		return false;
	}

	public function getBookingWeeks($customer_id = null, $csid){
		if ($customer_id) {
			$booking_data = $this->_db->get_ordered('Booking', array(array('customer', '=', $customer_id), 
																		array('cancellation', '=', '0'),
																		array('csid', '=', $csid),
																	));
			$booking_weeks = Date::weeksDifference($booking_data->first()->Abreisetag, $booking_data->first()->Anreisetag);
			return $booking_weeks;
		}
		return false;
	}

	public function lastBookingData(){
		return $this->_lastbookingdata;
	}
	
	public function data(){
		return $this->_data;
	}
	
}

?>