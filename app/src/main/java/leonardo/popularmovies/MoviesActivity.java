package leonardo.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class MoviesActivity extends MyAppCompatActivity implements
        MoviesFragment.OnMoviesFragmentInteractionListener,
        MovieDetailsFragment.OnFragmentInteractionListener{

    public static final String MOVIES_FRAGMENT_TAG = "MoviesFragmentTag";
    public static final String MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailsFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (savedInstanceState == null) {
            showMoviesFragment();
        }
    }

    private void showMoviesFragment() {
        // Add MoviesFragment to the Activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_movies, MoviesFragment.newInstance(2), MOVIES_FRAGMENT_TAG)
                .commit();
    }

    private void showMovieDetailsFragment(Movie movie) {
        // Add MovieDetailsFragment to the Activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_movies, MovieDetailsFragment.newInstance(movie), MOVIE_DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        showMovieDetailsFragment(movie);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
