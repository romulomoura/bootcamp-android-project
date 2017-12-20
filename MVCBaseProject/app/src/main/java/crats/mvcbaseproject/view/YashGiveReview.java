package crats.mvcbaseproject.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import crats.mvcbaseproject.R;

/**
 * Created by yashshah on 2017-12-15.
 */

public class YashGiveReview extends AppCompatActivity {
    EditText review,productname;
    Button submit;
    RatingBar ratingBar;

    ProgressBar progressBar;
    String qrcode = "";


    private int mProgressstatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.givereview);


        productname = (EditText)findViewById(R.id.productname);
        review = (EditText) findViewById(R.id.review);
        submit = (Button) findViewById(R.id.submit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        ratingBar = (RatingBar)findViewById(R.id.ratingbar);


        Bundle sum = getIntent().getExtras();
        qrcode = sum.getString("qrcode");



            submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int a  = ratingBar.getProgress();
                System.out.println(a);



                //ratingBar.setProgress(3);


                String getEditText = productname.getText().toString();
                String getEdiText1 = review.getText().toString();


                if (TextUtils.isEmpty(getEditText))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Product Name",
                            Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(getEdiText1))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Product Review",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    submit.setVisibility(View.INVISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (mProgressstatus < 100) {
                                mProgressstatus++;
                                android.os.SystemClock.sleep(10);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(mProgressstatus);

                                    }
                                });
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getApplicationContext(), "Review Submitted",
                                            Toast.LENGTH_LONG).show();
                                    Intent intentyash = new Intent(YashGiveReview.this, FirstScreen.class);
                                    startActivity(intentyash);

                                }
                            });

                        }
                    }).start();

                    String b = String.valueOf(a);

                    Getset getset = new Getset();

                    getset.setQrcode(qrcode);
                    getset.setReview(review.getText().toString());
                    getset.setProductName(productname.getText().toString());
                    getset.setProgress(b);


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    databaseReference.child("primarydatabase").push().setValue(getset);


                }


            }







        });


    }


    }
