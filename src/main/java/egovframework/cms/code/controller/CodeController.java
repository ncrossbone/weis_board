package egovframework.cms.code.controller;

//
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;




//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//

import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.cms.code.service.CodeService;
import egovframework.common.controller.DefaultController;

@Controller
@RequestMapping("/egov/cms")
public class CodeController extends DefaultController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "CodeService")
	private CodeService codeService;

	/**
	 * @see 시군구 지역구 목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/districtListAjax")
	@ResponseBody
	public List<Map<String, Object>> districtListAjax(Model model) throws Exception {
		List<Map<String, Object>> districtList = new ArrayList<Map<String, Object>>();  
		try{
			districtList = codeService.getDistrictList(commandMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return districtList;
	}
	
	/**
	 * @see 수계 하위 목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/waterSysSubListAjax")
	@ResponseBody
	public List<Map<String, Object>> waterSysSubListAjax(Model model) throws Exception {
		List<Map<String, Object>> waterSysSubList = new ArrayList<Map<String, Object>>();  
		try{
			waterSysSubList = codeService.getWaterSysSubList(commandMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return waterSysSubList;
	}
	
	/**
	 * @see 년도 최대 최소 조회기간 조회(param : 테이블/뷰 명, 해당 테이블 년도 컬럼 명)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/dateListAjax")
	@ResponseBody
	public List<Map<String, Object>> dateListAjax(Model model) throws Exception {
		List<Map<String, Object>> yearList = new ArrayList<Map<String, Object>>();  
		try{
			//yearList = codeService.getDate(commandMap);
			
			Map<String,Object> yearItem = new HashMap<String,Object>();
			int startY = 0;
			int endY = 0;
			int year = 0;
			
			if(commandMap != null){
				if(commandMap.containsKey("startY") && commandMap.containsKey("endY")){
					startY = Integer.parseInt(commandMap.get("startY").toString());
					endY = Integer.parseInt(commandMap.get("endY").toString());
					year = Calendar.getInstance().get(Calendar.YEAR);
					for(int i=endY; i>=startY; i--){
						yearItem = new HashMap<String,Object>();
						yearItem.put("YEAR", i+"");
						yearList.add(yearItem);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return yearList;
	}
}
