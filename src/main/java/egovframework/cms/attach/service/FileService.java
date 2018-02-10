package egovframework.cms.attach.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.cms.member.MemberVo;

public interface FileService {

	/**
	 * 첨부파일 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getFileList(Map<String, Object> param) throws Exception;

	public Map<String, Object> selectOneFile(Map<String, Object> param) throws Exception;
}
