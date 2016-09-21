<?php
	/**
	 * @package	functions
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */

	
	function escape($string = ''){
		return htmlentities(strip_tags(trim($string)), ENT_QUOTES, Config::get('database/character_encoding'));
	}

	function remove_html($string = ''){
		return htmlspecialchars(strip_tags(trim($string)), ENT_QUOTES, Config::get('database/character_encoding'));
	}


	