package egovframework.common.taglib.html;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import egovframework.common.taglib.html.bean.BeanUtil;


/**
 * TagSupport 확장
 * 
 * @author pcn
 * @version 2008. 04. 28
 */
public abstract class DefaultTagSupport extends TagSupport implements HtmlConstants
{
	protected String getParsedValue(String value)
	{
		String result = null;
		try
		{
			result = getParsedValue(value, null);
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
		return result;
	}

    /**
     * 입력된 값이 변수이면 그 변수의 값을 반환하고,
     * 그 값이 null이면 해당 이름으로 값을 가져와서 반환하다.
     * 
     * [수정]2006.05.12 : ....'xxxxx${yyyy}zzz'형태인 경우 고려해서 수정함. 
     * [수정]2008.01.15 : session값 체크. Map형태 .사용시 체크
     * 
     * @param name
     * @param value
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws IntrospectionException 
     * @throws IllegalArgumentException 
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	protected String getParsedValue(String value, String name) throws IllegalArgumentException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(value != null) {
			int idx1 = value.indexOf("${");
			int idx2 = value.lastIndexOf("}");
			String sPrefix = "";
			String sSurfix = "";
			if(idx1 >= 0){
				sPrefix = value.substring(0, idx1);
			}
			if(idx2 >= 0){
				sSurfix = value.substring(idx2+1, value.length());
			}
			
            int index = value.indexOf("${");

            if (index > -1) {
                Object result = null;               
                String varName = value.substring(index + 2, value.lastIndexOf('}'));
                int dotIndex = varName.indexOf('.');
                if (dotIndex > -1) {
                    String varName1 = varName.substring(0, dotIndex);
                    String varName2 = varName.substring(dotIndex+1);
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
                        if (obj != null) {
                        	if(obj.getClass().getName().indexOf("Map")>=0){
                        		result = ((Map)obj).get(varName2);
                        	}else{
                        		result = BeanUtil.getMethodValue(obj, varName2);
                        	}
                        }
                    }
                } else {
                    Object obj = super.pageContext.getAttribute(varName);
                    if (obj == null) {
                        obj = super.pageContext.getRequest().getAttribute(varName);    
                    }
                    if(obj == null){ //session체크
                    	obj = super.pageContext.getSession().getAttribute(varName);
                    }
                    if (obj != null) {
                        result = obj;
                    }
                }
                value = (result != null) ? result.toString() : null;                
            }
            if(value == null){
            	value = sPrefix + sSurfix;
            	if("".equals(value))
            		value = null;
            }else
            	value = sPrefix + value + sSurfix;
        } else {
            if (name != null) {
                ServletRequest request = super.pageContext.getRequest();
                value = request.getParameter(name);
                if (value == null) {
                    Object obj = request.getAttribute(name);
                    value = (obj != null) ? String.valueOf(obj) : null;
                }                           
            }
        }
        
        return value;
    }

	protected Object getParsedObject(String value)
	{
		Object result = null;
		if (value != null)
		{
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
						try
						{
							result = BeanUtil.getMethodValue(obj, varName2);
						}
						catch (IllegalArgumentException e)
						{
							System.out.println("IllegalArgumentException" + e);
							// e.printStackTrace();
						}
						catch (IntrospectionException e)
						{
							System.out.println("IntrospectionException" + e);
							// e.printStackTrace();
						}
						catch (NoSuchMethodException e)
						{
							System.out.println("NoSuchMethodException" + e);
							// e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							System.out.println("IllegalAccessException" + e);
							// e.printStackTrace();
						}
						catch (InvocationTargetException e)
						{
							System.out.println("InvocationTargetException" + e);
							// e.printStackTrace();
						}
					}
				}
			}
			else
			{
				result = super.pageContext.getRequest().getAttribute(value);
				if (result == null)
				{
					result = super.pageContext.getAttribute(value);
				}
				if (result == null)
				{//session체크
					result = super.pageContext.getSession().getAttribute(value);
				}
			}
		}
		return result;
	}

    protected Object getParsedObject(String value, Object defaulValue) throws Exception {
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
                        if (obj != null) {
                            result = BeanUtil.getMethodValue(obj, varName2);
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
                }
        }
        
        return result;
    }
}
