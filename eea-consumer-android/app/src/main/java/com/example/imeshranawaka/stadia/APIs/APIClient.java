package com.example.imeshranawaka.stadia.APIs;

import com.example.imeshranawaka.stadia.Models.LoginDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIClient {

   @POST("/authenticate/")
    Call<LoginDTO> createAuthenticationToken(@Body LoginDTO authenticationRequest);

   @POST("/validateToken")
    Call<Boolean> validateToken(@Body LoginDTO loginDTO);
}
