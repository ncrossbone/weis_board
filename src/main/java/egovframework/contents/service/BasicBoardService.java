package egovframework.contents.service;

import java.util.List;
import java.util.Map;

public interface BasicBoardService {
	
	/**
	 * 회원 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getOptionList_1(Map<String, Object> param) throws Exception;
	
	/**
	 * 지역 선택 목록(물환경 DB이관 데이터)
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> selSiteListAjax(Map<String, Object> param) throws Exception;
	
	/**
	 * 지역 결과 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getSiteResultListList(Map<String, Object> param) throws Exception;
	
	/**
	 * 어패류 구제현황 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getFsSuccorList(Map<String, Object> param) throws Exception;

	/**
	 * 4대강 보 퇴적물 요출 조사 및 평가 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getSedimentEvalCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 4대강 보 퇴적물 요출 조사 및 평가 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getSedimentEvalList(Map<String, Object> param) throws Exception;

	/**
	 * 자동유랼관측망 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAutoWaterCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 자동유랼관측망 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getAutoWaterList(Map<String, Object> param) throws Exception;
	
	/**
	 * 오염우려지역 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getPollutionConcernList(Map<String, Object> param) throws Exception;
	
	/**
	 * 일반지역 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getNormalSiteList(Map<String, Object> param) throws Exception;
	
	/**
	 * 퇴적물 측정망 운영 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getSdmAllList(Map<String, Object> param) throws Exception;
	
	/**
	 * 퇴적물 측정망 운영 하천 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getSdmRList(Map<String, Object> param) throws Exception;
	
	/**
	 * 퇴적물 측정망 운영 호소 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getSdmLList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수위 측정망 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getWaterLvlList(Map<String, Object> param) throws Exception;
	
	/**
	 * 유량 측정망 결과
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDischargeList(Map<String, Object> param) throws Exception;

	/**
	 * 수질예보제(조류자료) 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterForecastCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질예보제(조류자료) 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getWaterForecastList(Map<String, Object> param) throws Exception;

	/**
	 * 조류경보제 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBirdWarningCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 조류경보제 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBirdWarningList(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 운영현황 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoSiteList(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 운영현황 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoSiteCount(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 수질원격감시체계
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getTmsList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질원격감시체계 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getTmsCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 수심별 정밀조사 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterDetailCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 수심별 정밀조사
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getWaterDetailList(Map<String, Object> param) throws Exception;
	
	/**
	 * 4대강 관측정 조사 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getWaterObserveCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 4대강 관측정 조사목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getWaterObserveList(Map<String, Object> param) throws Exception;
	
	/**
	 * 취수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getWifcltyList(Map<String, Object> param) throws Exception;
	
	/**
	 * 양수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getWpfcltyList(Map<String, Object> param) throws Exception;
	
	/**
	 * 친수시설목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getWffcltyList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 생활계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getPopTotalList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 양식계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getFishfarmList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 기타수질오염원 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getOpsTotalList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 매립계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getLandfillList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 환경기초시설 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getFacilityList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 토지계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getLanduseList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 산업계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getIndTotalList(Map<String, Object> param) throws Exception;
	
	/**
	 * 전국오염원조사 축산계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAnimalTotalList(Map<String, Object> param) throws Exception;

	/**
	 * 보 구간 수생태계 모니터링 사업 - 식물
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_SCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 식물
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoRegion_SList(Map<String, Object> param) throws Exception;

	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_DCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoRegion_DList(Map<String, Object> param) throws Exception;

	/**
	 * 보 구간 수생태계 모니터링 사업 - 식물
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_SMCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 동물
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoRegion_SMList(Map<String, Object> param) throws Exception;

	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_JCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 저서성대형무척추동물
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoRegion_JList(Map<String, Object> param) throws Exception;

	/**
	 * 보 구간 수생태계 모니터링 사업 - 어류
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getBoRegion_UCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 구간 수생태계 모니터링 사업 - 어류
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoRegion_UList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질 자동 측정망 확정
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getFiveDateYList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질 자동 측정망 확정
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getFiveDateYCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질 자동 측정망 미확정
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getFiveDateNList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수질 자동 측정망 미확정
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getFiveDateNCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvAtalList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvAtalListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 어류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvFishList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 어류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvFishListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 서식수변 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvInhaList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 서식수변 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvInhaListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 저서 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvBemaList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 저서 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvBemaListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 식생 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvVtnList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 식생 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvVtnListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 수질 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemrvQltwtrList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 수질 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemrvQltwtrListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemesAtalList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 부착돌말류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemesAtalListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 어류 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemesFishList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 어류 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemesFishListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 저서성대형무척추동물 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemesBemaList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 저서성대형무척추동물 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemesBemaListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 식생 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public  List<Map<String, Object>> getAemesVtnList(Map<String, Object> param) throws Exception;
	
	/**
	 * 생물측정망(하천) 식생 목록 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAemesVtnListCount(Map<String, Object> param) throws Exception;
	
}
