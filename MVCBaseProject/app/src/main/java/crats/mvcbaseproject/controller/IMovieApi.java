package crats.mvcbaseproject.controller;

import java.util.ArrayList;

import crats.mvcbaseproject.model.Movie;

/**
 * Created by antho on 12/11/2017.
 */

public interface IMovieApi {
    void fetchSuccess(ArrayList<Movie> list);
    void fetchFailure(String errorMessage);
}
