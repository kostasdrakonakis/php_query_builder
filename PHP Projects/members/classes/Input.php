<?php

	/**
	 * @package	classes
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */
	class Input{

		/**
		* Exists method for Input class 
		* Checks if Input is not empty.
		*
		* @access public
		* @param String $type, the type of the input $_POST or $_GET.
		* @return returns TRUE if not empty or FALSE if is empty.
		*
		**/
		public static function exists($type = 'post'){
			switch($type){
				case 'post':{
					return (!empty($_POST)) ? true: false;
					break;
				}
				case 'get':{
					return (!empty($_GET)) ? true: false;
					break;
				}
				default:{
					return false;
					break;
				}
			}
		}
		
		/**
		* Get method for Input class 
		* Gets the user input.
		*
		* @access public
		* @param String $item, the input field we get the value from.
		* @return returns the input.
		* 
		* e.g. <input type="text" name="username" placeholder"Username" />
		* Instead of saying if(isset($_POST['username'])){$name = $_POST['username'];}
		* We use Input::get('username');
		*
		**/
		public static function get($item){
			if(isset($_POST[$item])){
				return $_POST[$item];
			}elseif(isset($_GET[$item])){
				return $_GET[$item];
			}
			return '';
		}

		public static function mask($number, $amount = 6, $maskingCharacter = 'X'){
			$digits_not_to_be_replaced = strlen($number) - $amount;
			$replaceCount = strlen($number) - $digits_not_to_be_replaced;
			return substr_replace($number, self::setMaskingAmount($replaceCount), 0, $replaceCount);
		}

		private static function setMaskingAmount($count){
			$mask = '';
			for ($i=0; $i < $count; $i++) { 
				$mask .= 'X';
			}

			return $mask;
		}
	}
?>