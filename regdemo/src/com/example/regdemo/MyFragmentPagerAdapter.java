package com.example.regdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	
	final int PAGE_COUNT = 5;
	MyFragment myFragment;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int arg0) {
		int posi=arg0 + 1;
		 myFragment = new MyFragment();
		Bundle data = new Bundle();
		data.putInt("current_page", posi);
		myFragment.setArguments(data);
		
		return myFragment;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {		
		return PAGE_COUNT;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return getPageTitles(1+position);
	}
	
	
	public String getPageTitles(int position){
		String title="";
		switch(position){
		case 1:
	     title="Taarifa Binafsi";
		break;
		case 2:
			  title="Taarifa Za Familia";
		break;
		case 3:
			  title="Taarifa Za Biashara";
		break;
		case 4:
			  title="Picha Ya Mteja";
		break;
		case 5:
			  title="Taarifa Za Mdhamini";
			break;
		default:
			  title="";
		break;
		}
		return title;
	}
	
}
