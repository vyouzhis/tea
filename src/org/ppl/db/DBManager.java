package org.ppl.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
	public static DBManager dataSource = null;
	private static Connection Con = null;
	
	public static DBManager getInstance() {
		if (dataSource == null) {
			dataSource = new DBManager();
		}

		return dataSource;
	}
	
	public Connection getCon() {
		return Con;
	}
	
	public void init() {
		HikariConnectionPool hcp = HikariConnectionPool.getInstance();
		Con = hcp.GetCon();
		//System.out.println("dbmanager init");
	}
	
	public void free() {
		if (Con != null) {
			try {
				Con.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HikariConnectionPool hcp = HikariConnectionPool.getInstance();
			hcp.free(Con);
			Con = null;
			//System.out.println("dbmanager free");
		}
//		else{
//		
//			System.out.println("dbmanager free error");
//		}
	}
}
