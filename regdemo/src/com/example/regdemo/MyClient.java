package com.example.regdemo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
public class MyClient {
	private HttpClient httpClient = new DefaultHttpClient();
	private ArrayList <NameValuePair> params;
	private String url="";
	private String imageUrl="";
	private String result="failed";
	private String res="";
	private Bitmap bitmap;
	private   ByteArrayBody bab;
public MyClient(String url,String imageUrl){
	params = new ArrayList<NameValuePair>();
	this.url=url;
	this.imageUrl=imageUrl;
}

public void addImages(String imageUrl){
	bitmap=BitmapFactory.decodeFile(imageUrl);
	 ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.JPEG, 75, bos);
      byte[] data = bos.toByteArray();
     bab = new ByteArrayBody(data,"profile.jpg");
}
private   ByteArrayBody getImage(){
return this.bab;
}
public String sendMultiformData(){
	HttpPost postRequest=new HttpPost(url);
	MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	try {
		for(NameValuePair h : params)
        {
			reqEntity.addPart(h.getName(), new StringBody(h.getValue()));
        }
	       reqEntity.addPart("picha", getImage());
	} catch (UnsupportedEncodingException e) {
    res=e.toString();
	}
	
	postRequest.setEntity(reqEntity);       
    try {
		HttpResponse response = httpClient.execute(postRequest);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String sResponse;
		StringBuilder s = new StringBuilder();
		while ((sResponse = reader.readLine()) != null) {
		    s = s.append(sResponse);
		}
		result=s.toString();
	} catch (ClientProtocolException e) {
	result=e.toString();
	} catch (UnsupportedEncodingException e) {
	result=e.toString();
	} catch (IllegalStateException e) {
	result=e.toString();
	} catch (IOException e) {
    result=e.toString();
	}
   return result+"partimage"+res;
}

/**
 * This method used to add url parameter in name value pair format
 * It is used to set the parameter for get or post request
 * @param name
 * @param value
 */
public void addParam(String name, String value)
{
    params.add(new BasicNameValuePair(name, value));
}
}
