package com.example.regdemo;







import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnItemSelectedListener, OnClickListener {
	private LayoutInflater inflater;
	private String urls;
	private String results="";
	
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	private HashMap<String,String> myData;
	private EditText jina,tareheKuzaliwa,simu,nambaKitambulisho,ainaKitambulisho;
	private AutoCompleteTextView mkoa,wilaya,kata;
	private RadioGroup jinsia;
	private RadioButton mke,mme;
	private Button  sendButton;
	private String huumkoa,hiiwilaya;
	private Spinner spinMkoa,spinWilaya,spinKata;
	private ArrayAdapter<String> mkoaAdapt,wilayaAdapt,kataAdapt;
	private String ids;
	//private ProgressBar barprogress=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Button inayofuata=(Button)findViewById(R.id.inayofuata);
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 urls=this.getResources().getString(R.string.apiURL);
		 //barprogress=(ProgressBar)findViewById(R.id.inaload);
		 //MyAdapter(this,int spinner, int textViewResourceId,String[] objects,LayoutInflater inflates,int viewlayoutid)
		 spinMkoa=(Spinner)findViewById(R.id.mkoa);
		 spinWilaya=(Spinner)findViewById(R.id.wilaya);
		 spinKata=(Spinner)findViewById(R.id.kata);
		 spinMkoa.setOnItemSelectedListener(this);
		 spinWilaya.setOnItemSelectedListener(this);
		 spinKata.setOnItemSelectedListener(this);
		 mkoaAdapt=new ArrayAdapter<String>(this,
	    			android.R.layout.simple_spinner_item, getSpinnerData("mikoa","",""));
		 addSpinnerData(spinMkoa,mkoaAdapt);
		createItems();
		sendButton.setOnClickListener(this);
		//mkoa.setAdapter(new  MyAutoComplete(this,R.layout.autocomplete_list,urls,"","mikoa","mikoa"));
		//mkoa.setOnItemClickListener(this);
		//wilaya.setOnItemClickListener(this);
		//kata.setOnItemClickListener(this);
		//kata.setAdapter(new  MyAutoComplete(this,R.layout.autocomplete_list,urls,wilaya.getText().toString(),"mikoa","mikoa"));
	     
		// barprogress.setVisibility(View.GONE);
		 ClientWebService test=new ClientWebService(urls,MainActivity.this,inflater,"login",false);
		 test.AddParam("user", "all");
		 String[] mapkey={"name","status"};
  	     test.setMapKey(mapkey);
		 test.execute("get");
		 try {
  			 results=test.get();
  			 String value=String.valueOf(results);
  			 if(!value.trim().equals("false")){
  				
  				
  				if(results!=null){
  					logData=test.getData();
  		           if(logData!=null && logData.size()>0){
                	  
      					myData=logData.get(0);
      					String userid=myData.get("name");
      					ids=userid;
      					String status=myData.get("status");
      					Log.e("Hash","online :"+userid+status);
      				}
  				}
  			 }
		 }catch (InterruptedException e) {} catch (ExecutionException e) {}
  			
        	/*
        inayofuata.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				
				EditText mkoa=(EditText)findViewById(R.id.mkoa);
				EditText wilaya=(EditText)findViewById(R.id.wilaya);
				EditText kata=(EditText)findViewById(R.id.kata);
				EditText simu=(EditText)findViewById(R.id.simu);
				EditText kikundi=(EditText)findViewById(R.id.kikundi);
				EditText biashara=(EditText)findViewById(R.id.biashara);
				String[] value={jina.getText().toString(),mkoa.getText().toString(),wilaya.getText().toString(),kata.getText().toString(),simu.getText().toString(),kikundi.getText().toString(),biashara.getText().toString()};
				Intent intent=new Intent(MainActivity.this,NextPage.class);
		        intent.putExtra("pageone", value);
		    	   startActivity(intent);
		    	   
			}
        	
        });*/
    }
   
    @Override
	public void onClick(View v) {
	if(v==sendButton){
		sendData(v);	
	}
		
	}
    private void resetFields(){
    	jina.setText("");
    	simu.setText("");
    	nambaKitambulisho.setText("");
    	ainaKitambulisho.setText("");
    }
    private void sendData(View view){
    	if(view==sendButton){
        String jinaz=jina.getText().toString();
        String simuz=simu.getText().toString();
        String nambaKita=nambaKitambulisho.getText().toString();
        String ainaKita=ainaKitambulisho.getText().toString();
        String mkoaz=spinMkoa.getSelectedItem().toString();
        String wilayaz=spinWilaya.getSelectedItem().toString();
        String kataz=spinKata.getSelectedItem().toString();
        Boolean mkez=mke.isChecked();
        Boolean mmez=mme.isChecked();
        String jinsiaz="";
        if(mkez){
        jinsiaz="mwanamke";	
        }
        if(mmez){
        jinsiaz="mwanaume";
        }
        String mydata="{\"userbasic\":[{\"jina\":\""+jinaz+"\",\"tarehekuzaliwa\":\""+jinsiaz+"\",\"jinsia\":\""+jinsiaz+"\",\"simu\":\""+simuz+"\",";
               mydata+="\"nambakita\":\""+nambaKita+"\",\"ainakita\":\""+ainaKita+"\",\"mkoa\":\""+mkoaz+"\",\"wilaya\":\""+wilayaz+"\",";
               mydata+="\"kata\":\""+kataz+"\",\"mratibu\":\"2\",\"id\":\""+ids+"\"}]}";
           
       ClientWebService register=new ClientWebService(urls,MainActivity.this,inflater,"data",false);
       register.AddParam("data", mydata);
       register.AddParam("action", "register");
       
		 String[] mapkey={"refId"};
	     register.setMapKey(mapkey);
		 register.execute("post");
		 try {
			 resetFields();
			 results=register.get();
			 String value=String.valueOf(results);
			 if(!value.trim().equals("false")){
				
				
				if(results!=null){
					logData=register.getData();
		           if(logData!=null && logData.size()>0){
              	  
    					myData=logData.get(0);
    					String userid=myData.get("refId");
    					ids=userid;
    					String[] values={userid};
    					Intent intent=new Intent(MainActivity.this,NextPage.class);
    			        intent.putExtra("pageone", values);
    			    	   startActivity(intent);
    					Log.e("Hash","online :"+userid);
    				}
				}
			 }
		 }catch (InterruptedException e) {} catch (ExecutionException e) {}
    	//Log.e("Nyumbani","Hellow people");
    	}
    }
    
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) { 
    	String str = (String) adapterView.getItemAtPosition(position);
  
    	int mkoaz=R.id.automkoa;
    	int wilayaz=R.id.autowilaya;
    	int kataz=R.id.autokata;
    	int myview=view.getId();
    	
    	if( myview==mkoaz){
    		wilaya.setAdapter(new  MyAutoComplete(this,R.layout.autocomplete_wilaya,urls,str,"wilaya","wilaya"));
    		mkoa.setText(str);
    	Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
           
    	}else if(myview==wilayaz){
    		wilaya.setText(str);
    		kata.setAdapter(new  MyAutoComplete(this,R.layout.autocomplete_kata,urls,str,"kata","kata"));
    	}else if(myview==kataz){
    		kata.setText(str);
    	}
    	
   // Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    
    private void createItems(){
    	jina=(EditText)findViewById(R.id.jina);
    	tareheKuzaliwa=(EditText)findViewById(R.id.tarehekuzaliwa);
    	jinsia=(RadioGroup)findViewById(R.id.jinsia);
    	simu=(EditText)findViewById(R.id.simu);
    	nambaKitambulisho=(EditText)findViewById(R.id.nambakitambulisho);
    	ainaKitambulisho=(EditText)findViewById(R.id.ainakitambulisho);
    	mke=(RadioButton)findViewById(R.id.mke);
    	mme=(RadioButton)findViewById(R.id.mume);
    	
    	//mkoa=(AutoCompleteTextView)findViewById(R.id.mkoa);
    	//wilaya=(AutoCompleteTextView)findViewById(R.id.wilaya);
    	//kata=(AutoCompleteTextView)findViewById(R.id.kata);
    	sendButton=(Button)findViewById(R.id.inayofuata);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public ArrayList<String> getSpinnerData(String jsonArray,String input,String infos){
    	ArrayList<String> resultList = null;
		ClientWebService test=new ClientWebService(urls);
		String data="{\""+jsonArray+"\":[{\"type\":\""+jsonArray+"\",\"search\":\""+input+"\",\"value\":\""+infos+"\"}]}";
		test.AddParam("action","autocomp");
		test.AddParam("type",jsonArray);
		 test.AddParam("data", data);
		 test.execute("get");
		 try {
  			 results=test.get();
  			 String value=String.valueOf(results);
  			 if(!value.trim().equals("false")){
  				Log.e("Filter",infos);
  				Log.e("Fromautocomplete","myresult: "+results);
  				if(results!=null){
  					
  					resultList=test.arrayListData(results,jsonArray,jsonArray);
  				}
  			 }
		 }catch (InterruptedException e) {} catch (ExecutionException e) {}
		return resultList;
    }
    
    
    public void addSpinnerData(Spinner spinner,ArrayAdapter<String> dataAdapter){
    		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		spinner.setAdapter(dataAdapter);
    }

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int position,
			long posi) {
		String str = (String) adapterView.getItemAtPosition(position);
		if(str==null || str.equals("")){
			str="";
		}
		Log.e("spinnerValue",str+" posi "+position);
		int mkoaId=R.id.mkoa;
		int wilayaId=R.id.wilaya;
		int kataId=R.id.kata;
	   int id=adapterView.getId();
		if(id==mkoaId){
			wilayaAdapt=new ArrayAdapter<String>(MainActivity.this,
	    			android.R.layout.simple_spinner_item, getSpinnerData("wilaya","",str));
			 addSpinnerData(spinWilaya,wilayaAdapt);	
		}else if(id==wilayaId){
			kataAdapt=new ArrayAdapter<String>(MainActivity.this,
	    			android.R.layout.simple_spinner_item, getSpinnerData("kata","",str));
			addSpinnerData(spinKata,kataAdapt);
		}else if(id==kataId){
			
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
}
