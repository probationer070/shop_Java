/**
 * 
 */

$(function() {
	$('.Delete-item').on("click", function() {
		if(confirm("정말로 삭제 하시겠습니까?") == true) {
			return true;
		}
		return false;
	})
	
	$('.Product-add_btn').on({
		'mouseover' : () => {
			$("img[class=icon]").attr("src", "/images/cart-b.gif");
		},
		'mouseout' : () => {
			$("img[class=icon]").attr("src", "/images/cart-w.png");
		},
		'click': () => {
			var itemId = $('input[name=itemId]').val();
			$("form[name=ItemServ]").attr('action', '/addCartItem/'+itemId);
			$("form[name=ItemServ]").submit();
		}
	})
	
	
	// Need Some FeedBack - Failed to get value
	$('.Modify-item').on('click', () => {
		/*var itemId = $(this).parent(".item-foot-goto").find("input[name='itemId']").val();
        alert(itemId);*/
		/*$("form[name=itemform]").attr('action', '/itemModify/'+itemNo);
		$("form[name=itemform]").submit();*/
	})
	
	$('.ModifyProAct').on('click', () => {
		var itemId = $('input[name=itemId]').val();
		$("form[name=itemform]").attr('action', '/itemModifyPro/'+itemId);
		$("form[name=itemform]").submit();
	})
	
	$('#signin').on("click", () => {
		$("form[name=sign]").attr('action', "signin")
		$("form[name=sign]").submit();
	})
})