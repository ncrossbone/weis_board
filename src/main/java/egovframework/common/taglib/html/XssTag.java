package egovframework.common.taglib.html;



import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

public class XssTag extends BodyTagSupport
{
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(XssTag.class);

	private String text;

	public int doAfterBody()
	{
		BodyContent body = getBodyContent();
		if (body != null)
		{
			text = body.getString();
		}
		if (text != null)
		{
			try
			{
				JspWriter out = getPreviousOut();

				// <xxx   onkeydown=''.... "onXXX=" 를 제거
				String outText = text.replaceAll("(?i)<script", "<s_cript")
									.replaceAll("(?i)<iframe", "<i_frame")
									.replaceAll("(?i)</script", "<s_cript")
									.replaceAll("(?i)</iframe", "</i_frame")
									.replaceAll("(?i)([ \\t\\n'\"])(on[a-z]+[ \\t\\n]*=)", "$1");
				out.print(outText);
			}
			catch (IOException e)
			{
				logger.info("IOException: "+e);
			}
		}
		return SKIP_BODY;
	}
}