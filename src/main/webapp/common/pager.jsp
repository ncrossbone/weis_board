<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hana" uri="/WEB-INF/tld/hana.tld"%>
<p>
<c:if test="${pager.prevPage > 0}">
    <hana:url var="pagerUrl" value="" htmlEscape="true">
        <hana:param name="page">1</hana:param>
    </hana:url>
    <a href="${pagerUrl}" class="start"><img src="/weis_board/images/comm/icon_start.gif" alt="첫 페이지" /></a>
    <hana:url var="pagerUrl" value="" htmlEscape="true">
        <hana:param name="page">${pager.prevPage}</hana:param>
    </hana:url>
    <a href="${pagerUrl}"><img src="/weis_board/images/comm/icon_pre.gif" alt="이전 페이지" /></a>
</c:if>
<c:forEach begin="${pager.startPage}" end="${pager.endPage}" var="page">
    <c:choose>
        <c:when test="${page == pager.currentPage}">
            <a class="on">${page}</a>
        </c:when>
        <c:otherwise>
            <hana:url var="pagerUrl" value="" htmlEscape="true">
            <hana:param name="page">${page}</hana:param>
            </hana:url>
            <c:if test="${page >0 }">
            <a href="${pagerUrl}" >${page}</a>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:forEach>
<c:if test="${pager.nextPage > 0}">
    <hana:url var="pagerUrl" value="" htmlEscape="true">
        <hana:param name="page">${pager.nextPage}</hana:param>
    </hana:url>
    <a href="${pagerUrl}"><img src="/weis_board/images/comm/icon_next.gif" alt="다음 페이지" /></a>
    <hana:url var="pagerUrl" value="" htmlEscape="true">
        <hana:param name="page">${pager.totalPage}</hana:param>
    </hana:url>
    <a href="${pagerUrl}"><img src="/weis_board/images/comm/icon_end.gif" alt="마지막 페이지" /></a>
</c:if>
</p>
