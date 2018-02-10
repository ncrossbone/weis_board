package egovframework.cms.log.access.service;

import java.util.List;
import java.util.Map;

public interface AccessLogService {
	
	/**
	 * Access 로그 등록
	 * @param Map
	 * @exception Exception Exception
	 */
	public Integer insertAccessLog(Map<String, Object> param) throws Exception;
	/**
	 * Access 로그 전체 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getAccessLogList(Map<String, Object> param) throws Exception;
	
	/**
	 * Access 로그 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getAccessLogCount(Map<String, Object> param) throws Exception;
}
