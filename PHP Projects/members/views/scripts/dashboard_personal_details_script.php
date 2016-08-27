<script src="<?php echo js_path(); ?>jquery/jquery-2.2.3.min.js"></script>
<script src="<?php echo js_path(); ?>jquery/jquery-ui.min.js"></script>
<script src="<?php echo js_path(); ?>moment/moment.min.js"></script>
<script src="<?php echo js_path(); ?>bootstrap/bootstrap.min.js"></script>

<script src="<?php echo js_path(); ?>daterangepicker/daterangepicker.js"></script>
<script src="<?php echo js_path(); ?>slimscroll/jquery.slimscroll.min.js"></script>
<script src="<?php echo js_path(); ?>icheck/icheck.min.js"></script>
<script src="<?php echo js_path(); ?>fastclick/fastclick.js"></script>
<script src="<?php echo js_path(); ?>app/app.min.js"></script>

<script>
    $('#reservation1').daterangepicker({
	  singleDatePicker: true,
	  showDropdowns: true,
	  locale: {
          format: 'YYYY-MM-DD'
      }
	});
	$('#reservation2').daterangepicker({
	  singleDatePicker: true,
	  showDropdowns: true,
	  locale: {
          format: 'YYYY-MM-DD'
      }
	});
	$('#reservation3').daterangepicker({
	  singleDatePicker: true,
	  showDropdowns: true,
	  locale: {
          format: 'YYYY-MM-DD'
      }
	});

	function printDiv(divName) {
	     var printContents = document.getElementById(divName).innerHTML;
	     var originalContents = document.body.innerHTML;

	     document.body.innerHTML = printContents;

	     window.print();

	     document.body.innerHTML = originalContents;
	}
</script>