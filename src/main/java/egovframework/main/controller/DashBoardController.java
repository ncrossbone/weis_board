package egovframework.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.cms.menu.service.MenuService;
import egovframework.common.controller.DefaultController;
import egovframework.main.service.DashBoardService;

@Controller
@RequestMapping("/egov/main")
public class DashBoardController extends DefaultController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "DashBoardService")
	private DashBoardService dashBoardService;

	@Resource(name = "MenuService")
	private MenuService menuService;
    /**
      * 메인 화면 오픈 
      * @return
      * @throws Exception
    */
	@SuppressWarnings("unchecked")
  	@RequestMapping(value = "/site/dashboardInfo")
  	public String dashboardInfo(Model model) throws Exception {

  		return "/main/dashboard/info";
	}
	
    /**
     * 메인 화면 오픈 
     * @return
     * @throws Exception
    */
	@ResponseBody
 	@RequestMapping(value = "/site/boardInfo")
 	public List<Map<String, Object>> boardInfo(@RequestParam String gubun, @RequestParam String ptNo) throws Exception {
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		commandMap.put("ptNo",ptNo);
		commandMap.put("gubun",gubun);
		rtnList = dashBoardService.getMainGridList(commandMap);
		
 		return rtnList;
	}
	
	/**
     * 이미지 가져오기
     * @return
     * @throws Exception
    */
	@ResponseBody
 	@RequestMapping(value = "/site/getImage")
 	public void getImage(@RequestParam String paths,@RequestParam String fileName, HttpServletResponse res) throws Exception {
		
		String path = paths + File.separator + fileName + ".jpg";
		File file = new File(path);
		
		res.setContentType("application/x-msdownload;");
		res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\";");
		
		OutputStream out = res.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
			out.flush();
		}
	}
}
