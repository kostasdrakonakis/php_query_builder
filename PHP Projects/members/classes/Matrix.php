<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Matrix{
	

	/**
	* Unify method for Matrix Class
	* @access public
	* @param Array left the permissions checked as YES
	* from the left column of the permission screen.
	* @param Array center the permissions checked as YES
	* from the center column of the permission screen.
	* @param Array right the permissions checked as YES
	* from the right column of the permission screen.
	* 
	* @return returns the permissions string with all permissions
	* checked YES for each permission group.
	*/
	public static function unify($left = array(), $center = array(), $right = array()){
		$array_left = implode(',', $left);
		$array_center = implode(',', $center);
		$array_right = implode(',', $right);
		$main = $array_left . "," . $array_center . "," . $array_right;
		return $main;
	}


	/**
	* Split method for Matrix Class
	* Splits the permissions for each group
	* into 3 columns.
	* @access public
	* @param Array stack the permissions array
	* @param int columns how many columns to split.
	* @param Array right the permissions checked as YES
	* @return returns the new Array splitted.
	*
	*/
	public static function split($stack = array(), $columns){
		$offset = ceil(count($stack)/$columns);
		$stack = array_chunk($stack, $offset, true);
		return $stack;
	}
}