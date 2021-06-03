package com.chris.yelp;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Business {
    String name;
    String id;
    Location location;

    @JsonSetter("name")
    public void setName(String name){
        this.name = name;
    }

    @JsonSetter("id")
    public void setId(String id){
        this.id = id;
    }

    @JsonSetter("location")
    public void setLocation(Location location){
        this.location = location;
    }
}
