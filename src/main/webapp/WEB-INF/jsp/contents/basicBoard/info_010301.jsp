<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	
	var _initVal = "";
	$(document).ready(function(){
		$(":radio[name='siteTypeItem']").on('change', function(){
			setAllItemClear();
			setSiteTypeItem();
		});
		$('#select_area').on('change', function(){
			_initVal = "N";
			distirctListAjax($(this).val());
		});
		$('#select_water_sys').on('change', function(){
			_initVal = "N";
			waterSysListAjax($(this).val());
		});
		
		$('#select_area_search').on('change', function(){
			_initVal = "N";
			distirctListAjax($(this).val());
		});
		$('#select_water_sys_search').on('change', function(){
			_initVal = "N";
			waterSysListAjax($(this).val());
		});
		
		$('#select_area_search_btn').on('click', function(){
			_initVal = "N";
			searchSite();
		});
		$('#select_water_sys_search_btn').on('click', function(){
			_initVal = "N";
			searchWaterSys();
		});
		$('#area_nm_search_btn').on('click', function(){
			_initVal = "N";
			searchName();
		});
		
		$('#excelBtn').on('click', function(){
			var resultCount = $("#resultCount").val();
			var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
			
			if(resultCount >= 60000){
				alert("데이터가 많아  최신의 6만 건만 출력 됩니다.");
			}
			$("#downType").val("xls");
			
			if(varSiteType == "D" || varSiteType == "E"){
				resultSearch02();
			}else{
				resultSearch01();
			}
		});
		$('#csvBtn').on('click', function(){
			var resultCount = $("#resultCount").val();
			var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
			
			if(resultCount >= 60000){
				alert("데이터가 많아  최신의 6만 건만 출력 됩니다.");
			}
			$("#downType").val("csv");
			
			if(varSiteType == "D" || varSiteType == "E"){
				resultSearch02();
			}else{
				resultSearch01();
			}
		});
		$('#srcBtn').on('click', function(){
			var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
			if(varSiteType == "D" || varSiteType == "E"){
				resultSearch02();
			}else{
				resultSearch01();
			}
		});
		
		if("${param.siteTypeItem}" != null && "${param.siteTypeItem}" != ""){
			setSiteTypeItem();
		}
		if("${param.select_area}" != null && "${param.select_area}" != ""){
			_initVal = "N";
			distirctListAjax("${param.select_area}");
		}
		if("${param.select_water_sys}" != null && "${param.select_water_sys}" != ""){
			_initVal = "N";
			waterSysListAjax("${param.select_water_sys}");
		}
		
		if("${param.select_area_search}" != null && "${param.select_area_search}" != ""){
			_initVal = "Y";
			distirctListAjax("${param.select_area_search}");
		}
		if("${param.select_water_sys_search}" != null && "${param.select_water_sys_search}" != ""){
			_initVal = "Y";
			waterSysListAjax("${param.select_water_sys_search}");
		}
		if("${param.area_nm_search}" != null && "${param.area_nm_search}" != ""){
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
		var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		//2단계
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#select_area_search_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_search_sub").html("<option value=''>선택</option>");
		$("#second_item01").find("select, input").val("");
		$("#second_item02").find("select, input").val("");
		
		//3단계
		$("#date_item").find("select").val("");
		
		//검색결과
		tableHtml01 += "<tr class='odd3'>";
		tableHtml01 += "   <td colspan='3'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml01 += "</tr>";
		
		tableHtml02 += "<tr class='odd3'>";
		tableHtml02 += "   <td colspan='100'>검색 결과가 존재하지 않습니다.</td>";
		tableHtml02 += "</tr>";
		
		$("#siteTable").html(tableHtml01);
		$("#resultSiteTable").html(tableHtml02);
		$("#resultCnt").text("조회현황 : 0 건");
		$("#divTab").html("");
		$("#paging").html("");
		
		if(varSiteType == "D" || varSiteType == "E"){
			setItemOff();
		}else{
			setItemOpen();
		}
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
	
	//분류 검색 조건 변경
	function setSiteTypeItem(){
		var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
		$("#area_item01").css("display", "none");
		$("#area_item02").css("display", "none");
		if(varSiteType == "D" || varSiteType == "E"){
			setItemOff();
			$("#area_item02").css("display", "block");
		}else{
			$("#area_item01").css("display", "block");
		}
	}
	
	//탭 설정
	function setTabItem(){
		$("#landFillTab01").attr("class", "");
		$("#landFillTab02").attr("class", "");
		$("#faciTab01").attr("class", "");
		$("#faciTab02").attr("class", "");
		$("#faciTab03").attr("class", "");
		$("#faciTab04").attr("class", "");
		$("#faciTab05").attr("class", "");
		
		var varSiteTypeItem = "${param.siteTypeItem}";
		var varSiteTypeItemSub = "${param.siteTypeItemSub}";
		
		if(varSiteTypeItem == "D"){
			if(varSiteTypeItemSub == "A"){
				$("#landFillTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#landFillTab02").attr("class", "on");
			}
		}else if(varSiteTypeItem == "E"){
			if(varSiteTypeItemSub == "A"){
				$("#faciTab01").attr("class", "on");
			}else if(varSiteTypeItemSub == "B"){
				$("#faciTab02").attr("class", "on");
			}else if(varSiteTypeItemSub == "C"){
				$("#faciTab03").attr("class", "on");
			}else if(varSiteTypeItemSub == "D"){
				$("#faciTab04").attr("class", "on");
			}else if(varSiteTypeItemSub == "E"){
				$("#faciTab05").attr("class", "on");
			}
		}
	}
	
	//탭 선택
	function selTabItem(divId){
		$("#siteTypeItemSub").val(divId);
		resultSearch02();
	}
	
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
		var varSelect_area_search = "${param.select_area_search}";
		var varSelect_area_search_sub = "${param.select_area_search_sub}";
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("select[id=select_water_sys_search]").val("");
		$("#select_water_sys_search_sub").html("<option value=''>선택</option>");
		$("#area_nm_search").val("");
		$("#select_area_search_sub").html("<option value=''>선택</option>");
		
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
				$("#select_area_search_sub").html("");
				$("#select_area_search_sub").append(selOpt);
				
				if(_initVal == "Y"){
					if((varSelect_area_search != null && varSelect_area_search != "") || (varSelect_area_search_sub != null && varSelect_area_search_sub != "")){
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
		var varSelect_water_sys_search = "${param.select_water_sys_search}";
		var varSelect_water_sys_search_sub = "${param.select_water_sys_search_sub}";
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("select[id=select_area_search]").val("");
		$("#select_area_search_sub").html("<option value=''>선택</option>");
		$("#area_nm_search").val("");	
		$("#select_water_sys_search_sub").html("<option value=''>선택</option>");
		
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
				$("#select_water_sys_search_sub").html("");
				$("#select_water_sys_search_sub").append(selOpt);
				
				if(_initVal == "Y"){
					if((varSelect_water_sys_search != null && varSelect_water_sys_search != "") || (varSelect_water_sys_search_sub != null && varSelect_water_sys_search_sub != "")){
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
        var getAdmCd = $("#select_area_search").val();
        var getAdmCdSub = $("#select_area_search_sub").val();
        var admCd = "";
        var searchType = "1";
        var doType = "";
        
        if(getAdmCd == null || getAdmCd == ""){
        	alert("행정구역을 선택하세요.");
        	$("#select_area_search").focus();
        	return false;
        }
        
       	admCd = getAdmCd;
        if(getAdmCdSub != null && getAdmCdSub != ""){
        	admCd = getAdmCdSub;
        	searchType = "2";
        }
        
        if(getSiteType == "D"){
        	doType = "landfill";
        }else if(getSiteType == "E"){
        	doType = "facility";
        }
        
		var param = { TYPE_CD:getSiteType, ADM_CD:admCd, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//수계 검색
	function searchWaterSys(){
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getWsCd = $("#select_water_sys_search").val();
        var getAmCd = $("#select_water_sys_search_sub").val();
        var searchType = "3";
        var doType = "";
        
        if(getWsCd == null || getWsCd == ""){
        	alert("수계를 선택하세요.");
        	$("#select_water_sys_search").focus();
        	return false;
        }
        
        if(getAmCd != null && getAmCd != ""){
        	searchType = "4";
        }
        
        if(getSiteType == "D"){
        	doType = "landfill";
        }else if(getSiteType == "E"){
        	doType = "facility";
        }
        
		var param = { TYPE_CD:getSiteType, WS_CD:getWsCd, AM_CD:getAmCd, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
        searchSiteAjax(param);
        setItemOpen();
	}
	
	//이름 검색
	function searchName(){
		$("select[id=select_area_search]").val("");
		$("#select_area_search_sub").html("<option value=''>선택</option>");
		$("select[id=select_water_sys_search]").val("");
		$("#select_water_sys_search_sub").html("<option value=''>선택</option>");
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
        var getName = $("#area_nm_search").val();
        var searchType = "5";
        var doType = "";
        
        if( getName == null || getName == ""){
        	alert("이름을 입력하세요.");
        	$("#area_nm_search").focus();
        	return false;
        }
        
        if(getSiteType == "D"){
        	doType = "landfill";
        }else if(getSiteType == "E"){
        	doType = "facility";
        }
        
        var param = { TYPE_CD:getSiteType, ST_NM:getName, SEARCH_TYPE:searchType, DO_TYPE:doType };
        
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
						}else if(_initVal == "Y" && rtnAdm_cd.indexOf(item.FACI_CD) > -1){
							checked = "checked";
						}else{
							checked = "";
						}
						
						selOpt += "<tr class='odd3'>";
						selOpt += "   <td><input name='chkSite' id='chkSite' type='checkbox' value='" + item.FACI_CD + "' class='item' "+checked+"/></td>";
						selOpt += "   <td>"+item.FACI_NM+"</td>";
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
	
	//검색결과 다운로드
	function docDown(){
		var varSiteType = $(":radio[name='siteTypeItem']:checked").val();
		var varSiteTypeItemSub = "${param.siteTypeItemSub}";
		
		if(varSiteType == 'A'){
			//생활계
			$("#mId").val("excelPopTotalList");
		}else if(varSiteType == 'B'){
			//양식계
			$("#mId").val("excelFishfarmList");
		}else if(varSiteType == 'C'){
			//기타수질오염원
			$("#mId").val("excelOpsTotalList");
		}else if(varSiteType == 'D'){
			//매립장
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelLandfillBasicList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelLandfillLeachList");
			}
		}else if(varSiteType == 'E'){
			//환경기초시설
			if(varSiteTypeItemSub == "" || varSiteTypeItemSub == "A"){
				$("#mId").val("excelFacilityDetailList");	
			}else if(varSiteTypeItemSub == "B"){
				$("#mId").val("excelFaciInTotalList");
			}else if(varSiteTypeItemSub == "C"){
				$("#mId").val("excelFaciPipeTransferList");
			}else if(varSiteTypeItemSub == "D"){
				$("#mId").val("excelFaciDirectTransferList");
			}else if(varSiteTypeItemSub == "E"){
				$("#mId").val("excelFaciOutTotalList");
			}
		}else if(varSiteType == 'F'){
			//토지계
			$("#mId").val("excelLanduseList");
		}else if(varSiteType == 'G'){
			//산업계
			$("#mId").val("excelIndTotalList");
		}else if(varSiteType == 'H'){
			//축산계
			$("#mId").val("excelAnimalTotalList");
		}
		
		fnSubmit("resultFrm", "/weis_board/egov/cms/excel/Download");
		$("#downType").val("N");
	}
	
	//검색 결과(생활계, 양식계, 기타수질오염원, 토지계, 산업계, 축산계)
	function resultSearch01(){
		var varSelectArea = $("#select_area").val();
		var varSelectAreaSub = $("#select_area_sub").val();
		var varSelectWaterSys = $("#select_water_sys").val();
		var varSelectWaterSysSub = $("#select_water_sys_sub").val();
		
		var startYear = $("select[id=select_start_year]").val();
		var endYear = $("select[id=select_end_year]").val();
		
		var varDownType = $("#downType").val();
		
		if( (varSelectArea == null || varSelectArea == "")
		&& (varSelectAreaSub == null || varSelectAreaSub == "")
		&& (varSelectWaterSys == null || varSelectWaterSys == "")
		&& (varSelectWaterSysSub == null || varSelectWaterSysSub == "")){
        	alert("검색조건을 선택하세요.");
        	$("#select_area").focus();
        	return false;
        }
		
		if( startYear == null || startYear == ""){
        	alert("시작일시년도를 선택하세요.");
        	$("#select_start_year").focus();
        	return false;
        }
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.");
        	$("#select_end_year").focus();
        	return false;
        }
		
		if(varDownType == "xls" || varDownType == "csv" ){
			docDown();
		}else{
			$("#doSearch").val("Y");
			fnSubmit("resultFrm", "/weis_board/egov/contents/site/basicBoardInfo");
		}
	}
	
	//검색 결과(매립계, 환경기초시설)
	function resultSearch02(){
		var chked_val = "";
		var startYear = $("select[id=select_start_year]").val();
		var endYear = $("select[id=select_end_year]").val();
		
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
		
		if( endYear == null || endYear == ""){
        	alert("종료일시년을 선택하세요.");
        	$("#select_end_year").focus();
        	return false;
        }
		
		$("#adm_cd").val(chked_val);	
		
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
	                    	<input name="siteTypeItem" type="radio" id="siteType_A" title="생활계" value="A" ${'A' eq param.siteTypeItem or empty param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_A" >생활계</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="양식계" value="B" ${'B' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_B" >양식계</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_C" title="기타수질오염원" value="C" ${'C' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_C" >기타수질오염원</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_D" title="매립계" value="D" ${'D' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_D" >매립계</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_E" title="환경기초시설" value="E" ${'E' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_E" >환경기초시설</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_F" title="토지계" value="F" ${'F' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_F" >토지계</label>
	                    </dd>
	                </dl>
	                <dl class="rad">
	                	<dd style="padding-left:45px;padding-right:45px;margin-right:33px;">&nbsp;</dd>
	                    <dd>
	                        <input name="siteTypeItem" type="radio" id="siteType_G" title="산업계" value="G" ${'G' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_G" >산업계</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_H" title="축산계" value="H" ${'H' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_H" >축산계</label>
	                    </dd>
	                </dl>
	           </div>
	       </dd>
	   	</dl>
	    <dl>
			<dt><b>2단계</b> 지역선택</dt>
	       	<dd>
				<div class="cond">
					<div id="area_item01">
						<div id="second_item01">
							<dl>
								<dt>행정구역</dt>
	                           	<dd>
	                               	<select class="W10p L0" name="select_area" id="select_area">
	                               		<option value="">선택</option>
	        							<c:forEach var="item" items="${optionList_1}" varStatus="idx">
	                                   		<option value="${item.ADM_CD }" ${item.ADM_CD eq param.select_area ? 'selected="selected"' : ''}>${item.DO_NM }</option>
	                                   	</c:forEach>
	                               	</select>
	                               
	                               	<select class="W10p L0" name="select_area_sub" id="select_area_sub">
	                                   	<option value="">선택</option>
	                               	</select>
	                           	</dd>
	                           	<dt class="MgL70">수계</dt>
	                           	<dd>
	                               	<select class="W10p L0" name="select_water_sys" id="select_water_sys">
	                                   	<option value="">선택</option>
	                                   	<c:forEach var="item" items="${optionList_2}" varStatus="idx">
	                                   		<option value="${item.CODE }" ${item.CODE eq param.select_water_sys ? 'selected="selected"' : ''}>${item.CODE_NM }</option>
	                                   	</c:forEach>
	                               	</select>
	                               
	                               	<select class="W10p L0" name="select_water_sys_sub" id="select_water_sys_sub">
	                                   	<option value="">선택</option>
	                               	</select>
	                           	</dd>
	                       	</dl>
	                   	</div>
                   	</div>
				
					<div id="area_item02" style="display:none;">
						<div class="divi">
			               	<div id="second_item02">
								<dl>
									<dt>행정구역</dt>
		                           	<dd>
		                               	<select class="W21p" name="select_area_search" id="select_area_search">
		                               		<option value="">선택</option>
		        							<c:forEach var="item" items="${optionList_1}" varStatus="idx">
		                                   		<option value="${item.ADM_CD }" ${item.ADM_CD eq param.select_area_search ? 'selected="selected"' : ''}>${item.DO_NM }</option>
		                                   	</c:forEach>
		                               	</select>
		                               
		                               	<select class="W21p" name="select_area_search_sub" id="select_area_search_sub">
		                                   	<option>선택</option>
		                               	</select>
		                               	<a class="btn01" name="select_area_search_btn" id="select_area_search_btn" href="#">검색</a>
		                           	</dd>
		                       	</dl>
			                       
		                       	<dl>
		                           	<dt>수계</dt>
		                           	<dd>
		                               	<select class="W21p" name="select_water_sys_search" id="select_water_sys_search">
		                                   	<option value="">선택</option>
		                                   	<c:forEach var="item" items="${optionList_2}" varStatus="idx">
		                                   		<option value="${item.CODE }" ${item.CODE eq param.select_water_sys_search ? 'selected="selected"' : ''}>${item.CODE_NM }</option>
		                                   	</c:forEach>
		                               	</select>
		                               
		                               	<select class="W21p" name="select_water_sys_search_sub" id="select_water_sys_search_sub">
		                                   	<option>선택</option>
		                               	</select>
		                               	<a class="btn01" name="select_water_sys_search_btn" id="select_water_sys_search_btn" href="#">검색</a>
		                           	</dd>
		                       	</dl>
			                       
		                       	<dl>
		                           	<dt>이름</dt>
		                           	<dd>
										<label style="display: none;">지역명</label>
										<input type="text" style="width:39.5% !important;" name="area_nm_search"  id="area_nm_search"  value="${param.area_nm_search}">
		                               	<a class="btn01" name="area_nm_search_btn" id="area_nm_search_btn" href="#">검색</a>
		                           	</dd>
								</dl>
		                   	</div>
			                   
		                   	<div>
			                   	<div class="box">
			                       	<table class="st01" summary="처리시설명, 주소로 이루어진 표">
										<caption>측정소 선택하기</caption>
										<colgroup>
											<col width="6%" />
											<col width="29%" />
											<col />
										</colgroup>
			                            <thead>
											<tr>
												<th><input class="checkAll" name="" type="checkbox" value="" /></th>
			                                    <th>처리시설명</th>
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
		<c:when test="${param.siteTypeItem eq 'D'}">
			<ul class="tab" id="tabItem01">
	    		<li class="on" id="landFillTab01"><a href="javascript:selTabItem('A');">매립장 기본현황</a></li>
			    <li id="landFillTab02"><a href="javascript:selTabItem('B');">매립장침출수</a></li>
			</ul>
		</c:when>
		<c:when test="${param.siteTypeItem eq 'E'}">
			<ul class="tab" id="tabItem02">
			    <li class="on" id="faciTab01"><a href="javascript:selTabItem('A');">기본현황</a></li>
			    <li id="faciTab02"><a href="javascript:selTabItem('B');">총유입량</a></li>
			    <li id="faciTab03"><a href="javascript:selTabItem('C');">관거이송량</a></li>
			    <li id="faciTab04"><a href="javascript:selTabItem('D');">직접이송량</a></li>
			    <li id="faciTab05"><a href="javascript:selTabItem('E');">방류유량</a></li>
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
		<!-- 전국오염원조사 생활계 목록 -->
		<div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th rowspan="3">번호</th>
						<th rowspan="3">연도</th>
						<th rowspan="3">법정동코드</th>
						<th rowspan="3">시도(법정동)</th>
						<th rowspan="3">시군구(법정동)</th>
						<th rowspan="3">읍면동(법정동)</th>
						<th rowspan="3">동리(법정동)</th>
						<th colspan="3">면적(㎢)</th>
						<th colspan="2">지정내역</th>
						<th colspan="3">하수처리시설</th>
						<th colspan="3">분뇨처리시설</th>
						<th colspan="21">인구(명)</th>
		            </tr>
		            <tr>
						<th rowspan="2">하수처리구역</th>
						<th rowspan="2">하수미처리구역</th>
						<th rowspan="2">계</th>
						<th rowspan="2">종류</th>
						<th rowspan="2">편입일자</th>
						<th rowspan="2">코드</th>
						<th rowspan="2">편입일자</th>
						<th rowspan="2">시설명</th>
						<th rowspan="2">코드</th>
						<th rowspan="2">편입일자</th>
						<th rowspan="2">시설명</th>
						<th rowspan="2">합계</th>
						<th colspan="10">시가</th>
						<th colspan="10">비시가</th>
					</tr>
					<tr>
						<th>소계</th>
						<th>하수처리 소계</th>
						<th>분류식 공공하수 계</th>
						<th>분류식 폐수종말 계</th>
						<th>합류식 공공하수 계</th>
						<th>합류식 폐수종말 계</th>
						<th>하수미처리 소계</th>
						<th>오수</th>
						<th>정화조</th>
						<th>수거식</th>
						<th>소계</th>
						<th>하수처리 소계</th>
						<th>분류식 공공하수 계</th>
						<th>분류식 폐수종말 계</th>
						<th>합류식 공공하수 계</th>
						<th>합류식 폐수종말 계</th>
						<th>하수미처리 소계</th>
						<th>오수</th>
						<th>정화조</th>
						<th>수거식</th>
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
								<td>${item.RNUM}</td>
								<td>${item.YYYY}</td>
								<td>${item.ADM_CD}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.AREA_A1}</td>
								<td>${item.AREA_A2}</td>
								<td>${item.AREA_SUM}</td>
								<td>${item.REGION}</td>
								<td>${item.REGION_DATE}</td>
								<td>${item.U_A1_TP_CODE}</td>
								<td>${item.U_A1_TP_DATE}</td>
								<td>${item.U_A1_TP_NAME}</td>
								<td>${item.U_A3_TP_CODE}</td>
								<td>${item.U_A3_TP_DATE}</td>
								<td>${item.U_A3_TP_NAME}</td>
								<td>${item.POP_SUM}</td>
								<td>${item.UPOP_SUM}</td>
								<td>${item.UPOP_A1_SUM}</td>
								<td>${item.UPOP_A1_SEPARATE_WT_SUM}</td>
								<td>${item.UPOP_A1_SEPARATE_IT_SUM}</td>
								<td>${item.UPOP_A1_COMBINED_WT_SUM}</td>
								<td>${item.UPOP_A1_COMBINED_IT_SUM}</td>
								<td>${item.UPOP_A2_SUM}</td>
								<td>${item.UPOP_A2_SANITARY}</td>
								<td>${item.UPOP_A2_SEPTIC}</td>
								<td>${item.UPOP_A2_REMOVAL}</td>
								<td>${item.SPOP_SUM}</td>
								<td>${item.SPOP_A1_SUM}</td>
								<td>${item.SPOP_A1_SEPARATE_WT_SUM}</td>
								<td>${item.SPOP_A1_SEPARATE_IT_SUM}</td>
								<td>${item.SPOP_A1_COMBINED_WT_SUM}</td>
								<td>${item.SPOP_A1_COMBINED_IT_SUM}</td>
								<td>${item.SPOP_A2_SUM}</td>
								<td>${item.SPOP_A2_SANITARY}</td>
								<td>${item.SPOP_A2_SEPTIC}</td>
								<td>${item.SPOP_A2_REMOVAL}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'B'}">
		<!-- 전국오염원조사 양식계 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>연도</th>
						<th>수계</th>
						<th>중권역</th>
						<th>법정동코드</th>
						<th>시도(법정동)</th>
						<th>시군구(법정동)</th>
						<th>읍면동(법정동)</th>
						<th>동리(법정동)</th>
						<th>가두리 면허면적(㎡)</th>
						<th>가두리 시설면적(㎡)</th>
						<th>가두리 사료사용량(㎏/년)</th>
						<th>가두리 출고량(㎏/년)</th>
						<th>도전양식 면허면적(㎡)</th>
						<th>도전양식 시설면적(㎡)</th>
						<th>도전양식 사료사용량(㎏/년)</th>
						<th>도전양식 출고량(㎏/년)</th>
						<th>순환여과식면허면적(㎡)</th>
						<th>순환여과식시설면적(㎡)</th>
						<th>순환여과식 사료(㎏/년)</th>
						<th>순환여과식 출고량(㎏/년)</th>
						<th>유수식 면허면적(㎡)</th>
						<th>유수식 시설면적(㎡)</th>
						<th>유수식 사료사용량(㎏/년)</th>
						<th>유수식 출고량(㎏/년)</th>
						<th>지수식 면허면적(㎡)</th>
						<th>지수식 시설면적(㎡)</th>
						<th>지수식 사료사용량(㎏/년)</th>
						<th>지수식 출고량(㎏/년)</th>
						<th>기타 면허면적(㎡)</th>
						<th>기타 시설면적(㎡)</th>
						<th>기타 사료사용량(㎏/년)</th>
						<th>기타 출고량(㎏/년)</th>
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
								<td>${item.YYYY}</td>
								<td>${item.WS_CD}</td>
								<td>${item.AM_NM}</td>
								<td>${item.ADM_CD}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.AREA_REG1}</td>
								<td>${item.AREA_INST1}</td>
								<td>${item.FEED_AMT1}</td>
								<td>${item.DELI_AMT1}</td>
								<td>${item.AREA_REG2}</td>
								<td>${item.AREA_INST2}</td>
								<td>${item.FEED_AMT2}</td>
								<td>${item.DELI_AMT2}</td>
								<td>${item.AREA_REG3}</td>
								<td>${item.AREA_INST3}</td>
								<td>${item.FEED_AMT3}</td>
								<td>${item.DELI_AMT3}</td>
								<td>${item.AREA_REG4}</td>
								<td>${item.AREA_INST4}</td>
								<td>${item.FEED_AMT4}</td>
								<td>${item.DELI_AMT4}</td>
								<td>${item.AREA_REG5}</td>
								<td>${item.AREA_INST5}</td>
								<td>${item.FEED_AMT5}</td>
								<td>${item.DELI_AMT5}</td>
								<td>${item.AREA_REG6}</td>
								<td>${item.AREA_INST6}</td>
								<td>${item.FEED_AMT6}</td>
								<td>${item.DELI_AMT6}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'C'}">
		<!-- 전국오염원조사 기타수질오염원 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>연도</th>
						<th>수계</th>
						<th>중권역</th>
						<th>법정동코드</th>
						<th>시도(법정동)</th>
						<th>시군구(법정동)</th>
						<th>읍면동(법정동)</th>
						<th>동리(법정동)</th>
						<th>관할기관명</th>
						<th>업소명</th>
						<th>기타수질오염원분류</th>
						<th>처리방법</th>
						<th>폐수방류량(㎥/일)</th>
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
								<td>${item.RNUM}</td>
								<td>${item.YYYY}</td>
								<td>${item.WS_CD}</td>
								<td>${item.AM_NM}</td>
								<td>${item.ADM_CD}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.INST_NM}</td>
								<td>${item.IND_NM}</td>
								<td>${item.OT_NM}</td>
								<td>${item.EH_NM}</td>
								<td>${item.WW_AMT}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	
	<c:when test="${param.siteTypeItem eq 'D'}">
		
		<c:choose>
			<c:when test="${empty param.siteTypeItemSub or param.siteTypeItemSub eq 'A'}">
				<!-- 매립장 기본현황 목록 -->
			    <div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>수계</th>
								<th>중권역</th>
								<th>법정동코드</th>
								<th>시도(법정동)</th>
								<th>시군구(법정동)</th>
								<th>읍면동(법정동)</th>
								<th>동리(법정동)</th>
								<th>매립장명</th>
								<th>지정내역</th>
								<th>편입일자</th>
								<th>가동개시일자</th>
								<th>처리유형</th>
								<th>매립대상폐기물</th>
								<th>매립장 전체면적(㎡)</th>
								<th>매립용량(㎥)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.WS_CD}</td>
										<td>${item.AM_NM}</td>
										<td>${item.ADM_CD}</td>
										<td>${item.DO_NM}</td>
										<td>${item.CTY_NM}</td>
										<td>${item.DONG_NM}</td>
										<td>${item.RI_NM}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.REGION}</td>
										<td>${item.REGION_DATE}</td>
										<td>${item.START_DD}</td>
										<td>${item.TRT_METHOD}</td>
										<td>${item.GUBUN}</td>
										<td>${item.TOTAL_AREA}</td>
										<td>${item.CAPACITY}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 매립장 침출수 목록 -->
				<div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>매립장명</th>
								<th>운영일자</th>
								<th>침출수량 발생유량(㎥/일)</th>
								<th>침출수량 방류유량(㎥/일)</th>
								<th>발생농도 BOD(㎎/ℓ)</th>
								<th>발생농도 COD(㎎/ℓ)</th>
								<th>발생농도 TN(㎎/ℓ)</th>
								<th>발생농도 TP(㎎/ℓ)</th>
								<th>방류농도 BOD(㎎/ℓ)</th>
								<th>방류농도 COD(㎎/ℓ)</th>
								<th>방류농도 TN(㎎/ℓ)</th>
								<th>방류농도 TP(㎎/ℓ)</th>
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
										<td>${item.FACI_NM}</td>
										<td>${item.WORK_DT}</td>
										<td>${item.PRODUCT_AMT}</td>
										<td>${item.DISCHARGE_AMT}</td>
										<td>${item.PRODUCT_BOD}</td>
										<td>${item.PRODUCT_COD}</td>
										<td>${item.PRODUCT_TN}</td>
										<td>${item.PRODUCT_TP}</td>
										<td>${item.DISCHARGE_BOD}</td>
										<td>${item.DISCHARGE_COD}</td>
										<td>${item.DISCHARGE_TN}</td>
										<td>${item.DISCHARGE_TP}</td>
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
				<!-- 환경기초시설 기본현황 -->
			    <div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>처리시설명</th>
								<th>시설용량 물리적(㎥/일)</th>
								<th>시설용량 생물학적(㎥/일)</th>
								<th>시설용량 고도(㎥/일)</th>
								<th>전처리</th>
								<th>1차처리</th>
								<th>2차처리</th>
								<th>3차처리</th>
								<th>운영기관</th>
								<th>가동개시일자</th>
								<th>가동률(%)</th>
								<th>가동일수</th>
								<th>재이용량 계(㎥/일)</th>
								<th>재이용량 조경수(㎥/일)</th>
								<th>재이용량 세척수(㎥/일)</th>
								<th>재이용량공정재이용(㎥/일)</th>
								<th>재이용량 기타(㎥/일)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.FACI_AMT_PHYS}</td>
										<td>${item.FACI_AMT_BIO}</td>
										<td>${item.FACI_AMT_HIGHTEC}</td>
										<td>${item.TRT_METHOD_PRE}</td>
										<td>${item.TRT_METHOD_1}</td>
										<td>${item.TRT_METHOD_2}</td>
										<td>${item.TRT_METHOD_3}</td>
										<td>${item.OPR_ORG}</td>
										<td>${item.START_DD}</td>
										<td>${item.OPR_PERCENT}</td>
										<td>${item.OPR_DAYS}</td>
										<td>${item.REUSE_SUM}</td>
										<td>${item.REUSE_LANDSCAPE}</td>
										<td>${item.REUSE_WASH}</td>
										<td>${item.REUSE_PROCESS}</td>
										<td>${item.REUSE_ETC}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'B'}">
				<!-- 환경기초시설 총유입량 -->
				<div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>처리시설명</th>
								<th>운영일자</th>
								<th>유입구번호</th>
								<th>유량(㎥/일)</th>
								<th>BOD(㎎/ℓ)</th>
								<th>COD(㎎/ℓ)</th>
								<th>SS(㎎/ℓ)</th>
								<th>TN(㎎/ℓ)</th>
								<th>TP(㎎/ℓ)</th>
								<th>대장균군수(총대장균군수)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.WORK_DT}</td>
										<td>${item.PIPE_NUM}</td>
										<td>${item.AMT}</td>
										<td>${item.BOD}</td>
										<td>${item.COD}</td>
										<td>${item.SS}</td>
										<td>${item.TN}</td>
										<td>${item.TP}</td>
										<td>${item.COLI}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'C'}">
				<!-- 환경기초시설 관거이송량 -->
				<div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>처리시설명</th>
								<th>운영일자</th>
								<th>관거번호</th>
								<th>유입원</th>
								<th>연계처리시설명</th>
								<th>유량(㎥/일)</th>
								<th>BOD(㎎/ℓ )</th>
								<th>COD(㎎/ℓ )</th>
								<th>SS(㎎/ℓ )</th>
								<th>TN(㎎/ℓ )</th>
								<th>TP(㎎/ℓ )</th>
								<th>대장균군수(총대장균군수)</th>
								<th>미처리배제유량(㎥/일)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.WORK_DT}</td>
										<td>${item.PIPE_NUM}</td>
										<td>${item.PIPE_TYPE}</td>
										<td>${item.CONNECT_FACI_NM}</td>
										<td>${item.AMT}</td>
										<td>${item.BOD}</td>
										<td>${item.COD}</td>
										<td>${item.SS}</td>
										<td>${item.TN}</td>
										<td>${item.TP}</td>
										<td>${item.COLI}</td>
										<td>${item.BYPASS_AMT}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'D'}">
				<!-- 환경기초시설 직접이송량 -->
				<div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>처리시설명</th>
								<th>운영일자</th>
								<th>유입원</th>
								<th>유량(㎥/일)</th>
								<th>BOD(㎎/ℓ)</th>
								<th>COD(㎎/ℓ)</th>
								<th>SS(㎎/ℓ)</th>
								<th>TN(㎎/ℓ)</th>
								<th>TP(㎎/ℓ)</th>
								<th>대장균군수(총대장균군수)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.WORK_DT}</td>
										<td>${item.IN_PL_TYPE}</td>
										<td>${item.AMT}</td>
										<td>${item.BOD}</td>
										<td>${item.COD}</td>
										<td>${item.SS}</td>
										<td>${item.TN}</td>
										<td>${item.TP}</td>
										<td>${item.COLI}</td>
									</tr>
				                </c:forEach>
							</c:if>
						</tbody>
				    </table>
				</div>
			</c:when>
			<c:when test="${param.siteTypeItemSub eq 'E'}">
				<!-- 환경기초시설 방류유량 -->
				<div class="result sc">
				    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
				        <caption>측정소 선택하기</caption>
						<thead>
				            <tr>
								<th>번호</th>
								<th>연도</th>
								<th>처리시설명</th>
								<th>운영일자</th>
								<th>방류구 번호</th>
								<th>유량_물리적(㎥/일)</th>
								<th>유량_생물학적(㎥/일)</th>
								<th>유량_고도(㎥/일)</th>
								<th>BOD(㎎/ℓ)</th>
								<th>COD(㎎/ℓ)</th>
								<th>SS(㎎/ℓ)</th>
								<th>TN(㎎/ℓ)</th>
								<th>TP(㎎/ℓ)</th>
								<th>대장균군수(총대장균군수)</th>
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
										<td>${item.YYYY}</td>
										<td>${item.FACI_NM}</td>
										<td>${item.WORK_DT}</td>
										<td>${item.DISCHARGE_NUM}</td>
										<td>${item.DISCHARGE_AMT_PHYS}</td>
										<td>${item.DISCHARGE_AMT_BIO}</td>
										<td>${item.DISCHARGE_AMT_HIGHTEC}</td>
										<td>${item.BOD}</td>
										<td>${item.COD}</td>
										<td>${item.SS}</td>
										<td>${item.TN}</td>
										<td>${item.TP}</td>
										<td>${item.COLI}</td>
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
		<!-- 전국오염원조사 토지계 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>조사년도</th>
						<th>대권역</th>
						<th>중권역</th>
						<th>시도(법정동)</th>
						<th>시군구(법정동)</th>
						<th>읍면동(법정동)</th>
						<th>동리(법정동)</th>
						<th>개별처리시설유형</th>
						<th>지목별면적 합계(㎡)</th>
						<th>전 면적(㎡)</th>
						<th>답 면적(㎡)</th>
						<th>과수원 면적(㎡)</th>
						<th>목장용지 면적(㎡)</th>
						<th>임야 면적(㎡)</th>
						<th>광천지 면적(㎡)</th>
						<th>염전 면적(㎡)</th>
						<th>대지 면적(㎡)</th>
						<th>공장용지 면적(㎡)</th>
						<th>학교용지 면적(㎡)</th>
						<th>주차장 면적(㎡)</th>
						<th>주유소용지 면적(㎡)</th>
						<th>창고용지 면적(㎡)</th>
						<th>도로 면적(㎡)</th>
						<th>철도용지 면적(㎡)</th>
						<th>하천 면적(㎡)</th>
						<th>제방 면적(㎡)</th>
						<th>구거 면적(㎡)</th>
						<th>유지 면적(㎡)</th>
						<th>양어장 면적(㎡)</th>
						<th>수도용지 면적(㎡)</th>
						<th>공원 면적(㎡)</th>
						<th>체육용지 면적(㎡)</th>
						<th>유원지 면적(㎡)</th>
						<th>종교용지 면적(㎡)</th>
						<th>사적지 면적(㎡)</th>
						<th>묘지 면적(㎡)</th>
						<th>잡종지 면적(㎡)</th>
						<th>골프장면적(㎡)</th>
						<th>환경기초시설명</th>
						<th>하천부지점용 합계(㎡)</th>
						<th>하천부지점용 논(㎡)</th>
						<th>하천부지점용 밭(㎡)</th>
						<th>하천부지점용 기타(㎡)</th>
						<th>토사채취량(㎥/년)</th>
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
								<td>${item.YYYY}</td>
								<td>${item.WS_NM}</td>
								<td>${item.AM_NM}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.TP_TYPE}</td>
								<td>${item.AREA_SUM}</td>
								<td>${item.AREA_RICE}</td>
								<td>${item.AREA_FIELD}</td>
								<td>${item.AREA_FLUIT}</td>
								<td>${item.AREA_STOCKFARM}</td>
								<td>${item.AREA_FOREST}</td>
								<td>${item.AREA_SPA}</td>
								<td>${item.AREA_SALTFIELD}</td>
								<td>${item.AREA_PLATEAU}</td>
								<td>${item.AREA_FACTORY}</td>
								<td>${item.AREA_EDUCATION}</td>
								<td>${item.AREA_PARKING}</td>
								<td>${item.AREA_OILING}</td>
								<td>${item.AREA_WAREHOUSE}</td>
								<td>${item.AREA_ROAD}</td>
								<td>${item.AREA_RAILROAD}</td>
								<td>${item.AREA_RIVER}</td>
								<td>${item.AREA_EMBANKMENT}</td>
								<td>${item.AREA_WATERROAD}</td>
								<td>${item.AREA_WATERRANGE}</td>
								<td>${item.AREA_FISHFARM}</td>
								<td>${item.AREA_WATER}</td>
								<td>${item.AREA_PARK}</td>
								<td>${item.AREA_HEALTH}</td>
								<td>${item.AREA_AMUSEMENTPARK}</td>
								<td>${item.AREA_RELIGION}</td>
								<td>${item.AREA_HISTORICAL}</td>
								<td>${item.AREA_GRAVEYARD}</td>
								<td>${item.AREA_MIXED}</td>
								<td>${item.GOLF_RANGE}</td>
								<td>${item.TP_NAME}</td>
								<td>${item.STRA_SUM}</td>
								<td>${item.STRA_RICE}</td>
								<td>${item.STRA_FIELD}</td>
								<td>${item.STRA_ETC}</td>
								<td>${item.STRA_SANDPICK}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'G'}">
		<!-- 전국오염원조사 산업계 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>조사년도</th>
						<th>대권역</th>
						<th>중권역</th>
						<th>시도(법정동)</th>
						<th>시군구(법정동)</th>
						<th>읍면동(법정동)</th>
						<th>동리(법정동)</th>
						<th>관할기관</th>
						<th>휴업</th>
						<th>사업자등록번호</th>
						<th>업소명</th>
						<th>전화</th>
						<th>대표자</th>
						<th>허가신고여부</th>
						<th>업종</th>
						<th>규모</th>
						<th>업소형태</th>
						<th>유해물질배출여부</th>
						<th>평균해발고도(m)</th>
						<th>자본금(백만원)</th>
						<th>사업장면적 대지(㎡)</th>
						<th>사업장면적 건물(㎡)</th>
						<th>지역구분1</th>
						<th>지역구분2</th>
						<th>산업단지</th>
						<th>기타산업단지</th>
						<th>농공단지</th>
						<th>기타농공단지</th>
						<th>상수원보호구역</th>
						<th>업소관리등급</th>
						<th>자가측정유형</th>
						<th>환경관리인1 성명</th>
						<th>환경관리인1 자격등급</th>
						<th>환경관리인2 성명</th>
						<th>환경관리인2 자격등급</th>
						<th>전담부서 형태</th>
						<th>전담부서 인원(명)</th>
						<th>원료1 원료명</th>
						<th>원료2 원료명</th>
						<th>원료3 원료명</th>
						<th>제품1 제품명</th>
						<th>제품1 생산량</th>
						<th>제품2 제품명</th>
						<th>제품2 생산량</th>
						<th>제품3 제품명</th>
						<th>제품3 생산량</th>
						<th>폐수처리 형태</th>
						<th>폐수처리 종말처리</th>
						<th>폐수처리 기타종말</th>
						<th>폐수처리 공동처리</th>
						<th>폐수처리 기타공동</th>
						<th>폐수처리 위탁처리</th>
						<th>폐수처리 기타위탁</th>
						<th>배출허용기준적용지역</th>
						<th>연계처리시설</th>
						<th>방류구역코드</th>
						<th>방류하천코드</th>
						<th>하류취수장이름</th>
						<th>총용수량 계(㎥/일)</th>
						<th>총용수량 상수도(㎥/일)</th>
						<th>총용수량 지하수급수(㎥/일)</th>
						<th>총용수량 하천수(㎥/일)</th>
						<th>총용수량 해수(㎥/일)</th>
						<th>총용수량 재이용수(㎥/일)</th>
						<th>공업용수량 계(㎥/일)</th>
						<th>원료및보일러용수(㎥/일)</th>
						<th>공업용수량공정용수(㎥/일)</th>
						<th>공업용수량 희석수(㎥/일)</th>
						<th>공업용수량 기타수(㎥/일)</th>
						<th>생활용수량(㎥/일)</th>
						<th>제품및증발량(㎥/일)</th>
						<th>폐수발생량 계(㎥/일)</th>
						<th>폐수발생량공정폐수(㎥/일)</th>
						<th>폐수발생량냉각폐수(㎥/일)</th>
						<th>폐수발생량생활오수(㎥/일)</th>
						<th>폐수방류량(㎥/일)</th>
						<th>냉각수방류량(㎥/일)</th>
						<th>폐수재이용수량 계(㎥/일)</th>
						<th>처리장유입전(㎥/일)</th>
						<th>처리장내(㎥/일)</th>
						<th>폐수처리후(㎥/일)</th>
						<th>최대폐수발생량(㎥/일)</th>
						<th>처리전 구리(㎎/ℓ)</th>
						<th>처리전 납(㎎/ℓ)</th>
						<th>처리전 비소(㎎/ℓ)</th>
						<th>처리전 수은(㎎/ℓ)</th>
						<th>처리전 시안(㎎/ℓ)</th>
						<th>처리전 유기인(㎎/ℓ)</th>
						<th>처리전 6가크롬(㎎/ℓ)</th>
						<th>처리전 카드뮴(㎎/ℓ)</th>
						<th>처리전 테트라클로로에틸렌(㎎/ℓ)</th>
						<th>처리전 트리클로로에틸렌(㎎/ℓ)</th>
						<th>처리전 페놀(㎎/ℓ)</th>
						<th>처리전 PCB(㎎/ℓ)</th>
						<th>처리전 셀레늄(㎎/ℓ)</th>
						<th>처리전 벤젠(㎎/ℓ)</th>
						<th>처리전 사염화탄소(㎎/ℓ)</th>
						<th>처리전 디클로로메탄(㎎/ℓ)</th>
						<th>처리전 1,1-디클로로에틸렌(㎎/ℓ)</th>
						<th>처리전 1,2-디클로로에탄(㎎/ℓ)</th>
						<th>처리전 클로로폼(㎎/ℓ)</th>
						<th>처리후 구리(㎎/ℓ)</th>
						<th>처리후 납(㎎/ℓ)</th>
						<th>처리후 비소(㎎/ℓ)</th>
						<th>처리후 수은(㎎/ℓ)</th>
						<th>처리후 시안(㎎/ℓ)</th>
						<th>처리후 유기인(㎎/ℓ)</th>
						<th>처리후 6가크롬(㎎/ℓ)</th>
						<th>처리후 카드뮴(㎎/ℓ)</th>
						<th>처리후 테트라클로로에틸렌(㎎/ℓ)</th>
						<th>처리후 트리클로로에틸렌(㎎/ℓ)</th>
						<th>처리후 페놀(㎎/ℓ)</th>
						<th>처리후 PCB(㎎/ℓ)</th>
						<th>처리후 셀레늄(㎎/ℓ)</th>
						<th>처리후 벤젠(㎎/ℓ)</th>
						<th>처리후 사염화탄소(㎎/ℓ)</th>
						<th>처리후 디클로로메탄(㎎/ℓ)</th>
						<th>처리후 1,1-디클로로에틸렌(㎎/ℓ)</th>
						<th>처리후 1,2-디클로로에탄(㎎/ℓ)</th>
						<th>처리후 클로로폼(㎎/ℓ)</th>
						<th>폐수오염도_특정폐수발생량(㎥/일)</th>
						<th>폐수오염도_특정폐수방류량(㎥/일)</th>
						<th>처리전 pH(㎎/ℓ)</th>
						<th>처리전 BOD(㎎/ℓ)</th>
						<th>처리전 COD(㎎/ℓ)</th>
						<th>처리전 SS(㎎/ℓ)</th>
						<th>n헥산_B(광유류)(㎎/ℓ)</th>
						<th>n헥산_A(유지류)(㎎/ℓ)</th>
						<th>처리전 Cr(㎎/ℓ)</th>
						<th>처리전 Zn(㎎/ℓ)</th>
						<th>처리전 Mn(㎎/ℓ)</th>
						<th>처리전 계면활성제(㎎/ℓ)</th>
						<th>처리전 F(㎎/ℓ)</th>
						<th>처리전 Fe(㎎/ℓ)</th>
						<th>처리전 총인(㎎/ℓ)</th>
						<th>처리전 총질소(㎎/ℓ)</th>
						<th>처리후 pH(㎎/ℓ)</th>
						<th>처리후 BOD(㎎/ℓ)</th>
						<th>처리후 COD(㎎/ℓ)</th>
						<th>처리후 SS(㎎/ℓ)</th>
						<th>n헥산_A(광유류)(㎎/ℓ)</th>
						<th>n헥산_A(유지류)(㎎/ℓ)</th>
						<th>처리후 Cr(㎎/ℓ)</th>
						<th>처리후 Mn(㎎/ℓ)</th>
						<th>처리후 Zn(㎎/ℓ)</th>
						<th>처리후 F(㎎/ℓ)</th>
						<th>처리후 Fe(㎎/ℓ)</th>
						<th>처리후 계면활성제(㎎/ℓ)</th>
						<th>처리후 총인(㎎/ℓ)</th>
						<th>처리후 총질소(㎎/ℓ)</th>
						<th>배출시설1 코드</th>
						<th>배출시설1 시설수</th>
						<th>배출시설1 배출량(㎥/일)</th>
						<th>배출시설2 코드</th>
						<th>배출시설2 시설수</th>
						<th>배출시설2 배출량(㎥/일)</th>
						<th>배출시설3 코드</th>
						<th>배출시설3 시설수</th>
						<th>배출시설3 배출량(㎥/일)</th>
						<th>배출시설4 코드</th>
						<th>배출시설4 시설수</th>
						<th>배출시설4 배출량(㎥/일)</th>
						<th>배출시설5 코드</th>
						<th>배출시설5 시설수</th>
						<th>배출시설5 배출량(㎥/일)</th>
						<th>방지시설1 처리방법</th>
						<th>방지시설1 처리능력(㎥/일)</th>
						<th>방지시설2 처리방법</th>
						<th>방지시설2 처리능력(㎥/일)</th>
						<th>방지시설3 처리방법</th>
						<th>방지시설3 처리능력(㎥/일)</th>
						<th>방지시설4 처리방법</th>
						<th>방지시설4 처리능력(㎥/일)</th>
						<th>방지시설5 처리방법</th>
						<th>방지시설5 처리능력(㎥/일)</th>
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
								<td>${item.YYYY}</td>
								<td>${item.WS_NM}</td>
								<td>${item.AM_NM}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.INST_CD}</td>
								<td>${item.STOP_FLAG}</td>
								<td>${item.IND_ID}</td>
								<td>${item.IND_NM}</td>
								<td>${item.TEL_NO}</td>
								<td>${item.IND_OWNER}</td>
								<td>${item.LC_YN}</td>
								<td>${item.IND_TYPE}</td>
								<td>${item.SCALE}</td>
								<td>${item.CTG_NM}</td>
								<td>${item.TOX_FLAG}</td>
								<td>${item.AVG_ELV}</td>
								<td>${item.CAPITAL}</td>
								<td>${item.AREA_LAND}</td>
								<td>${item.AREA_BDG}</td>
								<td>${item.REGION1}</td>
								<td>${item.REGION2}</td>
								<td>${item.IND_BASE_CD}</td>
								<td>${item.IND_ETC}</td>
								<td>${item.FARM_CD}</td>
								<td>${item.FARM_ETC}</td>
								<td>${item.PROTECT_AREA}</td>
								<td>${item.IND_GRADE}</td>
								<td>${item.SELF_TYPE}</td>
								<td>${item.MGR1_NM}</td>
								<td>${item.MGR1_CERT}</td>
								<td>${item.MGR2_NM}</td>
								<td>${item.MGR2_CERT}</td>
								<td>${item.MGR_DEP}</td>
								<td>${item.MGR_CNT}</td>
								<td>${item.MTL1_NM}</td>
								<td>${item.MTL2_NM}</td>
								<td>${item.MTL3_NM}</td>
								<td>${item.PDT1_NM}</td>
								<td>${item.PDT1_AMT}</td>
								<td>${item.PDT2_NM}</td>
								<td>${item.PDT2_AMT}</td>
								<td>${item.PDT3_NM}</td>
								<td>${item.PDT3_AMT}</td>
								<td>${item.TRT_TYPE}</td>
								<td>${item.DEND_CD}</td>
								<td>${item.DEND_ETC}</td>
								<td>${item.DPUB_CD}</td>
								<td>${item.DPUB_ETC}</td>
								<td>${item.DIND_CD}</td>
								<td>${item.DIND_ETC}</td>
								<td>${item.AREA_CD}</td>
								<td>${item.FACI_NM}</td>
								<td>${item.RV_SEC_CD}</td>
								<td>${item.STREAM_PATH}</td>
								<td>${item.WTF_NAME}</td>
								<td>${item.WUSE_T_SUM}</td>
								<td>${item.WUSE_T_WATERWORK}</td>
								<td>${item.WUSE_T_GROUND}</td>
								<td>${item.WUSE_T_STREAM}</td>
								<td>${item.WUSE_T_SEA}</td>
								<td>${item.WUSE_T_RECYCLE}</td>
								<td>${item.WUSE_I_SUM}</td>
								<td>${item.WUSE_I_BOILERUSE}</td>
								<td>${item.WUSE_I_INDUSE}</td>
								<td>${item.WUSE_I_DILTE}</td>
								<td>${item.WUSE_I_COOLING}</td>
								<td>${item.WUSE_P}</td>
								<td>${item.WUSE_E}</td>
								<td>${item.WUSE_W_SUM}</td>
								<td>${item.WUSE_W_INDUSE}</td>
								<td>${item.WUSE_W_COOLING}</td>
								<td>${item.WUSE_W_LIFE}</td>
								<td>${item.WUSE_D}</td>
								<td>${item.WUSE_DC}</td>
								<td>${item.WUSE_R_SUM}</td>
								<td>${item.WUSE_R_BEFORE}</td>
								<td>${item.WUSE_R_IN}</td>
								<td>${item.WUSE_R_AFTER}</td>
								<td>${item.WUSE_MAX}</td>
								<td>${item.CU_B}</td>
								<td>${item.PB_B}</td>
								<td>${item.A_S_B}</td>
								<td>${item.HG_B}</td>
								<td>${item.CROM_B}</td>
								<td>${item.OP_B}</td>
								<td>${item.CR6_B}</td>
								<td>${item.CD_B}</td>
								<td>${item.PC_B}</td>
								<td>${item.TC_B}</td>
								<td>${item.PE_B}</td>
								<td>${item.PCB_B}</td>
								<td>${item.SEL_B}</td>
								<td>${item.BEN_B}</td>
								<td>${item.CAR_B}</td>
								<td>${item.DICH_B}</td>
								<td>${item.ETHYL_11_B}</td>
								<td>${item.ETHYL_12_B}</td>
								<td>${item.CHLO_B}</td>
								<td>${item.CU_A}</td>
								<td>${item.PB_A}</td>
								<td>${item.A_S_A}</td>
								<td>${item.HG_A}</td>
								<td>${item.CROM_A}</td>
								<td>${item.OP_A}</td>
								<td>${item.CR6_A}</td>
								<td>${item.CD_A}</td>
								<td>${item.PC_A}</td>
								<td>${item.TC_A}</td>
								<td>${item.PE_A}</td>
								<td>${item.PCB_A}</td>
								<td>${item.SEL_A}</td>
								<td>${item.BEN_A}</td>
								<td>${item.CAR_A}</td>
								<td>${item.DICH_A}</td>
								<td>${item.ETHYL_11_A}</td>
								<td>${item.ETHYL_12_A}</td>
								<td>${item.CHLO_A}</td>
								<td>${item.TWW_AMT}</td>
								<td>${item.TWW_DISCHARGE}</td>
								<td>${item.PH_B}</td>
								<td>${item.BOD_B}</td>
								<td>${item.COD_B}</td>
								<td>${item.SS_B}</td>
								<td>${item.NHEX_A_B}</td>
								<td>${item.NHEX_P_B}</td>
								<td>${item.CR_B}</td>
								<td>${item.ZN_B}</td>
								<td>${item.MN_B}</td>
								<td>${item.ABS_B}</td>
								<td>${item.F_B}</td>
								<td>${item.FE_B}</td>
								<td>${item.TP_B}</td>
								<td>${item.TN_B}</td>
								<td>${item.PH_A}</td>
								<td>${item.BOD_A}</td>
								<td>${item.COD_A}</td>
								<td>${item.SS_A}</td>
								<td>${item.NHEX_A_A}</td>
								<td>${item.NHEX_P_A}</td>
								<td>${item.CR_A}</td>
								<td>${item.MN_A}</td>
								<td>${item.ZN_A}</td>
								<td>${item.F_A}</td>
								<td>${item.FE_A}</td>
								<td>${item.ABS_A}</td>
								<td>${item.TP_A}</td>
								<td>${item.TN_A}</td>
								<td>${item.IND_TYPE_1}</td>
								<td>${item.FAC_CNT_1}</td>
								<td>${item.CAPA_SUM_1}</td>
								<td>${item.IND_TYPE_2}</td>
								<td>${item.FAC_CNT_2}</td>
								<td>${item.CAPA_SUM_2}</td>
								<td>${item.IND_TYPE_3}</td>
								<td>${item.FAC_CNT_3}</td>
								<td>${item.CAPA_SUM_3}</td>
								<td>${item.IND_TYPE_4}</td>
								<td>${item.FAC_CNT_4}</td>
								<td>${item.CAPA_SUM_4}</td>
								<td>${item.IND_TYPE_5}</td>
								<td>${item.FAC_CNT_5}</td>
								<td>${item.CAPA_SUM_5}</td>
								<td>${item.TEC_METHOD1}</td>
								<td>${item.TFC_CAPA1}</td>
								<td>${item.TEC_METHOD2}</td>
								<td>${item.TFC_CAPA2}</td>
								<td>${item.TEC_METHOD3}</td>
								<td>${item.TFC_CAPA3}</td>
								<td>${item.TEC_METHOD4}</td>
								<td>${item.TFC_CAPA4}</td>
								<td>${item.TEC_METHOD5}</td>
								<td>${item.TFC_CAPA5}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'H'}">
		<!-- 전국오염원조사 축산계 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th>번호</th>
						<th>조사년도</th>
						<th>대권역</th>
						<th>중권역</th>
						<th>시도(법정동)</th>
						<th>시군구(법정동)</th>
						<th>읍면동(법정동)</th>
						<th>동리(법정동)</th>
						<th>업주명</th>
						<th>축종</th>
						<th>사육두수</th>
						<th>축사면적(㎡)</th>
						<th>법적규제 여부</th>
						<th>법적규제일</th>
						<th>방류선환경기초시설</th>
						<th>방류선행정구역코드</th>
						<th>방류선하천명</th>
						<th>개별및공공처리_처리방법</th>
						<th>개별및공공처리_처리량(톤/일)</th>
						<th>개별및공공처리_처리비용(천원)</th>
						<th>퇴비화 처리공법</th>
						<th>퇴비화 처리량(톤/일)</th>
						<th>퇴비화 처리비용(천원)</th>
						<th>액비화 처리공법</th>
						<th>액비화 처리량(톤/일)</th>
						<th>액비화 처리비용(천원)</th>
						<th>공공처리 차집유형</th>
						<th>공공처리 편입일자</th>
						<th>공공처리 시설명</th>
						<th>공공처리 처리량(톤/일)</th>
						<th>공공처리 처리비용(천원)</th>
						<th>재활용 처리량(톤/일)</th>
						<th>재활용 처리비용(천원)</th>
						<th>해양배출 처리량(톤/일)</th>
						<th>해양배출 처리비용(천원)</th>
						<th>가축분뇨처리업자 처리량(톤/일)</th>
						<th>기타 처리방법</th>
						<th>기타 처리량(톤/일)</th>
						<th>기타 처리비용(천원)</th>
						<th>기타</th>
						<th>무처리량(톤/일)</th>
						<th>고형축분뇨처리방법</th>
						<th>기타축분뇨처리방법</th>
						<th>살포지역용도</th>
						<th>살포지역 행정구역</th>
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
								<td>${item.YYYY}</td>
								<td>${item.WS_NM}</td>
								<td>${item.AM_NM}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DONG_NM}</td>
								<td>${item.RI_NM}</td>
								<td>${item.MANAGER}</td>
								<td>${item.LIVESTOCK_NM}</td>
								<td>${item.LIVESTOCK_CNT}</td>
								<td>${item.LIVESTOCK_AREA}</td>
								<td>${item.REGS}</td>
								<td>${item.REGS_DATE}</td>
								<td>${item.DISCHARGE_FACI_NM}</td>
								<td>${item.DISCHARGE_ADM_CD}</td>
								<td>${item.DISCHARGE_RIVER_NM}</td>
								<td>${item.INDIV_PURI_METHOD}</td>
								<td>${item.INDIV_PURI_AMT}</td>
								<td>${item.INDIV_PURI_MONEY}</td>
								<td>${item.INDIV_COMPOST_METHOD}</td>
								<td>${item.INDIV_COMPOST_AMT}</td>
								<td>${item.INDIV_COMPOST_MONEY}</td>
								<td>${item.INDIV_LIQUID_METHOD}</td>
								<td>${item.INDIV_LIQUID_AMT}</td>
								<td>${item.INDIV_LIQUID_MONEY}</td>
								<td>${item.ENTRUST_PUB_COLMETHOD}</td>
								<td>${item.ENTRUST_PUB_DT}</td>
								<td>${item.ENTRUST_PUB_FACI_NM}</td>
								<td>${item.ENTRUST_PUB_AMT}</td>
								<td>${item.ENTRUST_PUB_MONEY}</td>
								<td>${item.ENTRUST_REUSE_AMT}</td>
								<td>${item.ENTRUST_REUSE_MONEY}</td>
								<td>${item.ENTRUST_SEA_AMT}</td>
								<td>${item.ENTRUST_SEA_MONEY}</td>
								<td>${item.ENTRUST}</td>
								<td>${item.ETC_METHOD}</td>
								<td>${item.ETC_AMT}</td>
								<td>${item.ETC_MONEY}</td>
								<td>${item.ETC}</td>
								<td>${item.NO_TRT_AMT}</td>
								<td>${item.LEX_METHOD}</td>
								<td>${item.LEX_METHOD_ETC}</td>
								<td>${item.SPRAY_LANDUSE}</td>
								<td>${item.SPRAY_ADM_CD}</td>
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



