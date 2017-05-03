package leonardo.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import leonardo.popularmovies.MovieDetailsFragment.OnVideoInteractionListener;
import leonardo.popularmovies.model.Review;

/**
 * Created by unity on 21/04/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> mReviews;
    private final OnVideoInteractionListener mListener;

    public ReviewsAdapter(List<Review> videos, OnVideoInteractionListener listener) {
        mReviews = videos;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_details_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mReview = mReviews.get(position);

//        Context context = holder.mView.getContext();
//        String name = mReviews.get(position).getName();
//        Picasso.with(context)
//                .load(name)
//                .resize(500, 1000)
//                .centerInside()
//                .placeholder(context.getResources().getDrawable(R.drawable.ic_movie_cyan_600_24dp))
//                .into(holder.mPosterView);

        holder.mContent.setText(mReviews.get(position).getContent());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onVideoClicked(holder.mReview);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void setReviewsData(List<Review> videos) {
        mReviews = videos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContent;
        public Review mReview;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContent = (TextView) view.findViewById(R.id.tv_review_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContent.getText() + "'";
        }
    }
}
