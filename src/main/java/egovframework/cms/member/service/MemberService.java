package egovframework.cms.member.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.cms.member.MemberVo;

public interface MemberService {
	
	/**
	 * 회원 로그인 정보
	 * @param Map
	 * @exception Exception Exception
	 */
	public MemberVo memberLogin(Map<String, Object> param, MemberVo memberVo) throws Exception;

	/**
	 * 회원 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getMemberList(Map<String, Object> param) throws Exception;

	/**
	 * 회원 정보 item
	 * @param Map
	 * @exception Exception Exception
	 */
	public Map<String, Object> getMemberItem(Map<String, Object> param) throws Exception;

	/**
	 * 회원 정보 수정
	 * @param Map
	 * @exception Exception Exception
	 */
	public int memberUpdate(Map<String, Object> param) throws Exception;

	/**
	 * 회원 정보 수정
	 * @param Map
	 * @exception Exception Exception
	 */
	public int memberInsert(Map<String, Object> param) throws Exception;
	
	public int memberFileTest(HttpServletRequest request, Map<String, Object> param) throws Exception;
}
