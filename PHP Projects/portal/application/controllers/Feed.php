<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Feed extends CI_Controller {
    public function __construct(){  
        parent::__construct();
        $this->load->helper(array('xml'));
        $this->load->model('MY_articles');
    }
    public function index(){
       $this->data['feed_name'] = 'myawesomeportal.com';
       $this->data['encoding'] = 'utf-8';
       $this->data['feed_url'] = 'http://myawesomeportal.com/feed';
       $this->data['page_language'] = 'en';
       $this->data['page_description'] = 'Today Latest News';
       $this->data['creator_email'] = 'kostasdrakonakis13@gmail.com';
       $this->data['query'] = $this->view_table();
       header("Content-Type: application/rss+xml");
       $this->load->view('feed_view',$this->data);
    }

    public function view_table(){
      $result = $this->MY_articles->show_all_news();
        if ($result != false) {
        return $result;
        } else {
        return 'Database is empty !';
      }
  }
}
