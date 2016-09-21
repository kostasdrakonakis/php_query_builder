	<?php 
	/**
	 * @package	helpers
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */
	
	/**
	 * Returns the file located in rootfolder/templates/email/filename.php
	 * @param String $filename, the name of the email template
	 * we would like to send 
	 * @return the filename
	 */
	function email_templates_path(){
		return server_url() . Config::get('folder/templates') .'/'. Config::get('folder/email') .'/';
	}

	/**
	 * Returns the file located in rootfolder/templates/customermenu/filename.php
	 * @param String $filename, the name of the email template
	 * we would like to send 
	 * @return the filename
	 */
	function customermenu_templates_path(){
		return server_url() . Config::get('folder/templates') .'/'. Config::get('folder/customermenu') .'/';
	}
	
	/**
	 * Returns the path untill rootfolder/img/
	 */
	function image_path(){
		return base_url() . Config::get('folder/img') .'/';
	}
	
	/**
	 * Returns the path untill rootfolder/js/
	 */
	function js_path(){
		return base_url() . Config::get('folder/js') .'/';
	}

	/**
	 * Returns the path untill rootfolder/css/
	 */
	function css_path(){
		return base_url() . Config::get('folder/css') .'/';
	}
	
	/**
	 * Returns the path untill rootfolder/logs/
	 */
	function log_path(){
		return server_url() . Config::get('folder/logs') .'/';
	}

	/**
	 * Returns the path untill rootfolder/libraries/
	 */
	function libraries_path(){
		return server_url() . Config::get('folder/libraries') .'/';
	}

	/**
	 * Returns the path untill rootfolder/logs/
	 */
	function views_path(){
		return server_url() . Config::get('folder/views') .'/';
	}

	/**
	 * Returns the path untill rootfolder/extensions/
	 */
	function extensions_path(){
		return base_url() . Config::get('folder/extensions') .'/';
	}

	/**
	 * Returns the path untill rootfolder/extensions/pdf/
	 */
	function pdf_path(){
		return extensions_path() . Config::get('folder/pdf') .'/';
	}

	/**
	 * Returns the path untill rootfolder/extensions/travelDescriptions/
	 */
	function travel_descriptions_path(){
		return extensions_path() . Config::get('folder/travelDescriptions') .'/';
	}
	
	

	?>