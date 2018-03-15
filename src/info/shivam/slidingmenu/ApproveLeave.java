package info.shivam.slidingmenu;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ApproveLeave extends Fragment {

	EditText name;
	Button chng;
	ProgressBar pb;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rV = inflater.inflate(R.layout.approve, container, false);
		pb=(ProgressBar)rV.findViewById(R.id.progressBarchng);
		name=(EditText)rV.findViewById(R.id.name);
		chng=(Button)rV.findViewById(R.id.changename);
		chng.setBackgroundColor(Color.BLUE);
		final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			// Name, email address, and profile photo Url
			String name = user.getDisplayName();
			String email = user.getEmail();
			Uri photoUrl = user.getPhotoUrl();

			// Check if user's email is verified
			boolean emailVerified = user.isEmailVerified();

			// The user's ID, unique to the Firebase project. Do NOT use this value to
			// authenticate with your backend server, if you have one. Use
			// FirebaseUser.getToken() instead.
			String uid = user.getUid();
		}

		chng.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					pb.setVisibility(View.VISIBLE);
				UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
						.setDisplayName(name.getText().toString())
						.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
						.build();

				user.updateProfile(profileUpdates)
						.addOnCompleteListener(new OnCompleteListener<Void>() {


							@Override
							public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
								if (task.isSuccessful()) {
									pb.setVisibility(View.GONE);
									Toast.makeText(getActivity(),"Name Changed to:"+name.getText().toString(),Toast.LENGTH_SHORT).show();
									//Log.d("hello", "User profile updated.");
								}
							}
						});

			}
		});


		return rV;
	}
}
