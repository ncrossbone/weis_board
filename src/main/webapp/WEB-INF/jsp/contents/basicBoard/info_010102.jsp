<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
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
		$('#select_water_sys').on('change', function(){
			waterSysListAjax($(this).val());
		});
		$('#select_area').on('change', function(){
			distirctListAjax($(this).val());
		});
		
		$('#srcBtn').on('click', function(){
			resultSearch();
		});
		if("${param.select_area}" != null && "${param.select_area}" != ""){
			distirctListAjax("${param.select_area}");
			searchSite();
		}
		if("${param.select_water_sys}" != null && "${param.select_water_sys}" != ""){
			waterSysListAjax("${param.select_water_sys}");
			searchWaterSys();
		}
		if("${param.area_nm}" != null && "${param.area_nm}" != ""){
			searchName();
		}
	});
	var do_type = 'DepRwmpt';
	
	//행정구역 검색
	function searchSite(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getAdmCd = $("#select_area").val();
        var getAdmCdSub = $("#select_area_sub").val();
        var admCd = "";
        var searchType = "1";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.");
        	$("#select_area").focus();
        	return false;
        }
        
       	admCd = getAdmCd
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub
        	searchType = "2";
        }
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType,DO_TYPE:do_type};
        
        searchSiteAjax(param);
	}
	//이름 검색
	function searchName(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getName = $("#area_nm").val();
        var searchType = "5";
        var param = { TYPE_CD:getSiteType, ST_NM:getName, SEARCH_TYPE:searchType,DO_TYPE:do_type};
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.");
        	$("#area_nm").focus();
        	return false;
        }
        
        searchSiteAjax(param);
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
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType,DO_TYPE:do_type};
        
        searchSiteAjax(param);
        
	}

	//지역 선택 목록 조회 ajax
	function searchSiteAjax(param){
		var getParam = param;
		var rtnAdm_cd = "${param.adm_cd}";
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
						if(rtnAdm_cd.indexOf(item.ST_CD) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.ST_CD + "' class='item'"+checked+"/></td>";
						selOpt += "   <td>"+item.ST_NM+"</td>";
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
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");
		$("select[id=select_water_sys]").val("");
		
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
							selOpt += "<option value="+item.ADM_CD+" selected='selected'>"+item.CTY_NM+"</option>";
						}else{
							selOpt += "<option value="+item.ADM_CD+">"+item.CTY_NM+"</option>";
						}
					});
				}
				
				$("#select_area_sub").html("");
				$("#select_area_sub").append(selOpt);
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	//수계 하위목록 조회
	function waterSysListAjax(objVal){
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");	
		$("select[id=select_area]").val("");
		
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
							selOpt += "<option value="+item.CODE+" selected='selected'>"+item.CODE_NM+"</option>";
						}else{
							selOpt += "<option value="+item.CODE+">"+item.CODE_NM+"</option>";
						}
					});
				}
				
				$("#select_water_sys_sub").html("");
				$("#select_water_sys_sub").append(selOpt);
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//검색 결과
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
        	alert("측정소를 선택하세요.");
        	$("#siteTable").focus();
        	return false;
        }

		if( startYear == null || startYear == ""){
        	alert("시작일시년도를 선택하세요.");
        	$("#select_start_year").focus();
        	return false;
        }
		
		if( startMonth == null || startMonth == ""){
        	alert("시작일시월을 선택하세요.");
        	$("#select_start_month").focus();
        	return false;
        }
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.");
        	$("#select_end_year").focus();
        	return false;
        }
		
		if( endMonth == null || endMonth == ""){
        	alert("종료일시월을 선택하세요.");
        	$("#select_end_month").focus();
        	return false;
        }
		
		$("#adm_cd").val(chked_val);	
		$("#start_ym").val(startYM);	
		$("#end_ym").val(endYM);
		
		if(varDownType == "xls" || varDownType == "csv" ){
			fnSubmit("resultFrm", "/weis_board/egov/cms/excel/Download");
			$("#downType").val("N");
		}else{
			$("#doSearch").val("Y");
			$("#resultFrm").attr("action", "/weis_board/egov/contents/site/basicBoardInfo");
			$("#resultFrm").submit();
		}
		
	}
	
	
</script>
<div id="tit">
	<h4>보 운영현황</h4>
	<p>보 운영현황과 관련된 데이터를 확인할 수 있습니다.</p>
</div>	 
<div id="inner">
	<h5>검색조건</h5>
    <p class="info">각 단계 선택 후 검색버튼을 클릭하십시오.</p>
</div>
   

<!--검색조건-->
<form name="resultFrm" id="resultFrm">
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code}" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu}" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	<input type="hidden" id="start_ym" name="start_ym" value="" />
	<input type="hidden" id="end_ym" name="end_ym" value="" />
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
    <input type="hidden" id="mId" name="mId" value ="ExcelWaterDetail"/>
	<input type="hidden" id="downType" name="downType" value="" />
	
<div class="search">
	<dl>
	<dt><b>1단계</b> 지역선택</dt>
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
                               	<a class="btn01" name="select_area_btn" id="select_area_btn" href="javascript:searchSite();">검색</a>
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
                               	<a class="btn01" name="area_nm_btn" id="area_nm_btn" href="javascript:searchName();">검색</a>
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
       	<div class="cond" id="third_item">
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
    <div class="MgT20 AC"><a class="btn04" href="#" style="" id="srcBtn">검색</a></div>
</div>	
</form>
<!--검색결과-->
<div class="divi MgT50">
	<div>
        <h5>검색결과
        	<span id="resultCnt">
	        	조회현황 : 
		        <c:if test="${empty resultList}">0</c:if>
		        <c:if test="${not empty resultList}">
		        ${totalCnt}
		        <input type ="hidden" id="resultCount" value="${totalCnt}"/>
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

<div class="result sc ">
    <table class="st01" summary="보 운영현황으로 이루어진 표">
        <caption>보 운영현황</caption>
	
		<thead>
            <tr>
				<th>지점코드</th>
				<th>지점명</th>
				<th>측정일자</th>
				<th>위치</th>
				<th>측정시간</th>
				<th>수심<span>(m)</span></th>
				<th>측정수심<span>(m)</span></th>
				<th>수온<span>(℃)</span></th>
				<th>PH</th>
				<th>DO<span>(㎎/L)</span></th>
				<th>전기전도도<span>(㎲/㎝)</span></th>
				<th>클로로필A<span>(㎎/L)</span></th>
				

                
            </tr>
		</thead>
		<tbody id="resultSiteTable">
			<c:if test="${empty resultList}">
				<tr>
					<td colspan="12">검색 결과가 존재하지 않습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty resultList}">
				<c:forEach var="item" items="${resultList}" varStatus="idx">
					<tr>
						<td><c:out value="${item.PT_NO}"/></td>
						<td><c:out value="${item.PT_NM}"/></td>
						<td><c:out value="${item.WMYMD}"/></td>
						<td><c:out value="${item.POINT}"/></td>
						<td><c:out value="${item.WMTM}"/></td>
						<td><c:out value="${item.DEP}"/></td>
						<td><c:out value="${item.WMDEP}"/></td>
						<td><c:out value="${item.ITEM_TEMP}"/></td>
						<td><c:out value="${item.ITEM_PH}"/></td>
						<td><c:out value="${item.ITEM_DOC}"/></td>
						<td><c:out value="${item.ITEM_EC}"/></td>
						<td><c:out value="${item.ITEM_CLOA}"/></td>
					</tr>
                </c:forEach>
			</c:if>
		</tbody>
    </table>
</div>
<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>



