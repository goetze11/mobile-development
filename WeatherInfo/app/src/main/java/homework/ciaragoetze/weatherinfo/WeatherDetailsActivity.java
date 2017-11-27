package homework.ciaragoetze.weatherinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import homework.ciaragoetze.weatherinfo.data.City;
import homework.ciaragoetze.weatherinfo.data.WeatherResult;
import homework.ciaragoetze.weatherinfo.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDetailsActivity extends AppCompatActivity {

    public static final String CITY_NAME = "CITY_NAME";
    private static final String CITY_WEATHER = "CITY_WEATHER";
    private static final String CITY_MAX = "CITY_MAX";
    public static final String CITY_MIN = "CITY_MIN";
    public static final String CITY_CLOUDS = "CITY_CLOUDS";
    public static final String CITY_WIND = "CITY_WIND";
    public static final String CITY_SUNRISE = "CITY_SUNRISE";
    public static final String CITY_SUNSET = "CITY_SUNSET";
    public static final String CITY_ICON = "CITY_ICON";

    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvMax;
    private TextView tvMin;
    private TextView tvClouds;
    private TextView tvWind;
    private TextView tvSunrise;
    private TextView tvSunset;

    private ImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        Intent intent = getIntent();
        final String cityName = intent.getExtras().getString(CITY_NAME);
        final String weather = intent.getExtras().getString(CITY_WEATHER);
        final String max = intent.getExtras().getString(CITY_MAX);
        final String min = intent.getExtras().getString(CITY_MIN);
        final String clouds = intent.getExtras().getString(CITY_CLOUDS);
        final String wind = intent.getExtras().getString(CITY_WIND);
        final String sunrise = intent.getExtras().getString(CITY_SUNRISE);
        final String sunset = intent.getExtras().getString(CITY_SUNSET);
        final String icon = intent.getExtras().getString(CITY_ICON);



        tvCity = (TextView)findViewById(R.id.tvCity);
        tvWeather = (TextView)findViewById(R.id.tvWeather);
        tvMax = (TextView)findViewById(R.id.tvMax);
        tvMin = (TextView)findViewById(R.id.tvMin);
        tvClouds = (TextView)findViewById(R.id.tvClouds);
        tvWind = (TextView)findViewById(R.id.tvWind);
        tvSunrise = (TextView)findViewById(R.id.tvSunrise);
        tvSunset = (TextView)findViewById(R.id.tvSunset);
        ivImage = (ImageView)findViewById(R.id.ivImage);

        tvCity.setText(cityName);
        tvWeather.setText(getString(R.string.degree_sign, weather));
        tvMax.setText(getString(R.string.max_temp, max));
        tvMin.setText(getString(R.string.min_temp, min));
        tvClouds.setText(getString(R.string.clouds, clouds));
        tvWind.setText(getString(R.string.wind, wind));
        tvSunrise.setText(getString(R.string.sunrise, sunrise));
        tvSunset.setText(getString(R.string.sunset, sunset));

        Glide.with(this).load(getString(R.string.icon_base, icon)).into(ivImage);





    }
}
