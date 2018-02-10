package egovframework.cms.excel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import egovframework.cms.excel.service.ExcelService;
import egovframework.common.mapper.CommonMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ExcelService")
public class ExcelServiceImpl extends EgovAbstractServiceImpl implements ExcelService  {

	
	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;

	private String namespaceBasic = "excelDownMapper.";

	
	public SXSSFWorkbook getExcel(String templateUrl,int startRow,int startCell, Map<String, Object> param) throws Exception {
		return commonMapper.getBigDataExcelProc(String.valueOf(param.get("mId")),templateUrl,startRow,startCell,param);
	}
}


