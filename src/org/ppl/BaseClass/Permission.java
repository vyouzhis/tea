package org.ppl.BaseClass;

import java.util.List;
import java.util.Map;

import org.ppl.common.ShowMessage;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.lib.common.Header;
import com.lib.common.Menu;
import com.lib.common.Navbar;

public class Permission extends BaseTheme implements BasePerminterface {
		
	protected int Init(){
		Config mConfig = new Config(globale_config.Config);
		int uid = aclGetUid();
		if(uid==0 && !stdClass.equals(mConfig.GetValue("login_module"))){
			UrlClassList ucl = UrlClassList.getInstance();
			String bad_url = ucl.BuildUrl("admin_login", "");
			
			ShowMessage ms = ShowMessage.getInstance();				
			String res = ms.SetMsg(bad_url, _CLang("error_login"), 3000);
			
			super.setHtml(res);
			return -1;
		}
		return 0;
	}
		
	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub
		
	}  

	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
	}
	
	
	public String navbar() {
		Navbar nb = new Navbar();		
		nb.filter();		
		String html = nb.getHtml();
		
		return html;
	}

	public String menu() {
		Menu m = new Menu();		
		m.filter();
		String html =m.getHtml();
		
		return html;
	}
}
