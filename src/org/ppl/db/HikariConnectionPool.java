package org.ppl.db;

import java.sql.Connection;
import java.util.LinkedList;

import org.ppl.etc.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionPool {
	static HikariConnectionPool source;
	public HikariDataSource ds = null;
	private Config mConfig = null;
	private LinkedList<Connection> Con = null;
	
	public static HikariConnectionPool getInstance() {
		if (source == null) {
			source = new HikariConnectionPool();
		}

		return source;
	}

	public HikariConnectionPool() {
		// TODO Auto-generated constructor stub
		mConfig = new Config("properties/mysql.properties");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HikariConfig config = new HikariConfig();
		// System.out.println(mConfig.GetValue("database.url"));
		config.setJdbcUrl(mConfig.GetValue("database.url"));
		config.setUsername(mConfig.GetValue("database.username"));
		config.setPassword(mConfig.GetValue("database.password"));

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");

		ds = new HikariDataSource(config);
		ds.setMaximumPoolSize(mConfig.GetInt("database.MaximumPoolSize"));
		
		Con = new LinkedList<Connection>();
		Connect();
	}

	private void Connect() {

		try {
			Connection con;
			for (int i = 0; i < ds.getMaximumPoolSize(); i++) {
				con = ds.getConnection();
				con.setAutoCommit(false);
				Con.push(con);
			}
			//System.out.println("init Con size:"+Con.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public Connection GetCon() {
		Connection con = null;
		synchronized (Con) {
			//System.out.println("Con size:"+Con.size());
			if(Con.size()>0){
				con = Con.pop();
			}else{
				System.out.println("MaximumPoolSize is limit");
			}
		}
		return con;
	}

	public void free(Connection con) {
		//System.out.println("free con");
		synchronized (Con) {
			Con.add(con);
		}
	}
}
