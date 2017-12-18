package crats.mvcbaseproject.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import crats.mvcbaseproject.R;
import crats.mvcbaseproject.controller.IMovieController;
import crats.mvcbaseproject.controller.MovieController;
import crats.mvcbaseproject.model.Movie;
import crats.mvcbaseproject.model.MovieAdapter;

public class MovieListActivity extends AppCompatActivity implements IMovieController{
    ListView movieListView;
    Spinner optionsSpinner;
    String options[] = {"Top Rated Movies", "Now Playing Movies", "Upcoming Movies"};
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    String API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Movie List Activity");
        
        movieListView = (ListView) findViewById(R.id.movieoptionsListView);
        optionsSpinner = (Spinner) findViewById(R.id.movieoptionsSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        optionsSpinner.setAdapter(adapter);
        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MovieController.shared().fetch("https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY);
                        break;
                    case 1:
                        MovieController.shared().fetch("https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY);
                        break;
                    case 2:
                        MovieController.shared().fetch("https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        API_KEY = MovieController.shared().getAPI_KEY();
        MovieController.shared().setupMovieController(this, getBaseContext());
        MovieController.shared().fetch("https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY);
    }
    private void setupListView() {
        MovieAdapter adapter = new MovieAdapter(this,moviesList);
        movieListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Movie> getMoviesList() {
        return MovieController.shared().getMoviesList();
    }

    @Override
    public void fetchSuccess() {
        moviesList = this.getMoviesList();
        this.setupListView();
    }

    @Override
    public void fetchFailure(String errorMessage) {
        Log.e("fetchFailure", errorMessage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                showAboutDialog();
                return true;
            case R.id.help:
                showHelpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String github = "<a href='https://github.com/anthonys44'>https://github.com/anthonys44</a>";
        String linkedin = "<a href='https://www.linkedin.com/in/anthony-s44/'>https://www.linkedin.com/in/anthony-s44/</a>";
        String message = "Name: Anthony<br/>Email: anthony.salim.123@gmail.com<br/> Github Profile: " + github + "<br/> LinkedIn Profile: " + linkedin;
        TextView textView = new TextView(this);
        int pixel = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
        textView.setPadding(pixel,pixel,pixel,pixel);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(message));
        builder.setTitle("About")
                .setView(textView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help")
                .setMessage("This application is used to view movie information.\n\n" +
                        "You can choose options to view movies that are top rated, now playing or upcoming movies.\n\n" +
                        "Clicking on one of the movie will show you more details about the movie")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }
}
