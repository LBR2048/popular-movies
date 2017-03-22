/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leonardo.popularmovies.utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.Movie;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class TMDBUtils {

    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w185";

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param forecastJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static List<Movie> getMoviesFromJsonString(Context context, String forecastJsonStr)
            throws JSONException {

        final String TDB_RESULTS = "results";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        List<Movie> movies = new ArrayList<>();

        /* Is there an error? */
//        if (forecastJson.has(OWM_MESSAGE_CODE)) {
//            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
//
//            switch (errorCode) {
//                case HttpURLConnection.HTTP_OK:
//                    break;
//                case HttpURLConnection.HTTP_NOT_FOUND:
//                    /* Location invalid */
//                    return null;
//                default:
//                    /* Server probably down */
//                    return null;
//            }
//        }

        JSONArray moviesArray = forecastJson.getJSONArray(TDB_RESULTS);

        for (int i = 0; i < moviesArray.length(); i++) {

            /* These are the values that will be collected */
            final String title;
            final String poster;
            final String overview;
            final String rating;
            final String releaseDate;

            /* Get the JSON object representing the movie */
            JSONObject movieData = moviesArray.getJSONObject(i);

            title = movieData.getString(ORIGINAL_TITLE);
            poster = BASE_POSTER_PATH + movieData.getString(POSTER_PATH);
            overview = movieData.getString(OVERVIEW);
            rating = movieData.getString(VOTE_AVERAGE);
            releaseDate = movieData.getString(RELEASE_DATE);

            Movie movie = new Movie(title, poster, overview, rating, releaseDate);
            movies.add(movie);
        }

        return movies;
    }

    /**
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param forecastJsonStr The JSON to parse into ContentValues.
     *
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}