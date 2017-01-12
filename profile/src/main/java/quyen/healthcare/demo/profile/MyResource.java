package quyen.healthcare.demo.profile;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("properties")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    /*
    @GET
    
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    */
	
    @GET
    @Path("/{property}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperty(@PathParam("property")String property)
    {
    	JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(property,config.Configuration.getPropertyValue(property));
		return Response.ok(jsonObject.toString()).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProperties()
    {
    	JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("customerAccountServiceEndPoint",config.Configuration.getPropertyValue("customerAccountServiceEndPoint"));
		jsonObject.addProperty("redisDB",config.Configuration.getPropertyValue("redisDB"));
		jsonObject.addProperty("photoDir",config.Configuration.getPropertyValue("photoDir"));
		jsonObject.addProperty("elasticSearchEndPoint",config.Configuration.getPropertyValue("elasticSearchEndPoint"));
		jsonObject.addProperty("adminUserName",config.Configuration.getPropertyValue("adminUserName"));
		jsonObject.addProperty("adminPassword",config.Configuration.getPropertyValue("adminPassword"));
		return Response.ok(jsonObject.toString()).build();
    }
   
}
