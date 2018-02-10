package egovframework.cms.excel.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import egovframework.cms.member.MemberVo;

public interface ExcelService {
	
	public SXSSFWorkbook getExcel(String templateUrl,
			int startRow,
			int startCell,
			Map<String, Object> param) throws Exception;
}
