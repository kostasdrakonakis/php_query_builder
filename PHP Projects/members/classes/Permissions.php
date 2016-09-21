<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Permissions{
	
	/**
	* getPermissions method for Permissions class 
	* Gets all the permissions.
	*
	* @access public
	* @return returns an array with the permissions.
	*
	**/
	public static function getPermissions(){
		$permissions = DB::getInstance()->get_all('permissions');
		$permission_names = array();
		foreach ($permissions->results() as $key => $value) {
			$permission_names[''.$value->permission_number.''] = str_replace('_', ' ', $value->permission_name);
		}

		return $permission_names;
	}

	/**
	* getGroupNames method for Permissions class 
	* Gets all the group names.
	*
	* @access public
	* @return returns an array with the group names.
	*
	**/
	public static function getGroupNames(){
		$permissions = DB::getInstance()->get_all('permission_groups');
		$permission_names = array();
		foreach ($permissions->results() as $key => $value) {
			$permission_names[] = $value->group_name;
		}

		return $permission_names;
	}


	/**
	* hasPermission method for Permissions class 
	* Checks if current group name has the specified permission.
	*
	* @access public
	* @param String $group_name the group name to check
	* @param int $permission_number the permission number to check
	* @return returns TRUE if group name has permission or FALSE if not.
	*
	**/
	public static function hasPermission($group_name, $permission_number){
		$permissions = DB::getInstance()->get('permission_groups', array('group_name', '=', $group_name));
		$permission_numbers = array();
		foreach ($permissions->results() as $key => $value) {
			$permission_numbers = explode(',', $value->group_permissions);
		}
		if (in_array($permission_number, $permission_numbers)) {
			return true;
		} else {
			return false;
		}
		
	}


	/**
	* Update method for Permissions class 
	* Updates the groups permissions.
	*
	* @access public
	* @param Array $fields the fields to update
	* @param int $id, the group name id.
	* @param String $primary_key the field of the permission_groups table to update
	* @return returns TRUE if permissions updated or FALSE if not.
	*
	**/
	public static function update($fields = array(), $id = null, $primary_key = null){
		if(!DB::getInstance()->update('permission_groups', $primary_key, $id, $fields)){
			throw new Exception('There was a problemm while updating Permissions');
		}
	}
}