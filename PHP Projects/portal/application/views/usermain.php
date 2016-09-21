<?php include "header.php" ?>
<div class="container">
<br/><br>
	<div class="jumbotron">
		<img style="width:100%; margin:0 auto; height:30%;" src="http://cdn.skilledup.com/wp-content/uploads/2014/10/Blog-Promotion-101-Illustration-Feature_1290x688_KL.jpg" width="400" height="150"/>
		<h1 style="text-align:center;">This is just a portal created for the sake of the project using CodeIgniter.</h1>
	</div>
	
	 <div class="col-lg-12">
	 	<a href="<?php echo base_url() ?>user/new_post/<?php echo $user_name; ?>" name="btn"  class="pull-right btn btn-primary btn-md"><i class="fa fa-plus"></i> New Post</a>
      <h1 class="page-header">News</h1>

     </div>
     
	<?php if (isset($articles)) {
		if($articles === ''){ ?>
                <div class="row col-md-offset-1"  >
	                 <div class="alert alert-danger pull-left">
	               		You have not created any articles. <strong><a href="<?php echo base_url();?>user/new_post/<?php echo $user_name; ?>">Create </a></strong> one now. 
	                 </div>
                </div>
        <?php }else{
				foreach ($articles as $article) {
			?>
	 			<h2><?php echo $article->title;?></h2>
				 <div class="panel panel-default">
					<div class="panel-heading"><?php echo $article->title;?></div>
						<div class="panel-body">
							<div class="panel-body">
								<div class="col-sm-6">
						            <div class="pic pull-right">
						            	 <img src="<?php if($article->photo!='') {echo base_url()."images/".$article->photo;} ?>" alt="" 
						            	 <?php if($article->photo!='') { echo 'class="std_img"'; }?> />
						            </div>
				        		</div>
								<p><?php echo $article->text;?></p>
							</div>
							<div class="panel-footer"><?php echo '<strong>Author:</strong> '.$article->user_name." - <strong>Posted at</strong>: ".$article->curr_time;?>
								<div class="pic pull-right">
									<a href="<?php echo base_url(); ?>user/delete_user_post/<?php echo $article->id;?>/<?php echo $article->user_name;?>" >Delete</a>
								</div>
							</div>
					  </div>
	  <?php }}}?>
	  
	
	  
	
<?php include "footer.php"?>