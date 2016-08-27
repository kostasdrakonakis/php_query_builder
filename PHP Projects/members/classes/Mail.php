<?php 

/**
 * @package	classes
 * @author	Konstantinos Drakonakis
 * @copyright	Copyright (c) 2016 - 2017, Konstantinos Drakonakis
 * @since	Version 1.0.0
 */
class Mail{
	
	private $_subject, $_body, $_from, $_to, $_headers;

	/**
	* Constructor method for Mail Class
	* Sets the headers as content type: text/html
	* and the default charset.
	* @access public
	*
	*/
	public function __construct(){
		$this->_headers = "MIME-Version: 1.0" . "\r\n";
		$this->_headers .= "Content-type:text/html;charset=UTF-8" . PHP_EOL;
		$this->_headers .= "Content-Type: multipart/mixed; boundary=\"".Hash::unique() . PHP_EOL;
		$this->_headers .= "Content-Transfer-Encoding: base64 " . PHP_EOL;
	}


	/**
	* setBody method for Mail Class
	* Setter method for email body.
	* @access public
	* @param String message the message we would like
	* to send via email.
	* 
	*/
	public function setBody($message){
		$this->_body = $message;
	}


	/**
	* getBody method for Mail Class
	* Getter method for email body.
	* @access private
	* @return String message the message we would like
	* to send via email.
	* 
	*/
	private function getBody(){
		return $this->_body;
	}

	/**
	* setSubject method for Mail Class
	* Setter method for email subject.
	* @access public
	* @param String subject, the subject of the message we would like
	* to send via email.
	* 
	*/
	public function setSubject($subject){
		$this->_subject = $subject;
	}

	/**
	* getSubject method for Mail Class
	* Getter method for email subject.
	* @access private
	* @return String subject, the subject of the message we would like
	* to send via email.
	* 
	*/
	private function getSubject(){
		return $this->_subject;
	}

	/**
	* addFrom method for Mail Class
	* Defines From: as custom one and overrides
	* the default sender.
	* @access public
	* @param String from, the email address we send
	* email from.
	* 
	*/
	public function addFrom($from, $name = 'Office'){
		$this->_headers .= "From: " . $name ." ". strip_tags($from) . "" . PHP_EOL;
		$this->_from = $from;
	}

	/**
	* addFrom method for Mail Class
	* Defines From: as custom one and overrides
	* the default sender.
	* @access public
	* @param String from, the email address we send
	* email from.
	* 
	*/
	public function addAttachment($filename, $path){
		$separator = '';
		$path = realpath($path);
		$file = $path."\\".$filename;
		$file_size = is_file($file) ? filesize($file) : 0;
		//print_pre($file);
	    $handle = fopen($file, "r");
	    $content = fread($handle, $file_size);
	    fclose($handle);
	    $content = chunk_split(base64_encode($content));
	    ob_start();
	    $separator = md5(time());
	    $this->_body = "--".$separator . PHP_EOL;
	    $this->_headers .= "Content-Type: application/octet-stream; name=".$filename . PHP_EOL;
	    $this->_headers .= "Content-Disposition: attachment; filename=".$filename . PHP_EOL;
	    $this->_body .= $content . PHP_EOL;
	}

	/**
	* addReply method for Mail Class
	* Defines Reply-To: as custom one and overrides
	* the default reply.
	* @access public
	* @param String from, the email address we send
	* email from.
	* 
	*/
	public function addReply($reply){
		$this->_headers .= "Reply-To: ". strip_tags($reply) . PHP_EOL;
	}




	/**
	* addCC method for Mail Class
	* Defines CC: as custom one and sends
	* a carbon copy to the specified email.
	* @access public
	* @param String cc, the email address we send
	* email to as carbon copy.
	* 
	*/
	public function addCC($cc){
		$this->_headers .= 'Cc: '.$cc.'' . PHP_EOL;
	}


	/**
	* addBCC method for Mail Class
	* Defines BCC: as custom one and sends
	* a blind carbon copy to the specified email.
	* @access public
	* @param String bcc, the email address we send
	* email to as blind carbon copy.
	* 
	*/
	public function addBCC($bcc){
		$this->_headers .= 'Bcc: '.$bcc.'' . PHP_EOL;
	}

	/**
	* addTo method for Mail Class
	* Defines To: as custom one and sends
	* an email to the specified email.
	* @access public
	* @param String to, the email address we send
	* email to.
	* 
	*/
	public function addTo($to){
		$this->_to = $to;
	}

	/**
	* getTo method for Mail Class
	* Getter method for To: email address
	* @access private
	* @return String to, the email address we send
	* email to.
	* 
	*/
	private function getTo(){
		return $this->_to;
	}

	private function getFrom(){
		return $this->_from;
	}


	/**
	* getHeaders method for Mail Class
	* Getter method for Headers: we would like
	* to send along with the email.
	* 
	* @access private
	* @return String headers, the headers that we send
	* along with email.
	* 
	*/
	private function getHeaders(){
		return $this->_headers;
	}


	/**
	* send method for Mail Class
	* The method to actually send an email.
	* 
	* @access public
	* 
	* 
	*/
	public function send(){
		mail($this->getTo(), $this->getSubject(), $this->getBody(), $this->getHeaders(), "-f " . $this->getFrom());
	}

}