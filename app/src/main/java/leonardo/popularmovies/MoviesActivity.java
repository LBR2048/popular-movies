package leonardo.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

import leonardo.popularmovies.utils.NetworkUtils;
import leonardo.popularmovies.utils.TMDBUtils;

public class MoviesActivity extends AppCompatActivity implements
        MoviesFragment.OnMoviesFragmentInteractionListener,
        MovieDetailsFragment.OnFragmentInteractionListener{

    public static final String MOVIES_FRAGMENT_TAG = "MoviesFragmentTag";
    public static final String MOVIE_DETAILS_FRAGMENT_TAG = "MovieDetailsFragmentTag";

    private MoviesFragment mMoviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (savedInstanceState == null) {
            showMoviesFragment();
        }

        mMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(MOVIES_FRAGMENT_TAG);
        loadTopMovies();
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
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onMovieClicked(Movie movie) {
        Toast.makeText(getApplicationContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();

        showMovieDetailsFragment(movie);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void loadTopMovies() {
        new FetchTopMoviesTask().execute();
    }

    private class FetchTopMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            URL weatherRequestUrl = NetworkUtils.buildTopMoviesUrl();

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                List<Movie> movies = TMDBUtils
                        .getMoviesFromJsonString(getApplicationContext(), jsonMoviesResponse);

                return movies;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMoviesFragment.mMoviesAdapter.setMoviesData(movies);
//                showWeatherDataView();
//                mForecastAdapter.setWeatherData(weatherData);
            } else {
//                showErrorMessage();
            }
        }
    }
}
