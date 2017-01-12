package resource.customeraccountservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.bson.Document;
import org.bson.types.Binary;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import config.Configuration;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import redis.clients.jedis.Jedis;

@Path("/favorites")
/**
 * CRUD service of todo list table. It uses REST style.
 */
public class RedisResourceServlet {

	public RedisResourceServlet() {
	}
/*
	@POST
	public Response create(@QueryParam("id") Long id, @FormParam("name") String name, @FormParam("value") String value)
			throws Exception {

		DB db = null;
		try {
			db = getDB();
		} catch (Exception re) {
			re.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		String idString = id == null ? null : id.toString();
		JsonObject resultObject = create(db, idString, name, value, null, null);

		System.out.println("Create Successful.");

		return Response.ok(resultObject.toString()).build();
	}
	
	protected JsonObject create(DB db, String id, String name, String value, Part part, String fileName)
			throws IOException {

		// check if document exist
		HashMap<String, Object> obj = (id == null) ? null : db.find(HashMap.class, id);

		if (obj == null) {
			// if new document

			id = String.valueOf(System.currentTimeMillis());

			// create a new document
			System.out.println("Creating new document with id : " + id);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("name", name);
			data.put("_id", id);
			data.put("value", value);
			data.put("creation_date", new Date().toString());
			db.save(data);

			// attach the attachment object
			obj = db.find(HashMap.class, id);
			saveAttachment(db, id, part, fileName, obj);
		} else {
			// if existing document
			// attach the attachment object
			saveAttachment(db, id, part, fileName, obj);

			// update other fields in the document
			obj = db.find(HashMap.class, id);
			obj.put("name", name);
			obj.put("value", value);
			db.update(obj);
		}

		obj = db.find(HashMap.class, id);

		JsonObject resultObject = toJsonObject(obj);

		return resultObject;
	}
*/	
	protected JsonObject createProfile(Jedis db, String key,String  firstName,String  lastName,String  address,String  city,String  state,String  zip,String homePhone,String workPhone,String  mobilePhone,String  email,String gender,String  birthDate,Part part, String fileName)
	//(Database db, String id, String password, String firstName, String lastName, String address,String city, String state, String zip, String homePhone, String workPhone, String mobilePhone, String gender, String birthDate, String email,Part part, String fileName)
			throws IOException {
		JsonObject resultObject = null;
		
		if (!db.exists(key))
		{
			//db.del(key);
		
			//InputStream inputStream = part.getInputStream();
			//byte b[] = new byte[inputStream.available()];
			//inputStream.read(b);
		
		    //Binary data = new Binary(b);
			byte[] b=new byte[]{1,2,3,4,5};
			Binary data = new Binary(b);
			
		    String photo = ImageUtils.encodeImageBinaryToString(data);
		    String creationDate = new Date().toString();
		    
			RedisProfile p = new RedisProfile(key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, gender, birthDate, fileName, email, creationDate, photo);
			Map<String, String> profileProperties = new HashMap<String, String>();
			
			profileProperties.put("_id", p.get_id());
			profileProperties.put("firstname", p.getFirstName());
			profileProperties.put("lastname", p.getLastName());
			profileProperties.put("address", p.getAddress());
			profileProperties.put("city", p.getCity());
			profileProperties.put("state", p.getState());
			profileProperties.put("zip", p.getZip());
			profileProperties.put("homephone", p.getHomePhone());
			profileProperties.put("workphone", p.getWorkPhone());
			profileProperties.put("mobilephone", p.getMobilePhone());
			profileProperties.put("gender", p.getGender());
			profileProperties.put("birthdate", p.getBirthDate());
			profileProperties.put("filename", p.getFileName());
			profileProperties.put("email", p.getEmail());
			profileProperties.put("creationdate", creationDate);
			profileProperties.put("photo", photo);
			
			db.hmset(key, profileProperties);
			
			resultObject = new JsonObject();
			resultObject.addProperty("_id",p.get_id());
		}
		else
		{
			resultObject = new JsonObject();
			resultObject.addProperty("_id","-1");
		}
		
		
		
		

		
		//HashMap<String, Object> objTemp = obj.toJson();

		//JsonObject resultObject = toJsonObject(objTemp);
		
		
		

		return resultObject;
	}
	
