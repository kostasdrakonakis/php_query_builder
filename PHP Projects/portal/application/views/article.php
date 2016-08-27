<?php include "header.php" ?>
<div class="container">
	<div class="col-lg-12">
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
      <a href="<?php echo base_url() ?>pdf/loadPdf/<?php echo $article['id']; ?>"  name="btn"  target="_blank" class="pull-right btn btn-primary btn-md"><i class="fa fa-plus"></i> Download PDF</a>
     </div>
	<?php if (isset($article)) {
		?>

	 <h2><?php echo $article["title"];?></h2>
	  <div class="panel panel-default">
		<div class="panel-heading"><?php echo $article["title"];?></div>
		<div class="panel-body">
		<div class="panel-body">
			<div class="col-sm-6">
            <div class="pic pull-right">
            	 <img src="<?php if($article["photo"]!='') {echo "../../images/".$article["photo"];} ?>" alt="" <?php if($article["photo"]!='') { echo 'class="std_img"'; }?> />
            </div>
         </div>
        </div>
			<p><?php echo $article["text"];?></p>
			
		</div>
		<div class="panel-footer"><?php echo '<strong>Author:</strong> '.$article["user_name"]." - <strong>Posted at</strong>: ".$article["curr_time"];?></div>
	  </div>
	   <div class="row form-group">
		  <div class="col-sm-12">
			<input type="Button" value="Back" name="buttons" class="btn btn-danger" onclick="javascript:window.location.href='<?php echo base_url(); ?>'">
			
			</div>
	    </div>
	  <?php }?>
	
	  
	
<?php include "footer.php"?>