package egovframework.common.jfile.service.template;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.JProperties;
import egovframework.common.jfile.exception.JFileException;
import egovframework.common.jfile.security.service.CipherService;
import egovframework.common.jfile.service.FileUploadCompletedEventListener;
import egovframework.common.jfile.service.JFile;
import egovframework.common.jfile.service.JFileDetails;
import egovframework.common.jfile.service.strategy.JFileUploadTypeFactory;
import egovframework.common.jfile.utils.KeyHelper;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *
 * </pre>
 */
public abstract class JFileUploadModeTemplate {
	
	/** 로거 */
	private Logger logger  = LoggerFactory.getLogger(this.getClass());

	/** 프로퍼티 파일에 정의된 uploadpath 키 */
	private String uploadPathKey;

	/** 암*호*화 서비스 객체 */
	private CipherService cipherService;
	
	/** DB모드 */
	protected static final String DB_MODE = "db";
	
	/** 파일업로드 이벤트 리스너 */
	private Collection<FileUploadCompletedEventListener> fileUploadCompletedEventListeners;

	/**
	 * 파일아이디가 없다면 새로 생성하여 주고 있으면 있던 값을 반환한다.
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @return String 파일 아이디
	 */
	public String getFileId(String fileId) {
		return null == fileId || "".equals(fileId) ? KeyHelper.getStringKey() : fileId;
	}
	
	/**
	 * 마스킹 된 파일명, 파일 모드, 파일 경로를 전달 받아 파일의 전체경로를 반환한다.
	 * @param maskingName 마스킹 된 파일명.
	 * @param uploadMode 업로드 모드.
	 * @param uploadPath 업로드 패스.
	 * @return String 서버에 저장된 전체파일경로.
	 */
	public String getUploadFilePullPath(String maskingName, String uploadMode, String uploadPath) {
		return getUploadDirectoryPath(uploadPath, uploadMode).concat(maskingName);
	}
	
	/**
	 * 파일명이 포함된 업로드 경로, 업로드 모드를 전달받아 디렉터리 경로를반환한다.
	 * @param uploadPath 업로드 경로
	 * @param uploadMode 업로드모드.
	 * @return String 디렉터리 경로.
	 */
	public String getUploadDirectoryPath(String uploadPath, String uploadMode) {
		StringBuilder uploadPathSb = new StringBuilder();
		uploadPathSb.append(getFileUploadDirectoryPath(uploadMode, uploadPath));
		mkdir(uploadPathSb.toString());
		return uploadPathSb.toString();
	}

	public abstract Object getFileUploadDirectoryPath(String uploadMode, String uploadPath);

	public abstract String getFileMask(String originalFilename, int i, String uploadMode, String string);

	public abstract String getFileDownloadDirectoryPath(String maskingFileName, String uploadMode, String systemUploadPath); 
	
	public abstract String getFileDownloadPullPath(String fileMask, String dbMode,	String string);

	public JFile getJFile(JFileDetails fileVo, String useSecurity, String uploadPath) {
		
		return getJFile(fileVo, useSecurity, uploadPath, "N");
	}
	
	public JFile getJFile(JFileDetails fileVo, String useSecurity, String uploadPath, String fileThumbnailYn) {
		if(fileVo == null)
			throw new JFileException(" 첨부파일 이력 정보가 존재하지 않습니다. ");
		
		String fileMaskNm = "";
		if(fileThumbnailYn != null && fileThumbnailYn.equals("Y")){
			fileMaskNm = fileVo.getFileThumbnailMask();
		}else {
			fileMaskNm = fileVo.getFileMask();
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Server Repository Path : "+getFileDownloadPullPath(fileMaskNm, fileVo.getUploadMode(), uploadPath));
		}
		
		JFile jfile = new JFile(getFileDownloadPullPath(fileMaskNm, fileVo.getUploadMode(), uploadPath));			
		jfile.setOriginalFileName(fileVo.getFileName());
//		jfile.setUseSecurity(fileVo.getUseSecurity());
		jfile.setUseSecurity(useSecurity);
		return jfile;
	}
	
