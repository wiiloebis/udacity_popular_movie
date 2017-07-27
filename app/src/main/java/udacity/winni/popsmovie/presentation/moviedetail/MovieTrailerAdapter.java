package udacity.winni.popsmovie.presentation.moviedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.presentation.model.MovieTrailerVM;
import udacity.winni.popsmovie.presentation.moviegallery.MovieAdapter;

/**
 * Created by winniseptiani on 4/7/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MovieTrailerAdapter.OnItemClickedListener onItemClickedListener;

    private List<MovieTrailerVM> movieTrailerVMs;

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trailer_title)
        TextView tvTrailerTitle;

        private View itemView;

        private MovieTrailerAdapter.OnItemClickedListener onItemClickedListener;

        public TrailerViewHolder(View itemView,
            MovieTrailerAdapter.OnItemClickedListener onItemClickedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
            this.onItemClickedListener = onItemClickedListener;
        }

        public void bindData(final MovieTrailerVM movieTrailerVM) {
            tvTrailerTitle.setText(movieTrailerVM.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickedListener.onItemClicked(movieTrailerVM);
                }
            });
        }
    }

    public MovieTrailerAdapter(List<MovieTrailerVM> trailers,
        MovieTrailerAdapter.OnItemClickedListener onItemClickedListener) {
        this.movieTrailerVMs = trailers;
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_trailer, parent, false);
        return new MovieTrailerAdapter.TrailerViewHolder(view, onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieTrailerAdapter.TrailerViewHolder) holder)
            .bindData(movieTrailerVMs.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieTrailerVMs.size();
    }

    public void addData(List<MovieTrailerVM> trailers) {
        movieTrailerVMs.addAll(trailers);
        notifyDataSetChanged();
    }

    public void clearData() {
        movieTrailerVMs.clear();
        notifyDataSetChanged();
    }

    public void resetData(List<MovieTrailerVM> trailers) {
        movieTrailerVMs.clear();
        movieTrailerVMs.addAll(trailers);
        notifyDataSetChanged();
    }

    List<MovieTrailerVM> getData() {
        return movieTrailerVMs;
    }

    public interface OnItemClickedListener {

        void onItemClicked(MovieTrailerVM movieTrailerVM);
    }
}
