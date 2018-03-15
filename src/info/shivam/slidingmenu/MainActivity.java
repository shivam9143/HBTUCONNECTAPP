package info.shivam.slidingmenu;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.achartengine.GraphicalView;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import info.shivam.slidingmenu.adapter.LocationValue;
import info.shivam.slidingmenu.adapter.NavDrawerListAdapter;
import info.shivam.slidingmenu.model.Caller;
import info.shivam.slidingmenu.model.NavDrawerItem;
import info.shivam.slidingmenu.model.Showlist;


public class MainActivity extends AppCompatActivity {

	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =1 ;
	//copy
	private GraphicalView mChartView;
	LinearLayout li;
	private FirebaseAuth auth;
	LinearLayout li1;
	LinearLayout li2;
	ImageView imageView11;
	ImageView imageView1;
    Context context;
    LocationValue locationValue;
    GPSTracker gps;
    double latitude;
    double longitude;
	String res="",res1="";
    String IPaddress;
    Boolean IPValue;
    String macaddress ;
    String host;
    String address;
    String city;
    String loc="";
    Button dial;
    TextView text;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    Caller c;


	private ViewPager viewPager;
	private TabPagerAdapter tabPagerAdapter;
	private ActionBar actionBar;
	private String[] tabNames = { "Latest", "Completed", "Inprocess" ,"Overdue"};

	Context con;
	String resultedData = null;
	String h;
	String h1;
	int count2;
	String status1;
	String eid;
	JSONObject jobj1;
	SessionManager manager2=new SessionManager();
	SessionManager manager3=new SessionManager();
	ProgressDialog dialog;
	ArrayList<StoreData> arraylist = new ArrayList<StoreData>();
	ArrayList<String> array=new ArrayList<String>();

	int Record;

	int totaltask;
	int comptask;
	int intask;
	int overtask;
	int totalmile;
	int compmile;
	int inmile;
	int overmile;
	int totalproj;
	int compproj;
	int inproj;
	int overproj;
	String sesnid;
	TextView txtname;
	String intime;
	String outtime;
	String workhrs;
	 Dialog dialog1;
	 WhatsHotFragment fn;
	 GridView lst;
	 Showlist shlist;
	 ProgressDialog progress;
	 public static String rslt="";
	 //copy end
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// nav drawer title
	private CharSequence mDrawerTitle;
	//private LinearLayout Drawer ;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	SessionManager manager;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private FirebaseAuth.AuthStateListener authListener;
	LinearLayout drawerll;

	MenuItem profile;
	ImageView imageView;
	MenuItem employname;
	MenuItem attendance;
	SessionManager manager1;
	JSONObject jobj;
	JsonParser jsonparser;
	Drawable d;
	String empid;
	String status;

	/*private GraphicalView mChartView;
	LinearLayout li;*/
	@SuppressWarnings("ResourceType")
    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().show();

		//get firebase auth instance
		auth = FirebaseAuth.getInstance();

