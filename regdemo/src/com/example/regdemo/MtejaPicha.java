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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MtejaPicha extends Activity{
	private static final int MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUriMteja;
	private LayoutInflater inflater;
	private String urls;
	private String results="";
	private HashMap<String,String> myData;
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private String[] frompageone;
	private Bitmap bitmap;
	private  Button sendData;
	private ProgressBar progress;
	private ImageView mtejaView;
	String url="";
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mtejapicha);
	        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 urls=this.getResources().getString(R.string.apiURL);
	        Intent pageone=getIntent();
	        progress=(ProgressBar)findViewById(R.id.progress);
	       
	        mtejaView=(ImageView)findViewById(R.id.mtejaview);
	    frompageone= pageone.getStringArrayExtra("pageone");
	
	   
	    }

	 public void pichaMteja(View v){
		// create Intent to take a picture and return control to the calling application
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    fileUriMteja = CameraProcess.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMteja); // set the image file name

		    // start the image capture Intent
		    startActivityForResult(intent, MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);  
	 }
	 
	 public void sendPicha(View v){
		 dataToSend();	 
	 }
     private void resetView(){

     }
	 private void dataToSend(){

				ClientWebService register=new ClientWebService(urls,MtejaPicha.this,inflater,"data",false);
				register.setProgressBar(progress);
				  register.AddParam("data", "");
			       register.AddParam("action", "register5");
			       register.AddParam("mratibu", "2");
			       register.AddParam("id",frompageone[0]);
			       register.addImages(fileUriMteja.getEncodedPath());
			       register.isMultForm(true);
					 String[] mapkey={"refId"};
				     register.setMapKey(mapkey);
				     
					 register.execute("post");
					 try {
						 resetView();
						 if( register.getResponseCode()!=500 && register.getResponseCode()!=503 && register.getResponseCode()!=404 && register.getResponseCode()!=408 ){
								
						 results=register.get();
						  
						 }else{
							 Log.e("not arrowed","errors");
						 }
						 String value=String.valueOf(results);
						 if(!value.trim().equals("false")){
							
							
							if(results!=null){
								logData=register.getData();
								
					           if(logData!=null && logData.size()>0){
			              	  
			    					myData=logData.get(0);
			    					
			    					Intent intent=new Intent(MtejaPicha.this,Mdhamini.class);
			    			        intent.putExtra("pageone", frompageone);
			    			    	   startActivity(intent);
			    				
			    				}
							}
						 }
					 }catch (InterruptedException e) {} catch (ExecutionException e) {}
			
				
		
	 }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        
	        if (requestCode == MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            	 
	            	 bitmap=BitmapFactory.decodeFile(fileUriMteja.getEncodedPath());
	            	mtejaView.setImageBitmap(bitmap);
	               // Toast.makeText(NextPage.this, "Image saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(MtejaPicha.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
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
