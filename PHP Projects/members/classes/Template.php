<?php 


/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Template {

	private $_db, $_data = array();

	/**
	 * Constructor Method for Template class
	 * It instanciates the Database Object.
	 */
	public function __construct(){
		$this->_db = DB::getInstance();
	}
	

	/**
	* Load method for Template Class
	* @access public
	* @param String template name. e.g. reset_password wil
	* fetch the reset_password.php under templates/email/
	* @param Array $values. Used to load templates and set values.
	* In order for a value to be set you have to use the
	* <u_value> and then have an array to set values. e.g.
	* $values = array('name' => 'Kostas'). This will replace
	* the <u_name> with Kostas.
	* @return returns the template with the values changed.
	* 
	*/
	public static function load($templateName, $values = array()){
		$temp = Template::get($templateName);
		if ($values) {
			foreach ($values as $key => $value) {
				$temp = str_replace("{{%".$key."%}}", $value, $temp);
			}
		}

		return $temp;

	}

	/**
	* Load method for Template Class
	* @access public
	* @param String template name. e.g. reset_password wil
	* fetch the reset_password.php under templates/customermenu/
	* @param Array $values. Used to load templates and set values.
	* In order for a value to be set you have to use the
	* <u_value> and then have an array to set values. e.g.
	* $values = array('name' => 'Kostas'). This will replace
	* the <u_name> with Kostas.
	* @return returns the template with the values changed.
	* 
	*/
	public static function loadMenu($templateName, $values = array()){
		$temp = Template::getMenu($templateName);
		if ($values) {
			foreach ($values as $key => $value) {
				$temp = str_replace("{{%".$key."%}}", $value, $temp);
				//print_pre($templateName);
			}
		}
		return $temp;

	}

	/**
	 * Gets a template from the database specified by the template ID and the language
	 * 
	 * @access public
	 * @param string $template, the template ID to return
	 * @param number $language, the language we want the template to be
	 */
	public function getTemplateFromDB($template = null, $language = 1){
		if ($template) {
			$data = $this->_db->get('Templates', array(array('Titel', '=', $template), array('language_id', '=', $language)));
			if ($data->count()) {
				$this->_data = $data->first();
			}
		}
	}


	/**
	 * Returns the template Object that was queried from database
	 * 
	 * @access public
	 * @return object
	 */
	public function data(){
		return $this->_data;
	}
	


	/**
	* Get method for Template Class
	* @access public
	* @param String template name. e.g. reset_password wil
	* fetch the reset_password.php under templates/email/
	* @return returns the template code.
	* 
	*/
	public static function get($template){
		return file_get_contents(load_email_template($template));
	}

	/**
	* Get Menu method for Template Class
	* @access public
	* @param String template name. e.g. reset_password wil
	* fetch the reset_password.php under templates/customermenu/
	* @return returns the template code.
	* 
	*/
	public static function getMenu($template){
		return file_get_contents(load_customermenu_template($template));
	}

	/**
	 * Save data to the specified template
	 * @access public 
	 * @param Object $data
	 * @param String $filename
	 */
	public static function save($data, $filename){
		return file_put_contents($data, load_email_template($template));
	}
}