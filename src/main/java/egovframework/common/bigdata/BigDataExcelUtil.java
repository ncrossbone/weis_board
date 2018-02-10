package egovframework.common.bigdata;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 대용량 엑셀 파일 다운로드 유틸
 * @author Bang
 * b8545@naver.com
 */
public class BigDataExcelUtil {

	public static String null2str(Object org) {
		if(org==null)
			return "";
		else
			return (String)org;
	}
	
	/**
	 * Excel Cell값 세팅
	 * @param cell
	 * @param value
	 * @param valueType
	 */
	public static void setCellValue(Cell cell, Object value, String valueType) {
		String celVal = BigDataExcelUtil.null2str(value);
		String sValType = valueType.toLowerCase();
		if (celVal == null || (celVal != null && celVal.equals(""))) {
			cell.setCellValue((RichTextString) new XSSFRichTextString(""));
		} else if ("integer".equals(sValType)) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(new Double(new String(celVal.toString())));
		} else if ("string".equals(sValType)) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue((RichTextString) new XSSFRichTextString(celVal));
		}
	}

	/**
	 * Cell단위 스타일들
	 * @param wb
	 * @return
	 */
	public static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {

		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		XSSFDataFormat fmt = wb.createDataFormat();

		// 기본 스타일
		XSSFCellStyle defaultSytle = wb.createCellStyle();
		defaultSytle.setBorderRight(BorderStyle.THIN);
		defaultSytle.setBorderLeft(BorderStyle.THIN);
		defaultSytle.setBorderTop(BorderStyle.THIN);
		defaultSytle.setBorderBottom(BorderStyle.THIN);
		defaultSytle.setAlignment(HorizontalAlignment.CENTER);
		defaultSytle.setVerticalAlignment(VerticalAlignment.CENTER);
		defaultSytle.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());        
		defaultSytle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styles.put("defaultSytle", defaultSytle);

	

		return styles;
	}
}
