<script src="<?php echo js_path(); ?>jquery/jquery-2.2.3.min.js"></script>
<script src="<?php echo js_path(); ?>bootstrap/bootstrap.min.js"></script>
<script src="<?php echo js_path(); ?>jquery/jquery-ui.min.js"></script>
<script src="<?php echo js_path(); ?>daterangepicker/daterangepicker.js"></script>
<script src="<?php echo js_path(); ?>datepicker/bootstrap-datepicker.js"></script>
<script src="<?php echo js_path(); ?>slimscroll/jquery.slimscroll.min.js"></script>
<script src="<?php echo js_path(); ?>icheck/icheck.min.js"></script>
<script src="<?php echo js_path(); ?>daterangepicker/moment.min.js"></script>
<script src="<?php echo js_path(); ?>fastclick/fastclick.js"></script>

<script src="<?php echo js_path(); ?>app/app.min.js"></script>

<script>
    $('#reservation').daterangepicker({
	  singleDatePicker: true,
	  showDropdowns: true,
	  dateFormat: 'YYYY-MM-DD'
	});
</script>

<script type="text/javascript">
	function openBooking(csid, id, property_name, days, percentage, iscancelled, arrival_day, booking_step1, booking_step2, contract_confirmed) {

		if (iscancelled == 0) {
			$('#details_widget').show();
			$('#booking_event_widget').show();
			$('#event-text').text("<?php echo BOOKING; ?> in " + property_name);
			$('#days').text(days + " <?php echo DAYS_LEFT; ?>");
			$('#percentage-completed').text(percentage + " % <?php echo COMPLETED; ?>");
			$('#payments_widget').show();
			$('#booking_contract_widget').show();
			$('#payments-title').text("<?php echo PAYMENTS; ?>");
			$('#payments-until').text("<?php echo PAYABLE_UNTIL; ?> " + arrival_day);
			$('#payments-days-left').text("<?php echo PAYMENT_METHOD; ?> : <?php echo CASH; ?>" );
			$('#percentage-style').attr("style", "width:" + percentage + "%;");
			if(contract_confirmed == 1){
				var confirmed = "<?php echo CONTRACT_CONFIRMED; ?>";
				$('#contract-confirmed').text(confirmed);
				$('#contract-confirmed').css("color", "#2EFE2E");
				$('#contract-not-confirmed').hide();
			}else{
				var notconfirmed = "<?php echo CONTRACT_NOT_CONFIRMED; ?>";
				$('#contract-confirmed').text(notconfirmed);
				$('#contract-confirmed').css("color", "#FF0000");
				$('#contract-not-confirmed').show();
				$('#contract-not-confirmed').text("<?php echo CONFIRM_CONTRACT ?>");
			}
			

			$('#percentage-style').attr("style", "width:" + percentage + "%;");

		}else{
			$('#details_widget').hide();
			$('#booking_event_widget').hide();
			$('#payments_widget').hide();
			$('#booking_contract_widget').hide();
		}
		
	}


</script>
