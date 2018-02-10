<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	var menu_id = "${param.menu_id}";
	menu_id = menu_id.substring(0,3);
	$('#gnb > li > a').removeClass("on");
	$('#gnb > li').each(function(i, e){
	    if($(this).data("menuid") == menu_id){
	    	$(this).find("a").addClass("on");
	    }
	});
	
	$('#gnb > li').click(function(e){
		location.href = $(this).data("action")+"?menu_id="+$(this).data("menuid");
	});	
	
	$('.logout').click(function(e){
		location.href = "/weis_board/egov/cms/login/loOut";
	});	
});
</script>
<div id="topmenu">
	<div class="topmenu_inner contLayout">
         <h1>로고</h1>
         <ul class="util fr">
             <li class="member">[${sessionScope.mngMb.user_name}]님 반갑습니다.</li>
            <li class="logout"><a href="#">로그아웃</a></li>
        </ul>
	</div>        
</div>
<div id="menu_wrap">
    <div id="menu" class="fullLayout">
        <ul id="gnb" class="contLayout menu_num6">
	        <c:forEach var="item" items="${topMenuList}">
	            <li data-action="${item.PROG_URL }" data-menuid="${item.MENU_ID }">
	            	<a href="#none">
	            		${item.MENU_NM }
	            	</a>
	            </li>
            </c:forEach>
        </ul>
    </div>
</div><!--menu_wrap끝-->