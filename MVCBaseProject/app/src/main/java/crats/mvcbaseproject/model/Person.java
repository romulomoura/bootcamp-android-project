package crats.mvcbaseproject.model;

/**
 * Created by Victor on 2017-11-08.
 */

public class Person {

    private String firstName = "";
    private String lastName = "";
    private String email = "";

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail() {
        return this.email;
    }


}
