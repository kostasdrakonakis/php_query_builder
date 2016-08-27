<?php

if (!defined('BASEPATH'))exit('No direct script access allowed');

class MY_articles extends CI_Controller {

public function show_all_news() {
	$this->db->select('*');
	$this->db->from('article');
	$this->db->order_by('curr_time','desc');
	$this->db->limit(10);
	$query = $this->db->get();
	if ($query->num_rows() > 0) {
	return $query->result();
	} else {
	return false;
	}

}

    function get_article($article_id)
	{
		$this->db->select()->from('article')->where(array('id'=>$article_id))->order_by('curr_time', 'desc');
		$query = $this->db->get();
		return $query->first_row('array');
	}

	function get_user_article($user_name)
	{
		$this->db->select()->from('article')->where(array('user_name'=>$user_name));
		$this->db->order_by('curr_time','desc');
		$this->db->limit(10);
		$query = $this->db->get();
		if ($query->num_rows() > 0) {
		  return $query->result();
	}
	else {
		return false;
	}

	}

	function delete_user_article($post_id)
	{
		$this->db->where('id', $post_id);
		$this->db->delete('article');
	}

	function create_article($data)
	{
		$this->db->insert('article', $data);
		
	}

}
