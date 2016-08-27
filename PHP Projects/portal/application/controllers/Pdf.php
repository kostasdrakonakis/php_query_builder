<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

require_once (APPPATH . 'libraries/tcpdf/tcpdf.php');

class Pdf extends CI_Controller{

    function __construct() { 
 		parent::__construct();
 		$this->load->model('MY_articles');
 	}

    function loadPdf($article_id){

		$pdf = new TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);
		$pdf->AddPage();
		$data = $this->MY_articles->get_article($article_id);

		$html = '<body style="height: 100%;margin-bottom: 30px;"><div style="height: 30px;">News<br/>Title:'.$data['title'] .'</div><div id="content"><center><img src="./././images/'.$data['photo'].'"'. 'width="100" height="150" align="center"></center><br/>'.$data['text'].'</div><div style="height: 30px;position: absolute;
		    bottom: 0;"><strong>Author:</strong> '.$data['user_name'].' - <strong>Posted at</strong>: '.$data['curr_time'].'</div>';
		$pdf->writeHTML($html, true, false, true, false, '');
		ob_clean();
		$pdf->Output('news_001.pdf', 'I');

	}
}
?>