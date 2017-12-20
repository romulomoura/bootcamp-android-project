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

import crats.mvcbaseproject.R;

public class ProjectLists extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView projectlistView;
    String [] projectList = {"ProDigi", "Anthony", "yogin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_lists);

       this.setupListView();


    }


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

        switch(position){
            case 0:
                Intent intent = new Intent(this, PersonNamesListScreen.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent = new Intent(this, MovieListActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(this,SplashScreen.class);
                startActivity(intent1);
                break;
            default:
                setContentView(R.layout.project_lists);
        }
    }
}
