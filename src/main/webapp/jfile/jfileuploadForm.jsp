<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.ibizsoftware.com/tags/jsp" prefix="jwork"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<input type="hidden" id="totalFileSize" value="0" />
<div id="swfupload-control" class="swfupload-control mt8">	
	<div class="middle">
		<div class="title">
			<div class="title_left" style="font-weight: bold;">첨부파일</div>
			<div class="title_right">
				<div class="content">
				<table width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" class="board_view3">
					<col width="50%"/><col width="25%"/><col width="25%"/>
					<tr>
					    <td style="text-align: center;">파일총 사이즈 : <span id="totalFileSizeDisplay">0 Byte</span></td>
					    <td style="text-align: right;"><span id="spanButtonAllDown" class="allDown"><button onclick="return false;" /></span></td>
					    <td style="text-align: right;"><span ><a href="" class="btn01" onclick="send1();return false;">업 로 드</a></span></td>
					</tr>
				</table>
				</div>	
			</div>
		</div>
		<div>
			<div class="content">
				<div class="content_left">
					<span id="spanButtonPlaceHolder" class="swfupload"></span>
					<span id="spanThumbnail" class="thumbnail"><img src="${ctx}/jfile/resources/swfupload/images/preview_img.gif" /></span> 		
				</div>
				<div class="content_right">
					<div style="width: 100%">
					<table id="fileAreaTable" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;" class="board_view2">
						<col width="*"/><col width="12%"/><col width="30%"/><col width="10%"/><col width="10%"/>
						<tr>
							<th class="top" style="background-image: url('${ctx}/jfile/resources/swfupload/images/text-bg.gif');background-repeat: repeat-x;">파일명</th>
							<th class="top" style="background-image: url('${ctx}/jfile/resources/swfupload/images/text-bg.gif');background-repeat: repeat-x;">사이즈</th>
							<th class="top" style="background-image: url('${ctx}/jfile/resources/swfupload/images/text-bg.gif');background-repeat: repeat-x;">업로드 진행바</th>
							<th class="top" style="background-image: url('${ctx}/jfile/resources/swfupload/images/text-bg.gif');background-repeat: repeat-x;">다운로드</th>
							<th class="topend" style="background-image: url('${ctx}/jfile/resources/swfupload/images/text-bg.gif');background-repeat: repeat-x;">삭제</th>
						</tr>
						<tr id="noDataRow">
							<td class="all_txt" colspan="5" style="text-align: center;">첨부된 파일이 없습니다.</td>
						</tr>
					</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
