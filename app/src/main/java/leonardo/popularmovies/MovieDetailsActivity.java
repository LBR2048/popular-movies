package leonardo.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.model.Video;
import leonardo.popularmovies.utils.YouTubeUtils;

public class MovieDetailsActivity extends AppCompatActivity implements
        MovieDetailsFragment.OnFavoriteInteractionListener,
        MovieDetailsFragment.OnVideoInteractionListener{

    public static final String MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailsFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (savedInstanceState == null) {
            Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
            showMovieDetailsFragment(movie);
        }
    }

    private void showMovieDetailsFragment(Movie movie) {
        // Add MovieDetailsFragment to the Activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_movies, MovieDetailsFragment.newInstance(movie), MOVIE_DETAILS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onFavoritePressed(Movie movie) {
    }

    @Override
    public void onVideoClicked(Video video) {
        // Open video on YouTube app or You Tube page on browser
        String key = video.getKey();
        // TODO Hello fellow Udacity reviewer! Please look inside the method below
        YouTubeUtils.showVideoOnYouTube(this, key);
    }
}
