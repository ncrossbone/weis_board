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
		$(":radio[name='siteTypeItem']").on('change', function(){
			setAllItemClear();
			dateListAjax($(this).val());
		});
		$('#srcBtn').on('click', function(){
			resultSearch();
		});
		if("${param.select_water_sys}" != null && "${param.select_water_sys}" != ""){
			waterSysListAjax("${param.select_water_sys}");
			searchWaterSys();
		}
		if("${param.area_nm}" != null && "${param.area_nm}" != ""){
			searchName();
		}
	});
	var doType = "SiteInfo";
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
		$("#startDate").val("");
		
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
				
				if((varSelect_water_sys != null && varSelect_water_sys != "") || (varSelect_water_sys_sub != null && varSelect_water_sys_sub != "")){
					searchWaterSys();
				}
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
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
        
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.");
        	$("#area_nm").focus();
        	return false;
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
						varCd = item.SITE_ID;
						
						if(rtnAdm_cd.indexOf(varCd) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='radio' value='" + item.SITE_ID + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.SITE_NAME+"</td>";
						selOpt += "   <td>"+item.SITE_POS+"</td>";
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
		var startYearDate = $("#startDate").val();
		var chkSite = $(":radio[name='chkSite']:checked").val();
		
		var varDownType = $("#downType").val();
		
		if( chkSite== null || chkSite== ""){
        	alert("지점를 선택하세요.");
        	return false;
        }
		
		if( startYearDate== null || startYearDate== ""){
        	alert("시작일시를 선택하세요.");
        	$("#startYear").focus();
        	return false;
        }
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		if(getSiteType == "A"){
			$("#mId").val("ExcelFiveDateY");
		}else{
			$("#mId").val("ExcelFiveDateN");
		}
		
		$("#adm_cd").val(chkSite);	
		$("#startYearDate").val(startYearDate);	
		
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
	
	<input type="hidden" id="mId" name="mId" value =""/>
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	<input type="hidden" id="target" name="target" value="${param.dta_code }" />
	<input type="hidden" id="downType" name="downType" value="" />
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
	<input type="hidden" id="startYearDate" name="startYearDate" value="" />
	
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                    	<input name="siteTypeItem" type="radio" id="siteType_A"  value="A" ${'A' eq param.siteTypeItem or empty param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_A" >확정</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B"  value="B" ${'B' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_B" >미확정</label>
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
											<th>&nbsp;</th>
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
		       	<div class="cond" id="date_item">
					<dl>
						<dt>시작일시</dt>
	                    <dd>
	                        <input name="startDate" type="text" class="W13p datepicker" id="startDate" value="${param.startYearDate}" readonly />
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
		        <c:if test="${empty totalCnt}">0</c:if>
		        <c:if test="${not empty totalCnt}">${totalCnt}
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
		<!-- 오염우려지역 결과 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>대권역</th>
						<th>측정소명</th>
						<th>측정일시</th>
						<th>수온<span>(℃)</span></th>
						<th>수소이온농도</th>
						<th>전기전도도<span>(μS/cm)</span></th>
						<th>용존산소<span>(mg/L)</span></th>
						<th>총유기탄소<span>(mg/L)</span></th>
						<th>탁도</th>
						<th>총질소<span>(mg/L)</span></th>
						<th>총인<span>(mg/L)</span></th>
						<th>암모니아성질소<span>(mg/L)</span></th>
						<th>질산성질소<span>(mg/L)</span></th>
						<th>인산염인<span>(mg/L)</span></th>
						<th>클로로필-a<span>(mg/㎥)</span></th>
						<th>벤젠<span>(μg/L)</span></th>
						<th>톨루엔<span>(μg/L)</span></th>
						<th>에틸벤젠<span>(μg/L)</span></th>
						<th>자일렌</th>
						<th>m,p-자일렌<span>(μg/L)</span></th>
						<th>o-자일렌<span>(μg/L)</span></th>
						<th>염화메틸렌<span>(μg/L)</span></th>
						<th>1.1.1-트리클로로에테인<span>(μg/L)</span></th>
						<th>사염화탄소<span>(μg/L)</span></th>
						<th>트리클로로에틸렌<span>(μg/L)</span></th>
						<th>테트라클로로에틸렌<span>(μg/L)</span></th>
						<th>카드뮴<span>(mg/L)</span></th>
						<th>납<span>(mg/L)</span></th>
						<th>구리<span>(mg/L)</span></th>
						<th>아연<span>(mg/L)</span></th>
						<th>페놀<span>(mg/L)</span></th>
						<th>임펄스<span>(pulse)</span></th>
						<th>염화메틸렌<span>(μg/L)</span></th>
						<th>1.1.1-트리클로로에테인<span>(μg/L)</span></th>
						<th>사염화탄소<span>(μg/L)</span></th>
						
						
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="52">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td><c:out value = "${item.RIVER_ID}"/></td>
								<td><c:out value = "${item.SITE_NAME}"/></td>
								<td><c:out value = "${item.MSR_DATE}"/></td>
								<td><c:out value = "${item.F02}"/></td>
								<td><c:out value = "${item.F03}"/></td>
								<td><c:out value = "${item.F04}"/></td>
								<td><c:out value = "${item.F05}"/></td>
								<td><c:out value = "${item.F06}"/></td>
								<td><c:out value = "${item.F79}"/></td>
								<td><c:out value = "${item.F27}"/></td>
								<td><c:out value = "${item.F28}"/></td>
								<td><c:out value = "${item.F36}"/></td>
								<td><c:out value = "${item.F37}"/></td>
								<td><c:out value = "${item.F35}"/></td>
								<td><c:out value = "${item.F29}"/></td>
								<td><c:out value = "${item.F14}"/></td>
								<td><c:out value = "${item.F17}"/></td>
								<td><c:out value = "${item.F19}"/></td>
								<td><c:out value = "${item.F56}"/></td>
								<td><c:out value = "${item.F20}"/></td>
								<td><c:out value = "${item.F21}"/></td>
								<td><c:out value = "${item.F22}"/></td>
								<td><c:out value = "${item.F23}"/></td>
								<td><c:out value = "${item.F24}"/></td>
								<td><c:out value = "${item.F25}"/></td>
								<td><c:out value = "${item.F26}"/></td>
								<td><c:out value = "${item.F74}"/></td>
								<td><c:out value = "${item.F75}"/></td>
								<td><c:out value = "${item.F76}"/></td>
								<td><c:out value = "${item.F77}"/></td>
								<td><c:out value = "${item.F78}"/></td>
								<td><c:out value = "${item.F07}"/></td>
								<td><c:out value = "${item.F12}"/></td>
								<td><c:out value = "${item.F13}"/></td>
								<td><c:out value = "${item.F15}"/></td>
							
								
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>


<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>
