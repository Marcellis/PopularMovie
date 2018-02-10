package com.example.marmm.popularmovie.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by marmm on 1/27/18.
 */

public final class MoviesContract {

    // provider
    public static final String AUTHORITY = "com.example.marmm.popularmovie.database.moviesprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + MoviesContract.MovieEntry.TABLE_NAME);

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MoviesContract() {
    }

    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_IMAGENAME = "imgename";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_VOTEAVERAGE = "voteaverage";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_RELEASEDATE = "releasedate";

    }
}

