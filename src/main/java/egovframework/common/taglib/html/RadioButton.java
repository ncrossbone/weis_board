package egovframework.common.taglib.html;

/**
 * 라디오 버튼 생성 태그
 * 
 * @author seominho
 *
 */
public class RadioButton implements HtmlConstants
{
	private String name;
	private String script;
	private String[] values;
	private String[] texts;
	private String checkedValue;
	private String defaultValue;
	private String space;
	private String[] imgs;
	private String[] bigimgs;
	private String[] ids;

	public RadioButton(String name, String[] values, String script, String checkedValue, String defaultValue, String[] texts, String space, String[] ids)
	{
		this.name = (name != null) ? name : null;
//		this.values = (values != null) ? values : null;
		this.script = (script != null) ? " " + script + " " : "";
		this.checkedValue = (checkedValue != null) ? checkedValue : null;
		this.defaultValue = (checkedValue == null || "".equals(checkedValue.trim())) ? (defaultValue != null ? defaultValue : null) : checkedValue;
//		this.texts = (texts != null) ? texts : ((values != null) ? values : null);
		this.space = (space != null) ? space : "";
//		this.ids = (texts != null) ? ids : null;
		if(values!=null){
			this.values = new String[values.length];
			for (int i=0; i < values.length; i++) this.values[i] = values[i];
		}
		if(texts!=null){
			this.texts = new String[texts.length];
			for (int i=0; i < texts.length; i++) this.texts[i] = texts[i];
		}else if(values!=null){
			this.texts = new String[values.length];
			for (int i=0; i < values.length; i++) this.texts[i] = values[i];
		}
		if(ids!=null){
			this.ids = new String[ids.length];
			for (int i=0; i < ids.length; i++) this.ids[i] = ids[i];
		}
	}

	public RadioButton(String name, String[] values, String script, String checkedValue, String defaultValue, String[] texts, String space, String[] imgs, String[] ids, String[] bigimgs)
	{
		this.name = name;
		this.script = (script != null) ? " " + script + " " : "";
//		this.values = (values != null) ? values : null;
//		this.texts = (texts != null) ? texts : this.values;
//		this.checkedValue = (checkedValue != null) ? checkedValue : null;
//		this.defaultValue = (checkedValue == null || "".equals(checkedValue.trim())) ? defaultValue : checkedValue;
//		this.space = (space == null) ? "" : space;
//		this.imgs = (imgs != null) ? imgs : null;
//		this.bigimgs = (bigimgs != null) ? bigimgs : null;
//		this.ids = (ids != null) ? ids : null;
		if(values!=null){
			this.values = new String[values.length];
			for (int i=0; i < values.length; i++) this.values[i] = values[i];
		}
		if(texts!=null){
			this.texts = new String[texts.length];
			for (int i=0; i < texts.length; i++) this.texts[i] = texts[i];
		}else if(values!=null){
			this.texts = new String[values.length];
			for (int i=0; i < values.length; i++) this.texts[i] = values[i];
		}
		if(imgs!=null){
			this.imgs = new String[imgs.length];
			for (int i=0; i < imgs.length; i++) this.imgs[i] = imgs[i];
		}else this.imgs=null;
		if(bigimgs!=null){
			this.bigimgs = new String[bigimgs.length];
			for (int i=0; i < bigimgs.length; i++) this.bigimgs[i] = bigimgs[i];
		}else this.bigimgs=null;
		if(ids!=null){
			this.ids = new String[ids.length];
			for (int i=0; i < ids.length; i++) this.ids[i] = ids[i];
		}
	}

	public String buildHtml()
	{
		StringBuffer html = new StringBuffer();
		int size = (values != null && texts != null) ? ((values.length == texts.length) ? values.length : 0) : 0;
		for (int i = 0; i < size; i++)
		{
			if (i > 0)
			{
				html.append(space);
			}
			html.append("<input type=\"radio\" name=\"").append(name).append("\"");
			if (ids[i] != null)
			{
				html.append(" id=\"").append(ids[i]).append("\"");
			}
			if (values[i] != null) html.append(" value=\"").append(values[i]).append("\"");
			html.append(script);
			if (values[i] != null) if (values[i].equalsIgnoreCase(checkedValue) || values[i].equalsIgnoreCase(defaultValue))
			{
				html.append(" checked=\"checked\"");
			}
			if (ids[i] != null && texts[i] != null)
			{
				html.append(" /> <label class='ra_chk' for=\"").append(ids[i]).append("\">").append(texts[i]).append("</label> \n");
			}
			else
			{
				if (texts[i] != null) html.append(" />").append(texts[i]).append("\n");
			}
		}
		return html.toString();
	}

	public String buildHtml2()
	{
		StringBuffer html = new StringBuffer();
		int size = (values != null && texts != null) ? ((values.length == texts.length) ? values.length : 0) : 0;
		for (int i = 0; i < size; i++)
		{
			String imgs_t = "";
			String bigimgs_t = "";
			if (imgs != null) {imgs_t = imgs[i];} else{ break;}
			if (bigimgs != null) {bigimgs_t = bigimgs[i];} else{ break;}
			html.append("<li><a href='#'><img src=\"/images/app/")
				.append(imgs_t)
				.append("\" alt='미리보기' onclick='preview(\"" + bigimgs_t + "\");return false;' /></a><br />");
			html.append("<input type=\"radio\" name=\"").append(name).append("\"");
			if (values[i] != null) html.append(" value=\"").append(values[i]).append("\"");
			html.append(script);
			if (values[i] != null){
				if (values[i].equalsIgnoreCase(checkedValue) || values[i].equalsIgnoreCase(defaultValue))
				{
					html.append(" checked");
				}
			}
			if (texts[i] != null) html.append(">").append(texts[i]);
			html.append("</li>");
		}
		return html.toString();
	}
}