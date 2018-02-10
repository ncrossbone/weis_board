package egovframework.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * CSRF 대비 임시토큰 생성 등 관련된 클래스
 * 
 * @author Administrator
 *
 */
public class CSRFTokenUtil
{
	private final static String DEFAULT_PRNG = "SHA1PRNG";
	public final static String SESSION_ATTR_KEY = "CSRF_TOKEN";
	private final static String NO_SESSION_ERROR = "No valid sessin found";

	private static String getToken() throws NoSuchAlgorithmException
	{
		return getToken(DEFAULT_PRNG);
	}

	/**
	 * <pre>
	 * 토큰 생성
	 * </pre>
	 * 
	 * @param prng
	 * @return String
	 * @throws ServletException
	 * @throws NoSuchAlgorithmException
	 */
	private static String getToken(String prng) throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance(prng);
		return "" + sr.nextLong();
	}

	/**
	 * <pre>
	 * 토큰 생성
	 * </pre>
	 * 
	 * @param session
	 * @return String
	 * @throws ServletException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getToken(HttpSession session) throws ServletException, NoSuchAlgorithmException
	{
		if (session == null)
		{
			throw new ServletException(NO_SESSION_ERROR);
		}

		String token_val = (String) session.getAttribute(SESSION_ATTR_KEY);
		if (token_val == null)
		{
			token_val = getToken();
			session.setAttribute(SESSION_ATTR_KEY, token_val);
		}
		return token_val;
	}

	/**
	 * <pre>
	 * 토큰 유효 확인
	 * </pre>
	 * 
	 * @param request
	 * @return boolean
	 * @throws ServletException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean isValid(HttpServletRequest request) throws ServletException, NoSuchAlgorithmException
	{
		if (request.getSession(false) == null)
		{
			throw new ServletException(NO_SESSION_ERROR);
		}
		return getToken(request.getSession(false)).equals(request.getParameter(SESSION_ATTR_KEY));
	}

	/**
	 * <pre>
	 * 토큰 삭제
	 * </pre>
	 * 
	 * @param session
	 * @throws ServletException
	 */
	public static void deleteToken(HttpSession session) throws ServletException
	{
		session.removeAttribute(SESSION_ATTR_KEY);
	}
}