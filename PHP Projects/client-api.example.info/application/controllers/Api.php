<?php 

defined('BASEPATH') OR exit ('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

class Api extends REST_Controller{
 public function __construct() {
  parent::__construct();
  $this->load->helper('my_api');
 }

 public function coffees_get() {
	$coffees_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_coffees');
	$coffees = $this->Model_coffees->get_all();//->order_by(array('name'));
	if(isset($coffees_id)){
		if(is_numeric($coffees_id)){
			$coffees = $this->Model_coffees->get_by(array('id' => $coffees_id));
			if ($coffees) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$coffees));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($coffees_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$coffees = $this->Model_coffees->limit($segmentFour)->get_all();
			if ($coffees) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$coffees));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($coffees_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$coffees = $this->Model_coffees->order_by($segmentFour, $segmentFive)->get_all();
			if ($coffees) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$coffees));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$coffees = $this->Model_coffees->get_by(array('name' => $coffees_id));
			if ($coffees) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$coffees));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $coffees));
	}
  
 }
 

 public function snacks_get() {
	$snacks_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_snacks');
	$snacks = $this->Model_snacks->get_all();
	if(isset($snacks_id)){
		if(is_numeric($snacks_id)){
			$snacks = $this->Model_snacks->get_by(array('id' => $snacks_id));
			if ($snacks) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$snacks));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($snacks_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$snacks = $this->Model_snacks->limit($segmentFour)->get_all();
			if ($snacks) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$snacks));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($snacks_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$snacks = $this->Model_snacks->order_by($segmentFour, $segmentFive)->get_all();
			if ($snacks) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$snacks));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$snacks = $this->Model_snacks->get_by(array('name' => $snacks_id));
			if ($snacks) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$snacks));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $snacks));
	}
  
 }

 public function sweets_get() {
	$sweets_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_sweets');
	$sweets = $this->Model_sweets->get_all();
	if(isset($sweets_id)){
		if(is_numeric($sweets_id)){
			$sweets = $this->Model_sweets->get_by(array('id' => $sweets_id));
			if ($sweets) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$sweets));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($sweets_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$sweets = $this->Model_sweets->limit($segmentFour)->get_all();
			if ($sweets) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$sweets));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($sweets_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$sweets = $this->Model_sweets->order_by($segmentFour, $segmentFive)->get_all();
			if ($sweets) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$sweets));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$sweets = $this->Model_sweets->get_by(array('name' => $sweets_id));
			if ($sweets) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$sweets));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $sweets));
	}
  
 }

 public function spirits_get() {
	$spirits_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_spirits');
	$spirits = $this->Model_spirits->get_all();
	if(isset($spirits_id)){
		if (is_numeric($spirits_id)) {
			$spirits = $this->Model_spirits->get_by(array('id' => $spirits_id));
			if ($spirits) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$spirits));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($spirits_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$spirits = $this->Model_spirits->limit($segmentFour)->get_all();
			if ($spirits) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$spirits));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($spirits_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "type" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$spirits = $this->Model_spirits->order_by($segmentFour, $segmentFive)->get_all();
			if ($spirits) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$spirits));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$spirits = $this->Model_spirits->get_by(array('type' => $spirits_id));
			if ($spirits) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$spirits));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $spirits));
	}
  
 }

 public function beverages_get() {
	$beverages_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_beverages');
	$beverages = $this->Model_beverages->get_all();
	if(isset($beverages_id)){
		if (is_numeric($beverages_id)) {
			$beverages = $this->Model_beverages->get_by(array('id' => $beverages_id));
			if ($beverages) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beverages));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($beverages_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$beverages = $this->Model_beverages->limit($segmentFour)->get_all();
			if ($beverages) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beverages));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($beverages_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$beverages = $this->Model_beverages->order_by($segmentFour, $segmentFive)->get_all();
			if ($beverages) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beverages));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$beverages = $this->Model_beverages->get_by(array('name' => $beverages_id));
			if ($beverages) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beverages));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $beverages));
	}
  
 }

 public function beers_get() {
	$beers_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_beers');
	$beers = $this->Model_beers->get_all();
	if(isset($beers_id)){
		if (is_numeric($beers_id)) {
			$beers = $this->Model_beers->get_by(array('id' => $beers_id));
			if ($beers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($beers_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$beers = $this->Model_beers->limit($segmentFour)->get_all();
			if ($beers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($beers_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$beers = $this->Model_beers->order_by($segmentFour, $segmentFive)->get_all();
			if ($beers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$beers = $this->Model_beers->get_by(array('name' => $beers_id));
			if ($beers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$beers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $beers));
	}
  
 }


 public function liquers_get() {
	$liquers_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_liquers');
	$liquers = $this->Model_liquers->get_all();
	if(isset($liquers_id)){
		if (is_numeric($liquers_id)) {
			$liquers = $this->Model_liquers->get_by(array('id' => $liquers_id));
			if ($liquers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$liquers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($liquers_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$liquers = $this->Model_liquers->limit($segmentFour)->get_all();
			if ($liquers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$liquers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($liquers_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$liquers = $this->Model_liquers->order_by($segmentFour, $segmentFive)->get_all();
			if ($liquers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$liquers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$liquers = $this->Model_liquers->get_by(array('name' => $liquers_id));
			if ($liquers) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$liquers));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $liquers));
	}
  
 }

 public function tequilas_get() {
	$tequilas_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_tequilas');
	$tequilas = $this->Model_tequilas->get_all();
	if(isset($tequilas_id)){
		if (is_numeric($tequilas_id)) {
			$tequilas = $this->Model_tequilas->get_by(array('id' => $tequilas_id));
			if ($tequilas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$tequilas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($tequilas_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$tequilas = $this->Model_tequilas->limit($segmentFour)->get_all();
			if ($tequilas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$tequilas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($tequilas_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$tequilas = $this->Model_tequilas->order_by($segmentFour, $segmentFive)->get_all();
			if ($tequilas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$tequilas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$tequilas = $this->Model_tequilas->get_by(array('name' => $tequilas_id));
			if ($tequilas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$tequilas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $tequilas));
	}
  
 }

 public function vodkas_get() {
	$vodkas_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_vodkas');
	$vodkas = $this->Model_vodkas->get_all();
	if(isset($vodkas_id)){
		if (is_numeric($vodkas_id)) {
			$vodkas = $this->Model_vodkas->get_by(array('id' => $vodkas_id));
			if ($vodkas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$vodkas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($vodkas_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$vodkas = $this->Model_vodkas->limit($segmentFour)->get_all();
			if ($vodkas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$vodkas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($vodkas_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$vodkas = $this->Model_vodkas->order_by($segmentFour, $segmentFive)->get_all();
			if ($vodkas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$vodkas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$vodkas = $this->Model_vodkas->get_by(array('name' => $vodkas_id));
			if ($vodkas) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$vodkas));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $vodkas));
	}
  
 }

 public function gins_get() {
	$gins_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_gins');
	$gins = $this->Model_gins->get_all();
	if(isset($gins_id)){
		if (is_numeric($gins_id)) {
			$gins = $this->Model_gins->get_by(array('id' => $gins_id));
			if ($gins) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$gins));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($gins_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$gins = $this->Model_gins->limit($segmentFour)->get_all();
			if ($gins) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$gins));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($gins_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$gins = $this->Model_gins->order_by($segmentFour, $segmentFive)->get_all();
			if ($gins) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$gins));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$gins = $this->Model_gins->get_by(array('name' => $gins_id));
			if ($gins) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$gins));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $gins));
	}
  
 }

 public function whiskeys_get() {
	$whiskeys_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_whiskeys');
	$whiskeys = $this->Model_whiskeys->get_all();
	if(isset($whiskeys_id)){
		if (is_numeric($whiskeys_id)) {
			$whiskeys = $this->Model_whiskeys->get_by(array('id' => $whiskeys_id));
			if ($whiskeys) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$whiskeys));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($whiskeys_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$whiskeys = $this->Model_whiskeys->limit($segmentFour)->get_all();
			if ($whiskeys) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$whiskeys));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($whiskeys_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$whiskeys = $this->Model_whiskeys->order_by($segmentFour, $segmentFive)->get_all();
			if ($whiskeys) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$whiskeys));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$whiskeys = $this->Model_whiskeys->get_by(array('name' => $whiskeys_id));
			if ($whiskeys) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$whiskeys));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $whiskeys));
	}
  
 }

 public function rums_get() {
	$rums_id = $this->uri->segment(3);
	$segmentFour = $this->uri->segment(4);
	$segmentFive = $this->uri->segment(5);
	$this->load->model('Model_rums');
	$rums = $this->Model_rums->get_all();
	if(isset($rums_id)){
		if (is_numeric($rums_id)) {
			$rums = $this->Model_rums->get_by(array('id' => $rums_id));
			if ($rums) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$rums));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}elseif ($rums_id === "limit" && isset($segmentFour) && is_numeric($segmentFour)) {
			$rums = $this->Model_rums->limit($segmentFour)->get_all();
			if ($rums) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$rums));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The specified limit does not exist"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}elseif ($rums_id === "order" && isset($segmentFour) && isset($segmentFive) && ($segmentFour === "name" || $segmentFour === "price" || $segmentFour === "id") && ($segmentFive === "ASC" || $segmentFive === "DESC") ) {
			$rums = $this->Model_rums->order_by($segmentFour, $segmentFive)->get_all();
			if ($rums) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$rums));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "500" , "response" =>"The order is only available with name or price."), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
			}
		}else{
			$rums = $this->Model_rums->get_by(array('name' => $rums_id));
			if ($rums) {
				$this->response(array("status" => "success" , "status_code" => "200" , "response"=>$rums));
			}else{
				$this->response(array("status" => "failure" , "status_code" => "404" , "response" =>"The specified product could not be found"), REST_Controller::HTTP_NOT_FOUND);
			}
		}
		
	}else{
		$this->response(array('status' => 'success', 'status_code' => '200', 'response' => $rums));
	}
  
 }


 public function coffees_post(){
 	$this->load->library('form_validation');
 	$data = remove_unknown_fields($this->post(), $this->form_validation->get_field_names('coffee_post'));
 	$this->form_validation->set_data($data);
 	if ($this->form_validation->run('coffee_post') != false) {
 		$this->load->model('Model_coffees');
 		$exists = $this->Model_coffees->get_by(array('name' => $this->post('name')));
 		if ($exists) {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name (".$this->post('name').") is already specified in the database. Duplicate entries are not allowed"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		}
 		$getpostproductname = $this->post('name');
 		if ($getpostproductname === "limit" || $getpostproductname === "order") {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name that you specified is not an allowed product name"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		} else {
 			$coffees_id = $this->Model_coffees->insert($data);
	 		if (!$coffees_id) {
	 			$this->response(array("status" => "failure" , "status_code" => "500" , "message" => "Unexpected Error", "response"=>"An unexpected error occured. Please make sure you have inserted correct values, making the correct Http Request or please try again later"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
	 		} else {
	 			$created = $this->Model_coffees->get_by(array('id' => $coffees_id));
	 			$this->response(array("status" => "success" , "status_code" => "201" , "message" => "created", "response"=>$created), REST_Controller::HTTP_CREATED);
	 		}
 		}
 		
 		
 		
 	} else {
 		$this->response(array("status" => "failure", "status_code" => "400", "response" => $this->form_validation->get_errors_as_array()), REST_Controller::HTTP_BAD_REQUEST);
 	}
 	
 }


 public function snacks_post(){
 	$this->load->library('form_validation');
 	$data = remove_unknown_fields($this->post(), $this->form_validation->get_field_names('snacks_post'));
 	$this->form_validation->set_data($data);
 	if ($this->form_validation->run('snacks_post') != false) {
 		$this->load->model('Model_snacks');
 		$exists = $this->Model_snacks->get_by(array('name' => $this->post('name')));
 		if ($exists) {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name (".$this->post('name').") is already specified in the database. Duplicate entries are not allowed"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		}
 		$getpostproductname = $this->post('name');
 		if ($getpostproductname === "limit" || $getpostproductname === "order") {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name that you specified is not an allowed product name"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		}else{
 			$snacks_id = $this->Model_snacks->insert($data);
	 		if (!$snacks_id) {
	 			$this->response(array("status" => "failure" , "status_code" => "500" , "message" => "Unexpected Error", "response"=>"An unexpected error occured. Please make sure you have inserted correct values, making the correct Http Request or please try again later"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
	 		} else {
	 			$created = $this->Model_snacks->get_by(array('id' => $snacks_id));
	 			$this->response(array("status" => "success" , "status_code" => "201" , "message" => "created", "response"=>$created), REST_Controller::HTTP_CREATED);
	 		}
 		}
 		
 		
 	} else {
 		$this->response(array("status" => "failure", "status_code" => "400", "response" => $this->form_validation->get_errors_as_array()), REST_Controller::HTTP_BAD_REQUEST);
 	}
 	
 }


 public function sweets_post(){
 	$this->load->library('form_validation');
 	$data = remove_unknown_fields($this->post(), $this->form_validation->get_field_names('sweets_post'));
 	$this->form_validation->set_data($data);
 	if ($this->form_validation->run('sweets_post') != false) {
 		$this->load->model('Model_sweets');
 		$exists = $this->Model_sweets->get_by(array('name' => $this->post('name')));
 		if ($exists) {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name (".$this->post('name').") is already specified in the database. Duplicate entries are not allowed"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		}
 		$getpostproductname = $this->post('name');
 		if ($getpostproductname === "limit" || $getpostproductname === "order") {
 			$this->response(array("status" => "failure" , "status_code" => "422" , "message" => "Unprocessable Entity", "response"=>"The product name that you specified is not an allowed product name"), REST_Controller::HTTP_UNPROCESSABLE_ENTITY);
 		}else{
 			$sweets_id = $this->Model_sweets->insert($data);
	 		if (!$sweets_id) {
	 			$this->response(array("status" => "failure" , "status_code" => "500" , "message" => "Unexpected Error", "response"=>"An unexpected error occured. Please make sure you have inserted correct values, making the correct Http Request or please try again later"), REST_Controller::HTTP_INTERNAL_SERVER_ERROR);
	 		} else {
	 			$created = $this->Model_sweets->get_by(array('id' => $sweets_id));
	 			$this->response(array("status" => "success" , "status_code" => "201" , "message" => "created", "response"=>$created), REST_Controller::HTTP_CREATED);
	 		}
 		}
 		
 		
 	} else {
 		$this->response(array("status" => "failure", "status_code" => "400", "response" => $this->form_validation->get_errors_as_array()), REST_Controller::HTTP_BAD_REQUEST);
 	}
 	
 }


}