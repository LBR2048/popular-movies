package leonardo.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import leonardo.popularmovies.MoviesFragment.OnMoviesFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Movie} and makes a call to the
 * specified {@link OnMoviesFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private final OnMoviesFragmentInteractionListener mListener;

    public MoviesRecyclerViewAdapter(List<Movie> movies, OnMoviesFragmentInteractionListener listener) {
        mMovies = movies;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mMovies.get(position);

        Context context = holder.mView.getContext();
        String posterPath = mMovies.get(position).getPoster();
        Picasso.with(context)
                .load(posterPath)
                .resize(500, 1000)
                .centerInside()
                .placeholder(context.getResources().getDrawable(R.drawable.ic_movie_cyan_600_24dp))
                .into(holder.mPosterView);

        holder.mTitle.setText(mMovies.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMovieClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMoviesData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPosterView;
        public final TextView mTitle;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPosterView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            mTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

}
