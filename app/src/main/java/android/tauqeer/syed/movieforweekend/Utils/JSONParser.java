package android.tauqeer.syed.movieforweekend.Utils;
/**
 * Copyright (c) 2017 Syed Tauqeer Hasan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software only for academic purposes and subject to the following conditions:
 * The above copyright notice, the author name and this permission notice shall be included in all copies or substantial portions of the Software.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed Tauqeer Hasan <syedtauqeer.h@gmail.com> on 13/05/2017.
 */

public class JSONParser {

    private static final String POSTER_KEY = "poster_path";
    private static final String RESULTS_KEY = "results";

    /**
     * Parses the JSON returned from movies API and return a list of all the poster paths.
     *
     * @param jsonString
     * @return
     */
    public static List<String> getAllPostersPath(final String jsonString) {

        List<String> posterPaths = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonResults = jsonObject.getJSONArray(RESULTS_KEY);
            for (int i = 0 ; i < jsonResults.length() ; i++) {
                posterPaths.add(jsonResults.getJSONObject(i).getString(POSTER_KEY));
            }
        } catch (JSONException e) {
            Log.e(AppConstants.LOG_TAG , "JSONException caught while creating HttpConnection: " +
                    e.getMessage());
        }
        return posterPaths;
    }
}
