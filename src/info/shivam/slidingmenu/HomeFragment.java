package info.shivam.slidingmenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import info.shivam.slidingmenu.model.Showlist;

@SuppressWarnings("deprecation")
public class HomeFragment extends Fragment 
{

    Context context;

    
    TextView text;
	public HomeFragment(){}
	

	
	Context con;
	String resultedData = null;
	String h;
	String h1;
	int count2;
	String status;
	String eid;
	JSONObject jobj;
	JsonParser jsonparser;
	SessionManager manager;
	SessionManager manager1;
	ProgressDialog dialog;


	

	 GridView lst;
	 Showlist shlist;
	 ProgressDialog progress;
	 public static String rslt="";
	 Button dash,payroll,leave,calender;
	 private TypedArray navMenuIcons;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{

		 getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		dash=(Button)rootView.findViewById(R.id.empdash);
		payroll=rootView.findViewById(R.id.payroll);
		calender=rootView.findViewById(R.id.calender);
		leave=rootView.findViewById(R.id.Leave);
		dash.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),Dashboard.class);
				startActivity(i);
			}
		});

		leave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),NoteMainActivity.class);
				startActivity(i);
			}
		});

		payroll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"This Module is under Development!!",Toast.LENGTH_SHORT).show();
			}
		});

		calender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"This Module is under Development!!",Toast.LENGTH_SHORT).show();
			}
		});

			  return rootView;

		}


}
