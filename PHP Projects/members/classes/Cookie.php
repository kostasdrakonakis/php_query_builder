<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Cookie{

	/**
	* Exists method for Cookie class 
	* Checks if the actual cookie exists.
	*
	* @access public
	* @param String $name, the cookie name to check.
	* @return returns TRUE if the cookie has been
	* set and exists or FALSE if not.
	*
	**/
	public static function exists($name){
		return (isset($_COOKIE[$name])) ? true: false;
	}
	

	/**
	* Get method for Cookie class 
	* Gets the cookie with the given cookie name.
	*
	* @access public
	* @param String $name, the cookie name to get.
	* @return returns the cookie name
	* 
	*
	**/
	public static function get($name){
		return $_COOKIE[$name];
	}
	

	/**
	* Put method for Cookie class 
	* Sets the cookie with the given name, value, expiry
	*
	* @access public
	* @param String $name, the cookie name to set.
	* @param String $value, the value of the cookie.
	* @param Integer $expiry, the time that the cookie lasts.
	* @return returns TRUE if the cookie has been
	* set or FALSE on error.
	*
	**/
	public static function put($name, $value, $expiry){
		if(setcookie($name, $value, time() + $expiry, '/')){
			return true;
		}
		return false;
	}
	

	/**
	* Delete method for Cookie class 
	* Deletes the actual cookie.
	*
	* @access public
	* @param String $name, the cookie name to delete.
	* @return returns TRUE if the cookie has been
	* deleted or FALSE if not.
	*
	**/
	public static function delete($name){
		self::put($name, '', time() - 1);
	}
}
?>