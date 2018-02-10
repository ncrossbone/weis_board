package egovframework.contents.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.cms.code.service.CodeService;
import egovframework.common.controller.DefaultController;
import egovframework.common.util.Pager;
import egovframework.common.util.StringUtil;
import egovframework.contents.service.BasicBoardService;

@Controller
@RequestMapping("/egov/contents")
public class BasicBoardController extends DefaultController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "BasicBoardService")
	private BasicBoardService basicBoardService;
	
	@Resource(name = "CodeService")
	private CodeService codeService;
	
    /**
      * 메인 화면 오픈 
      * @return
      * @throws Exception
    */
	@SuppressWarnings("unchecked")
  	@RequestMapping(value = "/site/basicBoardInfo")
  	public String bagicBoardInfo(Model model) throws Exception {
		List<Map<String, Object>> optionList_1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> optionList_2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> optionList_3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String rtnUrl = "";
		
		int totalCnt = 0;
	  	int pageSize = 10;
	  	int page = StringUtil.toInt( commandMap.get( "page" ), 1 );		
	  	int startDomainRow = page * pageSize - pageSize + 1;
	  	commandMap.put( "startRow", startDomainRow );
	  	commandMap.put( "endRow", startDomainRow + pageSize - 1 );
		
		//행정구역 목록조회
		commandMap.put("ADM_LVL", "1");
  		optionList_1 = codeService.getDistrictList(commandMap);	
		//수계 목록조회
		optionList_2 = codeService.getWaterSysList(commandMap);	  	
	  	try{
	  		if(!commandMap.containsKey("dta_code") || commandMap.get("dta_code").equals("010101") || commandMap.get("dta_code").equals("")){			//수질 측정망
		  		//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//		  		commandMap.put("FROM_NM", "V_RWMDTI_NEW_DE");
//		  		commandMap.put("YEAR_COL_NM", "WMYR");
	  			commandMap.put("startY", 1989);
	  			commandMap.put("endY", 2017);
		  		optionList_3 = codeService.getDate(commandMap);
		  		
		  		if(!commandMap.containsKey("dta_code") || commandMap.get("dta_code").equals("")){
		  			commandMap.put("dta_code", "010101");
		  			commandMap.put("openMenu", "0");
		  			commandMap.put("menuNm", "수질측정망");
		  		}
		  		
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
  					String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
	  				resultList = basicBoardService.getSiteResultListList(commandMap);
	  				
		  			if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
		  		}
	  		}else if(commandMap.get("dta_code").equals("010201") || commandMap.get("dta_code").equals("010202") || commandMap.get("dta_code").equals("010302")){	
		  		//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//	  			if(commandMap.get("dta_code").equals("010201")){
//		  			commandMap.put("FROM_NM", "V_RWMDTI_B_DE");
//		  		}else if(commandMap.get("dta_code").equals("010202")){
//		  			commandMap.put("FROM_NM", "V_RWMDTI_SC_DE");
//		  		}else if(commandMap.get("dta_code").equals("010302")){
//		  			commandMap.put("FROM_NM", "V_RWMDTI_SC_DE");
//		  		}
//	  			
//		  		commandMap.put("YEAR_COL_NM", "WMYR");
	  			if(commandMap.get("dta_code").equals("010201")){
	  				commandMap.put("startY", 2012);
		  			commandMap.put("endY", 2017);
	  			}else if(commandMap.get("dta_code").equals("010202")){
	  				commandMap.put("startY", 2000);
		  			commandMap.put("endY", 2017);
	  			}else if(commandMap.get("dta_code").equals("010302")){
	  				commandMap.put("startY", 2010);
	  				commandMap.put("endY", 2018);
	  			}
		  		optionList_3 = codeService.getDate(commandMap);
		  		
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
  					String[] admCdList = null;
  					String getAdmCd = "";
  					if(commandMap.get("adm_cd") != null){
  						getAdmCd = commandMap.get("adm_cd").toString();
  						admCdList = getAdmCd.split(",");
  					}
  					
  					commandMap.put("adm_cd_list", admCdList);
  					
	  				if(commandMap.get("dta_code").equals("010201")){
	  				  	totalCnt = basicBoardService.getWaterForecastCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	
	  					resultList = basicBoardService.getWaterForecastList(commandMap);				//수질예보제(조류자료)
	  					model.addAttribute("pager", pager);
	  				}else if(commandMap.get("dta_code").equals("010202")){
	  				  	totalCnt = basicBoardService.getBirdWarningCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	
	  					resultList = basicBoardService.getBirdWarningList(commandMap);					//조류경보제
	  					model.addAttribute("pager", pager);
	  				}else if(commandMap.get("dta_code").equals("010302")){
	  					totalCnt = basicBoardService.getTmsCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	
	  					resultList = basicBoardService.getTmsList(commandMap);					//수질원격감시체계
	  					model.addAttribute("pager", pager);
	  				}
	  				
	  			}
	  		}else if(commandMap.get("dta_code").equals("030101")){									//보 구간 수생태계 모니터링 사업
	  			
	  			if(!commandMap.containsKey("siteTypeItem") || commandMap.get("siteTypeItem").equals("S")){	//수질			
	  				commandMap.put("startY", 2010);
	  				commandMap.put("endY", 2016);
	  				optionList_3 = codeService.getDate(commandMap);
				}
		  		
	  			//검색결과 조회
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
	  				String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				
	  				commandMap.put("adm_cd_list", admCdList);
	  				if(commandMap.get("siteTypeItem").equals("S")){			
	  				  	totalCnt = basicBoardService.getBoRegion_SCount(commandMap);
	  				  	
	  				    resultList = basicBoardService.getBoRegion_SList(commandMap);							//수질
  					}else if(commandMap.get("siteTypeItem").equals("D")){			
	  				  	totalCnt = basicBoardService.getBoRegion_DCount(commandMap);
	  				  	
	  				    resultList = basicBoardService.getBoRegion_DList(commandMap);							//동물	
  					}else if(commandMap.get("siteTypeItem").equals("SM")){			
	  				  	totalCnt = basicBoardService.getBoRegion_SMCount(commandMap);
	  				  	
	  				    resultList = basicBoardService.getBoRegion_SMList(commandMap);							//식물	
  					}else if(commandMap.get("siteTypeItem").equals("J")){			
	  				  	totalCnt = basicBoardService.getBoRegion_JCount(commandMap);
	  				  	
	  				    resultList = basicBoardService.getBoRegion_JList(commandMap);							//저서성대형무척추동물
  					}else if(commandMap.get("siteTypeItem").equals("U")){			
	  				  	totalCnt = basicBoardService.getBoRegion_UCount(commandMap);
	  				  	
	  				    resultList = basicBoardService.getBoRegion_UList(commandMap);							//어류	
  					}

  					model.addAttribute("totalCnt", totalCnt);
  				  	Pager pager = new Pager( page, totalCnt, pageSize );
  				  	model.addAttribute("pager", pager);	
	  			}
	  		}else if(commandMap.get("dta_code").equals("030601")){									//어패류 구제 현황
	  			//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//		  		commandMap.put("FROM_NM", "V_FSNSLFS_RLIFDTA_DE");
//		  		commandMap.put("YEAR_COL_NM", "RLIF_ACT_DE");
	  			commandMap.put("startY", 2017);
	  			commandMap.put("endY", 2018);
		  		optionList_3 = codeService.getDate(commandMap);
		  		
	  			//검색결과 조회
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
	  				String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				
	  				commandMap.put("adm_cd_list", admCdList);
  					resultList = basicBoardService.getFsSuccorList(commandMap);				
  					
  					if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
	  			}
	  		}else if(commandMap.get("dta_code").equals("040201") || commandMap.get("dta_code").equals("050201")){									

		  		//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//	  			if(commandMap.get("dta_code").equals("040201")){
//		  			commandMap.put("FROM_NM", "V_DEST_BNFDTA_DE");
//			  		commandMap.put("YEAR_COL_NM", "EXAMIN_YEAR");
//	  			}else if(commandMap.get("dta_code").equals("050201")){
//		  			commandMap.put("FROM_NM", "V_ATMCFLUX_TM");
//			  		commandMap.put("YEAR_COL_NM", "OBSR_DT");
//	  			}
	  			if(commandMap.get("dta_code").equals("040201")){
	  				commandMap.put("startY", 2016);
	  				commandMap.put("endY", 2016);
	  			}else if(commandMap.get("dta_code").equals("050201")){
	  				commandMap.put("startY", 2009);
	  				commandMap.put("endY", 2017);
	  			}
		  		optionList_3 = codeService.getDate(commandMap);
	  			//검색결과 조회
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
	  				String[] admCdList = null;
  					String getAdmCd = "";
  					if(commandMap.get("adm_cd") != null){
  						getAdmCd = commandMap.get("adm_cd").toString();
  						admCdList = getAdmCd.split(",");
  					}
  					
  					commandMap.put("adm_cd_list", admCdList);
  					if(commandMap.get("dta_code").equals("040201")){			
	  				  	totalCnt = basicBoardService.getSedimentEvalCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	//4대강 보 퇴적물 요출 조사 및 평가
  						resultList = basicBoardService.getSedimentEvalList(commandMap);	
	  					model.addAttribute("pager", pager);			
  					}else if(commandMap.get("dta_code").equals("050201")){			
	  				  	totalCnt = basicBoardService.getAutoWaterCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	
	  				    resultList = basicBoardService.getAutoWaterList(commandMap);							//자동유량관측망	
	  					model.addAttribute("pager", pager);						
  					}
  								
	  			}
	  		}else if(commandMap.get("dta_code").equals("060101")){			//지하수 수질측정망
	  			//최초 진입시에만 기간조회
			  	//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//			  	commandMap.put("FROM_NM", "V_SGIS_E03_HT");
//			  	commandMap.put("YEAR_COL_NM", "GWMYR");
	  			commandMap.put("startY", 2010);
	  			commandMap.put("endY", 2017);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
		  		//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
		  			if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
		  				resultList = basicBoardService.getPollutionConcernList(commandMap);				//오염우려지역
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
		  				resultList = basicBoardService.getNormalSiteList(commandMap);					//일반지역
		  			}
		  			
		  			if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
	  			}
	  		}else if(commandMap.get("dta_code").equals("040101")){			//퇴적물 측정망 운영
  				//최초 진입시에만 기간조회
  				//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//			  	commandMap.put("FROM_NM", "V_SDM_RWMDTI_DE");
//			  	commandMap.put("YEAR_COL_NM", "WMYR");
	  			commandMap.put("startY", 2015);
  				commandMap.put("endY", 2015);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
	  				String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				
	  				commandMap.put("adm_cd_list", admCdList);
	  				if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("SDM_ALL")){
	  					resultList = basicBoardService.getSdmAllList(commandMap);
	  				}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("R")){
		  				resultList = basicBoardService.getSdmRList(commandMap);				//하천
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("L")){
		  				resultList = basicBoardService.getSdmLList(commandMap);				//호소
		  			}
	  				
	  				if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
	  			}
	  		}else if(commandMap.get("dta_code").equals("050101")){			//수위/유량 측정망
	  			
  				//최초 진입시에만 기간조회
  				//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//			  	commandMap.put("FROM_NM", "V_WLHR_TM");
//			  	commandMap.put("YEAR_COL_NM", "YMDH");
	  			commandMap.put("startY", 2017);
	  			commandMap.put("endY", 2018);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
	  				String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
	  				if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
		  				resultList = basicBoardService.getWaterLvlList(commandMap);				//수위
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
		  				resultList = basicBoardService.getDischargeList(commandMap);			//유량
		  			}
	  				
	  				if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
	  			}
	  		}else if(commandMap.get("dta_code").equals("050301")){			
	  			commandMap.put("startY", 2012);
	  			commandMap.put("endY", 2017);
		  		optionList_3 = codeService.getDate(commandMap);
		  		
			  	//검색결과 조회
			  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
			  			
			  			String[] admCdList = null;
	  					String getAdmCd = "";
	  					if(commandMap.get("adm_cd") != null){
	  						getAdmCd = commandMap.get("adm_cd").toString();
	  						admCdList = getAdmCd.split(",");
	  					}
	  					commandMap.put("adm_cd_list", admCdList);
	  					
	  					if(commandMap.get("dta_code").equals("050301")){
	  						
	  						totalCnt = basicBoardService.getBoSiteCount(commandMap);
		  					model.addAttribute("totalCnt", totalCnt);
		  				  	Pager pager = new Pager( page, totalCnt, pageSize );
		  				  	
		  					resultList = basicBoardService.getBoSiteList(commandMap);			
		  					model.addAttribute("pager", pager);
	  						
	  						
		  				}
		  			};
	  		}else if(commandMap.get("dta_code").equals("010102")){			
	  			//년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
//		  		commandMap.put("FROM_NM", "V_RWMDT_DEP_DE");
//		  		commandMap.put("YEAR_COL_NM", "WMYMD");
	  			commandMap.put("startY", 2017);
	  			commandMap.put("endY", 2017);
		  		optionList_3 = codeService.getDate(commandMap);
		  		
			  	//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			
		  			String[] admCdList = null;
  					String getAdmCd = "";
  					if(commandMap.get("adm_cd") != null){
  						getAdmCd = commandMap.get("adm_cd").toString();
  						admCdList = getAdmCd.split(",");
  					}
  					commandMap.put("adm_cd_list", admCdList);
  					
  					if(commandMap.get("dta_code").equals("010102")){
  						
  						totalCnt = basicBoardService.getWaterDetailCount(commandMap);
	  					model.addAttribute("totalCnt", totalCnt);
	  				  	Pager pager = new Pager( page, totalCnt, pageSize );
	  				  	
	  					resultList = basicBoardService.getWaterDetailList(commandMap);			
	  					model.addAttribute("pager", pager);
	  				}
	  			};
	  		}else if(commandMap.get("dta_code").equals("060202") || commandMap.get("dta_code").equals("060203")){		
			  	//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			String[] admCdList = null;
  					String getAdmCd = "";
  					if(commandMap.get("adm_cd") != null){
  						getAdmCd = commandMap.get("adm_cd").toString();
  						admCdList = getAdmCd.split(",");
  					}
  					commandMap.put("adm_cd_list", admCdList);
  					
  					if(commandMap.get("dta_code").equals("060202")){									//4대강 관측정 조사
  						commandMap.put("tableNm", "V_UGRWTR_OBSRDTA_A");
  					}else if(commandMap.get("dta_code").equals("060203")){								//사후환경 관측정 조사
  						commandMap.put("tableNm", "V_UGRWTR_OBSRDTA_B");
  					}

					totalCnt = basicBoardService.getWaterObserveCount(commandMap);
  					model.addAttribute("totalCnt", totalCnt);
  				  	Pager pager = new Pager( page, totalCnt, pageSize );
  					
  					resultList = basicBoardService.getWaterObserveList(commandMap);			
  					model.addAttribute("pager", pager);
		  		}
	  		}else if(commandMap.get("dta_code").equals("010103")){		
	  			if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			
  					if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
                    	//확정 V_MAN_FIVEDATA_Y
                    	totalCnt = basicBoardService.getFiveDateYCount(commandMap);
                    	resultList = basicBoardService.getFiveDateYList(commandMap);			
                    }else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
                    	//미확정 V_MAN_FIVEDATA_N
                    	totalCnt = basicBoardService.getFiveDateNCount(commandMap);
                    	resultList = basicBoardService.getFiveDateNList(commandMap);			
                    }

  					model.addAttribute("totalCnt", totalCnt);
  				  	Pager pager = new Pager( page, totalCnt, pageSize );
  					
  					model.addAttribute("pager", pager);
		  		}
	  		
	  		}else if(commandMap.get("dta_code").equals("090301")){	 //취수장, 양수장, 친수시설, 유량측정소, 수위관측소, 수질측정망 현황	
			  	//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			if(commandMap.containsKey("siteTypeItem")){
		  				if(commandMap.get("siteTypeItem").equals("A")){
			  				resultList = basicBoardService.getWifcltyList(commandMap);			//취수시설
			  			}else if(commandMap.get("siteTypeItem").equals("B")){
			  				resultList = basicBoardService.getWpfcltyList(commandMap);			//양수시설
			  			}else if(commandMap.get("siteTypeItem").equals("C")){
			  				resultList = basicBoardService.getWffcltyList(commandMap);			//친수시설
			  			}
		  			}
		  			
		  			if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
		  		}
	  		}else if(commandMap.get("dta_code").equals("010301")){			//전국오염원조사
	  			commandMap.put("startY", 1996);
	  			commandMap.put("endY", 2015);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
		  		//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
		  			if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
		  				resultList = basicBoardService.getPopTotalList(commandMap);					//생활계
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
		  				resultList = basicBoardService.getFishfarmList(commandMap);					//양식계
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("C")){
		  				resultList = basicBoardService.getOpsTotalList(commandMap);					//기타수질오염원
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("D")){
		  				resultList = basicBoardService.getLandfillList(commandMap);					//매립장
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("E")){
		  				resultList = basicBoardService.getFacilityList(commandMap);					//환경기초시설
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("F")){
		  				resultList = basicBoardService.getLanduseList(commandMap);					//토지계
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("G")){
		  				resultList = basicBoardService.getIndTotalList(commandMap);					//산업계
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("H")){
		  				resultList = basicBoardService.getAnimalTotalList(commandMap);				//축산계
		  			}
		  			
		  			if(resultList != null && resultList.size() > 0){
	  					totalCnt = Integer.parseInt(resultList.get(0).get("TOTCNT").toString());
	  					Pager pager = new Pager( page, totalCnt, pageSize );
					  	model.addAttribute("totalCnt", totalCnt);
					  	model.addAttribute("pager", pager);
	  				}
	  			}
	  		}else if(commandMap.get("dta_code").equals("030102")){			//생물측정망(하천)
	  			commandMap.put("startY", 2008);
	  			commandMap.put("endY", 2016);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
		  		//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
		  			if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
		  				resultList = basicBoardService.getAemrvAtalList(commandMap);					//부착돌말류
		  				totalCnt = basicBoardService.getAemrvAtalListCount(commandMap);
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
		  				resultList = basicBoardService.getAemrvFishList(commandMap);					//어류
		  				totalCnt = basicBoardService.getAemrvFishListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("C")){
		  				resultList = basicBoardService.getAemrvInhaList(commandMap);					//서식수변
		  				totalCnt = basicBoardService.getAemrvInhaListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("D")){
		  				resultList = basicBoardService.getAemrvBemaList(commandMap);					//저서
		  				totalCnt = basicBoardService.getAemrvBemaListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("E")){
		  				resultList = basicBoardService.getAemrvVtnList(commandMap);						//식생
		  				totalCnt = basicBoardService.getAemrvVtnListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("F")){
		  				resultList = basicBoardService.getAemrvQltwtrList(commandMap);					//수질
		  				totalCnt = basicBoardService.getAemrvQltwtrListCount(commandMap);	
		  			}
		  			
  				  	Pager pager = new Pager( page, totalCnt, pageSize );
  				  	model.addAttribute("totalCnt", totalCnt);
  					model.addAttribute("pager", pager);
	  			}
	  		}else if(commandMap.get("dta_code").equals("030106")){			//생물측정망(하구)
	  			commandMap.put("startY", 2008);
	  			commandMap.put("endY", 2017);
			  	optionList_3 = codeService.getDate(commandMap);
		  		
		  		//검색결과 조회
		  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
		  			String[] admCdList = null;
	  				String getAdmCd = "";
	  				if(commandMap.get("adm_cd") != null){
	  					getAdmCd = commandMap.get("adm_cd").toString();
	  					admCdList = getAdmCd.split(",");
	  				}
	  				commandMap.put("adm_cd_list", admCdList);
		  			if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("A")){
		  				resultList = basicBoardService.getAemesAtalList(commandMap);					//부착돌말류
		  				totalCnt = basicBoardService.getAemesAtalListCount(commandMap);
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("B")){
		  				resultList = basicBoardService.getAemesFishList(commandMap);					//어류
		  				totalCnt = basicBoardService.getAemesFishListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("C")){
		  				resultList = basicBoardService.getAemesBemaList(commandMap);					//저서성대형무척추동물
		  				totalCnt = basicBoardService.getAemesBemaListCount(commandMap);	
		  			}else if(commandMap.containsKey("siteTypeItem") && commandMap.get("siteTypeItem").equals("D")){
		  				resultList = basicBoardService.getAemesVtnList(commandMap);					//식생
		  				totalCnt = basicBoardService.getAemesVtnListCount(commandMap);	
		  			}
		  			
  				  	Pager pager = new Pager( page, totalCnt, pageSize );
  				  	model.addAttribute("totalCnt", totalCnt);
  					model.addAttribute("pager", pager);
	  			}
	  		}
	  		
	  		rtnUrl = "/contents/basicBoard/info_"+commandMap.get("dta_code");
			model.addAttribute("optionList_1", optionList_1);
			model.addAttribute("optionList_2", optionList_2);
			model.addAttribute("optionList_3", optionList_3);
			model.addAttribute("resultList", resultList);
			model.addAttribute("totalCnt", totalCnt);
			model.addAttribute("commandMap", commandMap);
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	
  		return rtnUrl;
	}
	
	/**
	 * @see 지역선택 목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/site/selSiteListAjax")
	@ResponseBody
	public List<Map<String, Object>> selSiteListAjax(Model model) throws Exception {
		List<Map<String, Object>> selSiteList = new ArrayList<Map<String, Object>>();  
		try{
			selSiteList = basicBoardService.selSiteListAjax(commandMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return selSiteList;
	}
	
	/**
	 * @see 지역 결과 목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/site/siteResultListAjax")
	@ResponseBody
	public List<Map<String, Object>> siteResultListAjax(Model model) throws Exception {
		List<Map<String, Object>> siteResultList = new ArrayList<Map<String, Object>>();  
		String[] admCdList = null;
		String getAdmCd = "";
		int totalCnt = 0;
	  	int pageSize = 10;
	  	int page = StringUtil.toInt( commandMap.get( "page" ), 1 );		
	  	int startDomainRow = page * pageSize - pageSize + 1;
	  	commandMap.put( "startRow", startDomainRow );
	  	commandMap.put( "endRow", startDomainRow + pageSize - 1 );
		
		try{
			if(commandMap.get("ADM_CD") != null){
				getAdmCd = commandMap.get("ADM_CD").toString();
				admCdList = getAdmCd.split(",");
			}
			
			commandMap.put("ADM_CD_LIST", admCdList);
			
			siteResultList = basicBoardService.getSiteResultListList(commandMap);
			
			if(siteResultList != null && siteResultList.size() > 0){
				totalCnt = Integer.parseInt(siteResultList.get(0).get("TOTCNT").toString());
			}
  			
		  	Pager pager = new Pager( page, totalCnt, pageSize );
		  	model.addAttribute("totalCnt", totalCnt);
		  	model.addAttribute("pager", pager);
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return siteResultList;
	}
	
}
