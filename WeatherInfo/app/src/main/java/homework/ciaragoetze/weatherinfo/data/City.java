package homework.ciaragoetze.weatherinfo.data;

import io.realm.RealmObject;

/**
 * Created by ciaragoetze on 11/15/17.
 */

public class City extends RealmObject{

    private String cityName;
    private String weather;


    public City () {}

    public City (String cityName) {
        this.cityName = cityName;
    }


    public String getCity() {
        return cityName;
    }

    public void setCity(String cityName) {
        this.cityName = cityName;
    }

    public String getWeather() {return weather;}

    public void setWeather(String weather) {this.weather = weather;}

}
