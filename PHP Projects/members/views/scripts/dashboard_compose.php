<script src="<?php echo js_path(); ?>jquery/jquery-2.2.3.min.js"></script>
<script src="<?php echo js_path(); ?>jquery/jquery-ui.min.js"></script>
<script src="<?php echo js_path(); ?>moment/moment.min.js"></script>
<script src="<?php echo js_path(); ?>bootstrap/bootstrap.min.js"></script>

<script src="<?php echo js_path(); ?>daterangepicker/daterangepicker.js"></script>
<script src="<?php echo js_path(); ?>slimscroll/jquery.slimscroll.min.js"></script>
<script src="<?php echo js_path(); ?>icheck/icheck.min.js"></script>
<script src="<?php echo js_path(); ?>fastclick/fastclick.js"></script>
<script src="<?php echo js_path(); ?>app/app.min.js"></script>
<script src="<?php echo js_path(); ?>wysi/bootstrap3-wysihtml5.all.min.js"></script>
<script>
  $(function () {
    $("#compose-textarea").wysihtml5({
    	toolbar: {
		    "font-styles": true,
		    "blockquote": true,
		    "emphasis": true,
		    "lists": true,
		    "link": true,
		    "image": true,
		    "fa": true,
		    "html": true
		}
	});
  });
</script>