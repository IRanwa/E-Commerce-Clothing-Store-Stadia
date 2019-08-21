package com.example.imeshranawaka.stadia.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIBuilder {

    private static APIClient apiClient;
    public static APIClient createBuilder(){
        if(apiClient==null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            apiClient = retrofit.create(APIClient.class);
        }
        return apiClient;
    }
}
