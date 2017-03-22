package leonardo.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnMoviesFragmentInteractionListener mListener;
    public MoviesRecyclerViewAdapter mMoviesAdapter;
    private ProgressBar mLoadingIndicator;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoviesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MoviesFragment newInstance(int columnCount) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
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

            // Set vertical separator lines
            RecyclerView.ItemDecoration itemDecorationVertical = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecorationVertical);

            // Set horizontal separator lines
            RecyclerView.ItemDecoration itemDecorationHorizontal = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
            recyclerView.addItemDecoration(itemDecorationHorizontal);

            // Set adapter
            mMoviesAdapter = new MoviesRecyclerViewAdapter(new ArrayList<Movie>(), mListener);
            recyclerView.setAdapter(mMoviesAdapter);

            // Load movies data
            loadTopMovies();
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
