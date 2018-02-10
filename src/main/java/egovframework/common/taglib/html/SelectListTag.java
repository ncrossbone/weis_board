package egovframework.common.taglib.html;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import egovframework.common.taglib.html.bean.BeanUtil;
import egovframework.common.taglib.html.bean.TypeConvertUtil;
import egovframework.common.util.StringUtil;

/**
 * 선택박스
 * @author admin
 *
 */
public class SelectListTag extends DefaultTagSupport {
	
	/**
     * 
     */
    private static final long serialVersionUID = 5827327941631548270L;
    private String name;
    private String id;
    private String title;
  	private String script;
  	private String optionValues;
  	private String optionNames;
  	private String selectedValue;
  	private String defaultValue;
    private String list;
    private String listValue;
    private String listName;
	
    private String  selectedDelimiter;
    private String  selectedIndex;
	//private Logger logger = Logger.getLogger(this);
	
	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}


	public int doEndTag() throws JspException {
		try {
		    selectedValue = getParsedValue(selectedValue, name);
		    if(selectedDelimiter!=null && selectedValue != null){
		    	String[] tValues =  StringUtil.toStringArray(selectedValue, selectedDelimiter);
		    	selectedValue = tValues[TypeConvertUtil.convertInt(selectedIndex,0)];
		    }
		    
		    JspWriter out = super.pageContext.getOut();
            String[] values = null;
            String[] names = null;
            
          if (list != null) {
                Object obj = getParsedObject(list);
//                if (obj == null) {
//                    obj = pageContext.getServletContext().getAttribute(list);
//                }
                if (obj != null) {
                    List list = (List)obj;
                    
                    
                    String[] tValues = null;
                    String[] tNames = null;
                    if (optionValues != null) {
                        tValues = (optionValues != null) ? StringUtil.toStringArray(optionValues, DELIMITER) : null;
                        tNames = (optionNames != null) ? StringUtil.toStringArray(optionNames, DELIMITER) : tValues;
                    }
                    int fSize = tValues != null ? tValues.length : 0;
                    int size = list.size() + fSize;
                    values = new String[size];
                    names = new String[size];
                    if (fSize > 0) {
                        for (int i = 0; i < fSize; i++) {
                          if(values !=null && tValues !=null){values[i] = tValues[i];}else{break;}
                          if(names !=null && tNames !=null){names[i] = tNames[i];}else{break;}
                        }
                    }

                    if (listValue != null && listName != null) {
                        Object data = null;
                        Object valueObj = null;
                        Object nameObj = null;
                        for (int i = 0; i < list.size(); i++) {
                            data = (Object)list.get(i);
                        	if(data.getClass().getName().indexOf("Map")>=0){
                        		valueObj = ((Map)data).get(listValue);
                        		nameObj = ((Map)data).get(listName);
                        	}else{
                                valueObj = BeanUtil.getMethodValue(data, listValue);
                                nameObj = BeanUtil.getMethodValue(data, listName);
                        	}
                            values[i+fSize] = (valueObj != null) ? String.valueOf(valueObj) : null;
                            names[i+fSize] = (nameObj != null) ? String.valueOf(nameObj) : null;
                        }                        
                    } else {
                        String[] data = null;
                        for (int i = 0; i < list.size(); i++) {
                            data = (String[])list.get(i);
                            values[i+fSize] = data[0];
                            names[i+fSize] = data[1];
                        }                        
                    }

                } else {
                    values = (optionValues != null) ? StringUtil.toStringArray(optionValues, DELIMITER) : null;
                    names = (optionNames != null) ? StringUtil.toStringArray(optionNames, DELIMITER) : values;
                }
            } else {
                values = (optionValues != null) ? StringUtil.toStringArray(optionValues, DELIMITER) : null;
                names = (optionNames != null) ? StringUtil.toStringArray(optionNames, DELIMITER) : values;
            }
            SelectList showSelectList = new SelectList(name, script, values, names, selectedValue, defaultValue, title ,id);
    		
            
            if(showSelectList!=null){out.println(showSelectList.buildHtml());}
  		} catch (IOException e) {
        System.out.println(e.toString());
      } catch (IllegalArgumentException e) {
        System.out.println(e.toString());
      } catch (IntrospectionException e) {
        System.out.println(e.toString());
      } catch (NoSuchMethodException e) {
        System.out.println(e.toString());
      } catch (IllegalAccessException e) {
        System.out.println(e.toString());
      } catch (InvocationTargetException e) {
        System.out.println(e.toString());
      }
		return (SKIP_BODY);
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


    
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

    /**
	 * @return
	 */
	public String getOptionNames() {
		return optionNames;
	}

	/**
	 * @return
	 */
	public String getOptionValues() {
		return optionValues;
	}

	/**
	 * @return
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}
	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @param string
	 */
	public void setOptionNames(String string) {
		optionNames = string;
	}

	/**
	 * @param string
	 */
	public void setOptionValues(String string) {
		optionValues = string;
	}

	/**
	 * @param string
	 */
	public void setScript(String string) {
		script = string;
	}

	/**
	 * @return
	 */
	public String getSelectedValue() {
		return selectedValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @param string
	 */
	public void setSelectedValue(String string) {
		selectedValue = string;
	}
	
	public void setDefaultValue(String string) {
		defaultValue = string;
	}

    /**
     * @param list The list to set.
     */
    public void setList(String list) {
        this.list = list;
    }


    /**
     * @param listName The listName to set.
     */
    public void setListName(String listName) {
        this.listName = listName;
    }


    /**
     * @param listValue The listValue to set.
     */
    public void setListValue(String listValue) {
        this.listValue = listValue;
    }


	public String getSelectedDelimiter() {
		return selectedDelimiter;
	}


	public void setSelectedDelimiter(String selectedDelimiter) {
		this.selectedDelimiter = selectedDelimiter;
	}


	public String getSelectedIndex() {
		return selectedIndex;
	}


	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }
    
	
    
}
