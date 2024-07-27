/*
boardSrcript
*/

$(function() {
	$('#signin').on("click", () => {
		$("form[name=sign]").attr('action', "signin")
		$("form[name=sign]").submit();
	});
	
	$(document).ready(function(){
		$('#load').delay('1000').fadeOut();
	});
})