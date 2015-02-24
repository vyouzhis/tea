package org.ppl.BaseClass;

import org.ppl.db.DBSQL;

public abstract class BaseRapidThread extends DBSQL {		
	public BaseRapidThread() {
		// TODO Auto-generated constructor stub
		ThreadSetCon();
	}
	
	public abstract void Run();
	public abstract boolean isRun();
	public abstract boolean Stop();
	public abstract void mailbox(Object o);
		
}
