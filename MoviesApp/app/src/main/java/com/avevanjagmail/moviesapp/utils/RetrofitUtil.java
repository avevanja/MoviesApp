package com.avevanjagmail.moviesapp.utils;

import com.avevanjagmail.moviesapp.Interface.LoginApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by irabokalo on 27.07.2016.
 */
public class RetrofitUtil {

    public static LoginApiService getLoginService(){
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(LoginApiService.BASE_URL)
                .build();
        LoginApiService mService = retrofit.create(LoginApiService.class);
        return  mService;
    }
}
