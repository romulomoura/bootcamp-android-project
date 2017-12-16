package crats.mvcbaseproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    String qrcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.givereview);


        productname = (EditText)findViewById(R.id.productname);
        review = (EditText) findViewById(R.id.review);
        submit = (Button) findViewById(R.id.submit);


        Bundle sum = getIntent().getExtras();
        qrcode = sum.getString("qrcode");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Getset getset = new Getset();

                getset.setQrcode(qrcode);
                getset.setReview(review.getText().toString());
                getset.setProductName(productname.getText().toString());


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();
                databaseReference.child("primarydatabase").push().setValue(getset);

                Toast.makeText(getApplicationContext(), "Review Submitted",
                        Toast.LENGTH_LONG).show();






            }


        });


    }
}
