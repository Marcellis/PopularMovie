package com.example.marmm.popularmovie.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.marmm.popularmovie.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by marmm on 12/9/17.
 */

public class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String API_KEY = "api_key";

    // the api key from https://api.themoviedb.org
    private static final String api_key = BuildConfig.API_KEY;
    private static final String MOVIE_URL =
            "https://api.themoviedb.org/3/movie/";


    public static Request buildUrl(String movie_endpoint) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(MOVIE_URL + movie_endpoint).newBuilder();
        urlBuilder.addQueryParameter(API_KEY, api_key);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        return  request;
    }



/*
    because of okHTPP not necessary
    public static URL buildUrl(String movie_endpoint) {

        Uri builtUri = Uri.parse(MOVIE_URL + movie_endpoint).buildUpon()
                .appendQueryParameter(API_KEY, api_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }
*/
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
  /*  public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

*/
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


}
