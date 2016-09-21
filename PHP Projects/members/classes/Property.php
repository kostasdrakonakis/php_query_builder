<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Property{
	private $_db, $_data, $_sessionName, $_cookieName, $_isLoggedIn;

	protected $property_table = 'properties';
	protected $property_description_table = 'property_description';
	protected $property_prices_table = 'property_prices';
	protected $property_availability_table = 'property_availability';
	protected $property_stuff_table = 'property_stuff';
	protected $property_images_table = 'property_images';
	protected $property_costs_table = 'property_costs';
	protected $property_feedbacks_table = 'property_feedbacks';
	
	/**
	* Constructor method for Property class 
	* If a Property specified it sets the current session to that Property.
	* Otherwise it finds the Property.
	*
	* @access public
	* @param String $Property, Property to set the session.
	*
	**/
	public function __construct($property = null){
		$this->_db = DB::getInstance();
		if($property){
			$this->find($property);
		}
	}
	

	/**
	* Create method for Property class 
	* It actually creates and inserts a new Property to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createProperty($fields = array()){
		if(!$this->_db->insert(''.$this->getPropertiesTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property');
		}
	}

	/**
	* Create method for Property class 
	* It actually creates and inserts a new Property to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createPropertyDescription($fields = array()){
		if(!$this->_db->insert(''.$property->getPropertiesDescriptionTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property');
		}
	}

	/**
	* Create method for Property class 
	* It actually creates and inserts a new Property to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createPropertyPrices($fields = array()){
		if(!$this->_db->insert(''.$property->getPropertiesPricesTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property');
		}
	}

	/**
	* Create method for Property class 
	* It actually creates and inserts a new Property to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function createPropertyAvailability($fields = array()){
		if(!$this->_db->insert(''.$property->getPropertiesAvailabilityTable().'', $fields)){
			throw new Exception('There was a problemm while creating a Property');
		}
	}
	
	/**
	* Update method for Property class 
	* It actually updates a Property from the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	* @param String $primary_key, The primary field based on the WHERE Clause
	* @param int $id, The id of the Property to be updated
	*
	**/
	public function updatePropertyInfo($fields = array(), $primary_key = null ,$property_id = null){
		if(!$property_id){
			$property_id = $this->data()->property_id;
		}
		if(!$this->_db->update('properties', $property_id, $fields)){
			throw new Exception('There was a problemm while updating a Property');
		}
	}
	

	/**
	* Find method for Property class 
	* It finds a Property and returns the Property's data.
	*
	* @access public
	* @param String $Property, The field to search by.
	* It can either be numeric to search with id or String 
	* to search with email.
	* @return returns TRUE if the Property has been found or FALSE if not.
	*
	**/
	public function find($property = null){
		if($property){
			$field = (is_numeric($property)) ? 'ID' : 'feld1';
			$data = $this->_db->get('Objekte', array(array($field, '=', $property)));
			if($data->count()){
				$this->_data = $data->first();
				return true;
			}
		}
		return false;
	}
	
	
	/**
	* Data method for Property class 
	* It actually contains the Property's data.
	*
	* @access public
	* @return returns the Property's data as an Object.
	*
	**/
	public function data(){
		return $this->_data;
	}

	private function getPropertiesTable(){
		return $this->property_table;
	}

	private function getPropertiesDescriptionTable(){
		return $this->property_description_table;
	}

	private function getPropertiesPricesTable(){
		return $this->property_prices_table;
	}

	private function getPropertiesAvailabilityTable(){
		return $this->property_availability_table;
	}

	private function getPropertiesFeedbackTable(){
		return $this->property_feedbacks_table;
	}

	private function getPropertiesImagesTable(){
		return $this->property_images_table;
	}

	private function getPropertiesStuffTable(){
		return $this->property_stuff_table;
	}

	private function getPropertiesCostsTable(){
		return $this->property_costs_table;
	}

}

?>