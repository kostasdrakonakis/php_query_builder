<?php defined('BASEPATH') OR exit('No direct script access allowed');


class Model_spirits extends MY_Model
{
	
	protected $_table = 'spirits';
	protected $primary_key = 'id';
	protected $return_type = 'array';
	
	public $after_get = array('remove_sensitive_data');
	
	public function remove_sensitive_data($spirits){
		unset($spirits['product1']);
		unset($spirits['product2']);
		unset($spirits['product3']);
		unset($spirits['product4']);
		unset($spirits['product5']);
		unset($spirits['product6']);
		unset($spirits['product7']);
		unset($spirits['product8']);
		unset($spirits['product9']);
		unset($spirits['product10']);
		unset($spirits['product11']);
		unset($spirits['product12']);
		unset($spirits['product13']);
		unset($spirits['product14']);
		unset($spirits['product15']);
		unset($spirits['product16']);
		unset($spirits['product17']);
		unset($spirits['product18']);
		unset($spirits['product19']);
		unset($spirits['product20']);
		unset($spirits['product21']);
		unset($spirits['product22']);
		unset($spirits['product23']);
		unset($spirits['product24']);
		unset($spirits['product25']);
		return $spirits;
	}
}