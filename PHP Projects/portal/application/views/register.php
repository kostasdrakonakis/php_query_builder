<?php include "header.php" ?>
<div class="container">
<div class="row">
<div class="panel-body">
<div class="col-lg-12">
      <h1 class="page-header">Registration </h1>
      
     </div>
    </div>
</div>
        
                    
                
    <div class="row">
	 <div class="col-md-12 col-md-offset-3">
     
                <?php if(!empty($name_error))
                    { ?>
                <div class="row col-md-offset-1"  >
                 <div class="alert alert-danger pull-left">
                <strong>Danger!</strong> <?php echo $name_error; ?>
                    </div>
                </div>
                    <?php } 
                else if(!empty($email_error))
                    { ?>
                <div class="row col-md-offset-1"  >
                 <div class="alert alert-danger pull-left">
                <strong>Danger!</strong> <?php echo $email_error; ?>
                    </div>
                </div>
                    <?php } 
                     else if(!empty($both_error))
                     {
                      ?>
                <div class="row col-md-offset-1"  >
                 <div class="alert alert-danger pull-left">
                <strong>Danger!</strong> <?php echo $both_error; ?>
                    </div>
                </div>
                    <?php } 
                     else if(!empty($success))
                     {
                      ?>
                <div class="row col-md-offset-1"  >
                 <div class="alert alert-success pull-left">
                <strong>Success!</strong> <?php echo $success; ?>
                    </div>
                </div>
                    <?php } ?>
              

        <div class="col-lg-6">
          <form role="form" action="<?php echo base_url() ?>user/registration_success/" method="post">
            <div class="form-group">
              <label for="usr">Name:</label>
              <input type="text" class="form-control" id="usr" placeholder="John Doe" name="InputName" required value="<?php echo $name; ?>">
            </div>
            <div class="form-group">
              <label for="pwd">Email:</label>
              <input type="email" class="form-control" id="pwd" placeholder="johndoe@example.com" name="InputEmail" required value="<?php echo $email; ?>">
            </div>
              <input type="submit" name="submit" id="submit"  value="Register" class="btn btn-info pull-right">
          </form>
        </div>
    </div>
	</div>

<?php include "footer.php"?>