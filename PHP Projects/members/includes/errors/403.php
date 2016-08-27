<?php
require_once $_SERVER['DOCUMENT_ROOT'] . 'members/core/init.php';

?>
<html lang="en">
  <head>
    <style>
      body{
        display: table;
        width: 100%;
        height: auto;
        background-image: url('<?php echo image_path(); ?>error/403-page-forbidden.jpg');
        background-repeat: no-repeat ;
        background-size: cover;
        background-position: center;

      }
      #placement{
        display: table;
        margin-left: auto;
        margin-right: auto;
      }

      @media (min-width: 300px) {
        #placement {
            margin-top: 215px;
        }
      }
      @media (min-width: 1024px) {
        #placement {
            margin-top: 230px;
        }
      }
      @media (min-width: 1440px) {
        #placement {
            margin-top: 270px;
        }
      }
      @media (min-width: 1880px) {
        #placement {
            margin-top: 560px;
        }
      }
      @media (min-width: 2560px) {
        #placement {
            margin-top: 430px;
        }
      }
    </style>
    <meta charset="UTF-8">
    <title>Backend</title>

    <?php View::renderHeader('dashboard_header'); ?>

    
  </head>
  <body>
  <div class="container">
      <br/><br/>
      <div class="col-lg-12">
        <a id="placement" href="<?php echo base_url(); ?>index.php" class="btn btn-info">Return to Dashboard</a>
      </div>
      </div>
  </body>
</html>