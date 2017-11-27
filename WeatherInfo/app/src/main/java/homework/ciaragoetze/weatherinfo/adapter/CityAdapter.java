package homework.ciaragoetze.weatherinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import homework.ciaragoetze.weatherinfo.MainActivity;
import homework.ciaragoetze.weatherinfo.R;
import homework.ciaragoetze.weatherinfo.data.City;
import homework.ciaragoetze.weatherinfo.touch.CityTouchAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ciaragoetze on 11/15/17.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>
        implements CityTouchAdapter {

    private List<City> cityList;

    private Context context;
    private Realm realmCity;


    public CityAdapter(Context context, Realm realmCity) {
        this.context = context;
        this.realmCity = realmCity;

        cityList = new ArrayList<City>();

        RealmResults<City> cityResult =
                realmCity.where(City.class).findAll()
                        .sort(context.getString(R.string.city_name), Sort.ASCENDING);

        for (City city : cityResult) {
            cityList.add(city);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cityRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_row, parent, false);
        return new ViewHolder(cityRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final City cityData = cityList.get(position);

        holder.tvName.setText(cityData.getCity());
        holder.tvWeather.setText(cityData.getWeather());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).openDetailsActivity(holder.getAdapterPosition(),
                        cityList.get(holder.getAdapterPosition()).getCity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public void onItemDismiss(int position) {

        City cityToDelete = cityList.get(position);
        realmCity.beginTransaction();
        cityToDelete.deleteFromRealm();
        realmCity.commitTransaction();

        cityList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cityList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cityList, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void addCity(String cityName, String weather){

        realmCity.beginTransaction();

        City newCity = realmCity.createObject(City.class);
        newCity.setCity(cityName);
        newCity.setWeather(weather);
        realmCity.commitTransaction();

        cityList.add(0, newCity);
        notifyItemInserted(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvWeather;
        private CardView cardView;

        public ViewHolder(View cityView) {
            super(cityView);

            tvName = cityView.findViewById(R.id.tvName);
            tvWeather = cityView.findViewById(R.id.tvWeather);
            cardView = cityView.findViewById(R.id.card_view);

        }
    }
}
