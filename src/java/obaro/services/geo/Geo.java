/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obaro.services.geo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Obaro
 */
@WebService(serviceName = "Geo")
public class Geo {

    private final String HOST_PATH="http://localhost/restful/Index.php";
   
    /**
     * Web service operation will get the current location from another php server and return it to the
     * web clients that called it
     */
    @WebMethod(operationName = "getCurrentLocation")
    public String getCurrentLocation() {
      
        //make a web request to be able to get the IP of the machine and return the current match location informations
        
        String param ="controller=Index&action=Index";
        
        return this.sendRequest(param);
    }
    
    
    
    private String sendRequest(String URLParam)
    {
          //make a web request to be able to get the IP of the machine and return the current match location informations
        URL url;
        String targetURL =HOST_PATH+"?"+URLParam;
        
     
        
        HttpURLConnection connection=null;
        StringBuilder response = new StringBuilder();
        try
        {
            //open the connection to the URL
            url=new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);           
        
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
            
            outStream.flush();
            outStream.close();
            
            
            //now get the reponse from the service
            
            InputStream is = connection.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(is));
            
            String line;
           
           // response.append(line);
            //read the response into the String Builder where is line to read
            
            while((line = reader.readLine()) !=null)
            {
                response.append(line);
               // response.append("\r");
            }
            
            
            reader.close();
            
            return response.toString();
            
            
        }
        catch(Exception err)
        {
            err.printStackTrace();
            return err.getMessage();
        }
        
    }

    /**
     * Web service operation
     * @param location
     * @return 
     */
    @WebMethod(operationName = "searh")
    public String searh(@WebParam(name = "location") String location) 
    {
      
       String response;     
       
        response = this.sendRequest("c=Park&action=Search&Address="+location);
        
        return response;
    }
}
