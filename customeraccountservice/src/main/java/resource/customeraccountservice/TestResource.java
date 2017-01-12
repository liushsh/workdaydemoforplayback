package resource.customeraccountservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Root resource (exposed at "myresource" path)
 */
@Path("redisattach")
@MultipartConfig()
public class TestResource {

    
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response createProfile(@FormDataParam("file") InputStream fileInputStream,@FormDataParam("file") FormDataContentDisposition fileMetaData, @QueryParam("id") String id, @QueryParam("password") String password,@QueryParam("firstname") String firstName, @QueryParam("lastname") String lastName, @QueryParam("address") String address, @QueryParam("city") String city,@QueryParam("state") String state,@QueryParam("zip") String zip,@QueryParam("homephone") String homePhone,@QueryParam("workphone") String workPhone,@QueryParam("mobilephone") String mobilePhone,@QueryParam("email") String email,@QueryParam("gender") String gender,@QueryParam("birthdate") String birthDate,@QueryParam("filename") String fileName) throws ServletException, IOException 
	{
		System.out.println("quyen 0");
		
		//Part part = null;
		
		//part = request.getPart("file");
		
		//InputStream targetStream = new FileInputStream(file);
		//targetStream.available();
		//targetStream.close();
		
		//fileInputStream.available();
		
		System.out.println("quyen 1 - " + id +","+password);
		
		

		Jedis db = null;
		try {
			System.out.println("quyen 2");
			db = new Jedis(config.Configuration.getPropertyValue("redisDB"));
			System.out.println("quyen 3");
		} catch (Exception re) {
			db.close();
			re.printStackTrace();
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
			
		}
		System.out.println("quyen 4");
		RedisResourceServlet servlet = new RedisResourceServlet();
		System.out.println("quyen 5");
		String key = id;
		JsonObject resultObject = servlet.redisCreateProfile(db, key, firstName, lastName, address, city, state, zip,homePhone,workPhone, mobilePhone, email,gender,birthDate, fileInputStream, fileName);
		System.out.println("quyen 6");
		servlet.addDocumentToElasticSearch(email, password,  key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, gender, birthDate);
		System.out.println("quyen 7");
		System.out.println("Upload completed.");

		db.close();
		return Response.ok(resultObject.toString()).build();
	}
	
