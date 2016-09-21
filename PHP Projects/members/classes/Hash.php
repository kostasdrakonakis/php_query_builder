<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Hash{

	/**
	* Make method for Hash class 
	* Generates a hash based on a salt and the SHA256 Algorithm.
	*
	* @access public
	* @param String $string, the string that we want to hash.
	* @param String $salt, the actual salt we want to use.
	* @return returns the hash.
	*
	**/
	public static function make($string, $salt = ''){
		return hash('sha256', $string.$salt);
	}
	
	/**
	* Salt method for Hash class 
	* Generates a salt based on the mcrypt_create_iv() function.
	*
	* @access public
	* @param String $length, the desired length for the salt.
	* @return returns the salt.
	*
	**/
	public static function salt($length){
		return mcrypt_create_iv($length);
	}
	
	/**
	* Unique method for Hash class 
	* Generates a unique hash based on the uniqid() function.
	*
	* @access public
	* @return returns the hash.
	*
	**/
	public static function unique(){
		return self::make(uniqid() );
	}

	/**
	* Generate method for Hash class 
	* Generates a unique hash based on the base64_encode() function.
	* We generate a unique hash based on the user email and the BCRYPT user
	* password.
	* 
	* 
	* @access public
	* @param String $email, the current user email
	* @param String $password, the current user password
	* @return returns the hash.
	*
	**/
	public static function generate($email, $password){
		return base64_encode($email . $password);
	}



}	
?>