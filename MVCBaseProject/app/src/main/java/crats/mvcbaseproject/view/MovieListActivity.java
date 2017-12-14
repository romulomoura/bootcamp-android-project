package crats.mvcbaseproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
