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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Biashara extends Activity implements OnItemSelectedListener {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private LayoutInflater inflater;
	private String urls;
	private String results="";
	private EditText biashara,kikundi,banki,kiwango,kianzio,maliposiku;
	private Spinner spinHuduma,spinMuda;
	 Utilities hudumu,lipamuda;
	private HashMap<String,String> myData;
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private String[] frompageone;
	private Bitmap bitmap;
	private  Button sendData;
	private ImageView image;
	
   
	String url="";
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.biashara);
	        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 urls=this.getResources().getString(R.string.apiURL);
	        Intent pageone=getIntent();
	    frompageone= pageone.getStringArrayExtra("pageone");
	    createItems();
	   hudumu=new Utilities();
	   lipamuda=new Utilities();
	    spinHuduma=(Spinner)findViewById(R.id.huduma);
	    spinMuda=(Spinner)findViewById(R.id.mudamalipo);
		 spinHuduma.setOnItemSelectedListener(this);
		 spinHuduma.setOnItemSelectedListener(this);
		 hudumu.addSpinnerData(spinHuduma, new ArrayAdapter<String>(Biashara.this,
	    			android.R.layout.simple_spinner_item, hudumu.getSpinnerData(urls,"huduma","","")));
		 lipamuda.addSpinnerData(spinMuda, new ArrayAdapter<String>(Biashara.this,
	    			android.R.layout.simple_spinner_item, hudumu.getSpinnerData(urls,"mudamalipo","","")));
	    sendData=(Button)findViewById(R.id.inayofuata);
	    //tunatuma data hapa
	    sendData.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dataToSend();
			}
	    	
	    });
	    
	    }
	 
	 private void createItems(){
	    	biashara=(EditText)findViewById(R.id.biashara);
	    	kikundi=(EditText)findViewById(R.id.kikundi);
	    	kiwango=(EditText)findViewById(R.id.ombikiwango);
	    	kianzio=(EditText)findViewById(R.id.kuanziakiwango);
	    	banki=(EditText)findViewById(R.id.banki);
	    	maliposiku=(EditText)findViewById(R.id.maliposiku);
	    }
	 
     private void resetView(){
      biashara.setText("");
      kikundi.setText("");
      kiwango.setText("");
      kianzio.setText("");
      banki.setText("");
      maliposiku.setText("");
     }
     
	 private void dataToSend(){
		 String biasharaz=biashara.getText().toString();
		 String kikundiz=kikundi.getText().toString();
		 String kiwangoz=kiwango.getText().toString();
		 String kianzioz=kianzio.getText().toString();
		 String bankiz=banki.getText().toString();
		 String malipo=maliposiku.getText().toString();
		String huduma=spinHuduma.getSelectedItem().toString();
		String mudaz=spinMuda.getSelectedItem().toString();
		
		
		if(biasharaz.equals("") && kiwangoz.equals("") && kianzioz.equals("") && malipo.equals("")){
		//some fields are empty.	
		}else{
			String familia="{\"userbusiness\":[{\"biashara\":\""+biasharaz+"\",\"kikundi\":\""+kikundiz+"\",\"banki\":\""+bankiz+"\",";
			       familia+="\"kiwango\":\""+kiwangoz+"\",\"kianzio\":\""+kianzioz+"\",\"maliposiku\":\""+malipo+"\",";
			       familia+="\"huduma\":\""+huduma+"\",\"mudamalipo\":\""+mudaz+"\",\"id\":\""+frompageone[0]+"\",\"mratibu\":\"2\"}]}";
	             Log.e("famili",familia);
			   	if(!familia.equals("")){
					ClientWebService register=new ClientWebService(urls,Biashara.this,inflater,"data",false);
					  register.AddParam("data", familia);
				       register.AddParam("action", "register3");
				       
						 String[] mapkey={"refId"};
					     register.setMapKey(mapkey);
						 register.execute("post");
						 try {
							 resetView();
							 results=register.get();
							 String value=String.valueOf(results);
							 if(!value.trim().equals("false")){
								
								
								if(results!=null){
									logData=register.getData();
						           if(logData!=null && logData.size()>0){
				              	  
				    					myData=logData.get(0);
				    					String userid=myData.get("refId");
				    					Intent intent=new Intent(Biashara.this,MtejaPicha.class);
				    			        intent.putExtra("pageone", frompageone);
				    			    	   startActivity(intent);
				    					Log.e("Hash","online :"+userid);
				    				}
								}
							 }
						 }catch (InterruptedException e) {} catch (ExecutionException e) {}
				}
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
	        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            	 
	            	 bitmap=BitmapFactory.decodeFile(fileUri.getEncodedPath());
	            	image.setImageBitmap(bitmap);
	               // Toast.makeText(NextPage.this, "Image saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(Biashara.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
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
	  
	    
	
	  

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2,
				long arg3) {
			int hudumaz=R.id.huduma;
			int mudaz=R.id.mudamalipo;
			
		   int id=adapterView.getId();
			if(hudumaz==id){
				
				
			}
			if(mudaz==id){
				
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

			
		}
	    
}
