package org.ppl.core;

import org.ppl.common.SessionAction;
import org.ppl.db.DBSQL;
import org.ppl.etc.Config;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

public class PObject extends DBSQL {
	protected String stdClass = null;
	private String BindName = null;
	protected SessionAction SessAct = SessionAction.getInstance();

	public void echo(Object o) {
		System.out.println(o);
	}

	protected void GetSubClassName(String subClassname) {
		stdClass = subClassname;
	}

	public String SliceName(String k) {
		String[] name = k.split("\\.");
		String cName = name[name.length - 1];
		return cName;
	}

	public String getBindName() {
		if (BindName == null)
			BindName = stdClass;
		return BindName;
	}

	public void setBindName(String bindName) {
		BindName = bindName;
	}

	public String getSalt() {
		TimeClass tc = TimeClass.getInstance();
		Encrypt ec = Encrypt.getInstance();

		String salt = ec.MD5(String.valueOf(tc.time()));
		Config mConfig = new Config(globale_config.Config);

		SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt), salt);

		return salt;
	}

	public boolean checkSalt(String salt) {

		Config mConfig = new Config(globale_config.Config);
		Encrypt ec = Encrypt.getInstance();
		TimeClass tc = TimeClass.getInstance();
		String new_salt = ec.MD5(String.valueOf(tc.time()));

		String sess_salt = SessAct.GetSession(mConfig
				.GetValue(globale_config.SessSalt));
		if (sess_salt == null)
			return false;
		if (sess_salt.equals(salt)) {
			SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt),
					new_salt);
			return true;
		}

		return false;
	}

}
