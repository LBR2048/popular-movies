package leonardo.popularmovies.asynctasks;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.model.Review;
import leonardo.popularmovies.utils.FetchReviewsAsyncTaskListener;
import leonardo.popularmovies.utils.NetworkUtils;
import leonardo.popularmovies.utils.TMDBUtils;

/**
 * Created by unity on 04/05/17.
 */
public class FetchReviewsTask extends AsyncTask<Movie, Void, List<Review>> {

    private FetchReviewsAsyncTaskListener listener;

    public FetchReviewsTask(FetchReviewsAsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Review> doInBackground(Movie... movies) {
        URL reviewsRequestUrl = TMDBUtils.buildMovieReviewsUrl(movies[0].getId());

        try {
            String jsonReviewsResponse = NetworkUtils.getResponseFromHttpUrl(reviewsRequestUrl);
            List<Review> reviews = TMDBUtils.getReviewsFromJsonString(jsonReviewsResponse);
            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        if (reviews != null) {
            listener.onResult(reviews);
        } else {
//                showErrorMessage();
        }
    }
}
