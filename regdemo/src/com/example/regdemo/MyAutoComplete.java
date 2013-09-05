package com.example.regdemo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class MyAutoComplete extends ArrayAdapter<String> implements Filterable{
	private ArrayList<String> resultList;
	private String urls;
	private String infos;
	private String results;
	private String jsonArray;
	private String itemName;
	public MyAutoComplete(Context context, int resource,String urls,String infos,String jsonArray,String itemName) {
		super(context, resource);
	    this.urls=urls;
	    this.infos=infos;
	    this.jsonArray=jsonArray;
	    this.itemName=itemName;
	}
	
	@Override
	public int getCount() { 
		return resultList.size();
	}
	@Override
	public String getItem(int index) { 
		return resultList.get(index);
	}
	
	public ArrayList<String> autoComplete(String input){
		ArrayList<String> resultList = null;
		ClientWebService test=new ClientWebService(urls);
		//String data="{\""+jsonArray+"\":[{\"type\":\""+jsonArray+"\",\"search\":\""+input+"\",\"value\":\""+infos+"\"}]}";
		test.AddParam("action","autocomp");
		test.AddParam("type",jsonArray);
		test.AddParam("search", input);
		test.AddParam("value", infos);
		 test.AddParam("data", "");
		 test.execute("get");
		 try {
  			 results=test.get();
  			 String value=String.valueOf(results);
  			 if(!value.trim().equals("false")){
  				
  				Log.e("Fromautocomplete","myresult: "+results);
  				if(results!=null){
  					
  					resultList=test.arrayListData(results,jsonArray,itemName);
  				}
  			 }
		 }catch (InterruptedException e) {} catch (ExecutionException e) {}
		return resultList;
	
	}
	public Filter getFilter() {
		Filter filter = new Filter() {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) { FilterResults filterResults = new FilterResults();
		if (constraint != null) {
		                    // Retrieve the autocomplete results.
		                    resultList = autoComplete(constraint.toString());
		                    // Assign the data to the FilterResults
		                    filterResults.values = resultList;
		                    filterResults.count = resultList.size();
		                    Log.e("Filterautocomplete","myresult: "+resultList.size());
		                }
		return filterResults; }
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) { if (results != null && results.count > 0) {
		                    notifyDataSetChanged();
		                }
		else { notifyDataSetInvalidated();
		} }};
		return filter; 
		}

}
