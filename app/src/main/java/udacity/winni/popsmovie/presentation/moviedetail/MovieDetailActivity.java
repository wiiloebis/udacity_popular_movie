package udacity.winni.popsmovie.presentation.moviedetail;

import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.ApplicationComponent;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.base.EndlessScrollListener;
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.mapper.MovieVideoMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;
import udacity.winni.popsmovie.presentation.model.MovieTrailerVM;
import udacity.winni.popsmovie.presentation.moviereview.MovieReviewActivity;

/**
 * Created by winniseptiani on 6/15/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View,
    MovieTrailerAdapter.OnItemClickedListener {

    private static final int DIV_FACTOR = 10;

    public static String MOVIE_ID = "MOVIE_ID";

    public static String MOVIE = "MOVIE";

    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;

    @BindView(R.id.iv_movie_cover)
    ImageView ivMoviePoster;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_movie_duration)
    TextView tvMovieDuration;

    @BindView(R.id.tv_rating)
    TextView tvRating;

    @BindView(R.id.tv_movie_desc)
    TextView tvMovieOverview;

    @BindView(R.id.rv_trailer)
    RecyclerView rvTrailer;

    @BindView(R.id.layout_success)
    RelativeLayout rlMovieDetailSuccess;

    @BindView(R.id.layout_failed)
    TextView rlMovieDetailFailed;

    @BindView(R.id.cb_favorit_mark)
    CheckBox cbFavoritMark;

    private MovieTrailerAdapter movieTrailerAdapter;

    private LinearLayoutManager linearLayoutManager;

    private MovieDetailPresenter movieDetailPresenter;

    private ProgressDialog progressDialog;

    private MovieVM movie;

    private boolean loadMore = true;

    private long movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setScreenActionBar();
        setCheckboxView();
        setTrailerAdapter();
        movieDetailPresenter = new MovieDetailPresenter(this,
            ApplicationComponent.provideMovieDetail(), ApplicationComponent.provideMovieTrailers(),
            new MovieMapper(), new MovieVideoMapper());
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE)) {
            getMovieDetailFromId();
        } else {
            movie = savedInstanceState.getParcelable(MOVIE);
            onGetMovieDetailSuccess(movie);
        }
    }

    private void setTrailerAdapter() {
        movieTrailerAdapter = new MovieTrailerAdapter(new ArrayList<>(), this);
        linearLayoutManager = new LinearLayoutManager(this);
        rvTrailer.setAdapter(movieTrailerAdapter);
        rvTrailer.setLayoutManager(linearLayoutManager);
        rvTrailer.addOnScrollListener(new EndlessScrollListener(linearLayoutManager,
            EndlessScrollListener.ITEM_VISIBLE_TRESHOLD) {
            @Override
            public void onLoadMore() {
                if (loadMore) {
                    movieDetailPresenter.getMovieTrailer(movieId);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MOVIE, movie);
        super.onSaveInstanceState(outState);
    }

    private void setScreenActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.movie_detail));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setCheckboxView() {
        cbFavoritMark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbFavoritMark.setText(getString(R.string.unmark_as_favorite));
                } else {
                    cbFavoritMark.setText(getString(R.string.mark_as_favorite));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            case R.id.review:
                Intent intent = new Intent(this, MovieReviewActivity.class);
                intent.putExtra(MovieReviewActivity.MOVIE_ID, movieId);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMovieDetailFromId() {
        Intent intent = getIntent();
        movieId = intent.getLongExtra(MOVIE_ID, 0);
        movieDetailPresenter.getMovieDetail(movieId);
    }

    @Override
    public void onGetMovieDetailSuccess(MovieVM movieVM) {
        movie = movieVM;
        rlMovieDetailFailed.setVisibility(View.GONE);
        rlMovieDetailSuccess.setVisibility(View.VISIBLE);
        tvMovieTitle.setText(movieVM.getOriginalTitle());
        displayReleaseDate(movieVM.getReleaseDate());
        tvMovieDuration.setText(movieVM.getRuntime() + getString(R.string.min));
        tvMovieOverview.setText(movieVM.getOverview());
        displayPopularity(movieVM.getVoteAverage());
        displayPoster(movieVM.getPoster());
    }

    private void displayReleaseDate(String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) {
            releaseDate = getString(R.string.no_date);
        }
        tvReleaseDate.setText(releaseDate);
    }

    private void displayPopularity(double voteAverage) {
        StringBuffer popularityStringBuffer = new StringBuffer("");
        popularityStringBuffer.append(String.format("%.1f", voteAverage));
        popularityStringBuffer.append("/");
        popularityStringBuffer.append(DIV_FACTOR);
        tvRating.setText(popularityStringBuffer.toString());
    }

    private void displayPoster(String posterPath) {
        if (posterPath.isEmpty()) {
            posterPath = null;
        }
        Picasso.with(this).load(posterPath).placeholder(R.drawable.no_image).into(ivMoviePoster);
    }

    @Override
    public void onGetMovieDetailFailed() {
        rlMovieDetailFailed.setVisibility(View.VISIBLE);
        rlMovieDetailSuccess.setVisibility(View.GONE);
    }

    @Override
    public void onGetMovieTrailersSuccess(List<MovieTrailerVM> movieTrailerVMs) {
        movieTrailerAdapter.resetData(movieTrailerVMs);
    }

    @Override
    public void onGetMovieTrailersFailed() {

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
    public void onItemClicked(MovieTrailerVM movieTrailerVM) {

    }

    public void playVideo(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
