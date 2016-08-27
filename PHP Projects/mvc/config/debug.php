<?php 

	function console_log($flag, $value){
		echo "<script>console.log('".$flag.": " . $value . "' );</script>";
	}
	
	function log_message($type = 'INFO', $message){
		$filename = "logs_" . date('Y-m-d') . ".log";
		$protocol = isset($_SERVER["HTTPS"]) ? 'https' : 'http';
		$textToWrite = $type . " [" . date('Y-m-d H:i:s') . "] [".$_SERVER['REMOTE_ADDR']."] [".$protocol . "://" . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI']."] -> " . $message.PHP_EOL;
		file_put_contents('logs/' . $filename,  $textToWrite, FILE_APPEND);
	}
	
	function print_pre($text){
		echo "<pre>";
		print_r($text);
		echo "</pre>";
	}