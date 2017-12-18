package crats.mvcbaseproject.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import crats.mvcbaseproject.R;
import crats.mvcbaseproject.controller.IMovieApi;
import crats.mvcbaseproject.controller.IMovieController;
import crats.mvcbaseproject.controller.MovieController;
import crats.mvcbaseproject.model.Movie;

public class MovieDetailActivity extends AppCompatActivity implements IMovieController{
    TextView title, description, release_date, genre, company;
    ImageView poster;
    String API_KEY;
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.movietitleTextView);
        description = (TextView) findViewById(R.id.moviedescriptionTextView);
        release_date = (TextView) findViewById(R.id.moviereleasedateTextView);
        genre = (TextView) findViewById(R.id.moviegenreTextView);
        company = (TextView) findViewById(R.id.moviecompanyTextView);
        poster = (ImageView) findViewById(R.id.movieImageView);

        API_KEY = MovieController.shared().getAPI_KEY();
        Intent intent = getIntent();
        id = intent.getIntExtra("MovieID", -1);
        if (id != -1) {
            String url = "https://api.themoviedb.org/3/movie/" + String.valueOf(id) + "?api_key=" + API_KEY;
            MovieController.shared().setupMovieController(this, getBaseContext());
            MovieController.shared().fetchdetail(url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void fetchSuccess() {
        this.moviesList = MovieController.shared().getMoviesList();
        for (Movie movie: this.moviesList) {
            if (movie.getId() == id) {
                getSupportActionBar().setTitle(movie.getTitle());
                title.setText(movie.getTitle());
                release_date.setText("Release Date: " + movie.getRelease_date());
                description.setText("Description:\n" + movie.getOverview());
                genre.setText("Genre:\n" + movie.getGenres().toString());
                company.setText("Production Company:\n" + movie.getCompanies().toString());
                String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
                new DownloadImageTask(poster).execute(imageUrl);
            }
        }
    }

    @Override
    public void fetchFailure(String errorMessage) {
        Log.e("fetchFailure", errorMessage);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap   ;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
