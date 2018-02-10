package egovframework.common.utilDo;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import egovframework.common.bigdata.BigDataExcelUtil;
 
@SuppressWarnings("unused")
public class ExcelViewUtil extends AbstractExcelPOIView {
 
	@SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType(this.getContentType());
		String downFileNm = BigDataExcelUtil.null2str("ExcelDown.xls");
		SXSSFWorkbook sw = (SXSSFWorkbook) model.get("swb");
		
		if(logger.isInfoEnabled()) {logger.info("[end]bigdata excel download : " + System.currentTimeMillis());};

		response.setHeader("Content-Transfer-Encoding", "binary");
		setDisposition(downFileNm, request, response);
		try{
			sw.write(response.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
//    @SuppressWarnings({ "unchecked", "deprecation" })
//    @Override
    protected void buildExcelDocument1(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String target = "";
		String downType = "";
		String siteTypeItem = "";
    	
		if(model.containsKey("target")){
    		target = model.get("target").toString();
    	}
		if(model.containsKey("downType")){
			downType = model.get("downType").toString();
    	}
		if(model.containsKey("siteTypeItem")){
			siteTypeItem = model.get("siteTypeItem").toString();
		}
 
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(target);
        Row row = null;
        Cell cell = null;
        
        //Sheet 생성
        int rowCount = 0;
        int cellCount = 0;
        CellStyle style = wb.createCellStyle();
        
        //가는 경계선
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        //배경색지정
        style.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        //정렬
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        try{
        	//Object로 넘어온 값을 각 Model에 맞게 형변환 해준다.
        	List<Map<String,Object>> excelList = (List<Map<String,Object>>) model.get("excelList");
        	
        	//autuSizeColumn after setColumnWidth setting!!  
            for (int i=0; i<excelList.size(); i++)
            { 
              sheet.autoSizeColumn(i);
              sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+1024 );
            }
        
	        //target에 따라서 엑셀 문서 작성을 분기한다.
	        if(target.equals("040201")){
	        	// 제목 Cell 병합
	        	sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
	        	sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
	        	sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 6));
	        	sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 12));
	        	sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 17));
	        	sheet.addMergedRegion(new CellRangeAddress(0, 1, 18, 18));
	        	
	        	// 첫번째 행 제목 Cell 생성
	        	row = sheet.createRow(rowCount++);
	        	for(int i=0 ; i<=18 ; i++){
	        		cell = row.createCell(i);
		            cell.setCellStyle(style);
		            if(i == 0){
		            	cell.setCellValue("보 명");
		            }else if(i == 1){
		            	cell.setCellValue("지점 구분");
		            }else if(i == 2){
		            	cell.setCellValue("채취시기/회차");
		            }else if(i == 7){
		            	cell.setCellValue("현장측정값");
		            }else if(i == 13){
		            	cell.setCellValue("퇴적물용출률 측정값");
		            }else if(i == 18){
		            	cell.setCellValue("특이사항");
		            }
	        	}
	        	
	        	// 두번째 행 제목 Cell 생성
	        	row = sheet.createRow(rowCount++);
	        	cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	        	cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("년");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("회차");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("월");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("일");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("시간");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("표층_측정 수심(m)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("표층_수온(℃)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("표층_DO(㎎/L)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("저층_측정 수심(m)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("저층_수온(℃)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("저층_DO(㎎/L)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("현장_SOD(g/m2/d)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("폭기_SOD(g/m2/d)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("BNF_NH4-N(g/m2/d)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("BNF_NOx-N(g/m2/d)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            cell.setCellValue("BNF_PO4-P(g/m2/d)");
	            cell = row.createCell(cellCount++);
	            cell.setCellStyle(style);
	            /*
	            // 데이터 Cell 생성
	            for (Map<String,Object> excelItem : excelList) {
	                row = sheet.createRow(rowCount++);
	                cellCount = 0;
	                String useTxt = "";
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("OBSNM"))); 
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("SPOT_SE")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("EXAMIN_YEAR")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("EXAMIN_TME")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("EXAMIN_MT")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("EXAMIN_DE")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("EXAMIN_TIME")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("SURLYR_DPWT")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("SURLYR_WTRTP")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("SURLYR_DOXY")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BOTLYR_DPWT")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BOTLYR_WTRTP")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BOTLYR_DOXY")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("SPT_SOD")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("AERT_SOD")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BNF_NH4N")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BNF_NOXN_CM")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("BNF_PO4P")));
	                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("PARTCLR_MATTER")));
	            }*/
	        }else if(target.equals("040101")){
	        	if(siteTypeItem.equals("R")){
	        		// 제목 Cell 생성
		        	row = sheet.createRow(rowCount++);
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("지점명");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("채취일자");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("반기");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("채취시간");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("오염 단계");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("최고수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-측정 수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-수온(℃)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-DO(㎎/L)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층 pH");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층 전기전도도(25℃ μS/㎝)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층-측정 수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 수온(℃)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 DO(㎎/L)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 pH");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 전기전도도(25℃ μS/㎝)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-모래(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-실트(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-점토(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("함수율(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("완전연소가능량(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("완전연소가능량 등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("COD(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("TOC(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-N(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-N등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-P(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-P등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("SRP(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pb(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pb등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Zn(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Zn등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cu(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cu등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cr(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cr등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Ni(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Ni등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("As(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("As등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cd(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cd등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Hg(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Hg등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Al(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Li(㎎/㎏)");
		        	/*
		        	// 데이터 Cell 생성
		            for (Map<String,Object> excelItem : excelList) {
		                row = sheet.createRow(rowCount++);
		                cellCount = 0;
		                String useTxt = "";
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("PT_NM"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("YMD"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("WMWK"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("WMCTM"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("POLL_STEP"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW_SURF"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TEMP_SURF"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DO_SURF"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PH_SURF")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_EC_SURF"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW_LOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TEMP_LOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DO_LOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PH_LOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_EC_LOW"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FSD"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FST"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FCL"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_WTC")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PCA"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PCA_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_COD"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOC"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TN"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TN_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TP"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TP_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_SRP"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PB")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PB_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ZN"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ZN_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CU"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CU_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CR"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CR_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_NI"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_NI_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AS_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CD"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CD_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_HG"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_HG_CLASS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AL"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_LI")));
		            }*/
	        	}else if(siteTypeItem.equals("L")){
	        		// 제목 Cell 생성
		        	row = sheet.createRow(rowCount++);
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("지점명");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("채취일자");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("반기");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("채취시간");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("오염 단계");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("최고수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-측정 수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-수온(℃)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층-DO(㎎/L)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층 pH");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("표층 전기전도도(25℃ μS/㎝)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층-측정 수심(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 수온(℃)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 DO(㎎/L)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 pH");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("저층 전기전도도(25℃ μS/㎝)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("투명도(m)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-모래(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-실트(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("입도-점토(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("함수율(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("완전연소가능량(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("완전연소가능량 등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("COD(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("TOC(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-N(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-N등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-P(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("T-P등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("SRP(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pb(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pb등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Zn(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Zn등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cu(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cu등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cr(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cr등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Ni(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Ni등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("As(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("As등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cd(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Cd등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Hg(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Hg등급");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Al(%)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Li(㎎/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Chlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Dichlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Trichlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Tetrachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pentachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Hexachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Heptachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Octachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Nonachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Decachlorobiphenyl(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Total PCBs(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Naphthalene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Acenaphthylene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Acenaphthene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Fluorene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Phenanthrene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Anthracene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Fluoranthene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Pyrene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzo[a]anthracene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Chrysene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzo[b]fluoranthene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzo[k]fluoranthene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzo[a]pyrene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Indeno[1,2,3-cd]pyrene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Dibenzo[a,h]anthracene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzo[g,h,i]perylene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Total PAHs(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("o,p'-DDE(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("p,p'-DDE(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("o,p'-DDD(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("p,p'-DDD(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("o,p'-DDT(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("p,p'-DDT(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Total DDTs(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("1,1,1-Trichloroethane(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("1,2-Dichloroethane(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Benzene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Carbon tetrachloride(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Chloroform(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Ethylbenzene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Methyl Chloride(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Tetrachloroethylene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Trichloroethylene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("Toluene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("m,p-Xylene(㎍/㎏)");
		        	cell = row.createCell(cellCount++);
		        	cell.setCellStyle(style);
		        	cell.setCellValue("o-Xylene(㎍/㎏)");
		        	/*
		        	// 데이터 Cell 생성
		            for (Map<String,Object> excelItem : excelList) {
		                row = sheet.createRow(rowCount++);
		                cellCount = 0;
		                String useTxt = "";
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("PT_NM"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("YMD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("WMWK")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("WMCTM")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("POLL_STEP")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW_SURF")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TEMP_SURF")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DO_SURF")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PH_SURF")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_EC_SURF"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DOW_LOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TEMP_LOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DO_LOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PH_LOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_EC_LOW")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TRANSPARENCY")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FSD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FST")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FCL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_WTC"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PCA")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PCA_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_COD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOC")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TN")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TN_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TP")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TP_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_SRP")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PB"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PB_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ZN")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ZN_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CU")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CU_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CR")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CR_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_NI")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_NI_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AS"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AS_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CD_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_HG")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_HG_CLASS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_AL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_LI")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_2_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_3_CL_2_PHENYL"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_4_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_5_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_6_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_7_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_8_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_9_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_10_CL_2_PHENYL")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOT_PCBS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_NAPTHALENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ACENAPTHALENE"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ACENAPTHENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FLUORENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PHENANTHRENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ANTHRACENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_FLUORANTHENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_PYRENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZO_A_ANTHRACENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CRYSENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZO_B_FLUORANTHENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZO_F_FLUORANTHENE"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZO_A_PYRENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_INDENO_1_2_3_CD_PYRENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_DIBENZO_A_H_ANTHRACENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZO_G_H_I_PERYLENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOTAL_PAHS")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_O_P_DDE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_P_P_DDE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_O_P_DDD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_P_P_DDD")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_O_P_DDT"))); 
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_P_P_DDT")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOTAL_DDT")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_1_1_1_TRICHLOROETHANE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_1_2_DICHLOROETHANE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_BENZENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CARBON_TETRA_CHLORIDE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_CHLOROFORM")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_ETHYL_BENZENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_METHYL_CHLORIDE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TETRA_CHLORO_ETHYLENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TRI_CHLORO_ETHYLENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_TOLUENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_M_P_XYLENE")));
		                row.createCell(cellCount++).setCellValue(notNull(excelItem.get("ITEM_O_XYLENE")));
		            }*/
	        	}
	        }else if(target.equals("050301")){
	        	// 제목 Cell 생성
	        	row = sheet.createRow(rowCount++);
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("보 코드");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("보 지점");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("관측일시(분)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("보 상류수위(m)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("보 하류수위(m)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("저수량(백만m^3)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("공용량(백만m^4)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("유입량(백만m^5)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("총 방류량(㎥/sec)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("발전 방류량(㎥/sec)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("가동보 방류량(㎥/sec)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("고정보 방류량(㎥/sec)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("어도 방류량(㎥/sec)");
	        	cell = row.createCell(cellCount++);
	        	cell.setCellStyle(style);
	        	cell.setCellValue("기타 방류량(㎥/sec)");
	        	
	        }
	        
	        
	        
	        
	        
	     // 데이터 Cell 생성
            for (Map<String,Object> excelItem : excelList) {
            	Iterator iterator = excelItem.entrySet().iterator();
            	row = sheet.createRow(rowCount++);
            	cellCount = 0;
            	String useTxt = "";

            	while (iterator.hasNext()) {
            	Entry entry = (Entry)iterator.next();

            	row.createCell(cellCount++).setCellValue(notNull(excelItem.get(entry.getKey()))); 
            	}
            }
	        
	        
	    }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename="+target+"."+downType);
            // 엑셀 출력
            wb.write(response.getOutputStream());
            wb.close();
        }	
    }
 
    protected String notNull(Object str){
    	String rtnVal = "";
    	if(str == null || str.equals("")){
    		rtnVal = "";
    	}else{
    		rtnVal = str.toString();
    	}
    	return rtnVal;
    }
    
    @Override
    protected Workbook createWorkbook() {
        return new XSSFWorkbook();
    }
    
    
    
    
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new IOException("Not supported browser");
		}
	    response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
	    
	    if ("Opera".equals(browser)){
	        response.setContentType("application/octet-stream;charset=UTF-8");
	    }
	}
    
    private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Mozilla") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "MSIE";
	}
    
    
    
}