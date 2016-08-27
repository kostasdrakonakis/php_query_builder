<?php
/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Date {
	
	/**
	 * Get method for Date class.
	 * Outputs the current date.
	 *
	 * @return string
	 */
	public static function get(){
		return date('Y-m-d');
	}

	public static function getDateTime(){
		return date('Y-m-d H:i:s');
	}
	
	/**
	 * Add method for Date class.
	 * Adds certain amount of days to the current date
	 *
	 * @param int $days
	 * @return string
	 */
	public static function add($days){
		$date = new DateTime(self::get());
		$date->add(new DateInterval('P'.$days.'D'));
		return $date->format('Y-m-d');
	}
	
	/**
	 * Difference method for Date class.
	 * Gets the days difference between 2 dates
	 *
	 * @param String $date1
	 * @param String $date2
	 * @param boolean $calculateEndDay if TRUE it
	 * will add 1 more day at the current sum.
	 * @return int
	 */
	public static function daysDifference($date1, $date2, $calculateEndDay = true){
		$given_date1 = new DateTime($date1);
		$given_date2 = new DateTime($date2);
		if ($calculateEndDay){
			return ($given_date2->diff($given_date1)->format("%a")+1);
		}else{
			return $given_date2->diff($given_date1)->format("%a");
		}
		
	}

	/**
	 * Difference method for Date class.
	 * Gets the weeks between 2 dates
	 *
	 * @param String $date1
	 * @param String $date2
	 * @return int
	 */
	public static function weeksDifference($date1, $date2, $calculateEndDay = false){
		return ceil(self::daysDifference($date1, $date2, $calculateEndDay)/7);
	}


	/**
	 * Extracts the date part from a datetime format
	 * 
	 * @access public
	 * @param datetime $datetime
	 * @return returns the extracted date format
	 */
	public static function extractDate($datetime){
		$date = new DateTime($datetime);
		return $date->format('Y-m-d');
	}


	
}
