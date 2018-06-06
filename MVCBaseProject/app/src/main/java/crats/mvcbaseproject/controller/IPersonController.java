package crats.mvcbaseproject.controller;

import java.util.ArrayList;

/**
 * Created by Victor on 2017-11-15.
 */

public interface IPersonController {
    void fetchPersonSuccess();
    void fetchPersonFailure(String errorMessage);
}
