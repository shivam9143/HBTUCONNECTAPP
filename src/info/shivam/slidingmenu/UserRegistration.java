package info.shivam.slidingmenu;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class UserRegistration extends Activity
{
	TextView txtLogin;
	EditText edtusername;
	EditText edtpwd;
	EditText conPwd;
	Button register;
	JsonParser jsonparser;
	JSONObject jobj;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	TextView mktUrl;
	private FirebaseAuth auth;
	private ProgressBar progressBar;

	@Override
		public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Get Firebase auth instance
		auth = FirebaseAuth.getInstance();
        setContentView(R.layout.register);
        txtLogin= findViewById(R.id.link_to_login);
        edtusername= findViewById(R.id.reg_email);
        edtpwd= findViewById(R.id.reg_password);
        conPwd= findViewById(R.id.con_password);
        register= findViewById(R.id.btnRegister);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		register.setBackgroundColor(Color.RED);

		final String Expn =

				"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

						+"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

						+"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

						+"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

						+"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        register.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				
				String uid=edtusername.getText().toString();
				String p1=edtpwd.getText().toString();

				cd = new ConnectionDetector(getApplicationContext());
				isInternetPresent = cd.isConnectingToInternet();
				
				 if (!isInternetPresent) 
				 {
					 
		             showAlertDialog(UserRegistration.this, "Internet Connection",
		                     "You don't have internet connection", true);
		             return;
		         } 
				 else
				 {
				
					if(uid.matches(""))
			        {
						edtusername.setError("Please Enter Email!");
						//showAlertDialog(UserRegistration.this, "User Registration","Please Enter Email Or MobileNo", true);
			        }
			        else if(!uid.matches(Expn))
					{
						//edtusername.setText("");
						edtusername.setError("Invalid E-mail");
						//Toast.makeText(UserRegistration.this,"Invalid E-mail",Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(p1.matches(""))
						{

							edtpwd.setError("Please Enter Password");
						}
						else if(p1.length()<6)
						{
							edtpwd.setText("");
							conPwd.setText("");
							edtpwd.setError("Password should be atleast 6 characters long!");
							conPwd.setError("Password should be atleast 6 characters long!");
							//Toast.makeText(UserRegistration.this,"Password should be atleast 6 characters long!",Toast.LENGTH_SHORT).show();
						}
						else
						{
							if(conPwd.getText().toString().equals(p1))
							{
								Log.e("Username=",uid);
								Log.e("password=",p1);
								progressBar.setVisibility(View.VISIBLE);



								auth.createUserWithEmailAndPassword(uid,p1)
										.addOnCompleteListener(UserRegistration.this, new OnCompleteListener<AuthResult>() {
											@Override
											public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
												if(task.isSuccessful())
												{
													Toast.makeText(UserRegistration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
													progressBar.setVisibility(View.GONE);
												}
												// If sign in fails, display a message to the user. If sign in succeeds
												// the auth state listener will be notified and logic to handle the
												// signed in user can be handled in the listener.
												if (!task.isSuccessful()) {
													Toast.makeText(UserRegistration.this, "Authentication failed." + task.getException(),
															Toast.LENGTH_SHORT).show();
													if(task.getException().toString().contains("The email address is already in use by another account"))
													{
														Toast.makeText(UserRegistration.this,"This E-mail is already registered, PLease Login!",
																Toast.LENGTH_SHORT).show();
														progressBar.setVisibility(View.GONE);
													}
												}
												else {
													startActivity(new Intent(UserRegistration.this, Verification.class));
													finish();
												}
											}
										});
							}
							else
							{
								showAlertDialog(UserRegistration.this, "User Registration",
							            "Password and confirm Password is Not match", true);
							}
						}


					}
				 }
			}
		});
        
        
        
        
        
        
        
        txtLogin.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(UserRegistration.this, Login1.class);
		        startActivity(i);
				
			}
		});


	}

	@Override
	protected void onResume() {
		super.onResume();
		progressBar.setVisibility(View.GONE);
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
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	
	public void showAlertDialog1(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);
		
		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				Intent i = new Intent(UserRegistration.this, Login1.class);
		        startActivity(i);
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}

