package org.ppl.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import org.ppl.core.PObject;
import org.ppl.etc.globale_config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionPool extends PObject {
	static HikariConnectionPool source;
	public HikariDataSource ds = null;

	private LinkedList<Connection> ConList = null;

	public static HikariConnectionPool getInstance() {
		if (source == null) {
			source = new HikariConnectionPool();
		}

		return source;
	}

	public HikariConnectionPool() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HikariConfig config = new HikariConfig();
		// echo(mConfig.GetValue("database.url"));
		config.setJdbcUrl(myConfig.GetValue("database.url"));
		config.setUsername(myConfig.GetValue("database.username"));
		config.setPassword(myConfig.GetValue("database.password"));

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");

		ds = new HikariDataSource(config);
		ds.setMaximumPoolSize(myConfig.GetInt("database.MaximumPoolSize"));

		ConList = new LinkedList<>();
		Connect();
	}

	private void Connect() {

		try {
			Connection con;
			for (int i = 0; i < ds.getMaximumPoolSize(); i++) {
				con = ds.getConnection();
				con.setAutoCommit(false);

				ConList.add(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
			echo(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public void GetCon() {

		synchronized (ConList) {
			while (ConList.isEmpty()) {
				echo("Conlist +++++++++ size empty");
				try {
					ConList.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				echo("Conlist ======= size:" + ConList.size());
			}
			long tid = myThreadId();
			echo("tid:" + tid);

			globale_config.GDB.put(tid, ConList.pop());

		}
	
	}

	public void free() {
		synchronized (ConList) {
			long tid = myThreadId();
			echo("free tid:" + tid);
			Connection con = globale_config.GDB.get(tid);
			if (con != null) {
				
				try {
					con.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ConList.add(con);
				globale_config.GDB.put(tid, null);
			}
			ConList.notify();
			echo("free ConList size:" + ConList.size());

		}
	}
}
