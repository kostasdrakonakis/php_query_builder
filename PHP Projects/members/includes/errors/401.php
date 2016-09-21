<?php
require_once server_url() . 'core/init.php';
?>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Backend</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="<?php echo Config::get('url/base_url'); ?>css/font-awesome.min.css">
    <!-- Metis core stylesheet -->
    <link rel="stylesheet" href="<?php echo Config::get('url/base_url'); ?>css/main.min.css">

    
  </head>
  <body class="error">
    <div class="container">
      <div class="col-lg-8 col-lg-offset-2 text-center">
        <div class="logo">
          <h1>401</h1>
        </div>
        <p class="lead text-muted">Oops, you have no privileges for this action!</p>
        <div class="clearfix"></div>
        <div class="clearfix"></div>
        <br>
        <div class="col-lg-6 col-lg-offset-3">
          <div class="btn-group btn-group-justified">
            <a href="#" class="btn btn-info">Return Dashboard</a> 
          </div>
        </div>
      </div><!-- /.col-lg-8 col-offset-2 -->
    </div>
  </body>
</html>