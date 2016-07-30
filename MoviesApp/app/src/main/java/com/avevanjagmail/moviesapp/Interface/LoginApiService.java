package com.avevanjagmail.moviesapp.Interface;

import com.avevanjagmail.moviesapp.Models.LogOutRequest;
import com.avevanjagmail.moviesapp.Models.LoginRequest;
import com.avevanjagmail.moviesapp.Models.LoginResponse;
import com.avevanjagmail.moviesapp.Models.RegisterRequest;
import com.avevanjagmail.moviesapp.Models.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by irabokalo on 27.07.2016.
 */
public interface LoginApiService {
    String BASE_URL = "http://146.185.180.39:4020/";

    @POST("login/email/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("login/logout")
   Call<LoginResponse> logout (@Body LogOutRequest logOutRequest);
    @POST("registration")
    Call<RegisterResponse>register (@Body RegisterRequest registerRequest);
   @GET("/login/email/{password}")
  Call<List<LoginRequest>> getPassword(@Path("password") String password);
}
