<?php 

	
	/**
	 * Converts Country 2 Letter code to Country
	 * @param string $countryCode, The specified 2 letter country code
	 * @return string, the String that applies to the specified country
	 */
	function convertCountryCodeToCountry($countryCode){
		switch ($countryCode) {
			case 'DE':
				return 'Germany';
				break;
			case 'AT':
				return 'Austria';
				break;
			case 'CH':
				return 'Switzerland';
				break;
			case 'FR':
				return 'France';
				break;
			case 'NL':
				return 'Netherlands';
				break;
			case 'GR':
				return 'Greece';
				break;
			default:
				# code...
				break;
		}
	}
?>
