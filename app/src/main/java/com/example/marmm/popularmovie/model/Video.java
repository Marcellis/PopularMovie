package com.example.marmm.popularmovie.model;

/**
 * Created by marmm on 24/01/2018.
 */

public class Video {

    String name;
    String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Video(String name, String key) {

        this.name = name;
        this.key = key;
    }

    public Video(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
