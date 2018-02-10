$(document).ready(function () {
});



/* jquery Null 체크 */
function fn_null_chk(checkItem, strMessage) {
	if (checkItem.val() == "") {
		alert(strMessage);
		checkItem.focus();
		return false;
	}
	return true;
}

function fnSubmit(frmNm, action){							// Sumbit
	$('#'+frmNm).attr("method", "post");
	$('#'+frmNm).attr("action", action);
	$('#'+frmNm).submit();
}
 
function fn_openPop(popUrl, w, h){
	if(x == ""){
		x = 400;
		h = 450;
	}
	
	var width = screen.width;
	var height = screen.height;

	var x = (width/2)-(w/2);
	var y = (height/2)-(h/2);

	var opt = "left=" + x + ", top=" + y + ", width=" + w + ", height=" + h;
		opt = opt + ", toolbar=no,location=no,directories=no,status=no,menubar=no";
		opt = opt + ",scrollbars=N";
		opt = opt + ",resizable=N";
		
	var win = window.open(popUrl, "_blank", opt);
	
	if(win == null){
		alert("팝업차단 기능이 실행되고있습니다.\n차단 해제후 다시 실행해 주시기 바랍니다.");
		return;
	}else{
		win.focus();		
	}
}

function fnSetDate(toDate, srcDate, objId){
	srcDate = parseInt(srcDate,10);
	var start   = "";
	
	var yy       = toDate.substring(0, 4);                     //년
	var mm       = parseInt(toDate.substring(5,  7), 10) - 1;  //월
	var dd       = parseInt(toDate.substring(8, 10), 10);      //일

    var theToDay = new Date(yy, mm, dd);                       //오늘날짜

	theToDay.setDate(theToDay.getDate() + srcDate);

	start = theToDay.getFullYear() + '-' + getDate(parseInt(theToDay.getMonth()) + 1) + '-' + getDate(theToDay.getDate());
	$('#'+objId).val(start);
}

function getDate(num){
    return num < 10 ? num = '0' + num : num;
}

var fileId = "";
var fileDiv = "";
var fileSeq = "";
function delModiFile(obj){
	
	var atchflid = obj.data("atchflid");
	var atchfldiv = obj.data('atchfldiv');
	var atchflseq = obj.data('atchflseq');

	obj.closest(".MultiFile-label").remove();
	
	//fileNo set
	if(fileId != '') {
		fileId += ",";
	}
	fileId += atchflid;
	$('#atchflid').val(fileId);
	
	////////////////////////////////////////////
	if(fileDiv != '') {
		fileDiv += ",";
	}
	fileDiv += atchfldiv;
	$('#atchfldiv').val(fileDiv);
	
	//////////////////////////////////////////////
	if(fileSeq != '') {
		fileSeq += ",";
	}
	fileSeq += atchflseq;
	$('#atchflseq').val(fileSeq);
}

function PrintElem(elem)
{
	Popup(elem.html());
}
function Popup(data)
{
	var mywindow = window.open('', 'my div', 'height=400,width=600');
	mywindow.document.write('<html><head><title>my div</title>');
	mywindow.document.write('</head><body >');
	mywindow.document.write(data);
	mywindow.document.write('</body></html>');
	mywindow.document.close(); // IE >= 10에 필요
	mywindow.focus(); // necessary for IE >= 10
	mywindow.print();
	mywindow.close();
	return true;
}