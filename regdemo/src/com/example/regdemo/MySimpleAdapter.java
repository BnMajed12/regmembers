package com.example.regdemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MySimpleAdapter extends SimpleAdapter {
	LayoutInflater inflates=null;
	 ArrayList<HashMap<String,String>> itemarray=null;
	 String[] mapKey=null;
	  private int viewlayoutid;
	  private int textviewid;
	  private int imageiconid=0;
	  private int imageViewId=0;
	 private int itemIndex=0;
	 private int addtocart=0;
	 private int addtoPlaylist=0;
	 private int itemButtonId=0,loadingIconId=0;
	 private int[] dataTextView=null;
	private ArrayList<TextView> textviews,addplaylist,addcart;
	private ArrayList<ProgressBar> loadingIcon;
 private  TextView itemPlayBtn;
	public MySimpleAdapter(Context context,
			ArrayList< HashMap<String, String>> data, int resource, String[] from,
			int[] to,LayoutInflater inflates,int viewlayoutid,int textViewResourceId,String[] mapKey) {
		super(context, data, resource, from, to);
		itemarray=data;
		this.inflates=inflates;
		this.viewlayoutid=viewlayoutid;
		this.textviewid=textViewResourceId;
		  textviews=new ArrayList<TextView>();
		  addplaylist=new ArrayList<TextView>();
		  addcart=new ArrayList<TextView>();
		  loadingIcon=new ArrayList<ProgressBar>();
		  this.mapKey=mapKey;
          this.dataTextView=to;
	}
	
	public int getTextViewResourceId(){
		return this.textviewid;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		
     View row=inflates.inflate(viewlayoutid, parent, false);
     if(itemarray!=null){
     ArrayList<HashMap<String,String>> items= itemarray;
     HashMap<String,String> item=items.get(position);
     
    String titles= item.get(mapKey[0]);
   String subtitle=  item.get(mapKey[1]);
   TextView itemtitle=(TextView)row.findViewById(dataTextView[0]);
   itemtitle.setText(titles);
   TextView itemsubtitle=(TextView)row.findViewById(dataTextView[1]);
   itemsubtitle.setText(subtitle);
     }
   //getimageiconid
   
     if(getImageIconId()!=0 && getImageViewId()!=0){
     ImageView iv=(ImageView)row.findViewById(getImageViewId());
     iv.setImageResource(getImageIconId());
     }
     
     //getitemindexview
     if(getItemIndexView()!=0){
    	 TextView itemindex=(TextView)row.findViewById(getItemIndexView());
    
    	 int index=position+1;
    	 itemindex.setText(index+".");
     }
     
     //getitemimage
     if(getItemImage()!=0){
    TextView itemIcon=(TextView)row.findViewById(getItemImage());
     //itemPlayBtna.add(position);
    	itemIcon.setText(" ");
    	itemIcon.setHint(position+"");
    	 textviews.add((TextView)row.findViewById(getItemImage()));
    	this.itemPlayBtn=itemIcon;
     }
      
   
     //getAddPlaylistTextView()
     if(getAddPlaylistTextView()!=0){
    	// TextView itemaddplaylist=(TextView)row.findViewById(getAddPlaylistTextView());
    	 addplaylist.add((TextView)row.findViewById(getAddPlaylistTextView()));
     }
     
     if(getItemLoadIcon()!=0){
    	 loadingIcon.add((ProgressBar)row.findViewById(getItemLoadIcon()));
     }
     //getCartTextView
     if(getCartTextView()!=0){
    	//TextView itemaddcart=(TextView)row.findViewById(getCartTextView());
    	 addcart.add((TextView)row.findViewById(getCartTextView()));
     }
    	   return row;
		
	}
	
	  public TextView getItemPlayBtn(){
	    	 return this.itemPlayBtn;
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
	 * This method is used in artist activity to add song to playlist or cart
	 * @param textViewCarts
	 * @param textViewAddPlaylist
	 */
	public void setIconCart(int  textViewCarts){
		addtocart=textViewCarts;
		
	}
	
	public void setIconAddPlaylist(int textViewAddPlaylist){
		addtoPlaylist=textViewAddPlaylist;
	}
	
	/**
	 * This method is used to set the loadingIcon or progress of each item in the list.
	 * @param loadingIconId
	 */
	public void setItemLoadIcon(int loadingIconId){
		this.loadingIconId=loadingIconId;
	}
	
	/**
	 * This method is  used to get the id of loadingIcon
	 * @return
	 */
	public int getItemLoadIcon(){
		return this.loadingIconId;
	}
	/**
	 * this method is used to return carttextview id
	 * @return int carttextview id
	 */
	public int getCartTextView(){
		return this.addtocart;
	}
	/**
	 * this method is used to return playlist textview id
	 * @return int playlist textview id
	 */
	public int getAddPlaylistTextView(){
		return this.addtoPlaylist;
	}
	public ArrayList<HashMap<String,String>> getItemArray(){
		return this.itemarray;
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
	/**
	 * this method is used to return the list of all loading icon per item
	 * @return
	 */
	public ArrayList<ProgressBar> getLoadIcon(){
		return this.loadingIcon;
	}
	/**
	 * this method is used to return the array list of button add cart
	 * @return
	 */
	public ArrayList<TextView> getCartTextViewBtn(){
		return this.addcart;
	}
	/**
	 * This method is used to return array list of button add to playlist
	 * @return
	 */
	public ArrayList<TextView> getAddPlaylistBtn(){
		return this.addplaylist;
	}
	public ArrayList<TextView> getTextViewImage(){
		return textviews;
	}

}
