<?php if( ! defined('BASEPATH') ) exit('No direct script access allowed');


$config = array(

 'coffee_post' => array(
   array( 'field' => 'name', 'label' => 'Product Name', 'rules' => 'trim|required' ),
   array( 'field' => 'price', 'label' => 'Product Price', 'rules' => 'trim|required|decimal' ),
   array( 'field' => 'image', 'label' => 'Product Image', 'rules' => 'trim|required|valid_url|prep_url' )
  ),
 'snacks_post' => array(
   array( 'field' => 'name', 'label' => 'Product Name', 'rules' => 'trim|required' ),
   array( 'field' => 'price', 'label' => 'Product Price', 'rules' => 'trim|required|decimal' ),
   array( 'field' => 'image', 'label' => 'Product Image', 'rules' => 'trim|required|valid_url|prep_url' )
  ),
 'sweets_post' => array(
   array( 'field' => 'name', 'label' => 'Product Name', 'rules' => 'trim|required' ),
   array( 'field' => 'price', 'label' => 'Product Price', 'rules' => 'trim|required|decimal' ),
   array( 'field' => 'image', 'label' => 'Product Image', 'rules' => 'trim|required|valid_url|prep_url' )
  )

);
?>﻿