<?php
	

	/**
	 * @package	classes
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */
	class PaymentsDB{
		private static $_instance = null;
		private $_pdo,
				$_query,
				$_results,
				$_error = false,
				$_count = 0;
		
		/**
		* Constructor for the DB class
		*
		* @access private
		* 
		* The private access is because we have
		* used the Singleton pattern so we dont want
		* the user to initialize the connection
		* every time.
		*
		**/
		private function __construct(){
			try{
				$dns = ''.Config::get('database/driver').':host='.Config::get('paymentsdb/host').';dbname='.Config::get('paymentsdb/dbname').'';
				$username = Config::get('paymentsdb/username');
				$password = Config::get('paymentsdb/password');
				$this->_pdo = new PDO($dns, $username, $password);
				$this->_pdo->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
				$this->_pdo->setAttribute(PDO::MYSQL_ATTR_INIT_COMMAND, "SET NAMES ".Config::get('database/names')." ");
				$this->_pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				$this->_pdo->exec("SET CHARACTER SET '".Config::get('database/charset')."'");
			}catch(PDOException $e){
				die($e->getMessage());
			}
		}
		
		/**
		* Method for getting the Instance of DB
		*
		* @access public
		* @return returns the Instance
		*
		* The method checks if we already have connected
		* and returns Instance. We access it like 
		* DB::getInstance()->method so we do avoid
		* multiple connections to the database
		*
		**/
		public static function getInstance(){
			if(!isset(self::$_instance)){
				self::$_instance = new PaymentsDB();
			}
			return self::$_instance;
		}
		
		
		/**
		* Query method for database
		*
		* @access public
		* @param String $sql, the actual SQL Query
		* @param Array $params the parameters for query
		* e.g. DB::getInstance()->query("SELECT * FROM table WHERE user = ? OR user = ? " , array('userName', 'secondUserName'))
		* @return Object $this->_results, the actual data
		* found in the database or error if something
		* was wrong.
		*
		**/
		public function query($sql, $params = array()){
			$this->_error = false;
			// print_pre($params);
			if($this->_query = $this->_pdo->prepare($sql)){
				$x = 1;
				if(count($params)){
					foreach($params as $param){
						$this->_query->bindValue($x, $param);
						$x++;
					}
				}
				if($this->_query->execute()){
					$this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
					$this->_count = $this->_query->rowCount();
				}else{
					$this->_error = true;
				}
			}
			return $this;
		}

		/**
		* Query method for database
		*
		* @access public
		* @param String $sql, the actual SQL Query
		* @param Array $params the parameters for query
		* e.g. DB::getInstance()->query("SELECT * FROM table WHERE user = ? OR user = ? " , array('userName', 'secondUserName'))
		* 
		* Same functionality with the above method
		* but when we insert or update there is nothing
		* to return so in order to avoid the 
		* SQLSTATE[HY000]: General error
		*
		**/
		public function queryInsert($sql, $params = array()){
			$this->_error = false;
			if ($this->_query = $this->_pdo->prepare($sql)) {
				$x = 1;
				if (count($params)) {
					foreach ($params as $param) {
						$this->_query->bindValue($x, $param);
						$x++;
					}
				}
				if (!$this->_query->execute()) {
					$this->_error = true;
				}
			}
			return $this;
		}


		/**
		* Query method for database
		*
		* @access public
		* @param String $sql, the actual SQL Query
		* @param Array $params the parameters for query
		* e.g. DB::getInstance()->query("SELECT * FROM table WHERE user = ? OR user = ? " , array('userName', 'secondUserName'))
		* 
		* Same functionality with the above method
		* but when we insert or update there is nothing
		* to return so in order to avoid the 
		* SQLSTATE[HY000]: General error
		*
		**/
		public function queryUpdate($sql, $params = array()){
			$this->_error = false;
			if ($this->_query = $this->_pdo->prepare($sql)) {
				$x = 1;
				if (count($params)) {
					foreach ($params as $param) {
						$this->_query->bindValue($x, $param);
						$x++;
					}
				}
				if (!$this->_query->execute()) {
					$this->_error = true;
				}
			}
			return $this;
		}
		
		/**
		* Method to resolve the errors
		*
		* @access public
		* @return returns TRUE if no Error
		* else returns FALSE
		*
		*
		**/
		public function error(){
			return $this->_error;
		}
		

		/**
		* Action method for database
		*
		* @access private
		* @param String $action, The action we would like to provide
		* e.g. "SELECT *", "DELETE"
		* @param String $table the tab;le we would like to perform
		* the action to.
		* @param Array $where, The actual where clause.
		* e.g. action("SELECT *", "users", array('username', '=', 'Alex'));
		* @return returns the object if TRUE else return FALSE
		*
		**/
		private function action($action, $table, $where = array(), $order = null, $sort = null){
			$ordersql = '';
			$append = '';
			if(is_array($where)){
				$master = $where;
				$operators = array('=', '>', '<', '<=', '>=');
				$temp = array_shift($where);
				if (!empty($where)) {
					foreach ($where as $key => $value) {
						$field = $where[$key][0];
						$operator = $where[$key][1];
						$v = $where[$key][2];
						if (!empty($field) && !empty($operator)) {
							$append .= " AND {$field} {$operator} ?";
						}
					}

					if(in_array($operator, $operators)){
						if (!is_null($order) && !is_null($sort)) {
							$ordersql = " ORDER BY {$order} {$sort}";
						}

						if ($ordersql != '') {
							$sql = "{$action} FROM {$table} WHERE {$temp[0]} {$temp[1]} ?" . $append . $ordersql;
						} else {
							$sql = "{$action} FROM {$table} WHERE {$temp[0]} {$temp[1]} ?" . $append;
						}
						
						foreach ($master as $key => $value) {
							$val[] = $master[$key][2];
						}
						
						if(!$this->query($sql, $val)->error()){
							return $this;
						}
					}
				} else {
					if(in_array($temp[1], $operators)){
						if (!is_null($order)) {
							$sql = "{$action} FROM {$table} WHERE {$temp[0]} {$temp[1]} ? ORDER BY {$order} {$sort}";
						} else {
							$sql = "{$action} FROM {$table} WHERE {$temp[0]} {$temp[1]} ?";
						}
						
						foreach ($master as $key => $value) {
							$val[] = $master[$key][2];
						}
						
						if(!$this->query($sql, $val)->error()){
							return $this;
						}
					}
				}
				
				
			}
			return false;
		}


		private function action_join($action, $tables = array(), $where = array(), $type, $order = null, $sort = null){
			$ordersql = '';
			$append = '';
			if (is_array($tables)) {
				$first_table = array_splice($tables, 0, 1);
				$table_from = array_keys($first_table);
				$table_from_value = array_values($first_table);
				foreach ($tables as $key => $value) {
					$join = " {$type} JOIN {$key} ON {$table_from[0]}.{$table_from_value[0]} = {$key}.{$value}";
				}

				if(is_array($where)){
					$master = $where;
					$operators = array('=', '>', '<', '<=', '>=');
					$temp = array_shift($where);
					if (!empty($where)) {
						foreach ($where as $key => $value) {
							$field = $where[$key][0];
							$operator = $where[$key][1];
							$v = $where[$key][2];
							if (!empty($field) && !empty($operator)) {
								$append .= " AND {$field} {$operator} ?";
							}
						}

						if(in_array($operator, $operators)){
							if (!is_null($order) && !is_null($sort)) {
								$ordersql = " ORDER BY {$order} {$sort}";
							}

							if ($ordersql != '') {
								$where = " WHERE {$temp[0]} {$temp[1]} ?" . $append . $ordersql;
							} else {
								$where = " WHERE {$temp[0]} {$temp[1]} ?" . $append;
							}
							$sql = "{$action} FROM {$table_from[0]} " . $join . $where;
							
							foreach ($master as $key => $value) {
								$val[] = $master[$key][2];
							}
							
							if(!$this->query($sql, $val)->error()){
								return $this;
							}
						}
					} else {
						if(in_array($temp[1], $operators)){
							if (!is_null($order)) {
								$where = " WHERE {$temp[0]} {$temp[1]} ? ORDER BY {$order} {$sort}";
							} else {
								$where = " WHERE {$temp[0]} {$temp[1]} ?";
							}
							$sql = "{$action} FROM {$table_from[0]} " . $join . $where;
							foreach ($master as $key => $value) {
								$val[] = $master[$key][2];
							}
							
							if(!$this->query($sql, $val)->error()){
								return $this;
							}
						}
					}
					
					
				}
				
			}
			
			return false;
		}

		/**
		* Action method for database
		*
		* @access private
		* @param String $action, The action we would like to provide
		* e.g. "SELECT *", "DELETE"
		* @param String $table the tab;le we would like to perform
		* the action to.
		* @param Array $where, The actual where clause.
		* e.g. action("SELECT *", "users", array('username', '=', 'Alex'));
		* @return returns the object if TRUE else return FALSE
		*
		**/
		private function actionDelete($action, $table, $where = array()){
			if(count($where) === 3){
				$operators = array('=', '>', '<', '<=', '>=');
				$field = $where[0];
				$operator = $where[1];
				$value = $where[2];
				if(in_array($operator, $operators)){
					$sql = "{$action} FROM {$table} WHERE {$field} {$operator} ?";
					if(!$this->queryUpdate($sql, array($value))->error()){
						return true;
					}
				}
			}else{
				$sql = "{$action} FROM {$table}";
					if(!$this->queryUpdate($sql)->error()){
						return true;
				}
			}
			return false;
		}

		
		/**
		* Get method for database
		*
		* @access public
		* @param String $table the table we would like to perform
		* the get from.
		* @param Array $where, The actual where clause.
		* e.g. DB::getInstance()->get("users", array('username', '=', 'Alex'));
		* @return returns the object from database or FALSE on error
		*
		**/
		public function get($table, $where){
			return $this->action('SELECT *', $table, $where);
		}

		/**
		* Get Many method for database
		* Returns an object with multiple
		* results based on multiple WHERE clause.
		*
		* @access public
		* @param String $table the table we would like to perform
		* the get from.
		* @param Array $where, The actual where clause.
		* e.g. DB::getInstance()->get("users", array('username', '=', 'Alex'));
		* @return returns the object from database or FALSE on error
		*
		**/
		public function get_ordered($table, $where=array(), $order = null, $sort = null){
			return $this->action('SELECT *', $table, $where, $order, $sort);
		}

		/**
		* Get all method for database. Returns all the results 
		* without WHERE clause
		*
		* @access public
		* @param String $table the table we would like to perform
		* the get_all from.
		* e.g. DB::getInstance()->get_all("users");
		* @return returns the object from database
		* or FALSE on error
		*
		**/
		public function get_all($table){
			return $this->action('SELECT *', $table);
		}


		public function join($fields = array(), $tables = array(), $where=array(), $type = 'INNER',$order = null, $sort = null){
			$fieldset = '';
			if (is_array($fields)) {
				$x = 1;
				foreach($fields as $field){
					$fieldset .= $field;
					if($x < count($fields)){
						$fieldset .= ', ';
					}
					$x++;
				}
				return $this->action_join('SELECT '.$fieldset.'', $tables, $where, $type, $order, $sort);
			}elseif(count($fields) <= 0 ){
				return $this->action_join('SELECT *', $tables, $where, $type, $order, $sort);
			}

			return false;
			
		}
		

		/**
		* Delete method for database. Deletes records
		* from the specified table based on the where
		* clause. 
		*
		* @access public
		* @param String $table the table we would like to perform
		* the delete from.
		* @param Array $where, The actual where clause.
		* e.g. DB::getInstance()->delete("users", array('username', '=', 'Alex'));
		* @return returns the object from database
		* or FALSE on error
		*
		**/
		public function delete($table, $where){
			return $this->actionDelete('DELETE', $table, $where);
		}


		/**
		* Insert method for database. Insert records
		* to the specified table based on the fields
		* array. 
		*
		* @access public
		* @param String $table the table we would like to perform
		* the insert to.
		* @param Array $fields, The actual fields to map.
		* e.g. DB::getInstance()->insert("users", array(
		* 										'username' => 'Alex',
		*										'password' => md5($password),
		*										'date_created' => NOW()
		*										));
		* @return returns TRUE on success
		* or FALSE on error
		*
		**/
		public function insert($table, $fields = array()){
			if(count($fields)){
				$keys = array_keys($fields);
				$values = "";
				$x = 1;
				foreach($fields as $field){
					$values .= '?';
					if($x < count($fields)){
						$values .= ', ';
					}
					$x++;
				}
				$sql = "INSERT INTO $table (`" . implode('`, `', $keys) . "`) VALUES ({$values})";
				if(!$this->queryInsert($sql, $fields)->error()){
					return true;
				}
			}
			return false;
		}
		

		/**
		* Update method for database. Update records
		* of the specified table based on the fields
		* array. 
		*
		* @access public
		* @param String $table the table we would like to perform
		* the insert to.
		* @param String $primary_key, The field we are updating a record 
		* based on the where clause.
		* @param String $id, The value of the primary_key field.
		* @param Array $fields, The actual fields to map.
		* e.g. DB::getInstance()->update("users", 'user_id', 3, array(
		* 										'username' => 'Alex',
		*										'password' => 'newpassword'
		*										));
		* The above query is the same as:
		* "UPDATE users SET username = Alex, password='newpassword' WHERE user_id = 3"
		* @return returns TRUE on success
		* or FALSE on error
		*
		**/
		public function update($table, $primary_key, $id, $fields = array()){
			$set = "";
			$x = 1;
			foreach($fields as $name => $value){
				$set .= "{$name} = ?";
				if($x < count($fields)){
					$set .= ", ";
				}
				$x++;
			}

			$sql = "UPDATE {$table} SET {$set} WHERE {$primary_key} = {$id}";
			//print_pre($sql);
			if(!$this->queryUpdate($sql, $fields)->error()){
				return true;
			}
			return false;
		}
		

		/**
		* Count method for database. Informs the 
		* user for the number records found.
		*
		* @access public
		* @return returns the number of records found
		*
		*
		**/
		public function count(){
			return $this->_count;;
		}
		

		/**
		* Results method for database. The actual object from 
		* get and get_all methods.
		*
		* @access public
		* @return returns the object
		* of the database records. We access it like this
		* 
		* foreach($query_variable->results() as $key => $value){
		*	$username = $value->username;
		* }
		*
		**/
		public function results(){
			return $this->_results;
		}
		
		/**
		* First method for database. Informs the 
		* user for the first record found.
		*
		* @access public
		* @return returns the first object found from
		* the database.
		*
		**/
		public function first(){
			return $this->results()[0];
		}
	}
?>