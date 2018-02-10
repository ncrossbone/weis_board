package egovframework.cms.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.cms.log.access.service.AccessLogService;
import egovframework.cms.member.MemberVo;
import egovframework.cms.member.service.MemberService;
import egovframework.common.controller.DefaultController;
import egovframework.common.helper.EgovHttpRequestHelper;
import egovframework.common.utilDo.JsUtil;
import egovframework.common.utilDo.SFAConstants;
import egovframework.common.utilDo.SessionCookieUtil;

@Controller
@RequestMapping("/egov/cms")
public class LoginController extends DefaultController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "MemberService")
	private MemberService memberService;
  
	@Resource(name = "AccessLogService")
	private AccessLogService accessLogService;
	
    /**
      * 관리자 로그인 폼
      * @return
      * @throws Exception
    */
	@SuppressWarnings("unchecked")
  	@RequestMapping(value = "/login/memberLoginFrm")
  	public String memberLiginFrm(Model model) throws Exception {
	  	try{
//	  	    MemberVo memberVo = memberService.memberLogin(commandMap);
//	        // 접속 로그
//	        accessLog(request, memberVo);
	  	}catch(Exception e){
	  		
	  	}

  		return "cms/login/memberLoginFrm";
	}
	
	/***********************************************************
	 * @subject 로그인 처리
	 * @content 웹에서 처리되는 로그인 
	 * @author 이현호
	 * @since 2017.11.07
	 * @return 
	 * @throws Exception
	 ************************************************************/
	@RequestMapping(value = "/login/loginProc")
	public void webLogin(MemberVo memberVo, Model model) throws Exception {
		//로그인 쿠키 처리
		SessionCookieUtil.loginCookieProc(memberVo, request, response);
		String callUrl = "/egov/cms/login/memberLoginFrm";
		
		try{
			//map으로 전달시 commandMap
			//vo로 전달시  memberVo
			memberVo = memberService.memberLogin(commandMap, memberVo);
	        // 접속 로그
//	        accessLog(request, memberVo);
	        
			if(SFAConstants.SUCCESS.equals(memberVo.getRes_cd())) {
				SessionCookieUtil.setSessionAttribute(request, "mngMb", memberVo);
				SessionCookieUtil.setMaxInactiveInterval(request, Integer.parseInt(session_time));
				
				JsUtil.goURL("/egov/systemMng/site/userInfo", response);
			
			} else {
				JsUtil.goAlertURL(memberVo.getRes_msg(), callUrl, response);
			}			
		}catch(Exception e){
			e.printStackTrace();
			
			JsUtil.goAlertURL(memberVo.getRes_msg(), callUrl, response);
		}
	}

	/**
	 * 관리자 로그아웃 처리
	 * @return ModelAndView
	 * @param commandMap : 모든 파라미터 정보, method = POST
	 * @exception Exception
	 * Exception
	 */
	@RequestMapping(value = "/login/loOut")
	public ModelAndView adminlogOutProc() throws Exception {

		/************** ModelView 로직 **************/
		HttpSession session = request.getSession(true);
		session.invalidate();
		ModelAndView mav = new ModelAndView();
		String resultURL = "redirect:/egov/cms/login/memberLoginFrm";
		mav.setViewName(resultURL);
		/************** ModelView 로직 **************/

		return mav;
	}
	
	/**
	 * 로그인 접속 기록 저장
	 * 
	 * @param request
	 * @param memberVo
   	* @throws Exception
   	*/
	public void accessLog(HttpServletRequest request, MemberVo memberVo) throws Exception {
		String INS_IP = EgovHttpRequestHelper.getRequestIp();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CRTR_ID", memberVo.getUser_id());
		param.put("CRTR_IP", INS_IP);
		param.put("LOG_GUBN", "2"); // 1:방문 , 2:로그인
		param.put("LOG_DESC", "로그인");
		accessLogService.insertAccessLog(param);
	}
}
