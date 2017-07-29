package android.tauqeer.syed.movieforweekend.Utils;
/**
 * Copyright (c) 2017 Syed Tauqeer Hasan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software only for academic purposes and subject to the following conditions:
 * The above copyright notice, the author name and this permission notice shall be included in all copies or substantial portions of the Software.
 */

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Syed Tauqeer Hasan <syedtauqeer.h@gmail.com> on 13/05/2017.
 */

public class NetworkUtils {

    /**
     * Creates a URL connection for extracting the list of movies.
     *
     * @param movieListType type of list to be extracted.
     * @return
     */
    public static HttpURLConnection getMovieListURLConnection(String movieListType) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(AppConstants.SCHEME)
                .authority(AppConstants.BASE_PATH_MOVIE_LIST)
                .appendPath(AppConstants.APPEND_PATH1_FOR_MOVIE_LIST)
                .appendPath(AppConstants.APPEND_PATH2_FOR_MOVIE_LIST)
                .appendPath(movieListType)
                .appendQueryParameter(AppConstants.API_KEY , AppConstants.API);
        String urlString = builder.build().toString();
        Log.i(AppConstants.LOG_TAG , "Built URL String: " + urlString);


        return createHttpConnection(urlString);
    }

    private static HttpURLConnection createHttpConnection(String urlString) {
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

        } catch (MalformedURLException e) {
            Log.e(AppConstants.LOG_TAG , "MalformedURLException caught while creating HttpConnection: " + e.getMessage());
        } catch (IOException e) {
            Log.e(AppConstants.LOG_TAG , "IOException caught while creating HttpConnection: " + e.getMessage());
        }
        return urlConnection;
    }

    /**
     * Reads the data from the established HTTP connection.
     *
     * @param urlConnection
     * @return
     */
    public static String readStringFromURLConnection(HttpURLConnection urlConnection) {
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine = "";
            while((readLine = bufferedReader.readLine()) != null) {
                buffer.append(readLine);
            }
            inputStream.close();
            return buffer.toString();

        } catch (IOException e) {
            Log.e(AppConstants.LOG_TAG , "IOException caught while creating HttpConnection: " + e.getMessage());
        }

        return null;
    }


    public static String getMoviePosterUrlString(final String posterPath){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(AppConstants.SCHEME)
                .authority(AppConstants.BASE_PATH_MOVIE_POSTER)
                .appendPath(AppConstants.APPEND_PATH1_FOR_MOVIE_POSTER)
                .appendPath(AppConstants.APPEND_PATH2_FOR_MOVIE_POSTER)
                .appendPath(AppConstants.APPEND_POSTER_SIZE);

        return (builder.build().toString() + posterPath);
    }

}
