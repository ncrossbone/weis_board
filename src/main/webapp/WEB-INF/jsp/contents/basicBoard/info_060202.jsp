<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
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

		$('#srcBtn').on('click', function(){
			searchResult();
		});
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
		var param = { ADM_CD:admCd, SEARCH_TYPE:_searchType, DO_TYPE:'v_ugrwtr_a' };
        
        searchSiteAjax(param);
        setItemOpen02();
	}
	
	//수계 검색
	function searchWaterSys(){
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
		var param = { WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:_searchType, DO_TYPE:'v_ugrwtr_a' };
        
        searchSiteAjax(param);
        setItemOpen02();
	}
	
	//이름 검색
	function searchName(){
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");

		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		
        var getName = $("#area_nm").val();
        _searchType = "5";
        var param = { ST_NM:getName, SEARCH_TYPE:_searchType, DO_TYPE:'v_ugrwtr_a' };
        
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
						if(rtnAdm_cd.indexOf(item.OBSRWL_CODE) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.OBSRWL_CODE + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.OBSRWL_NM+"</td>";
						if(item.ADDR != null){
							selOpt += "   <td>"+item.ADDR+"</td>";	
						}else{
							selOpt += "   <td></td>";
						}
// 						selOpt += "   <td>"+item.INST_NM+"</td>";
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
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
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
		
		if( startDate == null || startDate == ""){
        	alert("시작일시를 입력하세요.")
        	$("#startDate").focus();
        	return false;
        }
		
		if( endDate == null || endDate == ""){
        	alert("종료일시를 입력하세요.")
        	$("#endDate").focus();
        	return false;
        }

		$("#adm_cd").val(chked_val);
		$("#searchType").val(_searchType);

		
		
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
<form name="resultFrm" id="resultFrm" method="post">
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu }" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
	<input type="hidden" id="searchType" name="searchType" value="" />
	
	<input type="hidden" id="target" name="target" value="${param.dta_code }" />
	<input type="hidden" id="downType" name="downType" value="" />
	<input type="hidden" id="mId" name="mId" value ="Excel4WaterObserve"/>

	
	<div class="search">
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
								<input type="text" class="W38p" name="area_nm" id="area_nm" value="${param.area_nm }">
                               	<a class="btn01" id="area_nm_btn" href="javascript:searchName();">검색</a>
                           	</dd>
						</dl>
                   	</div>
	                   
                   	<div>
	                   	<div class="box">
	                       	<table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
								<caption>측정소 선택하기</caption>
								<colgroup>
									<col width="8%" />
									<col width="40%" />
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
	                        <input name="startDate" type="text" class="W13p datepicker" id="startDate" readonly value="${param.startDate }"/>
	                    </dd>
	                 
	                    <dt class="MgL50">종료일시</dt>
	                    <dd>
	                        <input name="endDate" type="text" class="W13p datepicker" id="endDate" readonly value="${param.endDate }"/>
	                    </dd>
					</dl>
	            </div>
	        </dd>
	    </dl>
		<div class="MgT20 AC"><a class="btn04" href="#none" id="srcBtn">검색</a></div>
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
				<th>관측소 명</th>
				<th>관측일시(분)</th>
				<th>지하수 수위(El.m)</th>
				<th>관측정 표고(El.m)</th>
            </tr>
		</thead>
		<tbody>
			<c:if test="${empty resultList}">
				<tr>
					<td colspan="19">검색 결과가 존재하지 않습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty resultList}">
				<c:forEach var="item" items="${resultList}" varStatus="idx">
					<tr>
						<td>${item.OBSRWL_NM }</td>
						<td>${item.OBSR_DT }</td>
						<td>${item.UGRWTR_WAL }</td>
						<td>${item.OBSRWL_AL }</td>
					</tr>
                </c:forEach>
			</c:if>
		</tbody>
    </table>
</div>

<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>


