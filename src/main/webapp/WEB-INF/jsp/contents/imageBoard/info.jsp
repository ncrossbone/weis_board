<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<script type="text/javascript" src="/weis_board/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	
	var _totSize = "";
	var _currentPage = "";
	var _dtaCd = "";
	$(document).ready(function(){
		_dtaCd = "${param.dta_code }";
		$('.btnFileDwn').on('click', function(){
			var brrercd = $(this).data("brrercd");
			var spotcd = $(this).data("spotcd");
			var potode = $(this).data("potode");
			
			var src = '';
			
			if('${param.doSearch}' == "Y"){
				src = '/weis_board/egov/contents/attach/fileDownload?dta_code='+_dtaCd+'&spot_code='+spotcd+'&brrer_code='+brrercd+'&potogrf_de='+potode;
			}else{
				src = '/weis_board/egov/contents/attach/zipDownload?dta_code='+_dtaCd+'&brrer_code='+brrercd;
			}
			
			$("#fileDownFrame").attr("src",src);
		});

		$('#srcBtn').on('click', function(){
			if($('#bo_src').val() == "" && $('#area_nm').val() == ""){
				$('#doSearch').val("N");
			}else{
				$('#doSearch').val("Y");	
			}
			
			fnSubmit("imgInfoForm", "/weis_board/egov/contents/site/imageBoardInfo");		
		});

		$('.detailImg').on('click', function(){
			fnImgShow($(this).closest(".thumb").find("a").data("brrercd"));
			$('.pic_view').show();
		});

		$('.next').on('click', function(){
			if(_currentPage < _totSize){
				_currentPage = parseInt(_currentPage, 10) + 1
			}
			
			$("#detailFileNm").html($(this).closest("#slides2").find("li").eq(_currentPage - 1).data("filenm"));
			$("#photoDate").html($(this).closest("#slides2").find("li").eq(_currentPage - 1).data("potode"));
			setImgPageInfo();
		});
		

		$('.prev').on('click', function(){
			if(_currentPage > 1){
				_currentPage = parseInt(_currentPage, 10) - 1
			}
			
			$("#detailFileNm").html($(this).closest("#slides2").find("li").eq(_currentPage - 1).data("filenm"));
			$("#photoDate").html($(this).closest("#slides2").find("li").eq(_currentPage - 1).data("potode"));
			setImgPageInfo();
		});

		$('.imgDetail').live('click', function(){
			$('.pic_view').hide();
		});
	});
	
	function fnImgShow(brrercd){
		$.ajax({
			type: 'POST',
			url: '/weis_board/egov/contents/attach/imgDetailList',
			data: { dta_code:_dtaCd, brrer_code:brrercd },
			dataType: 'json',
			success: function (data) {
				var html = "";
				if(data != '' && data != null) {
					console.dir(data);

					data.forEach(function (item) {
						html += '<li class="imgDetail" data-filenm="'+item.FILE_REAL_NM+'" data-potode="'+item.POTOGRF_DE+'"><a href="#none">';
						html += '	<img src="/weis_board/egov/contents/attach/fileDownload?dta_code='+_dtaCd+'&spot_code='+item.SPOT_CODE+'&brrer_code='+item.BRRER_CODE+'&potogrf_de='+item.POTOGRF_DE+'" />';
						html += '</a></li>';
					});
				}

				$(".slide").html("");
				$(".slide").html(html);

				_totSize = data.length;
				_currentPage = "1";
				$("#detailFileNm").html(data[0].FILE_REAL_NM);
				setImgPageInfo();
				
				var startSlide = 1;
				$('#slides2').slides({
					container: 'slide',
					pagination: true,
					generatePagination: false,
					paginationClass: 'page',
					start: 1,
					effect: 'slide',
					pause: 0,
					start: startSlide
				});
			}
	 		,error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert('처리중 에러가 발생하였습니다. 관리자에게 문의 바랍니다.\n' +'errorCode : ' + textStatus );
	 		}
		});
	}
	
	function setImgPageInfo(){
		$("#totSize").html(_totSize);
		$("#currentPage").html(_currentPage);
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
<form id="imgInfoForm" name="imgInfoForm" method="post"> 
	<input type="hidden" id="dta_code" name="dta_code" value="${param.dta_code }" />
	<input type="hidden" id="openMenu" name="openMenu" value="${param.openMenu }" />
	<input type="hidden" id="menuNm" name="menuNm" value="${param.menuNm }" />
	<input type="hidden" id="doSearch" name="doSearch" value="N" />
	<div class="search">
	    <div class="divi">
	        <dl class="fl W50p">
	            <dt><b>보 구간</b></dt>
	            <dd class="W60p">
	                <div class="cond">
	                    <select class="W80p" id="bo_src" name="bo_src">
	                    	<option value="">선택</option>
	                    	<c:forEach var="item" items="${boList}" varStatus="idx">
	                        	<option value="${item.BOOBSCD }" <c:if test="${param.bo_src eq item.BOOBSCD }">selected</c:if>>${item.OBSNM }</option>
	                        </c:forEach>
	                    </select>
	                </div>
	            </dd>
	        </dl>
	        <dl class="fr W50p">
	            <dt><b>지점명</b></dt>
	            <dd>
	                <div class="cond">
	                    <dl>
	                        <dt>검색어</dt>
	                        <dd>
	                            <input type="text" class="W45p" name="area_nm"  id="area_nm"  value="${param.area_nm}">
	                        </dd>
	                    </dl>
	                </div>
	            </dd>
	        </dl>
	    </div>	
	    <div class="MgT20 AC"><a class="btn04" href="#none" id="srcBtn">검색</a></div>
	</div>	
</form>

<!--검색결과-->
<div class="MgT50">
    <h5>검색결과
       	<span id="resultCnt">
        	조회현황 : 
	        <c:if test="${empty totalCnt}">0</c:if>
	        <c:if test="${not empty totalCnt}">${totalCnt}</c:if> 
        	건
       	</span>
    </h5>
</div>

<div class="result">
    <table class="st01" summary="측정소명, 주소, 조사기관으로 이루어진 표">
        <caption>측정소 선택하기</caption>
        <colgroup>
            <col width="" />
            <col width="30%" />
            <col width="" />
            <col width="30%" />
        </colgroup>
        <tbody>
	        <c:forEach var="item" items="${imgFileList}" varStatus="idx">
	        	<c:if test="${idx.index % 2 eq 0}">
	            <tr class="file_down">
	                <th>
	                	<div class="thumb">
	                    	<img class="detailImg" src="/weis_board/egov/contents/attach/thumbfileDownload?dta_code=${param.dta_code }&spot_code=${imgFileList[idx.index].SPOT_CODE}&brrer_code=${imgFileList[idx.index].BRRER_CODE}&potogrf_de=${imgFileList[idx.index].POTOGRF_DE}" />
	                    	<c:choose>
	                    		<c:when test="${param.doSearch eq 'Y'}">
	                    			<c:set var="format" value="${fn:toLowerCase(imgFileList[idx.index].FILE_FRMAT)}" />
	                    		</c:when>
	                    		<c:otherwise>
	                    			<c:set var="format" value="zip" />
	                    		</c:otherwise>
	                    	</c:choose>
	                        <a class="${format } btnFileDwn" href="#none"
	                        		data-brrercd="${imgFileList[idx.index].BRRER_CODE}" data-spotcd="${imgFileList[idx.index].SPOT_CODE}" data-potode="${imgFileList[idx.index].POTOGRF_DE}">
	                        	[이미지 다운로드]
	                        </a>
	                    </div>
	                </th>
	                <td>
	                    <div class="gallery">
	                    	<dl>
	                            <dt>파일명</dt>
	                            <dd>${imgFileList[idx.index].FILE_REAL_NM }</dd>
	                        </dl>
	                        <dl>
	                        	<dt>수계</dt>
	                            <dd>${imgFileList[idx.index].WRSSM_NM }</dd>
	                        </dl>
	                        <dl>
	                        	<dt>보구간</dt>
	                            <dd>${imgFileList[idx.index].BRRER_NM }</dd>
	                        </dl>
	                        <dl>
	                        	<dt>지점명</dt>
	                            <dd>${imgFileList[idx.index].SPOT_NM }</dd>
	                        </dl>
	                        <dl>
	                        	<dt>촬영일시</dt>
	                            <dd class="L0">${imgFileList[idx.index].POTOGRF_DE }</dd>
	                        </dl>
	                        <dl>
	                        	<dt>특이사항</dt>
	                            <dd>${imgFileList[idx.index].PARTCLR_MATTER }</dd>
	                        </dl>
	                    </div>
	                </td>
	                <c:choose>
	                	<c:when test="${empty imgFileList[idx.index + 1].FILE_REAL_NM }">
	                		<th>
                            	<div class="thumb pic_none">
                                	<img src="/images/comm/none.gif" />
                                </div>
                            </th>
                            <td>
                            	<div class="gallery">
                                	<dl>
                                        <dt>파일명</dt>
                                        <dd></dd>
                                    </dl>
                                    <dl>
                                    	<dt>수계</dt>
                                        <dd></dd>
                                    </dl>
                                    <dl>
                                    	<dt>보구간</dt>
                                        <dd></dd>
                                    </dl>
                                    <dl>
                                    	<dt>지점명</dt>
                                        <dd></dd>
                                    </dl>
                                    <dl>
                                    	<dt>촬영일시</dt>
                                        <dd></dd>
                                    </dl>
                                    <dl>
                                    	<dt>특이사항</dt>
                                        <dd></dd>
                                    </dl>
                                </div>
                            </td>
	                	</c:when>
	                	<c:otherwise>
	                		<th>
			                	<div class="thumb">
			                    	<img class="detailImg" src="/weis_board/egov/contents/attach/thumbfileDownload?dta_code=${param.dta_code }&spot_code=${imgFileList[idx.index + 1].SPOT_CODE}&brrer_code=${imgFileList[idx.index + 1].BRRER_CODE}&potogrf_de=${imgFileList[idx.index + 1].POTOGRF_DE}" />
			                    	<c:choose>
			                    		<c:when test="${param.doSearch eq 'Y'}">
			                    			<c:set var="format1" value="${fn:toLowerCase(imgFileList[idx.index + 1].FILE_FRMAT)}" />
			                    		</c:when>
			                    		<c:otherwise>
			                    			<c:set var="format1" value="zip" />
			                    		</c:otherwise>
			                    	</c:choose>
			                        <a class="${format1 } btnFileDwn" href="#"
			                        		data-brrercd="${imgFileList[idx.index + 1].BRRER_CODE}" data-spotcd="${imgFileList[idx.index + 1].SPOT_CODE}" data-potode="${imgFileList[idx.index + 1].POTOGRF_DE}">
			                        	[이미지 다운로드]
			                        </a>
			                    </div>
			                </th>
			                <td>
			                    <div class="gallery">
			                    	<dl>
			                            <dt>파일명</dt>
			                            <dd>${imgFileList[idx.index + 1].FILE_REAL_NM }</dd>
			                        </dl>
			                        <dl>
			                        	<dt>수계</dt>
			                            <dd>${imgFileList[idx.index + 1].WRSSM_NM }</dd>
			                        </dl>
			                        <dl>
			                        	<dt>보구간</dt>
			                            <dd>${imgFileList[idx.index + 1].BRRER_NM }</dd>
			                        </dl>
			                        <dl>
			                        	<dt>지점명</dt>
			                            <dd>${imgFileList[idx.index + 1].SPOT_NM }</dd>
			                        </dl>
			                        <dl>
			                        	<dt>촬영일시</dt>
			                            <dd class="L0">${imgFileList[idx.index + 1].POTOGRF_DE }</dd>
			                        </dl>
			                        <dl>
			                        	<dt>특이사항</dt>
			                            <dd>${imgFileList[idx.index + 1].PARTCLR_MATTER }</dd>
			                        </dl>
			                    </div>
			                </td>
	                	</c:otherwise>
	                </c:choose>
	            </tr>
	        	</c:if>
	        </c:forEach>
        </tbody>
    </table>
</div>

    <!--이미지 상세보기-->
    <div class="pic_view" style="display:none">
        <p class="tit">이미지 상세보기</p>
        <section>
            <div class="divi">
                <div class="W50p fl" id="detailFileNm">낙동강_창녕함안보_계성천합류부_20171109</div>
                  <div class="W50p fr AR fs12 L0"><b id="currentPage"></b> / <span id="totSize">24</span></div>
            </div>
            
            <div id="slides2" class="slides2">
                <a href="#" class="prev"></a>
                <a href="#" class="next"></a>
                <ul class="slide">
                </ul>
                <div class="date_num">
                	<span id="photoDate">2018-01-22</span>
                </div>
            </div>
        </section>
    </div>
<div class="paging">
	<%@ include file="/common/pager.jsp"%>
</div>


            
            
            
<iframe id="fileDownFrame" style="width:0px;height:0px;border:0px"></iframe>

