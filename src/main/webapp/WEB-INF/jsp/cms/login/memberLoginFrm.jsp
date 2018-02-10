<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.net.URLDecoder" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title>물환경 기초자료 연계관리시스템</title>
<link href="/css/login.css" rel="stylesheet" type="text/css" />
<link href="/css/BasicSet.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weis_board/js/common.js"></script>
<script type="text/javascript" src="/weis_board/js/common/md5.js"></script>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		//LOGIN Button Click
		$('#btnLogin').click(function(){ 	
			fnLogin();
		});
		
		//비밀번호 영역 Key Event
		$('#pwd').on('keyup',function(e){
		     if( e.which == 13){
		    	 fnLogin();
		    }
		});
		
		$('#user_id').focus();
	});

	$(window).load(function() {
	<%
       	//쿠키값 가져오기
	    Cookie[] cookies = request.getCookies() ;
		
		String user_id        = "";
		String pwd            = "";
		String customer_yn    = "";
		String cookie_save_yn = "";
	
	    if(cookies != null) {
	    	
	        for(int i = 0; i < cookies.length; i++) {
	            Cookie c = cookies[i] ;

	            //저장된 쿠키 이름을 가져온다
	            String cName = c.getName();

	            if(cName.equals("cookie_save_yn")) {
	            	cookie_save_yn = c.getValue();
	            }

	            if(cName.equals("pwd")) {
	            	pwd = c.getValue();
	            }

	            if(cName.equals("user_id")) {
	            	user_id = c.getValue();
	            }
	        }
	        
	        if("Y".equals(cookie_save_yn)) {
	 %>
		  		var fom = document.frmLogin;
				fom.cookie_check.checked = true;

				fom.user_id.value = '<%=URLDecoder.decode(user_id, "euc-kr")%>';
				fom.pwd.value     = '<%=URLDecoder.decode(pwd,     "euc-kr")%>';
				
	<%	
	        }
	    }
	 %>
	});
	
	function fnLogin() {
		//ID 점검 (isNull_J > default.js에 선언)
		if(!fn_null_chk($("#user_id"), "사용자 ID는 필수 입력입니다.")) {
			return;
		}

		//Password 점검 (isNull_J > default.js에 선언)
		if(!fn_null_chk($("#pwd"), "패스워드는 필수 입력입니다.")) {
			return;
		}
		
		if($('#cookie_check').is(":checked")){
			$('#cookie_save_yn').val("Y");
		}
		
		$('#user_pw').val(hex_md5($('#pwd').val()));
// 		$('#user_pw').val($('#pwd').val());
		
		fnSubmit("frmLogin", "/weis_board/egov/cms/login/loginProc");
	}
</script>
</head>
<body>
	<div class="box">
    	<div class="title_box">
			<span>물환경 기초자료 연계관리시스템</span>
        </div>
		<form id="frmLogin" name="frmLogin" method="post">
			<input type="hidden" id="user_pw" name="user_pw" value="" />
			<input type="hidden" id="cookie_save_yn" name="cookie_save_yn" value="" />
	        <div class="login_box">
	       		<input id="user_id" name="user_id" type="text" placeholder="ID" class="id" />
	            <input id="pwd" name="pwd" type="password" placeholder="PASSWORD" class="pass MgT15" />
	            <p class="MgT10"><label class="check"><input type="checkbox" id="cookie_check" class="MgR5">Remember</label></p>
	            <a href="#none" id="btnLogin" class="login_btn">로그인</a>
	        </div>
		</form>
    </div>
</body>
</html>
   