package crats.mvcbaseproject.view;

import java.io.Serializable;

/**
 * Created by yashshah on 2017-12-05.
 */

public class Getset implements Serializable {

    public String name;
    public String age;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAge(String age)
    {
        this.age = age;
    }


}