	protected JsonObject redisCreateProfile(Jedis db, String key,String  firstName,String  lastName,String  address,String  city,String  state,String  zip,String homePhone,String workPhone,String  mobilePhone,String  email,String gender,String  birthDate,InputStream inputStream, String fileName)
	//(Database db, String id, String password, String firstName, String lastName, String address,String city, String state, String zip, String homePhone, String workPhone, String mobilePhone, String gender, String birthDate, String email,Part part, String fileName)
			throws IOException {
		JsonObject resultObject = null;
		
		if (!db.exists(key))
		{
			//db.del(key);
		
			//InputStream inputStream = part.getInputStream();
			//byte b[] = new byte[inputStream.available()];
			//inputStream.read(b);
			
			int byteNum = 0;
			byte b[] = new byte[1000000];
			/*int byteNum = inputStream.read(b);
			System.out.println("Quyen oi chan qua "+ byteNum);
			byte a[] = new byte[byteNum];
			
			for(int i=0; i<byteNum;i++)
			{
				a[i]=b[i];
			}*/
			
			//Get profile from Redis
			
			String photo = "/photos/"+ fileName;
			String creationDate = new Date().toString();
			RedisProfile p = new RedisProfile(key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, gender, birthDate, fileName, email, creationDate, photo);
			
			//new
			//FileWriter outfile = new FileWriter("./photos/"+ fileName,true);
			//File f = new File(fileName);
			//f.createNewFile();
			
			photo = email+"."+getExtensionOfFile(fileName);
			
			System.out.println("Quyen oi chan qua "+ photo);
			
			OutputStream out = new FileOutputStream(new File(config.Configuration.getPropertyValue("photoDir")+"/"+photo));
			
	        while ((byteNum = inputStream.read(b)) != -1) 
	        {
	            out.write(b, 0, byteNum);
	        }
	        out.flush();
	        out.close();
	        
			//end new
			
		
		    //Binary data = new Binary(b);
			//byte[] b=new byte[]{1,2,3,4,5};
			//Binary data = new Binary(a);
			
			
		    
		    
			//RedisProfile p = new RedisProfile(key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, gender, birthDate, fileName, email, creationDate, photo);
			//String photo = ImageUtils.encodeImageBinaryToString(data);
			
		    
			
			Map<String, String> profileProperties = new HashMap<String, String>();
			
			profileProperties.put("_id", p.get_id());
			profileProperties.put("firstname", p.getFirstName());
			profileProperties.put("lastname", p.getLastName());
			profileProperties.put("address", p.getAddress());
			profileProperties.put("city", p.getCity());
			profileProperties.put("state", p.getState());
			profileProperties.put("zip", p.getZip());
			profileProperties.put("homephone", p.getHomePhone());
			profileProperties.put("workphone", p.getWorkPhone());
			profileProperties.put("mobilephone", p.getMobilePhone());
			profileProperties.put("gender", p.getGender());
			profileProperties.put("birthdate", p.getBirthDate());
			profileProperties.put("filename", p.getFileName());
			profileProperties.put("email", p.getEmail());
			profileProperties.put("creationdate", creationDate);
			profileProperties.put("photo", photo);
			
			
			
			db.hmset(key, profileProperties);
			
			resultObject = new JsonObject();
			resultObject.addProperty("_id",p.get_id());
			System.out.println("Quyen oi chan qua nua di"+ photo);
		}
		else
		{
			resultObject = new JsonObject();
			resultObject.addProperty("_id","-1");
		}
		
		
		
		

		
		//HashMap<String, Object> objTemp = obj.toJson();

		//JsonObject resultObject = toJsonObject(objTemp);
		
		
		

		return resultObject;
	}
	
