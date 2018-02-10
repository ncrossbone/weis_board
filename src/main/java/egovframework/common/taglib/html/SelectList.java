package egovframework.common.taglib.html;

import egovframework.common.util.StringUtil;

/**
 * 셀렉트 박스 리스트  생성 태그
 * @author seominho
 *
 */
public class SelectList implements HtmlConstants {
	
	private String name;
	private String id;
	private String title;
	private String script;
	private String[] optionValues;
	private String[] optionNames;
	
	private String selectedValue;
	
	private String defaultValue;
	
	//private Logger logger = Logger.getLogger(this);

	
	public SelectList(String name, String script, String[] optionValues, String[] optionNames, String selectedValue, String defaultValue, String title, String id) {
		this.name = name;
		if(id == null){
		  this.id = name;
		}else{
		  this.id = id;  
		}
		
		this.title = title;
		this.script = script;
    this.selectedValue = (selectedValue != null) ? selectedValue.trim() : selectedValue;
    this.defaultValue = (defaultValue != null) ? defaultValue.trim() : defaultValue;
    if (optionValues != null)
    {
      this.optionValues = new String[optionValues.length];
      for (int i = 0; i < optionValues.length; i++)
      {
        this.optionValues[i] = optionValues[i];
      }
    }
    if (optionNames != null)
    {
      this.optionNames = new String[optionNames.length];
      for (int i = 0; i < optionNames.length; i++)
      {
        this.optionNames[i] = optionNames[i];
      }
    }
	}


	public String buildHtml() {
		StringBuffer html = new StringBuffer();
		html.append("<select name=\"").append(name).append("\"").append(" id=\"").append(id).append("\"").append(" title=\"").append(title).append("\"");
		if (script != null) {
			html.append(" ");
			html.append(script);	
		}
		html.append(">\n");
          if (optionValues != null) {
              int size = (optionValues.length == optionNames.length) ? optionValues.length : 0;
              for (int i = 0; i < size ; i++) {
                  html.append("    <option value=\"").append(optionValues[i]).append("\"");
                  
                  if(!StringUtil.isEmpty(selectedValue)){
                    if (optionValues[i].equalsIgnoreCase(selectedValue) || optionNames[i].trim().equalsIgnoreCase(selectedValue)) {
                        html.append(" selected='selected'");
                    }
                  }else{
                    if (optionValues[i].equalsIgnoreCase(defaultValue) || optionNames[i].trim().equalsIgnoreCase(defaultValue)) {
                        html.append(" selected='selected'");
                    }
                  }
                  html.append(">");
                  html.append(optionNames[i]).append("</option>\n");
              }                
          }
		html.append("</select>");
		
		
		return html.toString();
	}
}
