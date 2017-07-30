package udacity.winni.popsmovie.presentation.moviereview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.ApplicationComponent;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.base.EndlessScrollListener;
import udacity.winni.popsmovie.presentation.mapper.MovieReviewMapper;
import udacity.winni.popsmovie.presentation.model.MovieReviewVM;

/**
 * Created by winniseptiani on 7/9/17.
 */

public class MovieReviewActivity extends AppCompatActivity implements MovieReviewContract.View,
    MovieReviewAdapter.OnItemClickedListener {

    public static String MOVIE_ID = "MOVIE_ID";

    public static String MOVIES_REVIEW = "MOVIES_REVIEW";

    public static String CURRENT_PAGE = "CURRENT_PAGE";

    public static String LOAD_MORE = "LOAD_MORE";

    private MovieReviewPresenter movieReviewPresenter;

    private MovieReviewAdapter movieReviewAdapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovieReview;

    @BindView(R.id.layout_failed)
    TextView tvFailMessage;

    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog progressDialog;

    private ActionBar actionBar;

    private List<MovieReviewVM> movieReviews;

    private boolean loadMore = false;

    private long movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);
        ButterKnife.bind(this);
        movieReviewPresenter = new MovieReviewPresenter(this,
            ApplicationComponent.provideGetMovieReviews(), new MovieReviewMapper());
        setMovieAdapter();
        handleDataFromState(savedInstanceState);
        setScreenActionBar();
    }

    private void handleDataFromState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIES_REVIEW)) {
            getMovieReviewsFromId();
        } else {
            movieReviews = savedInstanceState.getParcelableArrayList(MOVIES_REVIEW);
            movieReviewAdapter.resetData(movieReviews);
            int currentPage = savedInstanceState.getInt(CURRENT_PAGE);
            movieId = savedInstanceState.getLong(MOVIE_ID);
            loadMore = savedInstanceState.getBoolean(LOAD_MORE);
            movieReviewPresenter.setPage(currentPage);
        }
    }

    private void getMovieReviewsFromId() {
        Intent intent = getIntent();
        movieId = intent.getLongExtra(MOVIE_ID, 0);
        movieReviewPresenter.getMovieReviews(movieId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState
            .putParcelableArrayList(MOVIES_REVIEW, new ArrayList<>(movieReviewAdapter.getData()));
        outState.putInt(CURRENT_PAGE, movieReviewPresenter.getCurrentPage());
        outState.putLong(MOVIE_ID, movieId);
        outState.putBoolean(LOAD_MORE, loadMore);
        super.onSaveInstanceState(outState);
    }

    private void setScreenActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(getString(R.string.movie_review));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMovieAdapter() {
        movieReviewAdapter = new MovieReviewAdapter(new ArrayList<>(), this);
        linearLayoutManager = new LinearLayoutManager(this);
        rvMovieReview.setAdapter(movieReviewAdapter);
        rvMovieReview.setLayoutManager(linearLayoutManager);
        rvMovieReview.addOnScrollListener(new EndlessScrollListener(linearLayoutManager,
            EndlessScrollListener.ITEM_VISIBLE_TRESHOLD) {
            @Override
            public void onLoadMore() {
                if (loadMore) {
                    movieReviewPresenter.getMovieReviews(movieId);
                }
            }
        });
    }

    @Override
    public void onGetMovieReviewsSuccess(List<MovieReviewVM> movieReviews, boolean loadMore) {
        this.movieReviews = movieReviews;
        rvMovieReview.setVisibility(View.VISIBLE);
        tvFailMessage.setVisibility(View.GONE);
        movieReviewAdapter.addData(this.movieReviews);
        this.loadMore = loadMore;
    }

    @Override
    public void onGetMovieReviewsFailed() {
        rvMovieReview.setVisibility(View.GONE);
        tvFailMessage.setVisibility(View.VISIBLE);
        tvFailMessage.setText(getString(R.string.fetch_reviews_failed));
    }

    @Override
    public void showLoadingBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    @Override
    public void hideLoadingBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onItemClicked(MovieReviewVM movie) {

    }
}
