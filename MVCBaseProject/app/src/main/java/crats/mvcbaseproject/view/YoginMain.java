package crats.mvcbaseproject.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import crats.mvcbaseproject.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bholu on 2017-12-07.
 */

public class YoginMain extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "YoginMain";
    private EditText email;
    private EditText password;
    private Button login;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoginmain);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.emailEd);
        password = (EditText) findViewById(R.id.passwordEd);
        login = (Button) findViewById(R.id.login);
        signout = (Button) findViewById(R.id.signOutId);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "user signed in");
                } else {
                    Log.d(TAG, "user not signed in");
                }

            }
        };

login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String emailString = email.getText().toString();
        String pwd = password.getText().toString();

        if(!emailString.equals("") && !pwd.equals(""))
        {
            mAuth.signInWithEmailAndPassword(emailString, pwd)
                    .addOnCompleteListener(YoginMain.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(! task.isSuccessful())
                            {
                                Toast.makeText(YoginMain.this, "Failed to signed in ", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(YoginMain.this, "Signed in ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }
});

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(YoginMain.this, "Signed out successfully ", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
      //  FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
