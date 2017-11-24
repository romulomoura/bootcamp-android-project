package crats.mvcbaseproject.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import crats.mvcbaseproject.controller.IPersonController;
import crats.mvcbaseproject.controller.PersonController;
import crats.mvcbaseproject.model.Person;

public class PersonNamesListScreen extends AppCompatActivity implements AdapterView.OnItemClickListener,IPersonController {

    ListView customObjectlistView;

    ArrayList<String> PersonNamesList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_names_list);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customObjectlistView = (ListView) findViewById(R.id.customObjectListView);

        PersonController.shared().setupPersonController(this, this.getBaseContext());
        PersonController.shared().fetchList();

    }

    private void setupListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(),android.R.layout.simple_list_item_1,this.PersonNamesList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);return textView;
            }
        };
        customObjectlistView.setOnItemClickListener(this);
        customObjectlistView.setAdapter(adapter);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person person = PersonController.shared().getListOfObject().get(position);
        displayDialog(person);
    }

    public ArrayList<String> getPersonsList(){

        ArrayList<String> personList_name = new ArrayList<String>();
        ArrayList<Person> list = new ArrayList<Person>();
        String name;

        list = PersonController.shared().getListOfObject();

        for (int i = 0; i <list.size() ; i++) {
         name = list.get(i).getFullName();

            personList_name.add(name);
        }

            return personList_name;
    }


    private void displayDialog(Person obj) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Person " + obj.getFirstName());
        alertDialog.setMessage("You selected " + obj.getFullName() + "\nHis/Her email is " + obj.getEmail());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void fetchPersonSuccess() {

        PersonNamesList = this.getPersonsList();
        this.setupListView();
    }

    @Override
    public void fetchPersonFailure(String errorMessage) {

    }

}
