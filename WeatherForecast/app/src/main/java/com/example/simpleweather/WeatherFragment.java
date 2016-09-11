package com.example.simpleweather;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends Fragment {
    Typeface weatherFont;
     
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    
    TextView forecastdetailsDay1Field;
    TextView forecastdetailsDay2Field;
    TextView forecastdetailsDay3Field;
    
    TextView forecastdetailsDay1FieldHeader;
    TextView forecastdetailsDay2FieldHeader;
    TextView forecastdetailsDay3FieldHeader;
    
    TextView forecastDay1Icon;
    TextView forecastDay2Icon;
    TextView forecastDay3Icon;
    
    Handler handler;
 
    public WeatherFragment(){   
        handler = new Handler();
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
        
        forecastdetailsDay1FieldHeader = (TextView)rootView.findViewById(R.id.forecast_details_day1_fieldHeader);
        forecastdetailsDay2FieldHeader = (TextView)rootView.findViewById(R.id.forecast_details_day2_fieldHeader);
        forecastdetailsDay3FieldHeader = (TextView)rootView.findViewById(R.id.forecast_details_day3_fieldHeader);
        
        forecastdetailsDay1Field = (TextView)rootView.findViewById(R.id.forecast_details_day1_field);
        forecastdetailsDay2Field = (TextView)rootView.findViewById(R.id.forecast_details_day2_field);
        forecastdetailsDay3Field = (TextView)rootView.findViewById(R.id.forecast_details_day3_field);
        
        //forecastDay1Icon = (TextView)rootView.findViewById(R.id.forecast_details_day1_icon);
        //forecastDay2Icon = (TextView)rootView.findViewById(R.id.forecast_details_day2_icon);
        //forecastDay3Icon = (TextView)rootView.findViewById(R.id.forecast_details_day3_icon);
        
        weatherIcon.setTypeface(weatherFont);
        return rootView; 
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");     
        updateWeatherData(new City(getActivity()).getCity());
    }
    
    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = JsonFetch.getJSONWeather(getActivity(), city);
                final JSONObject jsonForecast = JsonFetch.getForecastJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(), 
                                    getActivity().getString(R.string.place_not_found), 
                                    Toast.LENGTH_LONG).show(); 
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                            renderWeatherForecast(jsonForecast);
                        }
                    });
                }               
            }
        }.start();
    }
    
    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) + 
                    ", " + 
                    json.getJSONObject("sys").getString("country"));
             
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                    "\n" + "Humidity: " + main.getString("humidity") + "%" +
                    "\n" + "Pressure: " + main.getString("pressure") + " hPa");
             
            currentTemperatureField.setText(
                        String.format("%.2f", main.getDouble("temp"))+ " ℃");
     
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);
     
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);
             
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }
    
    private void renderWeatherForecast(JSONObject json){
        try {
            JSONObject day1 = json.getJSONArray("list").getJSONObject(0);
            JSONObject day2 = json.getJSONArray("list").getJSONObject(1);
            JSONObject day3 = json.getJSONArray("list").getJSONObject(2);
            
            JSONObject day1Weather = day1.getJSONArray("weather").getJSONObject(0);
            JSONObject day2Weather = day2.getJSONArray("weather").getJSONObject(0);
            JSONObject day3Weather = day3.getJSONArray("weather").getJSONObject(0);

            //Log.w("SimpleWeather", day1.getJSONObject("temp").toString());
            
            forecastdetailsDay1Field.setText(
            		day1Weather.getString("description").toUpperCase(Locale.US) +
                    "\n" + "Humidity: " + day1.getString("humidity") + "%" +
                    "\n" + "Min Temp: " + day1.getJSONObject("temp").getString("min") + "  ℃" +
                    " " + "Max Temp: " + day1.getJSONObject("temp").getString("max") + "  ℃" +
                    "\n" + "Pressure: " + day1.getString("pressure") + " hPa");
            
            forecastdetailsDay2Field.setText(
            		day2Weather.getString("description").toUpperCase(Locale.US) +
                    "\n" + "Humidity: " + day2.getString("humidity") + "%" +
                    "\n" + "Min Temp: " + day2.getJSONObject("temp").getString("min") + "  ℃" +
                    " " + "Max Temp: " + day2.getJSONObject("temp").getString("max") + "  ℃" +
                    "\n" + "Pressure: " + day2.getString("pressure") + " hPa");
            
            forecastdetailsDay3Field.setText(
            		day3Weather.getString("description").toUpperCase(Locale.US) +
                    "\n" + "Humidity: " + day3.getString("humidity") + "%" +
                    "\n" + "Min Temp: " + day3.getJSONObject("temp").getString("min") + "  ℃" +
                    " " + "Max Temp: " + day3.getJSONObject("temp").getString("max") + "  ℃" +
                    "\n" + "Pressure: " + day3.getString("pressure") + " hPa");
           
            DateFormat df = DateFormat.getDateTimeInstance();
            forecastdetailsDay1FieldHeader.setText(df.format(new Date(day1.getLong("dt")*1000)));
            forecastdetailsDay2FieldHeader.setText(df.format(new Date(day2.getLong("dt")*1000)));
            forecastdetailsDay3FieldHeader.setText(df.format(new Date(day3.getLong("dt")*1000)));
            
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }   
    
    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
            case 2 : icon = getActivity().getString(R.string.weather_thunder);
                     break;         
            case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                     break;     
            case 7 : icon = getActivity().getString(R.string.weather_foggy);
                     break;
            case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                     break;
            case 6 : icon = getActivity().getString(R.string.weather_snowy);
                     break;
            case 5 : icon = getActivity().getString(R.string.weather_rainy);
                     break;
            }
        }
        weatherIcon.setText(icon);
    }
    
    public void changeCity(String city){
        updateWeatherData(city);
    }
}