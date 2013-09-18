package com.example.regdemo;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCursorAdapter extends SimpleCursorAdapter {
	 private int layout; 
     private LayoutInflater inflater;
     private Context context;
     private int cellLayout;
     private int[] textViewId;
	@SuppressWarnings("deprecation")
	public CustomCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.layout = layout;
        this.context = context;
        this.cellLayout=layout;
        this.textViewId=to;
        inflater = LayoutInflater.from(context);
	}
	
	
     public int getLayout(){
    	 return this.layout;
     }
     
     public Context getContext(){
    	 return this.context;
     }
	  @Override
      public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //  Log.i("NewView", newViewCount.toString());
 
        View v = inflater.inflate(cellLayout, parent, false);

          return v;
      }
     
      @Override
      public void bindView(View v, Context context, Cursor c) {
                  //1 is the column where you're getting your data from
          String name = c.getString(1);
        ///  Log.e("from cursor","name: "+name);
         String uri=c.getString(2);
        // Log.e("from cursor","uri: "+uri);
          /**
           * Next set the name of the entry.
           */
         ImageView myimage=(ImageView)v.findViewById(this.textViewId[1]);
          TextView name_text = (TextView) v.findViewById(this.textViewId[0]);
          if (name_text != null) {
              name_text.setText(name);
          }
          
          if(myimage!=null){
        	  Uri my=Uri.parse(uri);
 			 String files= my.getEncodedPath();
        	  File f=new File(files);
        	  Bitmap bm=Utilities.decodeFile(f, 40);
        	  myimage.setImageBitmap(bm);
          }
      }

}
