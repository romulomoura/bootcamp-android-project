package crats.mvcbaseproject.controller;

import java.util.ArrayList;

import crats.mvcbaseproject.model.Person;

/**
 * Created by Victor on 2017-11-15.
 */

public interface IPersonApi {
    void fetchSuccess(ArrayList<Person> list);
    void fetchFailure(String errorMessage);
}
