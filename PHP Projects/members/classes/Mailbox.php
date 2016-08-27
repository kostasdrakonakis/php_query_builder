<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Mailbox{

	private $_db, $_data = array(), $_datafirst = array();
	
	function __construct($mailid = null){
		$this->_db = DB::getInstance();
		if($mailid){
			$this->find($mailid);
		}
	}

	public function find($mailid = null){
		if($mailid){
			$field = 'id';
			$data = $this->_db->get_ordered('b1gmail_outbox', array(array($field, '=', $mailid)));
			if($data->count()){
				$this->_data = $data->results();
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