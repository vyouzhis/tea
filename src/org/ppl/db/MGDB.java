package org.ppl.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ppl.core.PObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.util.JSON;

public class MGDB extends PObject {
	public static MGDB dataSource = null;
	private MongoClient mongoClient = null;
	private DB db = null;

	public String eq = "$eq";
	public String gt = "$gt"; // >
	public String gte = "$gte"; // >=
	public String in = "$in"; // in
	public String lt = "$lt"; // <
	public String lte = "$lte"; // <=
	public String ne = "$ne"; // !=
	public String nin = "$nin"; // !in
	public String or = "$or"; // ||
	public String and = "$and"; // &&
	public String not = "$not"; // !
	public String nor = "$nor"; // nor
	public String exists = "$exists"; //
	public String type = "$type"; //
	public String mod = "$mod"; //
	public String regex = "$regex"; //
	public String text = "$text"; //
	public String where = "$where"; //
	
	public String cluster = "$cluster"; // 

	// Geospatial
	/*
	 * getwithin
	 */
	public String geoWithin = "$geoWithin";
	public String geoIntersects = "$geoIntersects";
	public String near = "$near";
	public String nearSphere = "$nearSphere";

	// Array
	public String all = "$all";
	public String elemMatch = "$elemMatch";
	public String size = "$size";
	// Projection Operators
	// private String elemMatch="$elemMatch";
	public String meta = "$meta";
	public String slice = "$slice";

	private int DBOffset = 0;
	private int DBLimit = 30;

//	private int DESC = 1;
//	private int ASC = -1;

	private DBCollection DBLink = null;
	private BasicDBObject DBColumn = null;
	private BasicDBObject DBWhere = null;
	private BasicDBObject DBSort = null;
	private DBCursor dbCursor = null;	
	
	private String ErrorMsg = "";

	public MGDB() {
		
		//Mongo m;
		try {
			MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder()
	                .acceptableLatencyDifference(10000)	               
	                .connectionsPerHost(1000)
	                .connectTimeout(15000)
	                .maxWaitTime(30000)
	                .socketTimeout(60000)
	                .threadsAllowedToBlockForConnectionMultiplier(5000);
	                
			MongoClientOptions options = optionsBuilder.build();
			mongoClient = new MongoClient(mgConfig.GetValue("database.host"),
					options);
			
			db = mongoClient.getDB(mgConfig.GetValue("database.dbname"));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean SetCollection(String col) {
		DBLink = db.getCollection(col);
		if (DBLink == null) {
			return false;
		}

		return true;
	}

	public Set<String> CollectionList() {
		Set<String> set = db.getCollectionNames();

		return set;
	}

	public String CollectionIndex(int i) {		
		Set<String> r = CollectionList();
		if(i>0 && i<r.size())
			return r.toArray()[i].toString();
		return null;
	}

	
	public void setLimit(int dInt) {
		DBLimit = dInt;
	}
	
	public int GetLimit() {
		return DBLimit;
	}
	
	public void JsonWhere(String json) {
		DBWhere = (BasicDBObject) JSON
				.parse(json);
	}
	
	public void JsonColumn(String json) {
		DBColumn = (BasicDBObject) JSON
				.parse(json);
	}

	public void JsonSort(String json) {
		DBSort = (BasicDBObject) JSON.parse(json);
	}
	
	public Integer FetchCont() {
		//System.out.println("FetchCont DBWhere:"+DBWhere);
		Integer count =  DBLink.find(DBWhere, DBColumn).count();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> Distinct(String field) {
		List<Object> list=DBLink.distinct(field);
		return list;
	}
	
	public Boolean FetchList() {
//		echo("DBWhere:"+DBWhere);
//		echo("DBColumn:"+DBColumn);

		dbCursor = DBLink.find(DBWhere, DBColumn)
				.sort(DBSort)
				.limit(DBLimit)
				.skip(DBOffset);
		if (dbCursor == null) {
			return false;
		}

		try {
			if(dbCursor.size()==0) {			
				return false;			
			}
		} catch (Exception e) {
			// TODO: handle exception			
			ErrorMsg = e.getMessage().toString();
			return false;
		}
		
		return true;
	}

	
	public List<Map<String, Object>> GetValue() {		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		while (dbCursor.hasNext()) {
			DBObject obj = dbCursor.next();
			Map<String, Object> ColMap = new HashMap<String, Object>();
			
			for (String key:obj.keySet()) {	
				ColMap.put(key, obj.get(key));
			}
									
			list.add(ColMap);
		}

		return list;
	}
	
	public List<String> GetJsonValue() {
		List<String> list = new ArrayList<>();

		while (dbCursor.hasNext()) {
			DBObject obj = dbCursor.next();					
			list.add(JSON.serialize(obj));
		}

		return list;
	}
	
	public void ClearWhere() {
		if (DBWhere != null) {
			DBWhere.clear();
		}
	}
	
	
	public void DBEnd() {
		if (DBColumn != null) {
			DBColumn.clear();
		}
		DBLimit = 30;
		if (DBSort != null) {
			DBSort.clear();
		}
		
		ClearWhere();

	}

	public int getDBOffset() {
		return DBOffset;
	}

	public void setDBOffset(int dBOffset) {
		DBOffset = dBOffset;
	}
	
	public Boolean WhereIsNull() {
		if(DBWhere == null) return true;
		else return false;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	
}
