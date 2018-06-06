package crats.mvcbaseproject.controller;

/**
 * Created by antho on 12/11/2017.
 */

public interface IMovieController {
    void fetchSuccess();
    void fetchFailure(String errorMessage);
}
