/* Initiate highlight.js */
hljs.tabReplace = '  ';
hljs.initHighlightingOnLoad();

$(function() {
/* Global Variabes */
var j_window = $(window),
	j_header = $('.header_fixed'),
	j_sidebar = $('.sidebar_popout'),
	j_sidebar_menu = j_sidebar.find('ul.menu'),
	j_content = $('#content'),
	j_footer = $('.footer_wrapper'),
	scroll_pos = $(this).scrollTop(),
	window_pos = j_window.scrollTop() + j_window.height();

/* Scroll Events */
(function() {
var timeout = null,
	header_shadow_class = 'header_shadow';

$(window).on('scroll resize', function() {
	if (timeout) { clearTimeout(timeout) }
	
	timeout = setTimeout(function() {
		scroll_pos = $(this).scrollTop(),
		window_pos = j_window.scrollTop() + j_window.height();
		
		// Header Shadow
		if ( !j_header.hasClass(header_shadow_class) && (scroll_pos >= 1) ) {
			j_header.addClass(header_shadow_class);
		}
		else if (scroll_pos == 0) {
			j_header.removeClass(header_shadow_class);
		}
		
		sidebar_attacher();
	}, 100);
});
})();

/* Enable Smooth Scroll to Anchors */
$('body').on('click', 'a', function() {
    var j_anchor_hash = $(this.hash),
    	href = $(this).attr('href');
    if (href && href.substring(0, 1) == '#' && j_anchor_hash.length) {		
    	$('html, body').animate({ scrollTop: j_anchor_hash.offset().top });
    	return false;
    }
});

/* Enable Sidebar Popout */
(function() {
$('#sidebar_button').on('click', function() {
	var sidebar_hidden_class = 'sidebar_hidden',
		animation_speed = 200;
	
	if ( !j_sidebar.hasClass(sidebar_hidden_class) ) {
		j_sidebar.stop()
				 .animate({
				 	marginLeft: -280
				 }, animation_speed)
				 .addClass(sidebar_hidden_class);
		j_content.stop()
				 .animate({
				 	paddingLeft: 38
				 }, animation_speed);
		$(this).stop()
			   .animate({
			   		marginLeft: -280
			   }, animation_speed);
	}
	else {
		j_sidebar.stop()
				 .animate({
				 	marginLeft: 0
				 }, animation_speed)
				 .removeClass(sidebar_hidden_class);
		j_content.stop()
				 .animate({
				    paddingLeft: 320
				 }, animation_speed);
		$(this).stop()
			   .animate({
			   		marginLeft: 0
			   }, animation_speed);
	}
});
})();

/* Enable Sidebar Accordians */
(function() {
	var animation_speed = 300;
	
	// Add .has_sub_menu to items with sub menus
	$('.sidebar .sub-menu li').each(function() {
		var j_this = $(this);
		
		if ( j_this.children('ul').length ) {
			j_this.addClass('has_sub_menu')
			j_this.children('a').before('<span class="sub_menu_arrow" />');
		}
	});
	
	$('body').on('click', '.sub_menu_arrow, .has_sub_menu a', function(e) {
		var j_this = $(this);
		
		// Hide any open sub menus
		j_this.parent().siblings().children('ul.sub-menu:visible').stop()
				  												  .animate(
				      											       {
				      											    	   height: 'hide',
				      											    	   opacity: 'hide'
				      											       }
				      											  , animation_speed);
		
		j_this.parent().siblings().removeClass('sub_menu_active');
		
		// Show / hide clicked sub menu
		if ( e.target.className == 'sub_menu_arrow' ) {
			toggle_submenu(j_this);
		}
		else if ( !j_this.parent().hasClass('sub_menu_active') ) {
			toggle_submenu(j_this);
		}
	});
	
	// Toggle the accordian
	function toggle_submenu(elm) {
	    elm.siblings('ul').stop()
	    	 				 .animate(
	    	 				     {
	    	 				     	height: 'toggle',
	    	 				     	opacity: 'toggle'
	    	 				     }
	    	 				 , animation_speed
	    	 				 , function() { sidebar_attacher(); });
	    
	    elm.parent().toggleClass('sub_menu_active');
	}
})();

/* Attach/Detach Sidebar */
function sidebar_attacher() {
	var padding_offset = j_header.height() + 30,
		sidebar_no_fixed_class = 'sidebar_no_fixed';
	
	// Remove fixed position from sidebar
	if ( !j_sidebar.hasClass(sidebar_no_fixed_class) && ( ( j_sidebar_menu.height() + padding_offset ) > j_window.height() ) ) {
		j_sidebar.addClass(sidebar_no_fixed_class);
		
		if ( ( window_pos >= j_footer.offset().top ) ) { scroll_pos = $(document).height() - ( j_footer.height() + j_sidebar_menu.height() + padding_offset + 30 ) }
		
		j_sidebar_menu.css('margin-top', scroll_pos + 'px');
		j_sidebar.height($(document).height());
	}
	// Add fixed position to sidebar
	else if ( ( j_sidebar_menu.height() + padding_offset ) <= j_window.height() ) {
		j_sidebar.removeClass(sidebar_no_fixed_class)
				 .height('100%');
		j_sidebar_menu.css('margin-top', 0);
	}
	
	// Animate sidebar to top
	if ( j_sidebar.hasClass(sidebar_no_fixed_class) && ( ( j_sidebar_menu.offset().top - padding_offset ) > scroll_pos ) ) {
		j_sidebar_menu.animate({
			marginTop : scroll_pos
		});
	}
}

/* Remove Self Hash Links from Sidebar */
(function() {
	$('.sidebar .menu a').each(function() {
		var j_this = $(this);
		
		if ( j_this.attr('href') == '#' ) { j_this.removeAttr('href'); }
	});
})();

/* Enable Pretty Date */
$('span.prettydate').prettyDate();

});