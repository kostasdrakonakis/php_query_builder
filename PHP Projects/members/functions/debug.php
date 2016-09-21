<?php
	/**
	* @package	functions
	* @author	Konstantinos Drakonakis
	* @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	* @since	Version 1.0.0
	*/
	
	/**
	 * Writes to the web browser console
	 * @param String $flag the flag to identify the message
	 * @param String $value the actual message
	 */
	function console_log($flag, $value){
		echo "<script>console.log('".$flag.": " . $value . "' );</script>";
	}
	
	/**
	 * Writes to the logs/log_(current_date).txt file.
	 * 
	 * @param String $type the type of the log.
	 * Posible Types:
	 * 1) INFO
	 * 2) DEBUG
	 * 3) WARNING
	 * 4) ERROR
	 * 5) NOTICE
	 * @param String $message The message we would like to see.
	 * Every message contains the date and time when the event 
	 * occurred.
	 * 
	 */
	function log_message( $message, $type = 'INFO'){
		$filename = "log_" . date('Y-m-d') . ".log";
		$textToWrite = $type . " [" . date('Y-m-d H:i:s') . "] [" . $_SERVER['REMOTE_ADDR'] . "] -> " . $message.PHP_EOL;
		file_put_contents(log_path() . $filename,  $textToWrite, FILE_APPEND);
	}
	
	/**
	 * Prints the given text in pre-formatted
	 * tags
	 * @param String $text the text we would like 
	 * to print
	 */
	function print_pre($text){
		echo "<pre>";
		print_r($text);
		echo "</pre>";
	}