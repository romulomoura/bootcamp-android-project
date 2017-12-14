package crats.mvcbaseproject.model;

import java.util.ArrayList;

public class Movie {
    private int id;
    private String title, release_date, overview, poster_path;
    private ArrayList<String> genres, companies;

    public Movie(int id, String title, String release_date,
                 String overview, String poster_path, ArrayList<String> genres,
                 ArrayList<String> companies) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.poster_path = poster_path;
        this.genres = genres;
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

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }
}
