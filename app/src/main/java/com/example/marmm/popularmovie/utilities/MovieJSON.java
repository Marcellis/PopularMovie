package com.example.marmm.popularmovie.utilities;

import com.example.marmm.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marmm on 14/12/2017.
 */

class MovieJSON {


    private static final String ID = "id";
    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";

    public static List<Movie> getJSON(String json) throws JSONException {


        JSONObject movieJSON = new JSONObject(json);
        JSONArray movieArray = movieJSON.getJSONArray(RESULTS);
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieResult = movieArray.getJSONObject(i);
            String id = movieResult.getString(ID);
            String description = movieResult.getString(POSTER_PATH);
            String overview = movieResult.getString(OVERVIEW);
            String vote_average = movieResult.getString(VOTE_AVERAGE);
            String release_date = movieResult.getString(RELEASE_DATE);
            String title = movieResult.getString(TITLE);

            Movie movie = new Movie(id, description, overview, vote_average, release_date, title);
            movies.add(movie);
        }
        return movies;
    }

}