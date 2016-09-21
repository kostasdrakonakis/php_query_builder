<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */ 
class Token{

	/**
	* Generate method for Token class 
	* Generates a unique token to protect CSRF.
	*
	* @access public
	* @return returns $_SESSION with the unique token.
	*
	**/
	public static function generate(){
		if (function_exists('mcrypt_create_iv')) {
	        return Session::put(Config::get('session/token_name'), bin2hex(mcrypt_create_iv(32, MCRYPT_DEV_URANDOM)));
	    } else {
	        return Session::put(Config::get('session/token_name'), bin2hex(openssl_random_pseudo_bytes(32)));
	    }
	}
	

	/**
	* Check method for Token class 
	* Checks if the token exists and if it is the same with the Session name for toke.
	* If it is it deletes the session and submits the form.
	* In this case CSRF will fail.
	*
	* @access public
	* @return returns TRUE if the token is correct and FALSE if not.
	*
	**/
	public static function check($token){
		$tokenName = Config::get('session/token_name');
		
		if(Session::exists($tokenName) && $token === Session::get($tokenName)){
			Session::delete($tokenName);
			return true;
		}
		return false;
	}
}
?>