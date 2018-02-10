package com.example.marmm.popularmovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marmm on 1/27/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FIRST_NAME = "movies";
    public static final String DATABASE_NAME_EXTENSION = ".db";
    public static final String DATABASE_NAME = DATABASE_FIRST_NAME + DATABASE_NAME_EXTENSION;
    public static final int DATABASE_VERSION = 1;

    // Creating the table
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME +
                    "("
                    + MoviesContract.MovieEntry.COLUMN_NAME_ID + " TEXT, "
                    + MoviesContract.MovieEntry.COLUMN_NAME_IMAGENAME + " TEXT, "
                    + MoviesContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT, "
                    + MoviesContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE + " TEXT, "
                    + MoviesContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT, "
                    + MoviesContract.MovieEntry.COLUMN_NAME_RELEASEDATE + " TEXT )";


    //Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

