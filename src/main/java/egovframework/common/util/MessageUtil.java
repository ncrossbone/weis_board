package egovframework.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 공통 메세지 클래스
 * 내용 : 공통적인 요소의 메세지를 Client 자바에서 사용할 수 있도록 함.
 * 
 * @author Administrator cetech - hyg
 * @since 2016-02-16
 *
 */
public class MessageUtil
{
	private static final String initErrorMessage = "Exception Occurred";

	/**
	 * Method Name : cmmnPrintError
	 * Method Desc : 에러메세지 로그 출력
	 * Created : 2016-02-16 by hyg
	 * Updated :
	 * 
	 * @param logger
	 * @param ex
	 */
	public static void cmmnPrintError(Logger logger, Exception ex)
	{
		cmmnPrintError(logger, ex, initErrorMessage);
	}

	/**
	 * Method Name : cmmnPrintError
	 * Method Desc : 에러메세지 로그 출력
	 * Created : 2016-02-16 by hyg
	 * Updated :
	 * 
	 * @param logger
	 * @param ex
	 * @param message
	 */
	public static void cmmnPrintError(Logger logger, Exception ex, String message)
	{
		logger.error(getCmmnPrintError(ex, message));
	}

	/**
	 * Method Name : getCmmnPrintError
	 * Method Desc : 에러메세지 로그 반환
	 * Created : 2016-02-16 by hyg
	 * Updated :
	 * 
	 * @param logger
	 * @param ex
	 * @return returnMessage : created message
	 */
	public static String getCmmnPrintError(Exception ex)
	{
		return getCmmnPrintError(ex, initErrorMessage);
	}

	/**
	 * Method Name : getCmmnPrintError
	 * Method Desc : 에러메세지 로그 반환
	 * Created : 2016-02-16 by hyg
	 * Updated :
	 * 
	 * @param logger
	 * @param ex
	 * @param message
	 * @return returnMessage : created message
	 */
	public static String getCmmnPrintError(Exception ex, String message)
	{
		StackTraceElement[] elements = ex.getStackTrace();
		int stackIdx = 0;
		String sClassName = elements[stackIdx].getClassName();
		String sMethodName = elements[stackIdx].getMethodName();

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String todayStr = sdf.format(today);

		String returnMessage = "[" + todayStr + "] " + sClassName + "." + sMethodName + " ::: "
				+ message;
		return returnMessage;

	}
}
