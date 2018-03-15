package info.shivam.slidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verification extends Activity {

    private FirebaseAuth auth;
    private Button verify,retest,logout;
    private TextView v;
    FirebaseUser user;
    SessionManager s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        verify=(Button)findViewById(R.id.btnVerify);
        retest=(Button)findViewById(R.id.retest);
        logout=(Button)findViewById(R.id.exit);
        s=new SessionManager();
        String status=s.getPreferences(Verification.this,"Email");
        verify.setBackgroundColor(Color.rgb(209, 69, 78));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser().reload().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(Verification.this,Login1.class));
                                finish();
                            }
                        });

            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification()
                        .addOnCompleteListener(Verification.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                // Re-enable button
                                findViewById(R.id.btnVerify).setEnabled(true);

                                if (task.isSuccessful()) {
                                    Toast.makeText(Verification.this,
                                            "Verification email sent to " + user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //Log.e(TAG, "sendEmailVerification", task.getException());
                                    Toast.makeText(Verification.this,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });

        retest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().getCurrentUser().reload().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
                                if(u.isEmailVerified())
                                {
                                    s.setPreferences(Verification.this,"Email",u.getEmail());
                                    Toast.makeText(Verification.this,"E-mail verified successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Verification.this,MainActivity.class));
                                    finish();
                                }

                            }
                        });

            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Verification.this,Login1.class);
        startActivity(i);
        finish();
    }
}
