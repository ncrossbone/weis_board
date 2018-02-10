package egovframework.common.jfile.web;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.JProperties;
import egovframework.common.jfile.service.JFile;
import egovframework.common.jfile.service.JFileDetails;
import egovframework.common.jfile.service.JFileService;
import egovframework.common.jfile.service.impl.JFileVO;
import egovframework.common.jfile.session.SessionUploadChecker;
import egovframework.common.jfile.utils.DateUtils;
import egovframework.common.jfile.view.JSonView;
import egovframework.common.jfile.view.JfileDownloadView;
import egovframework.common.util.StringUtil;


@Controller
public class JFileController {
	
	
	private Logger log  = LoggerFactory.getLogger(this.getClass());
		
	@Resource(name="multipartResolver")
	private MultipartResolver multipartResolver;
	
	@Autowired
	private JFileService service;
	
	@RequestMapping("/jfile/jfileuploadForm.do")
	public void jfileuploadForm() {}
	
	@RequestMapping("/jfile/jfileuploadUserForm.do")
	public void jfileuploadUserForm() {}
	
	/**
	 * 파일 ID를 읽어온다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readFileId.do")
	public ModelAndView readFileId(JFileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		String fileSeq[] = StringUtil.noNull(fileVO.getFileSeq()).split("\\|");//웹취약점 조치
		String fileId = service.getFileId(fileVO.getFileId(), fileSeq);
		modelAndView.addObject("fileId", fileId);
		SessionUploadChecker.check(request, fileId);
		return modelAndView;
	}
	
	@RequestMapping("/jfile/uploadingCheck.do")
	public ModelAndView uploadingCheck(@ModelAttribute JFileVO fileVO, HttpServletRequest request) {		
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		modelAndView.addObject("hasFileId", SessionUploadChecker.isContainsKey(request, fileVO.getFileId()));
		modelAndView.addObject("maxInactiveInterval", request.getSession().getMaxInactiveInterval());
		log.debug("\nlastAccessTime : "+DateUtils.getDateString(request.getSession().getLastAccessedTime(), "yyyy.MM.dd HH:mm:ss"));
		return modelAndView;
	}
	
	/**
	 * 파일 업로드 실행
	 * @param fileVo
	 * @param request
	 * @return
	 */
	@RequestMapping("/jfile/processUpload.do")
	public ModelAndView processUpload(@ModelAttribute JFileVO fileVo, HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		if(multipartResolver.isMultipart(request)) {
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;	

			Collection<MultipartFile> values = multiRequest.getFileMap().values();
			boolean exceedFileExt = isExceedFileExt(values);
			boolean exceedFileNameLength = isExceedFileNameLength(values);
			
			if(exceedFileNameLength || exceedFileExt) {
				try {
					if(exceedFileNameLength)
						response.sendError(GlovalVariables.Error.FILE_LENGTH_ERROR, "");
					if(exceedFileExt)
						response.sendError(GlovalVariables.Error.FILE_EXTENSION_ERROR, "");
				} catch (IOException e) {
					log.debug("입출력 오류"+e);
				}
			}else{
				try{
					if(values != null && fileVo!= null){
						service.upload(values, fileVo);
						
					}
				}finally{
					SessionUploadChecker.unCheck(request, fileVo.getFileId());
				}
			}
			
		}
		
		return modelAndView;
	}
	
	private boolean isExceedFileNameLength(Collection<MultipartFile> values) {
		if(values != null) {
			for(MultipartFile mfile : values) {
				if(mfile.getOriginalFilename().length() > 99) {
					  String fileName1 = mfile.getOriginalFilename();
					  String fileExt = fileName1.substring(fileName1.lastIndexOf(".")+1, fileName1.length());
					  fileExt = fileExt.toLowerCase();
					  if(fileExt.matches("asp|aspx|ascx|bat|cfc|cfm|cgi|cmd|com|csh|dll|exe|inf|jsp|jsp|ksh|php|php3|php5|phtml|ph|reg")){
						  return true;
					  }
				}
			}
		}
		return false;
	}
	private boolean isExceedFileExt(Collection<MultipartFile> values) {
		if(values != null) {
			for(MultipartFile mfile : values) {
				  String fileName1 = mfile.getOriginalFilename();
				  String fileExt = fileName1.substring(fileName1.lastIndexOf(".")+1, fileName1.length());
				  fileExt = fileExt.toLowerCase();
				  if(fileExt.matches("asp|aspx|ascx|bat|cfc|cfm|cgi|cmd|com|csh|dll|exe|inf|jsp|jsp|ksh|php|php3|php5|phtml|ph|reg")){
					  return true;
				  }
			}
		}
		return false;
	}
	
