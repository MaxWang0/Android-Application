package com.example.simpleweather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.json.JSONObject;
 
import android.content.Context;
 
public class JsonFetch {
 
    private static final String OPEN_WEATHER_MAP_API_C = 
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API_F = 
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial";
    private static final String OPEN_FORECAST_MAP_API_C = 
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&cnt=3&units=metric";
    private static final String OPEN_FORECAST_MAP_API_F =
    		"http://api.openweathermap.org/data/2.5/forecast/daily?q=dallas&cnt=3&units=imperial";
    
    public static JSONObject getJSONWeather(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_C, city));           
            HttpURLConnection connection = 
                    (HttpURLConnection)url.openConnection();
             
            connection.addRequestProperty("x-api-key", 
                    context.getString(R.string.open_weather_maps_app_id));
             
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
             
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
             
            JSONObject data = new JSONObject(json.toString());
             
            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }
             
            return data;
        }catch(Exception e){
            return null;
        }
    }
    
    public static JSONObject getForecastJSON(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_FORECAST_MAP_API_C, city));           
            HttpURLConnection connection = 
                    (HttpURLConnection)url.openConnection();
             
            connection.addRequestProperty("x-api-key", 
                    context.getString(R.string.open_weather_maps_app_id));
             
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
             
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
             
            JSONObject data = new JSONObject(json.toString());
             
            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }
             
            return data;
        }catch(Exception e){
            return null;
        }
    }
    
}
