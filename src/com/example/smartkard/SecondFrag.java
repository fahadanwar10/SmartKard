package com.example.smartkard;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SecondFrag extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
	String text;
		public static final String ARG_SECTION_NUMBER = "section_number";
		static Context ctx;
		TableRow[] rowa=new TableRow[10];
		TextView[] suba = new TextView[40];
		TableRow row;
		int f=0,fl;
		WindowManager.LayoutParams lp;
		TextView sub,name,usn;
		ViewGroup.LayoutParams p;
		TextView sub1,secm,name1,name2,name3;
		RelativeLayout par;
		LinearLayout ch1,mom;
		TableLayout table;
		RelativeLayout.LayoutParams rel;
		
		 Dialog dialog;
		 ArrayList<TableRow> r=new ArrayList<TableRow>();
		public SecondFrag() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v= inflater.inflate(R.layout.second, container, false);
			//AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
			name =new TextView(getActivity());
			usn = new TextView(getActivity());
			dialog=new Dialog(getActivity());
			par=new RelativeLayout(getActivity());
			ch1=new LinearLayout(getActivity());
			mom=new LinearLayout(getActivity());
			ch1.setId(1111);
			LinearLayout.LayoutParams pram=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams momp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			rel=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			par.setLayoutParams(rel);
			ch1.setLayoutParams(momp);
			mom.setLayoutParams(pram);
			mom.setOrientation(LinearLayout.VERTICAL);
			table =new TableLayout(getActivity());
			TableLayout.LayoutParams tparam = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
			tparam.setMargins(10,2,10,12);
			table.setLayoutParams(tparam);
			//par.setOrientation(RelativeLayout.CENTER_VERTICAL);
			ch1.setOrientation(LinearLayout.VERTICAL);
			//LayoutParams lp=dialog.getWindow().getAttributes(); 
			table.setStretchAllColumns(true);  
		    table.setShrinkAllColumns(true);
		   
		    
			//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	         //dialog.setContentView(R.layout.pop); 
			ImageButton ref = (ImageButton)v.findViewById(R.id.refresh);
			ImageButton int1 = (ImageButton)v.findViewById(R.id.int1);
			
			ImageButton task = (ImageButton)v.findViewById(R.id.task);
			ImageButton att = (ImageButton)v.findViewById(R.id.att);
			ImageButton all = (ImageButton)v.findViewById(R.id.all);
			
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			
			 ref.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						MainActivity.refresh();
						
					}
					});
			 int1.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						//((ViewGroup)par.getParent()).removeView(par);
						//par.removeViewAt(1);
						par.removeAllViews(); 
						table.removeAllViews(); 
						mom.removeAllViews();
						text=MainActivity.readfile();
						
						String[] q= text.split("\\?");
						String[] d=q[2].split("\\$");
						row=new TableRow(getActivity());
						name1=new TextView(getActivity());
						name2=new TextView(getActivity());
						name3=new TextView(getActivity());
						name1.setText("Subject");
						name2.setText("Internal 1");
						name3.setText("Internal 2");
						name1.setTypeface(Typeface.SERIF, Typeface.BOLD);
						name2.setTypeface(Typeface.SERIF, Typeface.BOLD);
						name3.setTypeface(Typeface.SERIF, Typeface.BOLD);
						row.addView(name1);
						row.addView(name2);
						row.addView(name3);
						table.addView(row);
						for(int i=0;i<d.length;i++){
							String[] m=d[i].split("\\&");//m[0],m[1]
						tablei(m,1,2);
						}
						par.addView(table);
						
						dialog.setContentView(par);
						dialog.setTitle("Internal marks");
						 
						 //dialog.getWindow().setLayout(200,200);
						 dialog.show();
					}
					});
			
			 task.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						par.removeAllViews(); 
						table.removeAllViews(); 
						mom.removeAllViews();
						text=MainActivity.readfile();
						
						String[] q= text.split("\\?");
						String[] d=q[2].split("\\$");
						row=new TableRow(getActivity());
						name1=new TextView(getActivity());
						name2=new TextView(getActivity());
						name1.setText("Subject");
						name2.setText("Task");
						name1.setTypeface(Typeface.SERIF, Typeface.BOLD);
						name2.setTypeface(Typeface.SERIF, Typeface.BOLD);
						row.addView(name1);
						row.addView(name2);
						table.addView(row);
						for(int i=0;i<d.length;i++){
							String m[]=d[i].split("\\&");//m[0],m[3]
						table(m,3);
						}
						par.addView(table);
						
						dialog.setContentView(par);
						dialog.setTitle("Task marks");
						 dialog.show();
					}
					});
			 att.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						par.removeAllViews(); 
						table.removeAllViews();
						mom.removeAllViews();
						text=MainActivity.readfile();
						
						String[] q= text.split("\\?");
						String[] d=q[2].split("\\$");
						row=new TableRow(getActivity());
						name1=new TextView(getActivity());
						name2=new TextView(getActivity());
						name1.setText("Subject");
						name2.setText("Attendance");
						name1.setTypeface(Typeface.SERIF, Typeface.BOLD);
						name2.setTypeface(Typeface.SERIF, Typeface.BOLD);
						row.addView(name1);
						row.addView(name2);
						table.addView(row);
						for(int i=0;i<d.length;i++){
							String m[]=d[i].split("\\&");//m[1],m[4]
							row=new TableRow(getActivity());
							sub=new TextView(getActivity());
							sub.setText(m[0]);
							sub1=new TextView(getActivity());
							String[] a=m[4].split("\\%");
							if(Integer.parseInt(a[0])<75){
								sub1.setText(m[4]);
								sub1.setTypeface(Typeface.SERIF, Typeface.BOLD);
								sub1.setTextColor(Color.rgb(139,0,0));
							}
							else{
							sub1.setText(m[4]);
							}
							//secm=new TextView(getActivity());
							row.addView(sub);
							row.addView(sub1);
							table.addView(row);
						}
						par.addView(table);
						
						dialog.setContentView(par);
						dialog.setTitle("Attendance");
						 dialog.show();
					}
					});
			 all.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						ch1.removeAllViews();
						table.removeAllViews();  
						par.removeAllViews();
						mom.removeAllViews();
						if(f!=0){
						for(int j=0;j<fl;j++){rowa[j].removeAllViews();}
						}
						f=1;
						row=new TableRow(getActivity());
						name1=new TextView(getActivity());
						name2=new TextView(getActivity());
						TextView nam3=new TextView(getActivity());
						TextView nam4=new TextView(getActivity());
						TextView nam5=new TextView(getActivity());
						name1.setText("Subject");
						name2.setText("Internal 1");
						nam3.setText("Internal 2");
						nam4.setText("task");
						nam5.setText("Attendance");
						name1.setTypeface(Typeface.SERIF, Typeface.BOLD);
						 name1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
						name2.setTypeface(Typeface.SERIF, Typeface.BOLD);
						 name2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
						nam3.setTypeface(Typeface.SERIF, Typeface.BOLD);
						 nam3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
						nam4.setTypeface(Typeface.SERIF, Typeface.BOLD);
						 nam4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
						nam5.setTypeface(Typeface.SERIF, Typeface.BOLD);
						 nam5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
						row.addView(name1);
						row.addView(name2);
						row.addView(nam3);
						row.addView(nam4);
						row.addView(nam5);
						table.addView(row);
						text=MainActivity.readfile();
						
						spliting();
						 mom.addView(ch1);
						// ch1.setId(111);
						 //rel.addRule(RelativeLayout.BELOW,ch1.getId());
						mom.addView(table);
						par.addView(mom);
						dialog.setContentView(par);
						dialog.setTitle("All Details");
						 dialog.show();
					}
					});
		
			return v;  
		}
		void table(String[] a,int n){
			
				row=new TableRow(getActivity());
				sub=new TextView(getActivity());
				sub.setText(a[0]);
				sub1=new TextView(getActivity());
				sub1.setText(a[n]);
				//secm=new TextView(getActivity());
				row.addView(sub);
				row.addView(sub1);
				table.addView(row);
			}
		void tablei(String[] a,int n,int m ){
			
			row=new TableRow(getActivity());
			sub=new TextView(getActivity());
			sub.setText(a[0]);
			sub1=new TextView(getActivity());
			sub1.setText(a[n]);
			secm=new TextView(getActivity());
			secm.setText(a[m]);
			row.addView(sub);
			row.addView(sub1);
			row.addView(secm);
			table.addView(row);
		}
		void spliting(){
			String[] sp= text.split("\\?");
			name.setText(sp[0]);
		    usn.setText(sp[1]);
		    name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		    usn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		    name.setTypeface(Typeface.SERIF, Typeface.BOLD);
		    usn.setTypeface(Typeface.SERIF, Typeface.BOLD);
		     ch1.addView(name);
		    ch1.addView(usn);
		    rowsplit(sp[2]);
		    for(int i=0;i<r.size();i++){
				table.addView(r.get(i));
					
				}
		    
		}
		void rowsplit(String l){
			
			String[] rs=l.split("\\$");
			
			
			for(int i=0;i<rs.length;i++){
				fl=rs.length;
				 rowa[i] = new TableRow(getActivity());
				 //rowa[i].setBackgroundColor(Color.rgb(169,169,169));
				 //row[i].setBackgroundResource(R.drawable.border);
				 
				 String[] last=rs[i].split("\\&");
				
				 

			for(int k=0;k<last.length;k++){	
				
				suba[k] = new TextView(getActivity());
				if(k==4){
				String[] a=last[4].split("\\%");
				if ((Integer.parseInt(a[0]))<75){
					suba[k].setText(last[k]);
					suba[k].setTypeface(Typeface.SERIF, Typeface.BOLD);
					suba[k].setTextColor(Color.rgb(139,0,0));
				}
				else{suba[k].setText(last[k]);}
				}	
				else{suba[k].setText(last[k]);}
				rowa[i].addView(suba[k]);
				}
			r.add(rowa[i]);
			}
			}
		
}