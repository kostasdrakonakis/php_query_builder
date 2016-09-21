<?php

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class User{
	private $_db, $_data, $_sessionName, $_cookieName, $_isLoggedIn;
	
	/**
	* Constructor method for User class 
	* If a user specified it sets the current session to that user.
	* Otherwise it finds the user.
	*
	* @access public
	* @param String $user, user to set the session.
	*
	**/
	public function __construct($user = null){
		$this->_db = DB::getInstance();
		$this->_sessionName = Config::get('session/session_name');
		$this->_cookieName = Config::get('remember/cookie_name');
		if(!$user){
			if(Session::exists($this->_sessionName)){
				$user = Session::get($this->_sessionName);
				if($this->find($user)){
					$this->_isLoggedIn = true;
				}
			}
		}else{
			$this->find($user);
		}
	}
	

	/**
	* Create method for User class 
	* It actually creates and inserts a new user to the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	*
	**/
	public function create($table, $fields = array()){
		if(!$this->_db->insert($table, $fields)){
			throw new Exception('There was a problemm while creating a User');
		}
	}
	
	/**
	* Update method for User class 
	* It actually updates a user from the database
	*
	* @access public
	* @param Array $fields, The fields to map in the database
	* @param String $primary_key, The primary field based on the WHERE Clause
	* @param int $id, The id of the user to be updated
	*
	**/
	public function update($fields = array(), $primary_key = null ,$id = null){
		if(!$id && $this->isLoggedIn()){
			$id = $this->data()->id;
		}
		if(!$this->_db->update('users', $id, $fields)){
			throw new Exception('There was a problemm while updating a User');
		}
	}
	

	/**
	* Find method for User class 
	* It finds a user and returns the User's data.
	*
	* @access public
	* @param String $user, The field to search by.
	* It can either be numeric to search with id or String 
	* to search with email.
	* @return returns TRUE if the user has been found or FALSE if not.
	*
	**/
	public function find($user = null){
		if($user){
			$field = (is_numeric($user)) ? 'user_id' : 'email';
			$data = $this->_db->get('users', array($field, '=', $user));
			if($data->count()){
				$this->_data = $data->first();
				return true;
			}
		}
		return false;
	}
	
	/**
	* Login method for User class 
	* It logs the user in and it sets a Session with the user ID.
	*
	* @access public
	* @param String $user, The specified username
	* @param String $password, The specified password
	* @return returns TRUE if user exists and logs in successfully and
	* FALSE if not.
	*
	**/
	public function login($email = null, $password = null, $remember = false){
		$user = $this->find($email);
		if (!$email && !$password && $this->exists()) {
			Session::put($this->_sessionName, $this->data()->user_id);
		} else {
			if($user){
				if(Password::verify($password, $this->data()->password)){
					Session::put($this->_sessionName, $this->data()->email);

					if ($remember) {
						$hash = Hash::unique();
						$hashCheck = $this->_db->get('users_session', array('user_id', '=', $this->data()->user_id));
						if (!$hashCheck->count()) {
							$this->_db->insert('users_session', array(
								'user_id' => $this->data()->user_id,
								'hash' => $hash
							));
						}else{
							$hash = $hashCheck->first()->hash;
						}

						Cookie::put($this->_cookieName, $hash, Config::get('remember/cookie_expiry'));
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	* Data method for User class 
	* It actually contains the user's data.
	*
	* @access public
	* @return returns the User's data as an Object.
	*
	**/
	public function data(){
		return $this->_data;
	}

	/**
	* Exists method for User class 
	* Checks if data is empty or not.
	*
	* @access public
	* @return returns true if data have been set
	* and false if not.
	*
	**/
	public function exists(){
		return (!empty($this->_data)) ? true: false;
	}
	

	/**
	* isLoggedIn method for User class 
	* It actually checks if the user is logged in.
	*
	* @access public
	* @return returns TRUE if user is logged in or FALSE if not.
	*
	**/
	public function isLoggedIn(){
		return $this->_isLoggedIn;
	}
	

	/**
	* Logout method for User class 
	* It logs the user out and unset the session.
	*
	* @access public
	*
	**/
	public function logout(){
		if (Cookie::exists($this->_cookieName)) {
			$this->_db->delete('users_session', array('user_id', '=', $this->data()->user_id));
			Session::delete($this->_sessionName);
			Cookie::delete($this->_cookieName);
		}else{
			Session::delete($this->_sessionName);
		}
		
	}


	/**
	* getUserGroup method for User class 
	* Informs about the current user's group.
	*
	* @access public
	* @param String $user, the user's email
	* @return returns the name of the current
	* user's permission group.
	*
	**/
	public static function getUserGroup($user){

		$employees = DB::getInstance()->get('users', array('email', '=', $user));

		foreach ($employees->results() as $key => $value) {
			$group_id = $value->user_group;
		}
		$group = DB::getInstance()->get('permission_groups', array('group_id', '=', $group_id));
		
		foreach ($group->results() as $key => $value) {
			$user_group = $value->group_name;
		}
		return $user_group;
	}



	/**
	* getUserPermissions method for User class 
	* Informs about the current user's permissions.
	*
	* @access public
	* @param String $user, the user's email
	* @return returns an array with the current
	* user's permissions.
	*
	**/
	public static function getUserPermissions($user){
		$group = User::getUserGroup($user);
		$permissions = DB::getInstance()->get('permission_groups', array('group_name', '=', $group));
		$permission_numbers = array();
		foreach ($permissions->results() as $key => $value) {
			$permission_numbers = explode(',', $value->group_permissions);
		}
		$permission_names = array();
		foreach ($permission_numbers as $key => $value) {
			$permission_names_table = DB::getInstance()->get('permissions', array('permission_number', '=', $value));
			foreach ($permission_names_table->results() as $keynames => $valuenames) {
				$permission_names[''.$valuenames->permission_number.''] = $valuenames->permission_name;
			}
		}
		return $permission_names;
		
	}



	/**
	* hasPermission method for User class 
	* Informs if the current user has permission
	* for an action.
	*
	* @access public
	* @param String $user, the user's email
	* @param int $permission_number, the permission number.
	* View permissions table to specify the permission number.
	* @return returns TRUE if user has permission or
	* FALSE if not.
	*
	**/
	public static function hasPermission($user, $permission_number){
		$group = User::getUserGroup($user);
		$permissions = DB::getInstance()->get('permission_groups', array('group_name', '=', $group));
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

	
}

?>