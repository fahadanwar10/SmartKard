package com.example.smartkard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String filedata;
	static ArrayAdapter<String> madapter;
	static NfcAdapter adapter;
	static PendingIntent pendingIntent;
	IntentFilter writeTagFilters[];
	static boolean writeMode;
	static Tag mytag=null;
	static String k;
	static Context ctx;
	Tag detectedTag;
	static String text;
	static Intent intent;
	static int flag=0,c=0,ck=0;
	
	 ActionBar actionBar ;
	 static int j=0;
	android.support.v4.app.Fragment fr;
	static TextView assgn; 
	//NfcAdapter adapter;
	//PendingIntent pendingIntent;
	//IntentFilter writeTagFilters[];
	IntentFilter[] readTagFilters;
	static LayoutInflater inflatera;
	static ViewGroup containera;
	static Bundle savedInstanceStatea;
	static SharedPreferences preferences ;
 public static void getStr(String j){
	 k= j;
	 
 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx=this;
		preferences= PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
		if (isFirstRun)
		{
		    // Code to run once
			final Dialog dialog=new Dialog(ctx);
			final EditText et=new EditText(ctx);
			Button b=new Button(ctx);
			b.setText("Ok");
			LinearLayout l=new LinearLayout(ctx);
			l.setOrientation(LinearLayout.VERTICAL);
			l.addView(et);
			l.addView(b);
			b.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					String k=et.getText().toString();
					if(k!=""){
						
						  SharedPreferences.Editor editor = preferences.edit();
						  editor.putString("USN",k);
						  editor.commit();
					}	
					dialog.hide();
				}
				});
			dialog.setContentView(l);
			dialog.show();
		    SharedPreferences.Editor editor = wmbPreference.edit();
		    editor.putBoolean("FIRSTRUN", false);
		    editor.commit();
		}
		setContentView(R.layout.activity_main);
		
		intent=getIntent();
		// Set up the action bar.
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		c=1;
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
				
						actionBar.setSelectedNavigationItem(position);
					}
				});
             
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
			
		}
		
		adapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter filter2     = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };
		readTagFilters = new IntentFilter[]{tagDetected,filter2};
	}
	public static String writeusn(){
		  String name = preferences.getString("USN","");
		  return name;
	}
	@Override
	protected void onNewIntent(Intent intent){
		setIntent(intent);
		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);    
			this.intent=intent;
			Toast.makeText(this, this.getString(R.string.ok_detection), Toast.LENGTH_LONG ).show();
			check();
		}
	}
	public static String readfile(){
		String FILENAME = "student.txt";
		String ret="";
		 try {
				File myFile = new File(Environment.getExternalStorageDirectory()+"/nfcpro/"+FILENAME);
				FileInputStream fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(
						new InputStreamReader(fIn));
				
				ret= myReader.readLine().toString();
				myReader.close();
				
				
			} catch (Exception e) {
				Toast.makeText(ctx, e.getMessage(),Toast.LENGTH_SHORT).show();
			}
		 return ret;
		
	}
	static void refresh(){
		if(mytag==null){
			Toast.makeText(ctx,"touch the tag ", Toast.LENGTH_SHORT).show();
		}
		else{
		readtag();
		mytag=null;
		if(text.length()<=11){
			Toast.makeText(ctx,"Data has not been written properly,try tapping the kiosk again ", Toast.LENGTH_SHORT).show();
		}
		else{
		writefile(text);
		Toast.makeText(ctx,"Data has been refreshed", Toast.LENGTH_SHORT).show();
		}
		}
	}
	 static void readtag(){
		 
		Ndef ndef = Ndef.get(mytag);
	
		
	       try{
	            ndef.connect();
	            
	           
	           Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	          
	           if (messages != null) 
	            {
	        	   ck=0;
	                NdefMessage[] ndefMessages = new NdefMessage[messages.length];
	                for (int i = 0; i < messages.length; i++) {
	                	
	                    ndefMessages[i] = (NdefMessage) messages[i];
	                }
	                
	            NdefRecord record = ndefMessages[0].getRecords()[0];

	            byte[] payload = record.getPayload();
	            
	            text = new String(payload);
	       
	            
	            
	            ndef.close();
	            
	            }
	           else
	        	   
	           {
	        	   ck=1;
	        	   Toast.makeText(ctx,"The tag is empty", Toast.LENGTH_SHORT).show();
	           }
	}
	       catch (Exception e) {
	        	
	            Toast.makeText(ctx,e.getMessage(), Toast.LENGTH_SHORT).show();
	           }
	 
		
			 
		 
	}
	//writing to flat file
	static void writefile(String s){
		String FILENAME = "student.txt";
		File dir = new File(Environment.getExternalStorageDirectory() + "/nfcpro");
		String g=dir.toString();
		 	if(!dir.exists())
		 		{
		 		 
		 			dir.mkdir();//directory is created;
		 			
		 		}
		 	try {
		 		File direct = new File(g+"/"+FILENAME);
		 		if(!direct.exists()){
		 			
		 			direct.createNewFile();
		 			
		 		}
		 		String k = direct.toString(); 
		 		FileOutputStream fOut = new FileOutputStream(k);
		 		
		 		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		 		myOutWriter.write(s);
		 		myOutWriter.close();
		 		fOut.flush();
		 		fOut.close();
		 		
		 
		 	} catch (Exception e) {
		 		Toast.makeText(ctx, e.getMessage(),
				Toast.LENGTH_SHORT).show();
		 	}
	}
		
	
