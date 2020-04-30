package com.example.mycoronavirusapp.ui.countries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycoronavirusapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CountriesFragment extends Fragment {

    TextView tvTotalCountries;
    RecyclerView rvCountry;
    ProgressBar progressBar;

    ArrayList<Country> countries;

    private static final String TAG = CountriesFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_countries, container, false);

        tvTotalCountries = root.findViewById(R.id.total_countries);
        rvCountry = root.findViewById(R.id.rvCountry);
        progressBar = root.findViewById(R.id.countries_progress);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCountry.setLayoutManager(linearLayoutManager);

        getCountries();
        return root;
    }

    private void showRecyclerView() {
        CountryAdapter countryAdapter = new CountryAdapter(countries, getActivity());
        rvCountry.setAdapter(countryAdapter);
    }

    private void getCountries() {
        String url = "https://corona.lmao.ninja/v2/countries";

        countries = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                int totalCountries = 0;

                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            JSONObject countryInfo = data.getJSONObject("countryInfo");
//
                            countries.add(new Country(
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("recovered"),
                                    data.getString("deaths"),
                                    countryInfo.getString("flag"),
                                    data.getLong("updated")
                            ));

                            totalCountries += 1;
                        }
                        tvTotalCountries.setText("Total countries : " + String.valueOf(totalCountries));
                        showRecyclerView();
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
