package egovframework.contents.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.common.mapper.CommonMapper;
import egovframework.contents.service.FileBoardService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("FileBoardService")
public class FileBoardServiceImpl extends EgovAbstractServiceImpl implements FileBoardService  {

	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "fileBoardMapper.";
	
	/**
	 * top menu정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getDescriptionList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"descriptionList");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}

	/**
	 * 드론 사진, 항공 사진 정보 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getImgFileCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"imgFileCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 드론 사진, 항공 사진 정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getImgFileList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"imgFileList");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}

	/**
	 * 드론 사진, 항공 사진 정보 카운팅
	 * @param Map
	 * @exception Exception Exception
	 */
	public int getImgSrcFileCount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"imgSrcFileCount");
		int reslutCnt = commonMapper.getCount(param);
		return reslutCnt;
	}
	
	/**
	 * 드론 사진, 항공 사진 정보검색  리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> getImgSrcFileList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"imgSrcFileList");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}
	
	public Map<String, Object> selectOneFile(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getFileItem");
		Map<String,Object> fileItem = (Map<String,Object>) commonMapper.get(param);

		return fileItem;
	}

	/**
	 * 보 이미지 파일 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	public List<Map<String, Object>> selectZipFileList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"zipFileList");
		List<Map<String,Object>> gridList = (List<Map<String,Object>>) commonMapper.getList(param);
		
		return gridList;
	}
}


