package egovframework.common.taglib.html;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import egovframework.common.taglib.html.bean.BeanUtil;
import egovframework.common.taglib.html.bean.TypeConvertUtil;
import egovframework.common.util.StringUtil;


/**
 * 셀렉트 박스 생성 태그
 * @author seominho
 *
 */
public class SelectInt extends DefaultTagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 835253724930373183L;
    private String begin;
    private String end;
    private String step;
    private String selected;
    private String  defaultValue;

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}	
	
	
	protected int getIntValue(Object obj) {
		int result = 0;
    	if (obj != null) {
        	if (obj instanceof Integer) {
        		result = ((Integer)obj).intValue();
        	} else if (obj instanceof Long) {
        		result = ((Long)obj).intValue();
        	} else {
        		result = TypeConvertUtil.convertInt(String.valueOf(obj), 0);
        	}	        		
    	}
    	
		return result;
	}

	public int doStartTag() throws JspException
	{
		String s = "";
		String sel;

		int sdt = 0;
		int bt = 1;
		int et = 9;
		int st = 1;
		int dt = 10;

		if (selected != null)
		{
			sdt = getIntValue(getParsedValue(selected, null));
		}

		bt = getIntValue(getParsedObject(begin, begin));
		et = getIntValue(getParsedObject(end, end));
		st = getIntValue(getParsedObject(step, step));
		dt = getIntValue(defaultValue);
		if (dt == 0) dt = 10;
		sdt = (sdt == 0) ? dt : sdt;
		st = (st == 0) ? 1 : st;
		for (int i = bt; i <= et; i += st)
		{
			sel = (i == sdt) ? " selected " : "";
			s = s + "<option value='" + StringUtil.leftPad(i, 2, "0") + "'" + sel + ">"
					+ StringUtil.leftPad(i, 2, "0") + "</option>\n";
		}
		try
		{
			pageContext.getOut().print(s);
		}
		catch (IOException e)
		{
			System.out.println("IOException: " + e);
		}
		return SKIP_BODY;
	}

	@SuppressWarnings("rawtypes")
	protected String getParsedValue(String value, String name)
	{
		if (value != null)
		{

			Object result = null;
			int dotIndex = value.indexOf('.');
			if (dotIndex > -1)
			{
				String varName1 = value.substring(0, dotIndex);
				String varName2 = value.substring(dotIndex + 1);
				if ("param".equalsIgnoreCase(varName1))
				{
					result = super.pageContext.getRequest().getParameter(varName2);
				}
				else
				{
					Object obj = super.pageContext.getRequest().getAttribute(varName1);
					if (obj == null)
					{
						obj = super.pageContext.getAttribute(varName1);
					}
					if (obj == null)
					{//session체크
						obj = super.pageContext.getSession().getAttribute(varName1);
					}
					if (obj != null)
					{
						if (obj.getClass().getName().indexOf("Map") >= 0)
						{
							result = ((Map) obj).get(varName2);
						}
						else
						{
							try
							{
								result = BeanUtil.getMethodValue(obj, varName2);
							}
							catch (IllegalArgumentException e)
							{
								System.out.println("IllegalArgumentException: " + e);
								// e.printStackTrace();
							}
							catch (IntrospectionException e)
							{
								System.out.println("IntrospectionException: " + e);
								// e.printStackTrace();
							}
							catch (NoSuchMethodException e)
							{
								System.out.println("NoSuchMethodException: " + e);
								// e.printStackTrace();
							}
							catch (IllegalAccessException e)
							{
								System.out.println("IllegalAccessException: " + e);
								// e.printStackTrace();
							}
							catch (InvocationTargetException e)
							{
								System.out.println("InvocationTargetException: " + e);
								// e.printStackTrace();
							}
						}
					}
				}
			}
			else
			{
				Object obj = super.pageContext.getAttribute(value);
				if (obj == null)
				{
					obj = super.pageContext.getRequest().getAttribute(value);
				}
				if (obj == null)
				{ //session체크
					obj = super.pageContext.getSession().getAttribute(value);
				}
				if (obj != null)
				{
					result = obj;
				}
			}
			value = (result != null) ? result.toString() : null;
		}
		else
		{
			if (name != null)
			{
				ServletRequest request = super.pageContext.getRequest();
				value = request.getParameter(name);
				if (value == null)
				{
					Object obj = request.getAttribute(name);
					value = (obj != null) ? String.valueOf(obj) : null;
				}
			}
		}

		return value;
	}

	protected Object getParsedObject(String value, Object defaulValue) {
        Object result = defaulValue;
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
                    if(obj == null){//session체크
                    	obj = super.pageContext.getSession().getAttribute(varName1);
                    }
					if (obj != null)
					{
						try
						{
							result = BeanUtil.getMethodValue(obj, varName2);
						}
						catch (IllegalArgumentException e)
						{
							System.out.println("IllegalArgumentException: " + e);
							// e.printStackTrace();
						}
						catch (IntrospectionException e)
						{
							System.out.println("IntrospectionException: " + e);
							// e.printStackTrace();
						}
						catch (NoSuchMethodException e)
						{
							System.out.println("NoSuchMethodException: " + e);
							// e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							System.out.println("IllegalAccessException: " + e);
							// e.printStackTrace();
						}
						catch (InvocationTargetException e)
						{
							System.out.println("InvocationTargetException: " + e);
							// e.printStackTrace();
						}
					}
                }
            } else {
                result = super.pageContext.getRequest().getAttribute(value);
                if (result == null) {
                	result = super.pageContext.getAttribute(value);
                }
                if(result == null){//session체크
                	result = super.pageContext.getSession().getAttribute(value);
                }
                if(result == null){
                	result = defaulValue;
                }
            }
        }
        
        return result;
    }
    
    
    public int doEndTag() {
  
        return EVAL_PAGE;
    }
}
