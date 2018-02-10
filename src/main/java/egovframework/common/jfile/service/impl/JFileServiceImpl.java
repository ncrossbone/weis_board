package egovframework.common.jfile.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.JProperties;
import egovframework.common.jfile.service.JFile;
import egovframework.common.jfile.service.JFileDetails;
import egovframework.common.jfile.service.JFileService;
import egovframework.common.jfile.service.template.JFileUploadModeFactory;
import egovframework.common.jfile.service.template.JFileUploadModeTemplate;
import egovframework.common.jfile.session.SessionUploadChecker;

@Service
public class JFileServiceImpl implements JFileService {

	/** 로거 */
	protected Logger logger  = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="multipartResolver")
	private MultipartResolver multipartResolver;
	
	@Autowired
	private AttachFileMapper mapper;
	
	private Object EMPTY_OBJECT = "";
	
	public String getFileId(String fileId, Object fileSeq) {		
		initializeAttachFileStatus(fileId, fileSeq);		
		return JFileUploadModeFactory.INSTANCE.getUploadType(JFileService.DB_MODE).getHandler().getFileId(fileId);
	}
	
	public void initializeAttachFileStatus(String fileId, Object fileSeq) {
		if(StringUtils.hasText(fileId))
			updateAttachFileDeleteYnByFileId(fileId, "N");
		
		if(fileSeq != null && !EMPTY_OBJECT.equals(fileSeq))
			updateAttachFileDeleteYn(fileId, fileSeq.getClass().isArray() ? (Object[])fileSeq : new Object[]{fileSeq}, "Y");
	}
	
	public void addAttachFile(JFileDetails fileVo) {
		mapper.addAttachFile(fileVo);
	}

	public void updateAttachFileDeleteYn(String fileId, Object[] fileSeqs, String yn) {
		mapper.updateAttachFileDeleteYn(fileId, fileSeqs, yn);
	}

	public void updateAttachFileDeleteYnByFileId(String fileId, String deleteYn) {
		mapper.updateAttachFileDeleteYnByFileId(fileId, deleteYn);
	}

	public void updateAttachFileDownloadCount(String fileId) {
		mapper.updateAttachFileDownloadCount(fileId);
	}

	public void updateAttachFileDownloadCount(String fileId, String fileSeq) {
		mapper.updateAttachFileDownloadCount(fileId, fileSeq);
	}

	public void updateAttachFileDownloadCountBySequence(String fileId, String fileSeq) {
		mapper.updateAttachFileDownloadCountBySequence(fileId, fileSeq);
	}
	
	public void updateAttachFileDownloadCountByFileId(String fileId) {
		mapper.updateAttachFileDownloadCountByFileId(fileId);
	}
	
	public void removeAttachFile(String fileId, List<Object> fileSeqs) {
		mapper.removeAttachFile(fileId, fileSeqs);
	}
	
	public boolean isExistingAttachFileInfo(String fileId, List<Object> fileSeqs) {
		if(fileId == null || fileSeqs == null)
			return false;
		return mapper.isExistingAttachFileInfo(fileId, fileSeqs);
	}
	
	public JFileVO getAttachFile(String fileId, String fileSeq) {
		return mapper.selectAttachFile(fileId, fileSeq);
	}
	public Object[] getAttacheFileSeqs(String fileId) {
		return mapper.getAttachFileSeqs(fileId);
	}

	public List<JFileDetails> getAttachFiles(String fileId) {
		return mapper.selectAttachFiles(fileId);
	}

	public void upload(Collection<MultipartFile> multipartFiles, JFileVO fileVo) {		
		if(multipartFiles == null)
			return;		
		for (final MultipartFile file : multipartFiles) {
			upload(file, fileVo);
        }
	}
	
	public void upload(MultipartFile multipartFile, JFileVO fileVo) {
		JFileUploadModeFactory.INSTANCE.getUploadType(fileVo.getUploadMode()).getHandler().upload(multipartFile, fileVo);
		if(JFileService.DB_MODE.equalsIgnoreCase(fileVo.getUploadMode())) {
			addAttachFile(fileVo);
		}
	}
	
	public void executeAfterUploadCompleted(String fileId) {
		Object[] fileSeqs = getAttacheFileSeqs(fileId);
		if(fileSeqs == null || fileSeqs.length == 0)
			return;
		
		/** 파일을 삭제한다. */
		if(fileSeqs != null) {
			JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileService.DB_MODE).getHandler();
			for(int i=0; i<fileSeqs.length; i++) {
				JFileDetails fileInfo = getAttachFile(fileId, (String)fileSeqs[i]);
				upload.deleteJFiles(fileInfo, JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), JFileService.DB_MODE);
			}
		}		
		/** 파일업로드 이력테이블(J_ATTACHFILE) 에서 삭제할 파일 목록을 삭제한다. */
		@SuppressWarnings("unchecked")	
		List<Object> list = CollectionUtils.arrayToList(fileSeqs);
		removeAttachFile(fileId, list);
	}
	
	public JFile getFile(String fileId, String fileSeq, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileService.DB_MODE).getHandler();
		return upload.getJFile(getAttachFile(fileId, fileSeq), useSecurity, JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY));
	}
	
	public JFile[] getFiles(String fileId, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileService.DB_MODE).getHandler();		
		return upload.getFiles(getAttachFiles(fileId), JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), useSecurity, JFileService.DB_MODE);
	}
	
	public JFile getFileBySequence(String fileId, String fileSeq, String useSecurity) {
		return getFileBySequence(fileId, fileSeq, useSecurity, "N") ;
	}
	public JFile getFileBySequence(String fileId, String fileSeq, String useSecurity, String fileThumbnailYn) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileService.DB_MODE).getHandler();
		return upload.getJFile(getAttachFile(fileId, fileSeq), useSecurity, JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), fileThumbnailYn);
	}
	
	/**
	 * JFile 파일 업르도 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String jfileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return  jfileUpload(request, response, "FILE_ID");
	}
	
	/**
	 * JFile 파일 업르도
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String jfileUpload(HttpServletRequest request, HttpServletResponse response, String inputNm) throws Exception
	{
		JFileVO fileVO = new JFileVO();
		fileVO.setFileSeq("");
		fileVO.setUploadMode("db");
		fileVO.setFileThumbnailYn("N");
		String fileSeq[] = fileVO.getFileSeq().split("\\|");
		String fileId = getFileId(fileVO.getFileId(), fileSeq);
		fileVO.setFileId(fileId);
		if(multipartResolver.isMultipart(request))
		{
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			MultipartFile mFile = multiRequest.getFile(inputNm);
			if(mFile.getSize() == 0){
				return "";
			}
			System.out.println(mFile.getSize());
			boolean exceedFileExt = isExceedFileExt(mFile);

			if (exceedFileExt)
			{
				throw new IOException("BAD_FILE");
			}
			else
			{
				try
				{
					upload(mFile, fileVO);
				}
				finally
				{
					SessionUploadChecker.unCheck(request, fileVO.getFileId());
				}
			}
		}
		return fileId;
	}

	/**
	 * <pre>
	 * 파일 확장자 체크
	 * </pre>
	 * 
	 * @param mFile
	 * @return boolean
	 */
	public boolean isExceedFileExt(MultipartFile mFile)
	{
		if (mFile != null)
		{
			String fileName1 = mFile.getOriginalFilename();
			String fileExt = fileName1.substring(fileName1.lastIndexOf(".") + 1, fileName1.length());
			if (fileExt.matches("asp|aspx|ascx|bat|cfc|cfm|cgi|cmd|com|csh|dll|exe|inf|jsp|jsp|ksh|php|php3|php5|phtml|ph|reg"))
			{
				return true;
			}
		}
		return false;
	}
}
