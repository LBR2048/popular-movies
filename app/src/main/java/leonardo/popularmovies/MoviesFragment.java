package leonardo.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.utils.NetworkUtils;
import leonardo.popularmovies.utils.TMDBUtils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMoviesFragmentInteractionListener}
 * interface.
 */
public class MoviesFragment extends Fragment {

    public static final int MOVIES_TOP_RATED = 0;
    public static final int MOVIES_MOST_POPULAR = 1;
    public static final int MOVIES_FAVORITES = 2;


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_MOVIES_SELECTION = "movies-selection";

    private int mColumnCount;
    private int mMoviesSelection;

    private OnMoviesFragmentInteractionListener mListener;
    public MoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingIndicator;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoviesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MoviesFragment newInstance(int columnCount, int moviesSelection) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_MOVIES_SELECTION, moviesSelection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mMoviesSelection = getArguments().getInt(ARG_MOVIES_SELECTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
//        mLoadingIndicator = (ProgressBar) getActivity().findViewById(R.id.pb_loading_indicator);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Set activity title
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);

            // Set vertical separator lines
            RecyclerView.ItemDecoration itemDecorationVertical = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecorationVertical);

            // Set horizontal separator lines
            RecyclerView.ItemDecoration itemDecorationHorizontal = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
            recyclerView.addItemDecoration(itemDecorationHorizontal);

            // Set adapter
            mMoviesAdapter = new MoviesAdapter(new ArrayList<Movie>(), mListener);
            recyclerView.setAdapter(mMoviesAdapter);

            // Load movies data
            loadMovies();

        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoviesFragmentInteractionListener) {
            mListener = (OnMoviesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMoviesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMovieClicked(Movie item);
    }

    public void loadMovies() {
        new FetchMoviesTask().execute();
    }

    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            URL moviesRequestUrl;
            if (mMoviesSelection == MOVIES_TOP_RATED) {
                moviesRequestUrl = TMDBUtils.buildTopRatedMoviesUrl();
            } else {
                moviesRequestUrl = TMDBUtils.buildMostPopularMoviesUrl();
            }

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                List<Movie> movies = TMDBUtils
                        .getMoviesFromJsonString(getActivity().getApplicationContext(), jsonMoviesResponse);

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
                mMoviesAdapter.setMoviesData(movies);
//                showWeatherDataView();
//                mForecastAdapter.setWeatherData(weatherData);
            } else {
//                showErrorMessage();
            }
        }
    }

}
