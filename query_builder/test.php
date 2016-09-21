<?php 


require 'core/init.php';


$db = DB::getInstance();
$data = $db->select('*')
		   ->from('users')
		   ->where('id', '=', 1)
		   ->fetch();
print_pre($data->results());