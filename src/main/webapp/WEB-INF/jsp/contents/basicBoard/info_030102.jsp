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
		$("#atalTab03").attr("class", "");
		$("#fishTab01").attr("class", "");
		$("#fishTab02").attr("class", "");
		$("#fishTab03").attr("class", "");
		$("#inhaTab01").attr("class", "");
		$("#inhaTab02").attr("class", "");
		$("#bemaTab01").attr("class", "");
		$("#bemaTab02").attr("class", "");
		$("#bemaTab03").attr("class", "");
		$("#vtnTab01").attr("class", "");
		$("#vtnTab02").attr("class", "");
		$("#vtnTab03").attr("class", "");
		$("#vtnTab04").attr("class", "");
		$("#vtnTab05").attr("class", "");
		$("#vtnTab06").attr("class", "");
		$("#vtnTab07").attr("class", "");
		
		var varSiteTypeItem = "${param.siteTypeItem}";
		var varSiteTypeItemSub = "${param.siteTypeItemSub}";

		if(varSiteTypeItem == "A"){
			if(varSiteTypeItemSub == "A"){
				$("#atalTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#atalTab02").attr("class", "on");
			}else if(varSiteTypeItemSub == "C"){
				$("#atalTab03").attr("class", "on");
			}
		}else if(varSiteTypeItem == "B"){
			if(varSiteTypeItemSub == "A"){
				$("#fishTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#fishTab02").attr("class", "on");
			}else if(varSiteTypeItemSub == "C"){
				$("#fishTab03").attr("class", "on");
			}
		}else if(varSiteTypeItem == "C"){
			if(varSiteTypeItemSub == "A"){
				$("#inhaTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#inhaTab02").attr("class", "on");
			}
		}else if(varSiteTypeItem == "D"){
			if(varSiteTypeItemSub == "A"){
				$("#bemaTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#bemaTab02").attr("class", "on");
			}else if(varSiteTypeItemSub == "C"){
				$("#bemaTab03").attr("class", "on");
			}
		}else if(varSiteTypeItem == "E"){
			if(varSiteTypeItemSub == "A"){
				$("#vtnTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#vtnTab02").attr("class", "on");
			}else if(varSiteTypeItemSub == "C"){
				$("#vtnTab03").attr("class", "on");
			}else if(varSiteTypeItemSub == "D"){
				$("#vtnTab04").attr("class", "on");
			}else if(varSiteTypeItemSub == "E"){
				$("#vtnTab05").attr("class", "on");
			}else if(varSiteTypeItemSub == "F"){
				$("#vtnTab06").attr("class", "on");
			}else if(varSiteTypeItemSub == "G"){
				$("#vtnTab07").attr("class", "on");
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
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType, DO_TYPE:'aemrv' };
        
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
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType, DO_TYPE:'aemrv' };
        
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
        var param = { TYPE_CD:getSiteType, ST_NM:getName, SEARCH_TYPE:searchType, DO_TYPE:'aemrv' };
        
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
						selOpt += "   <td>"+item.MDT_NM+"</td>";
						selOpt += "   <td>"+item.WRSSM_NM+"</td>";
						selOpt += "   <td>"+item.BSTRM_SE+"</td>";
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
				$("#mId").val("excelAemrvAtalAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemrvAtalAnalyList");
			}else if(varSiteTypeItemSub == "C"){
				$("#mId").val("excelAemrvAtalSpcsList");
			}
		}else if(varSiteType == 'B'){
			//어류
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemrvFishAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemrvFishAnalyList");
			}else if(varSiteTypeItemSub == "C"){
				$("#mId").val("excelAemrvFishSpcsList");
			}
		}else if(varSiteType == 'C'){
			//서식수변
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemrvInhaAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemrvInhaAnalyList");
			}
		}else if(varSiteType == 'D'){
			//저서성대형무척추동물
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemrvBemaAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemrvBemaAnalyList");
			}else if(varSiteTypeItemSub == "C"){
				$("#mId").val("excelAemrvBemaSpcsList");
			}
		}else if(varSiteType == 'E'){
			//식생
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelAemrvVtnAcpexmList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelAemrvVtnAnalyList");
			}else if(varSiteTypeItemSub == "C"){
				$("#mId").val("excelAemrvVtnSpcsList");
			}else if(varSiteTypeItemSub == "D"){
				$("#mId").val("excelAemrvVtnAreaList");
			}else if(varSiteTypeItemSub == "E"){
				$("#mId").val("excelAemrvVtnSctnList");
			}else if(varSiteTypeItemSub == "F"){
				$("#mId").val("excelAemrvVtnSctndtlList");
			}else if(varSiteTypeItemSub == "G"){
				$("#mId").val("excelAemrvVtnSctndomList");
			}
		}else if(varSiteType == 'F'){
			//수질
			$("#mId").val("excelAemrvQltwtrDtaList");
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
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_C" title="서식수변" value="C" ${'C' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_C" >서식수변</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="저서성대형무척추동물" value="D" ${'D' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_D" >저서성대형무척추동물</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_E" title="식생" value="E" ${'E' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_E" >식생</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_F" title="수질" value="F" ${'F' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_F" >수질</label>
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
		                                    <th>수계</th>
		                                    <th>대권역</th>
		                                    <th>지류구분</th>
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
			    <li id="atalTab02"><a href="javascript:selTabItem('B');">분석정보</a></li>
			    <li id="atalTab03"><a href="javascript:selTabItem('C');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'B'}">
			<ul class="tab">
			  	<li class="on" id="fishTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="fishTab02"><a href="javascript:selTabItem('B');">분석정보</a></li>
			    <li id="fishTab03"><a href="javascript:selTabItem('C');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'C'}">
			<ul class="tab">
			  	<li class="on" id="inhaTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			  	<li id="inhaTab02"><a href="javascript:selTabItem('B');">분석정보</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'D'}">
			<ul class="tab">
			  	<li class="on" id="bemaTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="bemaTab02"><a href="javascript:selTabItem('B');">분석정보</a></li>
			    <li id="bemaTab03"><a href="javascript:selTabItem('C');">출현생물</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'E'}">
			<ul class="tab">
			  	<li class="on" id="vtnTab01"><a href="javascript:selTabItem('A');">현지조사</a></li>
			    <li id="vtnTab02"><a href="javascript:selTabItem('B');">분석정보</a></li>
			    <li id="vtnTab03"><a href="javascript:selTabItem('C');">출현생물</a></li>
			    <li id="vtnTab04"><a href="javascript:selTabItem('D');">식생면적</a></li>
			    <li id="vtnTab05"><a href="javascript:selTabItem('E');">단면정보</a></li>
			    <li id="vtnTab06"><a href="javascript:selTabItem('F');">단면 구간별 정보</a></li>
			    <li id="vtnTab07"><a href="javascript:selTabItem('G');">우점도</a></li>
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
								<th colspan="10">공통자료</th>
								<th colspan="6">현지조사 일반정보</th>
								<th rowspan="2">하천현황</th>
								<th colspan="11">서식지</th>
								<th colspan="8">시료채집</th>
								<th colspan="14">수변 및 하천 환경</th>
								<th colspan="7" rowspan="2">환경요인</th>
								<th colspan="2">채집불가시</th>
								<th>채집가능시</th>
				            </tr>
				            <tr>
								<th rowspan="2">년도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="6">위치</th>
								<th colspan="2">조사지점</th>
								<th>조사일</th>
								<th>날씨</th>
								<th colspan="2">조사수행</th>
								<th colspan="6">서식지 조건</th>
								<th colspan="3">흐름상태</th>
								<th colspan="2">서식조건 기타</th>
								<th>채집도구</th>
								<th>채집방법</th>
								<th colspan="6">샘플 채집수 구성</th>
								<th>물빛</th>
								<th>냄새</th>
								<th colspan="2">수변식생</th>
								<th colspan="6">토지이용</th>
								<th>모래퇴적-침식(기질매몰도)</th>
								<th colspan="3">보의 위치 및 영향</th>
								<th colspan="2">항목 (건천화, 공사중, 준설, 조사불가)</th>
								<th>특이사항</th>
							</tr>
							<tr>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>소권역</th>
								<th>하천명</th>
								<th>본류_지류 구분</th>
								<th>위도(도.분.초)</th>
								<th>경도(도.분.초)</th>
								<th>조사일(YYMMDD)</th>
								<th>1 맑음, 2 흐림, 3 비(눈)</th>
								<th>조사기관</th>
								<th>조사자</th>
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
								<th>1 Suction 장치, 2 Scraping, 3 기타</th>
								<th>1 걸어서, 2 수변으로부터, 3 보트이용</th>
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
								<th>DO(mg/l)</th>
								<th>pH</th>
								<th>Conductivity(uS/cm)</th>
								<th>Turbidity(NTU)</th>
								<th>특이사항</th>
								<th>세부내용</th>
								<th>특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.WRSSM_NM}</td>
										<td>${item.DETAIL_WRSSM_NM}</td>
										<td>${item.MDT_NM}</td>
										<td>${item.SDT_NM}</td>
										<td>${item.RIVER_NM}</td>
										<td>${item.BSTRM_SE}</td>
										<td>${item.LA}</td>
										<td>${item.LO}</td>
										<td>${item.DE}</td>
										<td>${item.WETHR_SE}</td>
										<td>${item.EXAMIN_INSTT_NM}</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.RIVER_STLE_SE}</td>
										<td>${item.HBTT_SAND_RT}</td>
										<td>${item.HBTT_PEBB_RT}</td>
										<td>${item.HBTT_ROCK_RT}</td>
										<td>${item.HBTT_LP_RT}</td>
										<td>${item.HBTT_BP_RT}</td>
										<td>${item.HBTT_ROOT_RT}</td>
										<td>${item.HBTT_RAP_RT}</td>
										<td>${item.HBTT_FLOW_RT}</td>
										<td>${item.HBTT_POND_RT}</td>
										<td>${item.HBTT_CNPY_RT}</td>
										<td>${item.HBTT_VEGCOV_RT}</td>
										<td>${item.SMPLE_COLCT_UNT}</td>
										<td>${item.SMPLE_COLCT_MTH}</td>
										<td>${item.SMPLE_SAND_RT}</td>
										<td>${item.SMPLE_PEBB_RT}</td>
										<td>${item.SMPLE_ROCK_RT}</td>
										<td>${item.SMPLE_LP_RT}</td>
										<td>${item.SMPLE_BP_RT}</td>
										<td>${item.SMPLE_ROOT_RT}</td>
										<td>${item.RE_DCOL_SE}</td>
										<td>${item.RE_SMELL_SE}</td>
										<td>${item.RE_HRB_RT}</td>
										<td>${item.RE_SRB_RT}</td>
										<td>${item.RE_CTY_RT}</td>
										<td>${item.RE_FRT_RT}</td>
										<td>${item.RE_FRLND_RT}</td>
										<td>${item.RE_ISRLPX_RT}</td>
										<td>${item.RE_DRDG_RT}</td>
										<td>${item.RE_STALL_RT}</td>
										<td>${item.RE_WASH_SE}</td>
										<td>${item.BRRER_LC_SE}</td>
										<td>${item.BRRER_GAP_DSTNC}</td>
										<td>${item.BRRER_AFWQ_SE}</td>
										<td>${item.ENVFTR_BTRV}</td>
										<td>${item.ENVFTR_DPWT}</td>
										<td>${item.ENVFTR_WTRTP}</td>
										<td>${item.ENVFTR_DOC}</td>
										<td>${item.ENVFTR_PH}</td>
										<td>${item.ENVFTR_EC}</td>
										<td>${item.ENVFTR_TUR}</td>
										<td>${item.CLIMP_PARTCLR_MATTER}</td>
										<td>${item.CLIMP_DETAIL_CN}</td>
										<td>${item.CLPSS_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 부착돌말류 분석정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">출현종 수</th>
								<th colspan="3">생물량</th>
								<th colspan="8">지표종</th>
								<th rowspan="2">풍부도</th>
								<th colspan="3">우점종</th>
								<th rowspan="2">법정보호종</th>
								<th rowspan="2">외래종</th>
								<th rowspan="2">TDI</th>
								<th rowspan="2">건강성등급</th>
				            </tr>
				            <tr>
								<th>세포밀도(cell/cm2)</th>
								<th>엽록소(Chl-a)(ug/cm2)</th>
								<th>유기물함량(AFDM)(mg/cm2)</th>
								<th>호청수성종 출현 종수</th>
								<th>호청수성종 출현 종수비</th>
								<th>호청수성종 세포밀도(cell/cm2)</th>
								<th>호청수성종 상대밀도(cell/cm2)</th>
								<th>호오탁성종 출현종수</th>
								<th>호오탁성종 출현 종수비</th>
								<th>호오탁성종 세포밀도(cell/cm2)</th>
								<th>호오탁성종 상대밀도(cell/cm2)</th>
								<th>학명</th>
								<th>세포밀도(cell/cm2)</th>
								<th>상대밀도(cell/cm2)</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.TOT_SPCS_CO}</td>
										<td>${item.CHLA_QY}</td>
										<td>${item.AFDM_QY}</td>
										<td>${item.TOT_CELL_DN}</td>
										<td>${item.KSP_CO}</td>
										<td>${item.KSP_RT}</td>
										<td>${item.KSP_CELL_DN}</td>
										<td>${item.KSP_PARTN_DN}</td>
										<td>${item.PSP_CO}</td>
										<td>${item.PSP_RT}</td>
										<td>${item.PSP_CELL_DN}</td>
										<td>${item.PSP_PARTN_DN}</td>
										<td>${item.RIC_IDEX}</td>
										<td>${item.DKPAR_SCNCENM}</td>
										<td>${item.DKPAR_CELL_DN}</td>
										<td>${item.DKPAR_PARTN_DN}</td>
										<td>${item.LPRSP_CN}</td>
										<td>${item.EXO_CN}</td>
										<td>${item.TDI}</td>
										<td>${item.HEALTH_GRAD}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'C'}">
				<!-- 생물측정망 부착돌말류 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">학명</th>
								<th rowspan="2">국명</th>
								<th rowspan="2">세포밀도(cell/cm2)</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCNCENM}</td>
										<td>${item.KORNM}</td>
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
								<th colspan="10">공통자료</th>
								<th colspan="6">현지조사 일반정보</th>
								<th colspan="2">조사일반</th>
								<th colspan="2">채집방법</th>
								<th colspan="8">하상구조</th>
								<th>하천형태</th>
								<th>흐름상태</th>
								<th colspan="2">채집불가시</th>
								<th>조사가능시</th>
				            </tr>
				            <tr>
								<th rowspan="2">년도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="6">위치</th>
								<th colspan="2">조사지점</th>
								<th>조사일</th>
								<th>날씨</th>
								<th colspan="2">조사수행</th>
								<th>하천차수</th>
								<th>채집소요시간</th>
								<th>채집도구</th>
								<th>흐름상태</th>
								<th colspan="8">하상구성</th>
								<th>형태특성</th>
								<th></th>
								<th>항목</th>
								<th>항목</th>
								<th>특이사항</th>
							</tr>
							<tr>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>소권역</th>
								<th>하천명</th>
								<th>본류_지류 구분</th>
								<th>위도(도.분.초)</th>
								<th>경도(도.분.초)</th>
								<th>조사일(YYMMDD)</th>
								<th>1 맑음, 2 흐림, 3 비(눈)</th>
								<th>조사기관</th>
								<th>조사자</th>
								<th>하천차수</th>
								<th>채집소요시간(분)</th>
								<th>1 투망, 2 족대</th>
								<th>1 여울, 2 흐름, 3 소</th>
								<th>암반</th>
								<th>콘크리트</th>
								<th>진흙이하(<0.063mm)</th>
								<th>모래(0.063-2mm)</th>
								<th>잔자갈(2-16mm)</th>
								<th>자갈(16-64mm)</th>
								<th>작은돌(64-256mm)</th>
								<th>큰돌(>256mm)</th>
								<th>1(자연형), 2(직강화), 3(복합형), 4(댐/보/교각), 5(공사)</th>
								<th>1(아주빠름), 2(빠름), 3(보통), 4(느림), 5(정체)</th>
								<th>특이사항(접근불가, 건천화, 공사중, 준설 中)</th>
								<th>세부내용</th>
								<th>특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.WRSSM_NM}</td>
										<td>${item.DETAIL_WRSSM_NM}</td>
										<td>${item.MDT_NM}</td>
										<td>${item.SDT_NM}</td>
										<td>${item.RIVER_NM}</td>
										<td>${item.BSTRM_SE}</td>
										<td>${item.LA}</td>
										<td>${item.LO}</td>
										<td>${item.DE}</td>
										<td>${item.WETHR_SE}</td>
										<td>${item.EXAMIN_INSTT_NM}</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.RIVER_ODR}</td>
										<td>${item.COLCT_REQRE_TIME}</td>
										<td>${item.COLCT_UNT_SE}</td>
										<td>${item.FLOW_STTUS_SE}</td>
										<td>${item.STR_ROCK_RT}</td>
										<td>${item.STR_CNCRT_RT}</td>
										<td>${item.STR_MD_RT}</td>
										<td>${item.STR_SAND_RT}</td>
										<td>${item.STR_SBAL_RT}</td>
										<td>${item.STR_PEBB_RT}</td>
										<td>${item.STR_SS_RT}</td>
										<td>${item.STR_LS_RT}</td>
										<td>${item.RIVER_STLE_SE}</td>
										<td>${item.FLOW_VE_SE}</td>
										<td>${item.CLIMP_PARTCLR_MATTER}</td>
										<td>${item.CLIMP_DETAIL_CN}</td>
										<td>${item.CLPSS_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 어류 분석정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="2">비정상(DB용)</th>
								<th colspan="8">통합평가_백분율</th>
								<th rowspan="2">출현종 수</th>
								<th rowspan="2">생물량 (개체수)</th>
								<th colspan="8">지표종</th>
								<th rowspan="2">풍부도</th>
								<th colspan="3">우점종</th>
								<th rowspan="2">국내종(개체수비율)</th>
								<th rowspan="2">여울성종(개체수 비율)</th>
								<th rowspan="2">잡식종(개체수 비율)</th>
								<th rowspan="2">비정상종 개체수 비율	</th>
								<th rowspan="2">FAI</th>
								<th rowspan="2">건강성등급</th>
				            </tr>
				            <tr>
				            	<th>비정상개체수</th>
				            	<th>비정상개체수비율 (%)</th>
				            	<th>M1 (국내종의 총 종수)</th>
				            	<th>M2 (여울성 저서종수)</th>
				            	<th>M3 (민감종수)</th>
				            	<th>M4 (내성종의 개체수 비율)</th>
				            	<th>M5 (잡식종의 개체수 비율)</th>
				            	<th>M6 (국내종의 충식종 개체수 비율)</th>
				            	<th>M7 (채집된 국내종의 총 개체수)</th>
				            	<th>M8 (비정상종의 개체수 비율)</th>
				            	<th>민감종 출현종수</th>
				            	<th>민감종 출현종수 비</th>
				            	<th>민감종 개체수</th>
				            	<th>민감종 개체수 비</th>
				            	<th>내성종 출현종수</th>
				            	<th>내성종 출현종수 비</th>
				            	<th>내성종 개체수</th>
				            	<th>내성종 개체수 비</th>
				            	<th>국명</th>
				            	<th>개체수</th>
				            	<th>점유율</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.ABNRM_INDVD_CO}</td>
										<td>${item.ABNRM_INDVD_RT}</td>
										<td>${item.UNEVL_M1_PT}</td>
										<td>${item.UNEVL_M2_PT}</td>
										<td>${item.UNEVL_M3_PT}</td>
										<td>${item.UNEVL_M4_PT}</td>
										<td>${item.UNEVL_M5_PT}</td>
										<td>${item.UNEVL_M6_PT}</td>
										<td>${item.UNEVL_M7_PT}</td>
										<td>${item.UNEVL_M8_PT}</td>
										<td>${item.TOT_SPCS_CO}</td>
										<td>${item.TOT_INDVD_CO}</td>
										<td>${item.SSP_CO}</td>
										<td>${item.SSP_RT}</td>
										<td>${item.SSP_INDVD_CO}</td>
										<td>${item.SSP_INDVD_RT}</td>
										<td>${item.TSP_CO}</td>
										<td>${item.TSP_RT}</td>
										<td>${item.TSP_INDVD_CO}</td>
										<td>${item.TSP_INDVD_RT}</td>
										<td>${item.RIC_IDEX}</td>
										<td>${item.DKPAR_KORNM}</td>
										<td>${item.DKPAR_INDVD_CO}</td>
										<td>${item.DKPAR_POSSESN_RT}</td>
										<td>${item.DSP_INDVD_RT}</td>
										<td>${item.RSP_INDVD_RT}</td>
										<td>${item.PSP_INDVD_RT}</td>
										<td>${item.ASP_INDVD_RT}</td>
										<td>${item.FAI}</td>
										<td>${item.HEALTH_GRAD}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'C'}">
				<!-- 생물측정망 어류 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도	</th>
								<th>회차	</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>학명</th>
								<th>국명</th>
								<th>개체수</th>
								<th>내성도 길드</th>
								<th>섭식 길드</th>
								<th>서식지 길드</th>
								<th>외래종 여부</th>
								<th>고유종 여부</th>
								<th>멸종위기I급 여부</th>
								<th>멸종위기II급 여부</th>
								<th>천연기념물 여부	</th>
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
										<td>${item.YEARA}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCNCENM}</td>
										<td>${item.KORNM}</td>
										<td>${item.INDVD_CO}</td>
										<td>${item.TOE_CODE}</td>
										<td>${item.FED_CODE}</td>
										<td>${item.HBTT_CODE}</td>
										<td>${item.EXO_AT}</td>
										<td>${item.END_AT}</td>
										<td>${item.ENSPC_1CLS_AT}</td>
										<td>${item.ENSPC_2CLS_AT}</td>
										<td>${item.NTRMN_AT}</td>
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
				<!-- 생물측정망 서식수변 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="10">공통자료</th>
								<th colspan="6">현지조사 일반정보</th>
								<th colspan="3">조사일반</th>
								<th>사진정보</th>
								<th colspan="2">조사불가시</th>
								<th>조사가능시</th>
				            </tr>
				            <tr>
								<th rowspan="2">년도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="6">위치</th>
								<th colspan="2">조사지점</th>
								<th>조사일</th>
								<th>날씨</th>
								<th colspan="2">조사수행</th>
								<th>하천등급</th>
								<th>고도</th>
								<th>배후습지</th>
								<th>조사지점</th>
								<th colspan="2">항목</th>
								<th>항목</th>
							</tr>
							<tr>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>소권역</th>
								<th>하천명</th>
								<th>본류_지류 구분</th>
								<th>위도(도.분.초)</th>
								<th>경도(도.분.초)</th>
								<th>조사일(YYMMDD)</th>
								<th>1 맑음, 2 흐림, 3 비(눈)</th>
								<th>조사기관</th>
								<th>조사자</th>
								<th>1 국가하천, 2 지방하천, 3 소하천</th>
								<th>고도(m)</th>
								<th>0 없음, 1 있음</th>
								<th>파일명</th>
								<th>특이사항 (건천화, 공사중, 준설, 조사불가 中)</th>
								<th>세부내용</th>
								<th>특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.WRSSM_NM}</td>
										<td>${item.DETAIL_WRSSM_NM}</td>
										<td>${item.MDT_NM}</td>
										<td>${item.SDT_NM}</td>
										<td>${item.RIVER_NM}</td>
										<td>${item.BSTRM_SE}</td>
										<td>${item.LA}</td>
										<td>${item.LO}</td>
										<td>${item.DE}</td>
										<td>${item.WETHR_SE}</td>
										<td>${item.EXAMIN_INSTT_NM}</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.RIVER_GRAD}</td>
										<td>${item.ALTTD}</td>
										<td>${item.RRSMLD_SE}</td>
										<td>${item.FILE_NM}</td>
										<td>${item.EXIMP_PARTCLR_MATTER}</td>
										<td>${item.EXIMP_DETAIL_CN}</td>
										<td>${item.EXPSS_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 서식수변 분석정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>자연적 종횡사주</th>
								<th>하도 정비 및 하도특성의 자연성 정도</th>
								<th>유속 다양성</th>
								<th>하천변 폭</th>
								<th>저수로 하안공</th>
								<th>제방하안 재료</th>
								<th>저질 상태</th>
								<th>횡구조물</th>
								<th>제외지 토지 이용</th>
								<th>제내지 토지 이용</th>
								<th>HRI</th>
								<th>건강성 등급</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.LBSB_CO_EVL}</td>
										<td>${item.NTRLTY_DGREE_EVL}</td>
										<td>${item.SPFLD_DVRSTY_EVL}</td>
										<td>${item.RVS_BT_EVL}</td>
										<td>${item.RSV_ARV_EVL}</td>
										<td>${item.ARV_MATRL_EVL}</td>
										<td>${item.SEDM_STTUS_EVL}</td>
										<td>${item.WDSTRCTU_DSTRBNC_EVL}</td>
										<td>${item.FRCE_LANDUSE_EVL}</td>
										<td>${item.PRLL_LANDUSE_EVL}</td>
										<td>${item.HRI}</td>
										<td>${item.HEALTH_GRAD}</td>
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
				<!-- 생물측정망 저서성대형무척추동물 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="16">공통자료</th>
								<th colspan="2">조사환경</th>
								<th colspan="4">조사방법</th>
								<th colspan="14">유역환경</th>
								<th colspan="15">수변환경</th>
								<th colspan="6">하상구조</th>
								<th colspan="10">하천현황</th>
								<th colspan="2">채집불가시</th>
								<th>채집가능시</th>
				            </tr>
				            <tr>
								<th rowspan="2">년도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="8">위치</th>
								<th>조사일</th>
								<th>날씨</th>
								<th colspan="2">조사수행</th>
								<th colspan="2">기상조건</th>
								<th>Surber net(30*30)</th>
								<th>Surber net(50*50)</th>
								<th>드렛지</th>
								<th>에크만</th>
								<th colspan="9">유역이용(해당시 1)</th>
								<th colspan="5">오염원(해당시 1)</th>
								<th colspan="3">식생</th>
								<th>수피도(canopy cover)</th>
								<th>범람원의이용</th>
								<th colspan="5">제방(좌안) 상류->하류 시야 (해당시 1)</th>
								<th colspan="5">제방(우안) 상류->하류 시야 (해당시 1)</th>
								<th colspan="6">하상구성</th>
								<th>하천유형</th>
								<th colspan="4">수리수문</th>
								<th colspan="3">지형</th>
								<th colspan="2">기타</th>
								<th colspan="2">항목(건천화, 공사중, 준설, 조사불가)</th>
								<th>특이사항</th>
							</tr>
							<tr>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>소권역</th>
								<th>하천명</th>
								<th>본류_지류 구분</th>
								<th>위도(도,분,초)</th>
								<th>경도(도,분,초)</th>
								<th>조사일(YYMMDD)</th>
								<th>1 맑음, 2 흐림, 3 비(눈)</th>
								<th>조사기관</th>
								<th>조사자</th>
								<th>기온(°C)</th>
								<th>수온(°C)</th>
								<th>조사회수</th>
								<th>조사회수</th>
								<th>조사회수</th>
								<th>조사회수</th>
								<th>산림</th>
								<th>목초지</th>
								<th>마을</th>
								<th>상가, 음식점</th>
								<th>농경지</th>
								<th>공장지대</th>
								<th>주거밀집지</th>
								<th>기타</th>
								<th>기타내용</th>
								<th>생활하수의유입</th>
								<th>축산폐수의유입</th>
								<th>공장폐수의유입</th>
								<th>기타</th>
								<th>기타내용</th>
								<th>교목(%)</th>
								<th>관목(%)</th>
								<th>초본(%)</th>
								<th>0 없음, 1 부분적, 2 짙음</th>
								<th>1 자연형, 2 농경지, 3 도로, 4 주차장, 5 산책로, 6 기타</th>
								<th>자연형</th>
								<th>석축</th>
								<th>돌망태</th>
								<th>콘크리트</th>
								<th>수직</th>
								<th>자연형</th>
								<th>석축</th>
								<th>돌망태</th>
								<th>콘크리트</th>
								<th>수직</th>
								<th>진흙이하(<0.063mm)</th>
								<th>모래(0.063-2mm)</th>
								<th>왕모래(2-16mm)</th>
								<th>자갈(16-64mm)</th>
								<th>작은돌(64-256mm)</th>
								<th>큰돌(>256mm)</th>
								<th>1 산간형, 2 평지형, 3 강, 4 호소, 5 인공구조물)</th>
								<th>하폭(m)</th>
								<th>수폭(m)</th>
								<th>평균수심(cm)</th>
								<th>평균유속(cm/s)</th>
								<th>여울(Riffle)</th>
								<th>흐름역(Run)</th>
								<th>소(Pool)</th>
								<th>투명도(매우 맑음 1 - 5 매우탁함)</th>
								<th>냄새(없음 1 - 5 매우심함)</th>
								<th>특이사항</th>
								<th>세부내용</th>
								<th>특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.WRSSM_NM}</td>
										<td>${item.DETAIL_WRSSM_NM}</td>
										<td>${item.MDT_NM}</td>
										<td>${item.SDT_NM}</td>
										<td>${item.RIVER_NM}</td>
										<td>${item.BSTRM_SE}</td>
										<td>${item.LA}</td>
										<td>${item.LO}</td>
										<td>${item.DE}</td>
										<td>${item.WETHR_SE}</td>
										<td>${item.EXAMIN_INSTT_NM}</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.TMPRT}</td>
										<td>${item.WTRTP}</td>
										<td>${item.SBN30_EXAMIN_CO}</td>
										<td>${item.SBN50_EXAMIN_CO}</td>
										<td>${item.DR_EXAMIN_CO}</td>
										<td>${item.EK_EXAMIN_CO}</td>
										<td>${item.DRGUSE_MTST_AT}</td>
										<td>${item.DRGUSE_FORAGE_AT}</td>
										<td>${item.DRGUSE_VILAGE_AT}</td>
										<td>${item.DRGUSE_SOPSRT_AT}</td>
										<td>${item.DRGUSE_FRLND_AT}</td>
										<td>${item.DRGUSE_FCTRY_AT}</td>
										<td>${item.DRGUSE_RESIDE_AT}</td>
										<td>${item.DRGUSE_ETC_AT}</td>
										<td>${item.DRGUSE_ETC_CN}</td>
										<td>${item.POLTNSRC_LVSEWG_AT}</td>
										<td>${item.POLTNSRC_LSKR_AT}</td>
										<td>${item.POLTNSRC_WSTR_AT}</td>
										<td>${item.POLTNSRC_ETC_AT}</td>
										<td>${item.POLTNSRC_ETC_CN}</td>
										<td>${item.RE_VTN_TRE_RT}</td>
										<td>${item.RE_VTN_SRB_RT}</td>
										<td>${item.RE_VTN_HRB_RT}</td>
										<td>${item.RE_CNPY_SE}</td>
										<td>${item.RE_FLD_USE_SE}</td>
										<td>${item.RE_LFTBNK_NATURE_AT}</td>
										<td>${item.RE_LFTBNK_STON_AT}</td>
										<td>${item.RE_LFTBNK_GABN_AT}</td>
										<td>${item.RE_LFTBNK_CNCRT_AT}</td>
										<td>${item.RE_LFTBNK_VERTCL_AT}</td>
										<td>${item.RE_RHTBNK_NATURE_AT}</td>
										<td>${item.RE_RHTBNK_STON_AT}</td>
										<td>${item.RE_RHTBNK_GABN_AT}</td>
										<td>${item.RE_RHTBNK_CNCRT_AT}</td>
										<td>${item.RE_RHTBNK_VERTCL_AT}</td>
										<td>${item.STR_MD_RT}</td>
										<td>${item.STR_SAND_RT}</td>
										<td>${item.STR_SBAL_RT}</td>
										<td>${item.STR_PEBB_RT}</td>
										<td>${item.STR_SS_RT}</td>
										<td>${item.STR_LS_RT}</td>
										<td>${item.RIVER_TY_SE}</td>
										<td>${item.BTRV}</td>
										<td>${item.WTRBT}</td>
										<td>${item.AVRG_DPWT}</td>
										<td>${item.AVRG_SPFLD}</td>
										<td>${item.RIVER_RAP_RT}</td>
										<td>${item.RIVER_FLOW_RT}</td>
										<td>${item.RIVER_POND_RT}</td>
										<td>${item.TRNSPRC_SE}</td>
										<td>${item.SMELL_SE}</td>
										<td>${item.CLIMP_PARTCLR_MATTER}</td>
										<td>${item.CLIMP_DETAIL_CN}</td>
										<td>${item.CLPSS_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 저서성대형무척추동물 분석정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th rowspan="2">연도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th rowspan="2">출현종수</th>
								<th rowspan="2">개체수</th>
								<th colspan="3">우점종 및 점유율</th>
								<th rowspan="2">다양도</th>
								<th rowspan="2">종풍부도</th>
								<th rowspan="2">균등도</th>
								<th colspan="4">지표종</th>
								<th rowspan="2">BMI</th>
								<th rowspan="2">건강성 등급</th>
				            </tr>
				            <tr>
								<th>학명</th>
								<th>점유율</th>
								<th>개체수현존량</th>
								<th>EPT출현종수</th>
								<th>EPT출현종수비</th>
								<th>EPT출현개체밀도</th>
								<th>EPT출현개체밀도비율</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.TOT_SPCS_CO}</td>
										<td>${item.TOT_INDVD_CO}</td>
										<td>${item.DKPAR_SCNCENM}</td>
										<td>${item.DKPAR_POSSESN_RT}</td>
										<td>${item.DKPAR_INDVD_CO}</td>
										<td>${item.DIV_IDEX}</td>
										<td>${item.RIC_IDEX}</td>
										<td>${item.EVN_IDEX}</td>
										<td>${item.EPT_SPCS_CO}</td>
										<td>${item.EPT_SPCS_RT}</td>
										<td>${item.EPT_INDVD_CO}</td>
										<td>${item.EPT_INDVD_RT}</td>
										<td>${item.BMI}</td>
										<td>${item.HEALTH_GRAD}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'C'}">
				<!-- 생물측정망 저서성대형무척추동물 출현생물 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>학명</th>
								<th>국명</th>
								<th>개체수</th>
								<th>외래종 여부</th>
								<th>멸종위기야생동물 I급 여부</th>
								<th>멸종위기야생동물 II급 여부</th>
								<th>국외반출승인종 여부</th>
								<th>EPT종 여부</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCNCENM}</td>
										<td>${item.KORNM}</td>
										<td>${item.INDVD_CO}</td>
										<td>${item.EXO_AT}</td>
										<td>${item.ENSPC_1CLS_AT}</td>
										<td>${item.ENSPC_2CLS_AT}</td>
										<td>${item.OTKCSP_AT}</td>
										<td>${item.EPT_AT}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'E'}">
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 생물측정망 식생 현지조사 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="3">번호</th>
								<th colspan="16">공통자료</th>
								<th colspan="6">면적</th>
								<th colspan="2">조사불가시</th>
								<th rowspan="2">조사가능시</th>
				            </tr>
				            <tr>
								<th rowspan="2">년도</th>
								<th rowspan="2">회차</th>
								<th rowspan="2">분류코드</th>
								<th rowspan="2">조사구간명</th>
								<th colspan="8">위치</th>
								<th>조사일</th>
								<th>날씨</th>
								<th colspan="2">조사수행</th>
								<th colspan="6">식생 외 면적</th>
								<th colspan="2">특이사항</th>
				            </tr>
				            <tr>
								<th>대권역</th>
								<th>수계</th>
								<th>중권역</th>
								<th>소권역</th>
								<th>하천명</th>
								<th>본류_지류 구분</th>
								<th>위도(도,분,초)</th>
								<th>경도(도,분,초)</th>
								<th>조사일(YYMMDD)</th>
								<th>1 맑음, 2 흐림, 3 비(눈)</th>
								<th>조사기관</th>
								<th>조사자</th>
								<th>수로</th>
								<th>나지</th>
								<th>인공구조물</th>
								<th>산림식생</th>
								<th>주거지 및 상업시설</th>
								<th>경작지</th>
								<th>항목(접근불가, 건천화, 공사중, 준설 中)</th>
								<th>세부내용</th>
								<th>특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.WRSSM_NM}</td>
										<td>${item.DETAIL_WRSSM_NM}</td>
										<td>${item.MDT_NM}</td>
										<td>${item.SDT_NM}</td>
										<td>${item.RIVER_NM}</td>
										<td>${item.BSTRM_SE}</td>
										<td>${item.LA}</td>
										<td>${item.LO}</td>
										<td>${item.DE}</td>
										<td>${item.WETHR_SE}</td>
										<td>${item.EXAMIN_INSTT_NM}</td>
										<td>${item.EXMNR_NM}</td>
										<td>${item.WTCORS_AR}</td>
										<td>${item.BRGRD_AR}</td>
										<td>${item.ARSTRU_AR}</td>
										<td>${item.MTST_AR}</td>
										<td>${item.RESIDE_AR}</td>
										<td>${item.CLVT_AR}</td>
										<td>${item.EXIMP_PARTCLR_MATTER}</td>
										<td>${item.EXIMP_DETAIL_CN}</td>
										<td>${item.EXPSS_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 생물측정망 식생 분석정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th rowspan="2">번호</th>
								<th colspan="4">공통자료</th>
								<th colspan="10">우점군락수</th>
								<th colspan="10">우점면적</th>
								<th colspan="6">평가요소</th>
								<th colspan="6">평가점수</th>
								<th rowspan="2">전체군집수</th>
								<th rowspan="2">전체면적</th>
								<th rowspan="2">RVI</th>
								<th rowspan="2">건강성등급</th>
				            </tr>
				            <tr>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>Salix속출현우점군락수비율</th>
								<th>벼과출현우점군락수비율</th>
								<th>사초과출현우점군락수비율</th>
								<th>절대습지식물(습지출현빈도-OBL)출현우점군락수비율</th>
								<th>임의습지식물(습지출현빈도-FACW)출현우점군락수비율</th>
								<th>절대및임의습지식물(OBL+FACW)출현우점군락수비율</th>
								<th>아교목및교목(subtree+tree)출현우점군락수비율</th>
								<th>귀화종출현우점군락수비율</th>
								<th>생태계교란식물출현우점군락수비율</th>
								<th>버드나무속/오리나무속/느릅나무속/물푸레나무속우점군락수비율</th>
								<th>Salix속출현우점면적비율</th>
								<th>벼과출현우점면적비율</th>
								<th>사초과출현우점면적비율</th>
								<th>절대습지식물(습지출현빈도-OBL)출현우점면적비율</th>
								<th>임의습지식물(습지출현빈도-FACW)출현우점면적비율</th>
								<th>절대및임의습지식물(OBL+FACW)출현우점면적비율</th>
								<th>아교목및교목(subtree+tree)출현우점면적비율</th>
								<th>귀화종출현우점면적비율</th>
								<th>생태계교란식물출현우점면적비율</th>
								<th>버드나무속/오리나무속/느릅나무속/물푸레나무속우점면적비율</th>
								<th>일년생초본우점면적비율(HAA)</th>
								<th>외래종우점면적비율(EA)</th>
								<th>습지식물균등도(WTD)</th>
								<th>버드나무속및물푸레나무속우점면적비율(SalFraA)</th>
								<th>내성종출현종수비율(ToSC)</th>
								<th>식생단면안정성(BTI)</th>
								<th>일년생초본우점면적비율(HAA)</th>
								<th>외래종우점면적비율(EA)</th>
								<th>습지식물균등도(WTD)</th>
								<th>버드나무속및물푸레나무속우점면적비율(SalFraA)</th>
								<th>내성종출현종수비율(ToSC)</th>
								<th>식생단면안정성(BTI)</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.DOMPT_SALX_RT}</td>
										<td>${item.DOMPT_GRMN_RT}</td>
										<td>${item.DOMPT_CPRC_RT}</td>
										<td>${item.DOMPT_OBL_RT}</td>
										<td>${item.DOMPT_FACW_RT}</td>
										<td>${item.DOMPT_OBLFACW_RT}</td>
										<td>${item.DOMPT_TRE_RT}</td>
										<td>${item.DOMPT_NATSPCS_RT}</td>
										<td>${item.DOMPT_DISSPCS_RT}</td>
										<td>${item.DOMPT_APGEN_RT}</td>
										<td>${item.DOMAR_SALX_RT}</td>
										<td>${item.DOMAR_GRMN_RT}</td>
										<td>${item.DOMAR_CPRC_RT}</td>
										<td>${item.DOMAR_OBL_RT}</td>
										<td>${item.DOMAR_FACW_RT}</td>
										<td>${item.DOMAR_OBLFACW_RT}</td>
										<td>${item.DOMAR_TRE_RT}</td>
										<td>${item.DOMAR_NATSPCS_RT}</td>
										<td>${item.DOMAR_DISSPCS_RT}</td>
										<td>${item.DOMAR_APGEN_RT}</td>
										<td>${item.EVL_HAA_RT}</td>
										<td>${item.EVL_EA_RT}</td>
										<td>${item.EVL_WTD_RT}</td>
										<td>${item.EVL_SALFRAA_RT}</td>
										<td>${item.EVL_TOSC_RT}</td>
										<td>${item.EVL_BTI_RT}</td>
										<td>${item.EVL_HAA_SCORE}</td>
										<td>${item.EVL_EA_SCORE}</td>
										<td>${item.EVL_WTD_SCORE}</td>
										<td>${item.EVL_SALFRAA_SCORE}</td>
										<td>${item.EVL_TOSC_SCORE}</td>
										<td>${item.EVL_BTI_SCORE}</td>
										<td>${item.TOT_COMM_CO}</td>
										<td>${item.TOT_AR}</td>
										<td>${item.RVI}</td>
										<td>${item.HEALTH_GRAD}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'C'}">
				<!-- 생물측정망 식생 출현식생 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>학명</th>
								<th>국명</th>
								<th>출현 여부</th>
								<th>습지출현빈도</th>
								<th>생장형</th>
								<th>귀화종 여부</th>
								<th>재배종 여부</th>
								<th>외래종 여부</th>
								<th>버드나무 및 물푸레나무 속 여부</th>
								<th>버드나무+물푸레나무+오리나무+느룹나무속 여부</th>
								<th>내성종 여부</th>
								<th>생태교란종 여부</th>
								<th>희귀종 여부</th>
								<th>고유종 여부</th>
								<th>멸종위기종 여부</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCNCENM}</td>
										<td>${item.KORNM}</td>
										<td>${item.ERE_AT}</td>
										<td>${item.SMLD_ERE_CODE}</td>
										<td>${item.GROWFRM_CODE}</td>
										<td>${item.NATSPCS_AT}</td>
										<td>${item.CULSP_AT}</td>
										<td>${item.EXO_AT}</td>
										<td>${item.APPN_1_AT}</td>
										<td>${item.APPN_2_AT}</td>
										<td>${item.TOE_AT}</td>
										<td>${item.DIS_AT}</td>
										<td>${item.RARSP_AT}</td>
										<td>${item.END_AT}</td>
										<td>${item.ENSPC_AT}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'D'}">
				<!-- 생물측정망 식생 식생면적 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>학명</th>
								<th>국명</th>
								<th>면적</th>
								<th>습지출현빈도</th>
								<th>생장형</th>
								<th>귀화종 여부</th>
								<th>재배종 여부</th>
								<th>외래종 여부</th>
								<th>버드나무 및 물푸레나무 속 여부</th>
								<th>버드나무+물푸레나무+오리나무+느룹나무속 여부</th>
								<th>내성종 여부</th>
								<th>생태교란종 여부</th>
								<th>희귀종 여부</th>
								<th>고유종 여부</th>
								<th>멸종위기종 여부</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCNCENM}</td>
										<td>${item.KORNM}</td>
										<td>${item.AR}</td>
										<td>${item.SMLD_ERE_CODE}</td>
										<td>${item.GROWFRM_CODE}</td>
										<td>${item.NATSPCS_AT}</td>
										<td>${item.CULSP_AT}</td>
										<td>${item.EXO_AT}</td>
										<td>${item.APPN_1_AT}</td>
										<td>${item.APPN_2_AT}</td>
										<td>${item.TOE_AT}</td>
										<td>${item.DIS_AT}</td>
										<td>${item.RARSP_AT}</td>
										<td>${item.END_AT}</td>
										<td>${item.ENSPC_AT}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'E'}">
				<!-- 생물측정망 식생 단면정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>시점_위도</th>
								<th>시점_경도</th>
								<th>종점_위도</th>
								<th>종점_경도</th>
								<th>위치</th>
								<th>시점에서의 각도</th>
								<th>시점의 목표물</th>
								<th>제외지 하천구조 구분</th>
								<th>제외지 교란 구분</th>
								<th>제내지 우안 구분</th>
								<th>제내지 우안 구분</th>
								<th>조사특이사항</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.PNTTM_LA}</td>
										<td>${item.PNTTM_LO}</td>
										<td>${item.TMNL_LA}</td>
										<td>${item.TMNL_LO}</td>
										<td>${item.LC}</td>
										<td>${item.PNTTM_ANGLE}</td>
										<td>${item.PNTTM_TARGT}</td>
										<td>${item.FRCE_RVSTRCT_SE}</td>
										<td>${item.FRCE_DSTRB_SE}</td>
										<td>${item.PRLL_LFTBNK_SE}</td>
										<td>${item.PRLL_RHTBNK_SE}</td>
										<td>${item.EXIMP_PARTCLR_MATTER}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'F'}">
				<!-- 생물측정망 식생 단면 구간별 정보 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>단면번호</th>
								<th>거리</th>
								<th>각도</th>
								<th>상대 높이</th>
								<th>수심</th>
								<th>하상 재질</th>
								<th>나지 구분</th>
								<th>군집</th>
								<th>교목 높이</th>
								<th>교목 피도</th>
								<th>아교목 높이</th>
								<th>야교목 피도</th>
								<th>관목 높이</th>
								<th>관목 피도</th>
								<th>초본 높이</th>
								<th>초본 피도</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCTN_NO}</td>
										<td>${item.DSTNC}</td>
										<td>${item.ANGLE}</td>
										<td>${item.PARTN_HG}</td>
										<td>${item.DPWT}</td>
										<td>${item.RVSHP_MTRQLT}</td>
										<td>${item.BRGRD_SE}</td>
										<td>${item.COMM}</td>
										<td>${item.TRE_HG}</td>
										<td>${item.TRE_CV}</td>
										<td>${item.ARBSC_HG}</td>
										<td>${item.ARBSC_CV}</td>
										<td>${item.SRB_HG}</td>
										<td>${item.SRB_CV}</td>
										<td>${item.HRB_HG}</td>
										<td>${item.HRB_CV}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'G'}">
				<!-- 생물측정망 식생 우점도 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="검색결과 표">
				        <caption>검색결과</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>회차</th>
								<th>분류코드</th>
								<th>조사구간명</th>
								<th>단면번호</th>
								<th>국명</th>
								<th>우점도 (R,+,1~5)</th>
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
										<td>${item.YEAR}</td>
										<td>${item.TME}</td>
										<td>${item.SPOT_CODE}</td>
										<td>${item.SPOT_NM}</td>
										<td>${item.SCTN_NO}</td>
										<td>${item.KORNM}</td>
										<td>${item.DOM_IDEX}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			
		</c:choose>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'F'}">
		<!-- 생물측정망 수질 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="검색결과 표">
		        <caption>검색결과</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>연도</th>
						<th>차수</th>
						<th>분류코드</th>
						<th>조사구간명</th>
						<th>수온(°C)</th>
						<th>DO(mg/L)</th>
						<th>pH</th>
						<th>전기전도도(uS/cm)</th>
						<th>탁도(NTU)</th>
						<th>BOD(mg/L)</th>
						<th>NH3-N(mg/L)</th>
						<th>NO3-N(mg/L)</th>
						<th>T-N(mg/L)</th>
						<th>PO4-P(mg/L)</th>
						<th>T-P(mg/L)</th>
						<th>Chl-a(mg/m3)</th>
						<th>SS(mg/L)</th>
						<th>비고</th>
						<th>세부사항</th>
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
								<td>${item.YEAR}</td>
								<td>${item.TME}</td>
								<td>${item.SPOT_CODE}</td>
								<td>${item.SPOT_NM}</td>
								<td>${item.WTRTP}</td>
								<td>${item.DOC}</td>
								<td>${item.PH}</td>
								<td>${item.EC}</td>
								<td>${item.TUR}</td>
								<td>${item.BOD}</td>
								<td>${item.NH3N}</td>
								<td>${item.NO3N}</td>
								<td>${item.TN}</td>
								<td>${item.PO4P}</td>
								<td>${item.TP}</td>
								<td>${item.CHLA}</td>
								<td>${item.SS}</td>
								<td>${item.RM}</td>
								<td>${item.DETAIL_MATTER}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
</c:choose>

<div class="paging" id="paging">
	<%@ include file="/common/pager.jsp"%>
</div>



