package udacity.winni.popsmovie.presentation.moviedetail;

import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.ApplicationComponent;
import udacity.winni.popsmovie.R;
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/15/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

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

    private MovieDetailPresenter movieDetailPresenter;

    private ProgressDialog progressDialog;

    private MovieVM movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setScreenActionBar();
        setCheckboxView();
        movieDetailPresenter = new MovieDetailPresenter(this,
            ApplicationComponent.provideMovieDetail(), new MovieMapper());
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE)) {
            getMovieDetailFromId();
        } else {
            movie = savedInstanceState.getParcelable(MOVIE);
            onGetMovieDetailSuccess(movie);
        }
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
            case R.id.refresh:
                if (movieDetailPresenter != null) {
                    getMovieDetailFromId();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMovieDetailFromId() {
        Intent intent = getIntent();
        long movieId = intent.getLongExtra(MOVIE_ID, 0);
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
}
