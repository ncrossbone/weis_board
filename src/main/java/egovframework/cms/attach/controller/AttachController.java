package egovframework.cms.attach.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.cms.attach.model.AttachVo;
import egovframework.cms.attach.service.FileService;
import egovframework.common.controller.DefaultController;
import egovframework.common.utilDo.FileHandler;

@Controller
@RequestMapping("/egov/cms")
public class AttachController extends DefaultController  {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "FileService")
	private FileService fileService;
	/* INSTANCE VAR */
	/**
	 * 
	 * @MethodName  : fileDownload
	 * @Date        : 2014. 9. 1.
	 * @author      : PSJ
	 * @Description : 썸네일 파일 다운로드
	 * @History     : 2014. 9. 1. 최초 작성
	 * @param res   : HttpServletResponse 객체
	 */
	@RequestMapping(value="/admin/thumFileDownload.do")
	public void thumFileDownload(HttpServletResponse respose, AttachVo attachModel) {
		
//		try {
//			//첨부된 파일정보를 불러온다.
//			attachModel = attachService.selectOneFile(attachModel);
//			
//			//파일 풀패스가 있을때 다운로드
//			if(attachModel != null && attachModel.getPath() != null && attachModel.getName() != null) {
//				
//				FileHandler.thumFileDownload(attachModel, respose);
//				
//			} else {
//				
//				//파일정보가 조회 되지 않았을때 처리
//				
//			}
//			
//		} catch(Exception e) {
//			
//			e.printStackTrace();
//			
//			logger.error("AttachController.fileDownload > " + e.toString());
//			
//		}
		
	}
	
	/**
	 * 
	 * @MethodName  : fileDownload
	 * @Date        : 2014. 9. 1.
	 * @author      : PSJ
	 * @Description : 파일 다운로드
	 * @History     : 2014. 9. 1. 최초 작성
	 * @param res   : HttpServletResponse 객체
	 */
	@RequestMapping(value="/attach/fileDownload")
	public void fileDownload() {
		
		try {
			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap = fileService.selectOneFile(commandMap);
			
			//파일 풀패스가 있을때 다운로드
			if(fileMap != null) {
				
				FileHandler.fileDownload(fileMap, response);
				
			} else {
				
				//파일정보가 조회 되지 않았을때 처리
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			logger.error("AttachController.fileDownload > " + e.toString());
			
		}
		
	}
	
	/** 
	 * editor에서 등록한 이미지 처리
	 * @author 이현호 과장
	 * @param request
	 * @param response
	 * @param upload
	 */
	@RequestMapping(value = "/editor/imageUpload.do")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) {
 
//        OutputStream out = null;
//        PrintWriter printWriter = null;
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
// 
//        try{
//        	String targetFolder = request.getParameter("folder");
//            String fileName = upload.getOriginalFilename();
//            byte[] bytes = upload.getBytes();
//
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//			String folder = sdf.format(date);
//			String fileDir = Config.getString("file.upload.dir") + File.separator + folder + File.separator + targetFolder;
//			
//
//			File dir = new File(fileDir);
//			if(!dir.exists()) dir.mkdirs();
//			
//            String uploadPath = fileDir + "/" + fileName;//저장경로
// 
//            out = new FileOutputStream(new File(uploadPath));
//            out.write(bytes);
//            String callback = request.getParameter("CKEditorFuncNum");
// 
//            printWriter = response.getWriter();
//
//    		String fileGrpNo = attachService.setEditorFile(fileDir, fileName, upload.getSize());
//    		
//            String fileUrl = "/sys/attach/admin/fileDownload.do?attach="+fileGrpNo;//url경로
// 
//            printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
//                    + callback
//                    + ",'"
//                    + fileUrl
//                    + "','이미지를 업로드 하였습니다.'"
//                    + ")</script>");
//            printWriter.flush();
// 
//        }catch(IOException e){
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (printWriter != null) {
//                    printWriter.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
// 
//        return;
    }	
	
}
