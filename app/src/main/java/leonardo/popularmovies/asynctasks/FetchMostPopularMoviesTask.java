package leonardo.popularmovies.asynctasks;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.utils.NetworkUtils;
import leonardo.popularmovies.utils.TMDBUtils;

/**
 * Created by unity on 04/05/17.
 */
public class FetchMostPopularMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private FetchMoviesAsyncTaskListener listener;

    public FetchMostPopularMoviesTask(FetchMoviesAsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        URL moviesRequestUrl = TMDBUtils.buildMostPopularMoviesUrl();

        try {
            String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
            List<Movie> movies = TMDBUtils.getMoviesFromJsonString(jsonMoviesResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            listener.onResult(movies);
        } else {
//                showErrorMessage();
        }
    }
}
