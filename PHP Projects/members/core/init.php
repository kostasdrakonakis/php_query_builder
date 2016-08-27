<?php

/**
 * @package	core
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */

/**
* error_reporting and display_errors
* are on only in development mode
* when in production set:
* 
* ini_set('display_errors', 0);
*
**/
error_reporting(E_ALL);
ini_set("display_errors", 1);
ob_start();
ini_set("default_charset", "UTF-8");
header('Content-type: text/html; charset=UTF-8');
ini_set('sendmail_from', 'example@domain.com');
session_start();
$protocol = isset($_SERVER['HTTPS']) ? 'https://' : 'http://';
$GLOBALS['config'] = array(
		'mysql' => array(
				'host' => 'localhost',
				'username' => 'root',
				'password' => 'password',
				'dbname' => 'database1'
			),
		'paymentsdb' => array(
				'host' => 'localhost',
				'username' => 'root',
				'password' => 'password',
				'dbname' => 'database2'
			),
		'session' => array(
				'session_name' => 'user',
				'token_name' => 'token',
				'email_name' => 'email',
				'email_link' => 'link',
				'mail_id' => 'mail_outbox_id'
			),
		'remember' => array(
				'cookie_name' => 'hash',
				'cookie_expiry' => 604800
			),
		'folder' => array(
				//system folders
				'root' => 'members',
				'head' => 'head',
				'scripts' => 'scripts',
				'views' => 'views',
				'menus' => 'menus',
				'templates' => 'templates',
				'email' => 'email',
				'footer' => 'footer',
				'img' => 'img',
				'js' => 'js',
				'css' => 'css',
				'logs' => 'logs',
				'functions' => 'functions',
				'helpers' => 'helpers',
				'libraries' => 'libraries',
				'extensions' => 'extensions',
				'pdf' => 'pdf',
				'travelDescriptions' => 'travelDescriptions',
				//dashboard folders for new Customer Area
				'account' => 'account',
				'arrival' => 'arrival',
				'dashboard' => 'dashboard',
				'changepassword' => 'changepassword',
				'delete' => 'delete',
				'personalinfo' => 'personalinfo',
				'profile' => 'profile',
				'bookings' => 'bookings',
				'contact' => 'contact',
				'form' => 'form',
				'mailbox' => 'mailbox',
				'inbox' => 'inbox',
				'outbox' => 'outbox',
				'fellowtravelers' => 'fellowtravelers',
				'orders' => 'orders',
				'bedlinnen' => 'bedlinnen',
				'payments' => 'payments',
				'forgot' => 'forgot',
				'customermenu' => 'customermenu',
				'iban' => 'iban',
				'iban_examples' => 'iban_examples',
				'readoutbox' => 'readoutbox',
			),
		'database' => array(
				'names' => 'utf8mb4',
				'charset' => 'utf8mb4',
				'collation' => 'utf8mb4_general_ci',
				'driver' => 'mysql',
				'character_encoding' => 'UTF-8',
				'charset_html' => 'utf-8'
			),
		'url' => array(
				'base_url' => $protocol.'www.example.com/members/',
				'uri' => "$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]"
				),
		'languages' => array(
				'english' => 'en',
				'german' => 'de',
				'greek' => 'gr'
			),
		'headers' => array(
				'404' => 'HTTP/1.0 404 Not Found',
				'401' => 'HTTP/1.0 401 Unauthorized',
				'500' => 'HTTP/1.0 500 Internal Server Error',
				'403' => 'HTTP/1.0 403 Forbidden'
			),
		'html' => array(
				'width' => 'device-width',
				'initial_scale' => '1',
				'maximum_scale' => '1',
				'user_scalable' => 'no',
			)
	);

spl_autoload_register(function($class){
	require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' .'classes/' . $class . '.php');
});

require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['functions'] . '/' . 'sanitize.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['functions'] . '/' . 'debug.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['functions'] . '/' . 'loader.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['functions'] . '/' . 'localize.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['helpers'] . '/' . 'uri_helper.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['helpers'] . '/' . 'path_helper.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['helpers'] . '/' . 'error_helper.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['libraries'] . '/' . 'google-calendar/vendor/autoload.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['libraries'] . '/' . 'purify_html/HTMLPurifier.auto.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['libraries'] . '/' . 'iban/php-iban.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['libraries'] . '/' . 'iban/oophp-iban.php');
require_once($_SERVER['DOCUMENT_ROOT'] . "/" .$GLOBALS['config']['folder']['root'] . '/' . $GLOBALS['config']['folder']['libraries'] . '/' . 'php_mailer/PHPMailerAutoload.php');

if (isset($_GET['lang'])) {
	$lang = $_GET['lang'];
	if ($lang == '1') {
		require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/de.php');
	}elseif ($lang == '2') {
		require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/en.php');
	}elseif ($lang == '3') {
		require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/fr.php');
	}elseif ($lang == '4') {
		require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/nl.php');
	}else{
		require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/en.php');
	}
}else{
	require($_SERVER['DOCUMENT_ROOT']. "/" .$GLOBALS['config']['folder']['root'] . '/' . 'lang/en.php');
}

?>