package crats.mvcbaseproject.model;

import java.util.ArrayList;

/**
 * Created by antho on 12/11/2017.
 */

public class Movie {
    private int id;
    private String title, release_date, website, overview, poster_path;
    private ArrayList<String> genres, casts, companies;

    public Movie(int id, String title, String release_date, String website,
                 String overview, String poster_path, ArrayList<String> genres,
                 ArrayList<String> casts, ArrayList<String> companies) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.website = website;
        this.overview = overview;
        this.poster_path = poster_path;
        this.genres = genres;
        this.casts = casts;
        this.companies = companies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
