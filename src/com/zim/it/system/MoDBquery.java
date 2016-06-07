//Get coordinates from document like this "address" : { "building" : "461" , "coord" : [ -74.138492 , 40.631136] , "street" : "Port Richmond Ave" , "zipcode" : "10302"}
package com.zim.it.system;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MoDBquery {

	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient ("HFAVWRMONQA1", 27017);
		//MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("HFAVWRMONQA1", 27017), new ServerAddress("HFAVWRMONQA1", 27011), new ServerAddress("HFAVWRMONQA1", 27012)));
		mongoClient.setWriteConcern(WriteConcern.JOURNALED);
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("restaurants");
		
		DBObject query = new BasicDBObject();
		//query.put("address.street", "Port Richmond Ave");
		query.put("name", "Mandarin House");
		//query.put("address.building", "730");
		
		DBCursor cursor = coll.find(query);
		
		//BasicDBObject field = new BasicDBObject();
		//field.put("name", true);
		//field.put("address.coord", true);
		//field.put("_id", false);
		//field.put("cuisine", true);
		
		try {
			while(cursor.hasNext()) {
			
				BasicBSONObject addressList = (BasicBSONObject) cursor.next().get("address");
				BasicDBList aL = (BasicDBList) addressList.get("coord");
				
				double langitude = (double) aL.get(0);
				double longitude = (double) aL.get(1);
				
				System.out.println(langitude + " " + longitude);
				
			}
		} catch (NullPointerException e) {

				System.err.println("NullPointerException: " + e.getMessage());

		} finally {
				cursor.close();
		}
	}
}