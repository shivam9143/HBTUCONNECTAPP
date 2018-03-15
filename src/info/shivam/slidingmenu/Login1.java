package info.shivam.slidingmenu;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;

//import android.widget.ProgressBar;


public class Login1 extends Activity
{
	Button btn;
	TextView btnRegister;

	String username="";
	String Password="";
	String uid1="";
	EditText empid,institute;
	EditText pwd;
	TextView txtRegister;
	TextView mktUrl;
	Spinner s;
	private FirebaseAuth auth;

	private ProgressBar progressBar;
	//ProgressDialog dialog;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	 public static final String MyPREFERENCES = "MyPrefs" ;
	   public static final String Name = "nameKey";
	   public static final String Phone = "phoneKey";
	   public static final String Email = "emailKey";
	SessionManager session,session2,session3;
	SessionManager session1;
	url url=new url();
	TextView ins;
	Timer t = new Timer();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#275d7b"));
		//getActionBar().setBackgroundDrawable(colorDrawable);
        btn = findViewById(R.id.btnLogin);
		btn.setBackgroundColor(Color.RED);
        empid= findViewById(R.id.edit_USername);
		btnRegister = (TextView) findViewById(R.id.btnSignup);
        pwd= findViewById(R.id.edit_Pwd);
        txtRegister= findViewById(R.id.link_to_register);
       mktUrl= findViewById(R.id.myImageViewText);
       session2=new SessionManager();
       session3=new SessionManager();
		progressBar=(ProgressBar)findViewById(R.id.progressBarlogin);
       session=new SessionManager();
       session1=new SessionManager();


		auth = FirebaseAuth.getInstance();

       mktUrl.setOnClickListener(new OnClickListener() 
       {
		
		@Override
		public void onClick(View v) 
		{
			try {
			    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://hbtu.ac.in/"));
			    startActivity(myIntent);
			} catch (ActivityNotFoundException e) 
			{
			    Toast.makeText(getApplicationContext(), "No application can handle this request."
			        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
			    e.printStackTrace();
			}
		}
	});

        btnRegister.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Login1.this, UserRegistration.class);
		        startActivity(i);
			}
		});

		txtRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Login1.this, ForgetPassword.class));
				finish();
			}
		});

        btn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				String uid=empid.getText().toString();
				String p1=pwd.getText().toString();
				Log.e("Passssss", p1);

				cd = new ConnectionDetector(getApplicationContext());
				isInternetPresent = cd.isConnectingToInternet();
				
				 if (!isInternetPresent) 
				 {
					 Log.e("innnnnnnn", "innnnnn");
		             // Internet Connection is Present
		             // make HTTP requests
		             showAlertDialog(Login1.this, "Internet Connection",
		                     "You don't have internet connection", true);
		             return;
		         } 
				 else
				 {
				
					if(uid.matches("") || p1.matches(""))
			        {
						showAlertDialog(Login1.this, "Login Check",
					            "Please Enter Username and Password..", true);
			        }
					else
					{
						progressBar.setVisibility(View.VISIBLE);
						auth.signInWithEmailAndPassword(empid.getText().toString(), pwd.getText().toString())
								.addOnCompleteListener(Login1.this, new OnCompleteListener<AuthResult>() {
									@Override
									public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
										// If sign in fails, display a message to the user. If sign in succeeds
										// the auth state listener will be notified and logic to handle the
										// signed in user can be handled in the listener.
										//progressBar.setVisibility(View.VISIBLE);
										if (!task.isSuccessful()) {
											// there was an error
											progressBar.setVisibility(View.GONE);
											if (pwd.getText().length() < 6) {
												pwd.setError("Password too short, enter minimum 6 characters!");
											} else {
												Toast.makeText(Login1.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
											}
										}
										else {
											progressBar.setVisibility(View.GONE);
											//Toast.makeText(Login1.this, "Email-Status=:"+auth.getCurrentUser().isEmailVerified(), Toast.LENGTH_SHORT).show();
											if(auth.getCurrentUser().isEmailVerified())
											{
												Toast.makeText(Login1.this, "Welcome, "+empid.getText().toString(), Toast.LENGTH_SHORT).show();
												session.setPreferences(Login1.this,"Email",empid.getText().toString());
												Intent intent = new Intent(Login1.this, MainActivity.class);
												startActivity(intent);
												finish();
											}
											else
											{
												Intent intent = new Intent(Login1.this, Verification.class);
												startActivity(intent);
												finish();
											}

										}
									}
								});

					}
				 }
			}
		});
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
	
	 @Override
	    public void onBackPressed() 
	 {
	        super.onBackPressed();
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_HOME);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
	        startActivity(intent);
	        finish();
	        System.exit(0);
	    }
}
