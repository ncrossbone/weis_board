<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript">
	
	$(document).ready(function(){
	
	});

</script>	
<div>
	Hi Everyone </br>
	<c:forEach var="item" items="${memberList}" varStatus="idx">
		${item.USER_NM } </br>
	</c:forEach>
	<%@ include file="/common/pager.jsp"%>
</div>