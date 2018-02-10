package com.example.marmm.popularmovie.model;

/**
 * Created by marmm on 1/26/18.
 */

public class Review {

    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Review(String content) {

        this.content = content;
    }
}
