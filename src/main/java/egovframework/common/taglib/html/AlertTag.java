package egovframework.common.taglib.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;


public class AlertTag extends DefaultTagSupport {
	

    private static final long serialVersionUID = -3845824886278697801L;

    private String value;
    private String location;
    private String target;
    private String script;
	
	
	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}


	public int doEndTag() throws JspException {
		try {
            value = getParsedValue(value);
		    JspWriter out = super.pageContext.getOut();
            
            StringBuffer html = new StringBuffer();
            html.append("<script type=\"text/javascript\">");
            if (value != null) {
                html.append("alert(\"").append(value).append("\");");
            }
            
            if (location != null) {
            	if(target == null){
            		html.append("window.location.href=\"").append(location).append("\";");
            	}else{
            		html.append(target).append(".location.href=\"").append(location).append("\";");
            	}
            }
            
        	if("back".equals(script)){
        		html.append("history.back()");
        	}

            html.append("</script>");
            
			out.print(html.toString());
		} catch (IOException e) {
		  System.out.println(e.toString());
		} catch (Exception e) {
		  System.out.println(e.toString());
			//logger.println(Logger.ERROR, e, this);
		}
		return (SKIP_BODY);
	}


    /**
     * ����� ��
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * �̵��� url. null�̸� �̵����� ����
     * @param location The value to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * �̵��� url�� target
     * @param target The value to set.
     */
    public void setTarget(String target) {
        this.target = target;
    }
    
    /**
     * �߰� ��ũ��Ʈ
     * @param target The value to set.
     */
    public void setScript(String script) {
        this.script = script;
    }    
    
}
