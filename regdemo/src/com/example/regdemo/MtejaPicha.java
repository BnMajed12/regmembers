package com.example.regdemo;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MtejaPicha extends Activity{
	private static final int MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUriMteja;
	//private LayoutInflater inflater;
	//private String urls;
	//private String results="";
	//private HashMap<String,String> myData;
	//private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private DatabaseOperation db=null;
	private String[] frompageone;
	private Bitmap bitmap;
	//private  Button sendData;
	//private ProgressBar progress;
	private ImageView mtejaView;
	String url="";
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mtejapicha);
	       // inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// urls=this.getResources().getString(R.string.apiURL);
	        Intent pageone=getIntent();
	      //  progress=(ProgressBar)findViewById(R.id.progress);
	       
	        mtejaView=(ImageView)findViewById(R.id.mtejaview);
	    frompageone= pageone.getStringArrayExtra("pageone");
	
	   
	    }

	 public void pichaMteja(View v){
		// create Intent to take a picture and return control to the calling application
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    fileUriMteja = CameraProcess.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		    Log.e("imageurl",fileUriMteja.toString());
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMteja); // set the image file name

		    // start the image capture Intent
		    startActivityForResult(intent, MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);  
	 }
	 
	 public void sendPicha(View v){
		 dataToSend();	 
	 }

	 private void dataToSend(){
              HashMap<String,Object> data=new HashMap<String,Object>();
              data.put("imageurl", fileUriMteja.getEncodedPath());
              db=new DatabaseOperation(MtejaPicha.this);
              db.updateData(data, "profile", "id", frompageone[0]);
              Intent intent=new Intent(MtejaPicha.this,Mdhamini.class);
		        intent.putExtra("pageone", frompageone);
		    	   startActivity(intent);	
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
	            	
	            	 if(data!=null){
	            	
	            	 }else{
	            		 if(this.fileUriMteja!=null){
	            			 String files=this.fileUriMteja.getEncodedPath();
	    	            	 bitmap=BitmapFactory.decodeFile(files);
	    	            	mtejaView.setImageBitmap(bitmap); 
	            		 }else{
	            Toast.makeText(MtejaPicha.this,"Picha haija hifadhiwa Tafadhari Piga Nyingine", Toast.LENGTH_LONG).show();
	            		 }  	 
	            	 }
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
