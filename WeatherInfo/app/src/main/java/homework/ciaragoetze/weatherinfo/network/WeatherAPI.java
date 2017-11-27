package homework.ciaragoetze.weatherinfo.network;

import homework.ciaragoetze.weatherinfo.data.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ciaragoetze on 11/20/17.
 */

public interface WeatherAPI {
    @GET("/data/2.5/weather?")
    Call<WeatherResult> addCity(@Query("q") String cityName, @Query("units") String unitType,
                                   @Query("appid") String appID);


}
