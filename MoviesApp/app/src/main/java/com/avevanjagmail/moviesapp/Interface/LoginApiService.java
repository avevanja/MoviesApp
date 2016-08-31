package com.avevanjagmail.moviesapp.Interface;

import com.avevanjagmail.moviesapp.models.LogOutRequest;
import com.avevanjagmail.moviesapp.models.LoginRequest;
import com.avevanjagmail.moviesapp.models.LoginResponse;
import com.avevanjagmail.moviesapp.models.LogoutResponse;
import com.avevanjagmail.moviesapp.models.RegisterRequest;
import com.avevanjagmail.moviesapp.models.RegisterResponse;
import com.avevanjagmail.moviesapp.models.ActivateRequest;
import com.avevanjagmail.moviesapp.models.ActivateResponse;
import com.avevanjagmail.moviesapp.models.VerifyRequest;
import com.avevanjagmail.moviesapp.models.VerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by irabokalo on 27.07.2016.
 */
public interface LoginApiService {
    String BASE_URL = "http://146.185.180.39:4020/";

    @POST("login/email/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("registration")
    Call<RegisterResponse>register (@Body RegisterRequest registerRequest);
    @POST("users/verify")
    Call<VerifyResponse> verify (@Body VerifyRequest verifyRequest);
    @PUT("users/activate")
    Call<ActivateResponse>activate (@Body ActivateRequest verifyRequest);
    @POST("login/logout")
    Call<LogoutResponse> logout (@Body LogOutRequest logOutRequest);
}
