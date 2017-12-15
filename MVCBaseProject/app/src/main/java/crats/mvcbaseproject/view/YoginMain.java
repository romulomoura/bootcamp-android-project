package crats.mvcbaseproject.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.view.Menu;
import android.view.MenuItem;
import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseUser mUser;
    private Button loginButton;
    private Button createActButton;
    private EditText emailField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoginmain);

        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.login);
       // createActButton = (Button) findViewById(R.id.loginCreateAccount);
        emailField = (EditText) findViewById(R.id.emailEd);
        passwordField = (EditText) findViewById(R.id.passwordEd);

/*        createActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(YoginMain.this, CreateAccountActivity.class));
                finish();
            }
        });
*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();

                if (mUser != null) {
                    Toast.makeText(YoginMain.this, "Signed In", Toast.LENGTH_LONG).show();
                //    startActivity(new Intent(YoginMain.this, PostListActivity.class));
                    finish();
                }else {
                    Toast.makeText(YoginMain.this, "Not Signed In", Toast.LENGTH_LONG).show();
                }




            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailField.getText().toString())
                        && !TextUtils.isEmpty(passwordField.getText().toString())) {

                    String email = emailField.getText().toString();
                    String pwd = passwordField.getText().toString();

                    login(email, pwd);




                }else {

                }
            }
        });



    }

    private void login(String email, String pwd) {

        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Yay!! We're in!
                            Toast.makeText(YoginMain.this, "Signed in", Toast.LENGTH_LONG)
                                    .show();

                         //   startActivity(new Intent(YoginMain.this, PostListActivity.class));
                            finish();
                        }else {
                            // Not it!
                        }

                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.action_signout) {

            mAuth.signOut();

        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



   /* private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "YoginMain";
    private EditText email;
    private EditText password;
    private Button login;
    private Button signout;
    private Button createAccount;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoginmain);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");

        email = (EditText) findViewById(R.id.emailEd);
        password = (EditText) findViewById(R.id.passwordEd);
        login = (Button) findViewById(R.id.login);
        signout = (Button) findViewById(R.id.signOutId);
        createAccount = (Button) findViewById(R.id.createAct);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "user signed in");
                    Log.d(TAG,"UserName: " +  user.getEmail());
                } else {
                    Log.d(TAG, "user not signed in");
                }

            }
        };

login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final String emailString = email.getText().toString();
        String pwd = password.getText().toString();
        Log.i(TAG, "onClick: ");


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

                                Customer customer = new Customer("Yogin","Bhatt",emailString,78);
                                databaseReference.setValue(customer);

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

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String pwd = password.getText().toString();
                if(!emailString.equals("") && !pwd.equals(""))
                {
                mAuth.createUserWithEmailAndPassword(emailString,pwd).addOnCompleteListener(YoginMain.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (! task.isSuccessful())
                        {
                            Toast.makeText(YoginMain.this,"failed", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(YoginMain.this,"Account created", Toast.LENGTH_LONG).show();

                        }

                    }
                });
                }


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
    */




}
