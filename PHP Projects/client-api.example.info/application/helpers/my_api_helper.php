<?php defined('BASEPATH') OR exit('No direct script access allowed');

function remove_unknown_fields($form_fields, $expected_fields){
	$new_data = array();
	foreach ($form_fields as $key => $value) {
		if ($value != "" && in_array($key, array_values($expected_fields))) {
			$new_data[$key] = $value; 
		}		
	}
	return $new_data;
}

?>