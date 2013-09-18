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
	private DatabaseOperation db=null;
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
		    Log.e("imageurl",fileUriMdhamini.toString());
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMdhamini); // set the image file name

		    // start the image capture Intent
		    startActivityForResult(intent, MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); 
	 }
	 

     private void resetView(){
    	 mdhamini.setText("");
    	 simumdhamini.setText("");
     }
	 private void dataToSend(){
	String jina=mdhamini.getText().toString();
	String simu=simumdhamini.getText().toString();

				ClientWebService register=new ClientWebService(urls,Mdhamini.this,inflater,"data",false);
				   if(jina.equals("") || simu.equals("")){
					   register.setToastSMS("Tafadhari jaza form yote");
				   }else{
					   resetView();
					 String[] data={frompageone[0],jina,simu,fileUriMdhamini.getEncodedPath()};
					 db=new DatabaseOperation(Mdhamini.this);
					 db.insertData(data, "mdhamini");
					  db.close();
					  Intent intent=new Intent(Mdhamini.this,HomePage.class);
  			        intent.putExtra("pageone", frompageone);
  			    	   startActivity(intent);
					 
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
	            	 
	            	 if(data!=null){
	            	 String files=fileUriMdhamini.getEncodedPath();
	            	 bitmap=BitmapFactory.decodeFile(files);
	            	mdhaminiView.setImageBitmap(bitmap);
	            	 }else{
	            		 if(this.fileUriMdhamini!=null){
	            			 String files=this.fileUriMdhamini.getEncodedPath();
	    	            	 bitmap=BitmapFactory.decodeFile(files);
	    	            	mdhaminiView.setImageBitmap(bitmap); 
	            		 }else{
	            			 
	              Toast.makeText(Mdhamini.this, "Picha haija hifadhiwa Tafadhari Piga Nyingine", Toast.LENGTH_LONG).show();
	            		 }
	            		 }
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
