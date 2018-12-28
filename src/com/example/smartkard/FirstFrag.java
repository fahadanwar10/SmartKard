package com.example.smartkard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FirstFrag extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */

		public static final String ARG_SECTION_NUMBER = "section_number";
		public FirstFrag() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v= inflater.inflate(R.layout.first, container, false);
			 Button write = (Button)v.findViewById(R.id.write);
			
			 write.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						
                       
						String a=MainActivity.writeusn();
							MainActivity.writetag(a);
						
							
						
					}
					});
			
						
						
						
						
						// Create a new TextView and set its text to the fragment's section
			// number argument value.
			
			/*TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));*/
		/*Intent i=new Intent();
		getStr(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));*/
			return v;  
		}
		
	}