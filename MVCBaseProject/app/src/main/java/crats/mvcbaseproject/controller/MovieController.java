package crats.mvcbaseproject.controller;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import crats.mvcbaseproject.model.Movie;
import crats.mvcbaseproject.model.Person;

import static com.android.volley.toolbox.Volley.newRequestQueue;

/**
 * Created by Anthony on 12/11/2017.
 */

public class MovieController implements IMovieApi {
    private static MovieController instance = null;
    private ArrayList<Movie> moviesList = new ArrayList<Movie>();

    private IMovieApi iMovieApi = null;
    private IMovieController iMovieController = null;
    private RequestQueue requestQueue = null;
    private final String API_KEY = "fc5fcd256800192941231337ea025422";
    private MovieController() {
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setupMovieController(IMovieController delegate, Context context) {
        this.iMovieApi = this;
        this.iMovieController = delegate;
        requestQueue = newRequestQueue(context);
    }
    public static MovieController shared() {
        if (instance == null) {
            instance = new MovieController();
        }
        return instance;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public void fetch(String url) {
        requestQueue.add(fetchMovieRequest(url));
        requestQueue.start();
    }
    
    public void fetchdetail(String url) {
        requestQueue.add(fetchMovieDetailRequest(url));
        requestQueue.start();
    }

    private JsonObjectRequest fetchMovieDetailRequest(String url) {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // To avoid create variable inside of loops
                                ArrayList<Movie> returnList = new ArrayList<Movie>();
                                JSONArray jsonArray = null;
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response;
                                    Movie object = convertJSONToMovieObject(jsonObject);
                                    ArrayList<Movie> temp = new ArrayList<Movie>();
                                    for (Movie movie : moviesList) {
                                        if (movie.getId() == object.getId()) {
                                            temp.add(object);
                                        } else {
                                            temp.add(movie);
                                        }
                                    }
                                    moviesList = temp;
                                    iMovieApi.fetchSuccess(temp);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    iMovieApi.fetchFailure("JSON read failure");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iMovieApi.fetchFailure("Unknown failure");
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // On error response, implement callback for it too
                                Log.e("API error", "Server ERROR: " + error.getMessage());
                                iMovieApi.fetchFailure(error.getMessage());
                            }
                        });

        return jsonObjectRequest;
    }

    private Movie convertJSONToMovieObject(JSONObject object) throws JSONException{
        String title = object.getString("title");
        int id = object.getInt("id");
        String posterpath = object.getString("poster_path");
        String release_date = object.getString("release_date");
        String overview = object.getString("overview");
        ArrayList<String> companies = new ArrayList<String>();
        ArrayList<String> genres = new ArrayList<String>();
        try {
            JSONArray array = object.getJSONArray("production_companies");
            for (int i = 0; i < array.length(); i++) {
                companies.add(array.getJSONObject(i).getString("name"));
            }
            array = object.getJSONArray("genres");
            for (int i = 0; i < array.length(); i++) {
                genres.add(array.getJSONObject(i).getString("name"));
            }
        } catch (JSONException ex){
            companies = null;
            genres = null;
        }
        return new Movie(id,title,release_date, overview,posterpath,genres,companies);
}

    private JsonObjectRequest fetchMovieRequest(String url) {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET,
                        url,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // To avoid create variable inside of loops
                                ArrayList<Movie> returnList = new ArrayList<Movie>();
                                JSONArray jsonArray = null;
                                JSONObject jsonObject = null;

                                try {
                                    jsonArray = response.getJSONArray("results");

                                    for (int i = 0; i <jsonArray.length(); i++) {
                                        try {
                                            jsonObject = jsonArray.getJSONObject(i);

                                            returnList.add(convertJSONToMovieObject(jsonObject));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            iMovieApi.fetchFailure("JSON read failure");
                                            break;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            iMovieApi.fetchFailure("Unknown failure");
                                            break;
                                        }
                                    }
                                    iMovieApi.fetchSuccess(returnList);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    iMovieApi.fetchFailure("JSON read failure");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iMovieApi.fetchFailure("Unknown failure");
                                }
                            }

                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // On error response, implement callback for it too
                                Log.e("API error", "Server ERROR: " + error.getMessage());
                                iMovieApi.fetchFailure(error.getMessage());
                            }
                        });


        return jsonObjectRequest;
    }

    @Override
    public void fetchSuccess(ArrayList<Movie> list) {
        this.moviesList = list;
        iMovieController.fetchSuccess();
    }

    @Override
    public void fetchFailure(String errorMessage) {
        iMovieController.fetchFailure(errorMessage);
    }
}
