package leonardo.popularmovies.utils;

import java.util.List;

import leonardo.popularmovies.model.Review;

/**
 * Created by unity on 04/05/17.
 */

public interface FetchReviewsAsyncTaskListener {
    void onResult(List<Review> reviews);
}
