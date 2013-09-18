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
	private ArrayList<HashMap<String,String>>  logData=new ArrayList<HashMap<String,String>>();
	private HashMap<String,String> myData;
	private EditText jina,tareheKuzaliwa,simu,nambaKitambulisho,ainaKitambulisho;
	private RadioGroup jinsia;
	private RadioButton mke,mme;
	private DatabaseOperation db;
	private Button  sendButton;
	private String huumkoa,hiiwilaya;
	private DatabaseOperation forSpin=null,ops=null;
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
        String kuzaliwa=tareheKuzaliwa.getText().toString();
        Boolean mkez=mke.isChecked();
        Boolean mmez=mme.isChecked();
        String jinsiaz="";
        if(mkez){
        jinsiaz="mwanamke";	
        }
        if(mmez){
        jinsiaz="mwanaume";
        }
        if(jinaz.equals("") || simuz.equals("") || ainaKita.equals("") || nambaKita.equals("")){
       //fill all fields
        	 Toast.makeText(MainActivity.this,"Tafadhari Jana Fomu Yote", Toast.LENGTH_LONG).show();
        }else{
        	 String[] data={jinaz,jinsiaz,kuzaliwa,mkoaz,wilayaz,kataz,simuz,nambaKita,ainaKita,"","","","","","","",""};
             db=new DatabaseOperation(MainActivity.this);
             int owner=db.insertData(data,"profile");
             db.close();
             if(owner>0){
             	String[] values={""+owner};
     			Intent intent=new Intent(MainActivity.this,NextPage.class);
     	        intent.putExtra("pageone", values);
     	    	   startActivity(intent);	
             }
        }
        
        
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
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
}
