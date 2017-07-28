package udacity.winni.popsmovie.presentation.moviegallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
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
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;
import udacity.winni.popsmovie.presentation.moviedetail.MovieDetailActivity;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieGalleryActivity extends AppCompatActivity implements MovieGalleryContract.View,
    MovieAdapter.OnItemClickedListener {

    public static String MOVIES = "MOVIES";

    public static String SCREEN_TITLE = "SCREEN_TITLE";

    public static String CURRENT_PAGE = "CURRENT_PAGE";

    public static String GALLERY_MODE = "GALLERY_MODE";

    private static final int POPULAR_MODE = 1;

    private static final int TOP_RATED_MODE = 2;

    private static final int FAVORITE_MODE = 3;

    private static final int NUMBER_OF_COLUMN = 2;

    private MovieGalleryPresenter movieGalleryPresenter;

    private MovieAdapter movieAdapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @BindView(R.id.layout_failed)
    TextView tvFailMessage;

    private GridLayoutManager mGridLayoutManager;

    private ProgressDialog progressDialog;

    private ActionBar actionBar;

    private List<MovieVM> movies;

    private boolean loadMore = true;

    private int galleryMode = POPULAR_MODE;

    private String screenTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);
        ButterKnife.bind(this);
        movieGalleryPresenter = new MovieGalleryPresenter(this,
            ApplicationComponent.provideGetHighRatedMovies(),
            ApplicationComponent.provideGetPopularMovies(),
            ApplicationComponent.provideGetFavoriteMovies(),
            new MovieMapper());
        setMovieAdapter();
        handleDataFromState(savedInstanceState);
        setScreenActionBar();
    }

    private void handleDataFromState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIES)) {
            movieGalleryPresenter.getPopularMovies();
            screenTitle = getString(R.string.pop_movies_title);
        } else {
            movies = savedInstanceState.getParcelableArrayList(MOVIES);
            movieAdapter.resetData(movies);
            screenTitle = savedInstanceState.getString(SCREEN_TITLE);
            int currentPage = savedInstanceState.getInt(CURRENT_PAGE);
            movieGalleryPresenter.setPage(currentPage);
            galleryMode = savedInstanceState.getInt(GALLERY_MODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (galleryMode == FAVORITE_MODE) {
            if (requestCode == MovieDetailActivity.OPEN_DETAIL_ACTVITY
                && resultCode == MovieDetailActivity.RESULT_OK) {
                movies.clear();
                if (movieGalleryPresenter != null) {
                    movieAdapter.clearData();
                    movieGalleryPresenter.resetPage();
                    movieGalleryPresenter.getFavoriteMovies();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES, new ArrayList<>(movieAdapter.getData()));
        outState.putString(SCREEN_TITLE, screenTitle);
        outState.putInt(CURRENT_PAGE, movieGalleryPresenter.getCurrentPage());
        outState.putInt(GALLERY_MODE, galleryMode);
        super.onSaveInstanceState(outState);
    }

    private void setScreenActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(screenTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.popular:
                screenTitle = getString(R.string.pop_movies_title);
                actionBar.setTitle(screenTitle);
                galleryMode = POPULAR_MODE;
                movies.clear();
                if (movieGalleryPresenter != null) {
                    movieAdapter.clearData();
                    movieGalleryPresenter.resetPage();
                    movieGalleryPresenter.getPopularMovies();
                }
                return true;
            case R.id.toprated:
                galleryMode = TOP_RATED_MODE;
                screenTitle = getString(R.string.top_rated_movies_title);
                actionBar.setTitle(screenTitle);
                movies.clear();
                if (movieGalleryPresenter != null) {
                    movieAdapter.clearData();
                    movieGalleryPresenter.resetPage();
                    movieGalleryPresenter.getTopRatedMovies();
                }
                return true;
            case R.id.favorite:
                galleryMode = FAVORITE_MODE;
                screenTitle = getString(R.string.favorite_movies_title);
                actionBar.setTitle(screenTitle);
                movies.clear();
                if (movieGalleryPresenter != null) {
                    movieAdapter.clearData();
                    movieGalleryPresenter.resetPage();
                    movieGalleryPresenter.getFavoriteMovies();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMovieAdapter() {
        movieAdapter = new MovieAdapter(new ArrayList<>(), this);
        mGridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMN,
            GridLayoutManager.VERTICAL, false);
        rvMovie.setAdapter(movieAdapter);
        rvMovie.setLayoutManager(mGridLayoutManager);
        rvMovie.addOnScrollListener(new EndlessScrollListener(mGridLayoutManager,
            EndlessScrollListener.ITEM_VISIBLE_TRESHOLD) {
            @Override
            public void onLoadMore() {
                if (loadMore && (galleryMode == POPULAR_MODE)) {
                    movieGalleryPresenter.getPopularMovies();
                } else if (loadMore && (galleryMode == TOP_RATED_MODE)) {
                    movieGalleryPresenter.getTopRatedMovies();
                } else if (loadMore && (galleryMode == TOP_RATED_MODE)) {
                    movieGalleryPresenter.getFavoriteMovies();
                }
            }
        });
    }

    @Override
    public void onGetMoviesSuccess(List<MovieVM> movies, boolean loadMore) {
        this.movies = movies;
        rvMovie.setVisibility(View.VISIBLE);
        tvFailMessage.setVisibility(View.GONE);
        movieAdapter.addData(movies);
        this.loadMore = loadMore;
    }

    @Override
    public void onGetMoviesFailed() {
        rvMovie.setVisibility(View.GONE);
        tvFailMessage.setVisibility(View.VISIBLE);
        tvFailMessage.setText(getString(R.string.fetch_movie_failed));
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
    public void onItemClicked(MovieVM movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
        startActivityForResult(intent, MovieDetailActivity.OPEN_DETAIL_ACTVITY);
    }
}
