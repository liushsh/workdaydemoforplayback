package config;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	/*
	// This one works well for production
	//Service end points
	public static final String connectProviderEndPoint ="http://connectionprovider.mybluemix.net";
	public static final String customerAccountServiceEndPoint="http://customeraccountservice.mybluemix.net";
	
	
	// MongoDB configuration
	public static final String mongoServer="mongodb137.aws-us-east-1-portal.20.dblayer.com";
	public static final int mongoDBPort = 10137 ;
	public static final String mongoDB = "profiledb";
	public static final String mongoDBUser = "qvtran";
	public static final String mongoDBPassword = "abc123";
	
	// LDAP configuration
	public static final String ldapServer = "169.50.102.93";
	public static final int ldapPort = 389;
	public static final String ldapDNAdmin = "cn=Manager,dc=srv,dc=world";
	public static final String ldapDNAdminPassword = "abc123";
	public static final String ldapBase="ou=People,dc=srv,dc=world";
	*/
	/*
	// This one works well for DYS0
	//Service end points
	public static final String connectProviderEndPoint ="http://connectprovider.dys0.mybluemix.net";
	//public static final String customerAccountServiceEndPoint="http://customeraccountservice.dys0.mybluemix.net";
	public static final String customerAccountServiceEndPoint = "http://169.44.132.155:8080/customeraccountservice";
	
	// MongoDB configuration
	//DYS0 System
	//public static final String mongoServer="mongodb3471.bluemix-test1-ibm-305.1.compose.direct";
	//public static final int mongoDBPort = 13471;
	//public static final String mongoServer = "mongodb4582.bluemix-test1-ibm-305.0.compose.direct";
	//public static final int mongoDBPort = 14582;
	
	//Production
	//public static final String mongoServer="mongodb137.aws-us-east-1-portal.20.dblayer.com";
	//public static final int mongoDBPort = 10137 ;
	public static final String mongoConnectionString = "mongodb://qvtran:abc123@aws-us-east-1-portal.20.dblayer.com:10137/profiledb";
	
	//MBLab
	public static final String mongoServer="ds011755.mlab.com";
	public static final int mongoDBPort = 11755 ;
	
	public static final String mongoDB = "profiledb";
	public static final String mongoDBUser = "qvtran";
	public static final String mongoDBPassword = "abc123";
	
	// LDAP configuration
	public static final String ldapServer = "169.50.102.93";
	public static final int ldapPort = 389;
	public static final String ldapDNAdmin = "cn=Manager,dc=srv,dc=world";
	public static final String ldapDNAdminPassword = "abc123";
	public static final String ldapBase="ou=People,dc=srv,dc=world";
	
	//Redis
	public static final String redisDB = "redis://x:IPRBKTMZKVSKPUJU@sl-us-dal-9-portal.3.dblayer.com:15532";
	*/
	private static Properties pro;
	
	static
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("config.properties");
	
		pro = new Properties();
		try
		{
			pro.load(input);
			
			
		}
		catch(Exception e)
		{
			System.out.println("config.properties not found");
		}
	}
	
	public static String getPropertyValue(String key){
        return pro.getProperty(key);
    }
	
	 
}
