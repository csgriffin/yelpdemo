package com.chris.yelp;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Location {
    String address1;

    @JsonSetter("address1")
    public void setAddress1(String address1){
        this.address1 = address1;
    }
}
