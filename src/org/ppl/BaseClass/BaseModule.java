package org.ppl.BaseClass;

public abstract class BaseModule extends BaseView {

	public abstract void filter();
	
	public String getHtml() {
		return html;
	}
	@Override
	public void View() {
		// TODO Auto-generated method stub
		setRoot("static_uri", porg.getContext_Path());
		super.View();
	}
}
