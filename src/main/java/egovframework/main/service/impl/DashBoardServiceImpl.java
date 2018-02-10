package egovframework.main.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.common.mapper.CommonMapper;
import egovframework.main.service.DashBoardService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("DashBoardService")
public class DashBoardServiceImpl extends EgovAbstractServiceImpl implements DashBoardService  {

	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "mainMapper.";
	
	/**
	 * top menu정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getMainGridList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+param.get("gubun")+"_MainGridList");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}
}


