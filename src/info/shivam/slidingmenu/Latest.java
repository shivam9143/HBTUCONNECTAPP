/*package info.androidhive.slidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Latest extends Activity
{
	TextView txt1;
	TextView txt2;
	TextView txt3;
	TextView txt4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.latest);
		
		txt1=(TextView)findViewById(R.id.textView1);
		txt2=(TextView)findViewById(R.id.textView2);
		txt3=(TextView)findViewById(R.id.textView3);
		txt4=(TextView)findViewById(R.id.textView4);
		
		
   		txt1.setOnClickListener(new OnClickListener() 
		 {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Latest.this, JSONActivity.class);
				i.putExtra("proj_type", "Project Type");
		        startActivity(i);
				
			}
		});
   		
   		
   		txt2.setOnClickListener(new OnClickListener() 
		 {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Latest.this, JSONActivity.class);
				i.putExtra("proj_name", "Project Name");
		        startActivity(i);
				
			}
		});
   		
   		txt3.setOnClickListener(new OnClickListener() 
		 {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Latest.this, JSONActivity.class);
				i.putExtra("assign_by", "Assigned By");
		        startActivity(i);
				
			}
		});
		
   		
   		txt4.setOnClickListener(new OnClickListener() 
		 {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Latest.this, JSONActivity.class);
				i.putExtra("task", "Task");
		        startActivity(i);
				
			}
		});
	}
}
*/