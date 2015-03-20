package org.ppl.BaseClass;

public abstract class BaseModule extends BaseView {

	/**
	 * @since module enter here
	 */
	public abstract void filter();
	
	/**
	 * @since Conversion html
	 * @return
	 */
	public String getHtml() {
	
		return html;
	}
	
	/**
	 * @since buile html
	 */
	@Override
	public void View() {
		// TODO Auto-generated method stub
		setRoot("BaseModule", "1");
		super.View();
	}
}
