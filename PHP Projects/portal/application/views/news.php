<?php include "header.php" ?>
<div class="container">
<br>
<br><br>
	<div class="jumbotron">
		<img style="width:100%; margin:0 auto; height:30%;" src="http://cdn.skilledup.com/wp-content/uploads/2014/10/Blog-Promotion-101-Illustration-Feature_1290x688_KL.jpg" width="400" height="150"/>
		<h1 style="text-align:center;">This is just a portal created for the sake of the project using CodeIgniter.</h1>
		<p><a href="<?php base_url();?>news/" style="width:100%;" name="btn"  class="pull-right btn btn-primary btn-md">View Latest News</a></p>
	</div>

	 <div class="col-lg-12">
      <h1 class="page-header">Latest Articles</h1>
      <a href="<?php echo base_url(); ?>feed" name="btn"  class="pull-right btn btn-primary btn-md" target="_blank"><i class="fa fa-plus" ></i> RSS Feeds</a>
     </div>
     
	<?php if (isset($show_table)) {
		foreach ($show_table as $value) {
		?>

	 <h2><?php echo $value->title;?></h2>
	  <div class="panel panel-default">
		<div class="panel-heading"><?php echo $value->title;?></div>
		<div class="panel-body">
			<p><?php echo substr($value->text,0,strlen($value->text)/2).".......................";?></p>
			<p><a href="<?php echo base_url() ?>news/article/<?php echo $value->id ?>" target="_blank" class="btn btn-success">Read More &raquo;</a></p>
		</div>
		<div class="panel-footer"><?php echo '<strong>Author:</strong> '.$value->user_name." - <strong>Posted at</strong>: ".$value->curr_time;?></div>
	  </div>
	  <?php }}?>
	
	  
	
<?php include "footer.php"?>