package com.example.regdemo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment{

	int mCurrentPage;
	private View v;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();
		
		/** Getting integer data of the key current_page from the bundle */
		mCurrentPage = data.getInt("current_page", 0);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		switch(mCurrentPage){
		case 1:
			
		v = inflater.inflate(R.layout.activity_main, container,false);
		
		break;
		case 2:
			v = inflater.inflate(R.layout.nextpage, container,false);
		break;
		case 3:
			v = inflater.inflate(R.layout.biashara, container,false);
		break;
		case 4:
			v = inflater.inflate(R.layout.mtejapicha, container,false);
		break;
		case 5:
			v = inflater.inflate(R.layout.mdhamini, container,false);
			break;
		default:
			v = inflater.inflate(R.layout.activity_main, container,false);
		break;
		}
						
		return v;		
	}
	
	public String getPageTitle(int position){
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
