package leonardo.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import leonardo.popularmovies.MovieDetailsFragment.OnVideoInteractionListener;
import leonardo.popularmovies.model.Video;

/**
 * Created by unity on 21/04/17.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private List<Video> mVideos;
    private final OnVideoInteractionListener mListener;

    public VideosAdapter(List<Video> videos, OnVideoInteractionListener listener) {
        mVideos = videos;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_details_video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mVideo = mVideos.get(position);

//        Context context = holder.mView.getContext();
//        String name = mVideos.get(position).getName();
//        Picasso.with(context)
//                .load(name)
//                .resize(500, 1000)
//                .centerInside()
//                .placeholder(context.getResources().getDrawable(R.drawable.ic_movie_cyan_600_24dp))
//                .into(holder.mPosterView);

        holder.mName.setText(mVideos.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onVideoClicked(holder.mVideo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setVideosData(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public Video mVideo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.tv_video_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
