<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:choose>
    <c:when test="${ERROR_TYPE == 'ScriptOnly'}">
        ${SCRIPT}
    </c:when>
    <c:otherwise>
        <!doctype html>
        <html lang="ko">
        <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <title>에러 처리 관리</title>
        <script>
        <!--
            var errType = "<c:out value="${ERROR_TYPE}"/>";
            var errMessage = "<spring:message text="${ERROR_MESSAGE}" javaScriptEscape="true"/>";
            if(errMessage != "") alert(errMessage);
            switch(errType){
                case "AlertAndBack": history.back(); break;
                case "AlertAndReload": 
                    try{
                        opener.location.href = "<c:out value="${REDIRECT_URL}"/>";
                        self.close();
                        break;
                    } catch(e){ } 
                case "AlertAndRedirect": location.href = "<c:out value="${REDIRECT_URL}"/>"; break;
                case "AlertAndClose": self.close(); break;
            }
        //-->
        </script>
        </head>
        <body>
        </body>
        </html>
    </c:otherwise>
</c:choose>