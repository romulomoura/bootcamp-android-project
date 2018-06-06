package crats.mvcbaseproject.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import crats.mvcbaseproject.R;

public class ProjectLists extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView projectlistView;
    ArrayList<String> projectList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_lists);

        projectList.add("ProDigi");
        projectList.add("Anthony");

        // TODO: Step 1. Add your name on projectList array
        // Example:
        // projectList.add(<<Your Name>>);

        this.setupListView();
    }

    // Do not change this method
    private void setupListView(){
        projectlistView = (ListView) findViewById(R.id.projectListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(),android.R.layout.simple_list_item_1,this.projectList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);return textView;
            }
        };
        projectlistView.setOnItemClickListener(this);
        projectlistView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch(position){
            case 0:
                intent = new Intent(this, PersonNamesListScreen.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, MovieListActivity.class);
                startActivity(intent);
                break;
            case 2:
                // TODO: Step 2. Add your activity screen here.
                // Example:

                // intent = new Intent(this, <<Your Activity Name>>.class);
                // startActivity(intent);
                break;
        }
    }
}
