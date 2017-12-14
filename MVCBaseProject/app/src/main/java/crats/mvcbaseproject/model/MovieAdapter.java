package crats.mvcbaseproject.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import crats.mvcbaseproject.R;
import crats.mvcbaseproject.view.MovieDetailActivity;

/**
 * Created by antho on 12/11/2017.
 */

public class MovieAdapter extends BaseAdapter {
    private final Activity context;
    private final ArrayList<Movie> movies;
    private static LayoutInflater inflater=null;

    public MovieAdapter(Activity context, ArrayList<Movie> movies) {
//        accounts.clear();
        this.context = context;
        this.movies = movies;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        Movie movie;
        ImageView imageView;
        TextView title, release_date, overview;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listview_movie_detail,null);
        holder.title = (TextView) rowView.findViewById(R.id.movietitleTextView);
        holder.release_date = (TextView) rowView.findViewById(R.id.moviereleasedateTextView);
        holder.overview = (TextView) rowView.findViewById(R.id.movieoverviewTextView);
        holder.imageView = (ImageView) rowView.findViewById(R.id.movieposterImageView);

        holder.movie = movies.get(position);
        holder.title.setText(holder.movie.getTitle());
        holder.release_date.setText(holder.movie.getRelease_date());
        holder.overview.setText(holder.movie.getOverview());
        String imageUrl = "https://image.tmdb.org/t/p/w500" + holder.movie.getPoster_path();

        new DownloadImageTask((ImageView) rowView.findViewById(R.id.movieposterImageView)).execute(imageUrl);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MovieDetailActivity.class);
                intent.putExtra("MovieID", holder.movie.getId());
                context.startActivityForResult(intent, 2);
            }
        });
        return rowView;
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
