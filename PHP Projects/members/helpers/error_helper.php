<?php 
	
	/**
	 * @package	helpers
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */


	/**
	 * Includes the file for the specific error type
	 * @param unknown_type $error_code
	 * Error codes:
	 * 1) 404 Not Found
	 * 2) 401 Unauthorized
	 * 3) 500 Internal Server Error
	 */
	function generate_error($error_code = 404){
		include(server_url() . 'includes/errors/'.$error_code.'.php');
	}

?>