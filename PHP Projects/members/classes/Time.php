<?php
/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Time {
	
	/**
	 * Get method for Time class.
	 * Outputs the current time.
	 * 
	 * @return string
	 */
	public static function get(){
		return date('H:i:s', time());
	}
	
	/**
	 * Add method for Time class.
	 * Adds certain amount of time to the current time
	 * e.g. if you want to add 30 minutes to the current time
	 * and the current time is 12:00 the event will fire at
	 * 12:30. We mostly use it for the password reset link
	 * that it is valid only for 30 minutes.
	 * 
	 * @param int $minutes
	 * @return string
	 */
	public static function add($minutes){
		return date('H:i:s', time() + ($minutes*60));
	}
}
