package com.example.marmm.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by marmm on 07/12/2017.
 */

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private String id;
    private String imagename;
    private String overview;
    private String voteAverage;
    private String title;
    private String releaseDate;


    public Movie(String id, String imagename, String overview, String voteAverage, String releaseDate, String title) {
        this.id = id;
        this.imagename = imagename;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImagename() {
        return imagename;
    }


    private Movie(Parcel in) {
        id = in.readString();
        imagename = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        releaseDate = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(imagename);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
    }
}



