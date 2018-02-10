package egovframework.cms.log.access.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.common.mapper.CommonMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("AccessLogService")
public class AccessLogServiceImpl extends EgovAbstractServiceImpl implements AccessLogService {

	
	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "accessLogMapper.";
	
	/**
	 * Access 로그 등록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public Integer insertAccessLog(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"insertAccessLog");
		Integer resultInt = (Integer) commonMapper.insert(param);
		return resultInt;
	}
	/**
	 * Access 로그 전체 목록
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String, Object>> getAccessLogList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getAccessLogList");
		List<Map<String, Object>> reslutList = commonMapper.getList(param);
		return reslutList;
	}
	/**
	 * Access 로그 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public int getAccessLogCount(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getAccessLogCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
}
