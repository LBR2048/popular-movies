package leonardo.popularmovies.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by unity on 21/04/17.
 */

public final class YouTubeUtils {

    private static final String TAG = YouTubeUtils.class.getSimpleName();

    private static final String YOU_TUBE_BASE_URL = "https://www.youtube.com/";
    private static final String PATH_WATCH = "watch";
    private static final String QUERY_PARAMETER_VIDEO = "v";


    // TODO Hello fellow Udacity reviewer! I need your help
    // Turning a URL into a String and then to a Uri seems strange.
    // What is the best way to do that?
    private static URL buildYouTubeVideoUrl(String movieId) {
        Uri builtUri = Uri.parse(YOU_TUBE_BASE_URL).buildUpon()
                .appendPath(PATH_WATCH)
                .appendQueryParameter(QUERY_PARAMETER_VIDEO, movieId)
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

    public static void showVideoOnYouTube(Context context, String videoKey) {
        URL videoURL = buildYouTubeVideoUrl(videoKey);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL.toString()));
        context.startActivity(intent);
    }
}
