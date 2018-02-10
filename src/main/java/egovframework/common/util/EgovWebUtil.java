package egovframework.common.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    	--------    ---------------------------
 *   2011.10.10  한성곤          최초 생성
 *
 * </pre>
 */

public class EgovWebUtil {
	public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		returnValue = returnValue.replaceAll(".", "&#46;");
		returnValue = returnValue.replaceAll("%2E", "&#46;");
		returnValue = returnValue.replaceAll("%2F", "&#47;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) {
		return value;
	}

	 public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

		return ipPattern.matcher(str).matches();
    }

	 public static String removeCRLF(String parameter) {
		 return parameter.replaceAll("\r", "").replaceAll("\n", "");
	 }

	 public static String removeSQLInjectionRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	 }

	 public static String removeOSCmdRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
	 }
	 
	 /**
	 * 뷰에서 직접 첨부파일 등의 아이콘을 얻기 위해 사용
	 * @param fileNm 파일명
	 * @return <img src="/images/common/file/___.gif"/>
	 */
	public static String getFileIcon( Object fileNm ) {
		
		String fileIcoS = "<span><img src=\"/web/_images/file/";
		String fileIcoE = ".gif\"  alt=\"" + StringUtil.noNull( fileNm ) + "\"/></span>";
		String fileIco = "default";
		
		String fileExt = StringUtil.noNull( fileNm );
		fileExt = fileExt.substring( fileExt.lastIndexOf( "." ) + 1 ).toLowerCase();
		
		if( fileExt.equals( "hwp" ) ) {
			fileIco = "hwp";
		}
		else if( fileExt.equals( "pdf" ) ) {
			fileIco = "pdf";	
		}
		else if( fileExt.equals( "xls" ) || fileExt.equals( "xlsx" )) {
			fileIco = "xls";		
		}
		else if( fileExt.equals( "ppt" ) || fileExt.equals( "pptx" )) {
			fileIco = "ppt";
		}
		else if( fileExt.equals( "zip" ) || fileExt.equals( "rar" ) || fileExt.equals( "alz" ) || fileExt.equals( "a00" ) ) {
			fileIco = "zip";
		}
		else if( fileExt.equals( "txt" ) ) {
			fileIco = "txt";
		}
		else if( fileExt.equals( "jpg" ) || fileExt.equals( "jpeg" ) || fileExt.equals( "png" ) || fileExt.equals( "bmp" ) || fileExt.equals( "gif" ) ) {
			fileIco = "jpg";
		}
		else if( fileExt.equals( "htm" ) || fileExt.equals( "html" ) ) {
			fileIco = "html";
		}
		else if( fileExt.equals( "exe" ) || fileExt.equals( "com" ) ) {
			fileIco = "exe";
		}
		
		return fileIcoS + fileIco + fileIcoE;
	}
	
	/**
    * Comment  : 현재 페이지의 서블릿 URL 전체 경로를 추출. 
    */
    public static String getCurrentlyURL(HttpServletRequest req){
        Enumeration<?> param = req.getParameterNames();
 
        StringBuffer strParam = new StringBuffer();
        StringBuffer strURL = new StringBuffer();
 
        if (param.hasMoreElements()){
            strParam.append("?");
        }
 
        while (param.hasMoreElements()){
            String name = (String) param.nextElement();
            String value = req.getParameter(name);
 
            strParam.append(name + "=" + value);
 
            if (param.hasMoreElements()){
                strParam.append("&");
            }
        }
 
        String url;
        if(req.getAttribute("javax.servlet.forward.request_uri") == null) {
            url = req.getRequestURI().toString();
        }
        else {
            url = req.getAttribute("javax.servlet.forward.request_uri").toString();
        }
        // contextPath 제거, 필요한 값(/index.do)
        url = url.replace(req.getContextPath(),"");
 
 
        //# URL 에서 URI 를 제거, 필요 값만 사용(프로토콜, 호스트, 포트)
        String getUrl = req.getRequestURL().toString().replace(req.getRequestURI(), "");
        strURL.append(getUrl);
        strURL.append(url); // servlet 경로 : /index.do 
        strURL.append(strParam); // getQueryString() 값
         
        // 전체 추출 경로 : http://www.aaa.co.kr/index.do?type=aaa(쿼리스트링)
        return strURL.toString();
    }
    
    /**
     * 이메일주소를 붙임
     * @param email1
     * @param email2
     * @return
     */
    public static String mergeEmail(String email1, String email2) {
        StringBuffer result = new StringBuffer();
        if (email1 != null) {
            result.append(email1.trim());
        }
        if (email2 != null) {
            result.append("@");
            result.append(email2.trim());
        }
        return result.toString();
    }
    
    /**
     * 세 문자열을 입력받아 전화번호를 만든다.
     * 
     * @param telNo1
     * @param telNo2
     * @param telNo3
     * @return
     */
    public static String mergeTelNo(String telNo1, String telNo2, String telNo3) {
        StringBuffer result = new StringBuffer();
        if (telNo1 != null) {
            result.append(telNo1.trim());
            if (telNo2 != null) {
                result.append("-");
                result.append(telNo2.trim());
            }
            if (telNo3 != null) {
                result.append("-");
                result.append(telNo3.trim());
            }
        } else {
            if (telNo2 != null) {
                result.append(telNo2.trim());
                if (telNo3 != null) {
                    result.append("-");
                    result.append(telNo3.trim());
                }
            } else {
                if (telNo3 != null) {
                    result.append(telNo3.trim());
                }                
            }
        }

        return result.toString();
    }
    
    /**
     * 브라우저 구분 얻기.
     * 
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
    	String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
    }
    
    /**
     * Disposition 지정하기.
     * 
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;
		filename = filename==null?"":filename;
		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
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

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

}