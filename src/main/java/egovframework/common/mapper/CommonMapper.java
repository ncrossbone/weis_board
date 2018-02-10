package egovframework.common.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import egovframework.cms.attach.model.AttachVo;
import egovframework.common.bigdata.BigDataExcelUtil;
import egovframework.common.bigdata.handler.BigDataExcelRowHandler;
import egovframework.common.helper.EgovBasicLogger;
import egovframework.common.util.StringUtil;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;


/**
 * 공용 Mapper 클래스
 * @author Administrator
 *
 */
@Repository("CommonMapper")
public class CommonMapper extends EgovAbstractMapper {
	/**
	 *  리스트 조회
	 * @param Map
	 * @exception Exception Exception
	 */
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> getList(Map<String, Object> param) throws Exception {
      //엑셀 다운로드시 사용
      if(StringUtil.noNull(param.get("EXCEL_YN")).equals("Y")){
        param.put( "endRow", 99999999 );
      }
      return selectList(mapperId(param), param);
    }
    /**
	 * 카운트
	 * @param Map
	 * @exception Exception Exception
	 */
    public int getCount(Map<String,Object> param) throws Exception {
    	return selectOne(mapperId(param), param);
    }

    /**
	 *  상세 조회
	 * @param Map
	 * @exception Exception Exception
	 */
    public Map<String, Object> get(Map<String,Object> param) throws Exception {
    	return selectOne(mapperId(param), param); 
    }
    /**
     *  상세 조회
     * @param Map
     * @exception Exception Exception
     */
    public Object get2(Map<String,Object> param) throws Exception {
    	return selectOne(mapperId(param), param); 
    }
    /**
	 *  등록
	 * @param Map
	 * @exception Exception Exception
	 */
    public int insert(Map<String,Object> param) throws Exception {
    	return insert(mapperId(param), param);
    }
    /**
	 *  수정
	 * @param Map
	 * @exception Exception Exception
	 */
    public int update(Map<String,Object> param) throws Exception {
    	return update(mapperId(param), param);
    }
    /**
	 *  삭제
	 * @param Map
	 * @exception Exception Exception
	 */
    public int delete(Map<String,Object> param) throws Exception {
      return update(mapperId(param), param);
    }
    
    /**
     * mapperId 등록
     * @param param
     * @return
     */
    public String mapperId(Map<String,Object> param) {
    	String mId = StringUtil.noNull(param.get("mId"));
    	EgovBasicLogger.info("QueryID : "+mId);
    	return mId;
    }
    
    /**
	 *  등록
	 * @param Map
	 * @exception Exception Exception
	 */
    public int insert(Map<String,Object> param, AttachVo attachVo) throws Exception {
    	return insert(mapperId(param), attachVo);
    }
    
    /**
	 *  삭제
	 * @param Map
	 * @exception Exception Exception
	 */
    public int delete(Map<String,Object> param, AttachVo attachVo) throws Exception {
      return update(mapperId(param), attachVo);
    }
    
    public SXSSFWorkbook getBigDataExcelProc(String queryId,String templateURL,int beginRownum,int beginCelnum,Map paramMap) 
    		throws FileNotFoundException, IOException, DataAccessException {
    	XSSFWorkbook xb = null;
    	Map<String, XSSFCellStyle> styles = null;
    	SXSSFWorkbook wb = null;
    	try{
    	
	    xb = new XSSFWorkbook(new FileInputStream(new File(templateURL)));//엑셀 2007이상인경우 읽기,쓰기 가능
	    styles = BigDataExcelUtil.createStyles(xb);//스타일들 저장
	    
	    wb = new SXSSFWorkbook(xb);//대용량 엑셀파일용 쓰기만 가능
	    SXSSFSheet sheet = (SXSSFSheet) wb.getSheetAt(0);
	    BigDataExcelRowHandler rowHandler = new BigDataExcelRowHandler(sheet, styles, beginRownum, beginCelnum);
	    getSqlSession().select(queryId, paramMap, rowHandler);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	    
	    return wb;
	}
}
