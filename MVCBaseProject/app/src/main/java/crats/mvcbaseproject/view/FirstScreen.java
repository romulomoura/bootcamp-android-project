package crats.mvcbaseproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import crats.mvcbaseproject.R;

/**
 * Created by yashshah on 2017-12-13.
 */

public class FirstScreen extends AppCompatActivity {


    Button givereview,showreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);

        givereview = (Button)findViewById(R.id.givereview);
        showreview = (Button)findViewById(R.id.showreview);


        givereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent givereviewintent = new Intent(FirstScreen.this, YashMainActivity.class);
                startActivity(givereviewintent);
            }
        });


        showreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showreviewintent = new Intent(FirstScreen.this, RetriveReview.class);
                startActivity(showreviewintent);

            }
        });


    }
}