public static  void writetag(String m){
	try {
		if(mytag==null){
			Toast.makeText(ctx, ctx.getString(R.string.error_detected), Toast.LENGTH_LONG ).show();
		}else{
			write(m,mytag);
			Toast.makeText(ctx, "Tag written", Toast.LENGTH_LONG ).show();
			
		}
	} catch (IOException e) {
	Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG ).show();
		e.printStackTrace();
	} catch (FormatException e) {
		Toast.makeText(ctx, ctx.getString(R.string.error_writing) , Toast.LENGTH_LONG ).show();
		e.printStackTrace();
	}
	}
private static void write(String text, Tag tag) throws IOException, FormatException {

	NdefRecord[] records = { createRecord(text) };
	NdefMessage  message = new NdefMessage(records);
	// Get an instance of Ndef for the tag.
	Ndef ndef = Ndef.get(tag);
	// Enable I/O
	ndef.connect();
	// Write the message
	ndef.writeNdefMessage(message);
	// Close the connection
	ndef.close();
	
}
private static NdefRecord createRecord(String text) throws UnsupportedEncodingException {
	String lang       = "en";
	String id="20";
	byte[] textBytes  = text.getBytes();
	byte[] langBytes  = lang.getBytes("US-ASCII");
	int    langLength = langBytes.length;
	int    textLength = textBytes.length;
	byte[] payload    = new byte[1 + textLength];

	// set status byte (see NDEF spec for actual bits)
	//payload[0] = (byte) textLength;

	// copy langbytes and textbytes into payload
	//System.arraycopy(langBytes, 0, payload, 1,              langLength);
	//arraycopy(Object src, int srcPos, Object dst, int dstPos, int length)
	System.arraycopy(textBytes, 0, payload, 1, textLength);

	NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

	return recordNFC;
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
	
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if(position==0){
				Fragment f=new FirstFrag();
				
				return f;
			}
			else if(position==1){
				Fragment f=new SecondFrag();
								
				return f;
			}
			else if(position==2){
				Fragment f=new DummySectionFragment();
				fr=f;
				return f;
			}
			return null;
			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
			
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends ListFragment {
		 
	    
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        /** Creating an array adapter to store the list of countries **/
	    	
	if(flag==1){
		wrfile(text);
		flag=0;
	}
	 int i=0;
			 String thisLine="";
				String a="";
	        /** Setting the list adapter for the ListFragment */
				inflatera=inflater;
				containera=container;
				savedInstanceStatea=savedInstanceState;
				int start;
				String suffix;
	        ArrayList<String> q=new ArrayList<String>();
	        
	        try {
				File myFile = new File(Environment.getExternalStorageDirectory()+"/nfcpro/"+"names.txt");
				//FileInputStream fIn = new FileInputStream(myFile);
				//BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
					
				BufferedReader myReader = new BufferedReader(new FileReader(myFile));
					
				while ((thisLine=myReader.readLine()) != null) {
					q.add(thisLine.replace(".txt", ""));
					 i++;
	        }
				Collections.reverse(q);
				myReader.close();
	        }
	        catch(Exception e){
	        	
	        }
	      
	        madapter = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1,q);
	        setListAdapter(madapter);
	        return super.onCreateView(inflater, container, savedInstanceState);
	        
	    }
	   
	    @Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
	    	String item = (String) getListAdapter().getItem(position);
	    	readassgn(item);

	    }
	    @Override
	    public void onViewCreated (View view, Bundle savedInstanceState){
        ListView listView = getListView();
        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    File file = new File(Environment.getExternalStorageDirectory() + "/nfcpro/"+madapter.getItem(position)+".txt");
                                    boolean result = file.delete();
                                    madapter.remove(madapter.getItem(position));
                                    try{
                                    File dir = new File(Environment.getExternalStorageDirectory() + "/nfcpro");
                                	String g=dir.toString();
                                    File namefile = new File(g+"/names.txt");
                                    FileOutputStream f = new FileOutputStream(namefile);
                        	 		OutputStreamWriter myOut = new OutputStreamWriter(f);
                        	 		for(int k=(madapter.getCount())-1;k>=0 ;k--){
                        	 			myOut.write(madapter.getItem(k)+".txt"+"\n");
                        	 		}
                        	 		myOut.close();
                        	 		f.flush();
                        	 		f.close();
                                }catch(Exception e){
                                	Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                }
                                
                                madapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());
        
	}
	}
	/*public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public  View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
		
			inflatera=inflater;
			containera=container;
			savedInstanceStatea=savedInstanceState;
			LinearLayout ch1=new LinearLayout(getActivity());
			LinearLayout.LayoutParams pramn=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			ch1.setLayoutParams(pramn);
			j=1;
			
			Toast.makeText(ctx,"here", Toast.LENGTH_SHORT).show();
			assgn = new TextView(getActivity());
			if((flag==1)){
		wrfile(text);
		flag=0;
		//readassgn();
		
		}
			else{
				
				//readassgn();
				
			}
		return assgn;
		}
		
		
	}*/
	@Override
	public void onPause(){
		super.onPause();
		WriteModeOff();
	}

	@Override
	public void onResume(){
		super.onResume();
		WriteModeOn();
	}

	private void WriteModeOn(){
		writeMode = true;
		adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
	}

	private void WriteModeOff(){
		writeMode = false;
		adapter.disableForegroundDispatch(this);
	}
	void check(){
		readtag();
		if(ck==0){
		if(text.length()>11){
			
			int position = text.indexOf("$");   
			if(position<0){
				mytag=null;
				flag=1;
			
			fr.onCreateView(inflatera,containera,savedInstanceStatea);
			actionBar.setSelectedNavigationItem(2);
			
			
			
			
		}
		
		}
		}
		}
