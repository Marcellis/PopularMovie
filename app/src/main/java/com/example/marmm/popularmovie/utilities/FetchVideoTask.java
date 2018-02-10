package com.example.marmm.popularmovie.utilities;

import android.os.AsyncTask;

import com.example.marmm.popularmovie.model.Video;

import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marmm on 25/01/2018.
 */

public class FetchVideoTask extends AsyncTask<String, Void, List<Video>> {

    private AsyncTaskCompleteListener<List<Video>> listener;

    public FetchVideoTask(AsyncTaskCompleteListener<List<Video>> listener) {
        this.listener = listener;
    }

    @Override
    protected List<Video> doInBackground(String... params) {
        // if there is no movie request there is nothing to look up
        if (params.length == 0)
            return null;

        String videoRequest = params[0];



        try {

            Request request = NetworkUtils.buildUrl(videoRequest);
            OkHttpClient client = new OkHttpClient();
            Response jsonVideoResponse  = client.newCall(request).execute();

            return VideoJSON.getJSON(jsonVideoResponse.body().string());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<Video> videoData) {
        listener.onTaskComplete(videoData);
    }
}
