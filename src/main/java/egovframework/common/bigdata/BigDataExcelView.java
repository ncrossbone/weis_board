package egovframework.common.bigdata;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

public class BigDataExcelView extends AbstractView {

	private Log logger = LogFactory.getLog(this.getClass());
	
	/** View 명 */
	public static final String NAME = "bigDataExcelView";

	/** 컨텐트 타입을 엑셀형식으로 설정한다. */
	public BigDataExcelView(){
		this.setContentType("application/x-msexcel");
	}

	/**
	 * 대용량 엑셀 파일 다운로드
	 * @author Bang
	 * b8545@naver.com
	 * 
	 * 엑셀파일로 렌더링한다.
	 * 기본 엑셀양식을 사용
	 * 헤더 부분은 기본양식엑셀파일에 저장하여 사용한다. 
	 * templateURL  : 텟플릿 파일 위치  (필수)
	 * downFileNm   : 다운로드 파일명 (필수)
	 * list         : 목록 배열 (필수)
	 * rownum       : 엑셀 데이터 첫로우 위치 (필수)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType(this.getContentType());
		String downFileNm = BigDataExcelUtil.null2str(model.get(ExcelConstants.DOWN_FILE_NM));
		SXSSFWorkbook sw = (SXSSFWorkbook) model.get(ExcelConstants.SWB);
		if(logger.isInfoEnabled()) {logger.info("[end]bigdata excel download : " + System.currentTimeMillis());};

		response.setHeader("Content-Transfer-Encoding", "binary");
		setDisposition(downFileNm, request, response);
		sw.write(response.getOutputStream());
	}

	/**
	 * 브라우저 구분 얻기.
	 *
	 * @param request
	 * @return
	 */
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

	/**
	 * Disposition 지정하기.
	 *
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
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
}
