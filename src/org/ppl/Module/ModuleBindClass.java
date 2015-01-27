package org.ppl.Module;

import java.util.ArrayList;
import java.util.List;

import org.ppl.core.PObject;

import com.lang._article;
import com.lang._common;
import com.lang.manager.setting._admin_permission_list;
import com.lang.manager.setting._admin_permission_setting;
import com.lang.manager.user._my_profile;
import com.lang.manager.user._user_profile;
import com.lang.manager.user._user_list;
import com.lib.common.Footer;
import com.lib.common.Header;
import com.lib.common.SurfaceHeader;
import com.lib.common.SurfaceFooter;
import com.lib.icore.Register;
import com.lib.icore.Register_ok;
import com.lib.icore.icore;
import com.lib.icore.icore_login;
import com.lib.manager.admin_index;
import com.lib.manager.admin_login;
import com.lib.manager.admin_login_action;
import com.lib.manager.setting.admin_permission_list;
import com.lib.manager.setting.admin_permission_setting;
import com.lib.manager.setting.setting_index;
import com.lib.manager.user.my_profile;
import com.lib.manager.user.user_profile;
import com.lib.manager.user.user_index;
import com.lib.manager.user.user_list;
import com.lib.surface.About;
import com.lib.surface.article;
import com.lib.surface.home;
import com.lib.surface.jsonapi;

public class ModuleBindClass extends PObject{
	
	protected List<Object> LangList = null;
	protected List<Object> ModuleList = null;
	protected List<Object> SurfaceList = null;
	protected List<Object> iCoreList = null;
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
		LangList.add(_user_profile.class);
		LangList.add(_my_profile.class);
		
		/**
		 * surface start
		 */
		SurfaceList = new ArrayList<Object>();
		//lib
		SurfaceList.add(About.class);
		SurfaceList.add(article.class);
		SurfaceList.add(home.class);
		SurfaceList.add(jsonapi.class);
		SurfaceList.add(Register.class);
		SurfaceList.add(Register_ok.class);
		SurfaceList.add(icore_login.class);
		/**
		 * surface end
		 */
		
		/**
		 * icore start
		 */
		iCoreList = new ArrayList<Object>();
		iCoreList.add(icore.class);
		/**
		 * icore end
		 */
		
		/**
		 *  common start
		 */
		ModuleList = new ArrayList<Object>();
		//common
		ModuleList.add(Footer.class);
		ModuleList.add(Header.class);
		ModuleList.add(SurfaceFooter.class);
		ModuleList.add(SurfaceHeader.class);
		
		/**
		 *  common end
		 */
		
		/*
		 * manager start
		 */
		//Permission
		PermList = new ArrayList<Object>();
		PermList.add(admin_index.class);
		PermList.add(admin_login.class);
		PermList.add(admin_login_action.class);
		PermList.add(my_profile.class);
		
		PermList.add(admin_permission_list.class);
		PermList.add(admin_permission_setting.class);
		
		PermList.add(user_list.class);
		PermList.add(user_profile.class);
		
		//manager index
		ManagerList = new ArrayList<Object>();
		ManagerList.add(setting_index.class);
		ManagerList.add(user_index.class);
		
		/*
		 * manager end
		 */
	}
	
	
}
