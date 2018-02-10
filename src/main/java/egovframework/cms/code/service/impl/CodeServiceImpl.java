package egovframework.cms.code.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.CommandMap;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cms.code.service.CodeService;
import egovframework.common.mapper.CommonMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("CodeService")
public class CodeServiceImpl extends EgovAbstractServiceImpl implements CodeService  {

	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "codeMapper.";
	
	/**
	 * top menu정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getDetailCodeList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"detailCodeList");
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 시군구 지역구 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getDistrictList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"districtList");
		if(param.containsKey("ADM_LVL")){
			if(param.get("ADM_LVL").equals("2")){
				param.put("ADM_CD", param.get("ADM_CD").toString().substring(0, 2));
			}
		}
		
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 수계 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterSysList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"waterSysList");
		
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 수계 하위 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getWaterSysSubList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"waterSysSubList");
		
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
	
	/**
	 * 년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getDate(Map<String, Object> param) throws Exception {
//		param.put("mId", namespace+"dateYearList");
//		
//		List<Map<String,Object>> yearList = (List<Map<String,Object>>) commonMapper.getList(param);
		List<Map<String,Object>> yearList = new ArrayList<Map<String,Object>>();
		Map<String,Object> yearItem = new HashMap<String,Object>();
		int startY = 0;
		int endY = 0;
		int year = 0;
		
		if(param != null){
			if(param.containsKey("startY") && param.containsKey("endY")){
				startY = (int) param.get("startY");
				endY = (int) param.get("endY");
				year = Calendar.getInstance().get(Calendar.YEAR);
				for(int i=endY; i>=startY; i--){
					yearItem = new HashMap<String,Object>();
					yearItem.put("YEAR", i+"");
					yearList.add(yearItem);
				}
			}
		}
		
		return yearList;
	}
	
	/**
	 * 보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getBoList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"boList");
		
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return menuList;
	}
}


