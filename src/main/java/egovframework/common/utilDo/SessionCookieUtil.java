package egovframework.common.utilDo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import egovframework.cms.member.MemberVo;

public class SessionCookieUtil {

	
	/**
	 * 로그인 확인
	 * @param request
	 * @param loginInfo
	 * @return
	 */
	public static boolean isAdminLogin(HttpServletRequest request) {
		try{
			HttpSession session = request.getSession();
			return (session == null || session.getAttribute("adminMb") == null) ? false : true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 로그인 확인
	 * @param request
	 * @param loginInfo
	 * @return
	 */
	public static boolean isMobileLogin(HttpServletRequest request) {
		try{
			HttpSession session = request.getSession();
			return (session == null || session.getAttribute("mobileMb") == null) ? false : true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * HttpSession에 주어진 키 값으로 세션 정보를 생성하는 기능
	 * 
	 * @param request
	 * @param keyStr
	 *            - 세션 키
	 * @param valStr
	 *            - 세션 값
	 * @throws Exception
	 */
	public static void setSessionAttribute(HttpServletRequest request,
			String keyStr, String valStr) throws Exception {

		HttpSession session = request.getSession();
		session.setAttribute(keyStr, valStr);
	}

	/**
	 * HttpSession에 주어진 키 값으로 세션 객체를 생성하는 기능
	 * 
	 * @param request
	 * @param keyStr
	 *            - 세션 키
	 * @param valStr
	 *            - 세션 값
	 * @throws Exception
	 */
	public static void setSessionAttribute(HttpServletRequest request,
			String keyStr, Object obj) throws Exception {

		HttpSession session = request.getSession();
		session.setAttribute(keyStr, obj);
	}

	/**
	 * HttpSession에 존재하는 주어진 키 값에 해당하는 세션 값을 얻어오는 기능
	 * 
	 * @param request
	 * @param keyStr
	 *            - 세션 키
	 * @return
	 * @throws Exception
	 */
	public static Object getSessionAttribute(HttpServletRequest request,
			String keyStr) throws Exception {

		HttpSession session = request.getSession();
		return session.getAttribute(keyStr);
	}

	/**
	 * HttpSession 객체내의 모든 값을 호출하는 기능
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getSessionValuesString(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String returnVal = "";

		Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String sessionKey = (String) e.nextElement();
			returnVal = returnVal + "[" + sessionKey + " : "
					+ session.getAttribute(sessionKey) + "]";
		}

		return returnVal;
	}

	/**
	 * HttpSession에 존재하는 세션을 주어진 키 값으로 삭제하는 기능
	 * 
	 * @param request
	 * @param keyStr
	 *            - 세션 키
	 * @throws Exception
	 */
	public static void removeSessionAttribute(HttpServletRequest request,
			String keyStr) throws Exception {

		HttpSession session = request.getSession();
		session.removeAttribute(keyStr);
	}
	
	
	/**
	 * HttpSession에 존재하는 모든 세션 값 삭제하는 기능
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void removeSessionAllAttribute(HttpServletRequest request
			) throws Exception {

		HttpSession session = request.getSession();

		Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String sessionKey = (String) e.nextElement();
			session.removeAttribute(sessionKey);
		}
		
	}
	
	/**
	 * 쿠키생성 - 입력받은 분만큼 쿠키를 유지되도록 세팅한다. 쿠키의 유효시간을 5분으로 설정 =>(cookie.setMaxAge(60
	 * * 5) 쿠키의 유효시간을 10일로 설정 =>(cookie.setMaxAge(60 * 60 * 24 * 10)
	 * 
	 * @param response
	 *            - Response
	 * @param cookieNm
	 *            - 쿠키명
	 * @param cookieValue
	 *            - 쿠키값
	 * @param minute
	 *            - 지속시킬 시간(분)
	 * @return
	 * @exception
	 * @see
	 */
	public static void setCookie(HttpServletResponse response, String cookieNm,
			String cookieVal, int minute) throws UnsupportedEncodingException {

		// 특정의 encode 방식을 사용해 캐릭터 라인을 application/x-www-form-urlencoded 형식으로 변환
		// 일반 문자열을 웹에서 통용되는 'x-www-form-urlencoded' 형식으로 변환하는 역할
		String cookieValue = URLEncoder.encode(cookieVal, "utf-8");

		// 쿠키생성 - 쿠키의 이름, 쿠키의 값
		Cookie cookie = new Cookie(cookieNm, cookieValue);

		// 쿠키의 유효시간 설정
		cookie.setMaxAge(60 * minute);
		
		cookie.setPath("/");

		// response 내장 객체를 이용해 쿠키를 전송
		response.addCookie(cookie);
	}

	/**
	 * 쿠키생성 - 쿠키의 유효시간을 설정하지 않을 경우 쿠키의 생명주기는 브라우저가 종료될 때까지
	 * 
	 * @param response
	 *            - Response
	 * @param cookieNm
	 *            - 쿠키명
	 * @param cookieValue
	 *            - 쿠키값
	 * @return
	 * @exception
	 * @see
	 */

	public static void setCookie(HttpServletResponse response, String cookieNm,
			String cookieVal) throws UnsupportedEncodingException {

		// 특정의 encode 방식을 사용해 캐릭터 라인을 application/x-www-form-urlencoded 형식으로 변환
		// 일반 문자열을 웹에서 통용되는 'x-www-form-urlencoded' 형식으로 변환하는 역할
		String cookieValue = URLEncoder.encode(cookieVal, "utf-8");

		// 쿠키생성
		Cookie cookie = new Cookie(cookieNm, cookieValue);

		// response 내장 객체를 이용해 쿠키를 전송
		response.addCookie(cookie);
	}

	/**
	 * 쿠키값 사용 - 쿠키값을 읽어들인다.
	 * 
	 * @param request
	 *            - Request
	 * @param name
	 *            - 쿠키명
	 * @return 쿠키값
	 * @exception
	 * @see
	 */
	public static String getCookie(HttpServletRequest request, String cookieNm)
			throws Exception {

		// 한 도메인에서 여러 개의 쿠키를 사용할 수 있기 때문에 Cookie[] 배열이 반환
		// Cookie를 읽어서 Cookie 배열로 반환
		Cookie[] cookies = request.getCookies();

		if (cookies == null)
			return "";

		String cookieValue = null;

		// 입력받은 쿠키명으로 비교해서 쿠키값을 얻어낸다.
		for (int i = 0; i < cookies.length; i++) {

			if (cookieNm.equals(cookies[i].getName())) {

				// 특별한 encode 방식을 사용해 application/x-www-form-urlencoded 캐릭터 라인을
				// 디코드
				// URLEncoder로 인코딩된 결과를 디코딩하는 클래스
				cookieValue = URLDecoder.decode(cookies[i].getValue(), "utf-8");

				break;

			}
		}

		return cookieValue;
	}

	/**
	 * 쿠키값 삭제 - cookie.setMaxAge(0) - 쿠키의 유효시간을 0으로 설정해 줌으로써 쿠키를 삭제하는 것과 동일한 효과
	 * 
	 * @param request
	 *            - Request
	 * @param name
	 *            - 쿠키명
	 * @return 쿠키값
	 * @exception
	 * @see
	 */
	public static void setCookie(HttpServletResponse response, String cookieNm)
			throws UnsupportedEncodingException {

		// 쿠키생성 - 쿠키의 이름, 쿠키의 값
		Cookie cookie = new Cookie(cookieNm, null);

		// 쿠키를 삭제하는 메소드가 따로 존재하지 않음
		// 쿠키의 유효시간을 0으로 설정해 줌으로써 쿠키를 삭제하는 것과 동일한 효과
		cookie.setMaxAge(0);

		// response 내장 객체를 이용해 쿠키를 전송
		response.addCookie(cookie);
	}

	public static void setMaxInactiveInterval(HttpServletRequest request, int parseInt) {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(parseInt);

	}
	
	/**
	 * 
	 * 로그인 정보 쿠키 처리
	 * 
	 * @param memberMd
	 * @param request
	 * @param response
	 */
	public static void loginCookieProc(MemberVo memberVo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String[] cookieName = new String[] { "user_id", "pwd", "cookie_save_yn"};
			String[] cookieVal = null;

			int cookieMaxAge = 0;
			String cookieSavePath = "/";

			// 로그인정보 저장 시
			if ("Y".equals(memberVo.getCookie_save_yn())) {
				cookieVal = new String[] { memberVo.getUser_id(), memberVo.getPwd(), "Y"};
				// 한달 정도 정보를 가지고 있음
				cookieMaxAge = 30 * 24 * 60 * 60;
			} else { // 로그인정보 삭제
				cookieVal = new String[] { "", "", "", "" };
			}

			// 로그인정보 저장 유무에 따른 쿠키 설정
			for (int i = 0; i < cookieName.length; i++) {
				Cookie loginCookie = new Cookie(cookieName[i], URLEncoder.encode(cookieVal[i], "euc-kr"));
				loginCookie.setMaxAge(cookieMaxAge);
				loginCookie.setPath(cookieSavePath);
				response.addCookie(loginCookie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	
//	
//	/**
//	 * 세션 값 조회
//	 * @param div 종류
//	 * 	user_id : 일반사용자는 아이디
//	 * @param request
//	 * @return
//	 */
//	public static String getAdminSessionInfo(String div, HttpServletRequest request) {
//
//		String returnVal = "";
//
//		try {
//
//			MemberModel memberModel = (MemberModel) SessionCookieUtil.getSessionAttribute(request, "adminMb");
//
//			returnVal = memberModel.getInfo(div);
//
//		} catch (Exception e) {
//			return "";
//		}
//
//		return returnVal;
//	}

	

}
