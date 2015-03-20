package org.ppl.BaseClass;

import org.ppl.db.DBSQL;
import org.ppl.db.HikariConnectionPool;

public abstract class BaseCronThread extends DBSQL{
	public BaseCronThread() {
		// TODO Auto-generated constructor stub
		HikariConnectionPool hcp = HikariConnectionPool.getInstance();
		hcp.GetCon();
	}
	/**
	 * @since loop minute
	 * @return
	 */
	public abstract int minute();
	/**
	 * @since loop hour
	 * @return
	 */
	public abstract int hour();
	/**
	 * @since loop day
	 * @return
	 */
	public abstract int day();
	/**
	 * @since code 	entrance
	 */
	public abstract void Run();
	/**
	 * @since cron stop ?
	 * @return
	 */
	public abstract boolean isStop();
}
