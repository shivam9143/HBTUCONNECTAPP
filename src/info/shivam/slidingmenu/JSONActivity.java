/*package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class JSONActivity extends Activity 
{
	
	JsonParser jsonparser;
	
	JSONObject jobj;
	ListView lst;
	
	EditText editsearch;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	String[] proj_type;
	String[] proj_name;
	String[] assign_by;
	String[] task;
	ListViewAdapter adapter;
	Context con;
    LinearLayout mainLayout;
    
    String h;
    ProgressDialog dialog;
    TextView mktUrl;
    
	ArrayList<StoreData> arraylist = new ArrayList<StoreData>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.e("OnCreate.....",isInternetPresent.toString());
		setContentView(R.layout.lay);
		
       mainLayout = (LinearLayout) findViewById(R.id.Lay2);
		
		
		
		lst = (ListView) findViewById(R.id.lst);
		 mktUrl=(TextView)findViewById(R.id.myImageViewText);
		 
		 mktUrl.setOnClickListener(new OnClickListener() 
	       {
			
			@Override
			public void onClick(View v) 
			{
				try {
				    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mktsoftwares.com/"));
				    startActivity(myIntent);
				} catch (ActivityNotFoundException e) 
				{
				    Toast.makeText(getApplicationContext(), "No application can handle this request."
				        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
				    e.printStackTrace();
				}
				
			}
		});
		
		 Intent i=getIntent();
		 String Type=i.getStringExtra("type");
		 h = "http://203.158.91.19:90/calender_details.asmx/getEmployeedata?Mem_Type="+Type+"";
		 cd = new ConnectionDetector(getApplicationContext());
			isInternetPresent = cd.isConnectingToInternet();
			
			 if (!isInternetPresent) 
			 {
				 Log.e("innnnnnnn", "innnnnn");
	             // Internet Connection is Present
	             // make HTTP requests
	             showAlertDialog(JSONActivity.this, "Internet Connection",
	                     "You don't have internet connection", true);
	             return;
	         } 
			 else
			 {
			jsonparser = new JsonParser();
			jobj = new JSONObject();
			LoadJS js = new LoadJS();
			js.execute("");
			 }
		
		
		
		
		
		
		
		 
	}
	
	
	private class LoadJS extends AsyncTask<String, String, String> 
	{
		String resultedData = null;
		@Override
		protected String doInBackground(String... params) {
			try {
				
				resultedData = jsonparser.getJSON(h);
				Log.e("resultttttt", resultedData);
			} catch (Exception ex) {
				resultedData = "There's an error, that's all I know right now.. :(";
			}
			return resultedData;
		}
		@Override
		protected void onPreExecute() {
			 dialog = ProgressDialog.show(JSONActivity.this, "", 
	                    "Loading. Please wait...", true);
		}
		@Override
		protected void onPostExecute(String r)
		{
			dialog.dismiss();
			try {
				ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				JSONArray jarray = new JSONArray(r);
				for (int i = 0; i < jarray.length(); i++) 
				{
					HashMap<String, String> datanum = new HashMap<String, String>();
					jobj = jarray.getJSONObject(i);
					
					StoreData SD = new StoreData(jobj.getString("Column1"),jobj.getString("Company Name"),"",jobj.getString("uid"));
					arraylist.add(SD);
					
				}
				Log.e("sdfsfgf", "dgd");
				
				
				
				adapter=new ListViewAdapter(getApplicationContext(), arraylist);
				//adapter = new ListViewAdapter(this, arraylist);
				lst.setAdapter(adapter);
				
			
				lst.setOnItemClickListener(new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view, int position,
		                    long id) {
		            	
		            	TextView empid = (TextView) view.findViewById(R.id.txtid);
		            	
		            	
		            	Intent intent = new Intent(getApplicationContext(), SingleItemView.class);
						
		            	
						intent.putExtra("id",empid.getText());
						
						startActivity(intent);
		            }
		        });
				
				
				
				editsearch = (EditText) findViewById(R.id.search);

				// Capture Text in EditText
				editsearch.addTextChangedListener(new TextWatcher() {

					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
						adapter.filter(text);
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {
						// TODO Auto-generated method stub
					}
				});
				
				
			} 
			catch (Exception ex)
			{
				Toast.makeText(JSONActivity.this, "error", Toast.LENGTH_LONG)
						.show();
				
			}
		}
	}
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);
		
		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}

*/