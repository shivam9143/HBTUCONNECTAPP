package info.shivam.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

public class OthersLatest extends Fragment {
		
	public OthersLatest(){}
	Context con;
	String resultedData = null;
	String h;
	int count1;
	String status;
	JSONObject jobj;
	ListViewAdapterOther adapter;
	ListView lst;
	JsonParser jsonparser;
	SessionManager manager;
	ProgressDialog dialog;
	ArrayList<StoreDataOther> arraylist = new ArrayList<StoreDataOther>();
	
	ShowList2 shlist;
	 GridView lst1;

	 
	 private TypedArray navMenuIcons;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View rootView = inflater.inflate(R.layout.layout_other, container, false);
        lst1= rootView.findViewById(R.id.listView);

        /*FindPeopleFragment f = new FindPeopleFragment();
        f.setIndicator(FindPeopleFragment.this, getResources().);*/
    			
		ArrayList<String> arlst=new ArrayList<String>();
        arlst.add("Latest");
        arlst.add("Completed");
        arlst.add("Inprocess");
        arlst.add("Overdue");
        navMenuIcons = getResources().obtainTypedArray(R.array.icon);
        shlist=new ShowList2(getActivity(),arlst,navMenuIcons);
        Log.e("viewwwwwwwwwwwww", "Viewwwwwwwwwwwwwww");
        lst1.setAdapter(shlist);
        
        
        /*lst1.requestFocusFromTouch();  
         lst1.setSelection(1);
        lst1.setBackgroundColor(Color.BLUE);*/

        
      /*  TextView forgot_pswrd = (TextView) findViewById(R.id.ForgotPasswordText);
        forgot_pswrd.setOnTouchListener(this);     
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llp.setMargins(50, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
        forgot_pswrd.setLayoutParams(llp);*/
        
        lst = rootView.findViewById(R.id.lst);
        manager=new SessionManager();
	    jsonparser = new JsonParser();
	    
	    dialog = new ProgressDialog(getActivity());
	
 
	     status=manager.getPreferences(getActivity(),"status");
	        h = "http://203.158.91.19:90/calender_details.asmx/OthersLatestCheck?empid="+status;
		 
			jsonparser = new JsonParser();
			jobj = new JSONObject();
			LoadJS js = new LoadJS();
			js.execute("");
			 
		
		
        return rootView;
    }


private class LoadJS extends AsyncTask<String, String, String> 
{
	
	
	protected String doInBackground(String... params) 
	{
		
		try {
			
			
			//String status=manager.getPreferences(con,"status");
			
			resultedData = jsonparser.getJSON(h);
			Log.e("result",resultedData);
		} 
		catch (Exception ex)
		{
			resultedData = "There's an error, that's all I know right now.. :(";
		}
		return resultedData;
	}
	
	
	
	
	@Override
	protected void onPreExecute() 
	{
		dialog.setMessage("Loading...");
        dialog.show();
		 /*dialog = ProgressDialog.show(getActivity(), ".", "Loading...", true);*/
		/*dialog = new ProgressDialog(getActivity());
//        dialog.setMessage("Loading...");
//         dialog.show();
		dialog = ProgressDialog.show(con, "", 
                "Loading. Please wait...", true);*/
	}
	
	
	/*protected void onDestroy(){
		if ( dialog!=null && dialog.isShowing() ){
	        dialog.dismiss();
	    }
	}*/
	
	
	@Override
	protected void onPostExecute(String r)
	{
		Context con1;
				try {
			
					if ( dialog!=null && dialog.isShowing() ){
				        dialog.dismiss();
				        dialog=null;
				    }

			jobj = new JSONObject();
			
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			JSONArray jarray = new JSONArray(r);
			for (int i = 0; i < jarray.length(); i++) 
			{
				HashMap<String, String> datanum = new HashMap<String, String>();
				jobj = jarray.getJSONObject(i);
				
				StoreDataOther SD = new StoreDataOther(jobj.getString("Task"),jobj.getString("projectName"),jobj.getString("ActivityType"),jobj.getString("ActivityDate"),jobj.getString("SDate"),jobj.getString("EDate"),jobj.getString("WorkingHours"),jobj.getString("Comp_Status"),jobj.getString("Responsibleper"));
				arraylist.add(SD);
				
			}
			count1=arraylist.size();
			adapter=new ListViewAdapterOther(getActivity(),arraylist);
			Log.e("sdfsfgf",Integer.toString(adapter.getCount()));
			
			//adapter = new ListViewAdapter(this, arraylist);
			lst.setAdapter(adapter);
		}
		catch (Exception ex)
		{
			/*Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG)
					.show();*/
			
		}
	}
}




}

			
		
			