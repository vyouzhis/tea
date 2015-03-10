package org.ppl.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.core.PObject;

public class DBSQL extends PObject {

	public static DBSQL dataSource = null;
	private Connection Con = null;
	private Statement stmt = null;
	protected String DB_NAME = mConfig.GetValue("db.name");
	protected String DB_PRE = mConfig.GetValue("db.rule.ext");
	protected String DB_WEB_PRE = mConfig.GetValue("db.web.ext");
	
	public DBSQL() {
		
	}

	public void SetCon() {
		DBManager hcp = DBManager.getInstance();
		Con = hcp.getCon();
	}
	
	public void ThreadSetCon() {
		HikariConnectionPool hcp = HikariConnectionPool.getInstance();				
		Con = hcp.GetCon();		
	}

	public void end() {
		try {
			stmt.close();
			// c.commit();
			// c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			echo("dbmanager free");
		}else{
			echo("dbmanager free error");
		}
	}
	
	public void rollback() {
		
		try { 
			if (Con != null) {
				Con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> map(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		Object value = null;
		try {
			if (rs != null) {
				ResultSetMetaData meta = rs.getMetaData();
				int numColumns = meta.getColumnCount();
				while (rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for (int i = 1; i <= numColumns; ++i) {
						String name = meta.getColumnName(i);

						if (meta.getColumnTypeName(i).equals("TINYINT")) {
							value = rs.getInt(i);

						} else {
							value = rs.getObject(i);
						}

						row.put(name, value);
					}
					results.add(row);
				}
			}
		} finally {
			// close(rs);
		}

		return results;
	}

	public List<Map<String, Object>> query(String sql) throws SQLException {
		List<Map<String, Object>> results = null;
			
		if (Con == null){
			echo("con sql:"+sql);
			return null;
		}
		ResultSet rs = null;
		stmt = Con.createStatement();
		rs = stmt.executeQuery(sql);
		results = map(rs);
		rs.close();
		stmt.close();
				
		return results;
	}

	public List<Map<String, Object>> FetchAll(String sql) throws SQLException {
		return query(sql);
	}

	public Map<String, Object> FetchOne(String sql) {
		Map<String, Object> results = null;
		List<Map<String, Object>> fetlist = null;

		try {
			fetlist = query(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fetlist != null && fetlist.size() > 0) {
			results = fetlist.get(0);
		}

		return results;
	}

	public long update(String sql) throws SQLException {
		long numRowsUpdated = 0;
				
		if (Con == null) {
			echo("con sql:"+sql);
			return -1;
		}
		stmt = Con.createStatement();
		numRowsUpdated = stmt.executeUpdate(sql,
				Statement.RETURN_GENERATED_KEYS);

		return numRowsUpdated;
	}
	
	public void CommitDB() {
		try {
			Con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
