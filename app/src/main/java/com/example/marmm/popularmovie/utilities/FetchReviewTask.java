package com.example.marmm.popularmovie.utilities;

import android.os.AsyncTask;

import com.example.marmm.popularmovie.model.Review;

import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marmm on 1/26/18.
 */

public class FetchReviewTask extends AsyncTask<String, Void, List<Review>> {

    private AsyncTaskCompleteListener<List<Review>> listener;

    public FetchReviewTask(AsyncTaskCompleteListener<List<Review>> listener) {
        this.listener = listener;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        // if there is no movie request there is nothing to look up
        if (params.length == 0)
            return null;

        String reviewRequest = params[0];

        try {

            Request request = NetworkUtils.buildUrl(reviewRequest);
            OkHttpClient client = new OkHttpClient();
            Response jsonReviewResponse  = client.newCall(request).execute();

            return ReviewJSON.getJSON(jsonReviewResponse.body().string());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<Review> reviewData) {
        listener.onTaskComplete(reviewData);
    }
}



