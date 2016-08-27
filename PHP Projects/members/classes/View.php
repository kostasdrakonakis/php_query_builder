<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class View {
	
	public static function renderHeader($view){
		include views_path() . Config::get('folder/head') . "/" . $view . ".php" ;
	}

	public static function renderFooter($view){
		include views_path() . Config::get('folder/footer') . "/" . $view . ".php" ;
	}

	public static function renderScripts($view){
		include views_path() . Config::get('folder/scripts') . "/" . $view . ".php" ;
	}

	public static function renderJavascriptFile($file){
		include views_path() . Config::get('folder/js') . "/" . $file . "_js.php" ;
	}

	public static function renderMenu($view){
		require views_path() . Config::get('folder/menus') . "/" . $view . "_menu.php" ;
	}
}