package com.example.imeshranawaka.stadia.APIs;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.imeshranawaka.stadia.Fragments.Login;
import com.example.imeshranawaka.stadia.R;
import com.example.imeshranawaka.stadia.SharedPreferenceUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIBuilder {

    private static APIClient apiClient;
    private static APIClient apiAuthClient;
    public static APIClient createBuilder(){
        if(apiClient==null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl("http://10.0.2.2:8080")
                    .baseUrl("http://192.168.137.1:8080")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            apiClient = retrofit.create(APIClient.class);
        }
        return apiClient;
    }

    public static APIClient createAuthBuilder(Context context){
        if(apiAuthClient==null){
            String token = "Bearer "+SharedPreferenceUtility.getInstance(context).getUserToken();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor( new Interceptor() {
                  @Override
                  public Response intercept(Chain chain) throws IOException {
                      Request original = chain.request();

                      Request request = original.newBuilder()
                              .header("Authorization", token)
                              .method(original.method(), original.body())
                              .build();

                      return chain.proceed(request);
                  }
              });

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("http://10.0.2.2:8080")
                    .baseUrl("http://192.168.137.1:8080")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            apiAuthClient = retrofit.create(APIClient.class);
        }
        return apiAuthClient;
    }

    public static void Logout(Context context,FragmentActivity activity){
        FragmentManager fm = activity.getSupportFragmentManager();
        if(fm!=null) {
            FragmentTransaction transaction = fm.beginTransaction();
            int backStackEntry = fm.getBackStackEntryCount();
            List<Fragment> fragments = fm.getFragments();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {
                    fm.popBackStackImmediate();
                    if (fragments.size() < i) {
                        Fragment frag = fragments.get(0);
                        transaction.remove(frag);
                    }
                    fragments = fm.getFragments();
                }
            }

            SharedPreferenceUtility.getInstance(context).resetSharedPreferences();
            transaction.replace(R.id.mainFragment, new Login(), "Login");
            transaction.commit();
        }
    }
}
