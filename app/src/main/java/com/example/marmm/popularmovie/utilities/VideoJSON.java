package com.example.marmm.popularmovie.utilities;

import com.example.marmm.popularmovie.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marmm on 25/01/2018.
 */

public class VideoJSON {

    private static final String RESULTS = "results";
    private static final String KEY = "key";
    private static final String NAME = "name";


    public static List<Video> getJSON(String json) throws JSONException {


        JSONObject videoJSON = new JSONObject(json);
        JSONArray videoArray = videoJSON.getJSONArray(RESULTS);
        List<Video> videos = new ArrayList<>();

        for (int i = 0; i < videoArray.length(); i++) {
            JSONObject videoResult = videoArray.getJSONObject(i);
            String key = videoResult.getString(KEY);
            String name = videoResult.getString(NAME);

            Video video = new Video(name, key);
            videos.add(video);
        }
        return videos;
    }
}