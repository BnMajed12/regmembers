package com.example.regdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NextPage extends Activity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	//private LayoutInflater inflater;
	//private String urls;
	//private String results="";
	//private HashMap<String,String> myData;
	//private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private String[] frompageone; //watoto;
	private Bitmap bitmap;
	private  Button sendData;
	private ImageView image;
	private CheckBox umeoa;
	private DatabaseOperation db=null;
	private EditText idadiWatoto;
	private int myWidth;
	int _intMyLineCount;
	LinearLayout LLEnterText,UmeoaEnterText;
    private List<EditText> editListUmeoa = new ArrayList<EditText>();
    private List<EditText> editListWatoto = new ArrayList<EditText>();
    private List<RelativeLayout> linearlayoutList=new ArrayList<RelativeLayout>();
	String url="";
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nextpage);
	       // inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 //urls=this.getResources().getString(R.string.apiURL);
	        Intent pageone=getIntent();
	    frompageone= pageone.getStringArrayExtra("pageone");
	    LLEnterText=(LinearLayout) findViewById(R.id.chumbawatoto);
	    UmeoaEnterText=(LinearLayout)findViewById(R.id.chumbakuoa);
	    idadiWatoto=(EditText)findViewById(R.id.idadiwatoto);
	    umeoa=(CheckBox)findViewById(R.id.umeoa);
	    sendData=(Button)findViewById(R.id.inayofuata);
	    //tunatuma data hapa
	    sendData.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dataToSend();
			}
	    	
	    });
	    
	    //Tumeoweka check action ili kuweka field ya jina la mke au mume.
	  umeoa.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			Boolean kaoa=umeoa.isChecked();
			myWidth=idadiWatoto.getWidth();
			Log.e("Umeoa checked","NImeoa");
			if(kaoa){
				String hint="Jina La Mume/Mke";
				 UmeoaEnterText.addView(linearlayout(400,hint,myWidth,editListUmeoa));	
			}else{
				 UmeoaEnterText.removeViewAt(0);	
			}
			
		}
	  });
	  

	    //tunaongeza field kulingana na idadi ya watoto.
	    idadiWatoto.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {
				String idadi=idadiWatoto.getText().toString().trim();
				myWidth=idadiWatoto.getWidth();
				 int childs=LLEnterText.getChildCount();
		    	 if(childs>0){
		    		LLEnterText.removeAllViews();
		    		for(int i=0; i<editListWatoto.size(); i++){
		    		editListWatoto.remove(i);
		    		}
		    	 }
				if(!idadi.equals("")){
					  int idadiW=Integer.parseInt(idadi);
					    if(idadiW>0){
					    	for(int i=1; i<=idadiW; i++){
					    		String hint="Jina Mtoto "+i;
					    		Log.e("Addview","My editText");
					    		   LLEnterText.addView(linearlayout(_intMyLineCount,hint,myWidth,editListWatoto));
					    		   _intMyLineCount=i;
					    	}
					    }

				}else{
					  LLEnterText.removeAllViews();	
				    	 if(childs>0){
				    		LLEnterText.removeAllViews();
				    		for(int i=0; i<editListWatoto.size(); i++){
				    		editListWatoto.remove(i);
				    		}
				    	 }
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				LLEnterText.removeAllViews();	
				int childs=LLEnterText.getChildCount();
		    	 if(childs>0){
		    		LLEnterText.removeAllViews();
		    		for(int i=0; i<editListWatoto.size(); i++){
		    		editListWatoto.remove(i);
		    		}
		    	 }
				
			}
	    	   
	       });
	   
	    }
     public void resetView(){
    	 idadiWatoto.setText("");
    	 umeoa.setChecked(false);
    	 int childc= UmeoaEnterText.getChildCount();
    	 if(childc>0){
    		 UmeoaEnterText.removeAllViews(); 
    		 for(int i=0; i<editListUmeoa.size(); i++){
    	    		editListUmeoa.remove(i);
    	    		}
    	 }
    	 int childs=LLEnterText.getChildCount();
    	 if(childs>0){
    		LLEnterText.removeAllViews();
    		for(int i=0; i<editListWatoto.size(); i++){
    		editListWatoto.remove(i);
    		}
    	 }
     }
	 private void dataToSend(){
		String watoto=idadiWatoto.getText().toString().trim();
		//String myChild="";
		//String familia="{\"userfamily\":[";
		//String kuoa="";
		int childs=0;
		int childc=0;
		;
		
		Boolean umeoaz=umeoa.isChecked();
	
			 if(umeoaz){
			childc= UmeoaEnterText.getChildCount();
			 }
			       if(watoto.matches("\\d") && Integer.parseInt(watoto)>=1){
			    	   childs=LLEnterText.getChildCount();
						if(childs>0){
			                 for (EditText editText : editListWatoto) {
						   String[] kids={frompageone[0],editText.getText().toString(),"mtoto"};
						   db=new DatabaseOperation(NextPage.this);
						   db.insertData(kids, "family");
						   db.close();
		               
		                     }
						}
			       }
						
						if(childc>0){
							
							 //EditText myText=(EditText)UmeoaEnterText.getChildAt(0);
							 for (EditText myText : editListUmeoa) {
								  String[] mke={frompageone[0],myText.getText().toString(),"mke"};
								   db=new DatabaseOperation(NextPage.this);
								   db.insertData(mke, "family");
								   db.close();
							 }
							 }
						 Intent intent=new Intent(NextPage.this,Biashara.class);
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
	        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            	 
	            	 bitmap=BitmapFactory.decodeFile(fileUri.getEncodedPath());
	            	image.setImageBitmap(bitmap);
	               // Toast.makeText(NextPage.this, "Image saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(NextPage.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
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
	  
	    
	    private EditText editText(int _intID,String hitText,int width,List<EditText> editList) {
            EditText editText = new EditText(this);
            editText.setId(_intID);
            editText.setHint(hitText);
            editText.setMinWidth(LLEnterText.getWidth());
            editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            editList.add(editText);
            
            return editText;
        }
	    
	    private RelativeLayout linearlayout(int _intID,String editTextHint,int width,List<EditText> editList)
	    {
	    	int layId=(_intID +5)* 3;
	        RelativeLayout LLMain=new RelativeLayout(this);
	        LLMain.setId(layId);
	       // LLMain.addView(textView(_intID));
	        LLMain.addView(editText(_intID,editTextHint,width,editList));
	       // LLMain.setOrientation(LinearLayout.HORIZONTAL);
	        LLMain.refreshDrawableState();
	        linearlayoutList.add(LLMain);
	        return LLMain;

	    }
	    
}
