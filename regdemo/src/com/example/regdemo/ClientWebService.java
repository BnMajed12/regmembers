package com.example.regdemo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ClientWebService  extends AsyncTask<String,Integer,String> {
	private ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;
	private  ProgressDialog pd;
	private Bitmap bitmap;
	private Cursor adapterCursor;
	private ArrayList<String> fieldId=new ArrayList<String>();
	private ArrayList<String>  fieldValue=new ArrayList<String>();
	private ArrayList<String>  table=new ArrayList<String>();
	private ArrayList<String> imageParam=new ArrayList<String>();
	private   ByteArrayBody bab;
	private HashMap<String,ByteArrayBody> bobs=new HashMap<String,ByteArrayBody>();
	private Boolean isMultForm=false;
	HttpClient client = new DefaultHttpClient();
	private String url;
	private String progSMS="";
	private int responseCode;
	private String message;
	private String response;
	private String jsonArrayName;
	private Boolean connStatus=true;
	private Boolean loadingCompleted=false;
	private Boolean goodJSON=false;
	private Boolean toDb=false;
	private ProgressDialog dialog=null;
	private int resource;
	private Boolean hastoDelete=false;
	private String[] itemKey;
	private String[] mapKey;
	private int[] textViewid;
	private int _COUNT_IMAGE=0;
	private String toastSuccessSMS="",toastFailSMS="";
	private int addtocart=0,addtoPlaylist=0,dbcode=0;
	private ArrayList< HashMap<String, String>> data;
	private LayoutInflater inflates;
	private int viewlayoutid,ListViewId,textViewResourceId;
	private Context context;
	private ListView listView;
	private ProgressBar progressBar=null;
	private int itemIndex=0,loadingIconId=0,itemButtonId=0,imageViewId=0,imageiconid=0;
	private ArrayList<TextView> textviews,addplaylist,addcart;
	private ArrayList<ProgressBar> loadingIcon;
	private Boolean isListData=true;
	private DatabaseOperation db=null;
	//private Boolean hasAdapter=false;
	private List<ByteArrayBody> mybobs=new ArrayList<ByteArrayBody>();
	int i=1;
	/**
	 * Client webservice constructor which receive required url;
	 * @param url
	 */
	public ClientWebService(String url,Context context,ListView listView, LayoutInflater inflates,String jsonArrayName,int ListViewId){
		this.url = url;
		this.ListViewId=ListViewId;
		this.context=context;
		this.inflates=inflates;
		this.jsonArrayName= jsonArrayName;
		 
		 
		this.listView=listView;
		dialog=new ProgressDialog(context);
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public ClientWebService(String url,Context context,LayoutInflater inflates,String jsonArrayName,Boolean isList){
		this.url = url;
		this.context=context;
		this.inflates=inflates;
		this.jsonArrayName= jsonArrayName;
		dialog=new ProgressDialog(context);
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
		 pd=new ProgressDialog(this.context);
		this.isListData=isList;
	}

	public ClientWebService(){
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();	
	}

	public ClientWebService(String url,Context context,LayoutInflater inflates){
		this.url = url;
		this.context=context;
	   pd=new ProgressDialog(this.context);
		this.inflates=inflates;	
	}

	public ClientWebService(String url){
		this.url = url;	
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

   public void progresDialogMessage(String message,ProgressDialog pd){
	   this.progSMS=message;
	   this.pd=pd;
   }
	
	public void myProgresDialog(){
		if(!this.progSMS.equals("") && this.context!=null){
	if(pd!=null){
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(this.progSMS);
		pd.setIndeterminate(true);
		pd.setCancelable(false);
		pd.show();
	}
		}
	}

	/**
	 * This me used to check if the device has connection to the internet
	 * @return connection status
	 */
	 public Boolean hasConnection(){
		 Boolean isconn=false;
		 @SuppressWarnings("static-access")
		 ConnectivityManager connMgr = (ConnectivityManager) 
		 context.getSystemService(context.CONNECTIVITY_SERVICE);
		 NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		 if (networkInfo != null && networkInfo.isConnected()) {
			 // fetch data
			 isconn=true;
		 } else {
			 // display error
			 isconn=false;
		 }
		 return isconn;

	 }

	 /**
	  * This method is used to add enable multform to the httpclient
	  * @param isMultForm
	  */
	 public void isMultForm(Boolean isMultForm){
		 this.isMultForm=isMultForm;
	 }


	 /**
	  * This method is used to set the list displayer view layout.
	  * @param displayerId
	  */
	 public void setListDisplayer(int displayerId){
		 this.resource=displayerId;
	 }
	 

	 
	 /**
	  * This method is used to set simple list view data
	  * @param data
	  */
	 public void setData(ArrayList< HashMap<String, String>> data){
		 this.data=data;
	 }
	 
	 
	 /**
	  * Hashmap key value for main list textview and subtextview or other views. 
	  * @param itemKey
	  */
	 public void setViewItemKey(String[] itemKey){
		 this.itemKey=itemKey;
	 }
	 
	 
	 /**
	  * Array of TextView to hold listview data 
	  * @param textViewId
	  */
	 public void setTextViewId(int[] textViewId){
		 this.textViewid=textViewId;
	 }

	 /**
	  * This method is used to set layout inflator.
	  * @param inflates
	  */
	 public void setLayoutInflator(LayoutInflater inflates){
		 this.inflates=inflates;
	 }

	 /**
	  * This method is used to set given view layout.
	  * @param viewlayoutid
	  */
	 public void setViewLayoutId(int viewlayoutid){
		 this.viewlayoutid=viewlayoutid;
	 }
	 
	 
	 /**
	  * Option textview resource id usefull for simpleview adapter
	  * @param textViewResourceId
	  */
	 public void setTextViewResourceId(int textViewResourceId){
		 this.textViewResourceId=textViewResourceId;

	 }
	 
	 /**
	  * This method is used to enable or disable database deletion
	  * @param hastoDeleteDbData
	  */
	 public void hasDataToDelete(Boolean hastoDeleteDbData){
		this.hastoDelete=hastoDeleteDbData; 
	 }
	 
	 public void setAdpaterCursor(Cursor c){
	this.adapterCursor=c;	 
	 }
	 /**
	  * This is used to get information of database
	  * @param fieldId
	  * @param fieldValue
	  * @param table
	  */
	 public void setDbIdField(String fieldId,String fieldValue,String table){
		 this.fieldId.add(fieldId);
		 this.fieldValue.add(fieldValue);
		 this.table.add(table);
	 }
	 /**
	  * This is used to set mapkey of given hashmap json string
	  * @param mapKey
	  */
	 public void setMapKey(String[] mapKey){
		 this.mapKey=mapKey;
	 }
	 
	 
	 /**
	  * used to get post or get request.
	  * @author raphaelmartin
	  *
	  */
	 public enum RequestMethod
	 {
		 GET,
		 POST,
		 PUT,
		 DELETE
	 }
	 
	 /**
	  * this method return the response required.
	  * @return
	  */
	 public String getResponse() {
		 return this.response;
	 }

	 /**
	  * this method return the http error message when request fails.
	  * @return
	  */
	 public String getErrorMessage() {
		 return this.message;
	 }
	 
	 /**
	  * it used to return connection status to the server.
	  * @return
	  */
	 public Boolean getConnectionStatus(){
		 return this.connStatus;
	 }
     
	 /**
	  * This method used to return the status if completed or not
	  * @return true/false
	  */
	 public Boolean getLoadingStatus(){
		 return this.loadingCompleted;
	 }
	 
	 /**
	  * this method is used to get the response code
	  * @return
	  */
	 public int getResponseCode() {
		 return this.responseCode;
	 }
	 
	 /**
	  * This method used to add url parameter in name value pair format
	  * It is used to set the parameter for get or post request
	  * @param name
	  * @param value
	  */
	 public void AddParam(String name, String value)
	 {
		 params.add(new BasicNameValuePair(name, value));
	 }
	 
	 /**
	  * This method is used to set the progress bar 
	  * @param progressBar
	  */
	 public void setProgressBar(ProgressBar progressBar){
		 this.progressBar=progressBar;
	 }
	 
	 /**
	  * This method is used to set headers of the request in form of header name and header value.
	  * @param name
	  * @param value
	  */
	 public void AddHeader(String name, String value)
	 {
		 headers.add(new BasicNameValuePair(name, value));
	 }  
     
	 /**
	  * This method is used to set if the intended value will be in the list or not
	  * @param isListData
	  */
	 public void setIsListData(Boolean isListData){
		 this.isListData=isListData;
	 }
	 
	 /**
	  * It return true/false for listview 
	  * @return true/false
	  */
	 public Boolean getIsListData(){
		 return this.isListData;
	 }
	 
	 
	 public void addImageParam(String param){
		 this.imageParam.add(param);
	 }
    /**
     * This method is used to build image for uploading
     * @param imageUrl
     */
	 public void addImages(String imageUrl){
		 bitmap=BitmapFactory.decodeFile(imageUrl);
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 bitmap.compress(CompressFormat.JPEG, 20, bos);
	
		 byte[] data = bos.toByteArray();
		 bab = new ByteArrayBody(data,"profile.jpg");
		 mybobs.add(bab);
	 }
	 
	 public void addBitmap(Bitmap bitmap,String param){
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 bitmap.compress(CompressFormat.JPEG, 75, bos);
	
		 byte[] data = bos.toByteArray();
		 bab = new ByteArrayBody(data,"profile_"+_COUNT_IMAGE+".jpg");
		 bobs.put(param, bab);
		 mybobs.add(bab); 
		 _COUNT_IMAGE++;
	 }
	 
	 /**
	  * This is used to return list of created image ready for upload
	  * @return list of images
	  */
	 private   List<ByteArrayBody> getImage(){
		 return this.mybobs;
	 }
	 
	 /**
	  * this method is used to execute the given request using post or get method as specified
	  * @param method
	  * @throws Exception
	  */
	 public void runRequest(RequestMethod method) throws Exception
	 {
       switch(method) {
		 case GET:
		 {
			 //add parameters
			 Log.e("GET ","running request"+params);
			 String combinedParams = "";
			 if(!params.isEmpty()){
				 combinedParams += "?";
				 for(NameValuePair p : params)
				 {
					 String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),HTTP.UTF_8);
					 if(combinedParams.length() > 1)
					 {
						 combinedParams  +=  "&" + paramString;
					 }
					 else {
						 combinedParams += paramString;
					 }
				 } }

			 HttpGet request=new HttpGet();
			 request.setURI(new URI(url + combinedParams));
			 //add headers
			 for(NameValuePair h : headers)
			 {
				 request.addHeader(h.getName(), h.getValue());
			 }
			 executeRequest(request, url);
			 break; }
		 case POST: {
			 HttpPost request=new HttpPost(url);

			 MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			 //add headers
			 for(NameValuePair h : headers)
			 {
				 request.addHeader(h.getName(), h.getValue());

			 }
			 if(isMultForm){
				 for(NameValuePair h : params)
				 {

					 reqEntity.addPart(h.getName(), new StringBody(h.getValue()));

				 }
				 
				  if(bobs!=null){
					 for(String myparamz: imageParam){
						 ByteArrayBody images=bobs.get(myparamz);
						 reqEntity.addPart(myparamz, images);
						 
					 }
				  } 
				 
				 request.setEntity(reqEntity);
			 }else{
				 if(!params.isEmpty()){
					 request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				 }
			 }
			 executeRequest(request, url);
			 break;
		 }
		 case DELETE:
			 break;
		 case PUT:
			 break;
		 default:
			 break;
		 }
	 }
	 
	 /**
	  * this method is used to execute the given request from the given url.
	  * @param request
	  * @param url
	  */
	 private void executeRequest(HttpUriRequest request, String url) { 

		 HttpResponse httpResponse;
		 try {
			 this.connStatus=true;
			 Log.e("httpclien","executing request");
			 httpResponse = client.execute(request);
			 this.responseCode = httpResponse.getStatusLine().getStatusCode(); 
			 this.message = httpResponse.getStatusLine().getReasonPhrase();
			 // Closing the input stream will trigger connection release instream.close();
			 int statusCode=getResponseCode();
			 if(statusCode!=HttpStatus.SC_OK){
				 Log.e("Statuscode","Connected"+statusCode);
				 httpResponse.getEntity().getContent().close();
				 this.connStatus=false;
			 }else{
				 HttpEntity entity = httpResponse.getEntity(); if (entity != null) {
					 InputStream instream = entity.getContent();
					 long count=httpResponse.getEntity().getContentLength();
					 if(count>6000 || count==-1){
						 count=400;
					 }
					 Log.e("count is","counts"+count);
					 this.response = convertStreamToString(instream);
					 for(int i=0; i<count; i++){
						 publishProgress((int)(i/(float)count)*100);
					 }
					 if(statusCode==404){
						 if(context!=null){
							 AlertDialog.Builder builder=new AlertDialog.Builder(context);
							 builder.setMessage("Error! Connecting to the server");
							 builder.setCancelable(false);
							 builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

								 @Override
								 public void onClick(DialogInterface dialog, int which) {
									 dialog.cancel();
								 }
							 });
							 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

								 @Override
								 public void onClick(DialogInterface dialog, int which) {
									 dialog.cancel();
								 }
							 });
							 AlertDialog alert=builder.create();
							 alert.show();
						 }
					 }
					 Log.e("Statuscode","Connected"+statusCode);
					 this.connStatus=true;	
				 }

			 }
		 } catch (ClientProtocolException e) { 
			 this.connStatus=false;
			 cancel(true);
			 client.getConnectionManager().shutdown();
			 Log.e("ClientProtocolException", e.toString());
			 AlertDialog.Builder builder=new AlertDialog.Builder(context);
			 builder.setMessage("ClientProtocolException, "+e.toString());
			 builder.setCancelable(false);
			 builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 dialog.cancel();
				 }
			 });
			 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 dialog.cancel();
				 }
			 });
			 AlertDialog alert=builder.create();
			 alert.show();
		 } catch (IOException e) {
			 this.connStatus=false;
			 cancel(true);
			 client.getConnectionManager().shutdown(); 
			 AlertDialog.Builder builder=new AlertDialog.Builder(context);
			 builder.setMessage("IOException, "+e.toString());
			 builder.setCancelable(false);
			 builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 dialog.cancel();
				 }
			 });
			 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 dialog.cancel();
				 }
			 });
			 AlertDialog alert=builder.create();
			 alert.show();
			 Log.e("IOException", e.toString());
		 } 

	 }

	 /**
	  * this method ised to convert the given inputstream object to string
	  * it return the converted string
	  * @param is
	  * @return response
	  */
	 private  String convertStreamToString(InputStream is) {
		 BufferedReader reader = new BufferedReader(new InputStreamReader(is)); StringBuilder sb = new StringBuilder();
		 String line = null;
		 try {

			 while ((line = reader.readLine()) != null) { 

				 sb.append(line + "\n");

			 }
		 } catch (IOException e) {
			 Log.e("IOException ",e.toString());
		 } finally {
			 try {
				 is.close();
			 } catch (IOException e) {
				 Log.e("IOException",e.toString());
			 }
		 }
		 return sb.toString();
	 }

    /**
     * This method is used to build a jsonobject for http result
     * @param result
     * @return JSONObject
     */
	 public JSONObject getJsonOb(String result){
		 JSONObject jArray=null;
		 try{
			 if(result!=null){
				 jArray=new JSONObject(result);
				 this.goodJSON=true;
				 return jArray;
			 }else{
				 return null;	
			 }
		 }catch(JSONException e){
			 this.goodJSON=false;
			 Log.e("log_tag", "Error parsing data "+e.toString());
		 }
		 return null;
	 }

	/**
	 * This method is used to build and return ArralList HashMap of jsonobject results
	 * @param jsonDataSource
	 * @param jsonArrayName
	 * @param mapKey
	 * @param jsonElementName
	 * @return  ArrayList<HashMap<String,String>>
	 */
	 public ArrayList<HashMap<String,String>> myJsonArrayList(String jsonDataSource,String jsonArrayName,String[] mapKey,String[] jsonElementName){
		 ArrayList<HashMap<String,String>> dataList=new ArrayList<HashMap<String,String>>();
		 JSONObject json=this.getJsonOb(jsonDataSource);
		 try {
			 JSONArray myArray=null;
			 if(jsonDataSource!=null){
				 if((!jsonDataSource.equals("")) && (this.goodJSON)){

					 myArray=json.getJSONArray(jsonArrayName);
				 }else{
					 String datasource="{'nodata':[{'datakey1':'No Data','datakey2':'No data'}]}";
					 json=this.getJsonOb(datasource);
					 myArray=json.getJSONArray("nodata");
					 String keys[]={"datakey1","datakey2"};
					 this.mapKey=keys;
				 }
			 }
			 if(myArray!=null){

				 for(int i=0; i<myArray.length(); i++){
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
			 }
			 //Log.e("sources",dataList.get(0).get("name"));
			 return dataList;
		 } catch (JSONException e) {
			 this.goodJSON=false;
			 cancel(true);
			 Log.e("JSONException",e.toString());
		 }

		 return null;
	 }

	 /**
	  * This method is used to build and return ArrayList of http data.
	  * @param jsonResults
	  * @param jsonArray
	  * @param itemName
	  * @return ArrayList<String>
	  */
	 public ArrayList<String> arrayListData(String jsonResults,String jsonArray,String itemName){
		 ArrayList<String> resultList = null;
		 try {
			 // Create a JSON object hierarchy from the results
			 JSONObject jsonObj = new JSONObject(jsonResults.toString()); 
			 JSONArray predsJsonArray = jsonObj.getJSONArray(jsonArray);
			 // Extract the Place descriptions from the results
			 resultList = new ArrayList<String>(predsJsonArray.length()); 
			 for (int i = 0; i < predsJsonArray.length(); i++) {
				 resultList.add(predsJsonArray.getJSONObject(i).getString(itemName));
				 Log.e("ArrayList",""+predsJsonArray.getJSONObject(i).getString(itemName));
			 }
		 } catch (JSONException e) {
			 Log.e("Failed", "Cannot process JSON results", e);
		 }
		 return resultList;

	 }
	 @Override
	 protected void onPreExecute(){
		myProgresDialog();
		
		 if(this.progressBar!=null){
			 this.progressBar.setVisibility(View.VISIBLE);
		 }
	 }

	 /**
	  * This method is used to build and return vector of string from http result
	  * @param jsonResults
	  * @param jsonArray
	  * @param itemName
	  * @return Vector<String>
	  */
	 public Vector<String> arrayOfString(String jsonResults,String jsonArray,String itemName){
		 Vector<String> resultList =new Vector<String>();
		 try {
			 // Create a JSON object hierarchy from the results
			 JSONObject jsonObj = new JSONObject(jsonResults.toString()); 
			 JSONArray predsJsonArray = jsonObj.getJSONArray(jsonArray);
			 // Extract the Place descriptions from the results

			 for (int i = 0; i < predsJsonArray.length(); i++) {
				 resultList.add(predsJsonArray.getJSONObject(i).getString(itemName));

				 Log.e("ArrayList",""+predsJsonArray.getJSONObject(i).getString(itemName));
			 }
		 } catch (JSONException e) {
			 Log.e("Failed", "Cannot process JSON results", e);
		 }
		 return resultList;
	 }
	 
	 /**
	  * this method is used to execute the given request in background.
	  */
	 @Override
	 protected String doInBackground(String... requestmethod) {
		 if(requestmethod[0].toLowerCase().equals("post")){
			 try {
				 runRequest(RequestMethod.POST);
				 this.connStatus=true;
			 } catch (Exception e) {
				 cancel(true);
				 Log.e("POST error", e.toString());
			 }
		 }else{
			 try {
				 runRequest(RequestMethod.GET);
				 this.connStatus=true;
			 } catch (Exception e) {
				 cancel(true);
				 Log.e("GET error", e.toString());
			 }	
		 }
		
		 return this.response;
	 }

	 /**
	  * This used to update progress bar
	  */
	 protected void onProgressUpdate(Integer...progress){
		
		 if(progress[0]!=100){
			 if(this.progressBar!=null){
				 this.progressBar.setVisibility(View.VISIBLE);
			 }

		 }else{
			 this.loadingCompleted=true;
			 if(this.progressBar!=null){
				 this.progressBar.setVisibility(View.GONE);
			 }


		 }
		 i++;

	 }
	 @Override
	 protected void onCancelled(){
		 Log.e("cancelled","true");
		 this.connStatus=false;
		 if(dialog!=null){
			 dialog.dismiss();
		 }
	 }


	protected void onPostExecute(String response){
		 Log.e("PostExec","data is"+response);
		 if(response==null){
			 showToastFailSMS();
		 }else{
		 showToastSuccessSMS();
		 }
        if(this.pd!=null){
        this.pd.setCancelable(true);
        this.pd.cancel();	
        }
		 if(this.hastoDelete && response!=null){
			 if((this.table!=null && this.fieldId!=null && this.fieldValue!=null)){
				 for(int i=0; i<table.size(); i++){
			DatabaseOperation po=new DatabaseOperation(this.context);
			po.deleteFomuData(this.table.get(i),this.fieldId.get(i), this.fieldValue.get(i));
			
			po.close();
			
				 }
				
			 }
		 }
		 
		 
		 this.connStatus=true;
		 this.response=response;
         
		 this.loadingCompleted=true;
		 if(dialog!=null){
			 dialog.dismiss();
		 }
		 if((!isCancelled() )&&getResponseCode()==200){
			 if(this.progressBar!=null){
				 this.progressBar.setVisibility(View.GONE);
			 }
			 if(this.mapKey!=null && this.jsonArrayName!=null){
				 this.data=myJsonArrayList(response, this.jsonArrayName, this.mapKey, this.mapKey);
			 }
			 if(this.goodJSON && this.getIsListData()){
					 //create listview adapter
					 createListViewAdapter();
					 //affect database
					 toDatabase();
					 //affect spinner adapter
					 spinnerAdapter();

			 }else if(this.goodJSON){

			 }else{
				 Log.e("Bad JSON","Bad JSON array");
			 }
		 }
	 }


	 /**
	  * This method is used to create a listview data.
	  */
	 private void createListViewAdapter(){
		 if(this.listView!=null){
		 MySimpleAdapter simple=new MySimpleAdapter(context,data,resource,this.itemKey,this.textViewid,this.inflates,this.viewlayoutid,this.ListViewId,this.mapKey);
		 Log.e("eachclick","It was bad idea");
		 this.textviews=simple.getTextViewImage();
		 this.addcart=simple.getCartTextViewBtn();
		 this.addplaylist=simple.getAddPlaylistBtn();
		 this.loadingIcon=simple.getLoadIcon();
		 simple.setItemImage(getItemImage());
		 simple.setIconCart(getCartTextView());
		 simple.setIconAddPlaylist(getAddPlaylistTextView());
		 simple.setItemIndexView(getItemIndexView());
		 simple.setImageIconId(getImageIconId(), getImageViewId());
		 simple.setItemLoadIcon(getItemLoadIcon());
		 //   	this.JsonData= simple.getItemArray();
		 listView.setAdapter(simple);
		 }
	 }

	 public void setDBCode(int code){
		 this.dbcode=code;
	 }
	 /**
	  * This insert given data to the local database
	  */
	 private void toDatabase(){
      if(this.toDb && context!=null){
    	  //do db process here.
    	  ArrayList<String> resultList = null;
    	  switch(this.dbcode){
    	  case 100:
    		  //insert huduma
    		  resultList=arrayListData(this.getResData(),"huduma","huduma");
    		  if(resultList!=null){
    	    		for(String mydata: resultList){
    	    			  db=new DatabaseOperation(context);
    	    		      db.insertHuduma(mydata);
    	    		    	db.close();	
    	    		}
    		  }
    		  break;
    	  case 101:
    		  //insert muda
    		  resultList=arrayListData(this.getResData(),"mudamalipo","mudamalipo");
    		  if(resultList!=null){
    	    		for(String mydata: resultList){
    	    			  db=new DatabaseOperation(context);
    	    		      db.insertMuda(mydata);
    	    		    	db.close();	
    	    		}
    		  }
    		  break;
    	  case 102:
    		  //insert mikoa
    		  resultList=arrayListData(this.getResData(),"mikoa","mikoa");
    		  if(resultList!=null){
    	    		for(String mydata: resultList){
    	    			  db=new DatabaseOperation(context);
    	    		      db.insertMkoa(mydata);
    	    		    	db.close();	
    	    		}
    		  }
    		  break;
    	  case 103:
    		  //insert wilaya
    		  break;
    	  case 104:
    		  //insert kata
    		  break;
    	  default:
    		 break;
    	  }
      
    	  
      }
	 }
	 
	 private void spinnerAdapter(){
		 //add data to given spinner adapter
	 }

	 /**
	  * This method is used to return response
	  * @return
	  */
	 public String getResData(){
		 return this.response;
	 }

	 /**
	  * 
	  * @return arraylist<HashMap<String,String> data
	  */
	 public ArrayList<HashMap<String,String>> getData(){
		 return myJsonArrayList(this.response, this.jsonArrayName, this.mapKey, this.mapKey);	
	 }
	 /**
	  * This method is used to set image icon to display on left side of list item song
	  * @param imageIconId
	  * @param imageViewId
	  */
	 public void setImageIconId(int imageIconId,int imageViewId){
		 this.imageiconid=imageIconId;
		 this.imageViewId=imageViewId;
	 }

	 /**
	  * This method is used to set the loadingIcon or progress of each item in the list.
	  * @param loadingIconId
	  */
	 public void setItemLoadIcon(int loadingIconId){
		 this.loadingIconId=loadingIconId;
	 }

	 /**
	  * This method is  used to get the id of loadingIcon
	  * @return
	  */
	 public int getItemLoadIcon(){
		 return this.loadingIconId;
	 }

	 /**
	  * this method is used to return the list of all loading icon per item
	  * @return
	  */
	 public ArrayList<ProgressBar> getLoadIcon(){
		 return this.loadingIcon;
	 }
	 /**
	  * This is used to return an id of image icon
	  * @return
	  */
	 public int getImageIconId(){
		 return this.imageiconid;
	 }
	 public int getImageViewId(){
		 return this.imageViewId;
	 }

	 public void setItemIndexView(int itemIndexId){
		 this.itemIndex=itemIndexId;

	 }

	 public int getItemIndexView(){
		 return itemIndex;
	 }

	 public void setItemImage(int itemImageId){
		 this.itemButtonId=itemImageId;
	 }

	 public int getItemImage(){
		 return itemButtonId;
	 }
	 /**
	  * This method is used in artist activity to add song to playlist or cart
	  * @param textViewCarts
	  * @param textViewAddPlaylist
	  */
	 public void setIconCart(int  textViewCarts){
		 this.addtocart=textViewCarts;

	 }

	 public void setIconAddPlaylist(int textViewAddPlaylist){
		 this.addtoPlaylist=textViewAddPlaylist;
	 }

	 /**
	  * this method is used to return carttextview id
	  * @return int carttextview id
	  */
	 public int getCartTextView(){
		 return addtocart;
	 }
	 /**
	  * this method is used to return the array list of button add cart
	  * @return
	  */
	 public ArrayList<TextView> getCartTextViewBtn(){
		 return this.addcart;
	 }
	 /**
	  * This method is used to return array list of button add to playlist
	  * @return
	  */
	 public ArrayList<TextView> getAddPlaylistBtn(){
		 return this.addplaylist;
	 }
	 /**
	  * this method is used to return playlist textview id
	  * @return int playlist textview id
	  */
	 public int getAddPlaylistTextView(){
		 return addtoPlaylist;
	 }
	 public ArrayList<TextView> getTextViewImage(){
		 return textviews;
	 }

	 public void showToastSuccessSMS(){
		 if(this.context!=null){
			 Toast.makeText(this.context,this.toastSuccessSMS, Toast.LENGTH_LONG).show();
		 }

	 }
	 public void showToastFailSMS(){
		 if(this.context!=null){
			 Toast.makeText(this.context,this.toastFailSMS, Toast.LENGTH_LONG).show();
		 }

	 }
	 
	 
	 public void setToastSuccessSMS(String sms){
		this.toastSuccessSMS=sms; 
	 }
	 
	 public void setToastFailSMS(String sms){
			this.toastFailSMS=sms; 
		 }
	 
	 public void setToastSMS(String sms){
		 if(this.context!=null){
			 Toast.makeText(this.context,sms, Toast.LENGTH_LONG).show();
		 }

	 }
	 
}


