<?php
/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class URL {
	
	/**
	 * clearQuery function for URL class
	 * Sanitizes URL and clears the query part
	 * e.g. https://www.domain.com/index.php?id=1
	 * will remove the ?id=1 part
	 * 
	 * @param String $url
	 * @return the cleared URL
	 */
	public static function clearQuery($url){
		return strtok($url, '?');
	}
	
	/**
	 * validate method for URL class
	 * Validates a URL
	 * @param String $url
	 * @return boolean, TRUE if is valid and FALSE
	 * if not.
	 */
	public static function validate($url=''){
		if (!filter_var($url, FILTER_VALIDATE_URL) === false) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * parse method for URL class
	 * It parses parts of URL
	 * e.g. URL::parse('http://www.domain.com', 'protocol');
	 * will return http.
	 * 
	 * valid parse parts:
	 * 1) protocol
	 * 2) domain
	 * 3) port
	 * 4) path
	 * 5) query
	 * 6) anchor
	 * 7) extension
	 * 
	 * @param String $url
	 * @param String $parse
	 */
	public static function parse($url, $parse = NULL){
		if (self::validate($url)) {
			if (!is_null($parse)) {
				switch ($parse) {
					case 'protocol':
						return parse_url($url, PHP_URL_SCHEME);
						break;
					case 'domain':
						return parse_url($url, PHP_URL_HOST);
						break;
					case 'port':
						return parse_url($url, PHP_URL_PORT);
						break;
					case 'path':
						return parse_url($url, PHP_URL_PATH);
						break;
					case 'query':
						return parse_url($url, PHP_URL_QUERY);
						break;
					case 'anchor':
						return parse_url($url, PHP_URL_FRAGMENT);
						break;
					case 'extension':
						return basename($url);
						break;
					default:
						return $url;
						break;
				}
			}else{
				return $url;
			}
		}
	}
}
