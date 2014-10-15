$(function(){
	// menu logica
	$('.mainNav nav ul > li').hover(
		function() {
			$(this).find('.submenu').stop().fadeIn(150);
			$(this).find('> a').addClass('open');
		},
		function() {
			$(this).find('.submenu').stop().hide();			
			$(this).find('> a').removeClass('open');			
		}
	);
	// custom selects ed
	$('form select').selectpicker({
		style: 'btn-primary'
	});	
	$('form input').iCheck({
		checkboxClass: 'icheckbox_square-blue',
		radioClass: 'iradio_square-blue'
	});	
	// collapsable panels
	$('.panel.collapsable').each(function(){
		$(this).find('.panel-title').prepend('<i class="collapsePanel fa fa-caret-up"></i>');
		
	});
	$('.collapsable .panel-heading').click(function(){
		var $pb = $(this).parents('.collapsable').find('.panel-body');
		$pb.slideToggle('slow', function(){
			if($(this).is(':visible')) {
				$(this).parents('.collapsable').find('.collapsePanel').addClass('fa-caret-up').removeClass('fa-caret-down');
			}
			else {
				$(this).parents('.collapsable').find('.collapsePanel').removeClass('fa-caret-up').addClass('fa-caret-down');				
			}
		});
		return false;
	});
});