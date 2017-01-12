package resource.customeraccountservice;

import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Set;

//import com.cloudant.client.api.ClientBuilder;
//import com.cloudant.client.api.CloudantClient;
//import com.cloudant.client.api.Database;
//import com.cloudant.client.org.lightcouch.CouchDbException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.UnknownHostException;
import config.Configuration;
import redis.clients.jedis.Jedis;


public class RedisClientMgr {

	//private static Mongo mongo = null;
	//private static MongoClient mongoClient = null;
	//private static MongoDatabase db = null;
	
	private Jedis db = null;

	

	public RedisClientMgr() {
		
	}
	
	private void initClient() {
		
	}

	public Jedis getDB() {
		 if (db == null) 
		 {
			 db = new Jedis(config.Configuration.getPropertyValue("redisDB"));
		 }
		
		
		return db;
	}

	
}
