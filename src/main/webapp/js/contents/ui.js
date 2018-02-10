$(document).ready(function () {
	$( '.checkAll' ).click( function() {
		$( '.item' ).prop( 'checked', this.checked );
	});

	$("h3").click(function () {
		if($(this).siblings("ul").hasClass("show")){
			$(this).addClass("off");
			$(this).siblings("ul").slideUp();
			$(this).siblings("ul").removeClass("show");
		}else{
			$("h3").addClass("off");
			$("h3").siblings("ul").slideUp();
			$("h3").siblings("ul").removeClass("show");
			$(this).removeClass("off");
			$(this).siblings("ul").slideDown();	
			$(this).siblings("ul").addClass("show");
		}
	});

	$(".ctrl_btn").click(function () {
		if($(this).prop("checked")){
			$(this).parents().find("h3").removeClass("off");
			$(this).parents().siblings().find(".dep3").slideDown();
			$(this).siblings("ul").addClass("show");
		}else{
			$(this).parents().find("h3").addClass("off");
			$(this).parents().siblings().find(".dep3").slideUp();
			$(this).siblings("ul").removeClass("show");
		}
	});
	
	$("ul.dep3> li> em").click(function () {
		$(this).toggleClass("off");
		$(this).siblings("ul").slideToggle("fast");
	});
});

function fnOpenMenu(i){

	$("h3").addClass("off");
	$("h3").eq(i).removeClass("off");
	$("h3").siblings("ul").hide();
	$("h3").eq(i).siblings("ul").show();
	$("h3").eq(i).siblings("ul").addClass("show");
}
