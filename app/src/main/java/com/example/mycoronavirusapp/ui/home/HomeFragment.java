package com.example.mycoronavirusapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycoronavirusapp.R;
import com.example.mycoronavirusapp.ui.countries.CountriesFragment;
import com.example.mycoronavirusapp.ui.countries.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView tvConfirmed, tvRecovered, tvDeaths, tvLastUpdate, tvIdCases, tvIdTodayCases, tvIdDeaths, tvIdTodayDeaths, tvIdActive, tvIdCritical, tvIdRecovered;
    private ProgressBar progressBar;

    private static final String TAG = HomeFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvConfirmed = root.findViewById(R.id.total_confirmed_cases);
        tvRecovered = root.findViewById(R.id.total_recovered);
        tvDeaths = root.findViewById(R.id.total_deaths);
        tvLastUpdate = root.findViewById(R.id.last_update);
        progressBar = root.findViewById(R.id.home_progress);
        tvIdCases = root.findViewById(R.id.id_cases);
        tvIdTodayCases = root.findViewById(R.id.id_today_cases);
        tvIdDeaths = root.findViewById(R.id.id_deaths);
        tvIdTodayDeaths = root.findViewById(R.id.id_today_deaths);
        tvIdActive = root.findViewById(R.id.id_active);
        tvIdCritical = root.findViewById(R.id.id_critical);
        tvIdRecovered = root.findViewById(R.id.id_recovered);

        getData();
        getCountries();

        return root;
    }

    private String getDate(long milliSecond) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MM yyyy hh:mm:ss aaa");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);

        return formatter.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvConfirmed.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvDeaths.setText(jsonObject.getString("deaths"));
                    tvLastUpdate.setText( "Last Update\n" + getDate(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest);
    }

    private void getCountries() {
        String url = "https://corona.lmao.ninja/v2/countries";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject data = jsonArray.getJSONObject(i);
                            if(data.getString("country").equals("Indonesia")) {
                                tvIdCases.setText(data.getString("cases"));
                                tvIdTodayCases.setText(data.getString("todayCases"));
                                tvIdDeaths.setText(data.getString("deaths"));
                                tvIdTodayDeaths.setText(data.getString("todayDeaths"));
                                tvIdActive.setText(data.getString("active"));
                                tvIdCritical.setText(data.getString("critical"));
                                tvIdRecovered.setText(data.getString("recovered"));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onResponse: " + error);
            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}