var _today = "";
$(document).ready(function () {
	var clareCalendar = {
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dateFormat: 'yy-mm-dd', //형식(20120303)
		changeMonth: true, //월변경가능
		changeYear: true, //년변경가능
		showMonthAfterYear: true, //년 뒤에 월 표시
		buttonText: 'Calendar',
		buttonImageOnly: true, //이미지표시
		buttonImage: '/weis_board/images/comm/icon_calendar.png',
		showOn: "both", //(both,button)
		yearRange: '2012:2020'
	};
	$(".datepicker").datepicker(clareCalendar);

	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd < 10) {
	    dd = '0'+dd;
	}

	if(mm < 10) {
	    mm = '0'+mm;
	} 

	_today = yyyy + '-' + mm + '-' + dd;
});

/******************************************************************
 *Function   : fn_setSearchDate
 *Description: 작성날짜(당일, 1주일, 1개월, 3개월, 6개월, 전체) 기준으로 Data 조회
 ******************************************************************/
function fn_setSearchDate(srcDate) {
	var toDate  = "";
	var start   = "";
	
	if(srcDate == "all") {
		$(".datepicker").val("");
		$('.datepicker').datepicker("destroy");
	} else {
		toDate = _today;
		
		var yy       = toDate.substring(0,4);                  //년
		var mm       = parseInt(toDate.substring(5,7),10)-1;   //월
		var dd       = parseInt(toDate.substring(8,10),10);    //일

		var theToDay = new Date(yy, mm, dd);                   //오늘날짜

		theToDay.setDate(theToDay.getDate() - srcDate); 

		start = theToDay.getFullYear() + '-' + getDate(parseInt(theToDay.getMonth())+1) + '-' + getDate(theToDay.getDate());

		$(".datepicker").eq(0).val(start);
		$(".datepicker").eq(1).val(toDate);
	}
}