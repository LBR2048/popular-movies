package leonardo.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity implements
        MovieDetailsFragment.OnFragmentInteractionListener{

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
    public void onMovieDetailsFavoritePressed(boolean favorite) {
        Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
    }

}
