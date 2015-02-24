package org.ppl.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.types.ObjectId;
import org.ppl.core.PObject;
import org.ppl.etc.Config;
import org.ppl.etc.globale_config;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

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

	private int DESC = 1;
	private int ASC = -1;

	private DBCollection DBLink = null;
	private BasicDBObject DBColumn = null;
	private BasicDBObject DBWhere = null;
	private BasicDBObject DBArray = null;
	private List<Object>  DBAList = null;
	private BasicDBObject DBSort = null;
	private DBCursor DBCursor = null;	
	private List<String> ColList = null;

	@SuppressWarnings("deprecation")
	public MGDB() {
		
		//Mongo m;
		try {
			MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder()
	                .acceptableLatencyDifference(10000)
	                .writeConcern(WriteConcern.REPLICA_ACKNOWLEDGED)
	                .readPreference(ReadPreference.secondaryPreferred())
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
		/*
		 * for (String string:set) { DBCollection coll =
		 * db.getCollection(string); System.out.println(string); }
		 */
		return set;
	}

	public String CollectionIndex(int i) {		
		Set<String> r = CollectionList();
		if(i>0 && i<r.size())
			return r.toArray()[i].toString();
		return null;
	}
	
	public void SetColumn(String Column) {
		if (DBColumn == null)
			DBColumn = new BasicDBObject();
		if (ColList == null)
			ColList = new ArrayList<String>();
		DBColumn.append(Column, 1);
		ColList.add(Column);
	}

	public void EditWhere(HashMap<String, Object> wMap) {
		if (DBWhere == null)
			DBWhere = new BasicDBObject();

		if (wMap == null)
			return;
		for (Entry<String, Object> entry : wMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			DBWhere.append(key, value);
		}
		
	}
	
	public void norArray(String Col, String Operator, Object key) {
		if (DBArray == null){
			DBArray = new BasicDBObject();
			DBAList = new ArrayList<Object>();
		}
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put(Operator, key);
				
		DBArray.append(Col, conditions);
		DBAList.add(DBArray);
	}
	
	public void norWhere() {
		if (DBWhere == null)
			DBWhere = new BasicDBObject();
		
		DBWhere.append(nor, DBAList);
	}
	
	public void SetSort(List<String> sList, int i) {
		if(DBSort == null)
			DBSort = new BasicDBObject();
		
		for (String pro : sList) {		
			DBSort.append(pro, i);
		}
	}

	public void setLimit(int dInt) {
		DBLimit = dInt;
	}
	
	public int GetLimit() {
		return DBLimit;
	}

	public Integer FetchCont() {
		//System.out.println("FetchCont DBWhere:"+DBWhere);
		Integer count =  DBLink.find(DBWhere, DBColumn).count();
		return count;
	}
	
	public Boolean FetchList() {
		//System.out.println("DBWhere:"+DBWhere);
		//System.out.println("DBColumn:"+DBColumn);
		// DBCursor = DBLink.find(DBWhere, DBColumn)
		// .limit(DBLimit);
		DBCursor = DBLink.find(DBWhere, DBColumn)
				.sort(DBSort)
				.limit(DBLimit)
				.skip(DBOffset);
		//System.out.println(DBCursor);
		if (DBCursor == null) {
			return false;
		}

		return true;
	}

	public List<Map<String, Object>> GetValue() {
		String key = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		if (ColList == null && ColList.size() == 0)
			return null;

		while (DBCursor.hasNext()) {
			DBObject obj = DBCursor.next();
			Map<String, Object> ColMap = new HashMap<String, Object>();
			for (int m = 0; m < ColList.size(); m++) {
				key = ColList.get(m);
				Object val = GetColValue(obj, key);
				ColMap.put(key, val);
			}
			list.add(ColMap);
		}

		return list;
	}

	private Object GetColValue(DBObject obj, String key) {
		DBObject pobj = obj;
		Set<String> keys = null;
		String[] parts = key.split("\\.");

		for (int j = 0; j < parts.length; j++) {
			keys = pobj.keySet();
			if (keys.size() == 0)
				break;

			if (keys.contains(parts[j])) {
				if (j == parts.length - 1) {
					return pobj.get(parts[j]);
				}
				pobj = (DBObject) pobj.get(parts[j]);
			}
		}

		return null;
	}

	public void SetWhere(String Col, String Operator, Object key) {
		HashMap<String, Object> where = new HashMap<String, Object>();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put(Operator, key);
				
		where.put(Col, conditions);
		EditWhere(where);
	}
	
	public void SetWhere(String Col, Map<String, Object> conditions ) {
		HashMap<String, Object> where = new HashMap<String, Object>();				
		where.put(Col, conditions);
		EditWhere(where);
	}
	
	public void ClearWhere() {
		if (DBWhere != null) {
			DBWhere.clear();
		}
	}
	
	public void Print_r() {
		System.out.println(DBWhere);
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
		
		if (ColList != null) {
			ColList.clear();
		}
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
	
}
