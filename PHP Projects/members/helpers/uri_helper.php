<?php 
	
	/**
	 * @package	helpers
	 * @author	Konstantinos Drakonakis
	 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
	 * @since	Version 1.0.0
	 */

	/**
	 * Returns the base URL
	 * http://www.domain.com/rootfolder/
	 */
	function base_url(){
		return Config::get('url/base_url');
	}
	
	/**
	 * Returns the server root url
	 * e.g. var/www/public_html/rootfolder/
	 */
	function server_url(){
		return $_SERVER['DOCUMENT_ROOT'] . Config::get('folder/root') . "/";
	}
	
	/**
	 * Returns the base name of the URL
	 * @return the file
	 * extension
	 */
	function base_name(){
		return basename(Config::get('url/uri'));
	}
	
	/**
	 * Returns the path until the dashboard folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['dashboard']
	 * and it will automatically change
	 */
	function dashboard(){
		return base_url() . Config::get('folder/dashboard') .'/';
	}

	/**
	 * Returns the path until the forgot folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['forgot']
	 * and it will automatically change
	 */
	function forgot(){
		return base_url() . Config::get('folder/forgot') .'/';
	}

	/**
	 * Returns the path until the arrival folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['arrival']
	 * and it will automatically change
	 */
	function arrival(){
		return dashboard() . Config::get('folder/arrival') .'/';
	}
	
	/**
	 * Returns the path until the account/profile folder
	 * If you want to change the account folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['account']
	 * If you want to change the profile folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['profile']
	 * and it will automatically change
	 */
	function account_profile(){
		return dashboard() . Config::get('folder/account') .'/' . Config::get('folder/profile') . '/';
	}

	/**
	 * Returns the path until the account/personalinfo folder
	 * If you want to change the account folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['account']
	 * If you want to change the personalinfo folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['personalinfo']
	 * and it will automatically change
	 */
	function account_personal_info(){
		return dashboard() . Config::get('folder/account') .'/' . Config::get('folder/personalinfo') . '/';
	}

	/**
	 * Returns the path until the account/changepassword folder
	 * If you want to change the account folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['account']
	 * If you want to change the changepassword folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['changepassword']
	 * and it will automatically change
	 */
	function account_change_password(){
		return dashboard() . Config::get('folder/account') .'/' . Config::get('folder/changepassword') . '/';
	}

	/**
	 * Returns the path until the account/delete folder
	 * If you want to change the account folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['account']
	 * If you want to change the delete folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['delete']
	 * and it will automatically change
	 */
	function account_delete(){
		return dashboard() . Config::get('folder/account') .'/' . Config::get('folder/delete') . '/';
	}


	
	/**
	 * Returns the path untill the bookings folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['bookings']
	 * and it will automatically change
	 */
	function bookings(){
		return dashboard() . Config::get('folder/bookings') .'/';
	}
	
	/**
	 * Returns the path untill the contact folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['contact']
	 * and it will automatically change
	 */
	function contact(){
		return dashboard() . Config::get('folder/contact') .'/';
	}

	/**
	 * Returns the path untill the contact folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['contact']
	 * and it will automatically change
	 */
	function mailbox(){
		return dashboard() . Config::get('folder/mailbox') .'/';
	}
	
	/**
	 * Returns the path untill the fellowtravelers folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['fellowtravelers']
	 * and it will automatically change
	 */
	function fellow_travelers(){
		return dashboard() . Config::get('folder/fellowtravelers') .'/';
	}
	
	/**
	 * Returns the path untill the orders folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['orders']
	 * and it will automatically change
	 */
	function orders(){
		return dashboard() . Config::get('folder/orders') .'/';
	}
	
	/**
	 * Returns the path untill the payments folder
	 * If you want to change the dashboard folder name
	 * you have to edit in core/init.php $GLOBALS['config']['folder']['payments']
	 * and it will automatically change
	 */
	function payments(){
		return dashboard() . Config::get('folder/payments') .'/';
	}
	
	
	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/index.php
	 */
	function dashboard_url(){
		return dashboard() . base_name();
	}
	
	/**
	 * Returns the path http://www.domainname.com/rootfolder/index.php
	 */
	function login_url(){
		return base_url() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/forgot/index.php
	 */
	function forgot_url(){
		return forgot() . base_name();
	}
	
	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/arrival/index.php
	 */
	function arrival_url(){
		return arrival() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/account/profile/index.php
	 */
	function account_profile_url(){
		return account_profile() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/account/personalinfo/index.php
	 */
	function account_personal_info_url(){
		return account_personal_info() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/account/changepassword/index.php
	 */
	function account_change_password_url(){
		return account_change_password() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/account/delete/index.php
	 */
	function account_delete_url(){
		return account_delete() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/bookings/index.php
	 */
	function bookings_url(){
		return bookings() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/orders/bedlinnen/index.php
	 */
	function contact_url(){
		return contact() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/mailbox/index.php
	 */
	function mailbox_url(){
		return mailbox() . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/mailbox/outbox.php
	 */
	function mailbox_outboxread_url(){
		return mailbox() . Config::get('folder/readoutbox') . '/' . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/orders/bedlinnen/index.php
	 */
	function mailbox_inbox_url(){
		return mailbox() . Config::get('folder/inbox') . '/' . base_name();
	}

	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/orders/bedlinnen/index.php
	 */
	function mailbox_outbox_url(){
		return mailbox() . Config::get('folder/outbox') . '/' . base_name();
	}
	
	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/fellowtravelers/index.php
	 */
	function fellow_travelers_url(){
		return fellow_travelers() . base_name();
	}
	
	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/orders/bedlinnen/index.php
	 */
	function orders_bedlinnen_url(){
		return orders() . Config::get('folder/bedlinnen') . '/' . base_name();
	}
	
	/**
	 * Returns the path 
	 * @link http://www.domainname.com/rootfolder/dashboard/payments/index.php
	 */
	function payments_url(){
		return payments() . base_name();
	}
	
	


?>