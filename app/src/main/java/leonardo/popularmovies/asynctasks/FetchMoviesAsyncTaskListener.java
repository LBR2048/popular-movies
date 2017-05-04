package leonardo.popularmovies.asynctasks;

import java.util.List;

import leonardo.popularmovies.model.Movie;

/**
 * Created by unity on 04/05/17.
 */

public interface FetchMoviesAsyncTaskListener {
    void onResult(List<Movie> movies);
}
