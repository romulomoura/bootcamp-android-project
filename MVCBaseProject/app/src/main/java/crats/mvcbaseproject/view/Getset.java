package crats.mvcbaseproject.view;

import java.io.Serializable;

/**
 * Created by yashshah on 2017-12-05.
 */

public class Getset implements Serializable {


    public String email;
    public String review;
    public String productName;
    public String qrcode;
    public String progress;

    public void setReview(String review)
    {
        this.review = review;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public void setQrcode(String qrcode)
    {
        this.qrcode = qrcode;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setProgress(String progress){this.progress = progress;}


}
