package leonardo.popularmovies.asynctasks;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.model.Video;
import leonardo.popularmovies.utils.NetworkUtils;
import leonardo.popularmovies.utils.TMDBUtils;

/**
 * Created by unity on 04/05/17.
 */
public class FetchVideosTask extends AsyncTask<Movie, Void, List<Video>> {

    private FetchVideosAsyncTaskListener listener;

    public FetchVideosTask(FetchVideosAsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Video> doInBackground(Movie... movies) {
        URL videosRequestUrl = TMDBUtils.buildMovieVideosUrl(movies[0].getId());

        try {
            String jsonVideosResponse = NetworkUtils.getResponseFromHttpUrl(videosRequestUrl);
            List<Video> videos = TMDBUtils.getVideosFromJsonString(jsonVideosResponse);
            return videos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        if (videos != null) {
            listener.onResult(videos);
        } else {
//                showErrorMessage();
        }
    }
}