	/**
	 * 파일업로드가 완료된 후 처리 작업을 수행한다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/afterProcessUploadCompleted.do")
	public ModelAndView afterProcessUploadCompleted(JFileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		service.executeAfterUploadCompleted(fileVO.getFileId());
		SessionUploadChecker.unCheck(request, fileVO.getFileId());
		return modelAndView;
	}
	
	/**
	 * 파일 아이디로 첨부파일 목록을 읽어온다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readFiles.do")
	public ModelAndView readFiles(JFileVO fileVO) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		List<JFileDetails> fileList= service.getAttachFiles(fileVO.getFileId());			
		modelAndView.addObject("fileList", fileList);
		return modelAndView;
	}	
	
	/**
	 * 
	 */
	@RequestMapping("/jfile/jfiledownloadForm.do")
	public void jfiledownloadForm() {}

	
	/**
	 * 파일 아이디로 첨부파일 목록을 읽어온다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readDownloadFiles.do")
	public ModelAndView readDownloadFiles(JFileVO fileVO) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		modelAndView.addObject("fileList", service.getAttachFiles(fileVO.getFileId()));
		return modelAndView;
	}
	
	/**
	 * 파일을 다운로드 받는다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readDownloadFile.do")
	public ModelAndView readDownloadFile(JFileVO fileVO) {	
		System.out.println(fileVO.toString());
		JFile downloadFile = service.getFile(fileVO.getFileId(), fileVO.getFileSeq(), fileVO.getUseSecurity());
		// 다운로드 카운트를 증가 시킨다.
		service.updateAttachFileDownloadCountBySequence(fileVO.getFileId(), fileVO.getFileSeq());
        return new ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME, downloadFile);
	}

	/**
	 * 멀티 업로드된 모든 파일을 zip로 압축하여 다운로드 받는다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/downloadAll.do")
	public ModelAndView downloadAll(JFileVO fileVO) {
		JFile[] downloadZipFile = service.getFiles(fileVO.getFileId(), fileVO.getUseSecurity());
		service.updateAttachFileDownloadCountByFileId(fileVO.getFileId());
		return new ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME, downloadZipFile);
	}
	
	/**
	 * 이미지 파일일 경우 미리보기를 한다. ,fileThumbnailYn = Y 일경우 썸네일 이미지를 보여준다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/preview.do")
	public ModelAndView preview(JFileVO fileVO) {
		JFileDetails prevFileVO = null;
		prevFileVO = service.getAttachFile(fileVO.getFileId(), fileVO.getFileSeq());
		JFile previewFile =null;
		if(prevFileVO == null){
			previewFile = new JFile(getNoImagePath());
		}else if(fileVO.getFileThumbnailYn() !=null &&fileVO.getFileThumbnailYn().equals("Y") && !prevFileVO.getFileThumbnailYn().equals("Y")){
			previewFile = new JFile(getNoImagePath());
		}else{
			previewFile = prevFileVO.isImage() ?
					service.getFileBySequence(fileVO.getFileId(), fileVO.getFileSeq(), fileVO.getUseSecurity(), fileVO.getFileThumbnailYn()) : new JFile(getNoImagePath());
		}
		return new ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME, previewFile);
	}
	
	/**
	 * 이미지 경로를 읽어온다.
	 * @return
	 */
	private String getNoImagePath() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("/") + JProperties.getString(GlovalVariables.DEFAULT_NO_IMAGE_APP_PATH_KEY);
	}

	/**
	 * 에디터 파일 업로드
	 * @param commandMap : 모든 파라미터 정보, method = GET
	 * @exception Exception Exception
	 */
	 @RequestMapping(value={"/editor/upload.do","/editor/upload"} )
	 public ModelAndView editorUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		 
		 JFileVO fileVO = new JFileVO();
		 fileVO.setFileSeq("");
		 fileVO.setUploadMode("db");
		 fileVO.setFileThumbnailYn("N");
		 String fileSeq[] = fileVO.getFileSeq().split("\\|");
		 String fileId = service.getFileId(fileVO.getFileId(), fileSeq);
		 fileVO.setFileId(fileId);
		 
		 if(multipartResolver.isMultipart(request)) {
				final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;	

				Collection<MultipartFile> values = multiRequest.getFileMap().values();
				boolean exceedFileNameLength = isExceedFileNameLength(values);
				boolean exceedFileExt = isExceedFileExt(values);
				
				if(exceedFileNameLength || exceedFileExt) {
					try {
						if(exceedFileNameLength)
							response.sendError(GlovalVariables.Error.FILE_LENGTH_ERROR, "");
						if(exceedFileExt)
							response.sendError(GlovalVariables.Error.FILE_EXTENSION_ERROR, "");
					} catch (IOException e) {
						log.debug("입출력 오류"+e);
						
					}
				}else{
					try{
						service.upload(values, fileVO);
					}finally{
						SessionUploadChecker.unCheck(request, fileVO.getFileId());
					}
				}
				
			}
		 
		 ModelAndView mav = new ModelAndView();
		 String resultURL = "/ckeditor/upload";
		 mav.setViewName( resultURL );
		 mav.addObject("fileId", fileId);
		 mav.addObject("CKEditorFuncNum", request.getParameter("CKEditorFuncNum"));
		 
		 return mav;
		 
	 } 
}