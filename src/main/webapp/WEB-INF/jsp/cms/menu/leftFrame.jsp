<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var _viewOpt = "";
var _dta_code = "";
var _openMenu = "";
var _menuNm = "";

$(document).ready(function(){
	if("${param.menuNm}" != ""){
		$("#tit").find("h4").text("${param.menuNm}");	
	}else{
		$("#menuNm").val("수질 측정망");
		fnSubmit("menuForm", "/weis_board/egov/contents/site/basicBoardInfo");	
	}
	
	var openMenu = '${param.openMenu}';
	if(openMenu != ""){
		fnOpenMenu(openMenu);
	}else{
		fnOpenMenu("0");
	}
	
	fnInitParam();
	
	$('.detailBtn').on('click', function(){
		_menuNm = $(this).closest(".viewLine").find("a").text();
		$("#menuNm").val(_menuNm);
		
		var viewOpt = $(this).data("type");
		var url = "";
		
		if(viewOpt == 'look'){
			url = "/weis_board/egov/contents/site/basicBoardInfo"
		}else if(viewOpt == 'fDown'){
			url = "/weis_board/egov/contents/site/fileBoardInfo"
		}else if(viewOpt == 'iDown'){
			url = "/weis_board/egov/contents/site/imageBoardInfo"
		}else if(viewOpt == 'link'){
			window.open($(this).data("link"), '_blank');
		}
		
		fnLocalOpenMenu();
		
		if(viewOpt != "link"){
			$('#dta_code').val($(this).data("seq"));
			$('#openMenu').val(_openMenu);
			fnSubmit("menuForm", url);			
		}
	});	
	
	$('.link').on('click', function(){
		if($(this).hasClass("on")){
			window.open($(this).closest(".viewLine").find(".detailBtn").data("link"), '_blank');
		}
	});	

	$('.look, .down').on('click', function(){
		_menuNm = $(this).closest(".viewLine").find("a").text();
		$("#menuNm").val(_menuNm);
		
		if($(this).hasClass("on")){
			$('#dta_code').val($(this).closest(".viewLine").find(".detailBtn").data("seq"));
			
			fnLocalOpenMenu();
			$('#openMenu').val(_openMenu);
			if($(this).data("mode") == "look"){
				fnSubmit("menuForm", "/weis_board/egov/contents/site/basicBoardInfo");	
			}else if($(this).data("mode") == 'fDown'){
				fnSubmit("menuForm", "/weis_board/egov/contents/site/fileBoardInfo");
			}else if($(this).data("mode") == 'iDown'){
				fnSubmit("menuForm", "/weis_board/egov/contents/site/imageBoardInfo");
			}
		}
	});	
});

function fnLocalOpenMenu(){
	$('.dep3').each(function(i, e){
	    if($(this).hasClass("show")){
	    	if(_openMenu != ""){
	    		_openMenu += ",";
	    	}
	    	
	    	_openMenu += i;
	    }
	});
}

function fnInitParam(){
	_dta_code = '${param.dta_code}';
	if(_dta_code == ""){
		$('.detailBtn').eq(0).addClass("line");
	}
}
</script>
<!--왼쪽 검색조건-->
<form id="menuForm" name="menuForm" method="post">
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="" />
	<input type="hidden" id="menuNm" name="menuNm" value="" />
</form>

<div id="l_top">
    <h2>측정항목 선택</h2>
    <div class="layer_ctrl">
        <input class="ctrl_btn" id="ctrl" name="" type="checkbox" value="" />
        <label for="ctrl"><b>전체펼침</b></label>
    </div>
</div>
<div class="left_inner">
    <div class="box2">
    	<c:forEach var="item" items="${leftMenu0List}" varStatus="idx">
	    	<c:if test="${item.DTA_UPPER_CODE eq null }">
	        <div>
	            <h3>${item.DTA_NM }</h3>
	            <ul class="dep3">
		            <c:forEach var="item_mid" items="${leftMenu0List}" varStatus="idx">
	    				<c:if test="${item_mid.DTA_UPPER_CODE eq item.DTA_CODE and item_mid.DTA_LEVEL eq '2'}">
		                <li class="titArea">
		                    <em>${item_mid.DTA_NM }</em>
		                    <ul>
		                    	<c:forEach var="item_detail" items="${leftMenu0List}" varStatus="idx">
			                    	<c:if test="${item_detail.DTA_UPPER_CODE eq item_mid.DTA_CODE and item_detail.DTA_LEVEL eq '3'}">
			                        <li class="viewLine <c:if test="${param.dta_code eq item_detail.DTA_CODE}">line</c:if>">
			                            <a href="#none" class="detailBtn"
			                            	data-type="${item_detail.VIEW_TYPE }" data-seq="${item_detail.DTA_CODE }" data-link="${item_detail.LINK_ADRES }">${item_detail.DTA_NM }</a>
			                            <div class="btn_sel">
			                            	<a href="#" class="link <c:if test="${item_detail.LINK_AT eq 'Y'}">on</c:if>"></a>
			                                <a href="#" class="look <c:if test="${item_detail.SEARCH_SKLL_AT eq 'Y'}">on</c:if>" data-mode="look"></a>
			                                <c:set var="mode" value="" />
			                                <c:choose>
			                                	<c:when test="${item_detail.DOC_DWLD_AT eq 'Y' }">
			                                		<c:set var="mode" value="fDown" />
			                                	</c:when>
			                                	<c:otherwise>
			                                		<c:set var="mode" value="iDown" />
			                                	</c:otherwise>
			                                </c:choose>
			                                <a href="#" class="down <c:if test="${item_detail.DOC_DWLD_AT eq 'Y' || item_detail.IMAGE_DWLD_AT eq 'Y'}">on</c:if>" data-mode="${mode }"></a>
			                            </div>
			                        </li>
			                        </c:if>
		                        </c:forEach>
		                    </ul>
		                </li>
		                </c:if>
	                </c:forEach>
	            </ul>
	        </div>
	        </c:if>
        </c:forEach>
    </div>
</div>