	protected JsonObject redisCreateProfileWithoutPhoto(Jedis db, String key, String  firstName,String  lastName,String  address,String  city,String  state,String  zip,String homePhone,String workPhone,String  mobilePhone,String  email,String gender,String  birthDate,String fileName)
	//(Database db, String id, String password, String firstName, String lastName, String address,String city, String state, String zip, String homePhone, String workPhone, String mobilePhone, String gender, String birthDate, String email,Part part, String fileName)
			throws IOException {
		JsonObject resultObject = null;
		
		if (!db.exists(key))
		{
			
			
			//Get profile from Redis
			
			String photo = "/photos/"+ fileName;
			String creationDate = new Date().toString();
			RedisProfile p = new RedisProfile(key, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, gender, birthDate, fileName, email, creationDate, photo);
			
			
			
			//photo = email+"."+getExtensionOfFile(fileName);
			photo = fileName;
			
			System.out.println("Quyen oi chan qua "+ photo);
			
			
			Map<String, String> profileProperties = new HashMap<String, String>();
			
			profileProperties.put("_id", p.get_id());
			profileProperties.put("firstname", p.getFirstName());
			profileProperties.put("lastname", p.getLastName());
			profileProperties.put("address", p.getAddress());
			profileProperties.put("city", p.getCity());
			profileProperties.put("state", p.getState());
			profileProperties.put("zip", p.getZip());
			profileProperties.put("homephone", p.getHomePhone());
			profileProperties.put("workphone", p.getWorkPhone());
			profileProperties.put("mobilephone", p.getMobilePhone());
			profileProperties.put("gender", p.getGender());
			profileProperties.put("birthdate", p.getBirthDate());
			profileProperties.put("filename", p.getFileName());
			profileProperties.put("email", p.getEmail());
			profileProperties.put("creationdate", creationDate);
			profileProperties.put("photo", photo);
			
			
			
			db.hmset(key, profileProperties);
			
			resultObject = new JsonObject();
			resultObject.addProperty("_id",p.get_id());
			System.out.println("Quyen oi chan qua nua di"+ photo);
		}
		else
		{
			resultObject = new JsonObject();
			resultObject.addProperty("_id","-1");
		}
		
		
		
		

		
		//HashMap<String, Object> objTemp = obj.toJson();

		//JsonObject resultObject = toJsonObject(objTemp);
		
		
		

		return resultObject;
	}
	
