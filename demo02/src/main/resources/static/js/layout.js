/*
boardSrcript
*/

$(function() {
	$('.Delete-item').on("click", function() {
		if(confirm("정말로 삭제 하시겠습니까?") == true) {
			return true;
		}
		return false;
	})
	
	$('#signin').on("click", () => {
		$("form[name=sign]").attr('action', "signin")
		$("form[name=sign]").submit();
	})
})