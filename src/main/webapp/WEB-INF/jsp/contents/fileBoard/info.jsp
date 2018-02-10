<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		var periodVal = '${param.periodVal}';
		if(periodVal != ""){
			fn_setSearchDate(periodVal);
		}else{
			fn_setSearchDate("7");	
		}
		
		$('.btnFileDwn').on('click', function(){
			var atchflid = $(this).data("idx");
			var atchflsn = $(this).data("sn");
			var src = '/weis_board/egov/cms/attach/fileDownload?atchflid='+atchflid+'&atchflsn='+atchflsn;
			$("#fileDownFrame").attr("src",src);
		});

		$("input[name=item]").on('click', function(){
			fn_setSearchDate($(this).val());
			
			if($(this).val() != "all"){
				var clareCalendar = {
					monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
					dateFormat: 'yy-mm-dd', //형식(20120303)
					changeMonth: true, //월변경가능
					changeYear: true, //년변경가능
					showMonthAfterYear: true, //년 뒤에 월 표시
					buttonText: 'Calendar',
					buttonImageOnly: true, //이미지표시
					buttonImage: '/weis_board/images/comm/icon_calendar.png',
					showOn: "both", //(both,button)
					yearRange: '2012:2020'
				};
				$('.datepicker').datepicker(clareCalendar);
			}
		});

		$('#srcBtn').on('click', function(){
			$('#periodVal').val($(':radio[name="item"]:checked').val());
			if($('#doc_sj').val() == '검색어 입력'){
				$('#doc_sj').val("");
			}
			fnSubmit("fileInfoForm", "/weis_board/egov/contents/site/fileBoardInfo");		
		});
	});
</script>
<div id="tit">
   	<h4>4대강 조사평가 보고서</h4>
    <p>4대강 조사평가 보고서와 관련된 데이터를 확인할 수 있습니다.</p>
</div>	 
<div id="inner">
  	<h5>검색조건</h5>
    <p class="info">각 단계 선택 후 검색버튼을 클릭하십시오.</p>
</div>
<form id="fileInfoForm" name="fileInfoForm" method="post"> 
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu }" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	<input type="hidden" id="periodVal" name="periodVal" value="" />
	<!--검색조건-->
	<div class="search">
		<dl>
			<dt><b>조회기간</b></dt>
			<dd>
				<div class="cond">
					<dl>
	                    <dt>시작일시</dt>
	                    <dd>
	                        <input name="startDate" type="text" class="W13p datepicker" id="startDate" readonly />
	                    </dd>
	                 
	                    <dt class="MgL50">종료일시</dt>
	                    <dd>
	                        <input name="endDate" type="text" class="W13p datepicker" id="endDate" readonly />
	                    </dd>
	                    <div class="term MgL125 MgT5">
	                        <input name="item" type="radio" id="r01" title="1주일" value="7" <c:if test="${param.periodVal eq '7' || empty param.periodVal}">checked="checked"</c:if>/>
	                        <label for="r01" >1주일</label>
	                         
	                        <input name="item" type="radio" id="r02" title="1개월" value="30" <c:if test="${param.periodVal eq '30' }">checked="checked"</c:if>/>
	                        <label for="r02" >1개월</label>
	                         
	                        <input name="item" type="radio" id="r03" title="3개월" value="90" <c:if test="${param.periodVal eq '90' }">checked="checked"</c:if>/>
	                        <label for="r03" >3개월</label>
	                         
	                        <input name="item" type="radio" id="r04" title="6개월" value="180" <c:if test="${param.periodVal eq '180' }">checked="checked"</c:if>/>
	                        <label for="r04" >6개월</label>
	                         
	                        <input name="item" type="radio" id="r05" title="1년" value="365" <c:if test="${param.periodVal eq '365' }">checked="checked"</c:if>/>
	                        <label for="r05" >1년</label>
	                         
	                        <input name="item" type="radio" id="r06" title="전체" value="all" <c:if test="${param.periodVal eq 'all' }">checked="checked"</c:if>/>
	                        <label for="r06" >전체</label>
	                    </div>
					</dl>
				</div>
			</dd>
		</dl>
	    <dl>
	    	<dt><b>제목검색</b></dt>
	        <dd>
	        <div class="cond">
	            <dl>
	                <dt>검색어</dt>
	                <dd>
	                    <input name="doc_sj" type="text" class="W25p" id="doc_sj" value="<c:if test="${empty param.doc_sj}">검색어 입력</c:if><c:if test="${!empty param.doc_sj}">${param.doc_sj }</c:if>" 
	                    									onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;" />
	                </dd>
	            </dl>
	        </div>
		    </dd>
		</dl>
		<div class="MgT20 AC"><a class="btn04" href="#" id="srcBtn" style="">검색</a></div>
	</div>	
</form>
    
<!--검색결과-->
<div class="divi MgT50">
	<div>
        <h5>검색결과
        	<span id="resultCnt">
	        	조회현황 : 
		        <c:if test="${empty fileList}">0</c:if>
		        <c:if test="${not empty fileList}">${fn:length(fileList)}</c:if> 
	        	건
        	</span>
        </h5>
    </div>
    <div class="AR MgT5">
    </div>
</div>
    
<div class="result">
    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
        <caption>측정소 선택하기</caption>
        <colgroup>
            <col width="340" />
            <col />
        </colgroup>
		<tbody>
		<c:choose>
			<c:when test="${empty fileList }">
				<tr class="file_down">
					<td colspan="2">
						검색 결과가 존재하지 않습니다.
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="item" items="${fileList}" varStatus="idx">
					<tr class="file_down">
						<th>
							<dl class="book">
								<dt>${item.DOC_SJ }</dt>
								<dd>
		                            <span>${item.DOC_PBLICTE_DE }</span>
		                            <c:forEach var="fileItem" items="${item.fileList}" varStatus="idx">
		                            	<a href="#none" class="btnFileDwn ${fn:toLowerCase(fileItem.FILE_FRMAT)}" data-idx="${fileItem.FILE_ID}" data-sn="${fileItem.FILE_SN}">[첨부파일 다운로드]</a>
		                            </c:forEach>
		                            <em>${item.DOC_PBLICTE_INSTT_NM }</em>
		                        </dd>
							</dl>
						</th>
		                <td>
							<div class="data_inc">
								<span>개요</span>
		                        <div>
		                            <p class="dtlCont">${item.DOC_SUMRY }</p>
		                        </div>
							</div>
		                    <div class="data_inc">
		                        <span>목차</span>
		                        <div class="dtlCont">
		                            ${item.DOC_CNTNT }
		                        </div>
		                    </div>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</div>
<iframe id="fileDownFrame" style="width:0px;height:0px;border:0px"></iframe>

