<?php

    class DB {
        private static $_instance = null;
        private $_pdo, $_query, $_results, $_error = false, $_count = 0;
        private $_select = '', $_delete = '', $_join = '', $_from = '', $_where = '', $_or = '', $_and = '', $_in = '', $_not_in = '', $_like = '', $_not_like = '', $_regex = '', $_not_regex = '', $_having = '', $_between = '', $_not_between = '', $_if_null = '', $_null = '', $_not_null = '', $_wherevalues = array(), $_order = '', $_group = '', $_limit = '', $_union = '', $_sql = '', $_operators = array('=', '>', '<', '<=', '>=', '!=');
        private $return_type = 'object', $database_name, $database_host, $database_driver, $database_username, $database_charset, $database_names, $_dns, $database_password;
        private $_charsets = array('latin1', 'latin2', 'utf8', 'utf8mb4', 'ucs2', 'ascii', 'greek', 'cp866', 'cp852', 'latin7', 'utf16', 'swe7', 'utf32', 'binary',);

        private function __construct() {
            $this->database_name = Config::get('mysql/dbname');
            $this->database_host = Config::get('mysql/host');
            $this->database_driver = Config::get('database/driver');
            $this->database_username = Config::get('mysql/username');
            $this->database_password = Config::get('mysql/password');
            $this->database_charset = Config::get('database/charset');
            $this->database_names = Config::get('database/names');
            try {
                $this->_dns = '' . $this->database_driver . ':host=' . $this->database_host . ';dbname=' . $this->database_name . '';
                $this->_pdo = new PDO($this->_dns, $this->database_username, $this->database_password);
                $this->_pdo->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
                $this->_pdo->setAttribute(PDO::MYSQL_ATTR_INIT_COMMAND, "SET NAMES " . $this->database_names . " ");
                $this->_pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $this->_pdo->exec("SET CHARACTER SET '" . $this->database_charset . "'");
            } catch (PDOException $e) {
                die($e->getMessage());
            }
        }

        public static function getInstance() {
            if (!isset(self::$_instance)) {
                self::$_instance = new DB();
            }
            return self::$_instance;
        }

        public function raw($sql, $fetch_type = 'object') {
            $this->_query = $this->_pdo->query($sql);
            if ($fetch_type === 'both') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_BOTH);
                $this->_count = $this->_query->rowCount();
            } elseif ($fetch_type === 'array') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_ASSOC);
                $this->_count = $this->_query->rowCount();
            } elseif ($fetch_type === 'class') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_CLASS);
                $this->_count = $this->_query->rowCount();
            } elseif ($fetch_type === 'object') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
                $this->_count = $this->_query->rowCount();
            } else {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
                $this->_count = $this->_query->rowCount();
            }

            return $this;
        }

        public function join($table, $condition, $type = 'INNER') {
            if ($table != '' && !is_null($table)) {
                if ($condition != '' && !is_null($condition)) {
                    $this->_join .= " {$type} JOIN {$table} ON {$condition}";
                } else {
                    $this->_error = true;
                }
            } else {
                $this->_error = true;
            }
            return $this;
        }

        public function __call($function, $args = array()) {
            if ($function === 'and') {
                $and = $args;
                $field = $and[0];
                $operator = $and[1];
                $value = $and[2];
                if (in_array($operator, $this->_operators)) {
                    $this->_and .= " AND {$field} {$operator} ?";
                    array_push($this->_wherevalues, $value);
                }
                return $this;
            } elseif ($function === 'or') {
                $or = $args;
                $field = $or[0];
                $operator = $or[1];
                $value = $or[2];
                if (in_array($operator, $this->_operators)) {
                    $this->_or .= " OR {$field} {$operator} ?";
                    array_push($this->_wherevalues, $value);
                }
                return $this;
            }
        }

        public function like($column, $value, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_like = " WHERE {$column} LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            } elseif ($or_statement === FALSE) {
                $this->_like .= " AND {$column} LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            } else {
                $this->_like .= " OR {$column} LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function not_like($column, $value, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_not_like = " WHERE {$column} NOT LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            } elseif ($or_statement === FALSE) {
                $this->_not_like .= " AND {$column} NOT LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            } else {
                $this->_not_like .= " OR {$column} NOT LIKE ?";
                $value = '%' . $value . '%';
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function in($column, $in_array, $or_statement = FALSE) {
            $field = $column;
            $in = $in_array;
            if ($this->_where == '') {
                $this->_in = " WHERE {$field} IN({$in})";
            } elseif ($or_statement === FALSE) {
                $this->_in .= " AND {$field} IN({$in})";
            } else {
                $this->_in .= " OR {$field} IN({$in})";
            }
            return $this;
        }

        public function not_in($column, $in_array, $or_statement = FALSE) {
            $field = $column;
            $in = $in_array;
            if ($this->_where == '') {
                $this->_not_in = " WHERE {$field} NOT IN({$in})";
            } elseif ($or_statement === FALSE) {
                $this->_not_in .= " AND {$field} NOT IN({$in})";
            } else {
                $this->_not_in .= " OR {$field} NOT IN({$in})";
            }
            return $this;
        }

        public function regex($column, $value, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_regex = " WHERE {$column} REGEXP ?";
                array_push($this->_wherevalues, $value);
            } elseif ($or_statement === FALSE) {
                $this->_regex .= " AND {$column} REGEXP ?";
                array_push($this->_wherevalues, $value);
            } else {
                $this->_regex .= " OR {$column} REGEXP ?";
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function not_regex($column, $value, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_not_regex = " WHERE {$column} NOT REGEXP ?";
                array_push($this->_wherevalues, $value);
            } elseif ($or_statement === FALSE) {
                $this->_not_regex .= " AND {$column} NOT REGEXP ?";
                array_push($this->_wherevalues, $value);
            } else {
                $this->_not_regex .= " OR {$column} NOT REGEXP ?";
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function null($column, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_null = " WHERE {$column} IS NULL";
            } elseif ($or_statement === FALSE) {
                $this->_null .= " AND {$column} IS NULL";
            } else {
                $this->_null .= " OR {$column} IS NULL";
            }
            return $this;
        }

        public function not_null($column, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_not_null = " WHERE {$column} IS NOT NULL";
            } elseif ($or_statement === FALSE) {
                $this->_not_null .= " AND {$column} IS NOT NULL";
            } else {
                $this->_not_null .= " OR {$column} IS NOT NULL";
            }
            return $this;
        }

        public function if_null($column, $value, $or_statement = FALSE) {
            if ($this->_where == '') {
                $this->_if_null = " WHERE IFNULL(NULL, {$column} = ?)";
                array_push($this->_wherevalues, $value);
            } elseif ($or_statement === FALSE) {
                $this->_if_null .= " AND IFNULL(NULL, {$column} = ?)";
                array_push($this->_wherevalues, $value);
            } else {
                $this->_if_null .= " OR IFNULL(NULL, {$column} = ?)";
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function between($column, $value1, $value2) {
            if ($this->_where == '') {
                $this->_between = " WHERE {$column} BETWEEN ? AND ?";
                array_push($this->_wherevalues, $value1);
                array_push($this->_wherevalues, $value2);
            } else {
                $this->_between .= " AND {$column} BETWEEN ? AND ?";
                array_push($this->_wherevalues, $value1);
                array_push($this->_wherevalues, $value2);
            }
            return $this;
        }

        public function not_between($column, $value1, $value2) {
            if ($this->_where == '') {
                $this->_not_between = " WHERE {$column} NOT BETWEEN ? AND ?";
                array_push($this->_wherevalues, $value1);
                array_push($this->_wherevalues, $value2);
            } else {
                $this->_not_between .= " AND {$column} NOT BETWEEN ? AND ?";
                array_push($this->_wherevalues, $value1);
                array_push($this->_wherevalues, $value2);
            }
            return $this;
        }

        public function having($column, $operator, $value) {
            if (in_array($operator, $this->_operators)) {
                $this->_having .= " HAVING {$column} {$operator} ?";
                array_push($this->_wherevalues, $value);
            }
            return $this;
        }

        public function order($column, $sort = 'ASC') {
            if (isset($column)) {
                if ($column === 'random') {
                    $this->_order = " ORDER BY RAND()";
                } else {
                    $this->_order = " ORDER BY {$column} {$sort}";
                }
            }
            return $this;
        }

        public function group() {
            $group = func_get_args();
            if (count($group) > 1) {
                $x = 0;
            } else {
                $x = 1;
            }
            $values = '';
            foreach ($group as $table) {
                $values .= $table;
                if ($x < count($table)) {
                    $values .= ', ';
                }
                $x++;
            }
            $this->_group = " GROUP BY {$values}";
            return $this;
        }

        public function limit($limit, $offset = 0) {
            if (isset($limit)) {
                $this->_limit = " LIMIT {$offset}, {$limit}";
            }
            return $this;
        }

        public function sql() {
            $sql = $this->_select . $this->_from . $this->_join . $this->_where . $this->_in . $this->_not_in . $this->_like . $this->_not_like . $this->_regex . $this->_not_regex . $this->_having . $this->_between . $this->_not_between . $this->_null . $this->_not_null . $this->_if_null . $this->_and . $this->_or . $this->_order . $this->_group . $this->_limit;
            return $sql;
        }

        public function execute() {
            $sql = $this->_delete . $this->_from . $this->_where . $this->_in . $this->_not_in . $this->_like . $this->_not_like . $this->_regex . $this->_not_regex . $this->_having . $this->_between . $this->_not_between . $this->_null . $this->_not_null . $this->_if_null . $this->_and . $this->_or . $this->_order . $this->_group . $this->_limit;
            // print_pre($sql);
            // print_pre($this->_wherevalues);
            if (!$this->query($sql, $this->_wherevalues, TRUE)->error()) {
                return $this;
            }
            return false;
        }

        public function error() {
            return $this->_error;
        }

        public function query($sql, $params = array(), $inser_update = FALSE) {
            $this->_error = false;
            if ($this->_query = $this->_pdo->prepare($sql)) {
                $x = 1;
                if (count($params)) {
                    foreach ($params as $param) {
                        $this->_query->bindValue($x, $param);
                        $x++;
                    }
                }
                $insert_update = '';
                if ($insert_update) {
                    if (!$this->_query->execute()) {
                        $this->_error = true;
                    }
                } else {
                    if ($this->_query->execute()) {
                        $this->_query->fetchAll($this->getFetchType($this->getReturnType()));
                    } else {
                        $this->_error = true;
                    }
                }
            }
            return $this;
        }

        private function getFetchType($return_type) {
            if ($return_type === 'both') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_BOTH);
                $this->_count = $this->_query->rowCount();
            } elseif ($return_type === 'array') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_ASSOC);
                $this->_count = $this->_query->rowCount();
            } elseif ($return_type === 'class') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_CLASS);
                $this->_count = $this->_query->rowCount();
            } elseif ($return_type === 'object') {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
                $this->_count = $this->_query->rowCount();
            } else {
                $this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
                $this->_count = $this->_query->rowCount();
            }
        }

        public function getReturnType() {
            return $this->return_type;
        }

        public function get($table, $where) {
            return $this->select('*')->from($table)->where($where)->fetch();
        }

        public function fetch() {
            $_sql = $this->_select . $this->_from . $this->_join . $this->_where . $this->_in . $this->_not_in . $this->_like . $this->_not_like . $this->_regex . $this->_not_regex . $this->_having . $this->_between . $this->_not_between . $this->_null . $this->_not_null . $this->_if_null . $this->_and . $this->_or . $this->_order . $this->_group . $this->_limit;
            // print_pre($sql);
            // print_pre($this->_wherevalues);
            if (!$this->query($_sql, $this->_wherevalues)->error()) {
                return $this;
            }
            return false;
        }

        public function where() {
            $where = func_get_args();
            foreach ($where as $key => $val) {
                $field = $where[0];
                $operator = $where[1];
                $value = $where[2];
                if (is_array($val)) {

                    $field = $val[0];
                    $operator = $val[1];
                    $value = $val[2];
                }
            }


            if (in_array($operator, $this->_operators)) {
                $this->_where = " WHERE {$field} {$operator} ?";
                $this->_wherevalues[] = $value;
            }
            return $this;
        }

        public function from() {
            $from = func_get_args();
            if (count($from) > 1) {
                $x = 0;
            } else {
                $x = 1;
            }
            $values = '';
            foreach ($from as $table) {
                $values .= $table;
                if ($x < count($table)) {
                    $values .= ', ';
                }
                $x++;
            }
            $this->_from = "FROM {$values}";
            return $this;
        }

        public function select() {
            $select_action = func_get_args();
            if (count($select_action) > 1) {
                $x = 0;
            } else {
                $x = 1;
            }
            $val = '';
            foreach ($select_action as $field) {
                $val .= $field;
                if ($x < count($field)) {
                    $val .= ', ';
                }
                $x++;
            }

            $this->_select = "SELECT {$val} ";
            return $this;
        }

        public function get_all($table) {
            return $this->select('*')->from($table)->fetch();
        }

        public function count_all($table) {
            return $this->select('COUNT(*) as ' . $table . '_count')->from($table)->fetch();
        }

        public function count_by($table, $where = array()) {
            return $this->select('COUNT(*) as ' . $table . '_count')->from($table)->where($where)->fetch();
        }

        public function delete() {
            $this->_delete = "DELETE ";
            return $this;
        }

        public function union() {
            $queries = func_get_args();
            $x = 1;
            foreach ($queries as $key => $query) {
                if ($x < (count($queries))) {
                    $this->_union .= " UNION " . $queries[$x];
                }
                $x++;
            }
            $this->_union = $queries[0] . $this->_union;
            return $this;
        }

        public function compile() {
            $sql = $this->_union;
            if (!$this->query($sql, $this->_wherevalues)->error()) {
                return $this;
            }
        }

        public function insert($table, $fields = array()) {
            if (count($fields)) {
                $keys = array_keys($fields);
                $values = "";
                $x = 1;
                foreach ($fields as $field) {
                    $values .= '?';
                    if ($x < count($fields)) {
                        $values .= ', ';
                    }
                    $x++;
                }
                $sql = "INSERT INTO $table (`" . implode('`, `', $keys) . "`) VALUES ({$values})";
                if (!$this->query($sql, $fields, TRUE)->error()) {
                    return true;
                }
            }
            return false;
        }

        public function update($table, $primary_key, $id, $fields = array()) {
            $set = "";
            $x = 1;
            foreach ($fields as $name => $value) {
                $set .= "{$name} = ?";
                if ($x < count($fields)) {
                    $set .= ", ";
                }
                $x++;
            }
            $sql = "UPDATE {$table} SET {$set} WHERE {$primary_key} = {$id}";
            if (!$this->query($sql, $fields, TRUE)->error()) {
                return true;
            }
            return false;
        }

        public function count() {
            return $this->_count;;
        }

        public function first() {
            return $this->results()[0];
        }

        public function results() {
            return $this->_results;
        }

        public function return_as($return_type = 'object') {
            $this->return_type = $return_type;
        }

        public function getDatabase() {
            return $this->database_name;
        }

        public function setDatabase($dbname) {
            $this->database_name = $dbname;
        }

        public function getDns() {
            return $this->_dns;
        }

        public function setDns($dns) {
            $this->_dns = $dns;
        }

        public function getDatabaseHost() {
            return $this->database_host;
        }

        public function setDatabaseHost($host) {
            $this->database_host = $host;
        }

        public function getDatabaseDriver() {
            return $this->database_driver;
        }

        public function setDatabaseDriver($driver) {
            $this->database_driver = $driver;
        }

        public function getDatabaseNames() {
            return $this->database_names;
        }

        public function setDatabaseNames($names) {
            if (in_array($names, $this->_charsets)) {
                $this->database_names = $names;
            }
        }

        public function getDatabaseCharset() {
            return $this->database_charset;
        }

        public function setDatabaseCharset($charset) {
            if (in_array($charset, $this->_charsets)) {
                $this->database_charset = $charset;
            }
        }
    }

?>
