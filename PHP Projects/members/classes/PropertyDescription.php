<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class PropertyDescription{
	private $_db, $_data = array();

	protected $property_description_table = 'PropertyDescription';
	
	/**
	* Constructor method for PropertyDescription class 
	* If a Property specified it sets the current session to that Property.
	* Otherwise it finds the Property.
	*
	* @access public
	* @param String $propertyDescription, PropertyDescription to set the session.
	*
	**/
	public function __construct($propertyDescription = null, $languageid){
		$this->_db = DB::getInstance();
		if($propertyDescription){
			$this->find($propertyDescription, $languageid);
		}
	}

	/**
	* Create method for PropertyDescription class 
	* It actually creates and inserts a new PropertyDescription to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createPropertyDescription($fields = array()){
		if(!$this->_db->insert(''.$this->getPropertiesDescriptionTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property description');
		}
	}
	

	/**
	* Find method for PropertyDescription class 
	* It finds a Property and returns the PropertyDescription's data.
	*
	* @access public
	* @param String $Property, The field to search by.
	* It can either be numeric to search with id or String 
	* to search with email.
	* @return returns TRUE if the Property has been found or FALSE if not.
	*
	**/
	public function find($propertyDescription = null, $languageid){
		if($propertyDescription){
			$field = 'ID';
			$data = $this->_db->get(''.$this->getPropertiesDescriptionTable().'', array(array($field, '=', $propertyDescription), array('language_id', '=', $languageid)));
			if($data->count()){
				$this->_data = $data->first();
				return true;
			}
		}
		return false;
	}
	
	
	/**
	* Data method for PropertyDescription class 
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
	 * Getter method for propertyDescription table
	 * @access private
	 * @return string
	 */
	private function getPropertiesDescriptionTable(){
		return $this->property_description_table;
	}

}

?>