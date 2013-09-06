package com.example.regdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Mdhamini extends Activity{
	private static final int MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUriMdhamini;
	private LayoutInflater inflater;
	private String urls;
	private String results="";
	private HashMap<String,String> myData;
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private String[] frompageone;
	private Bitmap bitmap;
	private ImageView mdhaminiView;
	private ProgressBar progress;
	private EditText mdhamini,simumdhamini;
	String url="";
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mdhamini);
	        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 urls=this.getResources().getString(R.string.apiURL);
	        Intent pageone=getIntent();
	        progress=(ProgressBar)findViewById(R.id.progress);
	        
	        mdhamini=(EditText)findViewById(R.id.jinamdhamini);
	        simumdhamini=(EditText)findViewById(R.id.simumdhamini);
	        mdhaminiView=(ImageView)findViewById(R.id.mdhaminiview);
	    frompageone= pageone.getStringArrayExtra("pageone");
	    
	
	   
	    }
	 public void sendMdhaminiData(View v){
		 dataToSend(); 
	 }
	 public void pichaMdhamini(View v){
			// create Intent to take a picture and return control to the calling application
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    fileUriMdhamini = CameraProcess.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMdhamini); // set the image file name

		    // start the image capture Intent
		    startActivityForResult(intent, MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); 
	 }
	 

     private void resetView(){
    	 mdhamini.setText("");
    	 simumdhamini.setText("");
     }
	 private void dataToSend(){

			String familia="";
	String jina=mdhamini.getText().toString();
	String simu=simumdhamini.getText().toString();

				ClientWebService register=new ClientWebService(urls,Mdhamini.this,inflater,"data",false);
				  register.AddParam("data", familia);
				  register.setProgressBar(progress);
			       register.AddParam("action", "register4");
			       register.AddParam("mdhamini", jina);
			       register.AddParam("mdhaminisimu", simu);
			       register.AddParam("mratibu", "2");
			       register.AddParam("id",frompageone[0]);
			       register.addImages(fileUriMdhamini.getEncodedPath());
			       register.isMultForm(true);
					 String[] mapkey={"refId"};
				     register.setMapKey(mapkey);
				   if(jina.equals("") || simu.equals("")){
					   register.setToastSMS("Tafadhari jaza form yote");
				   }else{
					 register.execute("post");
					 try {
						 resetView();
						 if(register.getResponseCode()!=500 && register.getResponseCode()!=503 && register.getResponseCode()!=404 && register.getResponseCode()!=408 ){
							 
							 results=register.get();
						 }
						 String value=String.valueOf(results);
						 if(!value.trim().equals("false")){
							
							
							if(results!=null){
								logData=register.getData();
					           if(logData!=null && logData.size()>0){
			              	  
			    					myData=logData.get(0);
			    					
			    					Intent intent=new Intent(Mdhamini.this,MainActivity.class);
			    			        intent.putExtra("pageone", frompageone);
			    			    	   startActivity(intent);
			    				
			    				}
							}
						 }
					 }catch (InterruptedException e) {} catch (ExecutionException e) {}
				   }
				
		
	 }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            	 
	            	 bitmap=BitmapFactory.decodeFile(fileUriMdhamini.getEncodedPath());
	            	mdhaminiView.setImageBitmap(bitmap);
	               // Toast.makeText(NextPage.this, "Image saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(Mdhamini.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
	                // User cancelled the image capture
	            } else {
	                // Image capture failed, advise user
	            }
	        }
	       

	        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Video captured and saved to fileUri specified in the Intent
	                //Toast.makeText(NextPage.this, "Video saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	                // User cancelled the video capture
	            } else {
	                // Video capture failed, advise user
	            }
	        }
	    }
	  
	    
	
	    
}
