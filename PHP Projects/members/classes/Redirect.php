<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Redirect{

	/**
	* To method for Redirect class 
	* Redirects to the specified location
	*
	* @access public
	* @param String|int $location the location to redirect to.
	*
	**/
	public static function to($location = null){
		if($location){
			if(is_numeric($location)){
				switch($location){
					case 404:{
						header(Config::get('headers/404'));
						generate_error('404');
						exit();
						break;
					}
					case 401:{
						header(Config::get('headers/401'));
						generate_error('401');
						exit();
						break;
					}
					case 403:{
						header(Config::get('headers/403'));
						generate_error('403');
						exit();
						break;
					}
					case 500:{
						header(Config::get('headers/500'));
						generate_error('500');
						exit();
						break;
					}
				}
			}
			header('Location: ' . $location);
			exit();
		}
	}
}
?>