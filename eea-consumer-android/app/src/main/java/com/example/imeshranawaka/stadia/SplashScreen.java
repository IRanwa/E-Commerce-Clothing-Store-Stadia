package com.example.imeshranawaka.stadia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imeshranawaka.stadia.Models.City;
import com.example.imeshranawaka.stadia.Models.Country;
import com.example.imeshranawaka.stadia.Models.State;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);
        //saveCountryList();

        Intent intent = new Intent(this,Stadia.class);
        startActivity(intent);
        finish();
    }

//    private void saveCountryList() {
//        List<Country> list = Country.listAll(Country.class);
//        if(list.size()>0){
//            Country.deleteAll(Country.class);
//            State.deleteAll(State.class);
//            City.deleteAll(City.class);
//            list = Country.listAll(Country.class);
//        }
//        if(list.size()==0) {
//            String json = loadJSONFromAsset();
//
//            try {
//                JSONObject obj = new JSONObject(json);
//                JSONArray countryList = obj.getJSONArray("countryList");
//                for (int x = 0; x < countryList.length(); x++) {
//                    JSONObject country = (JSONObject) countryList.get(x);
//                    JSONArray stateList = country.getJSONArray("states");
//                    for (int y = 0; y < stateList.length(); y++) {
//                        JSONObject state = (JSONObject) stateList.get(y);
//
//                        JSONArray cityList = state.getJSONArray("cities");
//                        for (int z = 0; z < cityList.length(); z++) {
//                            JSONObject city = (JSONObject) cityList.get(z);
//
////                            City saveCity = new City(Integer.parseInt(city.getString("id")), Integer.parseInt(state.getString("id")),
////                                    city.getString("name"));
////                            saveCity.save();
//                        }
////                        State saveState = new State(Integer.parseInt(state.getString("id")), Integer.parseInt(country.getString("id")),
////                                state.getString("name"));
////                        saveState.save();
//                    }
////                    Country saveCountry = new Country(Integer.parseInt(country.getString("id")), country.getString("name"));
////                    saveCountry.save();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("end");
//            //new CountrySave().execute(json);
//        }
//    }
//
//    class CountrySave extends AsyncTask<String,Void,Void>{
//
//        JSONObject obj;
//        @Override
//        protected Void doInBackground(String... strings) {
//            try {
//                obj = new JSONObject(strings[0]);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            JSONArray countryList = null;
//            try {
//                countryList = obj.getJSONArray("countryList");
//                for (int x = 0; x < countryList.length(); x++) {
//                    JSONObject country = (JSONObject) countryList.get(x);
//                    JSONArray stateList = country.getJSONArray("states");
//                    for (int y = 0; y < stateList.length(); y++) {
//                        JSONObject state = (JSONObject) stateList.get(y);
//                        new StateSave().execute(state,country);
//                    }
//                    Country saveCountry = new Country(Integer.parseInt(country.getString("id")), country.getString("name"));
//                    saveCountry.save();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class StateSave extends AsyncTask<JSONObject,Void,Void>{
//
//        JSONObject state;
//        JSONObject country;
//        @Override
//        protected Void doInBackground(JSONObject... jsonObjects) {
//
//            state = jsonObjects[0];
//            country = jsonObjects[1];
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            JSONArray cityList = null;
//            try {
//                cityList = state.getJSONArray("cities");
//                for (int z = 0; z < cityList.length(); z++) {
//                    JSONObject city = (JSONObject) cityList.get(z);
//                    new CitySave().execute(city,state);
//                }
//                State saveState = new State(Integer.parseInt(state.getString("id")), Integer.parseInt(country.getString("id")),
//                        state.getString("name"));
//                saveState.save();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class CitySave extends AsyncTask<JSONObject,Void,Void>{
//
//        @Override
//        protected Void doInBackground(JSONObject... jsonObjects) {
//            JSONObject city = jsonObjects[0];
//            JSONObject state = jsonObjects[1];
//
//            City saveCity = null;
//            try {
//                saveCity = new City(Integer.parseInt(city.getString("id")), Integer.parseInt(state.getString("id")),
//                        city.getString("name"));
//                saveCity.save();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//
//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("countryList.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
}
