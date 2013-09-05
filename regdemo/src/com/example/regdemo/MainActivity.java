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
	private String results=null;
	private DatabaseOperation ops=null;
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	private HashMap<String,String> myData;
	private EditText jina,tareheKuzaliwa,simu,nambaKitambulisho,ainaKitambulisho;
	private AutoCompleteTextView mkoa,wilaya,kata;
	private RadioGroup jinsia;
	private RadioButton mke,mme;
	private Button  sendButton;
	private String huumkoa,hiiwilaya;
	private DatabaseOperation forSpin=null;
	private Spinner spinMkoa,spinWilaya,spinKata;
	private ArrayAdapter<String> mkoaAdapt,wilayaAdapt,kataAdapt;
	private ArrayList<String> mkoaList,kataList,wilayaList;
	private String ids;
	//private ProgressBar barprogress=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Button inayofuata=(Button)findViewById(R.id.inayofuata);
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 urls=this.getResources().getString(R.string.apiURL);
		 mkoaList=new ArrayList<String>();
		 wilayaList=new ArrayList<String>();
		 kataList=new ArrayList<String>();
		 //barprogress=(ProgressBar)findViewById(R.id.inaload);
		 //MyAdapter(this,int spinner, int textViewResourceId,String[] objects,LayoutInflater inflates,int viewlayoutid)
		 spinMkoa=(Spinner)findViewById(R.id.mkoa);
		 spinWilaya=(Spinner)findViewById(R.id.wilaya);
		 spinKata=(Spinner)findViewById(R.id.kata);
		 spinMkoa.setOnItemSelectedListener(this);
		 spinWilaya.setOnItemSelectedListener(this);
		 spinKata.setOnItemSelectedListener(this);
		 forSpin=new DatabaseOperation(MainActivity.this);
		 ArrayList<String> mkoaSpin=forSpin.getMkoa();
		 forSpin.close();
		 if(mkoaSpin!=null && mkoaSpin.size()>0){
			mkoaList=mkoaSpin;
		 }else{
			 mkoaList=getSpinnerData("mikoa","","");
			 for(String mydata: mkoaList){
    			 ops=new DatabaseOperation(MainActivity.this);
    			ops.insertMkoa(mydata);
    			ops.close();
    		}
		 }
		 
		 mkoaAdapt=new ArrayAdapter<String>(this,
	    			android.R.layout.simple_spinner_item,mkoaList );
		 addSpinnerData(spinMkoa,mkoaAdapt);
		createItems();
		sendButton.setOnClickListener(this);
	
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
        /*
        String mydata="{\"userbasic\":[{\"jina\":\""+jinaz+"\",\"tarehekuzaliwa\":\""+jinsiaz+"\",\"jinsia\":\""+jinsiaz+"\",\"simu\":\""+simuz+"\",";
               mydata+="\"nambakita\":\""+nambaKita+"\",\"ainakita\":\""+ainaKita+"\",\"mkoa\":\""+mkoaz+"\",\"wilaya\":\""+wilayaz+"\",";
               mydata+="\"kata\":\""+kataz+"\",\"mratibu\":\"2\",\"id\":\""+ids+"\"}]}";
               */
           
       ClientWebService register=new ClientWebService(urls,MainActivity.this,inflater,"data",false);
       register.AddParam("data", "");
       register.AddParam("action", "register");
       register.AddParam("jina",jinaz);
       register.AddParam("tarehekuzaliwa", jinsiaz);
       register.AddParam("jinsia", jinsiaz);
       register.AddParam("simu", simuz);
       register.AddParam("mkoa", mkoaz);
       register.AddParam("wilaya", wilayaz);
       register.AddParam("nambakita",nambaKita);
       register.AddParam("ainakita", ainaKita);
       register.AddParam("kata", kataz);
       register.AddParam("mratibu", "2");
       register.AddParam("id", ids);
       //register.isMultForm(true);
	   String[] mapkey={"refId"};
	   register.setMapKey(mapkey);
	   register.execute("post");
		 try {
			 resetFields();
			 if(register.getResponseCode()!=503 && register.getResponseCode()!=404 && register.getResponseCode()!=408 ){
			results=register.get();
			 }else{
            //display some errors	 
			 }
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
		//String data="{\""+jsonArray+"\":[{\"type\":\""+jsonArray+"\",\"search\":\""+input+"\",\"value\":\""+infos+"\"}]}";
		test.AddParam("action","autocomp");
		test.AddParam("type",jsonArray);
		 test.AddParam("search",input);
		 test.AddParam("value", infos);
		 test.isMultForm(true);
		 test.execute("post");
		 try {
              if(test.getResponseCode()!=503 && test.getResponseCode()!=404 && test.getResponseCode()!=408  ){
  			 results=test.get();
              }else{
            	  //some errors
            	  results="{\""+jsonArray+"\":[{\""+jsonArray+"\":\"none\"}]}";
              }
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
	   /*
	    * 
	    * for(String mydata: mkoaList){
    			 ops=new DatabaseOperation(MainActivity.this);
    			ops.insertHuduma(mydata);
    			ops.close();
    		}
	    */
		if(id==mkoaId){
			forSpin=new DatabaseOperation(MainActivity.this);
	   		 ArrayList<String> wilayaSpin=forSpin.getWilaya(str);
	   		forSpin.close();
	   		 if(wilayaSpin!=null && wilayaSpin.size()>0){
	   			wilayaList=wilayaSpin;
	   		 }else{
	   			 wilayaList=getSpinnerData("wilaya","",str);
	   			for(String mydata: wilayaList){
	    			 ops=new DatabaseOperation(MainActivity.this);
	    			ops.insertWilaya(mydata, str);
	    			ops.close();
	    		}
	   		 }
	   		 
			wilayaAdapt=new ArrayAdapter<String>(MainActivity.this,
	    			android.R.layout.simple_spinner_item, wilayaList);
			 addSpinnerData(spinWilaya,wilayaAdapt);	
		}else if(id==wilayaId){
			forSpin=new DatabaseOperation(MainActivity.this);
	   		 ArrayList<String> wilayaSpin=forSpin.getKata(str);
	   		forSpin.close();
	   		 if(wilayaSpin!=null && wilayaSpin.size()>0){
	   			wilayaList=wilayaSpin;
	   		 }else{
	   			 kataList=getSpinnerData("kata","",str);
	   			for(String mydata: kataList){
	    			 ops=new DatabaseOperation(MainActivity.this);
	    			ops.insertKata(mydata,str);
	    			ops.close();
	    		}
	   		 }
	   		 
			kataAdapt=new ArrayAdapter<String>(MainActivity.this,
	    			android.R.layout.simple_spinner_item, kataList);
			addSpinnerData(spinKata,kataAdapt);
		}else if(id==kataId){
			
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
}
