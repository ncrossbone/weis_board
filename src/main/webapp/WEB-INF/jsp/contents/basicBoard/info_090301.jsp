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
		
		
		$(":radio[name='siteTypeItem']").on('change', function(){
			setAllItemClear();
		});
		$('#select_area').on('change', function(){
			distirctListAjax($(this).val());
		});
		$('#select_water_sys').on('change', function(){
			waterSysListAjax($(this).val());
		});
		if("${param.select_area}" != null && "${param.select_area}" != ""){
			distirctListAjax("${param.select_area}");
		}
		if("${param.select_water_sys}" != null && "${param.select_water_sys}" != ""){
			waterSysListAjax("${param.select_water_sys}");
		}
		$('#srcBtn').on('click', function(){
			resultSearch();
		});
	});
	
	//모든 검색조건 초기화
	function setAllItemClear(){
		var tableHtml01 = "";
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		//2단계
		$("#select_area_sub").html("<option value=''>선택</option>");
		$("#select_water_sys_sub").html("<option value=''>선택</option>");
		$("#second_item").find("select, input").val("");
		
		//검색결과
		tableHtml01 += "<tr class='odd3'>";
		if(getSiteType == "A"){
			tableHtml01 += "   <td colspan='34'>검색 결과가 존재하지 않습니다.</td>";
		}else if(getSiteType == "B"){
			tableHtml01 += "   <td colspan='58'>검색 결과가 존재하지 않습니다.</td>";
		}else if(getSiteType == "C"){
			tableHtml01 += "   <td colspan='30'>검색 결과가 존재하지 않습니다.</td>";
		}
		tableHtml01 += "</tr>";
		
		$("#resultSiteTable").html(tableHtml01);
		$("#resultCnt").text("조회현황 : 0 건");
	}
	
	//행정구역 하위목록 조회
	function distirctListAjax(objVal){
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
				
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	//수계 하위목록 조회
	function waterSysListAjax(objVal){
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
				
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	function resultSearch(){
		var varSelectArea = $("#select_area").val();
		var varSelectAreaSub = $("#select_area_sub").val();
		var varSelectWaterSys = $("#select_water_sys").val();
		var varSelectWaterSysSub = $("#select_water_sys_sub").val();
		
		var varDownType = $("#downType").val();
		
		if( (varSelectArea == null || varSelectArea == "")
		&& (varSelectAreaSub == null || varSelectAreaSub == "")
		&& (varSelectWaterSys == null || varSelectWaterSys == "")
		&& (varSelectWaterSysSub == null || varSelectWaterSysSub == "")){
        	alert("검색조건을 선택하세요.");
        	$("#select_area").focus();
        	return false;
        }
		
		var getSiteType = $(":radio[name='siteTypeItem']:checked").val();
		
		if(getSiteType == 'A'){
			$("#mId").val("Excelwifclty");
		}else if(getSiteType == 'B'){
			$("#mId").val("Excelwpfclty");
		}else if(getSiteType == 'C'){
			$("#mId").val("Excelwffclty");
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
	
	<input type="hidden" id="mId" name="mId" value =""/>
	
	<div class="search">
		<dl>
	    	<dt><b>1단계</b> 자료분류</dt>
	        <dd>
	        	<div class="cond">
					<dl class="rad">
	                    <dt>분류</dt>
	                    <dd>
	                    	<input name="siteTypeItem" type="radio" id="siteType_A" title="취수시설" value="A" ${'A' eq param.siteTypeItem or empty param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_A" >취수시설</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_B" title="양수시설" value="B" ${'B' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_B" >양수시설</label>
	                        
	                        <input name="siteTypeItem" type="radio" id="siteType_C" title="호친수시설" value="C" ${'C' eq param.siteTypeItem ? 'checked="checked""' : ''}/>
	                        <label for="siteType_C" >친수시설</label>
	                    </dd>
	                </dl>
	           </div>
	       </dd>
	   	</dl>
	    <dl>
			<dt><b>2단계</b> 지역선택</dt>
		       	<dd>
				<div class="cond">
	               	<div id="second_item">
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
		<!-- 취수시설 결과 목록 -->
		<div class="result sc">
		    <table class="st01" summary="">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th rowspan="2">번호</th>
						<th rowspan="2">수계</th>
						<th rowspan="2">4대강보</th>
						<th rowspan="2">취수장명</th>
						<th rowspan="2">제약수위</th>
						<th rowspan="2">시설개선비용 (추정, 백만원)</th>
						<th rowspan="2">용도</th>
						<th colspan="3">위치</th>
						<th rowspan="2">관리기관</th>
						<th rowspan="2">담당자 (연락처)</th>
						<th rowspan="2">취수원 하천명</th>
						<th rowspan="2">보상류 이격거리km</th>
						<th rowspan="2">취수가능수위 EL. m</th>
						<th rowspan="2">취수 허가량 천㎥/일</th>
						<th rowspan="2">취수장 설치년월</th>
						<th colspan="3">대강사업 당시 및 이후 개선여부</th>
						<th rowspan="2">급수인구 '15년 기준</th>
						<th rowspan="2">가동시작일 / 가동종료일</th>
						<th rowspan="2">취수장 가동 기간월.일 / 시간</th>
						<th rowspan="2">연간총 취수량 천㎥/년</th>
						<th rowspan="2">일평균 취수량 천㎥/d</th>
						<th rowspan="2">일최대 취수량 실적 천㎥/d</th>
						<th colspan="5">펌프시설현황</th>
						<th rowspan="2">공급 정수장</th>
						<th rowspan="2">취수장 이설/증설 계획여부</th>
						<th rowspan="2">비 고</th>
		            </tr>
		            <tr>
		            	<th>시도</th>
		            	<th>시군</th>
		            	<th>주소</th>
		            	<th>O, X</th>
		            	<th>년/월</th>
		            	<th>개선내용</th>
		            	<th>설치년월</th>
		            	<th>펌프형식</th>
		            	<th>구경mm</th>
		            	<th>동력Kw</th>
		            	<th>총대수예비</th>
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
								<td>${item.ROWNO}</td>
								<td>${item.WRSSM_NM}</td>
								<td>${item.OBSNM}</td>
								<td>${item.WIFCLT_NM}</td>
								<td>${item.RSTRCT_WAL}</td>
								<td>${item.FCLTY_IMPRVM_CT}</td>
								<td>${item.PRPOS}</td>
								<td>${item.DO_NM}</td>
								<td>${item.CTY_NM}</td>
								<td>${item.DETAIL_ADRES}</td>
								<td>${item.MANAGE_INSTT}</td>
								<td>${item.CHARGER}</td>
								<td>${item.WIPLT}</td>
								<td>${item.BRRER_UPSTRIM_GAP_DSTNC}</td>
								<td>${item.DBD_POSBL_WAL}</td>
								<td>${item.DBD_PRMISN_QY}</td>
								<td>${item.INSTL_YM}</td>
								<td>${item.IMPRVM_BSNS_AT}</td>
								<td>${item.IMPRVM_BSNS_YM}</td>
								<td>${item.IMPRVM_CN}</td>
								<td>${item.WSP_POPLTN}</td>
								<td>${item.OPRER_TIME}</td>
								<td>${item.OPRER_DE}</td>
								<td>${item.YY_TOT_DBD_QY}</td>
								<td>${item.DE_AVRG_DBD_QY}</td>
								<td>${item.DE_MXMM_DBD_QY}</td>
								<td>${item.PUMP_INSTL_YM}</td>
								<td>${item.PUMP_FOM}</td>
								<td>${item.CALBR}</td>
								<td>${item.POWER}</td>
								<td>${item.PUMP_CO}</td>
								<td>${item.SUPLY_FLTPLT}</td>
								<td>${item.WIFCLT_CHANGE_PLAN_AT}</td>
								<td>${item.RM}</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'B'}">
		<!-- 양수시설 결과 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th rowspan="3">번호</th>
						<th rowspan="3">수계</th>
						<th rowspan="3">4대강보</th>
						<th rowspan="3">시설명</th>
						<th rowspan="3">제약수위</th>
						<th rowspan="3">시설개선비용 (추정, 백만원)</th>
						<th rowspan="3">준공년월</th>
						<th colspan="3">위치</th>
						<th rowspan="3">관리기관</th>
						<th rowspan="3">관리기관 (세부기관)</th>
						<th rowspan="3">담당자 (연락처)</th>
						<th rowspan="3">수혜면적 (ha)</th>
						<th colspan="3">주요작물</th>
						<th rowspan="3">보상류 이격거리(km)</th>
						<th rowspan="3">취수원(하천명)</th>
						<th rowspan="3">양수허가량(㎥/일)</th>
						<th rowspan="3">최대양수량 (㎥/min)</th>
						<th rowspan="3">평균양수량 (천㎥/일)</th>
						<th colspan="14">월별 양수량(만㎥)/가동일수(일)</th>
						<th colspan="2">양수제약수위(최저흡입수위)</th>
						<th rowspan="3">유입방식</th>
						<th colspan="9">양수시설 현황</th>
						<th colspan="4">양수장 개보수 현황</th>
						<th rowspan="3">양수장 가동 종료일</th>
						<th rowspan="3">양수장 가동 개시일</th>
						<th colspan="3">타목적 용수공급 현황</th>
						<th rowspan="3">비고</th>
		            </tr>
		            <tr>
		            	<th rowspan="2">시도</th>
		            	<th rowspan="2">시군</th>
		            	<th rowspan="2">주소</th>
		            	<th rowspan="2">벼(ha)</th>
		            	<th colspan="2">기타작물</th>
		            	<th colspan="2">2017.04월</th>
		            	<th colspan="2">2017.05월</th>
		            	<th colspan="2">2017.06월</th>
		            	<th colspan="2">2017.07월</th>
		            	<th colspan="2">2017.08월</th>
		            	<th colspan="2">2016.09월</th>
		            	<th colspan="2">2016.10월</th>
		            	<th rowspan="2">EL. m</th>
		            	<th rowspan="2">근거</th>
		            	<th rowspan="2">설치년월</th>
		            	<th rowspan="2">최저흡입</th>
		            	<th rowspan="2">전양정(m)</th>
		            	<th rowspan="2">내구연한(년)</th>
		            	<th rowspan="2">최근 점검결과</th>
		            	<th rowspan="2">펌프형식</th>
		            	<th rowspan="2">구경(mm)</th>
		            	<th rowspan="2">동력(kW)</th>
		            	<th rowspan="2">대수</th>
		            	<th rowspan="2">개보수 년월</th>
		            	<th rowspan="2">4대강 사업 관련여부</th>
		            	<th rowspan="2">4대강 이전/사업시/이후</th>
		            	<th rowspan="2">개보수 내용</th>
		            	<th rowspan="2">목적</th>
		            	<th rowspan="2">일양수량</th>
		            	<th rowspan="2">공급기간</th>
		            </tr>
		            <tr>
		            	<th>작물명</th>
		            	<th>(ha)</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            	<th>양수량</th>
		            	<th>가동일수</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="58">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.ROWNO}</td>
								<td>${item.WRSSM_NM }</td>
								<td>${item.OBSNM }</td>
								<td>${item.WPFCLT_NM }</td>
								<td>${item.RSTRCT_WAL }</td>
								<td>${item.FCLTY_IMPRVM_CT }</td>
								<td>${item.COMPET_YM }</td>
								<td>${item.DO_NM }</td>
								<td>${item.CTY_NM }</td>
								<td>${item.DETAIL_ADRES }</td>
								<td>${item.MANAGE_INSTT }</td>
								<td>${item.DETAIL_INSTT }</td>
								<td>${item.CHARGER }</td>
								<td>${item.RCVFVR_AR }</td>
								<td>${item.RCEPNT_CROPS_AR }</td>
								<td>${item.ETC_CROPS_CN }</td>
								<td>${item.ETC_CROPS_AR }</td>
								<td>${item.BRRER_UPSTRIM_GAP_DSTNC }</td>
								<td>${item.WIPLT }</td>
								<td>${item.PRMISN_WP_QY }</td>
								<td>${item.MXMM_WP_QY }</td>
								<td>${item.AVG_WP }</td>
								<td>${item.WP_QY_04 }</td>
								<td>${item.OPR_DAYCNT_04 }</td>
								<td>${item.WP_QY_05 }</td>
								<td>${item.OPR_DAYCNT_05 }</td>
								<td>${item.WP_QY_06 }</td>
								<td>${item.OPR_DAYCNT_06 }</td>
								<td>${item.WP_QY_07 }</td>
								<td>${item.OPR_DAYCNT_07 }</td>
								<td>${item.WP_QY_08 }</td>
								<td>${item.OPR_DAYCNT_08 }</td>
								<td>${item.WP_QY_09 }</td>
								<td>${item.OPR_DAYCNT_09 }</td>
								<td>${item.WP_QY_10 }</td>
								<td>${item.OPR_DAYCNT_10 }</td>
								<td>${item.WP_RSTRCT_WAL }</td>
								<td>${item.WP_RSTRCT_WAL_BASIS }</td>
								<td>${item.INFLOW_MTHD }</td>
								<td>${item.INSTL_YM }</td>
								<td>${item.LWET_INHL_WAL }</td>
								<td>${item.TOTPH }</td>
								<td>${item.DURA_TRM }</td>
								<td>${item.RECENT_CHCK_RESULT }</td>
								<td>${item.PUMP_FOM }</td>
								<td>${item.CALBR }</td>
								<td>${item.POWER }</td>
								<td>${item.PUMP_CO }</td>
								<td>${item.RPAIR_YM }</td>
								<td>${item.FRIVR_BSNS_AT }</td>
								<td>${item.FRIVR_BSNS_STDR }</td>
								<td>${item.RPAIR_CN }</td>
								<td>${item.OPR_END_DE }</td>
								<td>${item.OPR_BEGIN_DE }</td>
								<td>${item.ELSPPS }</td>
								<td>${item.ELSPPS_SUPLY_DE_QY }</td>
								<td>${item.ELSPPS_SUPLY_PD }</td>
								<td>${item.RM }</td>
							</tr>
		                </c:forEach>
					</c:if>
				</tbody>
		    </table>
		</div>
	</c:when>
	<c:when test="${param.siteTypeItem eq 'C'}">
		<!-- 친수시설 결과 목록 -->
	    <div class="result sc">
		    <table class="st01" summary="">
		        <caption>측정소 선택하기</caption>
				<thead>
		            <tr>
						<th rowspan="2">번호</th>
						<th rowspan="2">수계</th>
						<th rowspan="2">4대강보</th>
						<th rowspan="2">시설명</th>
						<th rowspan="2">제약수위</th>
						<th rowspan="2">시설개선비용 (추정, 백만원)</th>
						<th rowspan="2">시설목적</th>
						<th rowspan="2">시설목적분류</th>
						<th colspan="3">위치</th>
						<th rowspan="2">보상류 이격거리(km)</th>
						<th colspan="2">관리기관</th>
						<th colspan="2">운영기관</th>
						<th rowspan="2">운영 개시일</th>
						<th rowspan="2">주요 이용기간 (월.일)</th>
						<th rowspan="2">16년도 이용객수 (천명)</th>
						<th rowspan="2">16년도 매출액 (백만원)</th>
						<th colspan="4">보별 관리수위(EL.m)</th>
						<th colspan="2">운영가능 최저수위</th>
						<th rowspan="2">운영가능 최저수위 이하로 수위저하시 문제점 및 개선방안</th>
						<th rowspan="2">좌안 우안</th>
						<th rowspan="2">인허가 여부</th>
						<th rowspan="2">비 고</th>
		            </tr>
		            <tr>
		            	<th>시도</th>
		            	<th>시군</th>
		            	<th>주소</th>
		            	<th>기관명</th>
		            	<th>담당자(연락처)</th>
		            	<th>운영자 or 소유자</th>
		            	<th>담당자(연락처)</th>
		            	<th>현재관리수위</th>
		            	<th>지하수제약수위</th>
		            	<th>하한수위</th>
		            	<th>최저수위</th>
		            	<th>수위(EL.m)</th>
		            	<th>최저수위 확정 사유</th>
		            </tr>
				</thead>
				<tbody id="resultSiteTable">
					<c:if test="${empty resultList}">
						<tr>
							<td colspan="30">검색 결과가 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${not empty resultList}">
						<c:forEach var="item" items="${resultList}" varStatus="idx">
							<tr>
								<td>${item.ROWNO}</td>
								<td>${item.WRSSM_NM }</td>
								<td>${item.OBSNM }</td>
								<td>${item.WFFCLY_NM }</td>
								<td>${item.RSTRCT_WAL }</td>
								<td>${item.FCLTY_IMPRVM_CT }</td>
								<td>${item.FCLTY_PURPS }</td>
								<td>${item.FCLTY_PURPS_CL }</td>
								<td>${item.DO_NM }</td>
								<td>${item.CTY_NM }</td>
								<td>${item.DETAIL_ADRES }</td>
								<td>${item.BRRER_UPSTRIM_GAP_DSTNC }</td>
								<td>${item.MANAGE_INSTT }</td>
								<td>${item.CHARGER }</td>
								<td>${item.OPER_INSTT }</td>
								<td>${item.OPERATOR }</td>
								<td>${item.OPER_BEGIN_DE }</td>
								<td>${item.MAIN_USE_MD}</td>
								<td>${item.USER_CO }</td>
								<td>${item.SELNG_AM }</td>
								<td>${item.BRRER_MANAGE_WAL }</td>
								<td>${item.BRRER_UGRWTR_RSTRCT_WAL }</td>
								<td>${item.BRRER_LWLT_WAL }</td>
								<td>${item.BRRER_LWET_WAL }</td>
								<td>${item.LWET_WAL }</td>
								<td>${item.LWET_WAL_DCSN_PRVONSH }</td>
								<td>${item.LWET_WAL_METHOD }</td>
								<td>${item.LFTBNK_RHTBNK }</td>
								<td>${item.CPRMISN_AT }</td>
								<td>${item.RM }</td>
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



