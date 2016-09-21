<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Config{

	/**
	* Method to get data from the configuration file
	* located core/init.php
	* @access public
	* @param String $path. The path is considered to be used
	* as such. Config::get('mysql/host') and it will give
	* localhost
	* @return String $config is the actual data we got from
	* config file.
	*
	**/
	public static function get($path=null){
		if($path){
			$config = $GLOBALS['config'];
			$path = explode('/', $path);
			foreach($path as $bit){
				if(isset($config[$bit])){
					$config = $config[$bit];
				}
			}
			return $config;
		}
		return false;
	}
}

?>