package crats.mvcbaseproject.controller;

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

import crats.mvcbaseproject.model.Person;

import static com.android.volley.toolbox.Volley.newRequestQueue;

/**
 * Created by Victor on 2017-11-08.
 */

public class PersonController implements IPersonApi {
    private static PersonController instance = null;
    private ArrayList<Person> listOfObjects= new ArrayList<Person>();

    private IPersonApi iPersonApi = null;
    private IPersonController iPersonController = null;

    private final String url = "https://randomuser.me/api/?results=25";
    private RequestQueue requestQueue = null;

    private PersonController() {
        // Nothing
    }

    public void setupPersonController(IPersonController delegateHandler, Context context){
        this.iPersonApi = this;
        this.iPersonController = delegateHandler;
        requestQueue = newRequestQueue(context);
    }

    public static PersonController shared() {
        if (instance == null){
            instance = new PersonController();
        }

        return instance;
    }

    public ArrayList<Person> getListOfObject(){
        return listOfObjects;
    }

    public void fetchList() {
        requestQueue.add(fetchPersonRequest());
        requestQueue.start();
    }

    // Call back methods to trigger the UI
    @Override
    public void fetchSuccess(ArrayList<Person> list) {
        this.listOfObjects = list;
        iPersonController.fetchPersonSuccess();
    }

    @Override
    public void fetchFailure(String errorMessage) {
        iPersonController.fetchPersonFailure(errorMessage);
    }

    // Private methods
    private Person convertDataToCustomObject(JSONObject jsonObject) throws JSONException {
        JSONObject nameJsonObject = jsonObject.getJSONObject("name");
        String firstName = nameJsonObject.getString("first");
        String lastName = nameJsonObject.getString("last");

        String email = jsonObject.getString("email");

        return new Person(firstName, lastName, email);
    }

    private JsonObjectRequest fetchPersonRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // To avoid create variable inside of loops
                        ArrayList<Person> returnList = new ArrayList<Person>();
                        JSONArray jsonArray = null;
                        JSONObject jsonObject = null;

                        try {
                            jsonArray = response.getJSONArray("results");

                            for (int i = 0; i <jsonArray.length(); i++) {
                                try {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    returnList.add(convertDataToCustomObject(jsonObject));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    iPersonApi.fetchFailure("JSON read failure");
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iPersonApi.fetchFailure("Unknown failure");
                                    break;
                                }
                            }

                            iPersonApi.fetchSuccess(returnList);
                        } catch (JSONException e) {
                            e.printStackTrace();

                            iPersonApi.fetchFailure("JSON read failure");
                        } catch (Exception e) {
                            e.printStackTrace();

                            iPersonApi.fetchFailure("Unknown failure");
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // On error response, implement callback for it too
                        Log.e("API error", "Server ERROR: " + error.getMessage());
                        iPersonApi.fetchFailure(error.getMessage());
                    }
                });


        return jsonObjectRequest;
    }

}
