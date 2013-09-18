package com.example.regdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText nenosiri,mtumiaji;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.reg_login);
	          nenosiri=(EditText)findViewById(R.id.nenosiri);
	          mtumiaji=(EditText)findViewById(R.id.mtumiaji);
	          
	    }
	 
	 public void ingia(View v){
		String neno=nenosiri.getText().toString(); 
		String jina=mtumiaji.getText().toString();
		if((neno.equals("demo") && jina.equals("demo"))){
			//process login
			 Intent intent=new Intent(Login.this,HomePage.class);
	    	   startActivity(intent);
	    	   finish();
		}else{
			Toast.makeText(Login.this,"Taarifa sio sahihi", Toast.LENGTH_LONG).show();
		}
	 }
	 
	 public void ghairi(View v){
		Builder builder=new AlertDialog.Builder(Login.this);
		builder.setMessage("Kweli Unaghairi Kuingia?");
		builder.setCancelable(false);
		builder.setPositiveButton("Ndiyo", new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
			finish();	
			}
		});
		builder.setNegativeButton("Hapana", new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Login.this,"Ombi limehailishwa", Toast.LENGTH_LONG).show();      
			}
		});
	
		AlertDialog alert=builder.create();

		alert.show();
		
	 }
}
