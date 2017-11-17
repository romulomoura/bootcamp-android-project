package crats.mvcbaseproject;

import java.util.ArrayList;

/**
 * Created by Victor on 2017-11-15.
 */

public interface IPersonApi {
    void fetchSuccess(ArrayList<Person> list);
    void fetchFailure(String errorMessage);
}
