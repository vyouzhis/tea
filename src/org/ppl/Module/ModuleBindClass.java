package org.ppl.Module;

import java.util.ArrayList;
import java.util.List;

import org.lib.About;
import org.lib.article;
import org.lib.home;
import org.lib.jsonapi;
import org.ppl.core.PObject;

import com.lang._article;
import com.lang._common;
import com.lang.manager.setting._admin_permission_list;
import com.lang.manager.setting._admin_permission_setting;
import com.lang.manager.user._user_list;
import com.lib.common.Footer;
import com.lib.common.Header;
import com.lib.manager.admin_index;
import com.lib.manager.admin_login;
import com.lib.manager.admin_login_action;
import com.lib.manager.setting.admin_permission_list;
import com.lib.manager.setting.admin_permission_setting;
import com.lib.manager.setting.setting_index;
import com.lib.manager.user.user_index;
import com.lib.manager.user.user_list;

public class ModuleBindClass extends PObject{
	
	protected List<Object> LangList = null;
	protected List<Object> ModuleList = null;
	protected List<Object> ThemeList = null;
	protected List<Object> PermList = null;
	protected List<Object> ManagerList = null;
	
	public ModuleBindClass() {
		// TODO Auto-generated constructor stub
		LangList = new ArrayList<Object>();
		// lang
		LangList.add(_article.class);
		LangList.add(_common.class);
		LangList.add(_admin_permission_list.class);
		LangList.add(_admin_permission_setting.class);
		LangList.add(_user_list.class);
		
		ThemeList = new ArrayList<Object>();
		//lib
		ThemeList.add(About.class);
		ThemeList.add(article.class);
		ThemeList.add(home.class);
		ThemeList.add(jsonapi.class);
		
		ModuleList = new ArrayList<Object>();
		//common
		ModuleList.add(Footer.class);
		ModuleList.add(Header.class);
		
		//Permission
		PermList = new ArrayList<Object>();
		PermList.add(admin_index.class);
		PermList.add(admin_login.class);
		PermList.add(admin_login_action.class);
		
		PermList.add(admin_permission_list.class);
		PermList.add(admin_permission_setting.class);
		
		PermList.add(user_list.class);
		
		//manager index
		ManagerList = new ArrayList<Object>();
		ManagerList.add(setting_index.class);
		ManagerList.add(user_index.class);
	}
	
	
}
