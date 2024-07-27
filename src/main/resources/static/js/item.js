/**
 * 
 */

$(function() {
	
	/* 숫자에 ,를 추가하는 로직 */
	var nlen = $('.number').length;
	for(var i=0; i<nlen; i++) {
		var n = $('.number').eq(i).val();
		var to_n = n.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g,",");
		$('.number').eq(i).val(to_n);
	}
	
	$('.inNum').on('keyup change propertychange input paste',function(){
		var nlen = $('.inNum').length;
		for(var i=0; i<nlen; i++) {
			var n = $('.inNum').eq(i).val().replace(/[^0-9,]/g,'');
			var tmpn = n.replace(/[,]/g, '');
			$('.inNum').eq(i).val(numberWithCommas(tmpn));
		}
	});
	
	function numberWithCommas(num) {
		var parts = num.toString().split(".");
		return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",")
		+ (parts[1] ? "," + parts[1] : "");
	}
	
	/* */
	
	
	$('.itemModify').on("click", function(){
		const i_id = $(this).closest('tr').find('input[name=id]').val();
		location.href='/itemModify?id='+i_id;
	})
	
	$('.iDetail').on("click", function(){
		var i_id = $(this).find('input[name=iid]').val();
		location.href='/itemView?id='+i_id;
	})
	
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
	
	$('.ModifyProAct').on('click', function() {
		var nm = this.name;
		if (nm == 'update') {
			$("form[name=itemform]").attr('action', '/ItemMgtProc?flag=update');
		} else if (nm == 'delete') {
			$("form[name=itemform]").attr('action', '/ItemMgtProc?flag=delete');
		}
		$("form[name=itemform]").submit();
	})
	
	$('#signin').on("click", () => {
		$("form[name=sign]").attr('action', "signin")
		$("form[name=sign]").submit();
	})
	
	/* Item View Detail */
	$('.Accordian-btn').on('click', () => {
		$('.Accordian-Body').toggle();
	})
})