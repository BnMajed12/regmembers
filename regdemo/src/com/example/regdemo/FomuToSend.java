package com.example.regdemo;

import java.io.File;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FomuToSend extends ListActivity {
	private LayoutInflater inflater;
	 ClientWebService register;
	private String urls;
	private  ListView lists;
	private    TextView mytext;
	private CustomCursorAdapter adapter;
	private  DatabaseOperation op,ops,po;
	private String[]  from={"jina","imageurl"};
	final int[] to={R.id.onajina,R.id.onaPicha};
	private Cursor cs=null;
	private ProgressDialog pd;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        op=new DatabaseOperation(this);
	        cs=op.getFomu("profile");
	      
	        setContentView(R.layout.send_list);
	       pd=new ProgressDialog(this);
	      inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 urls=this.getResources().getString(R.string.apiURL);
			  
	       
	        
	        mytext=(TextView)findViewById(R.id.hamnafomu);
	        lists=(ListView)findViewById(android.R.id.list);
	        if(cs!=null){
	      mytext.setVisibility(View.GONE);
	 adapter=new CustomCursorAdapter(this,R.layout.send_textview,cs,from,to);
	     lists.setAdapter(adapter);
	      }else{
	        lists.setVisibility(View.GONE);
	        }
	        op.close();
	        pd.setOnCancelListener(new OnCancelListener(){

				
				@Override
				public void onCancel(DialogInterface dialog) {
				
					refreshAdapter();
				}
	        	
	        });
	        
	       
	        
	 }
	 

   public void refreshAdapter(){
		op=new DatabaseOperation(FomuToSend.this);
        cs=op.getFomu("profile");
		if( cs!=null){
			mytext.setVisibility(View.GONE);
			 adapter=new CustomCursorAdapter(FomuToSend.this,R.layout.send_textview,cs,from,to);
			  lists.setAdapter(adapter);
		    }else{
		   
		 lists.setVisibility(View.GONE);
		       	
		}
		op.close();
   }
	 public void tumaFomu(View v){
		//hapa tuatuma data online
		 
		 SparseBooleanArray checkedItems = lists.getCheckedItemPositions();
         int checkedItemsCount = checkedItems.size();
         register=new ClientWebService(urls,FomuToSend.this,inflater,"data",false);
		 register.progresDialogMessage("Subiri Natuma",pd);
	      register.setToastSuccessSMS("Umefanikiwa Kutuma Fomu "+checkedItemsCount+"");
	      register.setToastFailSMS("Nimeshindwa Kutuma Fomu,Lekebisha Mtandao Ujaribu Tena.");
         register.isMultForm(true);
		 register.hasDataToDelete(true);
		
         for (int i = 0; i < checkedItemsCount; ++i) {
             // Item position in adapter
             int position = checkedItems.keyAt(i);
             // Add team if item is checked == TRUE!
             if(checkedItems.valueAt(i)){
            	 adapter.getItem(position);
                //Log.e("Item Id",""+adapter.getItemId(position));
                sendProfileFomu(""+adapter.getItemId(position),""+i);
                
             }
            	 
              //  selectedTeams.add(myAdapter.getItem(position));
          }
         if(cs!=null){
         cs.close();
         }
         register.execute("post");
         
	 }
	 
	 public void futaFomu(View v){
		 final SparseBooleanArray checkedItems = lists.getCheckedItemPositions();
	
         final int checkedItemsCount = checkedItems.size();
		 Builder builder=new AlertDialog.Builder(FomuToSend.this);
			builder.setMessage("Unakaribia kufuta Fomu "+checkedItemsCount+". Hutaweza kuziona Tena. Je Unataka Kuzifuta?");
			builder.setCancelable(false);
			builder.setPositiveButton("Ndiyo", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int status=-1;  
			         for (int i = 0; i < checkedItemsCount; ++i) {
			             // Item position in adapter
			             int position = checkedItems.keyAt(i);
			             // Add team if item is checked == TRUE!
			             if(checkedItems.valueAt(i)){
			            	 adapter.getItem(position);
			            	String  id=""+adapter.getItemId(position);
			            	po=new DatabaseOperation(FomuToSend.this);
			    			po.deleteFomuData("profile","id", id);
			    			po.close();
			    			 po=new DatabaseOperation(FomuToSend.this);
			    			po.deleteFomuData("family","owner", id);
			    			po.close();
			    		     po=new DatabaseOperation(FomuToSend.this);
			    			po.deleteFomuData("mdhamini","owner", id);
			    			po.close();
			                status=i;
			             }}	
			         if(status>-1){
		Toast.makeText(FomuToSend.this,"Umefanikiwa Kufuta Fomu "+(status+1)+".", Toast.LENGTH_LONG).show();      
		refreshAdapter();		    	 
			         }
				}
			});
			builder.setNegativeButton("Hapana", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(FomuToSend.this,"Ombi limehailishwa", Toast.LENGTH_LONG).show();      
				}
			});
		
			AlertDialog alert=builder.create();

			alert.show();
		
         
         for (int i = 0; i < checkedItemsCount; ++i) {
             // Item position in adapter
             int position = checkedItems.keyAt(i);
             // Add team if item is checked == TRUE!
             if(checkedItems.valueAt(i)){
            	 adapter.getItem(position);
            	String  id=""+adapter.getItemId(position);
            	po=new DatabaseOperation(FomuToSend.this);
    			po.deleteFomuData("profile","id", id);
    			po.close();
    			 po=new DatabaseOperation(FomuToSend.this);
    			po.deleteFomuData("family","owner", id);
    			po.close();
    		     po=new DatabaseOperation(FomuToSend.this);
    			po.deleteFomuData("mdhamini","owner", id);
    			po.close();
                
             }}
		//hapa tunafuta data; 
		 /*
		  
			po=new DatabaseOperation(FomuToSend.this);
			po.deleteFomuData("profile","id", id);
			po.close();
			 po=new DatabaseOperation(FomuToSend.this);
			po.deleteFomuData("family","owner", id);
			po.close();
		     po=new DatabaseOperation(FomuToSend.this);
			po.deleteFomuData("mdhamini","owner", id);
			po.close();
			
		  */
	 }
	 
	 public void sendProfileFomu(String id,String index){
		 
		
		 register.setDbIdField("id", id, "profile");
		 register.setDbIdField("owner", id, "family");
		 register.setDbIdField("owner", id, "mdhamini");
		 ops=new DatabaseOperation(FomuToSend.this);
		Cursor c=ops.getFomuData("id",id,"profile");
		if(c!=null){
/*
 profile+=" jina VARCHAR,jinsia VARCHAR, kuzaliwa VARCHAR, mkoa VARCHAR,wilaya VARCHAR,kata VARCHAR,";
	       profile+="simu VARCHAR,namba_kitambulisho VARCHAR, aina_kitambulisho VARCHAR,huduma VARCHAR,kiwango_ombi VARCHAR,";
	       profile+="kiwango_kuanzia VARCHAR, malipo_siku VARCHAR,malipo_muda VARCHAR,biashara VARCHAR, kikundi VARCHAR,imageurl VARCHAR)";
	       
 */
			 register.AddParam("action", "register");
		       register.AddParam("jina["+index+"]",c.getString(1));
		       register.AddParam("trasid["+index+"]",id);
		       register.AddParam("jinsia["+index+"]",c.getString(2));
		       register.AddParam("tarehekuzaliwa["+index+"]", c.getString(3));
		       register.AddParam("mkoa["+index+"]", c.getString(4));
		       register.AddParam("wilaya["+index+"]",c.getString(5));
		       register.AddParam("kata["+index+"]", c.getString(6));
		       register.AddParam("simu["+index+"]", c.getString(7));
		       register.AddParam("nambakita["+index+"]",c.getString(8));
		       register.AddParam("ainakita["+index+"]", c.getString(9));
		       register.AddParam("huduma["+index+"]", c.getString(10));
		       register.AddParam("kiwango["+index+"]", c.getString(11));
		       register.AddParam("kianzio["+index+"]", c.getString(12));
		       register.AddParam("maliposiku["+index+"]", c.getString(13));
		       register.AddParam("mudamalipo["+index+"]",c.getString(14));
		       register.AddParam("biashara["+index+"]", c.getString(15));
		       register.AddParam("kikundi["+index+"]", c.getString(16));
		       register.AddParam("pichamte["+index+"]",c.getString(17));
		       register.AddParam("mratibu","2");
		       Log.e("biashara",c.getString(15));
		       register.addImageParam("mteja["+index+"]");
		       String st=Utilities.getEncodeStringUri(c.getString(c.getColumnIndex("imageurl")));
		       register.addBitmap(Utilities.decodeFile(new File(st), 150),"mteja["+index+"]");
		      
		       register.AddParam("banki["+index+"]", "");
		       
		    
		       
		
		}
		
		c.close();
		ops.close();
		ops=null;
		
		ops=new DatabaseOperation(FomuToSend.this);
		Cursor mto=ops.getFomuData("owner",id,"family");
		if(mto!=null){
			int i=0;
	  do{
		   register.AddParam("mwanajina["+index+"]["+i+"]", mto.getString(2));
		   register.AddParam("aina["+index+"]["+i+"]", mto.getString(3));
		   i++;
	   } while(mto.moveToNext());
	   mto.close();
		}
		
		ops.close();
		ops=null;
		
		//(id integer primary key autoincrement, owner integer,";
	      // mdhamini+="jina VARCHAR, simu VARCHAR, imageurl VARCHAR)";
		
		ops=new DatabaseOperation(FomuToSend.this);
		Cursor mdha=ops.getFomuData("owner",id,"mdhamini");
		if(mdha!=null){
			 register.AddParam("mdhamini["+index+"]", mdha.getString(2));
		     register.AddParam("mdhaminisimu["+index+"]", mdha.getString(3));
		     register.AddParam("pichaMdha["+index+"]", mdha.getString(4));
		    register.addImageParam("mdhapicha["+index+"]");
		     String st=Utilities.getEncodeStringUri(mdha.getString(4));
		     register.addBitmap(Utilities.decodeFile(new File(st), 150),"mdhapicha["+index+"]");
		     mdha.close();
		}
		
		ops.close();
		ops=null;
		
		
	}
}
