<?php
/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Editor{
	
	/**
	 * Loads the file we would like to edit in CKEditor
	 * 
	 * @param unknown_type $filename The file
	 * we would like to edit
	 */
	public static function edit($filename = ''){
		echo '<textarea class="ckeditor" name="editor1">'.$filename.'</textarea>';
		View::renderScripts('cksettings');
	}
	
}