package egovframework.cms.attach.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cms.attach.service.FileService;
import egovframework.common.mapper.CommonMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("FileService")
public class FileServiceImpl extends EgovAbstractServiceImpl implements FileService  {

	
	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "fileMapper.";

	/**
	 * 회원 정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getFileList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getFileList");
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);

		return menuList;
	}
	
	public Map<String, Object> selectOneFile(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getFileItem");
		Map<String,Object> fileItem = (Map<String,Object>) commonMapper.get(param);

		return fileItem;
		
	}
}


