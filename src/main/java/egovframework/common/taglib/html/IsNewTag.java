package egovframework.common.taglib.html;



//import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.common.taglib.html.bean.BeanUtil;
import egovframework.common.taglib.html.bean.TypeConvertUtil;

/**
 * <pre><xmp>
 * 현재날짜와 컨텐츠날짜를 비교해서 신규글 여부를 체크.
 *
 * ex) <html:isNew value="${eo.date}" day="1" />
 * </xmp></pre>
 * @author pcn
 * @version 2008. 04. 28
 */
public class IsNewTag extends BodyTagSupport {

    /**
     *
     */
    private static final long serialVersionUID = -5549606535783641602L;

    private Logger logger  = LoggerFactory.getLogger(this.getClass());

    private String value;
    private String day = "1";



    public int doAfterBody() {
        String text = null;
        int days = TypeConvertUtil.convertInt(day, 1);
        BodyContent body = getBodyContent();
        if (body != null) {
            text = body.getString();
        }
        try {
            Date date = (java.util.Date)getParsedObject(value);

        	//String receiveDate = (String)getParsedObject(value);
        	//Date date = DateUtil.toDate(receiveDate.split("-")[0].trim(),
        	//		receiveDate.split("-")[1].trim(),
        	//		receiveDate.split("-")[2].trim());

            if ((date != null && (date.getTime() + (days * 24 * 60 * 60 * 1000)) > System.currentTimeMillis())) {
                JspWriter out = getPreviousOut();
                out.print(text);
            }
        } catch (IOException e) {
        	logger.info("에러발생 {}",e);
        } catch (Exception e) {
          logger.info("에러발생 {}",e);
        }
        return SKIP_BODY;
    }


    /**
     * @param vale The vale to set.
     */
    public void setValue(String vale) {
        this.value = vale;
    }


    /**
     * @param day The day to set.
     */
    public void setDay(String day) {
        this.day = day;
    }


    protected Object getParsedObject(String value) throws Exception {
        Object result = value;
        if(value != null) {
                int dotIndex = value.indexOf('.');
                if (dotIndex > -1) {
                    String varName1 = value.substring(0, dotIndex);
                    String varName2 = value.substring(dotIndex+1);
                    if ("param".equalsIgnoreCase(varName1)) {
                        result = super.pageContext.getRequest().getParameter(varName2);
                    } else {
                        Object obj = super.pageContext.getRequest().getAttribute(varName1);
                        if (obj == null) {
                            obj = super.pageContext.getAttribute(varName1);
                        }
                        if (obj != null) {
                        	if(obj.getClass().getName().indexOf("Map") >= 0) {
                        		Map map = (Map) obj;
                        		result = map.get(varName2);
                        	} else {
                        		result = BeanUtil.getMethodValue(obj, varName2);
                        	}
                        }
                    }
                } else {
                    result = super.pageContext.getRequest().getAttribute(value);
                }
        }

        return result;
    }



}
