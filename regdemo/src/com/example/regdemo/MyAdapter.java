package com.example.regdemo;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {
  LayoutInflater inflates=null;
  String[] itemarray=null;
  private int viewlayoutid;
  private int textviewid;
  private int imageiconid=0;
  private int imageViewId=0;
 private int itemIndex=0;
 private int itemButtonId=0;
private ArrayList<TextView> textviews;
 private  TextView itemPlayBtn;

	public MyAdapter(Context context,int resource, int textViewResourceId,String[] objects,LayoutInflater inflates,int viewlayoutid) {
		super(context,resource, textViewResourceId,objects);
		itemarray=objects;
		this.inflates=inflates;
		this.viewlayoutid=viewlayoutid;
		this.textviewid=textViewResourceId;
		  textviews=new ArrayList<TextView>();
		
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		
     View row=inflates.inflate(viewlayoutid, parent, false);
     String[] items= itemarray;
     if(getImageIconId()!=0 && getImageViewId()!=0){
     ImageView iv=(ImageView)row.findViewById(getImageViewId());
     iv.setImageResource(getImageIconId());
     }
     if(getItemIndexView()!=0){
    	 TextView itemindex=(TextView)row.findViewById(getItemIndexView());
    
    	 int index=position+1;
    	 itemindex.setText(index+".");
     }
     
     if(getItemImage()!=0){
    TextView itemIcon=(TextView)row.findViewById(getItemImage());
     //itemPlayBtna.add(position);
    	itemIcon.setText(" ");
    	itemIcon.setHint(position+"");
    	 textviews.add((TextView)row.findViewById(getItemImage()));
    	this.itemPlayBtn=itemIcon;
     }
    
     TextView tv=(TextView)row.findViewById(textviewid);
     tv.setText(items[position]);
    	   return row;   
       
		
	}
	
	/**
	 * This method is used to set image icon to display on left side of list item song
	 * @param imageIconId
	 * @param imageViewId
	 */
	public void setImageIconId(int imageIconId,int imageViewId){
	this.imageiconid=imageIconId;
	this.imageViewId=imageViewId;
	}
	
	/**
	 * This is used to return an id of image icon
	 * @return
	 */
	public int getImageIconId(){
		return this.imageiconid;
	}
	public int getImageViewId(){
		return this.imageViewId;
	}
	
	public void setItemIndexView(int itemIndexId){
		this.itemIndex=itemIndexId;
		
	}
	
	public int getItemIndexView(){
		return itemIndex;
	}
	
	public void setItemImage(int itemImageId){
		this.itemButtonId=itemImageId;
	}
	
	public int getItemImage(){
		return itemButtonId;
	}
	
	public ArrayList<TextView> getTextViewImage(){
		return textviews;
	}
 
}