static void wrfile(String s){
	String name="names.txt";
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
  String FILENAME = sdf.format(new Date());
  FILENAME+=".txt";
	File dir = new File(Environment.getExternalStorageDirectory() + "/nfcpro");
	String g=dir.toString();
	
	 	if(!dir.exists())
	 		{
	 		 
	 			dir.mkdir();//directory is created;
	 			
	 		}
	 	try{
	 		File namefile = new File(g+"/"+name);
	 		if(!namefile.exists()){
	 			
	 			namefile.createNewFile();
	 			
	 		}
		 	FileOutputStream f = new FileOutputStream(namefile,true);
	 		OutputStreamWriter myOut = new OutputStreamWriter(f);
	 		myOut.write(FILENAME+"\n");
	 		myOut.close();
	 		f.flush();
	 		f.close();
	 	}
	 	catch(Exception e){
	 		Toast.makeText(ctx, e.getMessage(),
	 				Toast.LENGTH_SHORT).show();
	 	}
	 	try {
	 		File direct = new File(g+"/"+FILENAME);
	 		if(!direct.exists()){
	 			
	 			direct.createNewFile();
	 			
	 		}
	 		String k = direct.toString(); 
	 		FileOutputStream fOut = new FileOutputStream(k);
	 		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	 		myOutWriter.write(s);
	 		myOutWriter.close();
	 		fOut.flush();
	 		fOut.close();
	 			 
	 	} catch (Exception e) {
	 		Toast.makeText(ctx, e.getMessage(),
			Toast.LENGTH_SHORT).show();
	 	}
}
static void readassgn(String item){
	String ret="";
	Dialog dialog=new Dialog(ctx);
	assgn=new TextView(ctx);
	LinearLayout ch1=new LinearLayout(ctx);
	String  thisLine;
	String buff="";
	 try {
		 Toast.makeText(ctx,""+item,Toast.LENGTH_SHORT).show();
			File myFile = new File(Environment.getExternalStorageDirectory()+"/nfcpro/"+item+".txt");
			//FileInputStream fIn = new FileInputStream(myFile);
			//BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			BufferedReader myReader = new BufferedReader(new FileReader(myFile));
			/*while ((thisLine=myReader.readLine()) != null) {
				buff+=thisLine+"\n";
			}*/	 
			ret=myReader.readLine().toString(); // end while 
//ret=buff.toString();
			String z[]=ret.split("\\@");
			for(int i=0;i<z.length;i++){
				buff+=z[i]+"\n";
				
			}
			myReader.close();
			
			assgn.setText(buff);
			
			assgn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			
			ch1.addView(assgn);
			
			dialog.setContentView(ch1);
			dialog.setTitle("");
			 
			 
			 dialog.show();
		} catch (Exception e) {
			Toast.makeText(ctx,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
		}
	 
}
	
}
