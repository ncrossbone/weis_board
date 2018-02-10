package egovframework.common.jfile.service.impl;

import java.util.Date;

import egovframework.common.jfile.service.JFileDetails;

public class JFileVO implements JFileDetails {
	
	private String fileId = null; 
	 
	private String fileSeq = null; 
 
	private String fileName = null; 
 
	private long fileSize = -1; 

	private String fileMask = null;
	
	private String fileThumbnailMask = null;
	
	private String fileThumbnailYn = null; 
 
	private String downloadCount = null; 
 
	private Date downloadExpireDate = null; 
 
	private String downloadLimitCount = null; 
 
	private Date regDate = null; 
	
	private String useSecurity = null;
	
	private String uploadMode = "db";		
	
	private String fileType = null;
 
	/**
	 * @return fileId 파일 아이디
	 */
	public String getFileId() { 
		return fileId; 
	}	 
	/**
	 * @return fileSeq 파일 순번
	 */
	public String getFileSeq() { 
		return fileSeq; 
	}	 
	/**
	 * @return fileName 파일명
	 */
	public String getFileName() { 
		return fileName; 
	}	 
	/**
	 * @return fileThumbnail 썸네일 존재 여부
	 */
	public String getFileThumbnailYn() {
		return fileThumbnailYn;
	}
	/**
	 * @return fileThumbnailMask 썸네일 파일 마스크
	 */
	public String getFileThumbnailMask() {
		return fileThumbnailMask;
	}
	/**
	 * @return fileSize 파일 사이즈
	 */
	public long getFileSize() { 
		return fileSize; 
	}	 
	/**
	 * @return fileMask 파일 마스크
	 */
	public String getFileMask() { 
		return fileMask; 
	}	 
 
	/**
	 * @return downloadCount 다운로드 카운트
	 */
	public String getDownloadCount() { 
		return downloadCount; 
	}	 
	
	/**
	 * @return downloadExpireDate 다운로드 만료일
	 */
	public Date getDownloadExpireDate() { 
		return downloadExpireDate; 
	}	 
	
	/**
	 * @return downloadLimitCount 다운로드 제한 횟수
	 */
	public String getDownloadLimitCount() { 
		return downloadLimitCount; 
	}	 
	
	/**
	 * @return regDate 등록일자
	 */
	public Date getRegDate() { 
		return regDate; 
	}	 

	/**
	 * @param fileId 파일 아이디
	 */
	public void setFileId( String fileId) { 
		this.fileId = fileId; 
	} 
	/**
	 * @param fileSeq 파일 순번
	 */
	public void setFileSeq( String fileSeq) { 
		this.fileSeq = fileSeq; 
	} 
	/**
	 * @param fileSeq 파일명
	 */

	public void setFileName( String fileName) { 
		this.fileName = fileName; 
	} 

	/**
	 * @param fileSize 파일 사이즈
	 */
	public void setFileSize( long fileSize) { 
		this.fileSize = fileSize; 
	} 
	/**
	 * @param fileMask 파일 마스킹
	 */

	public void setFileMask( String fileMask) { 
		this.fileMask = fileMask; 
	}
	/**
	 * @param fileThumbnailMask 썸네일 파일 마스킹
	 */
	
	public void setFileThumbnailMask( String fileThumbnailMask) { 
		this.fileThumbnailMask = fileThumbnailMask; 
	}
	/**
	 * @param fileThumbnailMask 썸네일 파일 존재 여부
	 */
	
	public void setFileThumbnailYn(String fileThumbnailYn) {
		this.fileThumbnailYn = fileThumbnailYn;
	}
	
	/**
	 * @param downloadCount 다운로드 횟수
	 */

	public void setDownloadCount( String downloadCount) { 
		this.downloadCount = downloadCount; 
	} 
	
	/**
	 * @param downloadExpireDate 다운로드 만료일
	 */
	public void setDownloadExpireDate( Date downloadExpireDate) { 
		this.downloadExpireDate = downloadExpireDate; 
	} 

	/**
	 * @param downloadLimitCount 다운로드 제한 횟수
	 */
	public void setDownloadLimitCount( String downloadLimitCount) { 
		this.downloadLimitCount = downloadLimitCount; 
	} 

	/**
	 * @param regDate 등록일자
	 */
	public void setRegDate( Date regDate) { 
		this.regDate = regDate; 
	}
	public String getUseSecurity() {
		return useSecurity;
	}
	public void setUseSecurity(String useSecurity) {
		this.useSecurity = useSecurity;
	}
	public String getUploadMode() {
		return uploadMode;
	}
	public void setUploadMode(String uploadMode) {
		this.uploadMode = uploadMode;
	} 
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public boolean isImage() {
		return 	("bmp". equals(getExtension()) ||
			     "gif". equals(getExtension()) ||
		         "jpg". equals(getExtension()) ||
		         "jpeg".equals(getExtension()) ||
		         "png". equals(getExtension())
		) ;
	}

	public String getExtension() {
		if(fileName == null)
			return null;
		return  fileName.lastIndexOf(".") > -1 ? fileName.substring(fileName.lastIndexOf(".")+1) : null; 
	}
	@Override
	public String toString() {
		return "JFileVO [fileId=" + fileId + ", fileSeq=" + fileSeq
				+ ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileMask=" + fileMask + ", fileThumbnailMask="
				+ fileThumbnailMask + ", fileThumbnailYn=" + fileThumbnailYn
				+ ", downloadCount=" + downloadCount + ", downloadExpireDate="
				+ downloadExpireDate + ", downloadLimitCount="
				+ downloadLimitCount + ", regDate=" + regDate
				+ ", useSecurity=" + useSecurity + ", uploadMode=" + uploadMode
				+ ", fileType=" + fileType + "]";
	}

	
	
}
