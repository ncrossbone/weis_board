<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	
	var _initVal = "";
	$(document).ready(function(){
		setItemOff();
		$(":radio[name='siteTypeItem']").on('change', function(){
			setAllItemClear();
			setItemOff();
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
		$('#srcBtn').on('click', function(){
			var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
			resultSearch();
		});
		
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
		
		if("${param.siteTypeItemSub}" != null && "${param.siteTypeItemSub}" != ""){
			setTabItem();
		}
		
	});
	
	//모든 검색조건 초기화
	function setAllItemClear(){
		var tableHtml01 = "";
		var tableHtml02 = "";
		
		//2단계
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#second_item01").find("select, input").val("");
		$("#area_item").find("select, input").val("");
		
		//3단계
		$("#date_item").find("select").val("");
		
		//검색결과
		tableHtml01 += "<tr>";
		tableHtml01 += "   <td colspan='5'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml01 += "</tr>";
		
		tableHtml02 += "<tr class='odd3'>";
		tableHtml02 += "   <td colspan='100'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml02 += "</tr>";
		
		$("#siteTable").html(tableHtml01);
		$("#resultSiteTable").html(tableHtml02);
		$("#resultCnt").text("조회현황 : 0 건");
		$("#divTab").html("");
		$("#paging").html("");
		
	}
	
	//검색조건 비활성화
	function setItemOff(){
		//3단계
		$("#date_item").find("select").attr("disabled", true);
	}
	
	//검색조건 활성화
	function setItemOpen(){
		//3단계
		$("#date_item").find("select").attr("disabled", false);
	}
	
	//탭 설정
	function setTabItem(){
		$("#atalTab01").attr("class", "");
		$("#atalTab02").attr("class", "");
		$("#fishTab01").attr("class", "");
		$("#fishTab02").attr("class", "");
		$("#bemaTab01").attr("class", "");
		$("#bemaTab02").attr("class", "");
		$("#vtnTab01").attr("class", "");
		$("#vtnTab02").attr("class", "");
		
		var varSiteTypeItem = "${param.siteTypeItem}";
		var varSiteTypeItemSub = "${param.siteTypeItemSub}";

		if(varSiteTypeItem == "A"){
			if(varSiteTypeItemSub == "A"){
				$("#atalTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#atalTab02").attr("class", "on");
			}
		}else if(varSiteTypeItem == "B"){
			if(varSiteTypeItemSub == "A"){
				$("#fishTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#fishTab02").attr("class", "on");
			}
		}else if(varSiteTypeItem == "C"){
			if(varSiteTypeItemSub == "A"){
				$("#bemaTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#bemaTab02").attr("class", "on");
			}
		}else if(varSiteTypeItem == "D"){
			if(varSiteTypeItemSub == "A"){
				$("#vtnTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#vtnTab02").attr("class", "on");
			}
		}
	}
	
	//탭 선택
	function selTabItem(divId){
		$("#siteTypeItemSub").val(divId);
		resultSearch();
	}
	
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
		var varSelect_area = "${param.select_area}";
		var varSelect_area_sub = "${param.select_area_sub}";
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("select[id=select_water_sys]").val("");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");
		
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
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("select[id=select_area]").val("");
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#area_nm").val("");	
		
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
        var admCd = "";
        var searchType = "1";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.");
        	$("#select_area").focus();
        	return false;
        }
        
       	admCd = getAdmCd;
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub;
        	searchType = "2";
        }
        
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType, DO_TYPE:'aemes' };
        
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
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType, DO_TYPE:'aemes' };
        
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
        var param = { TYPE_CD:getSiteType, ST_NM:getName, SEARCH_TYPE:searchType, DO_TYPE:'aemes' };
        
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
						
						selOpt += "<tr>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.SPOT_CODE + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.SPOT_NM+"</td>";
						selOpt += "   <td>"+item.EST_SE_NM+"</td>";
						selOpt += "   <td>"+item.SAREA_NM+"</td>";
						selOpt += "   <td>"+item.DETAIL_WRSSM_NM+"</td>";
						selOpt += "</tr>";
					});
				}else{
					selOpt += "<tr>";
					selOpt += "   <td colspan='5'>검색 결과가 존재하지 않습니다.</td>";
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
	
	//검색결과 다운로드
	function docDown(){
		var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
		var varSiteTypeItemSub = "${param.siteTypeItemSub}";
		
		if(varSiteType == 'A'){
			//부착돌말류
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemesAtalAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemesAtalSpcsList");
			}
		}else if(varSiteType == 'B'){
			//어류
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemesFishAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemesFishSpcsList");
			}
		}else if(varSiteType == 'C'){
			//저서성대형무척추동물
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemesBemaAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemesBemaSpcsList");
			}
		}else if(varSiteType == 'D'){
			//식생
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemesVtnAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemesVtnSpcsList");
			}
		}
		
		fnSubmit("resultFrm", "/weis_board/egov/cms/excel/Download");
		$("#downType").val("N");
	}
	
	function resultSearch(){
		var chked_val = "";
		var startYear = $("select[id=select_start_year]").val();
		var startTme = $("select[id=select_start_tme]").val();
		var endYear = $("select[id=select_end_year]").val();
		var endTme = $("select[id=select_end_tme]").val();
		
		var startDate = startYear + startTme;
		var endDate = endYear + endTme;
		
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
		
		if( startTme == null || startTme == ""){
        	alert("시작회차를 선택하세요.");
        	$("#select_start_month").focus();
        	return false;
        }
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.");
        	$("#select_end_year").focus();
        	return false;
        }
		
		if( endTme == null || endTme == ""){
        	alert("종료회차를 선택하세요.");
        	$("#select_end_month").focus();
        	return false;
        }
		
		$("#adm_cd").val(chked_val);	
		$("#startDate").val(startDate);	
		$("#endDate").val(endDate);
		
		if(varDownType == "xls" || varDownType == "csv" ){
			docDown();
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
	<input type="hidden" id="startDate" name="startDate" value="" />
	<input type="hidden" id="endDate" name="endDate" value="" />
	<input type="hidden" id="adm_cd" name="adm_cd" value="" />
	<input type="hidden" id="siteTypeItemSub" name="siteTypeItemSub" value="${param.siteTypeItemSub }" />
	<input type="hidden" id="mId" name="mId" value =""/>
	<input type="hidden" id="resultCount" value="${totalCnt}"/>
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 자료분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                    	<input name="siteTypeItem" type="radio" id="siteType_A" title="부착돌말류" value="A" ${'A' eq param.siteTypeItem or empty param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_A" >부착돌말류</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="어류" value="B" ${'B' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_B" >어류</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_C" title="저서성대형무척추동물" value="C" ${'C' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_C" >저서성대형무척추동물</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="식생" value="D" ${'D' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_D" >식생</label>
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
		               	<div id="area_item">
							<dl>
								<dt>행정구역</dt>
	                           	<dd>
	                               	<select class="W21p" name="select_area" id="select_area">
	                               		<option value="">선택</option>
	        							<c:forEach var="item" items="${optionList_1}" varStatus="idx">
	                                   		<option value="${item.ADM_CD }" ${item.ADM_CD eq param.select_area ? 'selected="selected"' : ''}>${item.DO_NM }</option>
	                                   	</c:forEach>
	                               	</select>
	                               
	                               	<select class="W21p" name="select_area_sub" id="select_area_sub">
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
	                               	<a class="btn01" name="select_water_sys_btn" id="select_water_sys_btn" href="#">검색</a>
	                           	</dd>
	                       	</dl>
		                       
	                       	<dl>
	                           	<dt>이름</dt>
	                           	<dd>
									<label style="display: none;">지역명</label>
									<input type="text" style="width:39.5% !important;" class="W38p" name="area_nm"  id="area_nm"  value="${param.area_nm}">
	                               	<a class="btn01" name="area_nm_btn" id="area_nm_btn" href="#">검색</a>
	                           	</dd>
							</dl>
	                   	</div>
		                   
	                   	<div>
		                   	<div class="box">
		                       	<table class="st01" summary="측정소명, 주소로 이루어진 표">
									<caption>측정소 선택하기</caption>
									<colgroup>
										<col width="6%" />
										<col width="29%" /> 
										<col />
									</colgroup>
		                            <thead>
										<tr>
											<th><input class="checkAll" name="" type="checkbox" value="" /></th>
		                                    <th>조사구간명</th>
		                                    <th>구분</th>
		                                    <th>해역</th>
		                                    <th>수계명</th>
										</tr>
									</thead>
		                            <tbody id="siteTable">
			                            <tr>
											<td colspan="5">검색 결과가 존재하지 않습니다.</td>
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
							<select class="W10p L0" name="select_start_year" id="select_start_year">
								<option value="">선택</option>
								<c:forEach var="item" items="${optionList_3}" varStatus="idx">
		                       		<option value="${item.YEAR }" ${item.YEAR eq param.select_start_year ? 'selected="selected"' : ''}>${item.YEAR }</option>
		                       	</c:forEach>
							</select>
							<span class="MgR8">년</span>
							<select class="W8p L0" name="select_start_tme" id="select_start_tme">
								<option value="">선택</option>
								<option value="1" ${'1' eq param.select_start_tme ? 'selected="selected"' : ''}>1</option>
								<option value="2" ${'2' eq param.select_start_tme ? 'selected="selected"' : ''}>2</option>
							</select>
							<span>회차</span>
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
							<select class="W8p L0" name="select_end_tme" id="select_end_tme">
								<option value="">선택</option>
								<option value="1" ${'1' eq param.select_end_tme ? 'selected="selected"' : ''}>1</option>
								<option value="2" ${'2' eq param.select_end_tme ? 'selected="selected"' : ''}>2</option>
							</select>
							<span>회차</span>
						</dd>
					</dl>
	            </div>
	        </dd>
	    </dl>
		<div class="MgT20 AC"><a class="btn04" href="#" style="" id="srcBtn">검색</a></div>
	</div>	
</form>

<!-- 탭  -->
<div id="divTab">
	<c:choose>
		<c:when test="${param.siteTypeItem eq 'A'}">
			<ul class="tab">
				<li class="on" id="atalTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="atalTab02"><a href="javascript:selTabItem('B');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'B'}">
			<ul class="tab">
			  	<li class="on" id="fishTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="fishTab02"><a href="javascript:selTabItem('B');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'C'}">
			<ul class="tab">
			  	<li class="on" id="bemaTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="bemaTab02"><a href="javascript:selTabItem('B');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'D'}">
			<ul class="tab">
			  	<li class="on" id="vtnTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="vtnTab02"><a href="javascript:selTabItem('B');">출현생물</a></li>
			</ul>
		</c:when>
	</c:choose>
