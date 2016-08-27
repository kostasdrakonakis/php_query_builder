<?php 

class Help_Model extends Model{
	
	function __construct(){
		parent::__construct();
		echo "Help Model class instanciated<br/>";
	}

	public function meth(){
		echo "We are inside help model<br/>";
	}

	public function another(){
		echo "We are inside help model another method<br/>";
	}

}