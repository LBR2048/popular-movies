package leonardo.popularmovies.asynctasks;

import java.util.List;

import leonardo.popularmovies.model.Video;

/**
 * Created by unity on 04/05/17.
 */

public interface FetchVideosAsyncTaskListener {
    void onResult(List<Video> videos);
}
