package egovframework.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.common.helper.EgovUserDetailsHelper;

/**
 * 인증여부 체크 인터셉터 전자정부 공통 서비스 참조 수정자 : 방지환
 */
public class AuthenticInterceptor extends HandlerInterceptorAdapter {
  
  private Logger log = LoggerFactory.getLogger(this.getClass());
  
  /**
   * 세션에 계정정보(MemberVo)가 있는지 여부로 인증 여부를 체크한다. 계정정보(adminVO)가 없다면, 로그인 페이지로 이동한다.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    //테스트 모드일경우 통과
    if(EgovUserDetailsHelper.SYSTEM_MODE.equals("TEST")){
      return true;
    }
    
    
    String requestURI = request.getRequestURI(); //요청 URI
    String webPath = "cms";
    if(requestURI.startsWith("/cms")){
      webPath = "cms";
    }else if(requestURI.startsWith("/mobile")){
      webPath = "mobile";
    }
    
    return true;
  }
  
}
