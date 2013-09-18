package com.example.regdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ConsumeWebService {

//web service using post request	
	
public String callWebService(String url,String action,ArrayList<NameValuePair> data){
	final DefaultHttpClient httpClient=new DefaultHttpClient();
	HttpParams params=httpClient.getParams();
	HttpConnectionParams.setConnectionTimeout(params, 10000);
	HttpConnectionParams.setSoTimeout(params, 15000);
	//set Params
	HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);
	HttpPost httppost=new HttpPost(url);
	//httppost.setHeader("action",action);
	httppost.setHeader("Content-Type","application/json; charset=utf-8");
	HttpResponse httpResponse;
	String response="";
	try{
		//the entity hold the request
		//HttpEntity entity=new Entity(data);
		httppost.setEntity(new UrlEncodedFormEntity(data,HTTP.UTF_8));
		
		//response handler
		new ResponseHandler<String>(){
			//invoked when client receives response
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

			     // get response entity
			     HttpEntity entity = response.getEntity();

			     // read the response as byte array
			           StringBuffer out = new StringBuffer();
			           byte[] b = EntityUtils.toByteArray(entity);

			           // write the response byte array to a string buffer
			           out.append(new String(b, 0, b.length));
			           Log.e("json handle first",out.toString());
			           return out.toString();
			    }

			
			   };
			   
			   httpResponse=httpClient.execute(httppost); 
			 //  String message;
			  // int responseCode;
	           httpResponse.getStatusLine().getStatusCode();
	            httpResponse.getStatusLine().getReasonPhrase();
	            HttpEntity entit = httpResponse.getEntity();
	            if (entit!= null) {

	                InputStream instream = entit.getContent();
	                response = convertStreamToString(instream);

	                // Closing the input stream will trigger connection release
	                instream.close();
	            }
		}catch (Exception e) {
		      Log.v("exception", e.toString());
		  }
	httpClient.getConnectionManager().shutdown();
	 Log.e("json handle second",response);
	  return response;
	 }



//webservice using get request
public String restConsumer(String serviceURL,ArrayList<NameValuePair> params){
	// http get client
	DefaultHttpClient client=new DefaultHttpClient();
	HttpGet getRequest=new HttpGet();
	
   try {
	   try {
		String combinedParams = "";
		    if(!params.isEmpty()){
		        combinedParams += "?";
		        for(NameValuePair p : params)
		        {
		            String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),HTTP.UTF_8);
		            if(combinedParams.length() > 1)
		            {
		                combinedParams  +=  "&" + paramString;
		                Log.e("artists","i am in webrest 0");
		            }
		         
		else {
		                combinedParams += paramString;
		                Log.e("artists","i am in webrest 1");
		            }
		        }
		    }
		    Log.e("artists","i am in webrest 2");
		getRequest.setURI(new URI(serviceURL+combinedParams));
		   Log.e("artists","i am in webrest 3");
	} catch (UnsupportedEncodingException e) {
		
		Log.e("UnsupportedEncodingException", e.toString());
	}
} catch (URISyntaxException e) {
	Log.e("URISyntaxException", e.toString());
}
   Log.e("artists","i am in webrest 5");
	// buffer reader to read the response
	BufferedReader in=null;
	// the service response
	HttpResponse response=null;
	try {
		// execute the request
		  Log.e("artists","i am in webrest 6");
		response = client.execute(getRequest);
	} catch (ClientProtocolException e) {
		Log.e("ClientProtocolException", e.toString());
	} catch (IOException e) {
		Log.e("IO exception", e.toString());
	}
	try {
		  Log.e("artists","i am in webrest 7");
		in=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"iso-8859-1"),8);
		  Log.e("artists","i am in webrest 8");
	} catch (IllegalStateException e) {
		Log.e("IllegalStateException", e.toString());
	} catch (IOException e) {
		Log.e("IO exception", e.toString());
	}
	  Log.e("artists","i am in webrest 9");
	StringBuffer buff=new StringBuffer("");
	String line="";
	  Log.e("artists","i am in webrest 10");
	try {
		while((line=in.readLine())!=null)
		{
			  Log.e("artists","i am in webrest 11"+line);
			buff.append(line+"\n");
		}
	} catch (IOException e) {
		Log.e("IO exception", e.toString());
		
	}
	  Log.e("artists","i am in webrest 12"+buff.toString());
	try {
		 Log.e("artists","i am in webrest 13");
		in.close();
		  Log.e("artists","i am in webrest 14");
	} catch (IOException e) {
		Log.e("IO exception", e.toString());
	}
	// response, need to be parsed
	Log.e("from Get", buff.toString());
	
	return String.valueOf(buff);
	
}

//method to build json
public JSONObject getJsonOb(String result){
	JSONObject jArray=null;
	try{
        jArray=new JSONObject(result);
        return jArray;
  }catch(JSONException e){
     Log.e("log_tag", "Error parsing data "+e.toString());
 }
	return jArray;
}

//arraylist builder
public ArrayList<HashMap<String,String>> myJsonArrayList(String jsonDataSource,String jsonArrayName,String[] mapKey,String[] jsonElementName){
	ArrayList<HashMap<String,String>> dataList=new ArrayList<HashMap<String,String>>();
	if(!jsonDataSource.equals("")){
	JSONObject json=this.getJsonOb(jsonDataSource);
	try {
		
		JSONArray myArray=json.getJSONArray(jsonArrayName);
		for(int i=0; i<myArray.length()-1; i++){
			HashMap<String,String> map=new HashMap<String,String>();
			JSONObject jsonOb=myArray.getJSONObject(i);
			
			/*note: 1:mapkey length should be equal to jsonElementName length for simplicitys
			 *         2 index of all item is by default given by mapkey id;
			 */
			      map.put("id", String.valueOf(i));
			for(int j=0; j<mapKey.length; j++){
				map.put(mapKey[j],jsonOb.getString(jsonElementName[j]) );
			}
			dataList.add(map);
		}
		return dataList;
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
	
	}
	return null;
}

private static String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return sb.toString();
}
}
