package crats.mvcbaseproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import crats.mvcbaseproject.R;
import crats.mvcbaseproject.controller.IMovieController;
import crats.mvcbaseproject.controller.MovieController;
import crats.mvcbaseproject.model.Movie;
import crats.mvcbaseproject.model.MovieAdapter;

public class MovieListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IMovieController{
    ListView movieListView;
    ArrayList<Movie> moviesList = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieListView = (ListView) findViewById(R.id.movieoptionsListView);

        MovieController.shared().setupMovieController(this, getBaseContext());
        MovieController.shared().fetch("https://api.themoviedb.org/3/movie/top_rated?api_key=fc5fcd256800192941231337ea025422");
    }
    private void setupListView() {
        MovieAdapter adapter = new MovieAdapter(this,moviesList);
        movieListView.setOnItemClickListener(this);
        movieListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = MovieController.shared().getMoviesList ().get(position);
        Toast.makeText(this, movie.getTitle(), Toast.LENGTH_SHORT).show();
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
}
