<?php 

	/**
	* @package	functions
	* @author	Konstantinos Drakonakis
	* @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	* @since	Version 1.0.0
	*/
	
	/**
	 * Loads the email template with the specified name
	 * 
	 * @param String $filename, the name of the email template to
	 * load
	 */
	function load_email_template($filename){
		return email_templates_path() . $filename . '.php';
	}

	/**
	 * Loads the customermenu template with the specified name
	 *
	 * @param String $filename, the name of the customermenu template to
	 * load
	 */
	function load_customermenu_template($filename){
		return customermenu_templates_path() . $filename . '.php';
	}

	/**
	 * Loads the iban example with the specified country code
	 *
	 * @param String $country, the country code of the iban example to
	 * load
	 */
	function load_iban_example($country){
		return libraries_path() . Config::get('folder/iban') . '/' . Config::get('folder/iban_examples') . '/' . $country . '-ibans.txt';
	}
