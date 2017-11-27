package homework.ciaragoetze.weatherinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import homework.ciaragoetze.weatherinfo.adapter.CityAdapter;
import homework.ciaragoetze.weatherinfo.data.WeatherResult;
import homework.ciaragoetze.weatherinfo.network.WeatherAPI;
import homework.ciaragoetze.weatherinfo.touch.CityTouchHelperCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String CITY_NAME = "CITY_NAME";
    public static final String CITY_WEATHER = "CITY_WEATHER";
    public static final String CITY_MAX = "CITY_MAX";
    public static final String CITY_MIN = "CITY_MIN";
    public static final String CITY_CLOUDS = "CITY_CLOUDS";
    public static final String CITY_SUNRISE = "CITY_SUNRISE";
    public static final String CITY_SUNSET = "CITY_SUNSET";
    public static final String CITY_WIND = "CITY_WIND";
    public static final String CITY_ICON = "CITY_ICON";

    private CityAdapter adapter;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setNavigationBar(toolbar);

        setRecyclerView();

        fabAdd = (FloatingActionButton)findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCityDialog();
            }
        });

    }

    private void setRecyclerView() {
        ((CityApplication)getApplication()).openRealm();

        RecyclerView recyclerViewCity = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CityAdapter(this,((CityApplication)getApplication()).getRealmCity());

        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCity.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new CityTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewCity);

        recyclerViewCity.setAdapter(adapter);

    }

    private void setNavigationBar(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_city) {
            showAddCityDialog();
        } else if (id == R.id.nav_about) {
            Toast.makeText(this, R.string.author_toast, Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title);
        final EditText cityName = new EditText(MainActivity.this);
        builder.setView(cityName);

        // Set up the buttons
        builder.setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cityName.getText().toString().length() > 0) {
                    addCityURL(cityName.getText().toString());
                }else{
                    Toast.makeText(MainActivity.this, R.string.empty_error,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addCityURL(final String cityName){

        Call<WeatherResult> call = weatherAPI.addCity(cityName, getString(R.string.unit_type),
                getText(R.string.app_id).toString());

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                String weather = (response.body().getMain().getTempMax().toString());
                String icon = response.body().getWeather().get(0).getIcon();
                adapter.addCity(cityName.toString(), getString(R.string.degree_sign, weather));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    public void openDetailsActivity(final int adapterPosition, final String cityName){
        final Intent intentCity = new Intent(this, WeatherDetailsActivity.class);

        Call<WeatherResult> call = weatherAPI.addCity(cityName, getString(R.string.unit_type),
                getText(R.string.app_id).toString());

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

                String weather = (response.body().getMain().getTempMax().toString());
                String max = (response.body().getMain().getTempMax().toString());
                String min = (response.body().getMain().getTempMin().toString());
                String clouds = (response.body().getClouds().getAll().toString());
                String wind = (response.body().getWind().getSpeed().toString());
                String sunrise = (response.body().getSys().getSunrise().toString());
                String sunset = (response.body().getSys().getSunset().toString());
                String icon = response.body().getWeather().get(0).getIcon();



                intentCity.putExtra(CITY_NAME, cityName);
                intentCity.putExtra(CITY_WEATHER, weather);
                intentCity.putExtra(CITY_MAX, max);
                intentCity.putExtra(CITY_MIN, min);
                intentCity.putExtra(CITY_WIND, wind);
                intentCity.putExtra(CITY_CLOUDS, clouds);
                intentCity.putExtra(CITY_SUNRISE, sunrise);
                intentCity.putExtra(CITY_SUNSET, sunset);
                intentCity.putExtra(CITY_ICON, icon);

                startActivity(intentCity);
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    protected void onDestroy() {
        ((CityApplication)getApplication()).closeRealm();
        super.onDestroy();
    }
}
