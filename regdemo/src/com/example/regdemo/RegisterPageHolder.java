package com.example.regdemo;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterPageHolder extends FragmentActivity implements OnItemClickListener, OnItemSelectedListener{
	private static final int MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int DATE_DIALOG_ID = 3;
	private  ArrayList<Object> setValues;
	 public static final String PREFS_NAME = "MyRegFile";
	 private  ViewPager pager;
	 private MyFragmentPagerAdapter pagerAdapter;
	//private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static Uri fileUriMdhamini,fileUriMteja;
//	private LayoutInflater inflater;
	//private Uri myMdhaUri=null;
	private String urls;
	private String results=null;
   private Utilities hudumu,lipamuda;
 //  private   List<android.support.v4.app.Fragment> myFrag;
	private DatabaseOperation forSpin=null,ops=null;
	private Spinner spinMkoa,spinWilaya,spinKata,spinHuduma,spinMuda;
	private ArrayAdapter<String> mkoaAdapt,wilayaAdapt,kataAdapt;
	private ArrayList<String> mkoaList,kataList,wilayaList,hudumaList,mudaList;
	private ImageView mdhaminiView,mtejaView;
	private EditText idadiWatoto,mdhamini,simumdhamini;
	private EditText jina,tareheKuzaliwa,simu,nambaKitambulisho,ainaKitambulisho;
	private EditText biashara,kikundi,banki,kiwango,kianzio,maliposiku;
	private CheckBox umeoa;
	private RadioButton mke,mme;
	//private Button pichaMdha;
	private Bitmap bitmap;
	private int myWidth;
	int _intMyLineCount;
	LinearLayout LLEnterText,UmeoaEnterText;
    private List<EditText> editListUmeoa = new ArrayList<EditText>();
    private List<EditText> editListWatoto = new ArrayList<EditText>();
    private List<RelativeLayout> linearlayoutList=new ArrayList<RelativeLayout>();
    private int pYear=2011;
    private int pMonth=3;
    private int pDay=5;
    
    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                    //displayToast();
                }
            };
            
            /** Updates the date in the TextView */
            private void updateDisplay() {
            	String months="",days="";
            	if((pMonth+1)<10){
            		months="0"+(pMonth+1);
            	}else{
            	    months=""+(pMonth+1);
            	}
            	if((pDay)<10){
            		days="0"+(pDay);
            	}else{
            	    days=""+(pDay);
            	}
            	tareheKuzaliwa.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(pYear).append("-")
                            .append(months).append("-")
                            .append(days).append(" "));
            }
            
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.page_view);
      //  inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 urls=this.getResources().getString(R.string.apiURL);
		 mkoaList=new ArrayList<String>();
		 wilayaList=new ArrayList<String>();
		 kataList=new ArrayList<String>();
         hudumaList=new ArrayList<String>();
         mudaList=new ArrayList<String>();
         
         //try to set values to fomu if not exist
         setDefaultValues();
        /** Getting a reference to the ViewPager defined the layout file */        
       pager = (ViewPager) findViewById(R.id.pager);
        
        /** Getting fragment manager */
        final FragmentManager fm = getSupportFragmentManager();
    //myFrag=fm.getFragments();
    
        /** Instantiating FragmentPagerAdapter */
      pagerAdapter = new MyFragmentPagerAdapter(fm);
      
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				  switch(arg0){
				  
				    case 1:
				    		
				    	familiItems();
				     break;
				    case 2:
				    	biasharaSpinner();
				     break;
				    case 3:
				    	mtejaElements();
				    break;
				    case 4:
				    	mdhaminiElements();
				    break;
				      default:
				    	  //add fields for five
				    	  initSpinners();
					   break;
				    }
               
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			
				if(arg0==0){
		               initSpinners();
						}
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				setDefaultValues();
				if(arg0==0){
             //  initSpinners();
				}
				
			}
			
			
		});
        
        
        /** Setting the pagerAdapter to the pager object */
        pagerAdapter.saveState();
        pager.setAdapter(pagerAdapter);
    
        
    }

  
    
    /** Create a new dialog for date picker */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
        	Calendar cal = Calendar.getInstance();
              int iDay = cal.get(Calendar.DAY_OF_MONTH);
        	  int iMonth = cal.get(Calendar.MONTH);
        	  int iYear = cal.get(Calendar.YEAR);
        	DatePickerDialog mydial=new DatePickerDialog(this,
                    pDateSetListener,
                    iYear, iMonth, iDay);
        	mydial.setMessage("Tarehe Ya Kuzaliwa Mteja");
            return mydial;
        }
        return null;
    }
    
    
    public void setDefaultValues(){
    	firstPageData(0);
		secondPageData();
		thirdPageData();
		mdhaminPage();
    }
    public void firstPageData(int position){
    	//View v=pager.getChildAt(position);
    	if(jina!=null && spinMkoa!=null && spinWilaya!=null && spinKata!=null){
    	String wilayaz="wilaya";
    	String kataz="kata";
    	String jinaz=jina.getText().toString();
        String simuz=simu.getText().toString();
        String nambaKita=nambaKitambulisho.getText().toString();
        String ainaKita=ainaKitambulisho.getText().toString();
        String mkoaz=spinMkoa.getSelectedItem().toString();
        if(spinWilaya.getChildCount()>0){
         wilayaz=spinWilaya.getSelectedItem().toString();
        }
        if(spinKata.getChildCount()>0){
        kataz=spinKata.getSelectedItem().toString();
        }
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
       ArrayList<Object> list=Utilities.getSerializableList(RegisterPageHolder.this, PREFS_NAME,"profile");
                 if(list!=null){
           
        	 Toast.makeText(RegisterPageHolder.this,"Fomu Ya Kwanza Hujaiza Vizuri"+list.get(0), Toast.LENGTH_LONG).show();
                 }
        }else{
        	setValues = new ArrayList<Object>();
        
         setValues.add(jinaz);
         setValues.add(jinsiaz);
         setValues.add(kuzaliwa);
         setValues.add(mkoaz);
         setValues.add(wilayaz);
         setValues.add(kataz);
         setValues.add(simuz);
         setValues.add(nambaKita);
         setValues.add(ainaKita);
         setValues.add(spinMkoa.getSelectedItemPosition());
         setValues.add(spinWilaya.getSelectedItemPosition());
         setValues.add(spinKata.getSelectedItemPosition());
        Utilities.setSerializableList(RegisterPageHolder.this,PREFS_NAME, "profile", setValues);
     //  new Utilities().setStoreSet(RegisterPageHolder.this,PREFS_NAME, "profile", setValues);	
        }
        ArrayList<Object> list=Utilities.getSerializableList(RegisterPageHolder.this, PREFS_NAME,"profile");
        if(list!=null){
           jina.setText(list.get(0).toString());
           tareheKuzaliwa.setText(list.get(1).toString());
           spinMkoa.setSelection(Integer.parseInt(list.get(9).toString()));
           spinMkoa.setSelected(true);
           spinMkoa.requestLayout();
           spinWilaya.setSelection(Integer.parseInt(list.get(10).toString()));
           spinWilaya.setSelected(true);
           spinWilaya.requestLayout();
           spinKata.setSelection(Integer.parseInt(list.get(11).toString()));
           spinKata.setSelected(true);
           spinKata.requestLayout();
           simu.setText(list.get(6).toString());
           nambaKitambulisho.setText(list.get(7).toString());
           ainaKitambulisho.setText(list.get(8).toString());
	 //Toast.makeText(RegisterPageHolder.this,"Fomu Ya Kwanza Hujaiza Vizuri"+list.get(0), Toast.LENGTH_LONG).show();
        } 
        
    	}//end checking if we are in page 1
    }
     
    public void secondPageData(){
    	if(idadiWatoto!=null){
    	String watoto=idadiWatoto.getText().toString().trim();
		//String myChild="";
		//String familia="{\"userfamily\":[";
		//String kuoa="";
		int childs=0;
		int childc=0;
		;
		
		Boolean umeoaz=umeoa.isChecked();
	
			 if(umeoaz){
			childc= UmeoaEnterText.getChildCount();
			 }
			       if(watoto.matches("\\d") && Integer.parseInt(watoto)>=1){
			    	   childs=LLEnterText.getChildCount();
						if(childs>0){
							setValues = new ArrayList<Object>();	
			                 for (EditText editText : editListWatoto) {
						  // String[] kids={frompageone[0],editText.getText().toString(),"mtoto"};
						 
						   setValues.add(editText.getText().toString());
						   
		               
		                     }
		  Utilities.setSerializableList(RegisterPageHolder.this,PREFS_NAME, "mtoto", setValues);
						}
			       }
						
						if(childc>0){
							setValues = new ArrayList<Object>();
							
							 //EditText myText=(EditText)UmeoaEnterText.getChildAt(0);
							 for (EditText myText : editListUmeoa) {
					// String[] mke={"",myText.getText().toString(),"mke"};
								
								 setValues.add(myText.getText().toString());
								 
								 	 
							 }
			Utilities.setSerializableList(RegisterPageHolder.this,PREFS_NAME, "mke", setValues);
						
							 }
		
									
    	}//end checking if are i page 3
    	
    	
    	//try to build again mtoto fields
		 ArrayList<Object> mtotoz= Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "mtoto");
		if(mtotoz!=null && mtotoz.size()>0){
			if(idadiWatoto!=null && idadiWatoto.getText().equals("")){
				idadiWatoto.setText(""+mtotoz.size());
			}
		if(LLEnterText!=null){
			//editListWatoto.clear();
			if(LLEnterText.getChildCount()<1){
				if(editListWatoto!=null){
					editListWatoto.clear();
				}
			for(int i=0; i<mtotoz.size(); i++){
	      LLEnterText.addView(linearlayout(_intMyLineCount,mtotoz.get(i).toString(),200,editListWatoto));
	    	_intMyLineCount=i;
	    	}
			LLEnterText.requestLayout();
			}
		}
		}
			//try to build again mke fields
		  ArrayList<Object> mkez= Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "mke");
		   if(mkez!=null && mkez.size()>0){
			   if(umeoa!=null && !umeoa.isChecked()){
				   umeoa.setChecked(true);  //check the button if not checked  
			   }
			 if( UmeoaEnterText!=null){
				 
			if(UmeoaEnterText.getChildCount()<1){
			if(editListUmeoa!=null){
				editListUmeoa.clear();
			}
		UmeoaEnterText.addView(linearlayout(400,mkez.get(0).toString(),200,editListUmeoa)); 
			 }
		UmeoaEnterText.requestLayout();
			 }
			 }
    }
    
    public void thirdPageData(){
    	if(biashara!=null){
    	String huduma="";
    	String mudaz="";
    	 String biasharaz=biashara.getText().toString();
		 String kikundiz=kikundi.getText().toString();
		 String kiwangoz=kiwango.getText().toString();
		 String kianzioz=kianzio.getText().toString();
		 String bankiz=banki.getText().toString();
		 String malipo=maliposiku.getText().toString();
		 if(spinHuduma.getChildCount()>0){
		 huduma=spinHuduma.getSelectedItem().toString();
		 }
		 if(spinMuda.getChildCount()>0){
		mudaz=spinMuda.getSelectedItem().toString();
		 }
		if(!(biasharaz.equals("") && kikundiz.equals(""))){
		setValues = new ArrayList<Object>();
		 setValues.add(huduma);
		 setValues.add(kiwangoz);
		 setValues.add(kianzioz);
		 setValues.add(malipo);
		 setValues.add(mudaz);
		 setValues.add(biasharaz);
		 setValues.add(kikundiz);
		 setValues.add(bankiz);
		 setValues.add(""+spinHuduma.getSelectedItemPosition());
		 setValues.add(""+spinMuda.getSelectedItemPosition());
		 Utilities.setSerializableList(RegisterPageHolder.this,PREFS_NAME, "biashara", setValues);
		}
		 ArrayList<Object> list=Utilities.getSerializableList(RegisterPageHolder.this, PREFS_NAME, "biashara");
		 if(list!=null){
		spinHuduma.setSelection(Integer.parseInt(list.get(8).toString()));
		kiwango.setText(list.get(1).toString());
		kianzio.setText(list.get(2).toString());
		maliposiku.setText(list.get(3).toString());
		spinMuda.setSelection(Integer.parseInt(list.get(9).toString()));
		biashara.setText(list.get(5).toString());
		kikundi.setText(list.get(6).toString());
		banki.setText(list.get(7).toString());
		spinHuduma.setSelected(true);
		spinMuda.setSelected(true);
		  
		 }
    	}//end checking if we are in page 3
    }
    
    public void mdhaminPage(){
    	if(mdhamini!=null){
    	String jinaz=mdhamini.getText().toString();
    	String simuz=simumdhamini.getText().toString();
    	if(jinaz.equals("") || simuz.equals("")){
    		//fomu is empty
    	}else{
    	setValues = new ArrayList<Object>();
		 setValues.add(jinaz);
		 setValues.add(simuz);
		 Utilities.setSerializableList(RegisterPageHolder.this,PREFS_NAME, "jinamdhamini", setValues);
    	}
    	ArrayList<Object> list=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME,"jinamdhamini");
    	if(list!=null){
    	mdhamini.setText(list.get(0).toString());
    	simumdhamini.setText(list.get(1).toString());
    	}
    	String mdhaUriz=Utilities.getStoredString(RegisterPageHolder.this, PREFS_NAME, "mdhamini");
    	if(mdhaUriz!=null){
    	setImage(mdhaUriz,mdhaminiView);
    	}
    	}
    	
    	if(mtejaView!=null){
    		 String mteUriz= Utilities.getStoredString(RegisterPageHolder.this, PREFS_NAME, "mteja");
    	        if(mteUriz!=null){
    	    	setImage(mteUriz,mtejaView);
    	        }
    	}
			
    }
    
    public void sendData(View v){
    	EditText jina=(EditText)findViewById(R.id.jina);
   pager.beginFakeDrag();
    pager.fakeDragBy(150);
    pager.endFakeDrag();
  
        //;
    	Toast.makeText(RegisterPageHolder.this, "Hellow hellow "+jina.getText().toString(), Toast.LENGTH_LONG).show();
    }

  
    
    public void pichaMdhamini(View v){
    	// create Intent to take a picture and return control to the calling application
	   if(v.getId()==R.id.pichamdhamini){
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    fileUriMdhamini = CameraProcess.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	   // this.myMdhaUri=fileUriMdhamini;
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMdhamini);
	    intent.putExtra("rafa",fileUriMdhamini );;
	    // set the image file name
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("mdhamini",fileUriMdhamini.toString() );
	      // Commit the edits!
	      editor.commit();
	 
	    
	   
 	    startActivityForResult(intent, MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	 
	   }
    }
    
    public void pichaMteja(View v){
    	// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    fileUriMteja = CameraProcess.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriMteja); // set the image file name
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("mteja",fileUriMteja.toString() );
	      // Commit the edits!
	      editor.commit();
	    // start the image capture Intent
	    startActivityForResult(intent, MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    	
    }
    public void mdhaminiElements(){
    	//  progress=(ProgressBar)findViewById(R.id.progress);
    	  if(mdhamini==null || simumdhamini==null || mdhaminiView==null){
	        mdhamini=(EditText)findViewById(R.id.jinamdhamini);
	        simumdhamini=(EditText)findViewById(R.id.simumdhamini);
	        mdhaminiView=(ImageView)findViewById(R.id.mdhaminiview);
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	        String mdhaUriz = settings.getString("mdhamini", null);
	        if(mdhaUriz!=null){
	    	setImage(mdhaUriz,mdhaminiView);
	        }
    	  }
    }
    public void mtejaElements(){
    	if(mtejaView==null){
    	mtejaView=(ImageView)findViewById(R.id.mtejaview);
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mteUriz= settings.getString("mteja", null);
        if(mteUriz!=null){
    	setImage(mteUriz,mtejaView);
        }
    	}
    }
    //init and adding biashara spinner
public void biasharaSpinner(){
	 hudumu=new Utilities();
     lipamuda=new Utilities();
	  
 	biashara=(EditText)findViewById(R.id.biashara);
	kikundi=(EditText)findViewById(R.id.kikundi);
	kiwango=(EditText)findViewById(R.id.ombikiwango);
	kianzio=(EditText)findViewById(R.id.kuanziakiwango);
	banki=(EditText)findViewById(R.id.banki);
	maliposiku=(EditText)findViewById(R.id.maliposiku);
	
	    spinHuduma=(Spinner)findViewById(R.id.huduma);
	    spinMuda=(Spinner)findViewById(R.id.mudamalipo);
		 spinHuduma.setOnItemSelectedListener(this);
		 spinHuduma.setOnItemSelectedListener(this);
		 
		 int mudas=spinMuda.getChildCount();
		 int hudumas=spinHuduma.getChildCount();
		 //setting data to huduma spin
		 if(hudumas<1){
		 forSpin=new DatabaseOperation(RegisterPageHolder.this);
		 ArrayList<String> hudumaSpin=forSpin.getHuduma();
		 forSpin.close();
		 if(hudumaSpin!=null && hudumaSpin.size()>0){
		    hudumaList=hudumaSpin;
		 }else{
			 hudumaList=hudumu.getSpinnerData(urls,"huduma","","");
			 for(String mydata: hudumaList){
  			 ops=new DatabaseOperation(RegisterPageHolder.this);
  			ops.insertHuduma(mydata);
  			ops.close();
  		}
		 }
		 hudumu.addSpinnerData(spinHuduma, new ArrayAdapter<String>(RegisterPageHolder.this,
	    			android.R.layout.simple_spinner_item, hudumaList));
		 }
		 //setting data to muda spin
		 if(mudas<1){
		 forSpin=new DatabaseOperation(RegisterPageHolder.this);
		 ArrayList<String> mudaSpin=forSpin.getMuda();
		 forSpin.close();
		 if(mudaSpin!=null && mudaSpin.size()>0){
		    mudaList=mudaSpin;
		 }else{
			 mudaList=hudumu.getSpinnerData(urls,"mudamalipo","","");
			 for(String mydata: mudaList){
  			 ops=new DatabaseOperation(RegisterPageHolder.this);
  			ops.insertMuda(mydata);
  			ops.close();
  		}
		 }
		 lipamuda.addSpinnerData(spinMuda, new ArrayAdapter<String>(RegisterPageHolder.this,
	    			android.R.layout.simple_spinner_item, mudaList));
		 }
		 //end getting spinn data		 
}

public void initSpinners(){
	
	jina=(EditText)findViewById(R.id.jina);
	tareheKuzaliwa=(EditText)findViewById(R.id.tarehekuzaliwa);
	simu=(EditText)findViewById(R.id.simu);
	nambaKitambulisho=(EditText)findViewById(R.id.nambakitambulisho);
	ainaKitambulisho=(EditText)findViewById(R.id.ainakitambulisho);
	mke=(RadioButton)findViewById(R.id.mke);
	mme=(RadioButton)findViewById(R.id.mume);
	tareheKuzaliwa.setKeyListener(null);
	tareheKuzaliwa.setOnFocusChangeListener(new OnFocusChangeListener(){

		@SuppressWarnings("deprecation")
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			showDialog(DATE_DIALOG_ID);	
		}
			
		}
		
	});
	tareheKuzaliwa.setOnClickListener(new OnClickListener(){

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			showDialog(DATE_DIALOG_ID);	
		}
		
	});

	
	spinMkoa=(Spinner)findViewById(R.id.mkoa);
	
	 spinWilaya=(Spinner)findViewById(R.id.wilaya);
	 spinKata=(Spinner)findViewById(R.id.kata);
	 spinMkoa.setOnItemSelectedListener(this);
	spinWilaya.setOnItemSelectedListener(this);
	 spinKata.setOnItemSelectedListener(this);
	 int childs=spinMkoa.getChildCount();
	 if(childs<1){
	 forSpin=new DatabaseOperation(RegisterPageHolder.this);
	 ArrayList<String> mkoaSpin=forSpin.getMkoa();
	 forSpin.close();
	 if(mkoaSpin!=null && mkoaSpin.size()>0){
		mkoaList=mkoaSpin;
	 }else{
		 mkoaList=getSpinnerData("mikoa","","");
		 for(String mydata: mkoaList){
			 ops=new DatabaseOperation(RegisterPageHolder.this);
			ops.insertMkoa(mydata);
			ops.close();
			ops=null;
		}
	 }
	 
	 mkoaAdapt=new ArrayAdapter<String>(RegisterPageHolder.this,
   			android.R.layout.simple_spinner_item,mkoaList );
	 addSpinnerData(spinMkoa,mkoaAdapt);
	 }
	 
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

   public void wilayaKataSpinner(AdapterView<?> adapterView, View view, int position){
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
			forSpin=new DatabaseOperation(RegisterPageHolder.this);
	   		 ArrayList<String> wilayaSpin=forSpin.getWilaya(str);
	   		forSpin.close();
	   		 if(wilayaSpin!=null && wilayaSpin.size()>0){
	   			wilayaList=wilayaSpin;
	   		 }else{
	   			 wilayaList=getSpinnerData("wilaya","",str);
	   			for(String mydata: wilayaList){
	    			 ops=new DatabaseOperation(RegisterPageHolder.this);
	    			ops.insertWilaya(mydata, str);
	    			ops.close();
	    		}
	   		 }
	   		 
			wilayaAdapt=new ArrayAdapter<String>(RegisterPageHolder.this,
	    			android.R.layout.simple_spinner_item, wilayaList);
			 addSpinnerData(spinWilaya,wilayaAdapt);	
		}else if(id==wilayaId){
			forSpin=new DatabaseOperation(RegisterPageHolder.this);
	   		 ArrayList<String> kataSpin=forSpin.getKata(str);
	   		forSpin.close();
	   		 if(kataSpin!=null && kataSpin.size()>0){
	   			kataList=kataSpin;
	   		 }else{
	   			 kataList=getSpinnerData("kata","",str);
	   			for(String mydata: kataList){
	    			 ops=new DatabaseOperation(RegisterPageHolder.this);
	    			ops.insertKata(mydata,str);
	    			ops.close();
	    		}
	   		 }
	   		 
			kataAdapt=new ArrayAdapter<String>(RegisterPageHolder.this,
	    			android.R.layout.simple_spinner_item, kataList);
			addSpinnerData(spinKata,kataAdapt);
		}else if(id==kataId){
			
		}
   }

	@Override
	public void onItemSelected(AdapterView<?> adapterView,View view, int position,
			long arg3) {
		if(spinWilaya!=null && spinKata!=null){
			
		wilayaKataSpinner(adapterView,view,position);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		setDefaultValues();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

	public void familiItems(){
		 LLEnterText=(LinearLayout) findViewById(R.id.chumbawatoto);
		    UmeoaEnterText=(LinearLayout)findViewById(R.id.chumbakuoa);
		    idadiWatoto=(EditText)findViewById(R.id.idadiwatoto);
		    umeoa=(CheckBox)findViewById(R.id.umeoa);
		    //Tumeoweka check action ili kuweka field ya jina la mke au mume.
			  umeoa.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					Boolean kaoa=umeoa.isChecked();
					myWidth=idadiWatoto.getWidth();
					Log.e("Umeoa checked","NImeoa");
					if(kaoa){
						String hint="Jina La Mume/Mke";
						 UmeoaEnterText.addView(linearlayout(400,hint,myWidth,editListUmeoa));	
					}else{
						if(UmeoaEnterText!=null){
						 UmeoaEnterText.removeViewAt(0);
						}
					}
					
				}
			  });
			  

			    //tunaongeza field kulingana na idadi ya watoto.
			    idadiWatoto.addTextChangedListener(new TextWatcher(){
					@Override
					public void afterTextChanged(Editable arg0) {
						String idadi=idadiWatoto.getText().toString().trim();
						myWidth=idadiWatoto.getWidth();
						 int childs=LLEnterText.getChildCount();
				    	 if(childs>0){
				    		LLEnterText.removeAllViews();
				    		for(int i=0; i<editListWatoto.size(); i++){
				    		editListWatoto.remove(i);
				    		}
				    		
				    	 }
						if(!idadi.equals("")){
							  int idadiW=Integer.parseInt(idadi);
							    if(idadiW>0){
							    	for(int i=1; i<=idadiW; i++){
							    		String hint="Jina Mtoto "+i;
							    	
							    		   LLEnterText.addView(linearlayout(_intMyLineCount,hint,myWidth,editListWatoto));
							    		   _intMyLineCount=i;
							    	}
							    }

						}else{
							  LLEnterText.removeAllViews();	
						    	 if(childs>0){
						    		LLEnterText.removeAllViews();
						    		for(int i=0; i<editListWatoto.size(); i++){
						    		editListWatoto.remove(i);
						    		}
						    	 }
						}
					}
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {}
					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						LLEnterText.removeAllViews();	
						int childs=LLEnterText.getChildCount();
				    	 if(childs>0){
				    		LLEnterText.removeAllViews();
				    		for(int i=0; i<editListWatoto.size(); i++){
				    		editListWatoto.remove(i);
				    		}
				    	 }
						
					}
			    	   
			       });
	}
	
	   private EditText editText(int _intID,String hitText,int width,List<EditText> editList) {
           EditText editText = new EditText(this);
           editText.setId(_intID);
           if(width!=200){
           editText.setHint(hitText);
           }else{
           editText.setText(hitText);   
           }
           editText.setMinWidth(LLEnterText.getWidth());
           editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
           editList.add(editText);
           
           return editText;
       }
	    
	    private RelativeLayout linearlayout(int _intID,String editTextHint,int width,List<EditText> editList)
	    {
	    	int layId=(_intID +5)* 3;
	        RelativeLayout LLMain=new RelativeLayout(this);
	        LLMain.setId(layId);
	       // LLMain.addView(textView(_intID));
	        LLMain.addView(editText(_intID,editTextHint,width,editList));
	       // LLMain.setOrientation(LinearLayout.HORIZONTAL);
	        LLMain.refreshDrawableState();
	        linearlayoutList.add(LLMain);
	        return LLMain;

	    }
    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
         String mdhaUriz = settings.getString("mdhamini", null);
         String mteUriz= settings.getString("mteja", null);
	        if (requestCode == MDHA_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            
	            	 if(data!=null){
	            	 String files=fileUriMdhamini.getEncodedPath();
	            	 bitmap=BitmapFactory.decodeFile(files);
	            	mdhaminiView.setImageBitmap(bitmap);
	            	 }else{
	            		 if(mdhaUriz!=null){
	            			setImage(mdhaUriz,mdhaminiView); 
	            		 }else{
	            			 
	              Toast.makeText(RegisterPageHolder.this, " Tafadhari Piga Nyingine"+fileUriMdhamini, Toast.LENGTH_LONG).show();
	            		 }
	            		 }
	            	 } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(RegisterPageHolder.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
	                // User cancelled the image capture
	            } else {
	                // Image capture failed, advise user
	            }
	        }
	        
	        //mteja image
	        if (requestCode == MTE_CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	                // Image captured and saved to fileUri specified in the Intent
	            //	 Toast.makeText(NextPage.this,"myfile"+fileUri, Toast.LENGTH_LONG).show();
	            	
	            	 if(data!=null){
	            	
	            	 }else{
	            		 if(mteUriz!=null){
	            		
	    	            	setImage(mteUriz,mtejaView);
	            		 }else{
	           // Toast.makeText(RegisterPageHolder.this,"Picha haija hifadhiwa Tafadhari Piga Nyingine", Toast.LENGTH_LONG).show();
	            		 }  	 
	            	 }
	               // Toast.makeText(NextPage.this, "Image saved to:\n"+data.getData(), Toast.LENGTH_LONG).show();
	            } else if (resultCode == RESULT_CANCELED) {
	            Toast.makeText(RegisterPageHolder.this, "Camera Cancelled:", Toast.LENGTH_LONG).show();
	                // User cancelled the image capture
	            } else {
	                // Image capture failed, advise user
	            }
	        }
	       
	    }
	 
	    public void setImage(String uri,ImageView imageView){
	    	 Uri my=Uri.parse(uri);
			 String files= my.getEncodedPath();
			 File f=new File(files);
			bitmap=Utilities.decodeFile(f, 60);
        	imageView.setImageBitmap(bitmap);	
	    }
	    
	    public void sendMdhaminiData(View v){
	    	setDefaultValues();
	    	int saved=0;
	    	//EditText jina=(EditText)findViewById(R.id.jina);
	    	//Toast.makeText(RegisterPageHolder.this, "Qushinei "+jina.getText().toString(), Toast.LENGTH_LONG).show();
	    	ArrayList<Object> p=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "profile");
	    	ArrayList<Object> b=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "biashara");
	    	String images=Utilities.getStoredString(RegisterPageHolder.this,PREFS_NAME,"mteja");
	    	String[] data=new String[17];
	    	if(b!=null && p!=null){
	    	data[0]=p.get(0).toString();
	    	data[1]=p.get(1).toString();
	    	data[2]=p.get(2).toString();
	    	data[3]=p.get(3).toString();
	    	data[4]=p.get(4).toString();
	    	data[5]=p.get(5).toString();
	    	data[6]=p.get(6).toString();
	    	data[7]=p.get(7).toString();
	    	data[8]=p.get(8).toString();
	    	data[9]=b.get(0).toString();
	    	data[10]=b.get(1).toString(); 
	    	data[11]=b.get(2).toString();
	    	data[12]=b.get(3).toString();
	    	data[13]=b.get(4).toString();
	    	data[14]=b.get(5).toString();
	    	data[15]=b.get(6).toString();
	    	data[16]=images;
	    	 ops=new DatabaseOperation(RegisterPageHolder.this);
             int owner=ops.insertData(data,"profile");
             ops.close();
             ops=null;
             if(owner>0){
            	 //family
          ArrayList<Object> w=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "mke");
          ArrayList<Object> t=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "mtoto");
          if(w!=null && w.size()>0){
        	  String[] mke={""+owner,w.get(0).toString(),"mke"};
        	  ops=new DatabaseOperation(RegisterPageHolder.this);
        	 // Log.e("mke insert",w.get(0).toString() );
			  saved= ops.insertData(mke, "family");
			   ops.close();
			   ops=null;
          }
          
          if(t!=null && t.size()>0){
        	 for(int i=0; i<t.size(); i++){
        	  String[] mke={""+owner,t.get(i).toString(),"mtoto"};
        	  ops=new DatabaseOperation(RegisterPageHolder.this);
			  saved= ops.insertData(mke, "family");
			   ops.close(); 
			   ops=null;
        	 }
          }
          	
          //mdhamini
          ArrayList<Object> md=Utilities.getSerializableList(RegisterPageHolder.this,PREFS_NAME, "jinamdhamini");
         if(md!=null && md.size()>0){
        	 String mdha=Utilities.getStoredString(RegisterPageHolder.this,PREFS_NAME,"mdhamini");
 	    	
          String[] datas={""+owner,md.get(0).toString(),md.get(1).toString(),mdha};
          ops=new DatabaseOperation(RegisterPageHolder.this);
			saved=ops.insertData(datas, "mdhamini");
			  ops.close();
			  ops=null;
         }
         
             }
	    	}
	    	
	    	if(saved>0){
	    	Toast.makeText(RegisterPageHolder.this, "Fomu Imehifadhiwa", Toast.LENGTH_LONG).show();	
	    	}
	    }
	    
	   
	    
	   
}
