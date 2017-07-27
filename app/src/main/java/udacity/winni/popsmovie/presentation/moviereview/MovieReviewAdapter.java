package udacity.winni.popsmovie.presentation.moviereview;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.presentation.model.MovieReviewVM;
import udacity.winni.popsmovie.presentation.model.MovieVM;
import udacity.winni.popsmovie.presentation.moviegallery.MovieAdapter;

/**
 * Created by winniseptiani on 8/7/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MovieReviewAdapter.OnItemClickedListener onItemClickedListener;

    private List<MovieReviewVM> movieReviewVMs;

    public class ReviewViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_review_author)
        TextView tvReviewAuthor;

        @BindView(R.id.tv_review_content)
        TextView tvReviewContent;

        View itemView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final MovieReviewVM movieReviewVM) {

            tvReviewAuthor.setText(movieReviewVM.getAuthor());
            tvReviewContent.setText(movieReviewVM.getContent());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public MovieReviewAdapter(List<MovieReviewVM> movies,
        MovieReviewAdapter.OnItemClickedListener onItemClickedListener) {
        this.movieReviewVMs = movies;
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieReviewAdapter.ReviewViewHolder) holder)
            .bindData(movieReviewVMs.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return movieReviewVMs.size();
    }

    public void addData(List<MovieReviewVM> movieReviews) {
        movieReviewVMs.addAll(movieReviews);
        notifyDataSetChanged();
    }

    public void clearData() {
        movieReviewVMs.clear();
        notifyDataSetChanged();
    }

    public void resetData(List<MovieReviewVM> movieReviews) {
        movieReviewVMs.clear();
        movieReviewVMs.addAll(movieReviews);
        notifyDataSetChanged();
    }

    List<MovieReviewVM> getData() {
        return movieReviewVMs;
    }

    public interface OnItemClickedListener {

        void onItemClicked(MovieReviewVM movie);
    }
}
