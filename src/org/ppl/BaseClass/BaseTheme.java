package org.ppl.BaseClass;

import com.lib.common.Footer;
import com.lib.common.Header;
import com.lib.common.SufaceHeader;
import com.lib.common.SurfaceFooter;

public abstract class BaseTheme extends BaseView implements BaseThemeInterface {
	protected boolean isAutoHtml = true;
	private boolean ajax = false;
	protected String header_html = "";
	protected String footer_html = "";

	public BaseTheme() {
		// TODO Auto-generated constructor stub
		// echo(super.stdClass+":baseTheme");
	}

	public abstract void Show();

	public void setHtml(String Con) {
		this.html = Con;
	}

	public String getHtml() {

		if (isAutoHtml) {
			common();
		}

		return header_html + html + footer_html;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	private void common() {
		
		if (header_html.length() == 0) {
			SufaceHeader header = new SufaceHeader();
			header.filter();
			header_html = header.getHtml();
		}
		if (footer_html.length() == 0) {
			SurfaceFooter footer = new SurfaceFooter();
			footer.filter();
			footer_html = footer.getHtml();
		}
	}
}
