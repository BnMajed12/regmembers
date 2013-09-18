package com.example.regdemo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Hariri extends ListActivity implements OnItemClickListener{
	private  ArrayList<Object> setValues,setValues1;
	 public static final String PREFS_NAME = "MyRegFile";
	private  ListView lists;
	private CustomCursorAdapter adapter;
	private  DatabaseOperation op,ops;
	private Cursor cs=null;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        op=new DatabaseOperation(this);
	        cs=op.getFomu("profile");
	      
	        setContentView(R.layout.hariri_list);
	   	  
	        final String[] from={"jina","imageurl"};
	        final int[] to={R.id.onajina,R.id.onaPicha};
	        final TextView mytext=(TextView)findViewById(R.id.hamnafomu);
	        lists=(ListView)findViewById(android.R.id.list);
	        if(cs!=null){
	      mytext.setVisibility(View.GONE);
	 adapter=new CustomCursorAdapter(this,R.layout.hariri_text,cs,from,to);
	     lists.setAdapter(adapter);
	     lists.setOnItemClickListener(this);
	      }else{
	        lists.setVisibility(View.GONE);
	        }
	        op.close();
	        
	 }


	
	public void setHistory(String id){
		
		Utilities.setStoreString(Hariri.this, PREFS_NAME,"fomuid", id);
		
		 ops=new DatabaseOperation(Hariri.this);
		Cursor c=ops.getFomuData("id",id,"profile");
		if(c!=null){
		 setValues = new ArrayList<Object>();
         setValues.add(c.getString(1));
         setValues.add(c.getString(2));
         setValues.add(c.getString(3));
         setValues.add(c.getString(4));
         setValues.add(c.getString(5));
         setValues.add(c.getString(6));
         setValues.add(c.getString(7));
         setValues.add(c.getString(8));
         setValues.add(c.getString(9));
         setValues.add(0);
         setValues.add(0);
         setValues.add(0);
        Utilities.setSerializableList(Hariri.this,PREFS_NAME, "profile", setValues);
		
		
		
		setValues = new ArrayList<Object>();
		 setValues.add(c.getString(10));
		 setValues.add(c.getString(11));
		 setValues.add(c.getString(12));
		 setValues.add(c.getString(13));
		 setValues.add(c.getString(14));
		 setValues.add(c.getString(15));
		 setValues.add(c.getString(16));
		 setValues.add("");
		 setValues.add("0");
		 setValues.add("0");
		 Utilities.setSerializableList(Hariri.this,PREFS_NAME, "biashara", setValues);
		Utilities.setStoreString(Hariri.this, PREFS_NAME,"mteja", c.getString(17));
		 c.close();
		
		}
		ops.close();
		ops=null;
		
		setValues = new ArrayList<Object>();
		setValues1 = new ArrayList<Object>();
		ops=new DatabaseOperation(Hariri.this);
		Cursor mto=ops.getFomuData("owner",id,"family");
		if(mto!=null){
		 do{
		 if(mto.getString(3).equals("mke")){
		 setValues1.add(mto.getString(2));
		 Log.e("mke",mto.getString(2));
		  }
		   if(mto.getString(3).equals("mtoto")){
		  setValues.add(mto.getString(2));
			 Log.e("mtoto",mto.getString(2));
		  }
		
		   }while(mto.moveToNext());
		 Utilities.setSerializableList(Hariri.this,PREFS_NAME, "mke", setValues1);
		 Utilities.setSerializableList(Hariri.this,PREFS_NAME, "mtoto", setValues);
		
		    mto.close();
		}
		
		ops.close();
		ops=null;
		   
		   
		   setValues = new ArrayList<Object>();
		   ops=new DatabaseOperation(Hariri.this);
		Cursor mdha=ops.getFomuData("owner",id,"mdhamini");
		if(mdha!=null){
		   setValues.add( mdha.getString(2));
		     setValues.add( mdha.getString(3));
		    setValues.add( mdha.getString(4));
	 Utilities.setSerializableList(Hariri.this,PREFS_NAME, "jinamdhamini", setValues);
	 Utilities.setStoreString(Hariri.this, PREFS_NAME,"mdhamini", mdha.getString(4));
			
	 mdha.close();
		    }
		   
		ops.close();
		ops=null;  
		 
		
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//data has been selected
				adapter.getItemId(arg2);
				setHistory(""+adapter.getItemId(arg2));
				 Intent intent=new Intent(Hariri.this,RegisterPageHolder.class);
			  	   startActivity(intent); 
		
	}
}
