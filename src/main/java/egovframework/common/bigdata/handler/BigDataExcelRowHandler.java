package egovframework.common.bigdata.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import egovframework.common.bigdata.BigDataExcelUtil;

@SuppressWarnings("rawtypes")
public class BigDataExcelRowHandler implements ResultHandler {

	private SXSSFSheet sheet;
	private Map<String, XSSFCellStyle> styles;
	private int rownum;
	private int celnum;

	public BigDataExcelRowHandler(SXSSFSheet sheet, Map<String, XSSFCellStyle> styles, int rownum, int celnum) {
		super();
		this.sheet = sheet;
		this.styles = styles;
		this.rownum = rownum;
		this.celnum = celnum;
	}

	@Override
	public void handleResult(ResultContext resultContext) {
		
		if(resultContext != null && resultContext.getResultObject() != null) {
			if (resultContext.getResultObject() instanceof LinkedHashMap) {
				Map excelMap = (Map) resultContext.getResultObject();
				Row row = null;
				row = sheet.createRow(rownum);
				Iterator<String> keyData = excelMap.keySet().iterator();
				int j = celnum;
				while (keyData.hasNext()) {
					String key = BigDataExcelUtil.null2str(keyData.next());
					String value = getConvertString(excelMap.get(key));
					Cell cel = row.createCell(j);
					cel.setCellStyle(styles.get("defaultSytle"));
					BigDataExcelUtil.setCellValue(cel, value, "String");
					j++;
				}
				rownum++;
			}	
		}
	}
	
	private String getConvertString(Object obj) {
		String sReturn = null;
		if( obj == null) {
			sReturn = "";
		} else if( obj instanceof BigDecimal) {
			sReturn = String.valueOf(obj);
		} else if( obj instanceof String) {
			sReturn = String.valueOf(obj);
		} else {
			sReturn = String.valueOf(obj);
		}
		return sReturn;
	}
}
