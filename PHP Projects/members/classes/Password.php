<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Password{
	
	/**
	* Encrypt method for Password class 
	* Generates a password based on the BCRYPT Algo.
	*
	* @access public
	* @return returns the encrypted password.
	*
	**/
	public static function encrypt($string, $cost = array()){
		return password_hash($string, PASSWORD_BCRYPT, $cost);
	}

	/**
	* Verify method for Password class 
	* Verifys the password.
	*
	* @access public
	* @return returns TRUE if matches or FALSE if not.
	*
	**/
	public static function verify($password, $input){
		return password_verify($password, $input);
	}
}