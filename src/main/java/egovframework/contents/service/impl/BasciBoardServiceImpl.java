package egovframework.contents.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.common.mapper.CommonMapper;
import egovframework.contents.service.BasicBoardService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("BasicBoardService")
public class BasciBoardServiceImpl extends EgovAbstractServiceImpl implements BasicBoardService  {

	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "basicBoardMapper.";
	
	/**
	 * top menu정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getOptionList_1(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"optionList_1");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}
	
	/**
	 * 지역 선택 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> selSiteListAjax(Map<String, Object> param) throws Exception {

		if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("tobe")){
			param.put("mId", namespace+"selSiteTobeList");	
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("tms")){
			param.put("mId", namespace+"selSiteTmsList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("boobsif")){
			param.put("mId", namespace+"selSiteBoobsifList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("wlobsif")){
			param.put("mId", namespace+"selSiteWlobsifList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("fwobsif")){
			param.put("mId", namespace+"selSiteFwobsifList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("sgise03")){
			param.put("mId", namespace+"selSiteSgise03List");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("sgisg02")){
			param.put("mId", namespace+"selSiteSgisg02List");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("autoWater")){
			param.put("mId", namespace+"selSiteAutoWaterList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("DepRwmpt")){
			param.put("mId", namespace+"selSiteDepRwmptList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("SiteInfo")){
			param.put("mId", namespace+"selSiteInfoList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("bmSpot")){
			param.put("mId", namespace+"selSiteBmSpotList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").toString().indexOf("ugrwtr") > -1){
			param.put("tableNm", param.get("DO_TYPE").toString().toUpperCase());
			param.put("mId", namespace+"selSiteUgrwtrList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("landfill")){
			param.put("mId", namespace+"selSiteLandfillList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("facility")){
			param.put("mId", namespace+"selSiteFacilityList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("aemrv")){
			param.put("mId", namespace+"selSiteAemrvList");
		}else if(param.containsKey("DO_TYPE") && param.get("DO_TYPE").equals("aemes")){
			param.put("mId", namespace+"selSiteAemesList");
		}else{
			param.put("mId", namespace+"selSiteRwmptList");
		}
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 지역 결과 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getSiteResultListList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"siteResultList");
		
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 어패류 구제현황 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFsSuccorList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"fsSuccorList");
		
		List<Map<String,Object>> fsSuccorList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return fsSuccorList;
	}

	/**
	 * 4대강 보 퇴적물 요출 조사 및 평가 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getSedimentEvalCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"sedimentEvalCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 4대강 보 퇴적물 요출 조사 및 평가 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getSedimentEvalList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"sedimentEvalList");
		
		List<Map<String,Object>> sedimentEvalList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return sedimentEvalList;
	}

	/**
	 * 자동유량관측망 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAutoWaterCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"autoWaterCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 자동유량관측망 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAutoWaterList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"autoWaterList");
		
		List<Map<String,Object>> sedimentEvalList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return sedimentEvalList;
	}
	
	/**
	 * 오염우려지역 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getPollutionConcernList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"pollutionConcernList");
		
		List<Map<String,Object>> pollutionConcernList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return pollutionConcernList;
	}
	
	/**
	 * 일반지역 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getNormalSiteList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"normalSiteList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 퇴적물 측정망 운영 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getSdmAllList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"sdmAllList");
		
		List<Map<String,Object>> pollutionConcernList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return pollutionConcernList;
	}
	
	/**
	 * 퇴적물 측정망 운영 하천 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getSdmRList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"sdmRList");
		
		List<Map<String,Object>> pollutionConcernList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return pollutionConcernList;
	}
	
	/**
	 * 퇴적물 측정망 운영 호소 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getSdmLList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"sdmLList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 수위 측정망 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterLvlList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"waterLvlList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 유량 측정망 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getDischargeList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"dischargeList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}

	/**
	 * 수질예보제(조류자료) 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterForecastCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"waterForecastCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수질예보제(조류자료) 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterForecastList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"waterForecastList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}

	/**
	 * 수질예보제(조류자료) 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBirdWarningCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"birdWarningCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수질예보제(조류자료) 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBirdWarningList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"birdWarningList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 운영현황 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoSiteList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"BoSiteList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 운영현황 결과 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoSiteCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"BoSiteCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수질원격감시체계 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getTmsCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"TmsCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수질원격감시체계 
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getTmsList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"TmsList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 수심별 정밀조사 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterDetailCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"WaterDetailCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수심별 정밀조사
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterDetailList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"WaterDetailList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 4대강 관측정 조사 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterObserveCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"WaterObserveCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 4대강 관측정 조사 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterObserveList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"WaterObserveList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 취수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWifcltyList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"wifcltyList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 양수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWpfcltyList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"wpfcltyList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 친수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWffcltyList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"wffcltyList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 생활계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getPopTotalList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"popTotalList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 양식계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFishfarmList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"fishfarmList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 기타수질오염원 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getOpsTotalList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"opsTotalList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 매립계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getLandfillList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"landfillBasicList");		//매립장 기본현황
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"landfillLeachList");		//매립장 침출수
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 환경기초시설 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFacilityList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"facilityDetailList");			//환경기초시설 기본현황
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"faciInTotalList");				//환경기초시설 총유입량
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"faciPipeTransferList");		//환경기초시설 관거이송량
			}else if(param.get("siteTypeItemSub").equals("D")){
				param.put("mId", namespace+"faciDirectTransferList");	//환경기초시설 직접이송량
			}else if(param.get("siteTypeItemSub").equals("E")){
				param.put("mId", namespace+"faciOutTotalList");			//환경기초시설 방류유량
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 토지계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getLanduseList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"landuseList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 산업계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getIndTotalList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"indTotalList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 전국오염원조사 축산계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAnimalTotalList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"animalTotalList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 수질 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_SCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"boRegion_SCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 수질 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoRegion_SList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boRegion_SList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_DCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"boRegion_DCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoRegion_DList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boRegion_DList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_SMCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"boRegion_SMCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoRegion_SMList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boRegion_SMList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_JCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"boRegion_JCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoRegion_JList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boRegion_JList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_UCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"boRegion_UCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoRegion_UList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boRegion_UList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 수질자동측정망 확정 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFiveDateYList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"FiveDateYList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 수질자동측정망 확정 결과 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getFiveDateYCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"FiveDateYCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 수질자동측정망 미확정 결과 
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFiveDateNList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"FiveDateNList");
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 수질자동측정망 미확정 결과 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getFiveDateNCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"FiveDateNCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvAtalList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvAtalAcpexmList");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvAtalAnalyList");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvAtalSpcsList");			//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvAtalListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvAtalAcpexmListCount");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvAtalAnalyListCount");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvAtalSpcsListCount");			//출현생물
			}
		}
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 어류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvFishList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvFishAcpexmList");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvFishAnalyList");		//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvFishSpcsList");		//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 어류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvFishListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvFishAcpexmListCount");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvFishAnalyListCount");		//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvFishSpcsListCount");			//출현생물
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 서식수변 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvInhaList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvInhaAcpexmList");			//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvInhaAnalyList");				//분석정보
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 서식수변 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvInhaListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvInhaAcpexmListCount");			//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvInhaAnalyListCount");				//분석정보
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 저서 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvBemaList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvBemaAcpexmList");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvBemaAnalyList");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvBemaSpcsList");			//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 저서 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvBemaListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvBemaAcpexmListCount");		//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvBemaAnalyListCount");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvBemaSpcsListCount");			//출현생물
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 식생 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvVtnList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvVtnAcpexmList");			//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvVtnAnalyList");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvVtnSpcsList");				//출현식생
			}else if(param.get("siteTypeItemSub").equals("D")){
				param.put("mId", namespace+"aemrvVtnAreaList");				//식생면적
			}else if(param.get("siteTypeItemSub").equals("E")){
				param.put("mId", namespace+"aemrvVtnSctnList");				//단면정보
			}else if(param.get("siteTypeItemSub").equals("F")){
				param.put("mId", namespace+"aemrvVtnSctndtlList");			//단면 구간별 정보
			}else if(param.get("siteTypeItemSub").equals("G")){
				param.put("mId", namespace+"aemrvVtnSctndomList");		//우점도
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 식생 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvVtnListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemrvVtnAcpexmListCount");			//현지조사
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemrvVtnAnalyListCount");			//분석정보
			}else if(param.get("siteTypeItemSub").equals("C")){
				param.put("mId", namespace+"aemrvVtnSpcsListCount");				//출현식생
			}else if(param.get("siteTypeItemSub").equals("D")){
				param.put("mId", namespace+"aemrvVtnAreaListCount");				//식생면적
			}else if(param.get("siteTypeItemSub").equals("E")){
				param.put("mId", namespace+"aemrvVtnSctnListCount");				//단면정보
			}else if(param.get("siteTypeItemSub").equals("F")){
				param.put("mId", namespace+"aemrvVtnSctndtlListCount");			//단면 구간별 정보
			}else if(param.get("siteTypeItemSub").equals("G")){
				param.put("mId", namespace+"aemrvVtnSctndomListCount");		//우점도
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하천) 수질 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemrvQltwtrList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"aemrvQltwtrDtaList");			//수질
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하천) 수질 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemrvQltwtrListCount(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"aemrvQltwtrDtaListCount");			//수질
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하구) 부착돌말류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemesAtalList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesAtalAcpexmList");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesAtalSpcsList");				//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하구) 부착돌말류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemesAtalListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesAtalAcpexmListCount");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesAtalSpcsListCount");				//출현생물
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하구) 어류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemesFishList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesFishAcpexmList");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesFishSpcsList");				//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하구) 어류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemesFishListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesFishAcpexmListCount");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesFishSpcsListCount");				//출현생물
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하구) 저서성대형무척추동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemesBemaList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesBemaAcpexmList");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesBemaSpcsList");				//출현생물
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하구) 저서성대형무척추동물 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemesBemaListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesBemaAcpexmListCount");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesBemaSpcsListCount");				//출현생물
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
	/**
	 * 생물측정망(하구) 식생 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getAemesVtnList(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesVtnAcpexmList");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesVtnSpcsList");				//면적
			}
		}
		
		List<Map<String,Object>> normalSiteList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return normalSiteList;
	}
	
	/**
	 * 생물측정망(하구) 식생 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAemesVtnListCount(Map<String, Object> param) throws Exception {
		if(param.containsKey("siteTypeItemSub")){
			if(param.get("siteTypeItemSub").equals("") || param.get("siteTypeItemSub").equals("A")){
				param.put("mId", namespace+"aemesVtnAcpexmListCount");			//현지조사 및 분석
			}else if(param.get("siteTypeItemSub").equals("B")){
				param.put("mId", namespace+"aemesVtnSpcsListCount");				//면적
			}
		}
		
		int reslutCnt = commonMapper.getCount(param);
		
		return reslutCnt;
	}
	
}


