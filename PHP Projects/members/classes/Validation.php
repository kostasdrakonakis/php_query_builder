<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Validation{
	private $_passed = false,
			$_errors = array(),
			$_db = null;
	
	/**
	* Constructor method for Validation class 
	* @access public
	*
	**/		
	public function __construct(){
		$this->_db = DB::getInstance();
	}
	
	/**
	* Check method for Validation class 
	* Checks if user submitting a form has passed the validation.
	*
	* @access public
	* @param String $source, whether it is $_POST or $_GET
	* @param Array $items, the array of rules we would like to validate with
	*
	**/
	public function check($source, $items = array()){
		foreach($items as $item => $rules){
			foreach($rules as $rule => $rule_value){
				$value = trim($source[$item]);
				$item = escape($item);
				if($rule === 'required' && empty($value)){
					$this->addError("{$item} is required");
				}elseif(!empty($value)){
					switch($rule){
						case 'min':{
							if(strlen($value) < $rule_value){
								$this->addError("{$item} must be a minimum of {$rule_value} characters. ");
							}
							break;
						}
						case 'max':{
							if(strlen($value) > $rule_value){
								$this->addError("{$item} must be a maximum of {$rule_value} characters. ");
							}
							break;
						}
						case 'matches':{
							if($value != $source[$rule_value]){
								$this->addError("{$rule_value} must match {$item}");
							}
							break;
						}
						case 'unique':{
							$check = $this->_db->get($rule_value, array($item, '=', $value));
							if($check->count()){
								$this->addError("{$item} already exists.");
							}
							break;
						}
					}
				}
			}
		}
		
		if(empty($this->_errors)){
			$this->_passed = true;
		}
		
		return $this;
	}
	

	/**
	* addError method for Validation class 
	* Adds errors to the errors array.
	*
	* @access public
	* @param String $error, The error message that will be
	* displayed to the user if a field is wrong.
	*
	**/
	private function addError($error){
		$this->_errors[] = $error;
	}
	
	/**
	* Errors method for Validation class 
	* Displays the errors that occur if the validation
	* has not been passed.
	* @access public
	*
	**/
	public function errors(){
		return $this->_errors;
	}
	
	/**
	* Passed method for Validation class 
	* informs about whether the validation passed
	* or not.
	*
	* @access public
	* @return returns TRUE if validation passed
	* or FALSE if not.
	*
	**/
	public function passed(){
		return $this->_passed;
	}
}
?>