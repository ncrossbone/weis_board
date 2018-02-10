package egovframework.common.helper;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import egovframework.cms.member.MemberVo;
import egovframework.common.controller.ControllerConstants;
import egovframework.common.util.StringUtil;

/**
 * 사용자 및 관리자 정보
 * @author Administrator
 *
 */
public class EgovUserDetailsHelper implements ControllerConstants{
  
	/**
	 * 인증된 회원정보를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		return (MemberVo) RequestContextHolder.getRequestAttributes().getAttribute(SESSION_KEY_USER, RequestAttributes.SCOPE_SESSION); 
	}

	/**
	 * 인증된 회원인지 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)	
	 */
	public static Boolean isAuthUserChecked() {
		if (StringUtil.isBlank((MemberVo) RequestContextHolder.getRequestAttributes().getAttribute(SESSION_KEY_USER, RequestAttributes.SCOPE_SESSION))) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
}
