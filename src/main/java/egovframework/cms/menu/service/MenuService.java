package egovframework.cms.menu.service;

import java.util.List;
import java.util.Map;

import egovframework.cms.member.MemberVo;

public interface MenuService {
	
	/**
	 * 메뉴 정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getMenuList(Map<String, Object> param) throws Exception;
}
