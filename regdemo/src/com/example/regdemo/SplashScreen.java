package com.example.regdemo;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


public class SplashScreen extends Activity {
	private Thread splashScreen;
	protected int _splashTime=10000;
	//private String results="";
	private String urls="";
	private LayoutInflater inflater;
	private ProgressBar barprogress;
	private boolean splashFinished=false,islogedin=false;
	private ClientWebService clients=null;
	//private  DatabaseOperation  ops=null;
	public final static String USER_ID="com.vikoba.vikoba.USERID";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        Utilities.removeRegisterData(this,"MyRegFile");
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        urls=this.getResources().getString(R.string.apiURL);
        clients=new ClientWebService(urls,this,inflater);
      //  encrypt=new EncryptManager();
        barprogress=(ProgressBar)findViewById(R.id.splash_pro2);
        splashScreen=new Thread(){
       	 @Override
       	public void run(){
       		 try{
       		synchronized(this){
       			barprogress.setVisibility(View.VISIBLE);
       			wait(_splashTime);
       			
       		}
       		 }catch(InterruptedException e){}
       		 finally{
       			 try {
							this.finalize();
						} catch (Throwable e) {}
       			 if(islogedin){
       				 Intent intent=new Intent(SplashScreen.this,Login.class);
				    	   intent.putExtra(USER_ID,"");
				    	   startActivity(intent);
				    	   finish(); 
       			 }else{
       				 Intent intent=new Intent(SplashScreen.this,Login.class);
    			    	   startActivity(intent);
    			    	   finish();
       			 }
               
       			 }   
       		
       		 }
       	 
       };
       splashScreen.start();
       if(!splashFinished && clients.hasConnection()){
    	 
    	Utilities util=new Utilities();
    	Utilities utils=new Utilities();
    	Utilities utilz=new Utilities();
    	util.onlineToDbBackground(urls, "huduma", "", "",100);
    	utils.onlineToDbBackground(urls, "mudamalipo", "", "",101);
    	utilz.onlineToDbBackground(urls, "mikoa","","",102);

       }
	}
}
