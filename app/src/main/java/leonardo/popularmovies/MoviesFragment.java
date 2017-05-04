package leonardo.popularmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.asynctasks.FetchMostPopularMoviesTask;
import leonardo.popularmovies.asynctasks.FetchMoviesAsyncTaskListener;
import leonardo.popularmovies.asynctasks.FetchTopRatedMoviesTask;
import leonardo.popularmovies.data.MoviesUtils;
import leonardo.popularmovies.model.Movie;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMoviesFragmentInteractionListener}
 * interface.
 */
public class MoviesFragment extends Fragment {

    String TAG = ((Object) this).getClass().getSimpleName();

    public static final int MOVIES_TOP_RATED = 0;
    public static final int MOVIES_MOST_POPULAR = 1;
    public static final int MOVIES_FAVORITES = 2;

    public static final int COLUMN_COUNT_PORTRAIT = 2;
    public static final int COLUMN_COUNT_LANDSCAPE = 3;

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

    @SuppressWarnings("unused")
    public static MoviesFragment newInstance(int moviesSelection) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIES_SELECTION, moviesSelection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
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

            // Set number of columns showing movie posters depending on device orientation
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                mColumnCount = COLUMN_COUNT_PORTRAIT;
            }
            else{
                mColumnCount = COLUMN_COUNT_LANDSCAPE;
            }
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

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
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO Hello Udacity reviewer
        // I am loading the movies on every fragment resume to keep the favorites list
        // always in sync with the DB, but it reloads data from the web every time also
        // I don't think that is very smart, do you have any suggestions? Thanks =)

        // Load movies data
        loadMovies();
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
        void onMovieClicked(Movie item);
    }

    public void loadMovies() {
        switch (mMoviesSelection) {
            case MOVIES_TOP_RATED:
                loadTopRatedMovies();
                break;
            case MOVIES_MOST_POPULAR:
                loadMostPopularMovies();
                break;
            case MOVIES_FAVORITES:
                loadFavoriteMovies();
                break;
        }
    }

    // TODO Hello Udacity reviewer!
    // Could you suggest good material about Java callbacks, especially this type I have implemented?
    // I am still not confident about using them
    // Instead of creating a new FetchMoviesAsyncTaskListener,
    // I could have made MoviesFragment implement that interface,
    // but then the onResult callbacks from
    // FetchTopRatedMoviesTask and FetchMostPopularMoviesTask would get mixed up
    // Thanks =)
    public void loadTopRatedMovies() {
        new FetchTopRatedMoviesTask(new FetchMoviesAsyncTaskListener() {
            @Override
            public void onResult(List<Movie> movies) {
                mMoviesAdapter.setMoviesData(movies);
            }
        }).execute();
    }

    public void loadMostPopularMovies() {
        new FetchMostPopularMoviesTask(new FetchMoviesAsyncTaskListener() {
            @Override
            public void onResult(List<Movie> movies) {
                mMoviesAdapter.setMoviesData(movies);
            }
        }).execute();
    }

    public void loadFavoriteMovies() {
        mMoviesAdapter.setMoviesData(MoviesUtils.loadFavoriteMovies(getContext()));
        mMoviesAdapter.notifyDataSetChanged();
    }

}
