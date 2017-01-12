package resource.customeraccountservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Part;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import config.Configuration;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import redis.clients.jedis.Jedis;

@Path("/redisprofile")
public class RedisProfileResource {

	public RedisProfileResource() {
		// TODO Auto-generated constructor stub
	}
	
	private Jedis getDB() {
		return (new RedisClientMgr()).getDB();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("id") String id) throws Exception {
		System.out.println("quyen oi sai hoai nua");
		Jedis db = null;
		try {
			db = getDB();
		} catch (Exception re) {
			re.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		
		JsonArray jsonArray = new JsonArray();
		
		if (id == null) {
			try 
			{
				// get all the profile present in database
				Set<String> keys = db.keys("*@*:*");
				
				Iterator<String> it = keys.iterator();
				
				while (it.hasNext()) 
				{
					  //System.out.println(doc.toJson());
					String key = it.next();
					Map<String, String> p = db.hgetAll(key);
					JsonObject jsonObject = toJsonObject(p);
					jsonArray.add(jsonObject);
				}
				
			} 
			catch (Exception dnfe) 
			{
				System.out.println("Exception thrown : " + dnfe.getMessage());
			}

			//resultObject.addProperty("id", "all");
			//resultObject.add("body", jsonArray);

			//return Response.ok(resultObject.toString()).build();
			return Response.ok(jsonArray.toString()).build();
		}
		
		// check if document exists
		Map<String, String> p = db.hgetAll(id);
        
		
		if (p != null) 
		{
			System.out.println("quyen oi sai hoai"+p.get("photo"));
			JsonObject jsonObject = toJsonObject(p);
			return Response.ok(jsonObject.toString()).build();
		} 
		else 
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("key") String key) throws Exception {
		System.out.println("quyen oi search");
		
		
		
		JsonArray jsonArray = new JsonArray();
		
		if (key != null) {
			try 
			{
				//"https://qvtran:abc123@sl-us-dal-9-portal3.dblayer.com:15642/"
				JestClientFactory factory = new JestClientFactory();
				factory.setHttpClientConfig(new HttpClientConfig
				                        .Builder(Configuration.getPropertyValue("elasticSearchEndPoint"))
				                        .multiThreaded(true)
				                        .build());
				JestClient client = factory.getObject();
				 
				if (client!=null)
				{
					System.out.println("Connected to Elastic Search");
				}
				else
				{
					System.out.println("Failed to connect to Elastic Search");
				}
				
				// get all the profile present in database
				String operatorString = "\"operator\":   \"and\"";
				String query = "{"+
						  		"\"query\": {"+
						  					"\"multi_match\" : {"+
						  										"\"query\":      \""+key+"\","+
						  										"\"type\":       \"cross_fields\","+
						  										"\"fields\":     [ \"firstname\", \"lastname\" ],"+operatorString+
						  										"}"+
						  					"}"+
						  		"}";
				
				
				Search search = new Search.Builder(query)
		                // multiple index or types can be added.
		                .addIndex("testindex").addType("info").build();

				SearchResult result = client.execute(search);
				return Response.ok(result.getJsonString()).build();
				
				
			} 
			catch (Exception dnfe) 
			{
				System.out.println("Exception thrown : " + dnfe.getMessage());
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
		
	}
	
	private JsonObject toJsonObject(Map<String, String> obj) 
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", obj.get("_id") + "");
		jsonObject.addProperty("firstname", obj.get("firstname") + "");
		jsonObject.addProperty("lastname", obj.get("lastname") + "");
		jsonObject.addProperty("address", obj.get("address") + "");
		jsonObject.addProperty("city", obj.get("city") + "");
		jsonObject.addProperty("state", obj.get("state") + "");
		jsonObject.addProperty("zip", obj.get("zip") + "");
		jsonObject.addProperty("homephone", obj.get("homephone") + "");
		jsonObject.addProperty("workphone", obj.get("workphone") + "");
		jsonObject.addProperty("mobilephone", obj.get("mobilephone") + "");
		jsonObject.addProperty("gender", obj.get("gender") + "");
		jsonObject.addProperty("birthdate", obj.get("birthdate") + "");
		jsonObject.addProperty("email", obj.get("email") + "");
		jsonObject.addProperty("filename", obj.get("filename") + "");
		jsonObject.addProperty("photo", obj.get("photo") + "");
		return jsonObject;
	}

}
