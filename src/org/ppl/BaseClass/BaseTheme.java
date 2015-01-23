package org.ppl.BaseClass;

import java.util.List;

import com.lib.common.Footer;
import com.lib.common.Header;

public abstract class BaseTheme extends BaseView implements BaseThemeInterface {	
	protected boolean isAutoHtml = true;	
	private boolean ajax=false;
	private String header_html="";
	private String footer_html="";
	
	public BaseTheme() {
		// TODO Auto-generated constructor stub
		//echo(super.stdClass+":baseTheme");
	}
	
	public abstract void Show();
	
	public void setHtml(String Con) {
		this.html = Con;
	}
	
	public String getHtml() {
				
		if(isAutoHtml){
			common();
		}
		
		return header_html+html+footer_html;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	private void common() {
		Header header = new Header();
		header.filter();
		header_html = header.getHtml();
		
		Footer footer = new Footer();
		footer.filter();
		footer_html = footer.getHtml();
	}
}
