package info.shivam.slidingmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Changepswd extends Fragment {


	private EditText edtEmail;
	private Button btnResetPassword;
	private Button btnBack;
	private FirebaseAuth mAuth;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_payroll, container, false);

		edtEmail = (EditText) rootView.findViewById(R.id.edt_reset_email);
		btnResetPassword = (Button) rootView.findViewById(R.id.btn_reset_password);
		btnBack = (Button) rootView.findViewById(R.id.btn_back);

		mAuth = FirebaseAuth.getInstance();

		btnResetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String email = edtEmail.getText().toString().trim();

				if (TextUtils.isEmpty(email)) {
					Toast.makeText(getActivity(), "Enter your email!", Toast.LENGTH_SHORT).show();
					return;
				}

				mAuth.sendPasswordResetEmail(email)
						.addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if (task.isSuccessful()) {
									Toast.makeText(getActivity(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
								}
							}
						});
			}
		});

		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getActivity(), MainActivity.class));

			}
		});
	return rootView;
	}
}

