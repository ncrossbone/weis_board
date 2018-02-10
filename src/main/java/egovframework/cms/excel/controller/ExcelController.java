package egovframework.cms.excel.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import egovframework.cms.excel.service.ExcelService;
import egovframework.common.bigdata.ExcelConstants;
import egovframework.common.controller.DefaultController;
import egovframework.common.jfile.JProperties;
import egovframework.common.util.StringUtil;
/**
 * 관리자 회원 컨트롤러 클래스를 정의한다.
 * @author ICT융합사업부 개발팀 방지환
 * @since 2016.07.26
 * @version 1.0
 * @see <pre>
 * 
 *  == 개정이력(Modification Information) ==
 *  수정일           수정자          수정내용
 *  ---------    --------    ---------------------------
 *  2015.01.26   방지환          최초 생성
 * </pre>
 */
@Controller
@RequestMapping("/egov/cms")
public class ExcelController extends DefaultController {

  private Logger log = LoggerFactory.getLogger(this.getClass());
  
	@Resource(name = "ExcelService")
	private ExcelService excelService;
	
	@RequestMapping("/excel/Download")
    public ModelAndView excelDownload2(Map<String, Object> ModelMap, HttpServletResponse response) throws Exception{
		ModelAndView modelAndView = new ModelAndView("bigDataExcelView");
		String Url = JProperties.getString("EXCEL_FILE_PATH"); 
		String mapper = "excelDownMapper.";
		String templateUrl = null;
		SXSSFWorkbook swb = null;
		String sDownFileName = null;
		String queryId = null;
		String menuNm = null;
		String mid = null;
		String[] admCdList = null;
		String getAdmCd = "";
		String downType = "xls";
		//파일명
		
		if(commandMap.containsKey("menuNm")){
			menuNm = commandMap.get("menuNm").toString();
		}
		if(commandMap.containsKey("mId")){
			mid = commandMap.get("mId").toString();
		}
		if(commandMap.get("adm_cd") != null){
			getAdmCd = commandMap.get("adm_cd").toString();
			admCdList = getAdmCd.split(",");
		}
		if(commandMap.containsKey("downType")){
			downType = commandMap.get("downType").toString();
		}
		
		commandMap.put("adm_cd_list", admCdList);
		
		templateUrl=Url+mid+".xlsx";
		queryId = mapper+mid;
		sDownFileName = menuNm+"."+downType;
			
		commandMap.put("mid", queryId);
		swb = excelService.getExcel(templateUrl, 5, 0, commandMap);
		
		modelAndView.addObject(ExcelConstants.DOWN_FILE_NM, sDownFileName);
		modelAndView.addObject(ExcelConstants.SWB, swb);
		
		
		return modelAndView; 
	}
}
