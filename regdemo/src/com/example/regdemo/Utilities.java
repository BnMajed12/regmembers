package com.example.regdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Utilities {
	 private List<LinearLayout> linearlayoutList=new ArrayList<LinearLayout>();
	 /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     * */
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";
 
        // Convert total duration into time
           int hours = (int)( milliseconds / (1000*60*60));
           int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
           int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
           // Add hours if there
           if(hours > 0){
               finalTimerString = hours + ":";
           }
 
           // Prepending 0 to seconds if it is one digit
           if(seconds < 10){
               secondsString = "0" + seconds;
           }else{
               secondsString = "" + seconds;}
 
           finalTimerString = finalTimerString + minutes + ":" + secondsString;
 
        // return timer string
        return finalTimerString;
    }
 
    /**
     * Function to get Progress percentage
     * @param currentDuration
     * @param totalDuration
     * */
    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;
 
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
 
        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;
 
        // return percentage
        return percentage.intValue();
    }
 
    /**
     * Function to change progress to timer
     * @param progress -
     * @param totalDuration
     * returns current duration in milliseconds
     * */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);
 
        // return current duration in milliseconds
        return currentDuration * 1000;
    }
    
    public HashMap<String,String> decodeString(String values){
    	HashMap<String,String> maps=new HashMap<String,String>();
    	ArrayList<String> vals=new ArrayList<String>();
    	maps.clear();
    	vals.clear();
   String val= 	values.replaceAll("[{}]", " ");
   StringTokenizer tokens=new StringTokenizer(val,",");
   while(tokens.hasMoreTokens()){ vals.add(tokens.nextToken());};
	for(int i=0; i<vals.size(); i++){
	String[] splits=vals.get(i).split("=");
	String value="Unknown";
	if(splits.length<2){
		value="Unknown";
	}else{
		value=splits[1].trim();;
	}
	maps.put(splits[0].trim(), value);
	
	}
	Log.e("Split",maps.get("songname")+" value "+maps.get("filename"));
    	String results="{ 'tests':["+val+"]}";
    	Log.e("obtained",results);
  return maps;
    }
    
    public ArrayList<String> getSpinnerData(String urls,String jsonArray,String input,String infos){
    	String results="";
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
    
    private EditText editText(Context content,int _intID,String hitText,int width,List<EditText> editList) {
        EditText editText = new EditText(content);
        editText.setId(_intID);
        editText.setHint(hitText);
        editText.setWidth(width);
        editList.add(editText);
        return editText;
    }
    
    public LinearLayout linearlayout(LinearLayout layout,Context content,int _intID,String editTextHint,int width,List<EditText> editList)
    {
        LinearLayout LLMain=new LinearLayout(content);
        LLMain.setId(_intID);
       // LLMain.addView(textView(_intID));
        LLMain.addView(editText(content,_intID,editTextHint,width,editList));
        LLMain.setOrientation(LinearLayout.HORIZONTAL);
        linearlayoutList.add(LLMain);
        return LLMain;

    }
}
