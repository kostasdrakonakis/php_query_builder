<?php include "header.php"?>
<div class="container">
<div class="row">
<div class="panel-body">
<div class="col-lg-12">
      <h1 class="page-header">Login</h1>
      
     </div>
    </div>
</div>
        
                    
                
    <div class="row">
	 <div class="col-md-12 col-md-offset-3">
        <?php if(!empty($error))
                    { ?>
                <div class="row col-md-offset-1"  >
                 <div class="alert alert-danger pull-left">
                <strong>Danger!</strong> <?php echo $error; ?>
                    </div>
                </div>
                    <?php } ?>
        <div class="col-lg-6">
          <form role="form" action="<?php echo base_url() ?>user/login/" method="post">
            <div class="form-group">
              <label for="usr">Name:</label>
              <input type="text" class="form-control" id="usr" placeholder="John Doe" name="InputUserName" required>
            </div>
            <div class="form-group">
              <label for="pwd">Email:</label>
              <input type="password" class="form-control" id="pwd" name="InputPassword" id="InputPassword" required>
            </div>
              <input type="submit" name="submit" id="submit"  value="Login" class="btn btn-info pull-right">
          </form>
        </div>
    </div>
	</div>

<?php include "footer.php"?>