	public JFile[] getFiles(List<JFileDetails> files, String uploadPath, String useSecurity, String uploadMode) {
		if(files == null)
			return null;
		
		JFile[] jfiles = new JFile[files.size()];
		int idx = 0;
		
		for(JFileDetails file : files) {
			if(file == null)
				throw new JFileException(" FileUpload 이력 정보가 존재하지 않습니다. ");
			

			if(logger.isDebugEnabled()) {
				logger.debug("Server Repository Path : "+getFileDownloadPullPath(file.getFileMask(), uploadMode, uploadPath == null ? JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY) : uploadPath));
			}

			JFile jfile = new JFile(getFileDownloadPullPath(file.getFileMask(), uploadMode, uploadPath == null ? 
					JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY) : 
					uploadPath));
			jfile.setOriginalFileName(file.getFileName());
			jfile.setUseSecurity(useSecurity);
			jfiles[idx] = jfile;
			idx++;
		}
		return jfiles;
	}
	
	public void deleteJFiles(JFileDetails details, String uploadPath, String uploadMode) {
		if(details == null)
			return;
		
		/** 파일을 삭제한다. */
		deleteFileByFullPath(getFileUploadDirectoryPath(uploadMode, uploadPath)+details.getFileMask());
	}
	
	/**
	 * 파일업로드 이벤트 리스너를 반환한다.
	 * @return FileUploadEventListener 파일업로드 이벤트 리스너.
	 */
	public Collection<FileUploadCompletedEventListener> getFileUploadCompletedEventListener() {
		return fileUploadCompletedEventListeners;
	}

	/**
	 * 파일업로드 이벤트 리스너를 세팅한다.
	 * @param fileUploadEventListener 파일업로드 이벤트 리스너.
	 */
	public void setFileUploadCompletedEventListener(
			FileUploadCompletedEventListener fileUploadListener) {
		if( fileUploadCompletedEventListeners==null ) {
			fileUploadCompletedEventListeners = new ArrayList<FileUploadCompletedEventListener>();
		}
		fileUploadCompletedEventListeners.add(fileUploadListener);
	}
	
	public boolean isExceedFileExt(String 	fileName) {
		
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		if(fileExt.matches("asp|aspx|ascx|bat|cfc|cfm|cgi|cmd|com|csh|dll|exe|inf|jsp|jsp|ksh|php|php3|php5|phtml|ph|reg|sh")){
			return true;
		}
		return false;
	}

	public void upload(MultipartFile multipartFile, JFileDetails fileVo) {
		
String orgFileNm = multipartFile.getOriginalFilename();
		
		if(isExceedFileExt(orgFileNm)) throw new JFileException("확장자 오류");
		
		fileVo.setFileMask(getFileMask(orgFileNm, 0, fileVo.getUploadMode(), JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY)));
		fileVo.setFileName(orgFileNm);
		fileVo.setFileSize(multipartFile.getSize());
		fileVo.setFileType(multipartFile.getContentType());

		String uploadDirectoryPath = getUploadDirectoryPath(JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), fileVo.getUploadMode());
		String uploadPullPath = getUploadFilePullPath(
				  fileVo.getFileMask()
				, fileVo.getUploadMode()
				, (getUploadPathKey() != null && !"".equals(getUploadPathKey())) ? JProperties.getString(getUploadPathKey()) : JProperties.getString(GlovalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY)
		);
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(uploadPullPath);
			JFileUploadTypeFactory.INSTANCE.getUploadType(fileVo.getUseSecurity()).getHandler().handle(multipartFile.getInputStream(), fos);
			if (getFileUploadCompletedEventListener() != null)
			{
				for (FileUploadCompletedEventListener listener : getFileUploadCompletedEventListener())
				{
					listener.uploadCompleted(fileVo.getFileId(), uploadDirectoryPath, fileVo.getFileMask(), orgFileNm);
				}
			}

			//썸네일 이미지 생성
			if (fileVo.getFileThumbnailYn().equals("Y") && fileVo.isImage())
			{
				BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
				//일단 고정으로 쓰고 필요에 따라 파라미터로 빼던지 하자
				int width = 320;
				int height = 200;
				//이미지 사이즈 조정
				if(bi != null){//웹 취약점 조치
					if ((float) width / bi.getWidth() > (float) height / bi.getHeight())
					{
						width = (int) (bi.getWidth() * ((float) height / bi.getHeight()));
					}
					else
					{
						height = (int) (bi.getHeight() * ((float) width / bi.getWidth()));
					}
					scale(bi, uploadPullPath + "_thumbnail", "jpg", width, height);
				}
				
				fileVo.setFileThumbnailMask(fileVo.getFileMask() + "_thumbnail");
			}
			else
			{
				fileVo.setFileThumbnailYn("N");
			}
		}
		catch (IOException e)
		{
			throw new JFileException(e);
		}
		finally
		{
			try
			{
				if (fos != null)
				{
					fos.close();
				}
			}
			catch (IOException e)
			{
				logger.error("IO Exception", e);
			}
//			EgovResourceCloseHelper.close(fos);
		}
		if (logger.isDebugEnabled())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("\n================Upload Completed====================\n");
			sb.append("UPLOAD PATH : " + uploadDirectoryPath + "\n");
			sb.append("UPLOAD File : " + orgFileNm + "\n");
			logger.debug(sb.toString());
		}
	}
	
	/**
	 * 참조 
	 * http://code.google.com/p/java-image-scaling/
	 * http://code.google.com/p/hudson-assembler/downloads/detail?name=Filters.jar&can=2&q=
	 * java-image-scaling-0.8.6.jar, Filters.jar 사용
	 */
	public  void scale(BufferedImage srcImage, String destPath, String imaesFormat, int destWidth, int dsetHight){
		try{
			ResampleOp resampleOp = new ResampleOp(destWidth,dsetHight);
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
			BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
			File destFile = new File(destPath);
			ImageIO.write(rescaledImage, imaesFormat, destFile);
		}catch(IOException e){
			logger.debug("에러발생{}",e);
		}
		
	}
	
	/**
	 * 프로퍼티 파일에 정의된 uploadpath 키를 반환
	 * @return String 프로퍼티 파일에 정의된 uploadpath 키
	 */
	public String getUploadPathKey() {
		return uploadPathKey;
	}

	/**
	 * 프로퍼티 파일에 정의된 uploadpath 키를 세팅
	 * @param uploadPathKey 프로퍼티 파일에 정의된 uploadpath 키
	 */
	public void setUploadPathKey(String uploadPathKey) {
		this.uploadPathKey = uploadPathKey;
	}

	/**
	 * 입력받은 디렉터리가 존재 하지 않았다면 경로를 생성 한후 파일을 생성한다.
	 * @param file 파일 객체.
	 */
	protected void mkdir(File file) {
		if (!file.exists())
			file.mkdirs();
	}
	
	/**
	 * 입력받은 디렉터리가 존재 하지 않았다면 경로를 생성 한후 파일을 생성한다.
	 * @param fullpath 파일 경로.
	 */
	protected void mkdir(String fullpath) {
		File file = new File(fullpath);
		if (!file.exists())
			file.mkdirs();
	}
	
	/**
	 * 파일경로에 파일이 존재하면 삭제한다.
	 * @param fullPath 파일경로.
	 */
	public synchronized void deleteFileByFullPath(String fullPath) {
		/*File f = null;
		f = new File(fullPath);
		if (f.exists()) f.delete();*/
	}

	/**
	 * 암*호*화 서비스 객체를 반환한다.
	 * @return CipherService 암*호*화 서비스 객체.
	 */
	public CipherService getCipherService() {
		return cipherService;
	}

	/**
	 * 암*호*화 서비스 객체를 반환한다.
	 * @param cipherService 암*호*화 서비스 객체.
	 */
	public void setCipherService(CipherService cipherService) {
		this.cipherService = cipherService;
	}
}
