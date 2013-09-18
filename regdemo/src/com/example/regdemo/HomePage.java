package com.example.regdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class HomePage extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home_page);
	    }
	 
	 public void hariri(View v){
		 Intent intent=new Intent(HomePage.this,Hariri.class);
	  	   startActivity(intent); 
	 }
	 
	 public void sajiri(View v){
		 Intent intent=new Intent(HomePage.this,RegisterPageHolder.class);
  	   startActivity(intent); 
	 }
	 
	 public void toaMkopo(View v){
		 
	 }
	 public void tuma(View v){
		 Intent intent=new Intent(HomePage.this,FomuToSend.class);
	  	   startActivity(intent);	 
	 }
	 public void pokeaRejesho(View v){
		 
	 }
	 
	 public void wanachama(View v){
		 
	 }
}