	@POST
	@Path("importdata")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response importProfiles(String jsonData, @QueryParam("emailprefix") String emailPrefix, @QueryParam("password") String password, @QueryParam("photo") String fileName) throws ServletException, IOException 
	{
		System.out.println("Start importing data");
		
		Jedis db = null;
		try 
		{
			System.out.println("quyen 2");
			db = new Jedis(config.Configuration.getPropertyValue("redisDB"));
			System.out.println("quyen 3");
		
			System.out.println("quyen 4");
			RedisResourceServlet servlet = new RedisResourceServlet();
			System.out.println("quyen 5");
			
			JSONObject obj = new JSONObject(jsonData);
		    JSONArray dataArray = obj.getJSONArray("profiles");
		    
		    for (int i = 0; i < dataArray.length(); ++i) 
		    {
		    	JSONObject data = dataArray.getJSONObject(i);
		    	String firstName = (String) data.get("firstname");
		    	String lastName = (String) data.get("lastname");
		    	String address = (String) data.get("address");
		    	String city = (String) data.get("city");
		    	String state = (String) data.get("state");
		    	String zip = (String) data.get("zip");
		    	String homePhone = (String) data.get("homephone");
		    	String workPhone = (String) data.get("workphone");
		    	String mobilePhone = (String) data.get("mobilephone");
		    	String email = emailPrefix+i+"@gmail.com";
		    	String gender = (String) data.get("gender");
		    	String birthDate = (String) data.get("birthdate");
		    	String key = email +":"+password;
		    	
		    	JsonObject resultObject = servlet.redisCreateProfileWithoutPhoto(db, key, firstName, lastName, address, city, state, zip,homePhone,workPhone, mobilePhone, email,gender,birthDate, fileName);
				System.out.println("quyen 6");
				servlet.addDocumentToElasticSearch(email, password,  key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, gender, birthDate);
				System.out.println("quyen 7");
		    	
		    }
			
			System.out.println("Upload completed.");
	
			db.close();
		
		} 
		catch (Exception re) 
		{
			db.close();
			re.printStackTrace();
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
			
		}
		return Response.ok("Upload completed.").build();
	}
	
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    
	@GET
    public void getPhoto(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException{
    	String id = request.getParameter("id");
		String key = request.getParameter("key");
        
		response.setHeader("Content-Disposition", "inline; filename=\"" + key + "\"");

		byte c[];
        try
        {
        	
        	
        	//Connect to DB
            Jedis db = new Jedis(config.Configuration.getPropertyValue("redisDB"));

            Map<String, String> properties = db.hgetAll(id);
            RedisProfile p = new RedisProfile();
            p.set_id(properties.get("_id"));
            p.setFirstName(properties.get("firstname"));
            p.setLastName(properties.get("lastname"));
            p.setAddress(properties.get("address"));
            p.setCity(properties.get("city"));
            p.setZip(properties.get("zip"));
            p.setHomePhone(properties.get("homephone"));
            p.setWorkPhone(properties.get("workphone"));
            p.setMobilePhone(properties.get("mobilephone"));
            p.setGender(properties.get("gender"));
            p.setBirthDate(properties.get("birthdate"));
            p.setFileName(properties.get("filename"));
            p.setEmail(properties.get("email"));
            p.setCreationDate(properties.get("creationdate"));
            p.setPhoto(properties.get("photo"));
            
			
            
        	
            //DBObject obj = collection.findOne(new BasicDBObject("name", name));
            //String n = (String)obj.get("name");
            org.bson.types.Binary bin = ImageUtils.decodeStringToImageBinary(properties.get("photo"));
    		
            //c = (byte[])obj.get("photo");
            OutputStream output = response.getOutputStream();
            output.write(bin.getData(),0,bin.length());
            
            
        }
        catch (IOException e) {
            throw e;
        }
    }
    
	/*
	@GET
    public Response getPhoto(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException{
    	String id = request.getParameter("id");
		String key = request.getParameter("key");
        
		//response.setHeader("Content-Disposition", "inline; filename=\"" + key + "\"");

		byte c[];
        try
        {
        	
        	
        	//Connect to DB
            Jedis db = new Jedis(config.Configuration.redisDB);

            Map<String, String> properties = db.hgetAll(id);
            RedisProfile p = new RedisProfile();
            p.set_id(properties.get("_id"));
            p.setFirstName(properties.get("firstname"));
            p.setLastName(properties.get("lastname"));
            p.setAddress(properties.get("address"));
            p.setCity(properties.get("city"));
            p.setZip(properties.get("zip"));
            p.setHomePhone(properties.get("homephone"));
            p.setWorkPhone(properties.get("workphone"));
            p.setMobilePhone(properties.get("mobilephone"));
            p.setGender(properties.get("gender"));
            p.setBirthDate(properties.get("birthdate"));
            p.setFileName(properties.get("filename"));
            p.setEmail(properties.get("email"));
            p.setCreationDate(properties.get("creationdate"));
            p.setPhoto(properties.get("photo"));
            
			db.close();
            
        	
            //DBObject obj = collection.findOne(new BasicDBObject("name", name));
            //String n = (String)obj.get("name");
            org.bson.types.Binary bin = ImageUtils.decodeStringToImageBinary(properties.get("photo"));
    		
            //c = (byte[])obj.get("photo");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //OutputStream output = response.getOutputStream();
            
            output.write(bin.getData(),0,bin.length());
            output.flush();
            
            
            
            ResponseBuilder builder = Response.ok(output.toByteArray());
            builder.header("Content-Disposition", "inline; filename=\"" + key + "\"");
            return  builder.build();
            
            
        }
        catch (IOException e) {
            throw e;
        }
    }
    */
	
}
