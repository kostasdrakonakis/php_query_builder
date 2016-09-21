<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Session{

	/**
	* Put method for Session class 
	* Sets the $_SESSION with the specified name and value.
	*
	* @access public
	* @param String $name, the name for the session to set.
	* @param String $value, the value for the session to set.
	* @return returns $_SESSION.
	*
	**/
	public static function put($name, $value){
		return $_SESSION[$name] = $value;
	}
	

	/**
	* Exists method for Session class 
	* Checks if the $_SESSION has been set.
	*
	* @access public
	* @param String $name, the name for the session to check.
	* @return returns TRUE if has been set or FALSE if not.
	*
	**/
	public static function exists($name){
		return (isset($_SESSION[$name])) ? true: false;
	}
	

	/**
	* Delete method for Session class 
	* Deletes the current $_SESSION.
	*
	* @access public
	* @param String $name, the name for the session to delete.
	*
	**/
	public static function delete($name){
		if(self::exists($name)){
			unset($_SESSION[$name]);
		}
	}
	

	/**
	* Get method for Session class 
	* Gets the current $_SESSION with the specified name.
	*
	* @access public
	* @param String $name, the name for the session to get.
	* @return returns $_SESSION.
	*
	**/
	public static function get($name){
		return $_SESSION[$name];
	}
	

	/**
	* Flash method for Session class 
	* Displays a message for the specified Session.
	*
	* @access public
	* @param String $name, the name for the session to flash.
	* @param String $string, the message.
	* @return returns $_SESSION.
	*
	**/
	public static function flash($name, $string = ''){
		if(self::exists($name)){
			$session = self::get($name);
			self::delete($name);
			return $session;
		}else{
			self::put($name, $string);
		}
	}
	
	
}
?>