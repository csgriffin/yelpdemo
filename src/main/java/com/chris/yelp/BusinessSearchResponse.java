package com.chris.yelp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.Arrays;

public class BusinessSearchResponse {

    ArrayList<Business> businesses = new ArrayList<>();

    @JsonSetter("businesses")
    public void setBusinesses(Business[] businesses){
        this.businesses.clear();
        this.businesses.addAll(Arrays.asList(businesses));
    }
}
