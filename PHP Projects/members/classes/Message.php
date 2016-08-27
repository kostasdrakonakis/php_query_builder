<?php


/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Message{

	/**
	* Error Message method for Message Class
	*
	*
	* @access public
	* @param String the value to be shown
	* It displays the message as error as 
	* bootstrap alert dialog.
	*
	**/
	public static function displayErrorMessage($message){
		echo '<div class="container">';
		echo '<div class="col-md-4 col-md-offset-4">';
		echo '<div class="alert alert-danger">';
		echo '<strong>Error:</strong> ' . $message;
		echo '</div>';
		echo '</div>';
		echo '</div>';
	}


	/**
	* Success Message method for Message Class
	*
	*
	* @access public
	* @param String the value to be shown
	* It displays the message as success message
	* as bootstrap alert dialog.
	*
	**/
	public static function displaySuccessMessage($message){
		echo '<div class="container" >';
		echo '<div class="col-md-4 col-md-offset-4">';
		echo '<div class="alert alert-success" >';
		echo '<strong>Success:</strong> ' . $message;
		echo '</div>';
		echo '</div>';
		echo '</div><br/>';
	}


	/**
	 * Display an Alert box with the specified message
	 * 
	 * @access public
	 * @param string $message, the message to display
	 * @param string $title, the title of the message
	 * @param string $extraInfo, If we want to add any extra info
	 * apart from the message. The extra info will be added after the message
	 */
	public static function displayAlertDialog($message, $title='Error', $extraInfo = null){
		echo '<div class="alert alert-danger alert-dismissible">';
        echo '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>';
        echo '<h4><i class="icon fa fa-ban"></i> '.$title.'!</h4>';
        echo $message . '<br/><br/>' . $extraInfo;
      	echo '</div>';
	}
	
}