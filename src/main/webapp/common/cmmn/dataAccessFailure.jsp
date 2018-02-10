<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
 /**
  * Class Name : dataAccessFailure.jsp
  * Description : DataAccessException 화면
  * Modification Information
  * 
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *  
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title>물환경 보 모니터링 : ERROR</title>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="100%" height="100%" align="center" valign="middle" style="padding-top:150px;"><table border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<%-- <td class=" ">3<span style="font-family:Tahoma; font-weight:bold; color:#000000; line-height:150%; width:440px; height:70px;"><c:out value="${exception.message}"/></span></td> --%>
		<td><img src="/cms/_images/error.gif" alt="죄송합니다. 요청하신 페이지를 찾을 수 없습니다."/></td>
	  </tr>
	</table></td>
  </tr>
</table><%-- ${exception.message} --%>
</body>
</html>