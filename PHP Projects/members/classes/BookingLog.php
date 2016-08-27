<?php 

/**
 * 
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @license	http://opensource.org/licenses/MIT	MIT License
 * @since	Version 1.0.0
 */
class BookingLog{

	private $_db, $_data = array();
	
	/**
	 * Constructor method for BookingLog class.
	 * If no $bookingid has set then it instanciates the DB
	 * else it returns the data for the specified booking.
	 * @param string $bookingid, the id to get data for the
	 * specified booking
	 */
	function __construct($bookingid = null){
		$this->_db = DB::getInstance();
		if($bookingid){
			$this->find($bookingid);
		}
	}
	
	/**
	 * Find method for BookingLog class
	 * Gets data for the specified booking.
	 * 
	 * @access public
	 * @param string $bookingid, the booking id to search
	 * @return boolean, returns TRUE if data where found
	 * or FALSE if not
	 */
	public function find($bookingid = null){
		if($bookingid){
			$field = 'Booking';
			$data = $this->_db->get_ordered('BookingLog', array(array($field, '=', $bookingid)), 'Date', 'DESC');
			if($data->count()){
				$this->_data = $data->results();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the object's data
	 * @access public
	 * @return array
	 */
	public function data(){
		return $this->_data;
	}
}