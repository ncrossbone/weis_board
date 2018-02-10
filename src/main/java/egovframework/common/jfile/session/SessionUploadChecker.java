package egovframework.common.jfile.session;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SessionUploadChecker {
	
	
	private static Logger log  = LoggerFactory.getLogger(SessionUploadChecker.class);
	
	public static ConcurrentHashMap<String, Boolean> sessionMap = new ConcurrentHashMap<String, Boolean>(); 
	
	public static void check(HttpServletRequest request, String fileId) {
		
		log.debug(new StringBuilder().append("\n").append("\n")
		.append("================== session upload check ==================").append("\n")
		.append(" jsessionid_fileId : ").append(request.getSession().getId()).append("\n")
		.append("==========================================================").append("\n")
		.toString());
		sessionMap.put(request.getSession().getId()+"_"+fileId, true);
		
	}
	
	public static void unCheck(HttpServletRequest request, String fileId) {
		
		log.debug(new StringBuilder().append("\n").append("\n")
		.append("================= session upload uncheck =================").append("\n")
		.append(" jsessionid_fileId : ").append(request.getSession().getId()).append("\n")
		.append("==========================================================").append("\n")
		.toString());
		
		if(sessionMap.containsKey(request.getSession().getId()+"_"+fileId)) {
			sessionMap.remove(request.getSession().getId()+"_"+fileId);
		}
	}	
	
	public static boolean isContainsKey(HttpServletRequest request, String fileId) {
		return sessionMap.containsKey(request.getSession().getId()+"_"+fileId);
	}
}
