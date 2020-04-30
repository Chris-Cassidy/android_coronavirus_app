package com.example.mycoronavirusapp.ui.countries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycoronavirusapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    ArrayList<Country> countries;
    private Context context;

    public CountryAdapter(ArrayList<Country> countries, Context context) {
        this.countries = countries;
        this.context = context;
    }

    private String getDate(long milliSecond) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MM yyyy hh:mm:ss aaa");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);

        return formatter.format(calendar.getTime());
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.tvCountryName.setText(country.getCountry());
        holder.tvLastUpdate.setText(getDate(country.getUpdated()));
        holder.tvTotalConfirmed.setText(country.getCases());
        holder.tvTotalRecovered.setText(country.getRecovered());
        holder.tvTotalDeaths.setText(country.getDeaths());
        Glide.with(context)
                .load(country.getFlag())
                .apply(new RequestOptions().override(240,160))
                .into(holder.imgCountryFlag);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName, tvLastUpdate, tvTotalConfirmed, tvTotalRecovered, tvTotalDeaths;
        ImageView imgCountryFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvLastUpdate = itemView.findViewById(R.id.tvLastUpdate);
            tvTotalConfirmed = itemView.findViewById(R.id.tvTotalConfirmed);
            tvTotalRecovered = itemView.findViewById(R.id.tvTotalRecovered);
            tvTotalDeaths = itemView.findViewById(R.id.tvTotalDeaths);
            imgCountryFlag = itemView.findViewById(R.id.imgCountryFlag);

        }
    }
}
