package com.example.mycoronavirusapp.ui.countries;

public class Country {
    String country, cases, recovered, deaths, flag;
    long updated;

    public Country(String country, String cases, String recovered, String deaths, String flag, long updated) {
        this.country = country;
        this.cases = cases;
        this.recovered = recovered;
        this.deaths = deaths;
        this.flag = flag;
        this.updated = updated;
    }

    public String getCountry() {
        return country;
    }

    public String getCases() {
        return cases;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getFlag() {
        return flag;
    }

    public long getUpdated() {
        return updated;
    }
}