</div>

<!--검색결과-->
<div class="divi MgT50">
	<div>
        <h5>검색결과
        	<span id="resultCnt">
	        	조회현황 : 
		        <c:if test="${empty totalCnt}">0</c:if>
		        <c:if test="${not empty totalCnt}">${totalCnt}</c:if> 
	        	건
        	</span>
        </h5>
    </div>
    <div class="AR MgT5">
    	<a class="btn02" href="#" id="csvBtn">CSV저장</a>
        <a class="btn03" href="#" id="excelBtn">엑셀저장</a>
    </div>
</div>

<!-- 목록 -->
<c:choose>
	<c:when test="${empty param.siteTypeItem or param.siteTypeItem eq 'A'}">
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 생물측정망 부착돌말류 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="3">조사하구</th>
								<th colspan="6">공통자료</th>
								<th colspan="6">조사일반</th>
								<th>하천현황</th>
								<th colspan="19">서식지</th>
								<th colspan="14">수변 및 하천 환경</th>
								<th colspan="11">환경요인</th>
								<th colspan="2">채집불가시</th>
								<th colspan="4">채집가능시</th>
								<th colspan="2">영양염지수</th>
				            </tr>
				            <tr>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th colspan="6">하구정보</th>
								<th colspan="2">조사수행</th>
								<th>조사일</th>
								<th colspan="2">조사지점</th>
								<th>대상</th> 
								<th>하천형</th>
								<th colspan="6">서식지 조건</th>
								<th colspan="3">흐름상태</th>
								<th colspan="2">서식조건 기타</th>
								<th>부착조류채집도구</th>
								<th>부착조류채집방법</th>
								<th colspan="6">부착조류 샘플 채집수 구성</th>
								<th>물빛</th>
								<th>냄새</th>
								<th colspan="2">수변식생</th>
								<th colspan="6">토지이용</th>
								<th>모래퇴적-침식(기질매몰도)</th>
								<th colspan="3">보의 위치 및 영향</th>
								<th colspan="11">환경요인</th>
								<th colspan="2">항목 (건천화, 공사중, 준설, 조사불가)</th>
								<th colspan="3">특이사항</th>
								<th>총 개체 밀도</th>
								<th>생물지수</th>
								<th>등급</th>
							</tr>
							<tr>
								<th>구분</th>
								<th>해역</th>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>하구</th>
								<th>조사기관</th>
								<th>조사자</th>
								<th>조사일(YYYYMMDD)</th>
								<th>위도</th>
								<th>경도</th>
								<th>1. 부착(규조류), 2. 부유(플랑크톤)</th>
								<th>1 산간형, 2 평지형(도시), 3 평지형(농지), 4 강, 5 호소</th>
								<th>모래-실트-질흙-쓰레기(%)</th>
								<th>자갈(%)</th>
								<th>기반암(%)</th>
								<th>작은 나무조각(%)</th>
								<th>큰 나무조각(%)</th>
								<th>식물, 뿌리 등(%)</th>
								<th>여울(%)</th>
								<th>흐름(%)</th>
								<th>소(%)</th>
								<th>Canopy(%)</th>
								<th>수변식생피복(%)</th>
								<th>1. Suction 장치, 2. Scraping, 3. 기타(내용기재)</th>
								<th>1. 걸어서, 2. 수변으로부터, 3. 선박이용</th>
								<th>모래-실트-질흙-쓰레기(%)</th>
								<th>자갈(%)</th>
								<th>기반암(%)</th>
								<th>작은 나무조각(%)</th>
								<th>큰 나무조각(%)</th>
								<th>식물, 뿌리 등(%)</th>
								<th>물빛(1 맑음, 2 약간 탁함, 3 탁함)</th>
								<th>0 없음, 1 보통, 2 악취(유기물), 3 악취(공장폐수)</th>
								<th>초본(%)</th>
								<th>관목(%)</th>
								<th>도시(%)</th>
								<th>숲(%)</th>
								<th>농경지(%)</th>
								<th>공단(%)</th>
								<th>준설-공사(%)</th>
								<th>축사(%)</th>
								<th>0 거의없음, 1 10-20%, 2 20-50%, 3 50-80%, 4 >80%</th>
								<th>0 없음, 1 있음(상류), 2 있음(하류)</th>
								<th>보가 있는 경우 거리(m)</th>
								<th>수질영향 없음(0), 있음(1)</th>
								<th>하폭(m)</th>
								<th>수심(cm)</th>
								<th>수온(°C)</th>
								<th>DO(mg/L)</th>
								<th>pH</th>
								<th>Conductivity(uS/cm)</th>
								<th>Turbidity(NTU)</th>
								<th>염도(‰)</th>
								<th>화학적산소요구량(mg/L)</th>
								<th>총 질소(mg/L)</th>
								<th>총 인(mg/L)</th>
								<th>특이사항</th>
								<th>세부내용</th>
								<th>특이사항</th>
								<th>Chl-a (ug/cm2) - 부착, Chl-a (ug/cm3) - 부유</th>
								<th>AFDM (mg/cm2) -부착</th>
								<th>cells/cm2 - 부착, cells/mL - 부유</th>
								<th>TDI</th>
								<th>TDI (A - E)</th>
							</tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.EST_SE_NM }</td>
										<td>${item.SAREA_NM }</td>
										<td>${item.AL_NM }</td>
										<td>${item.WRSSM_NM }</td>
										<td>${item.AM_NM}</td>
										<td>${item.EST_NM }</td>
										<td>${item.EXMEN_NM }</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.EXAMIN_DE}</td>
										<td>${item.LA_CRDNT}</td>
										<td>${item.LO_CRDNT}</td>
										<td>${item.EXAMIN_TRGET_SE_CD}</td>
										<td>${item.RIVST_CD}</td>
										<td>${item.HBTT_SAND_RT}</td>
										<td>${item.HBTT_PEBB_RT}</td>
										<td>${item.HBTT_ROCK_RT}</td>
										<td>${item.HBTT_LP_RT}</td>
										<td>${item.HBTT_BP_RT}</td>
										<td>${item.HBTT_ROOT_RT}</td>
										<td>${item.FLOW_RAPIDS_RT}</td>
										<td>${item.FLOW_FLOW_RT}</td>
										<td>${item.FLOW_SMALL_RT}</td>
										<td>${item.HBTT_CANOPY_RT}</td>
										<td>${item.HBTT_VECV_RT}</td>
										<td>${item.SPLORE_COLLT_CD}</td>
										<td>${item.SPLORE_COLLM_CD}</td>
										<td>${item.SMPLE_SAND_RT}</td>
										<td>${item.SMPLE_PEBB_RT}</td>
										<td>${item.SMPLE_ROCK_RT}</td>
										<td>${item.SMPLE_LP_RT}</td>
										<td>${item.SMPLE_BP_RT}</td>
										<td>${item.SMPLE_ROOT_RT}</td>
										<td>${item.DCOL_CD}</td>
										<td>${item.SMELL_CD}</td>
										<td>${item.SHVE_ABSTRCT_RT}</td>
										<td>${item.SHVE_SRB_RT}</td>
										<td>${item.LAD_CTY_RT}</td>
										<td>${item.LAD_FRT_RT}</td>
										<td>${item.LAD_FRLND_RT}</td>
										<td>${item.LAD_PBLCRP_RT}</td>
										<td>${item.LAD_DC_RT}</td>
										<td>${item.LAD_STALL_RT}</td>
										<td>${item.SAND_WASH_RT_CD}</td>
										<td>${item.BR_SE_CD}</td>
										<td>${item.BR_DSTNC}</td>
										<td>${item.BR_QLTWTR_AFFC_YN}</td>
										<td>${item.ENVRN_BTRV}</td>
										<td>${item.ENVRN_DPWT}</td>
										<td>${item.ENVRN_WT}</td>
										<td>${item.ENVRN_DO}</td>
										<td>${item.ENVRN_PH}</td>
										<td>${item.ENVRN_CONDT}</td>
										<td>${item.ENVRN_NTU}</td>
										<td>${item.ENVRN_SALT_DO_RT}</td>
										<td>${item.ENVRN_CHOXDM}</td>
										<td>${item.ENVRN_TN}</td>
										<td>${item.ENVRN_TP}</td>
										<td>${item.NTIPS_PTCR_MATT}</td>
										<td>${item.NTIPS_DETAIL_CN}</td>
										<td>${item.NOTI_POSS_PTCR_MATT}</td>
										<td>${item.LBDY_QY_CHLA}</td>
										<td>${item.LBDY_QY_AFDM}</td>
										<td>${item.TOT_INDVD_CO}</td>
										<td>${item.TDI_LVB_IDEX}</td>
										<td>${item.TDI_GRAD}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 부착돌말류 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">연</th>
								<th rowspan="2">회</th>
								<th rowspan="2">학</th>
								<th rowspan="2">국</th>
								<th rowspan="2">세포밀도(Cells/cm2)</th>
								<th colspan="4">TDIm 지수</th>
							</tr>
							<tr>
								<th>KELLYS</th>
								<th>KELLYV</th>
								<th>WAT X</th>
								<th>WAT P</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.SP_SCNCENM}</td>
										<td>${item.SP_KORNM}</td>
										<td>${item.INDVD_CO}</td>
										<td>${item.KLYS_IDEX}</td>
										<td>${item.KLYV_IDEX}</td>
										<td>${item.WATX_IDEX}</td>
										<td>${item.WATP_IDEX}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'B'}">
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 생물측정망 어류 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="3">조사하구</th>
								<th colspan="6">공통자료</th>
								<th colspan="7">조사일반</th>
								<th colspan="2">채집방법</th>
								<th colspan="16">메트릭 항목</th>
								<th colspan="2">건강성 평가</th>
				            </tr>
				            <tr>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th colspan="6">하구정보</th>
								<th colspan="2">조사수행</th>
								<th>조사일</th>
								<th colspan="2">조사지점</th>
								<th>조사불가</th>
								<th>조사 후 출현생물 없음</th>
								<th colspan="2">채집도구</th>
								<th>M1 항목</th>
								<th>M2 항목</th>
								<th>M3 항목</th>
								<th>M4 항목</th>
								<th>M5 항목</th>
								<th>M6 항목</th>
								<th>M7 항목</th>
								<th>M8 항목</th>
								<th>M1 값</th>
								<th>M2 값</th>
								<th>M3 값</th>
								<th>M4 값</th>
								<th>M5 값</th>
								<th>M6 값</th>
								<th>M7 값</th>
								<th>M8 값</th>
								<th colspan="2">IBI</th>
				            </tr>
				            <tr>
				            	<th>구분</th>
				            	<th>해역</th>
				            	<th>대권역</th>
				            	<th>수계</th>
				            	<th>중권역</th>
				            	<th>하구</th>
				            	<th>조사기관</th>
				            	<th>조사자</th>
				            	<th>조사일(YYYYMMDD)</th>
				            	<th>위도(도,분,초)</th>
				            	<th>경도(도,분,초)</th>
				            	<th>특이사항</th>
				            	<th>특이사항</th>
				            	<th>1:투망, 2:족대, 3:일각망, 4:자망</th>
				            	<th>비정상 개체수(직접 입력)</th>
				            	<th>다양도 지수</th>
				            	<th>총 출현 종수</th>
				            	<th>회유어류 출현종수</th>
				            	<th>기수종 출현종수</th>
				            	<th>해산어종 출현종수</th>
				            	<th>내성상주종 출현율</th>
				            	<th>저서종 출현 비율</th>
				            	<th>비정상 개체수 비율</th>
				            	<th>다양도 지수</th>
				            	<th>총 출현 종수</th>
				            	<th>회유어류 출현종수</th>
				            	<th>기수종 출현종수</th>
				            	<th>해산어종 출현종수</th>
				            	<th>내성상주종 출현율</th>
				            	<th>저서종 출현 비율</th>
				            	<th>비정상 개체수 비율</th>
				            	<th>지수값</th>
				            	<th>등급</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.EST_SE_NM }</td>
										<td>${item.SAREA_NM }</td>
										<td>${item.AL_NM }</td>
										<td>${item.WRSSM_NM }</td>
										<td>${item.AM_NM}</td>
										<td>${item.EST_NM }</td>
										<td>${item.EXMEN_NM }</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.EXAMIN_DE}</td>
										<td>${item.LA_CRDNT}</td>
										<td>${item.LO_CRDNT}</td>
										<td>${item.EXMIMP_PTCR_MATT }</td>
										<td>${item.EXAMIN_PTCR_MATT }</td>
										<td>${item.COLLT_CD }</td>
										<td>${item.ABMIND_CO}</td>
										<td>${item.M1_DIV_IDEX }</td>
										<td>${item.M2_TTERE_CO }</td>
										<td>${item.M3_MFERE_CO }</td>
										<td>${item.M4_HRERE_CO }</td>
										<td>${item.M5_ALERE_CO }</td>
										<td>${item.M6_TEERE_RT }</td>
										<td>${item.M7_LTRERE_RATE }</td>
										<td>${item.M8_ABMIND_RATE }</td>
										<td>${item.M1_GRAD }</td>
										<td>${item.M2_GRAD }</td>
										<td>${item.M3_GRAD }</td>
										<td>${item.M4_GRAD }</td>
										<td>${item.M5_GRAD }</td>
										<td>${item.M6_GRAD }</td>
										<td>${item.M7_GRAD }</td>
										<td>${item.M8_GRAD }</td>
										<td>${item.IBI_IDEX }</td>
										<td>${item.IBI_GRAD }</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 어류 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>조사구간명</th>
								<th>연도</th>
								<th>회차</th>
								<th>학명</th>
								<th>국명</th>
								<th>개체수</th>
								<th>회유성어종_여부</th>
								<th>상주종_여부</th>
								<th>외래종_여부</th>
								<th>저서종_여부</th>
								<th>내성종_여부</th>
								<th>내성상주종_여부</th>
								<th>기수종_여부</th>
								<th>고유종_여부</th>
								<th>해산어류종_여부</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM}</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.SP_SCNCENM}</td>
										<td>${item.SP_KORNM}</td>
										<td>${item.INDVD_CO}</td>
										<td>${item.MSP_AT}</td>
										<td>${item.RSP_AT}</td>
										<td>${item.EXO_AT}</td>
										<td>${item.BSP_AT}</td>
										<td>${item.TSP_AT}</td>
										<td>${item.TRSP_AT}</td>
										<td>${item.HSP_AT}</td>
										<td>${item.END_AT}</td>
										<td>${item.CSP_AT}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'C'}">
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 생물측정망 저서성대형무척추동물 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="3">조사하구</th>
								<th colspan="6">공통자료</th>
								<th colspan="7">조사일반</th>
								<th colspan="2">조사환경</th>
								<th colspan="3">조사방법</th>
								<th colspan="20">군집분석</th>
								<th colspan="7">메트릭 항목</th>
								<th colspan="2">건강성 평가</th>
				            </tr>
				            <tr>
				            	<th rowspan="2">조사구간명</th>
				            	<th rowspan="2">연도</th>
				            	<th rowspan="2">회차</th>
				            	<th colspan="6">하구정보</th>
				            	<th colspan="2">조사수행</th>
				            	<th>조사일</th>
				            	<th colspan="2">조사지점</th>
				            	<th>조사불가</th>
				            	<th>조사 후 출현생물 없음</th>
				            	<th colspan="2">기상조건</th>
				            	<th>Surber net (가로 30cm*세로 30cm)</th>
				            	<th>Dredge net (폭 40cm*끄는거리 50cm)</th>
				            	<th>Ponar grab (가로 20cm*세로 20cm)</th>
				            	<th>종수</th>
				            	<th>개체밀도</th>
				            	<th colspan="3">민감종</th>
				            	<th></th>
				            	<th colspan="4">내성종</th>
				            	<th colspan="2">민감종/내성종</th>
				            	<th colspan="4">기수종</th>
				            	<th colspan="4">군집지수</th>
				            	<th>M1(0-4점)</th>
				            	<th>M2(0-4점)</th>
				            	<th>M3(0-4점)</th>
				            	<th>M4(0-4점)</th>
				            	<th>M5(0-4점)</th>
				            	<th>M6(0-4점)</th>
				            	<th>M7(0-4점)</th>
				            	<th colspan="2">KEBI</th>
				            </tr>
				           	<tr>
				            	<th>구분</th>
				            	<th>해역</th>
				            	<th>대권역</th>
				            	<th>수계</th>
				            	<th>중권역</th>
				            	<th>하구</th>
				            	<th>조사기관</th>
				            	<th>조사자</th>
				            	<th>조사일(YYYYMMDD)</th>
				            	<th>위도</th>
				            	<th>경도</th>
				            	<th>특이사항</th>
				            	<th>특이사항</th>
				            	<th>기온(°C)</th>
				            	<th>수온(°C)</th>
				            	<th>조사횟수</th>
				            	<th>조사횟수</th>
				            	<th>조사횟수</th>
				            	<th>종수</th>
				            	<th>개체밀도(개체/m2)</th>
				            	<th>종수</th>
				            	<th>종수비율(%)</th>
				            	<th>개체밀도(개체/m2)</th>
				            	<th>개체밀도 비율(%)</th>
				            	<th>종수</th>
				            	<th>종수비율(%)</th>
				            	<th>개체밀도(개체/m2)</th>
				            	<th>개체밀도 비율(%)</th>
				            	<th>종수비율(%)</th>
				            	<th>개체밀도 비율(%)</th>
				            	<th>종수</th>
				            	<th>종수비율(%)</th>
				            	<th>개체밀도(개체/m2)</th>
				            	<th>개체밀도 비율(%)</th>
				            	<th>우점도지수(DI)</th>
				            	<th>다양도지수(H')</th>
				            	<th>풍부도지수(R1)</th>
				            	<th>균등도지수(J')</th>
				            	<th>종수</th>
				            	<th>개체밀도</th>
				            	<th>%민감종수</th>
				            	<th>%민감종 밀도</th>
				            	<th>%기수종수</th>
				            	<th>%기수종 밀도</th>
				            	<th>다양도지수(H')</th>
				            	<th>Total(100점 환산)</th>
				            	<th>등급</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.EST_SE_NM }</td>
										<td>${item.SAREA_NM }</td>
										<td>${item.AL_NM }</td>
										<td>${item.WRSSM_NM }</td>
										<td>${item.AM_NM}</td>
										<td>${item.EST_NM }</td>
										<td>${item.EXMEN_NM }</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.EXAMIN_DE}</td>
										<td>${item.LA_CRDNT}</td>
										<td>${item.LO_CRDNT}</td>
										<td>${item.EXMIMP_PTCR_MATT }</td>
										<td>${item.EXMAT_PTCR_MATT }</td>
										<td>${item.WETHER_TMPRT }</td>
										<td>${item.WETHER_WT }</td>
										<td>${item.SBN30_EXAMIN_CO }</td>
										<td>${item.DR_EXAMIN_CO }</td>
										<td>${item.POR20_EXAMIN_CO }</td>
										<td>${item.SPCS_CO }</td>
										<td>${item.INDVD_DN }</td>
										<td>${item.SENSP_CO }</td>
										<td>${item.SENSP_CO_RATE }</td>
										<td>${item.SNTIND_DN_RT }</td>
										<td>${item.SNTIND_DN_RATE }</td>
										<td>${item.TOESP_CO_RT }</td>
										<td>${item.TOESP_CO_RATE }</td>
										<td>${item.TOE_INDVD_DN }</td>
										<td>${item.TOE_INDVD_DN_RATE }</td>
										<td>${item.SNTOSP_CO_RATE }</td>
										<td>${item.SNTOIN_DN_RATE }</td>
										<td>${item.BKPKSP_CO }</td>
										<td>${item.BKPKSP_CO_RATE }</td>
										<td>${item.HRSMN_INDDN }</td>
										<td>${item.HRSMN_INDDN_RATE }</td>
										<td>${item.CLUSTER_DOM_IDEX }</td>
										<td>${item.CLUSTER_DIV_IDEX }</td>
										<td>${item.CLUSTER_ABNDNC_IDEX }</td>
										<td>${item.CLUSTER_EQLTY_IDEX }</td>
										<td>${item.M1_SPCS_CO }</td>
										<td>${item.M2_INDVD_DN }</td>
										<td>${item.M3_SENSP_CO }</td>
										<td>${item.M4_SENSP_DN }</td>
										<td>${item.M5_BKPKSP_CO }</td>
										<td>${item.M6_BKPKSP_DN }</td>
										<td>${item.M7_DIV_IDEX }</td>
										<td>${item.KEBI_TOTAL }</td>
										<td>${item.KEBI_GRAD }</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 저서성대형무척추동물 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>조사구간명</th>
								<th>연도</th>
								<th>회차</th>
								<th>학명</th>
								<th>국명</th>
								<th>개체수</th>
								<th>법정보호종_여부 </th>
								<th>종_구분 </th>
								<th>기수종_여부 </th>
								<th>오탁_계급_수치 </th>
								<th>지표_가중_수치 </th>
								<th>FFG_코드 </th>
								<th>ESB_구분 </th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.SP_SCNCENM}</td>
										<td>${item.SP_KORNM}</td>
										<td>${item.INDVD_CO}</td>
										<td>${item.LPRSP_AT }</td>
										<td>${item.SPCS_SE }</td>
										<td>${item.HSP_AT }</td>
										<td>${item.POLLUTN_CLSS_NCL }</td>
										<td>${item.IX_AGRVTN_NCL }</td>
										<td>${item.FFG_CODE }</td>
										<td>${item.ESB_SE }</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'D'}">
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 생물측정망 식생 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="3">조사하구</th>
								<th colspan="6">공통자료</th>
								<th colspan="7">조사일반</th>
								<th>조사방법</th>
								<th>입지환경</th>
								<th>식생분포</th>
								<th colspan="5">식생자연도평가</th>
								<th>식생자연도평가</th>
								<th>식생자연도평가</th>
								<th colspan="7">식생구분 면적</th>
								<th colspan="7">식생구분 면적(%)</th>
								<th colspan="2">식생층위구분</th>
				            </tr>
				            <tr>
				            	<th rowspan="2">조사구간명</th>
				            	<th rowspan="2">연도</th>
				            	<th rowspan="2">회차</th>
				            	<th colspan="6">하구정보</th>
				            	<th colspan="2">조사수행</th>
				            	<th>조사일</th>
				            	<th colspan="2">조사지점</th>
				            	<th>조사불가</th>
				            	<th>조사지 특이사항</th>
				            	<th>평가수단</th>
				            	<th>온전성</th>
				            	<th>다양성</th>
				            	<th>온전성</th>
				            	<th>식생비중</th>
				            	<th>식생비중</th>
				            	<th>식생비중</th>
				            	<th>식생비중</th>
				            	<th>평가결과</th>
				            	<th>평가결과</th>
				            	<th>식생의 면적</th>
				            	<th>식생의 면적</th>
				            	<th>식생의 면적</th>
				            	<th>간섭지역의 면적</th>
				            	<th>자연입지의 면적</th>
				            	<th>경작지면적</th>
				            	<th>면적의 합계</th>
				            	<th>식생의 면적(%)</th>
				            	<th>식생의 면적(%)</th>
				            	<th>식생의 면적(%)</th>
				            	<th>간섭지역의 면적(%)</th>
				            	<th>자연입지의 면적(%)</th>
				            	<th>경작지면적(%)</th>
				            	<th>면적의 합계(%)</th>
				            	<th>식생명</th>
				            	<th>식생명</th>
				            </tr>
				            <tr>
				            	<th>구분</th>
				            	<th>해역</th>
				            	<th>대권역</th>
				            	<th>수계</th>
				            	<th>중권역</th>
				            	<th>하구명</th>
				            	<th>조사기관</th>
				            	<th>조사자</th>
				            	<th>조사일(YYYYMMDD)</th>
				            	<th>위도(도.분.초)</th>
				            	<th>경도(도.분.초)</th>
				            	<th>특이사항</th>
				            	<th>특이사항</th>
				            	<th>1.현존식생도 2.식생단면도 3.종조성</th>
				            	<th>M1: 식생환경의 온전성 등급</th>
				            	<th>M5: 적지군락수</th>
				            	<th>M1: 식생환경의 온전성(0-4점x5)</th>
				            	<th>M2: 외래식물군락의 분포면적(0-4점x5)</th>
				            	<th>M3 : 육상식물군락의 분포면적(0-4점x5)</th>
				            	<th>M4 : 습지식물군락 및 염생식물군락의 분포면적(0-4점x5)</th>
				            	<th>M5: 적지군락 다양성 평가(0-4점x5)</th>
				            	<th>지수값</th>
				            	<th>등급</th>
				            	<th>습지식물 및 염생식물군락(㎡)</th>
				            	<th>육상식물군락(㎡)</th>
				            	<th>외래식물군락(식재유형포함)(㎡)</th>
				            	<th>인공구조물 및 나지(㎡)</th>
				            	<th>수역 및 자연하상(㎡)</th>
				            	<th>경작지(㎡)</th>
				            	<th>계(㎡)</th>
				            	<th>습지식물 및 염생식물군락(㎡, %)</th>
				            	<th>육상식물군락(㎡, %)</th>
				            	<th>외래식물군락(식재유형포함)(㎡, %)</th>
				            	<th>인공구조물 및 나지(㎡, %)</th>
				            	<th>수역 및 자연하상(㎡,%)</th>
				            	<th>경작지(㎡,%)</th>
				            	<th>계(㎡,%)</th>
				            	<th>우점군락</th>
				            	<th>우점군락면적(㎡)</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.EST_SE_NM }</td>
										<td>${item.SAREA_NM }</td>
										<td>${item.AL_NM }</td>
										<td>${item.WRSSM_NM }</td>
										<td>${item.AM_NM}</td>
										<td>${item.EST_NM }</td>
										<td>${item.EXMEN_NM }</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.EXAMIN_DE}</td>
										<td>${item.LA_CRDNT}</td>
										<td>${item.LO_CRDNT}</td>
										<td>${item.EXMIMP_PTCR_MATT }</td>
										<td>${item.EXAMIN_PTCR_MATT }</td>
										<td>${item.EXAMIN_MTH_CD }</td>
										<td>${item.M1_LCT_ENVRN }</td>
										<td>${item.M5_VTN_DISTRB }</td>
										<td>${item.M1_VTNTEL }</td>
										<td>${item.M2_VTNTEL }</td>
										<td>${item.M3_VTNTEL }</td>
										<td>${item.M4_VTNTEL }</td>
										<td>${item.M5_VTNTEL }</td>
										<td>${item.VTNTEL_IDEX }</td>
										<td>${item.VTNTEL_GRAD }</td>
										<td>${item.VTSEAR_MV }</td>
										<td>${item.VTSEAR_LDPLMY }</td>
										<td>${item.VTSEAR_FNPLMY }</td>
										<td>${item.VTSEAR_AS }</td>
										<td>${item.VTSEAR_WATERS }</td>
										<td>${item.VTSEAR_FMLND }</td>
										<td>${item.VTSEAR_SM }</td>
										<td>${item.VTSEAT_MV }</td>
										<td>${item.VTSEAT_LDPLMY }</td>
										<td>${item.VTSEAT_FNPLMY }</td>
										<td>${item.VTSEAT_AS }</td>
										<td>${item.VTSEAT_WATERS }</td>
										<td>${item.VTSEAT_FMLND }</td>
										<td>${item.VTSEAT_SM }</td>
										<td>${item.VTALSE_DMCY_NM }</td>
										<td>${item.VTALSE_DMCY_AR }</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 식생 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>조사구간명</th>
								<th>연도</th>
								<th>회차</th>
								<th>층위구분</th>
								<th>군락명</th>
								<th>면적</th>
				            </tr>
						</thead>
						<tbody id="resultSiteTable">
							<c:if test="${empty resultList}">
								<tr>
									<td colspan="100">검색 결과가 존재하지 않습니다.</td>
								</tr>
							</c:if>
							<c:if test="${not empty resultList}">
								<c:forEach var="item" items="${resultList}" varStatus="idx">
									<tr>
										<td>${item.RNUM }</td>
										<td>${item.EXBF_NM }</td>
										<td>${item.EXAMIN_YEAR}</td>
										<td>${item.EXAMIN_TME}</td>
										<td>${item.VTN_NM}</td>
										<td>${item.CLY_NM }</td>
										<td>${item.CLY_AR}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
		</c:choose>
	</c:when>
</c:choose>

<div class="paging" id="paging">
	<%@ include file="/common/pager.jsp"%>
</div>



