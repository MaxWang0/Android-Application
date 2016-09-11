package com.example.simpleweather;

import android.app.Activity;
import android.content.SharedPreferences;
 
public class City {
     
    SharedPreferences prefs;
     
    public City(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
     
    // If the user has not chosen a city yet, return
    // Dallas as the default city
    String getCity(){
        return prefs.getString("city", "Dallas, US");        
    }
     
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
     
}
