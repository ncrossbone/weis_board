package egovframework.cms.menu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cms.menu.service.MenuService;
import egovframework.common.mapper.CommonMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("MenuService")
public class MenuServiceImpl extends EgovAbstractServiceImpl implements MenuService  {

	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;

	private String namespace = "menuMapper.";
	private String namespace_code = "codeMapper.";

	/**
	 * menu정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getMenuList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"menuList");
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
}


