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
			resultSearch();
			
		});
		$('#csvBtn').on('click', function(){
			var resultCount = $("#resultCount").val();
			
			if(resultCount >= 60000){
				alert("데이터가 많아  최신의 6만 건만 출력 됩니다.");
			}
			$("#downType").val("csv");
			resultSearch();
		});
		
		$(":radio[name='siteTypeItem']").on('change', function(){
			_initVal = "N";
			setAllItemClear();
			//dateListAjax($(this).val());
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
			resultSearch();
		});
		
		if("${param.siteTypeItem}" != null && "${param.siteTypeItem}" != ""){
			_initVal = "Y";
			//dateListAjax("${param.siteTypeItem}");
		}
		if("${param.select_area}" != null && "${param.select_area}" != ""){
			_initVal = "Y";
			distirctListAjax("${param.select_area}");
		}
		if("${param.select_water_sys}" != null && "${param.select_water_sys}" != ""){
			_initVal = "Y";
			waterSysListAjax("${param.select_water_sys}");
		}
		if("${param.area_nm}" != null && "${param.area_nm}" != ""){
			_initVal = "Y";
			searchName();
		}
		
	});
	
	//모든 검색조건 초기화
	function setAllItemClear(){
		var tableHtml01 = "";
		var tableHtml02 = "";
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		//2단계
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#second_item").find("select, input").val("");
		
		//3단계
		$("#date_item").find("select").val("");
		
		//검색결과
		tableHtml01 += "<tr class='odd3'>";
		tableHtml01 += "   <td colspan='4'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml01 += "</tr>";
		
		tableHtml02 += "<tr class='odd3'>";
		tableHtml02 += "   <td colspan='5'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml02 += "</tr>";
		
		$("#siteTable").html(tableHtml01);
		$("#resultSiteTable").html(tableHtml02);
		$("#resultCnt").text("조회현황 : 0 건");
		setItemOff();
	}
	
	//검색조건 비활성화
	function setItemOff(){
		//조회기간 검색조건
		$("#date_item").find("select").attr("disabled", true);
		$("#select_start_end_btn").css({ 'pointer-events': 'none' });
	}
	
	//검색조건 활성화
	function setItemOpen(){
		//조회기간 검색조건
		$("#date_item").find("select").attr("disabled", false);
		$("#select_start_end_btn").css({ 'pointer-events': '' });
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
					data.forEach(function (item) {
						if(item.ADM_CD == '${param.select_area_sub}'){
							selOpt += "<option value="+item.ADM_CD+" selected='selected'>"+item.CTY_NM+"</option>"
						}else{
							selOpt += "<option value="+item.ADM_CD+">"+item.CTY_NM+"</option>"
						}
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

					data.forEach(function (item) {
						if(item.CODE == '${param.select_water_sys_sub}'){
							selOpt += "<option value="+item.CODE+" selected='selected'>"+item.CODE_NM+"</option>"
						}else{
							selOpt += "<option value="+item.CODE+">"+item.CODE_NM+"</option>"
						}
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
	
	//조회기간 설정
	function dateListAjax(objVal){
		var varFromNm = "";
		var varYearColNm = "";
		
		if(objVal == null || objVal == ""){
			return false;
		}
		
		if(objVal == "A"){
			varFromNm = "V_WLHR_TM";
       	}else if(objVal == "B"){
       		varFromNm = "V_FWHR_TM";
       	}
		varYearColNm = "YMDH";
		
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/cms/code/dateListAjax',
			data: { FROM_NM:varFromNm, YEAR_COL_NM:varYearColNm },
			dataType: 'json',
			success: function (data) {
				var selOpt01 = "<option value=''>선택</option>";
				var selOpt02 = "<option value=''>선택</option>";
				if(data != '' && data != null) {
					console.dir(data);

					data.forEach(function (item) {
						if(_initVal == "N"){
							selOpt01 += "<option value="+item.YEAR+">"+item.YEAR+"</option>";
						}else if(_initVal == "Y" && item.YEAR == '${param.select_start_year}'){
							selOpt01 += "<option value="+item.YEAR+" selected='selected'>"+item.YEAR+"</option>";
						}else{
							selOpt01 += "<option value="+item.YEAR+">"+item.YEAR+"</option>";
						}
						if(_initVal == "N"){
							selOpt02 += "<option value="+item.YEAR+">"+item.YEAR+"</option>";
						}else if(_initVal == "Y" && item.YEAR == '${param.select_end_year}'){
							selOpt02 += "<option value="+item.YEAR+" selected='selected'>"+item.YEAR+"</option>";
						}else{
							selOpt02 += "<option value="+item.YEAR+">"+item.YEAR+"</option>";
						}
					});
				}
				
				$("#select_start_year").html("");
				$("#select_start_year").append(selOpt01);
				$("#select_end_year").html("");
				$("#select_end_year").append(selOpt02);
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
        var admCd = "";
        var searchType = "1";
        var doType = "";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.")
        	$("#select_area").focus();
        	return false;
        }
        
       	admCd = getAdmCd
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub
        	searchType = "2";
        }
       	
       	if(getSiteType == "A"){
       		doType = "wlobsif";
       	}else if(getSiteType == "B"){
       		doType = "fwobsif";
       	}
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//수계 검색
	function searchWaterSys(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getWsCd = $("#select_water_sys").val();
        var getAmCd = $("#select_water_sys_sub").val();
        var searchType = "3";
        var doType = "";
        
        if(getWsCd == null || getWsCd == ""){
        	alert("수계를 선택하세요.")
        	$("#select_water_sys").focus();
        	return false;
        }
        
        if(getAmCd != null && getAmCd != ""){
        	searchType = "4";
        }
        
        if(getSiteType == "A"){
       		doType = "wlobsif";
       	}else if(getSiteType == "B"){
       		doType = "fwobsif";
       	}
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
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
        var doType = "";
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.")
        	$("#area_nm").focus();
        	return false;
        }
        
        if(getSiteType == "A"){
       		doType = "wlobsif";
       	}else if(getSiteType == "B"){
       		doType = "fwobsif";
       	}
        
        var param = { TYPE_CD:getSiteType, ST_NM:getName, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//지역 선택 목록 조회 ajax
	function searchSiteAjax(param){
		var rtnAdm_cd = "${param.adm_cd}";
		var varSiteTypeItem = $(":radio[name='siteTypeItem']:checked").val();
		var getParam = param;
		var varCd = "";
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
						if(varSiteTypeItem == "A"){
							varCd = item.WLOBSCD;
						}else if(varSiteTypeItem == "B"){
							varCd = item.FWOBSCD;
						}
						
						if(_initVal == "N"){
							checked = "";
						}else if(_initVal == "Y" && rtnAdm_cd.indexOf(varCd) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + varCd + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.OBSNM+"</td>";
						selOpt += "   <td>"+item.ADDR+"</td>";
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
	
	function resultSearch(){
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
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		if(getSiteType == 'A'){
			$("#mId").val("ExcelwaterLvl");
		}else if(getSiteType == 'B'){
			$("#mId").val("Exceldischarge");
		}
		
		if(varDownType == "xls" || varDownType == "csv" ){
			fnSubmit("resultFrm", "/weis_board/egov/cms/excel/Download");
			$("#downType").val("N");
		}else{
			$("#doSearch").val("Y");
			fnSubmit("resultFrm", "/weis_board/egov/contents/site/basicBoardInfo");
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
<form name="resultFrm" id="resultFrm">
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu }" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	<input type="hidden" id="target" name="target" value="${param.dta_code }" />
	<input type="hidden" id="downType" name="downType" value="" />
	<input type="hidden" id="start_ym" name="start_ym" value="" />
	<input type="hidden" id="end_ym" name="end_ym" value="" />
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
	<input type="hidden" id="mId" name="mId" value ="ExcelBoSite"/>
	
	
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 자료분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                    	<input name="siteTypeItem" type="radio" id="siteType_A" title="수위" value="A" ${'A' eq param.siteTypeItem or empty param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_A" >수위</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="유량" value="B" ${'B' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_B" >유량</label>
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
		                                   		<option value="${item.ADM_CD }" ${item.ADM_CD eq param.select_area ? 'selected="selected"' : ''}>${item.DO_NM }</option>
		                                   	</c:forEach>
		                               	</select>
		                               
		                               	<select name="select_area_sub" id="select_area_sub" class="W21p">
		                                   	<option>선택</option>
		                               	</select>
		                               	<a class="btn01" name="select_area_btn" id="select_area_btn" href="#">검색</a>
		                           	</dd>
		                       	</dl>
		                       
		                       	<dl>
		                           	<dt>수계</dt>
		                           	<dd>
		                               	<select class="W21p" name="select_water_sys" id="select_water_sys">
		                                   	<option value="">선택</option>
		                                   	<c:forEach var="item" items="${optionList_2}" varStatus="idx">
		                                   		<option value="${item.CODE }" ${item.CODE eq param.select_water_sys ? 'selected="selected"' : ''}>${item.CODE_NM }</option>
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
										<label style="display: none;">지역명</label>
										<input type="text" style="width:39.5% !important;" name="area_nm"  id="area_nm"  value="${param.area_nm}">
		                               	<a class="btn01" name="area_nm_btn" id="area_nm_btn" href="#">검색</a>
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
	   		<dt><b>2단계</b> 조회기간</dt>
			<dd>
		       	<div class="cond" id="date_item">
					<dl>
						<dt>시작일시</dt>
						<dd>
							<select class="W10p L0" name="select_start_year" id="select_start_year">
								<option value="">선택</option>
								<c:forEach var="item" items="${optionList_3}" varStatus="idx">
		                       		<option value="${item.YEAR }" ${item.YEAR eq param.select_start_year ? 'selected="selected"' : ''}>${item.YEAR }</option>
		                       	</c:forEach>
							</select>
							<span class="MgR8">년</span>
							<select class="W8p L0" name="select_start_month" id="select_start_month">
								<option value="">선택</option>
								<option value="01" ${'01' eq param.select_start_month ? 'selected="selected"' : ''}>01</option>
								<option value="02" ${'02' eq param.select_start_month ? 'selected="selected"' : ''}>02</option>
								<option value="03" ${'03' eq param.select_start_month ? 'selected="selected"' : ''}>03</option>
								<option value="04" ${'04' eq param.select_start_month ? 'selected="selected"' : ''}>04</option>
								<option value="05" ${'05' eq param.select_start_month ? 'selected="selected"' : ''}>05</option>
								<option value="06" ${'06' eq param.select_start_month ? 'selected="selected"' : ''}>06</option>
								<option value="07" ${'07' eq param.select_start_month ? 'selected="selected"' : ''}>07</option>
								<option value="08" ${'08' eq param.select_start_month ? 'selected="selected"' : ''}>08</option>
								<option value="09" ${'09' eq param.select_start_month ? 'selected="selected"' : ''}>09</option>
								<option value="10" ${'10' eq param.select_start_month ? 'selected="selected"' : ''}>10</option>
								<option value="11" ${'11' eq param.select_start_month ? 'selected="selected"' : ''}>11</option>
								<option value="12" ${'12' eq param.select_start_month ? 'selected="selected"' : ''}>12</option>
							</select>
		                       <span>월</span>
						</dd>
		               
						<dt class="MgL70">종료일시</dt>
						<dd>
							<select class="W10p L0" name="select_end_year" id="select_end_year">
								<option value="">선택</option>
								<c:forEach var="item" items="${optionList_3}" varStatus="idx">
		                       		<option value="${item.YEAR }" ${item.YEAR eq param.select_end_year ? 'selected="selected"' : ''}>${item.YEAR }</option>
		                       	</c:forEach>
							</select>
							<span class="MgR8">년</span>
							<select class="W8p L0" name="select_end_month" id="select_end_month">
								<option value="">선택</option>
								<option value="01" ${'01' eq param.select_end_month ? 'selected="selected"' : ''}>01</option>
								<option value="02" ${'02' eq param.select_end_month ? 'selected="selected"' : ''}>02</option>
								<option value="03" ${'03' eq param.select_end_month ? 'selected="selected"' : ''}>03</option>
								<option value="04" ${'04' eq param.select_end_month ? 'selected="selected"' : ''}>04</option>
								<option value="05" ${'05' eq param.select_end_month ? 'selected="selected"' : ''}>05</option>
								<option value="06" ${'06' eq param.select_end_month ? 'selected="selected"' : ''}>06</option>
								<option value="07" ${'07' eq param.select_end_month ? 'selected="selected"' : ''}>07</option>
								<option value="08" ${'08' eq param.select_end_month ? 'selected="selected"' : ''}>08</option>
								<option value="09" ${'09' eq param.select_end_month ? 'selected="selected"' : ''}>09</option>
								<option value="10" ${'10' eq param.select_end_month ? 'selected="selected"' : ''}>10</option>
								<option value="11" ${'11' eq param.select_end_month ? 'selected="selected"' : ''}>11</option>
								<option value="12" ${'12' eq param.select_end_month ? 'selected="selected"' : ''}>12</option>
							</select>
							<span>월</span>
						</dd>
					</dl>
	            </div>
	        </dd>
	    </dl>
		<div class="MgT20 AC"><a class="btn04" href="#none" style="" id="srcBtn">검색</a></div>
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

<c:choose>
	<c:when test="${empty param.siteTypeItem or param.siteTypeItem eq 'A'}">
		<!-- 오염우려지역 결과 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>지점코드</th>
						<th>지점명</th>
						<th>관측일시(시)</th>
						<th>수위(mm)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="5">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${(totalCnt + 1 ) - item.RNUM }</td>
								<td>${item.WLOBSCD }</td>
								<td>${item.OBSNM }</td>
								<td>${item.YMDH }</td>
								<td>${item.WL }</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'B'}">
		<!-- 일반지역 결과 목록 -->
		<div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>지점코드</th>
						<th>지점명</th>
						<th>관측일시(시)</th>
						<th>유량(CMS)</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="5">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${(totalCnt + 1 ) - item.RNUM }</td>
								<td>${item.FWOBSCD }</td>
								<td>${item.OBSNM }</td>
								<td>${item.YMDH }</td>
								<td>${item.FW }</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
</c:choose>

<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>
