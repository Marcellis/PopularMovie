package com.example.marmm.popularmovie.utilities;

import com.example.marmm.popularmovie.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marmm on 1/26/18.
 */

public class ReviewJSON {

    private static final String RESULTS = "results";
    private static final String CONTENT = "content";

    public static List<Review> getJSON(String json) throws JSONException {

        JSONObject reviewJSON = new JSONObject(json);
        JSONArray reviewArray = reviewJSON.getJSONArray(RESULTS);
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject reviewResult = reviewArray.getJSONObject(i);
            String content = reviewResult.getString(CONTENT);

            Review review = new Review(content);
            reviews.add(review);
        }
        return reviews;
    }
}




