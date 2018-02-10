package egovframework.cms.code.service;

import java.util.List;
import java.util.Map;

import egovframework.cms.member.MemberVo;

public interface CodeService {
	
	/**
	 * 회원 로그인 정보
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDetailCodeList(Map<String, Object> param) throws Exception;
	
	/**
	 * 시군구 지역구 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDistrictList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수계 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getWaterSysList(Map<String, Object> param) throws Exception;
	
	/**
	 * 수계 하위 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getWaterSysSubList(Map<String, Object> param) throws Exception;
	
	/**
	 * 년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDate(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getBoList(Map<String, Object> param) throws Exception;
}