		//get current user
		final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		authListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user == null) {
					// user auth state is changed - user is null
					// launch login activity
					startActivity(new Intent(MainActivity.this, Login1.class));
					finish();
				}
			}
		};

		manager1=new SessionManager();
		/*status=manager1.getPreferences(MainActivity.this,"status");
	        sesnid=manager2.getPreferences(MainActivity.this, "session");*/
	        eid=manager3.getPreferences(MainActivity.this, "Email");
				mTitle = mDrawerTitle = getTitle();
               manager=new SessionManager();
		// load slide menu items
		 navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		//Drawer = ( LinearLayout) findViewById(R.id.drawer);

		mDrawerLayout = findViewById(R.id.drawer_layout);
		mDrawerList = findViewById(R.id.list_slidermenu);


		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Pages



		// Recycle the typed array
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout,
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		)
		{
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);

				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);


		if (savedInstanceState == null)
		{

			// on first time display view for first nav item
			displayView(0);
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		//View v = (View) menu.findItem(R.id.attend).getActionView();


		/* MenuItem item = menu.findItem(R.id.refresh);
		    Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
		    spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
*//*
		 jsonparser = new JsonParser();
			jobj = new JSONObject();
			LoadJS1 js1 = new LoadJS1();
			js1.execute("");
*/
		return true;
	}
	/*@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		*//*if(item.getTitle().toString().equalsIgnoreCase("Attendance"))
		{
		 dialog1 = new Dialog(this);
			dialog1.setContentView(R.layout.custom);
			dialog1.setTitle("Attendance...");

			// set the custom dialog components - text, image and button
		     text = dialog1.findViewById(R.id.text);
			//text.setText("In Time:");
			 text1 = dialog1.findViewById(R.id.text1);
			//text.setText("Out Time:");
			 text3 = dialog1.findViewById(R.id.text3);
			//text.setText("Working Hours:");
			 text2 = dialog1.findViewById(R.id.text2);
			//text.setText("Location:");
			 text4 = dialog1.findViewById(R.id.text4);
			ImageView image = dialog1.findViewById(R.id.image);
			image.setImageResource(R.drawable.cl);

			 dial = dialog1.findViewById(R.id.d);



			// if button is clicked, close the custom dialog
			dial.setOnClickListener(new OnClickListener()
			{
				@SuppressWarnings("static-access")
				@Override
				public void onClick(View v)
				{
						//dialog1.dismiss();
					try
					{
						Log.e("gpsCall", "cal");
						gps = new GPSTracker(MainActivity.this);
						if(gps.canGetLocation())
			            {
							Log.e("Below", "call");
			            	resultedData="1";

							latitude = gps.getLatitude();
			                 longitude = gps.getLongitude();
			            Log.e("Latitue", latitude+"");
			            Log.e("Long",longitude+"");

 			            LoadJS5 js3 = new LoadJS5();
	        				js3.execute("");
			            }
						else
			            {

			                gps.showSettingsAlert();
			            }


					}
			        	catch(Exception e)
						{
							Log.e("sdfsdf",e.getMessage().toString());
						}
				}

				});



				dialog1.show();
				LoadJS2 js2 = new LoadJS2();
			     js2.execute("");

		  }
*//*
		return super.onMenuItemSelected(featureId, item);
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId())
		{
		    case R.id.refresh:
			Intent intent = getIntent();
		    finish();
		    startActivity(intent);
            //Toast.makeText(getApplicationContext(),"Refresh Clicked",Toast.LENGTH_SHORT).show();
            return true;

		 //case R.id.photo:
             //Toast.makeText(getApplicationContext(),"Photo Clicked",Toast.LENGTH_SHORT).show();
           //  return true;
         //case R.id.text:
             //Toast.makeText(getApplicationContext(),"text Clicked",Toast.LENGTH_SHORT).show();
            // return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	/*@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}*/

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		String pos="";
		switch (position) {
		case 0:

			fragment = new HomeFragment();

			break;
		case 1:
			fragment=new ApproveLeave();
			break;
		case 2:
			fragment= new Changepswd();
			break;

		case 3:
			Log.e("123445545454", "1234567548548484");
				pos="yup";
			break;

		default:
			break;
		}


		if (pos != null)
		{
		  if(pos.matches("yup"))
		  {
              manager.setPreferences(MainActivity.this, "Email", "");
              Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
					mDrawerList.setItemChecked(position, true);
					mDrawerList.setSelection(position);
					setTitle(navMenuTitles[position]);
					mDrawerLayout.closeDrawer(mDrawerList);
			  		auth.signOut();
					Intent lg=new Intent(MainActivity.this,Login1.class);
					startActivity(lg);
					finish();
		  }
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void NetwordDetect() {

		 boolean WIFI = false;
		 boolean MOBILE = false;

		 ConnectivityManager CM = (ConnectivityManager)getApplicationContext(). getSystemService(Context.CONNECTIVITY_SERVICE);

		 NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

		 for (NetworkInfo netInfo : networkInfo) {

		 if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

		 if (netInfo.isConnected())

		 WIFI = true;

		 if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

		 if (netInfo.isConnected())

		 MOBILE = true;
		 }

		 if(WIFI == true)

		 {
		 IPaddress = GetDeviceipWiFiData();



		 }

		 if(MOBILE == true)
		 {

		 IPaddress = GetDeviceipMobileData();


		 }

		// Toast.makeText(getActivity(), "Your network ip: " +IPaddress, Toast.LENGTH_LONG).show();
		}


    public String GetDeviceipMobileData(){
		 try {
		 for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		 en.hasMoreElements();) {
		 NetworkInterface networkinterface = en.nextElement();
		 for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		 InetAddress inetAddress = enumIpAddr.nextElement();
		 if (!inetAddress.isLoopbackAddress()) {
		 return inetAddress.getHostAddress().toString();
		 }
		 }
		 }
		 } catch (Exception ex) {
		 Log.e("Current IP", ex.toString());
		 }
		 return null;
		 }

	 public String GetDeviceipWiFiData()
		 {

		 WifiManager wm = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

		 @SuppressWarnings("deprecation")

		 String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

		 return ip;

		 }

	private String capitalize(String s) {
			    if (s == null || s.length() == 0) {
			        return "";
			    }
			    char first = s.charAt(0);
			    if (Character.isUpperCase(first)) {
			        return s;
			    } else {
			        return Character.toUpperCase(first) + s.substring(1);
			    }
			}


	 public Bitmap getRoundedShape(Bitmap scaleBitmapImage)
	 {
		    int targetWidth = 90;
		    int targetHeight = 90;
		    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
		                        targetHeight,Bitmap.Config.ARGB_8888);

		    Canvas canvas = new Canvas(targetBitmap);
		    Path path = new Path();
		    path.addCircle(((float) targetWidth - 1) / 2,
		        ((float) targetHeight - 1) / 2,
		        (Math.min(((float) targetWidth),
		        ((float) targetHeight)) / 2),
		        Path.Direction.CCW);

		    canvas.clipPath(path);
		    Bitmap sourceBitmap = scaleBitmapImage;
		    canvas.drawBitmap(sourceBitmap,
		        new Rect(0, 0, sourceBitmap.getWidth(),
		        sourceBitmap.getHeight()),
		        new Rect(0, 0, targetWidth, targetHeight), null);
		    return targetBitmap;
		}

	 @Override
	 public void onBackPressed() {
	    super.onBackPressed();
	    FragmentManager fm = getSupportFragmentManager();
	    Fragment myFragment = fm.findFragmentById(R.id.frame_container);
	  //  if(myFragment.getContext()==getApplicationContext())
	    Log.e("fragment", myFragment.getFragmentManager().getFragments().toString());
	    String f="";
	    f=myFragment.getFragmentManager().getFragments().toString();
	    if(f.contains("HomeFragment"))
	    {
	    	  finish();
	    }
	    else
	    {
	    if(myFragment.isVisible()){
	       //Do what you want to do
	    	Intent i=new Intent(MainActivity.this,MainActivity.class);
	    	startActivity(i);
	    }
	    }
	 }

}
