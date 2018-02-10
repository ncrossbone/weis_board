<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">

	var _initVal = "";
	$(document).ready(function(){
		setItemOff();
		$('#excelBtn').on('click', function(){
			var resultCount = $("#resultCount").val();
			
			if(resultCount >= 60000){
				alert("데이터가 많아  최신의 6만 건만 출력 됩니다.");
			}
			$("#downType").val("xls");
			searchResult();
		});
		$('#csvBtn').on('click', function(){
			var resultCount = $("#resultCount").val();
			
			if(resultCount >= 60000){
				alert("데이터가 많아  최신의 6만 건만 출력 됩니다.");
			}
			$("#downType").val("csv");
			searchResult();
		});
		$(":radio[name='siteTypeItem']").on('change', function(){
			_initVal = "N";
			setAllItemClear();
			setItemOff();
			dateListAjax($(this).val());
		});
		$('#select_area').on('change', function(){
			_initVal = "N";
			distirctListAjax($(this).val());
		});
		$('#select_water_sys').on('change', function(){
			_initVal = "N";
			waterSysListAjax($(this).val());
		});	
		
		$('#select_area_btn').on('click', function(){
			_initVal = "N";
			searchSite();
		});
		$('#select_water_sys_btn').on('click', function(){
			_initVal = "N";
			searchWaterSys();
		});
		$('#area_nm_btn').on('click', function(){
			_initVal = "N";
			searchName();
		});
		
		$('#srcBtn').on('click', function(){
			searchResult();
		});
		
		var rtnSiteTypeItem = "${param.siteTypeItem}";
		if(rtnSiteTypeItem != null && rtnSiteTypeItem != ""){
			_initVal = "Y";
			dateListAjax(rtnSiteTypeItem);
		}
		
		var rtnSelect_area = "${param.select_area}";
		if(rtnSelect_area != null && rtnSelect_area != ""){
			_initVal = "Y";
			distirctListAjax(rtnSelect_area);
		}
		
		var rtnSelect_water_sys = "${param.select_water_sys}";
		if(rtnSelect_water_sys != null && rtnSelect_water_sys != ""){
			_initVal = "Y";
			waterSysListAjax(rtnSelect_water_sys);
		}
		
		var rtnArea_nm = "${param.area_nm}";
		if(rtnArea_nm != null && rtnArea_nm != ""){
			_initVal = "Y";
			searchName();
		}
		
	});

	//모든 검색조건 초기화
	function setAllItemClear(){
		var tableHtml01 = "";
		var tableHtml02 = "";
		
		//2단계
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#second_item").find("select, input").val("");
		
		//3단계
		$("#third_item").find("select").html("<option value=''>선택</option>");
		
		//검색결과
		tableHtml01 += "<tr>";
		tableHtml01 += "   <td colspan='3'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml01 += "</tr>";
		
		tableHtml02 += "<tr class='odd3'>";
		tableHtml02 += "   <td colspan='100'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml02 += "</tr>";
		
		$("#siteTable").html(tableHtml01);
		$("#resultSiteTable").html(tableHtml02);
		$("#resultCnt").text("조회현황 : 0 건");
		$("#paging").html("");
		setItemOff();
		
	}
	
	//모든 검색조건 비활성화
	function setItemOff(){
		//3단계
		$("#third_item").find("select").attr("disabled", true);
		$("#srcBtn").css({ 'pointer-events': 'none' });
	}

	//3단계 검색조건 활성화
	function setItemOpen(){
		//3단계
		$("#third_item").find("select").attr("disabled", false);
		$("#srcBtn").css({ 'pointer-events': '' });
	}
	
	//조회기간 설정
	function dateListAjax(objVal){
		var varStartY = "";
		var varEndY = "";
		var varSelect_start_year = "${param.select_start_year}";
		var varSelect_end_year = "${param.select_end_year}";
		if(objVal == null || objVal == ""){
			return false;
		}
		
		if(objVal == "S"){						//수질
			varStartY = "2010";
			varEndY = "2016";
		}else if(objVal == "D"){				//동물플랑크톤
			varStartY = "2010";
			varEndY = "2016";
		}else if(objVal == "SM"){			//식물플랑크톤
			varStartY = "2010";
			varEndY = "2016";
		}else if(objVal == "J"){				//저서성대형무척추동물
			varStartY = "2013";
			varEndY = "2016";
		}else if(objVal == "U"){				//어류
			varStartY = "2016";
			varEndY = "2016";
		}
		
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/cms/code/dateListAjax',
			data: { startY:varStartY, endY:varEndY },
			dataType: 'json',
			success: function (data) {
				var selOpt01 = "<option value=''>선택</option>";
				var selOpt02 = "<option value=''>선택</option>";
				if(data != '' && data != null) {
					console.dir(data);
					var selected = ""; 
					data.forEach(function (item) {
						if(_initVal == "N"){
							selected = "";
						}else if(_initVal == "Y" && varSelect_start_year == item.YEAR){
							selected = "selected";
						}else{
							selected = "";
						}
						selOpt01 += "<option value="+item.YEAR+" "+selected+">"+item.YEAR+"</option>" 
						
						if(_initVal == "N"){
							selected = "";
						}else if(_initVal == "Y" && varSelect_end_year == item.YEAR){
							selected = "selected";
						}else{
							selected = "";
						}
						selOpt02 += "<option value="+item.YEAR+" "+selected+">"+item.YEAR+"</option>" 
					});
				}
				
				$("#select_start_year").html("");
				$("#select_end_year").html("");
				$("#select_start_year").append(selOpt01);
				$("#select_end_year").append(selOpt02);
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
		var varSelect_area = "${param.select_area}";
		var varSelect_area_sub = "${param.select_area_sub}";
		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");
		
		if(objVal == null || objVal == ""){
			return false;
		}
		
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/cms/code/districtListAjax',
			data: { ADM_CD:objVal, ADM_LVL:"2" },
			dataType: 'json',
			success: function (data) {
				var selOpt = "<option value=''>선택</option>";
				if(data != '' && data != null) {
					console.dir(data);
					var selected = ""; 
					data.forEach(function (item) {

						if(varSelect_area_sub == item.ADM_CD){
							selected = "selected";
						}else{
							selected = "";
						}
						
						selOpt += "<option value="+item.ADM_CD+" "+selected+">"+item.CTY_NM+"</option>" 
					});
				}
				
				$("#select_area_sub").html("");
				$("#select_area_sub").append(selOpt);

				if(_initVal == "Y"){
					if((varSelect_area != null && varSelect_area != "") || (varSelect_area_sub != null && varSelect_area_sub != "")){
						searchSite();
					}
				}
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//수계 하위목록 조회
	function waterSysListAjax(objVal){
		var varSelect_water_sys = "${param.select_water_sys}";
		var varSelect_water_sys_sub = "${param.select_water_sys_sub}";
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");	
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		
		if(objVal == null || objVal == ""){
			return false;
		}
		
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/cms/code/waterSysSubListAjax',
			data: { UPPER_CODE:objVal },
			dataType: 'json',
			success: function (data) {
				var selOpt = "<option value=''>선택</option>";
				if(data != '' && data != null) {
					console.dir(data);
					var selected = ""; 
					data.forEach(function (item) {

						if(varSelect_water_sys_sub == item.CODE){
							selected = "selected";
						}else{
							selected = "";
						}
						
						selOpt += "<option value="+item.CODE+" "+selected+">"+item.CODE_NM+"</option>" 
					});
				}
				
				$("#select_water_sys_sub").html("");
				$("#select_water_sys_sub").append(selOpt);
				
				if(_initVal == "Y"){
					if((varSelect_water_sys != null && varSelect_water_sys != "") || (varSelect_water_sys_sub != null && varSelect_water_sys_sub != "")){
						searchWaterSys();
					}
				}
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//행정구역 검색
	function searchSite(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getAdmCd = $("#select_area").val();
        var getAdmCdSub = $("#select_area_sub").val();
        var admCd = getAdmCd;
        var searchType = "1";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.")
        	$("#select_area").focus();
        	return false;
        }
        
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub
        	searchType = "2";
        }
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType, DO_TYPE:'bmSpot' };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//수계 검색
	function searchWaterSys(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getWsCd = $("#select_water_sys").val();
        var getAmCd = $("#select_water_sys_sub").val();
        var searchType = "3";
        
        if(getWsCd == null || getWsCd == ""){
        	alert("수계를 선택하세요.");
        	$("#select_water_sys").focus();
        	return false;
        }
        
        if(getAmCd != null && getAmCd != ""){
        	searchType = "4";
        }
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType, DO_TYPE:'bmSpot' };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//이름 검색
	function searchName(){
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");

		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getName = $("#area_nm").val();
        var searchType = "5";
        var param = { TYPE_CD:getSiteType, PT_NM:getName, SEARCH_TYPE:searchType, DO_TYPE:'bmSpot' };
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.");
        	$("#area_nm").focus();
        	return false;
        }
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//지역 선택 목록 조회 ajax
	function searchSiteAjax(param){
		var rtnAdm_cd = "${param.adm_cd}";
		var getParam = param;
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/contents/site/selSiteListAjax',
			data: getParam,
			dataType: 'json',
			success: function (data) {
				var selOpt = "";
				if(data != '' && data != null) {
					console.dir(data);
					var checked = "";
					data.forEach(function (item) {
						if(_initVal == "N"){
							checked = "";
						}else if(_initVal == "Y" && rtnAdm_cd.indexOf(item.SPOT_CODE) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.SPOT_CODE + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.SPOT_CODE+"</td>";
						selOpt += "   <td>"+item.SPOT_NM+"</td>";
						selOpt += "</tr>";
					});
				}else{
					selOpt += "<tr class='odd3'>";
					selOpt += "   <td colspan='3'>검색 결과가 존재하지 않습니다.</td>";
					selOpt += "</tr>";
				}
				
				$("#siteTable").html("");
				$("#siteTable").append(selOpt);
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}

	//최종결과 검색
	function searchResult(){
		var chked_val = "";
		var startYear = $("select[id=select_start_year]").val();
		var endYear = $("select[id=select_end_year]").val();
		
		var varDownType = $("#downType").val();
		
		$(":checkbox[name='chkSite']:checked").each(function(pi,po){
			chked_val += ","+po.value;
		});
		if(chked_val!="")chked_val = chked_val.substring(1);
		
		if( chked_val == null || chked_val == ""){
        	alert("측정소를 선택하세요.")
        	$("#siteTable").focus();
        	return false;
        }
		
		if( startYear == null || startYear == ""){
        	alert("시작일시년도를 선택하세요.")
        	$("#select_start_year").focus();
        	return false;
        }
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.")
        	$("#select_end_year").focus();
        	return false;
        }
		
		$("#adm_cd").val(chked_val);
		$("#start_ym").val(startYear);
		$("#end_ym").val(endYear);
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		if(getSiteType == 'S'){
			$("#mId").val("ExcelboRegion_S");
		}else if(getSiteType == 'D'){
			$("#mId").val("ExcelboRegion_D");
		}else if(getSiteType == 'SM'){
			$("#mId").val("ExcelboRegion_SM");
		}else if(getSiteType == 'J'){
			$("#mId").val("ExcelboRegion_J");
		}else if(getSiteType == 'U'){
			$("#mId").val("ExcelboRegion_U");
		}
		
		if(varDownType == "xls" || varDownType == "csv" ){
			fnSubmit("infoForm", "/weis_board/egov/cms/excel/Download");
			$("#downType").val("N");
		}else{
			$("#doSearch").val("Y");
			fnSubmit("infoForm", "/weis_board/egov/contents/site/basicBoardInfo");
		}
		
		
	}
</script>
<div id="tit">
   	<h4></h4>
	<p>4대강 조사평가 보고서와 관련된 데이터를 확인할 수 있습니다.</p>
</div>	 
<div id="inner">
   	<h5>검색조건</h5>
	<p class="info">각 단계 선택 후 검색버튼을 클릭하십시오.</p>
</div>
   
<!--검색조건-->
<form id="infoForm" name="infoForm" method="post">
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu }" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
	<input type="hidden" id="start_ym" name="start_ym" value="" />
	<input type="hidden" id="end_ym" name="end_ym" value="" />
	
	<input type="hidden" id="downType" name="downType" value="" />
	<input type="hidden" id="mId" name="mId" value =""/>
	
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 자료분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                        <input name="siteTypeItem" type="radio" id="siteType_A" title="하천수" value="S" <c:if test="${param.siteTypeItem eq 'S' or empty param.siteTypeItem}">checked</c:if>/>
	                        <label for="siteType_A">수질</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="하수종말처리장" value="D" <c:if test="${param.siteTypeItem eq 'D'}">checked</c:if>/>
	                        <label for="siteType_B">동물플랑크톤</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="산업단지폐수종말처리장" value="SM" <c:if test="${param.siteTypeItem eq 'SM'}">checked</c:if>/>
	                        <label for="siteType_D">식물플랑크톤</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_E" title="농공단지 폐수종말처리장" value="J" <c:if test="${param.siteTypeItem eq 'J'}">checked</c:if>/>
	                        <label for="siteType_E">저서대형무척추동물</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_F" title="공동방지시설" value="U" <c:if test="${param.siteTypeItem eq 'U'}">checked</c:if>/>
	                        <label for="siteType_F">어류</label>
	                    </dd>
	                </dl>
	           </div>
	       </dd>
	   	</dl>
	   	<dl>
		<dt><b>2단계</b> 지역선택</dt>
	       	<dd>
			<div class="cond">
	           	<div class="divi">
	               	<div id="second_item">
						<dl>
							<dt>행정구역</dt>
                           	<dd>
                               	<select name="select_area" id="select_area" class="W21p">
                               		<option value="">선택</option>
        							<c:forEach var="item" items="${optionList_1}" varStatus="idx">
                                   		<option value="${item.ADM_CD }" <c:if test="${param.select_area eq item.ADM_CD }">selected</c:if>>${item.DO_NM }</option>
                                   	</c:forEach>
                               	</select>
                               
                               	<select name="select_area_sub" id="select_area_sub" class="W21p">
                                   	<option>선택</option>
                               	</select>
                               	<a class="btn01" name="select_area_btn" id="select_area_btn" href="#none">검색</a>
                           	</dd>
                       	</dl>
	                       
                       	<dl>
                           	<dt>수계</dt>
                           	<dd>
                               	<select class="W21p" name="select_water_sys" id="select_water_sys">
                                   	<option value="">선택</option>
                                   	<c:forEach var="item" items="${optionList_2}" varStatus="idx">
                                   		<option value="${item.CODE }" <c:if test="${param.select_water_sys eq item.CODE }">selected</c:if>>${item.CODE_NM }</option>
                                   	</c:forEach>
                               	</select>
                               
                               	<select class="W21p" name="select_water_sys_sub" id="select_water_sys_sub">
                                   	<option>선택</option>
                               	</select>
                               	<a class="btn01" name="select_water_sys_btn" id="select_water_sys_btn" href="#none">검색</a>
                           	</dd>
                       	</dl>
	                       
                       	<dl>
                           	<dt>이름</dt>
                           	<dd>
								<input type="text" style="width:39.5% !important;" name="area_nm" id="area_nm" value="${param.area_nm }">
                               	<a class="btn01" id="area_nm_btn" href="#none">검색</a>
                           	</dd>
						</dl>
                   	</div>
	                   
                   	<div>
	                   	<div class="box">
	                       	<table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
								<caption>측정소 선택하기</caption>
								<colgroup>
									<col width="6%" />
									<col width="29%" />
									<col />
								</colgroup>
	                            <thead>
									<tr>
										<th><input class="checkAll" name="" type="checkbox" value="" /></th>
	                                    <th>측정소코드</th>
	                                    <th>측정소명</th>
									</tr>
								</thead>
	                            <tbody id="siteTable">
		                            <tr>
										<td colspan="3">검색 결과가 존재하지 않습니다.</td>
									</tr>
	                            </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</dd>
		</dl>
	   
	   	<dl>
	   	<dt><b>3단계</b> 조회기간</dt>
			<dd>
	       	<div class="cond" id="third_item">
				<dl>
					<dt>시작일시</dt>
					<dd>
						<select class="W10p L0" name="select_start_year" id="select_start_year">
							<option value="">선택</option>
							<c:forEach var="item" items="${optionList_3}" varStatus="idx">
	                       		<option value="${item.YEAR }" <c:if test="${param.select_start_year eq item.YEAR }">selected</c:if>>${item.YEAR }</option>
	                       	</c:forEach>
						</select>
						<span class="MgR8">년</span>
					</dd>
	               
					<dt class="MgL70">종료일시</dt>
					<dd>
						<select class="W10p L0" name="select_end_year" id="select_end_year">
							<option value="">선택</option>
							<c:forEach var="item" items="${optionList_3}" varStatus="idx">
	                       		<option value="${item.YEAR }" <c:if test="${param.select_end_year eq item.YEAR }">selected</c:if>>${item.YEAR }</option>
	                       	</c:forEach>
						</select>
						<span class="MgR8">년</span>
					</dd>
	               </dl>
	            </div>
	        </dd>
	    </dl>
	    <div class="MgT20 AC"><a class="btn04" name="srcBtn" id="srcBtn" href="#none" style="">검색</a></div>
	</div>	
</form>

<!--검색결과-->
<div class="divi MgT50">
	<div>
        <h5>검색결과
        	<span id="resultCnt">
	        	조회현황 : 
		        <c:if test="${empty totalCnt}">0</c:if>
		        <c:if test="${not empty totalCnt}">
		        ${totalCnt}
		        <input type="hidden" id="resultCount" value="${totalCnt}"/>
		        </c:if> 
	        	건
        	</span>
        </h5>
    </div>
    <div class="AR MgT5">
    	<a class="btn02" href="#" id="csvBtn">CSV저장</a>
        <a class="btn03" href="#" id="excelBtn">엑셀저장</a>
    </div>
</div>

<div class="result sc">
    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
        <caption>측정소 선택하기</caption>
        <c:choose>
        	<c:when test="${param.siteTypeItem eq 'S' }">
				<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>조사년도</th>
						<th>조사월</th>
						<th>조사일자</th>
						<th>조사회차</th>
						<th>채수시각</th>
						<th>날씨</th>
						<th>수온 </th>
						<th>DO(mg/L)</th>
						<th>pH</th>
						<th>전기전도도(μs/cm)</th>
						<th>BOD(mg/L)</th>
						<th>COD(mg/L)</th>
						<th>TOC(mg/L)</th>
						<th>SS(mg/L)</th>
						<th>탁도</th>
						<th>TN(mg/L)</th>
						<th>TP(mg/L)</th>
						<th>인산염인 PO4-P (mg/L)</th>
						<th>클로로필A Chl-a (㎎/㎥)</th>
						<th>질산성질소 NO3-N(mg/L)</th>
						<th>암모니아성 질소 NH3-N(mg/L)</th>
						<th>카드뮴(mg/L)</th>
						<th>납(mg/L)</th>
						<th>6가크롬</th>
						<th>비소(mg/L)</th>
						<th>수은(mg/L)</th>
						<th>총대장균(CFU/100ml)</th>
						<th>분원성대장균군(CFU/100ml)</th>
						<th>용존총질소 DTN</th>
						<th>NH4</th>
						<th>용존총인 DTP</th>
						<th>인산염</th>
						<th>비고</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="34">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.MT}</td>
								<td>${item.TME}</td>
								<td>${item.DE}</td>
								<td>${item.WTRSMPLE_TM}</td>
								<td>${item.WETHR}</td>
								<td>${item.WTRTP}</td>
								<td>${item.DOC}</td>
								<td>${item.PH}</td>
								<td>${item.EC}</td>
								<td>${item.BOD}</td>
								<td>${item.COD}</td>
								<td>${item.TOC}</td>
								<td>${item.SS}</td>
								<td>${item.TUR}</td>
								<td>${item.TN}</td>
								<td>${item.TP}</td>
								<td>${item.PO4P}</td>
								<td>${item.CHLA}</td>
								<td>${item.NO3N}</td>
								<td>${item.NH4N}</td>
								<td>${item.CDMM}</td>
								<td>${item.PB}</td>
								<td>${item.CR6}</td>
								<td>${item.ASN}</td>
								<td>${item.MRC}</td>
								<td>${item.TCOLI}</td>
								<td>${item.FCOLI}</td>
								<td>${item.DTN}</td>
								<td>${item.NH4}</td>
								<td>${item.DTP}</td>
								<td>${item.OP4}</td>
								<td>${item.RM}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>        	
        	</c:when>
        	
        	<c:when test="${param.siteTypeItem eq 'D' }">
				<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>년도</th>
						<th>월</th>
						<th>회차</th>
						<th>구분</th>
						<th>학명</th>
						<th>개체수(Ind./L)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="7">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.MT}</td>
								<td>${item.TME}</td>
								<td>${item.SE_NM}</td>
								<td>${item.SCNCENM}</td>
								<td>${item.INDVD_CO}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>        	
        	</c:when>
        	
        	<c:when test="${param.siteTypeItem eq 'SM' }">
				<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>년도</th>
						<th>월</th>
						<th>회차</th>
						<th>학명</th>
						<th>조류 구분</th>
						<th>세포수(cells/mL)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="7">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.MT}</td>
								<td>${item.TME}</td>
								<td>${item.SCNCENM}</td>
								<td>${item.SEAWEED_SE}</td>
								<td>${item.CELL_CO}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>        	
        	</c:when>
        	
        	<c:when test="${param.siteTypeItem eq 'J' }">
				<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>년도</th>
						<th>회차</th>
						<th>조사방법(정량/정성)</th>
						<th>학명</th>
						<th>국명</th>
						<th>개체수(Ind./L)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="7">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.TME}</td>
								<td>${item.EXAMIN_MTH_SE}</td>
								<td>${item.SCNCENM}</td>
								<td>${item.KORNM}</td>
								<td>${item.INDVD_CO}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>        	
        	</c:when>
        	
        	<c:when test="${param.siteTypeItem eq 'U' }">
				<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>년도</th>
						<th>회차</th>
						<th>조사방법</th>
						<th>학명</th>
						<th>국명</th>
						<th>개체수(Ind./L)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="7">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.TME}</td>
								<td>${item.COLCT_SE}</td>
								<td>${item.SCNCENM}</td>
								<td>${item.KORNM}</td>
								<td>${item.INDVD_CO}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>        	
        	</c:when>
        	
        	<c:otherwise>
        		<thead>
		            <tr>
						<th>지점명칭(코드)</th>
						<th>년도</th>
						<th>회차</th>
						<th>조사방법</th>
						<th>학명</th>
						<th>국명</th>
						<th>개체수(Ind./L)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="7">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.SPOT_NM}</td>
								<td>${item.YEAR}</td>
								<td>${item.TME}</td>
								<td>${item.COLCT_SE}</td>
								<td>${item.SCNCENM}</td>
								<td>${item.KORNM}</td>
								<td>${item.INDVD_CO}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>   
        	</c:otherwise>
        </c:choose>
    </table>
</div>
<div class="paging" id="paging">
	<%@ include file="/common/pager.jsp"%>
</div>



