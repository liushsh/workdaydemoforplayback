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


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import config.Configuration;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;




@Path("/")
public class ProfileElasticSearch {

	@GET
	@Path("searchprofile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProfile(@QueryParam("username") String username) throws Exception {
        
		Response response = null;
		try
		{
			JestClientFactory factory = new JestClientFactory();
			 factory.setHttpClientConfig(new HttpClientConfig
			                        .Builder("https://qvtran:abc123@sl-us-dal-9-portal3.dblayer.com:15642/")
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
			 
			 
			 // Create index
			 client.execute(new CreateIndex.Builder("testindex").build());
			 
			 
			 // Add document to index
			 JsonObject jsonObject = new JsonObject();
			 jsonObject.addProperty("testusername","qvtran");
				
			 String source = jsonObject.toString();
			 
			 Index index = new Index.Builder(source).index("testindex").type("info").build();
			 client.execute(index);
			 
		}
		catch(Exception e)
		{
			throw e;
		}
		
		return Response.ok(username).build();
		
    }

}
