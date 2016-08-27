<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Owner{
	private $_db, $_data = array();

	protected $owner_table = 'Owner';
	
	/**
	* Constructor method for ownerId class 
	* If a Property specified it sets the current session to that Property.
	* Otherwise it finds the Property.
	*
	* @access public
	* @param String $ownerId, ownerId to set the session.
	*
	**/
	public function __construct($ownerId = null){
		$this->_db = DB::getInstance();
		if($ownerId){
			$this->find($ownerId);
		}
	}

	/**
	* Create method for ownerId class 
	* It actually creates and inserts a new ownerId to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createownerId($fields = array()){
		if(!$this->_db->insert(''.$this->getOwnerTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property description');
		}
	}
	

	/**
	* Find method for ownerId class 
	* It finds a Property and returns the ownerId's data.
	*
	* @access public
	* @param String $Property, The field to search by.
	* It can either be numeric to search with id or String 
	* to search with email.
	* @return returns TRUE if the Property has been found or FALSE if not.
	*
	**/
	public function find($ownerId = null){
		if($ownerId){
			$data = $this->_db->get(''.$this->getOwnerTable().'', array(array('Number', '=', $ownerId)));
			if($data->count()){
				$this->_data = $data->first();
				return true;
			}
		}
		return false;
	}
	
	
	/**
	* Data method for ownerId class 
	* It actually contains the Property's data.
	*
	* @access public
	* @return returns the Property's data as an Object.
	*
	**/
	public function data(){
		return $this->_data;
	}
	
	/**
	 * Getter method for ownerId table
	 * @access private
	 * @return string
	 */
	private function getOwnerTable(){
		return $this->owner_table;
	}

}

?>