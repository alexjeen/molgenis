var is_fullscreen = false;
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
	$('#fullscreen-toggle').click(function(){
		if(is_fullscreen) {

			$(this).find('.fa').addClass('fa-expand').removeClass('fa-compress');
			$('.mainContent > div').addClass('container').removeClass('container-fluid');			
			
		}
		else {
			$(this).find('.fa').removeClass('fa-expand').addClass('fa-compress');
			$('.mainContent > div').removeClass('container').addClass('container-fluid');
		}
		is_fullscreen = !is_fullscreen;
		return false;
	});
	$('.has-tools tr td').hover(
		function(){
			$(this).parents('tr').find('.tools').css('visibility', 'visible');
		},
		function(){
			$(this).parents('tr').find('.tools').css('visibility', 'hidden');
		}
	);
});