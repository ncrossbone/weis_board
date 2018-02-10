<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title>물환경 보 모니터링</title>
<link href="/weis_board/css/contents/BasicSet.css" rel="stylesheet" type="text/css" />
<link href="/weis_board/css/contents/comm.css" rel="stylesheet" type="text/css" />
<link href="/weis_board/css/contents/conts.css" rel="stylesheet" type="text/css" />
<link href="/weis_board/css/jquery/jquery-ui.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/weis_board/js/jquery/jquery.min.js"></script>
<script type="text/javascript"/ src="/weis_board/js/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/weis_board/js/jquery/slides.min.jquery.js"></script>
<script type="text/javascript" src="/weis_board/js/common.js"></script>
<script type="text/javascript" src="/weis_board/js/contents/ui.js"></script>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-ui-1.10.3.custom.js" ></script>
<script type="text/javascript" src="/weis_board/js/jquery/jquery.ui.datepicker.js" ></script>
<script type="text/javascript" src="/weis_board/js/component.js"></script>

<style>
/* @import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css); */
</style>

</head>
<body>
<h1 class="fullFrame">상세자료 검색</h1>
	
<div id="wrap">
	<div id="container">
    	<!--왼쪽 검색조건-->
        <div id="lnb">
        	<page:apply-decorator name="panel" page='/egov/cms/menu/leftFrame' />
        </div>

		<!--검색결과 본문내용-->
        <div id="conts">
        	<decorator:body />
        </div>
    </div>
</div>
</body>
</html>