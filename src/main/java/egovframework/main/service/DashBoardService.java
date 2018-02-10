package egovframework.main.service;

import java.util.List;
import java.util.Map;

import egovframework.cms.member.MemberVo;

public interface DashBoardService {
	
	/**
	 * 회원 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getMainGridList(Map<String, Object> param) throws Exception;
	
}
