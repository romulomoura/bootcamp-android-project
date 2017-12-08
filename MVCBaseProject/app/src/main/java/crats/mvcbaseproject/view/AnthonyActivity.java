package crats.mvcbaseproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import crats.mvcbaseproject.R;

public class AnthonyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anthony);
        Button btn = (Button) findViewById(R.id.btn);

//        btn.bind("<Button>")
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void click() {}

    public void ASU(View view) {

    }
}
