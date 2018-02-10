package egovframework.common.jfile.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import egovframework.common.jfile.service.impl.JFileVO;

public interface JFileService {

	public static final String DB_MODE = "db";
	
	/**
	 * 파일 아이디를 반환
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @return String 파일 아이디
	 */
	public String getFileId(String fileId, Object fileSeq);

	/**
	 * 첨부파일 정보를 저장한다.
	 * @param fileId 파일 아이디.
	 * @param fileName 파일 명.
	 * @param fileSize 파일 사이즈.
	 * @param maskingFileName 마스킹 파일명.
	 * @param expireDate 폐기 일자.
	 * @param limitCount 첨부파일 제한 갯수.
	 */
	public void addAttachFile(JFileDetails fileVo);
	
	/**
	 * 파일아이디로 첨부파일 정보를 조회한다.
	 * @param fileId 파일 아이디.
	 * @return List<Map<String, Object>> 첨부파일 목록 정보.
	 */
	public List<JFileDetails> getAttachFiles(String fileId) ;
	
	/**
	 * 파일아이디와 파일 시퀀스로 첨부 파일 정보를 조회한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeq 파일 시퀀스.
	 * @return Map<String, Object> 첨부파일 정보. 
	 */
	public JFileDetails getAttachFile(String fileId, String fileSeq);

	/**
	 * 파일 아이디로 파일 시퀀스 목록을 조회한다.
	 * @param fileId 파일아이디.
	 * @return Object[] 파일 시퀀스 목록.
	 */
	public Object[] getAttacheFileSeqs(String fileId);
	
	/**
	 * 파일 아이디와 파일 시퀀스 목록으로 첨부파일 목록이 존재하는 지 여부를 조회한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeqs 파일 시퀀스 목록.
	 * @return isExistingAttachFileInfo 파일목록 존재 여부.
	 */
	public boolean isExistingAttachFileInfo(String fileId, List<Object> arrayToList);

	/**
	 * 파일아이디로 삭제여부 컬럼을 변경한다.
	 * @param fileId 파일 아이디.
	 * @param deleteYn 삭제 여부.
	 */
	public void updateAttachFileDeleteYnByFileId(String fileId, String deleteYn);
	
	/**
	 * 파일 아이디로 파일 다운로드시 다운로드 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 */
	public void updateAttachFileDownloadCount(String fileId);
	
	/**
	 * 파일 아이디로 파일 다운로드시 다운로드 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 */
	public void updateAttachFileDownloadCount(String fileId, String fileSeq);

	/**
	 * 파일아이디로 삭제여부 컬럼을 변경한다.
	 * @param fileId 파일 아이디.
	 * @param deleteYn 삭제 여부.
	 */
	public void updateAttachFileDeleteYn(String fileId, Object[] fileSeqs, String yn);
	
	/**
	 * 파일 아이디와 파일 시퀀스로 다운로드한 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeq 파일 시퀀스.
	 */
	public void updateAttachFileDownloadCountBySequence(String fileId, String fileSeq);
	
	/**
	 * 파일아이디로 다운로드 한 건수를 변경한다.
	 * @param fileId 파일아이디.
	 */
	public void updateAttachFileDownloadCountByFileId(String fileId);
	
	/**
	 * 파일 아이디와 파일 시퀀스로 첨부파일 정보를 삭제한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeqs 파일 시퀀스 목록.
	 */
	public void removeAttachFile(String fileId, List<Object> arrayToList);

	/**
	 * 업로드를 수행한다.
	 * @param values 멀티 파일 
	 * @param fileVo 파일 정보를 담고 객체
	 */
	public void upload(Collection<MultipartFile> values, JFileVO fileVo);

	/**
	 * 업로드 완료 후 처리 작업을 수행한다.
	 * @param fileId 파일 아이디
	 */
	public void executeAfterUploadCompleted(String fileId);

	/**
	 * 파일 아이디와 파일 시퀀스 암*호*화 사용여부를 검색 조건으로 파일을 찾는다.
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @param useSecurity 암*호*화 모드
	 * @return File 파일 객체
	 */
	public JFile getFile(String fileId, String fileSeq, String useSecurity);

	/**
	 * 파일 아이디와 암*호*화 여부를 입력 받아 파일들을 찾는다.
	 * @param fileId 파일 아이디
	 * @param useSecurity 암*호*화 사용여부
	 * @return JFile[] 파일 객체
	 */
	public JFile[] getFiles(String fileId, String useSecurity);

	/**
	 * 파일아이디와 파일 시퀀스 암*호*화 여부를 검색 조건으로 파일을 찾는다.
	 * @param fileId 파일아이디
	 * @param fileSeq 파일 시퀀스
	 * @param useSecurity 암*호*화 여부
	 * @return File 파일 객체
	 */
	public JFile getFileBySequence(String fileId, String fileSeq, String useSecurity);
	public JFile getFileBySequence(String fileId, String fileSeq, String useSecurity, String fileThumbnailYn);
	/**
	 * JFile 파일 업르도
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String jfileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * JFile 파일 업르도
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String jfileUpload(HttpServletRequest request, HttpServletResponse response, String inputNm) throws Exception;
	
}
