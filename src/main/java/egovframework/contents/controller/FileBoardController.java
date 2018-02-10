package egovframework.contents.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.cms.attach.service.FileService;
import egovframework.cms.code.service.CodeService;
import egovframework.common.controller.DefaultController;
import egovframework.common.util.Pager;
import egovframework.common.util.StringUtil;
import egovframework.common.utilDo.FileHandler;
import egovframework.contents.service.FileBoardService;

@Controller
@RequestMapping("/egov/contents")
public class FileBoardController extends DefaultController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "FileBoardService")
	private FileBoardService fileBoardService;
	
	@Resource(name = "FileService")
	private FileService fileService;
	
	@Resource(name = "CodeService")
	private CodeService codeService;
	
    /**
     * 파일 게시판 정보 
     * @return
     * @throws Exception
   */
	@SuppressWarnings("unchecked")
 	@RequestMapping(value = "/site/fileBoardInfo")
 	public String fileBoardInfo(Model model) throws Exception {
	  	try{
	  		List<Map<String, Object>> descriptionList = fileBoardService.getDescriptionList(commandMap);
	  		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
	  		Map<String, Object> descriptionItem = new HashMap<String, Object>();
	  		
	  		for(int i=0; i<descriptionList.size(); i++){
	  			descriptionItem = descriptionList.get(i);
	  			if(!descriptionItem.get("ATCHMNFL_ID").equals("")){
	  				descriptionItem.put("fileList", fileService.getFileList(descriptionItem));
	  			}
	  			
	  			fileList.add(descriptionItem);
	  		}
	  		
	  		model.addAttribute("fileList", fileList);
			model.addAttribute("commandMap", commandMap);
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	
 		return "/contents/fileBoard/info";
	}

    /**
     * 이미지 게시판 정보 
     * @return
     * @throws Exception
   */
	@SuppressWarnings("unchecked")
 	@RequestMapping(value = "/site/imageBoardInfo")
 	public String imageBoardInfo(Model model) throws Exception {
	  	try{
		  	int pageSize = 10;
		  	int page = StringUtil.toInt( commandMap.get( "page" ), 1 );		
		  	int startDomainRow = page * pageSize - pageSize + 1;
		  	int totalCnt = 0;
		  	commandMap.put( "startRow", startDomainRow );
		  	commandMap.put( "endRow", startDomainRow + pageSize - 1 );
	  		List<Map<String, Object>> imgFileList = new ArrayList<Map<String, Object>>();
	  		
	  		if(commandMap.get("dta_code").equals("080101")){
	  			commandMap.put("tableNm", "V_DRONE_PHODTA_DE");
	  		}else if(commandMap.get("dta_code").equals("080201")){
	  			commandMap.put("tableNm", "V_FLIGHT_PHODTA_DE");
	  		}

	  		if(commandMap.containsKey("doSearch") && commandMap.get("doSearch").equals("Y")){
  			  	totalCnt = fileBoardService.getImgSrcFileCount(commandMap);
  				imgFileList = fileBoardService.getImgSrcFileList(commandMap);
  			}else{
  			  	totalCnt = fileBoardService.getImgFileCount(commandMap);
  				imgFileList = fileBoardService.getImgFileList(commandMap);
  			}
			model.addAttribute("totalCnt", totalCnt);
		  	Pager pager = new Pager( page, totalCnt, pageSize );
		  	
	  		model.addAttribute("pager", pager);
	  		model.addAttribute("imgFileList", imgFileList);
	  		model.addAttribute("boList", codeService.getBoList(commandMap));
			model.addAttribute("commandMap", commandMap);
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	
 		return "/contents/imageBoard/info";
	}
	
	@RequestMapping(value="/attach/fileDownload")
	public void fileDownload(HttpServletResponse respose) {
		
		try {
	  		if(commandMap.get("dta_code").equals("080101")){
	  			commandMap.put("tableNm", "V_DRONE_PHODTA_DE");
	  		}else if(commandMap.get("dta_code").equals("080201")){
	  			commandMap.put("tableNm", "V_FLIGHT_PHODTA_DE");
	  		}

			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap = fileBoardService.selectOneFile(commandMap);
			
			//파일 풀패스가 있을때 다운로드
			if(fileMap != null) {
				
				FileHandler.fileDownload(fileMap, respose);
				
			} else {
				
				//파일정보가 조회 되지 않았을때 처리
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			
			logger.error("AttachController.fileDownload > " + e.toString());
			
		}
	}
	
	@RequestMapping(value="/attach/thumbfileDownload")
	public void thumbfileDownload(HttpServletResponse respose) {
		
		try {
	  		if(commandMap.get("dta_code").equals("080101")){
	  			commandMap.put("tableNm", "V_DRONE_PHODTA_DE");
	  		}else if(commandMap.get("dta_code").equals("080201")){
	  			commandMap.put("tableNm", "V_FLIGHT_PHODTA_DE");
	  		}

			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap = fileBoardService.selectOneFile(commandMap);
			
			//파일 풀패스가 있을때 다운로드
			if(fileMap != null) {
				
				FileHandler.thumbfileDownload(fileMap, respose);
				
			} else {
				
				//파일정보가 조회 되지 않았을때 처리
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			
			logger.error("AttachController.fileDownload > " + e.toString());
			
		}
	}

	@RequestMapping(value="/attach/zipDownload")
	public void zipDownload(HttpServletRequest req, HttpServletResponse res) {
		
		try {
	  		if(commandMap.get("dta_code").equals("080101")){
	  			commandMap.put("tableNm", "V_DRONE_PHODTA_DE");
	  		}else if(commandMap.get("dta_code").equals("080201")){
	  			commandMap.put("tableNm", "V_FLIGHT_PHODTA_DE");
	  		}
	  	
			List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
			fileList = fileBoardService.selectZipFileList(commandMap);
			
			//파일 풀패스가 있을때 다운로드
			if(fileList != null) {
				
				FileHandler.zipfileDownload(fileList, req, res);
				
			} else {
				
				//파일정보가 조회 되지 않았을때 처리
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			
			logger.error("AttachController.fileDownload > " + e.toString());
			
		}
	}

	@RequestMapping(value="/attach/imgDetailList")
	@ResponseBody
	public List<Map<String, Object>> imgDetailList(HttpServletRequest req, HttpServletResponse res) {
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		try {
	  		if(commandMap.get("dta_code").equals("080101")){
	  			commandMap.put("tableNm", "V_DRONE_PHODTA_DE");
	  		}else if(commandMap.get("dta_code").equals("080201")){
	  			commandMap.put("tableNm", "V_FLIGHT_PHODTA_DE");
	  		}
	  	
			fileList = new ArrayList<Map<String, Object>>();
			fileList = fileBoardService.selectZipFileList(commandMap);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return fileList;
	}
}
