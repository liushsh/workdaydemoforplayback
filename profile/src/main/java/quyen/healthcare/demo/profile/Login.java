package quyen.healthcare.demo.profile;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import redis.clients.jedis.Jedis;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import config.Configuration;

@Path("/")
public class Login {

	@POST
	@Path("login_process")
	@Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) throws Exception {
        
		// Call webservice from connectprovider to authenticate
		try 
		{

			authenticate(username,password);
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("status", "OK");
			jsonObject.addProperty("token", username + "Qllygd_1");
			return Response.ok(jsonObject.toString()).build();

        } 
		catch (Exception e) 
		{
			//return Response.status(Response.Status.UNAUTHORIZED).build();
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("status", "failed");
			jsonObject.addProperty("token", "invalidtoken");
			return Response.ok(jsonObject.toString()).build();
        }
		
		
    }
	
	@POST
	@Path("redis_login_process")
	@Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
	public Response redisLogin(@FormParam("username") String username, @FormParam("password") String password) throws Exception {
        
		try 
		{
			//Connect to DB
			System.out.println("Quyen oi sai nua "+ config.Configuration.getPropertyValue("redisDB"));
			System.out.println("quyenaa 0");
			Jedis jedis = new Jedis(config.Configuration.getPropertyValue("redisDB"));
	        System.out.println("quyenaa 1");
	        String key = username+":"+password;
	        System.out.println("quyenaa 2");
	        if (jedis.exists(key))
	        {
	        	System.out.println("quyenaa 3");
	        	JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("status", "OK");
				jsonObject.addProperty("token", username + "Qllygd_1");
				return Response.ok(jsonObject.toString()).build();
	        }
	        System.out.println("quyenaa 4");
		}
        catch (Exception e) 
		{
			//return Response.status(Response.Status.UNAUTHORIZED).build();
        	System.out.println("quyenaa 5");
        	JsonObject jsonObject = new JsonObject();
    		jsonObject.addProperty("status", "failed");
    		jsonObject.addProperty("token", e.getMessage());
    		return Response.ok(jsonObject.toString()).build();
			
        }
		System.out.println("quyenaa 6");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", "failed");
		jsonObject.addProperty("token", "invalidtoken");
		System.out.println("quyenaa 7");
		return Response.ok(jsonObject.toString()).build();
		
    }
	
	// Call webservice from connectprovider to authenticate
	private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
		//http://connectionprovider.mybluemix.net/webapi/verifypassword/169.50.102.93/cn=Manager,dc=srv,dc=world/abc123/ou=People,dc=srv,dc=world/qvtran/abc123
		// good one works
		//String endPoint = "http://connectionprovider.mybluemix.net/webapi/verifypassword/169.50.102.93/cn=Manager,dc=srv,dc=world/abc123/ou=People,dc=srv,dc=world";
		String endPoint =  config.Configuration.getPropertyValue("connectProviderEndPoint") + "/webapi/verifypassword/"+config.Configuration.getPropertyValue("ldapServer")+"/"+config.Configuration.getPropertyValue("ldapDNAdmin")+"/"+config.Configuration.getPropertyValue("ldapDNAdminPassword")+"/"+config.Configuration.getPropertyValue("ldapBase");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(endPoint+"/"+username+"/"+password);
		Response response;
		try
		{
			response = target.request(MediaType.APPLICATION_JSON).get();
			
			if (response.getStatus() == 200)
			{
				String entity = response.readEntity(String.class);
				if (entity.indexOf("OK")==-1)
				{
					throw new Exception("Authentication failed");
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		
		//return response;
	
	}

}
