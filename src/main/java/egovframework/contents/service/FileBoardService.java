package egovframework.contents.service;

import java.util.List;
import java.util.Map;

public interface FileBoardService {
	
	/**
	 * 회원 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDescriptionList(Map<String, Object> param) throws Exception;

	/**
	 * 드론 사진, 항공 사진 정보 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getImgFileCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 드론 사진, 항공 사진 정보 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getImgFileList(Map<String, Object> param) throws Exception;


	/**
	 * 드론 사진, 항공 사진 정보 검색 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getImgSrcFileCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 드론 사진, 항공 사진 정보 검색 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getImgSrcFileList(Map<String, Object> param) throws Exception;

	public Map<String, Object> selectOneFile(Map<String, Object> param) throws Exception;
	
	/**
	 * 보 이미지 파일 List
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> selectZipFileList(Map<String, Object> param) throws Exception;
}
