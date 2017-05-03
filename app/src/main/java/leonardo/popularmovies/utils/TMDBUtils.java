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

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.BuildConfig;
import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.model.Review;
import leonardo.popularmovies.model.Video;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class TMDBUtils {

    private static final String TAG = TMDBUtils.class.getSimpleName();

    private static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    private static final String PATH_POPULAR = "popular";
    private static final String PATH_TOP_RATED = "top_rated";
    private static final String PATH_TRAILERS = "videos";
    private static final String PATH_REVIEWS = "reviews";

    private static final String QUERY_PARAMETER_API_KEY = "api_key";

    private static final String ID = "id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w500";

    private static final String KEY = "key";
    private static final String NAME = "name";
    public static final String CONTENT = "content";


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
            final int id;
            final String title;
            final String poster;
            final String overview;
            final String rating;
            final String releaseDate;

            /* Get the JSON object representing the movie */
            JSONObject movieData = moviesArray.getJSONObject(i);

            id = movieData.getInt(ID);
            title = movieData.getString(ORIGINAL_TITLE);
            poster = BASE_POSTER_PATH + movieData.getString(POSTER_PATH);
            overview = movieData.getString(OVERVIEW);
            rating = movieData.getString(VOTE_AVERAGE);
            releaseDate = movieData.getString(RELEASE_DATE);

            Movie movie = new Movie(id, title, poster, overview, rating, releaseDate);
            movies.add(movie);
        }

        return movies;
    }

    public static List<Video> getVideosFromJsonString(Context context, String forecastJsonStr)
            throws JSONException {

        final String TDB_RESULTS = "results";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        List<Video> videos = new ArrayList<>();

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

        JSONArray videosArray = forecastJson.getJSONArray(TDB_RESULTS);

        for (int i = 0; i < videosArray.length(); i++) {

            /* These are the values that will be collected */
            final String key;
            final String name;
            final String title;
            final String poster;
            final String overview;
            final String rating;
            final String releaseDate;

            /* Get the JSON object representing the movie */
            JSONObject movieData = videosArray.getJSONObject(i);

            key = movieData.getString(KEY);
            name = movieData.getString(NAME);
//            title = movieData.getString(ORIGINAL_TITLE);
//            poster = BASE_POSTER_PATH + movieData.getString(POSTER_PATH);
//            overview = movieData.getString(OVERVIEW);
//            rating = movieData.getString(VOTE_AVERAGE);
//            releaseDate = movieData.getString(RELEASE_DATE);

            Video video = new Video(key, name);
            videos.add(video);
        }

        return videos;
    }

    public static List<Review> getReviewsFromJsonString(Context context, String forecastJsonStr)
            throws JSONException {

        final String TDB_RESULTS = "results";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        List<Review> reviews = new ArrayList<>();

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

        JSONArray videosArray = forecastJson.getJSONArray(TDB_RESULTS);

        for (int i = 0; i < videosArray.length(); i++) {

            /* These are the values that will be collected */
            final String key;
            final String content;

            /* Get the JSON object representing the movie */
            JSONObject reviewData = videosArray.getJSONObject(i);

            content = reviewData.getString(CONTENT);
//            title = movieData.getString(ORIGINAL_TITLE);
//            poster = BASE_POSTER_PATH + movieData.getString(POSTER_PATH);
//            overview = movieData.getString(OVERVIEW);
//            rating = movieData.getString(VOTE_AVERAGE);
//            releaseDate = movieData.getString(RELEASE_DATE);

            Review video = new Review(content);
            reviews.add(video);
        }

        return reviews;
    }

    public static URL buildTopRatedMoviesUrl() {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(PATH_TOP_RATED)
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, API_KEY)
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

    public static URL buildMostPopularMoviesUrl() {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(PATH_POPULAR)
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, API_KEY)
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

    public static URL buildMovieTrailersUrl(int movieId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(Integer.toString(movieId))
                .appendPath(PATH_TRAILERS)
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, API_KEY)
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

    public static URL buildMovieReviewsUrl(int movieId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(Integer.toString(movieId))
                .appendPath(PATH_REVIEWS)
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, API_KEY)
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

}