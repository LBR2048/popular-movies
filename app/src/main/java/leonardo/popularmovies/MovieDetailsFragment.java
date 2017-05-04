package leonardo.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.asynctasks.FetchReviewsTask;
import leonardo.popularmovies.asynctasks.FetchVideosAsyncTaskListener;
import leonardo.popularmovies.asynctasks.FetchVideosTask;
import leonardo.popularmovies.data.MoviesUtils;
import leonardo.popularmovies.model.Movie;
import leonardo.popularmovies.model.Review;
import leonardo.popularmovies.model.Video;
import leonardo.popularmovies.utils.FetchReviewsAsyncTaskListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFavoriteInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Movie mMovie;

    private OnFavoriteInteractionListener mFavoriteListener;
    private OnVideoInteractionListener mVideoListener;
    private VideosAdapter mVideosAdapter;
    private RecyclerView mVideosRecyclerView;

    private ReviewsAdapter mReviewsAdapter;
    private RecyclerView mReviewsRecyclerView;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movie Parameter 1.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, Parcels.wrap(movie));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable(ARG_PARAM1));
        }
        setHasOptionsMenu(true  );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView posterView = (ImageView) getActivity().findViewById(R.id.iv_movie_details_poster);
        TextView overviewView = (TextView) getActivity().findViewById(R.id.tv_movie_details_overview);
        TextView ratingView = (TextView) getActivity().findViewById(R.id.tv_movie_details_rating);
        TextView releaseDateView = (TextView) getActivity().findViewById(R.id.tv_movie_details_release_date);
        mVideosRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_movie_videos);
        mReviewsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_movie_reviews);

        // Set activity title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mMovie.getTitle());

        String posterPath = mMovie.getPoster();
        Picasso.with(getContext())
                .load(posterPath)
                .placeholder(getContext().getResources().getDrawable(R.drawable.ic_movie_cyan_600_24dp))
                .into(posterView);

        overviewView.setText(mMovie.getOverview());
        ratingView.setText(mMovie.getRating());
        releaseDateView.setText(mMovie.getReleaseDate());

        // Setup videos RecyclerView
        mVideosAdapter = new VideosAdapter(new ArrayList<Video>(), mVideoListener);
        mVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mVideosRecyclerView.setAdapter(mVideosAdapter);

        // Setup reviews RecyclerView
        mReviewsAdapter = new ReviewsAdapter(new ArrayList<Review>(), null);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

        loadVideos();
        loadReviews();
    }

    private void loadReviews() {
        new FetchReviewsTask(new FetchReviewsAsyncTaskListener() {
            @Override
            public void onResult(List<Review> reviews) {
                mReviewsAdapter.setReviewsData(reviews);
            }
        }).execute(mMovie);
    }

    private void loadVideos() {
        new FetchVideosTask(new FetchVideosAsyncTaskListener() {
            @Override
            public void onResult(List<Video> videos) {
                mVideosAdapter.setVideosData(videos);
            }
        }).execute(mMovie);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_details, menu);
        if (MoviesUtils.checkIfMovieIsFavorite(getContext(), mMovie)) {
            showMovieAsFavorite(menu);
        } else {
            showMovieAsNotFavorite(menu);
        }
    }

    private void showMovieAsFavorite(Menu menu) {
        showMovieAsFavorite(menu.getItem(0));
    }

    private void showMovieAsNotFavorite(Menu menu) {
        showMovieAsNotFavorite(menu.getItem(0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                onFavoriteClicked(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMovieAsFavorite(MenuItem item) {
        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_18dp));
    }

    public void showMovieAsNotFavorite(MenuItem item) {
        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_18dp));
    }

    public void onFavoriteClicked(MenuItem menuItem) {
        if (MoviesUtils.checkIfMovieIsFavorite(getContext(), mMovie)) {
            MoviesUtils.deleteFavoriteMovie(getContext(), mMovie);
            showMovieAsNotFavorite(menuItem);
        } else {
            MoviesUtils.addFavoriteMovie(getContext(), mMovie);
            showMovieAsFavorite(menuItem);
        }

        if (mFavoriteListener != null) {
            mFavoriteListener.onFavoriteClicked(mMovie);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFavoriteInteractionListener) {
            mFavoriteListener = (OnFavoriteInteractionListener) context;
            mVideoListener = (OnVideoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFavoriteListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFavoriteInteractionListener {
        void onFavoriteClicked(Movie movie);
    }

    public interface OnVideoInteractionListener {
        void onVideoClicked(Video video);
    }

}
