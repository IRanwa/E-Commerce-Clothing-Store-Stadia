package com.example.imeshranawaka.stadia;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtility {
    private static SharedPreferenceUtility instance = new SharedPreferenceUtility();
    private static Context mContext;

    private SharedPreferenceUtility(){}

    public static SharedPreferenceUtility getInstance(Context context){
        mContext = context;
        return instance;
    }

    public SharedPreferences.Editor getEditor(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPref(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public void setUserName(String name){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("name",name);
        editor.apply();
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("email",email);
        editor.apply();
    }

    public void setUserToken(String token){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("token",token);
        editor.apply();
    }

    public void setUserPass(String pass){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("pass",pass);
        editor.apply();
    }

    public String getUserName(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("name","");
    }

    public String getUserEmail(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("email","");
    }

    public String getUserToken(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("token","");
    }

    public String getUserPass(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("pass","");
    }
}
