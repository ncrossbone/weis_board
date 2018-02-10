package egovframework.cms.log.access;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import egovframework.cms.log.access.service.AccessLogService;
import egovframework.common.controller.DefaultController;
import egovframework.common.util.Pager;
import egovframework.common.util.StringUtil;
/**
 * 접속로그 관리 컨트롤롤러
 * @author Administrator
 */
@Controller
public class AccessLogController extends DefaultController{
	
	@Resource(name = "AccessLogService")
	private AccessLogService accessLogService;
	
    // 검색 파라미터
  private List<String> searchParam	= Arrays.asList("pMENU_ID","startDay", "endDay","page");
    
	/**
	 * Access 로그 조회
	 * @return ModelAndView
	 * @param commandMap : 모든 파라미터 정보, method = GET
	 * @exception Exception Exception
	 */
	 @RequestMapping(value="/cms/accesLog")
	 public ModelAndView accesLogList() throws Exception {
		 /**************** 프로그램 로직 ****************/ 
		 
		 String startDay = StringUtil.noNull(commandMap.get("startDay")).equals("")?StringUtil.getCurDateStr("yyyy-MM-dd"):commandMap.get("startDay").toString();
		 String endDay = StringUtil.noNull(commandMap.get("endDay")).equals("")?StringUtil.getCurDateStr("yyyy-MM-dd"):commandMap.get("endDay").toString();
		 commandMap.put("startDay", startDay);
		 commandMap.put("endDay", endDay);
		 
		//페이징 처리
		 int totalCnt = accessLogService.getAccessLogCount(commandMap);
		 int pageSize = 10;
		 int page = StringUtil.toInt( commandMap.get( "page" ), 1 );		
		 int startDomainRow = page * pageSize - pageSize + 1;
		 Pager pager = new Pager( page, totalCnt, pageSize );
		 commandMap.put( "startRow", startDomainRow );
		 commandMap.put( "endRow", startDomainRow + pageSize - 1 );
		 
		 List<Map<String, Object>> accLogList = accessLogService.getAccessLogList(commandMap); 
		 
		 //파라미터 정보
		 String parameters = getRequestToQueryString( searchParam );
		 
		 /**************** 프로그램 로직 ****************/ 
		 
		 /************** ModelView 로직 **************/
		 ModelAndView mav = new ModelAndView();
		 String resultURL = "cms/log/accessLogList"; 
		 mav.addObject( "accLogList", accLogList);
		 mav.addObject("parameters", parameters);
		 mav.addObject("commandMap", commandMap);
		 mav.addObject("totalCnt", totalCnt);
		 mav.addObject("pager", pager);
		 mav.setViewName( resultURL );
		 /************** ModelView 로직 **************/
		 
		 return mav;
	 } 

}
