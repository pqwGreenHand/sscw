/// <reference path="jquery-1.9.1.min.js" />



$(function(){
	
	 $(".login-checkbox label").click(function(){
	 	if ($(this).hasClass("login-checkbox-on")) {
	 		$(this).removeClass("login-checkbox-on");
	 	} else{
	 		$(this).addClass("login-checkbox-on");
	 	}
	 	
	 });
	 
	 
	 $(".off-list li").click(function(){
		$('.off-popbox').show().css("bottom","0")
		
	 })
	 $(".off-popbox-btn").click(function(){
	 	$(this).siblings(".off-popCon").toggle();
	 	$(this).parent(".off-popbox").toggleClass("on");
	 })
});


jQuery(function($){
	$('.reduce').click(function () {
	   var $input = $(this).next();
	   var val = $input.val();
	   if (val <= 1) {
		   	$(this).css("color","#ddd")
		   return false;
		} else {
		   $input.val(--val);
		   $(this).css("color","#3978e3")
		}
	
	  
	});
	
	
	$('.add').click(function () {
	   var $input = $(this).prev();
	   var val = $input.val();
	   if (val < 999) {
	      $input.val(++val);
	   } else {
	     alert('不能大于999');
	   }
	   
	  if($('input[name="Gqty"]').val()<="1"){
		$(".reduce").css("color","#3978e3");
		}else{
			$(".reduce").css("color","#3978e3");
		}
	});
	
	$('input[name="Gqty"]').blur(function(){
	     
	      if ($(this).val() == 0) {
	         alert('不能小于1');
	         $(this).val(1);
	      } else if ($(this).val() > 999) {
	         alert('不能大于999');
	         $(this).val(1);
	      }
	  
	});

});




