	protected JsonObject addDocumentToElasticSearch(String userName,String password, String key,String  firstName,String  lastName,String  address,String  city,String  state,String  zip,String homePhone,String workPhone,String  mobilePhone,String  email,String gender,String  birthDate)
	//(Database db, String id, String password, String firstName, String lastName, String address,String city, String state, String zip, String homePhone, String workPhone, String mobilePhone, String gender, String birthDate, String email,Part part, String fileName)
			throws IOException 
	{
		JsonObject resultObject = null;
		try
		{
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
			
			// Add document to index
			 JsonObject jsonObject = new JsonObject();
			 jsonObject.addProperty("username",userName);
			 jsonObject.addProperty("password",password);
			 jsonObject.addProperty("key",key);
			 jsonObject.addProperty("firstname",firstName);
			 jsonObject.addProperty("lastname",lastName);
			 jsonObject.addProperty("address",address);
			 jsonObject.addProperty("city",city);
			 jsonObject.addProperty("state",state);
			 jsonObject.addProperty("zip",zip);
			 jsonObject.addProperty("homephone",homePhone);
			 jsonObject.addProperty("workphone",workPhone);
			 jsonObject.addProperty("mobilephone",mobilePhone);
			 jsonObject.addProperty("email",email);
			 jsonObject.addProperty("gender",gender);
			 jsonObject.addProperty("birthdate",birthDate);
				
			 String source = jsonObject.toString();
			 
			 Index index = new Index.Builder(source).index("testindex").type("info").build();
			 client.execute(index);
			 
			 resultObject = new JsonObject();
			 resultObject.addProperty("_id",key);
			 
		 
		}
		catch(Exception e)
		{
			resultObject = new JsonObject();
			resultObject.addProperty("_id","-1");
			
		}
		
		return resultObject;
	}
	
/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("id") Long id, @QueryParam("cmd") String cmd) throws Exception {

		Database db = null;
		try {
			db = getDB();
		} catch (Exception re) {
			re.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		JsonObject resultObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();

		if (id == null) {
			try {
				// get all the document present in database
				List<HashMap> allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse()
						.getDocsAs(HashMap.class);

				if (allDocs.size() == 0) {
					allDocs = initializeSampleData(db);
				}

				for (HashMap doc : allDocs) {
					HashMap<String, Object> obj = db.find(HashMap.class, doc.get("_id") + "");
					JsonObject jsonObject = new JsonObject();
					LinkedTreeMap<String, Object> attachments = (LinkedTreeMap<String, Object>) obj.get("_attachments");

					if (attachments != null && attachments.size() > 0) {
						JsonArray attachmentList = getAttachmentList(attachments, obj.get("_id") + "");
						jsonObject.addProperty("id", obj.get("_id") + "");
						jsonObject.addProperty("name", obj.get("name") + "");
						jsonObject.addProperty("value", obj.get("value") + "");
						jsonObject.add("attachements", attachmentList);

					} else {
						jsonObject.addProperty("id", obj.get("_id") + "");
						jsonObject.addProperty("name", obj.get("name") + "");
						jsonObject.addProperty("value", obj.get("value") + "");
					}

					jsonArray.add(jsonObject);
				}
			} catch (Exception dnfe) {
				System.out.println("Exception thrown : " + dnfe.getMessage());
			}

			resultObject.addProperty("id", "all");
			resultObject.add("body", jsonArray);

			return Response.ok(resultObject.toString()).build();
		}

		// check if document exists
		HashMap<String, Object> obj = db.find(HashMap.class, id + "");
		if (obj != null) {
			JsonObject jsonObject = toJsonObject(obj);
			return Response.ok(jsonObject.toString()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
*/
	
	

	private JsonArray getAttachmentList(LinkedTreeMap<String, Object> attachmentList, String docID) {
		JsonArray attachmentArray = new JsonArray();

		for (Object key : attachmentList.keySet()) {
			LinkedTreeMap<String, Object> attach = (LinkedTreeMap<String, Object>) attachmentList.get(key);

			JsonObject attachedObject = new JsonObject();
			// set the content type of the attachment
			attachedObject.addProperty("content_type", attach.get("content_type").toString());
			// append the document id and attachment key to the URL
			attachedObject.addProperty("url", "attach?id=" + docID + "&key=" + key);
			// set the key of the attachment
			attachedObject.addProperty("key", key + "");

			// add the attachment object to the array
			attachmentArray.add(attachedObject);
		}

		return attachmentArray;
	}

	private JsonObject toJsonObject(HashMap<String, Object> obj) {
		JsonObject jsonObject = new JsonObject();
		LinkedTreeMap<String, Object> attachments = (LinkedTreeMap<String, Object>) obj.get("_attachments");
		if (attachments != null && attachments.size() > 0) {
			JsonArray attachmentList = getAttachmentList(attachments, obj.get("_id") + "");
			jsonObject.add("attachements", attachmentList);
		}
		jsonObject.addProperty("id", obj.get("_id") + "");
		jsonObject.addProperty("name", obj.get("name") + "");
		jsonObject.addProperty("value", obj.get("value") + "");
		return jsonObject;
	}
	
	public static String getExtensionOfFile(String fileName)  
    {  
        String fileExtension="";  
        // Get file Name first  
       
        // If fileName do not contain "." or starts with "." then it is not a valid file  
        if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)  
        {  
            fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);  
        }  
          
        return fileExtension;  
    } 



	


	

}
