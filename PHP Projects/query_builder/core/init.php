<?php

error_reporting(E_ALL);
ini_set("display_errors", 1);
ob_start();
ini_set("default_charset", "UTF-8");
header('Content-type: text/html; charset=UTF-8');
session_start();
$protocol = isset($_SERVER['HTTPS']) ? 'https://' : 'http://';
$GLOBALS['config'] = array(
		'mysql' => array(
				'host' => 'localhost',
				'username' => 'root',
				'password' => 'root',
				'dbname' => 'database'
			),
		'folder' => array(
				'root' => 'project',
				'logs' => 'logs',
				'classes' => 'classes',
				'functions' => 'functions',
				'helpers' => 'helpers',
			),
		'url' => array(
				'base_url' => $protocol.'localhost/project/',
				'uri' => ".$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]"
				),
		'database' => array(
				'names' => 'utf8mb4',
				'charset' => 'utf8mb4',
				'collation' => 'utf8mb4_general_ci',
				'driver' => 'mysql',
				'character_encoding' => 'UTF-8',
				'charset_html' => 'utf-8'
			)
	);

spl_autoload_register(function($class){
	require($_SERVER['DOCUMENT_ROOT'] . $GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['classes'] . '/' . $class . '.php');
});

require_once($_SERVER['DOCUMENT_ROOT'] . $GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['functions'] . '/' . 'debug.php');
require_once($_SERVER['DOCUMENT_ROOT'] . $GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['helpers'] . '/' . 'uri_helper.php');
require_once($_SERVER['DOCUMENT_ROOT'] . $GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['helpers'] . '/' . 'path_helper.php');

?>