package com.avevanjagmail.moviesapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by irabokalo on 17.08.2016.
 */
public class LogoutResponse {

    private Succeeded succeeded;

  private Data data;

    public Succeeded getSucceeded() {
        return succeeded;
    }

    public Data getData() {
        return data;
    }
}
