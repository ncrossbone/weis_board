<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">

	var _searchType = "";
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

		
		$('#select_area').on('change', function(){
			distirctListAjax($(this).val());
		});
		$('#select_water_sys').on('change', function(){
			waterSysListAjax($(this).val());
		});	
		
		var rtnSelect_area = "${param.select_area}";
		if(rtnSelect_area != ""){
			distirctListAjax(rtnSelect_area);
		}
		
		var rtnSelect_water_sys = "${param.select_water_sys}";
		if(rtnSelect_water_sys != ""){
			waterSysListAjax(rtnSelect_water_sys);
		}
		
		var rntSearchType = parseInt("${param.searchType}",10); 
		if(rntSearchType == 5 ){
			searchName();
		}
	});

	//모든 검색조건 비활성화
	function setItemOff(){
		//3단계
		$("#third_item").find("select").attr("disabled", true);
		$("#select_start_end_btn").css({ 'pointer-events': 'none' });
	}

	//3단계 검색조건 활성화
	function setItemOpen02(){
		//3단계
		$("#third_item").find("select").attr("disabled", false);
		$("#select_start_end_btn").css({ 'pointer-events': '' });
	}
	
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
		var varSelect_area_sub = "${param.select_area_sub}";
		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		
		if('${param.area_nm}' == ""){
			$("#area_nm").val("");	
		}
		
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

				var rntSearchType = parseInt("${param.searchType}",10); 
				if(rntSearchType < 3){
					searchSite();
				}
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//수계 하위목록 조회
	function waterSysListAjax(objVal){
		var varSelect_water_sys_sub = "${param.select_water_sys_sub}";
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");
		
		if('${param.area_nm}' == ""){
			$("#area_nm").val("");	
		}
		
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
				
				var rntSearchType = parseInt("${param.searchType}",10); 
				if(rntSearchType > 2 && rntSearchType < 5 ){
					searchWaterSys();
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
        _searchType = "1";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.")
        	$("#select_area").focus();
        	return false;
        }
        
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub
        	_searchType = "2";
        }
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:_searchType, DO_TYPE:'tms' };
        
        searchSiteAjax(param);
        setItemOpen02();
	}
	
	//수계 검색
	function searchWaterSys(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getWsCd = $("#select_water_sys").val();
        var getAmCd = $("#select_water_sys_sub").val();
        _searchType = "3";
        
        if(getWsCd == null || getWsCd == ""){
        	alert("수계를 선택하세요.");
        	$("#select_water_sys").focus();
        	return false;
        }
        
        if(getAmCd != null && getAmCd != ""){
        	_searchType = "4";
        }
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:_searchType, DO_TYPE:'tms' };
        
        searchSiteAjax(param);
        setItemOpen02();
	}
	
	//이름 검색
	function searchName(){
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");

		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getName = $("#area_nm").val();
        _searchType = "5";
        var param = { TYPE_CD:getSiteType, PT_NM:getName, SEARCH_TYPE:_searchType, DO_TYPE:'tms' };
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.");
        	$("#area_nm").focus();
        	return false;
        }
        
        searchSiteAjax(param);
        setItemOpen02();
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
						if(rtnAdm_cd.indexOf(item.FACT_CODE) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.FACT_CODE + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.FACT_FNAME+"</td>";
						selOpt += "   <td>"+item.FACT_ADDR+"</td>";
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
		var startMonth = $("select[id=select_start_month]").val();
		var endYear = $("select[id=select_end_year]").val();
		var endMonth = $("select[id=select_end_month]").val();
		
		var startYM = startYear + startMonth;
		var endYM = endYear + endMonth;
		
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
		
		if( startMonth == null || startMonth == ""){
        	alert("시작일시월을 선택하세요.")
        	$("#select_start_month").focus();
        	return false;
        }
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.")
        	$("#select_end_year").focus();
        	return false;
        }
		
		if( endMonth == null || endMonth == ""){
        	alert("종료일시월을 선택하세요.")
        	$("#select_end_month").focus();
        	return false;
        }
		
		$("#adm_cd").val(chked_val);
		$("#start_ym").val(startYM);
		$("#end_ym").val(endYM);
		$("#searchType").val(_searchType);

		
		

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
	<input type="hidden" id="searchType" name="searchType" value="" />

	<input type="hidden" id="downType" name="downType" value="" />
	<input type="hidden" id="mId" name="mId" value ="ExcelTms"/>
	
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 자료분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                        <input name="siteTypeItem" type="radio" id="siteType_A" title="하천수" value="ALL" checked="checked"/>
	                        <label for="siteType_A" >전체</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="하수종말처리장" value="A" <c:if test="${param.siteTypeItem eq 'A'}">checked</c:if>/>
	                        <label for="siteType_B" >하수종말처리장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="산업단지폐수종말처리장" value="C" <c:if test="${param.siteTypeItem eq 'C'}">checked</c:if>/>
	                        <label for="siteType_D" >산업단지폐수종말처리장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_E" title="농공단지 폐수종말처리장" value="D" <c:if test="${param.siteTypeItem eq 'D'}">checked</c:if>/>
	                        <label for="siteType_E" >농공단지 폐수종말처리장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_F" title="공동방지시설" value="E" <c:if test="${param.siteTypeItem eq 'E'}">checked</c:if>/>
	                        <label for="siteType_F" >공동방지시설</label>
	                    </dd>
	                </dl>
					<dl class="rad">
	                    <dd style="padding-left:45px;padding-right:45px;margin-right:33px;">&nbsp;</dd>
	                    <dd>
	                        <input name="siteTypeItem" type="radio" id="siteType_A" title="1종배출사업장" value="F" <c:if test="${param.siteTypeItem eq 'F'}">checked</c:if>/>
	                        <label for="siteType_A" >1종배출사업장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="2종배출사업장" value="G" <c:if test="${param.siteTypeItem eq 'G'}">checked</c:if>/>
	                        <label for="siteType_B" >2종배출사업장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="3종배출사업장" value="H" <c:if test="${param.siteTypeItem eq 'H'}">checked</c:if>/>
	                        <label for="siteType_D" >3종배출사업장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_E" title="4종배출사업장" value="I" <c:if test="${param.siteTypeItem eq 'I'}">checked</c:if>/>
	                        <label for="siteType_E" >4종배출사업장</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_F" title="5종배출사업장" value="J" <c:if test="${param.siteTypeItem eq 'J'}">checked</c:if>/>
	                        <label for="siteType_F" >5종배출사업장</label>
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
                               	<a class="btn01" name="select_area_btn" id="select_area_btn" href="javascript:searchSite();">검색</a>
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
                               	<a class="btn01" name="select_water_sys_btn" id="select_water_sys_btn" href="javascript:searchWaterSys();">검색</a>
                           	</dd>
                       	</dl>
	                       
                       	<dl>
                           	<dt>이름</dt>
                           	<dd>
								<input type="text" style="width:39.5% !important;" name="area_nm" id="area_nm" value="${param.area_nm }">
                               	<a class="btn01" id="area_nm_btn" href="javascript:searchName();">검색</a>
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
	                                    <th>측정소명</th>
	                                    <th>주소</th>
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
						<select class="W8p L0" name="select_start_month" id="select_start_month">
							<option value="">선택</option>
							<option value="01" <c:if test="${param.select_start_month eq '01'}">selected</c:if>>01</option>
							<option value="02" <c:if test="${param.select_start_month eq '02'}">selected</c:if>>02</option>
							<option value="03" <c:if test="${param.select_start_month eq '03'}">selected</c:if>>03</option>
							<option value="04" <c:if test="${param.select_start_month eq '04'}">selected</c:if>>04</option>
							<option value="05" <c:if test="${param.select_start_month eq '05'}">selected</c:if>>05</option>
							<option value="06" <c:if test="${param.select_start_month eq '06'}">selected</c:if>>06</option>
							<option value="07" <c:if test="${param.select_start_month eq '07'}">selected</c:if>>07</option>
							<option value="08" <c:if test="${param.select_start_month eq '08'}">selected</c:if>>08</option>
							<option value="09" <c:if test="${param.select_start_month eq '09'}">selected</c:if>>09</option>
							<option value="10" <c:if test="${param.select_start_month eq '10'}">selected</c:if>>10</option>
							<option value="11" <c:if test="${param.select_start_month eq '11'}">selected</c:if>>11</option>
							<option value="12" <c:if test="${param.select_start_month eq '12'}">selected</c:if>>12</option>
						</select>
	                       <span>월</span>
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
						<select class="W8p L0" name="select_end_month" id="select_end_month">
							<option value="">선택</option>
							<option value="01" <c:if test="${param.select_end_month eq '01'}">selected</c:if>>01</option>
							<option value="02" <c:if test="${param.select_end_month eq '02'}">selected</c:if>>02</option>
							<option value="03" <c:if test="${param.select_end_month eq '03'}">selected</c:if>>03</option>
							<option value="04" <c:if test="${param.select_end_month eq '04'}">selected</c:if>>04</option>
							<option value="05" <c:if test="${param.select_end_month eq '05'}">selected</c:if>>05</option>
							<option value="06" <c:if test="${param.select_end_month eq '06'}">selected</c:if>>06</option>
							<option value="07" <c:if test="${param.select_end_month eq '07'}">selected</c:if>>07</option>
							<option value="08" <c:if test="${param.select_end_month eq '08'}">selected</c:if>>08</option>
							<option value="09" <c:if test="${param.select_end_month eq '09'}">selected</c:if>>09</option>
							<option value="10" <c:if test="${param.select_end_month eq '10'}">selected</c:if>>10</option>
							<option value="11" <c:if test="${param.select_end_month eq '11'}">selected</c:if>>11</option>
							<option value="12" <c:if test="${param.select_end_month eq '12'}">selected</c:if>>12</option>
						</select>
						<span>월</span>
					</dd>
	               </dl>
	            </div>
	        </dd>
	    </dl>
	    <div class="MgT20 AC"><a class="btn04" name="select_start_end_btn" id="select_start_end_btn" href="javascript:searchResult();" style="">검색</a></div>
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
		<thead>
            <tr>
				<th>사업장코드</th>
				<th>사업장명</th>
				<th>시설구분</th>
				<th>처리용량</th>
				<th>방류구 번호</th>
				<th>측정일자</th>
				<th>BOD<span>(㎎/L)</span> </th>
				<th>COD<span>(㎎/L)</span></th>
				<th>SS<span>(㎎/L)</span></th>
				<th>T-N<span>(㎎/L)</span></th>
				<th>T-P<span>(㎎/L)</span></th>
				<th>pH</th>
				<th>적산유량(평균)<span>(㎥/Hour)</span></th>
				<th>총유기탄소<span>(㎎/L)</span></th>
				
            </tr>
		</thead>
		<tbody>
			<c:if test="${empty resultList}">
				<tr>
					<td colspan="14">검색 결과가 존재하지 않습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty resultList}">
				<c:forEach var="item" items="${resultList}" varStatus="idx">
					<tr>
						<td>${item.FACT_CODE}</td>
						<td>${item.FACT_FNAME}</td>
						<td>${item.FACT_KIND_NAME}</td>
						<td>${item.FACT_CAPACITY}</td>
						<td>${item.WAST_NO}</td>
						<td>${item.BASE_TIME}</td>
						<td>${item.BOD}</td>
						<td>${item.COD}</td>
						<td>${item.SUS}</td>
						<td>${item.TON}</td>
						<td>${item.TOP}</td>
						<td>${item.PHY}</td>
						<td>${item.FLW}</td>
						<td>${item.TOC}</td>
					</tr>
                </c:forEach>
			</c:if>
		</tbody>
    </table>
</div>
<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>



