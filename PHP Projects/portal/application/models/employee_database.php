<?php

if (!defined('BASEPATH'))exit('No direct script access allowed');

class Employee_Database extends CI_Controller {

public function show_all_data() {
	$this->db->select('*');
	$this->db->from('employee_info');
	$query = $this->db->get();
	if ($query->num_rows() > 0) {
	return $query->result();
	} else {
	return false;
	}
}

public function show_data_by_id($id) {
	$condition = "emp_id =" . "'" . $id . "'";
	$this->db->select('*');
	$this->db->from('employee_info');
	$this->db->where($condition);
	$this->db->limit(1);
	$query = $this->db->get();

	if ($query->num_rows() == 1) {
	return $query->result();
	} else {
	return false;
	}
}

public function show_data_by_date($date) {
	$condition = "emp_date_of_join =" . "'" . $date . "'";
	$this->db->select('*');
	$this->db->from('employee_info');
	$this->db->where($condition);
	$query = $this->db->get();
	if ($query->num_rows() > 0) {
	return $query->result();
	} else {
	return false;
	}
}

public function show_data_by_date_range($data) {
	$condition = "emp_date_of_join BETWEEN " . "'" . $data['date1'] . "'" . " AND " . "'" . $data['date2'] . "'";
	$this->db->select('*');
	$this->db->from('employee_info');
	$this->db->where($condition);
	$query = $this->db->get();
	if ($query->num_rows() > 0) {
	return $query->result();
	} else {
	return false;
	}
}

}