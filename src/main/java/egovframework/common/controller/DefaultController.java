package egovframework.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import egovframework.cms.log.access.service.AccessLogService;
import egovframework.cms.member.MemberVo;
import egovframework.common.helper.EgovHttpRequestHelper;
import egovframework.common.helper.EgovUserDetailsHelper;


@Controller
public class DefaultController extends CommonController{
	
	static Logger logger = Logger.getLogger(DefaultController.class);
	
	@Resource(name = "AccessLogService")
	private AccessLogService accessLogService;
	
	/**
	 * 초기값 세팅
	 * @param request
	 * @param response
	 * @param commandMap
	 * @throws ModelAndViewDefiningException 
	 * @throws Exception
	 */
	@ModelAttribute("init")
	public void init(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> commandMap) throws Exception  {
	  this.request    = request;
	  this.response   = response;
	  this.commandMap = commandMap;
	  this.commandMap.putAll(getAuthUserInfo());
	}
	
	/**
	 * 접속 로그 저장 최초 1번만
	 * @param request
	 * @param memberVo
	 * @throws Exception
	 */
	public void accessLog () throws Exception{
		HttpSession session = request.getSession(true);
		if (session.getAttribute("cmsTopMenuListSession") == null) {
			String INS_IP = EgovHttpRequestHelper.getRequestIp();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("CRTR_IP", INS_IP);
			param.put("LOG_GUBN", "1"); //1:방문 , 2:로그인
			param.put("LOG_DESC", "최초접속방문");
			accessLogService.insertAccessLog(param);
		}
	}

}
