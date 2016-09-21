<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class SwiftBic{
	
	/**
	 * Validates a given BIC/Swift Bank code through a regex
	 * @access public
	 * @param string $swiftbic, the Swift/BIC to validate
	 * @return boolean TRUE if is valid or FALSE if invalid
	 */
	public static function validate($swiftbic){
		$regexp = '/^([a-zA-Z]){4}([a-zA-Z]){2}([0-9a-zA-Z]){2}([0-9a-zA-Z]{3})?$/';
        return (boolean) preg_match($regexp, $swiftbic);
	}
}