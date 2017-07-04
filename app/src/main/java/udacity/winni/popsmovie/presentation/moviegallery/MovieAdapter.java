package udacity.winni.popsmovie.presentation.moviegallery;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickedListener onItemClickedListener;

    private List<MovieVM> movieVMs;

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        private OnItemClickedListener onItemClickedListener;

        @BindView(R.id.iv_movie_cover)
        ImageView ivMovieCover;

        public MovieViewHolder(Context context, View itemView,
            OnItemClickedListener onItemClickedListener) {
            super(itemView);
            this.context = context;
            this.onItemClickedListener = onItemClickedListener;
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final MovieVM movieVM) {
            String posterPath = movieVM.getPoster();
            if (posterPath.isEmpty()) {
                posterPath = null;
            }

            Picasso.with(context)
                .load(posterPath)
                .placeholder(R.drawable.no_image)
                .into(ivMovieCover);

            ivMovieCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(movieVM);
                    }
                }
            });
        }
    }

    public MovieAdapter(List<MovieVM> movies, OnItemClickedListener onItemClickedListener) {
        this.movieVMs = movies;
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(parent.getContext(), view, onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieViewHolder) holder).bindData(movieVMs.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieVMs.size();
    }

    public void addData(List<MovieVM> movies) {
        movieVMs.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearData() {
        movieVMs.clear();
        notifyDataSetChanged();
    }

    public void resetData(List<MovieVM> movies) {
        movieVMs.clear();
        movieVMs.addAll(movies);
        notifyDataSetChanged();
    }

    List<MovieVM> getData() {
        return movieVMs;
    }

    public interface OnItemClickedListener {

        void onItemClicked(MovieVM movie);
    }
}
