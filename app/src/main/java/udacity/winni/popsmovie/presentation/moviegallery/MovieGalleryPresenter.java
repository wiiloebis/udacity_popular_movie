package udacity.winni.popsmovie.presentation.moviegallery;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.domain.usecase.GetFavoriteMovies;
import udacity.winni.popsmovie.domain.usecase.GetPopularMovies;
import udacity.winni.popsmovie.domain.usecase.GetTopRatedMovies;
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieGalleryPresenter implements MovieGalleryContract.Presenter {

    private static final int DEFAULT_PAGE = 1;

    private static final int MAX_PAGE = 1000;

    private MovieGalleryContract.View view;

    private GetPopularMovies getPopularMovies;

    private GetTopRatedMovies getTopRatedMovies;

    private GetFavoriteMovies getFavoriteMovies;

    private MovieMapper movieMapper;

    private int page = DEFAULT_PAGE;

    public MovieGalleryPresenter(MovieGalleryContract.View view,
        GetTopRatedMovies getTopRatedMovies,
        GetPopularMovies getPopularMovies,
        GetFavoriteMovies getFavoriteMovies,
        MovieMapper movieMapper) {
        this.view = view;
        this.getPopularMovies = getPopularMovies;
        this.getTopRatedMovies = getTopRatedMovies;
        this.getFavoriteMovies = getFavoriteMovies;
        this.movieMapper = movieMapper;
    }

    @Override
    public void getPopularMovies() {
        view.showLoadingBar();
        getPopularMovies.setPage(page);
        getPopularMovies.execute(new DisposableObserver<MovieList>() {

            @Override
            public void onNext(MovieList movieList) {
                List<MovieVM> movieVms = movieMapper.transform(movieList.getMovies());
                view.hideLoadingBar();
                if (movieVms != null) {
                    boolean loadMore = true;
                    if (movieList.getPage() > MAX_PAGE) {
                        loadMore = false;
                    }
                    view.onGetMoviesSuccess(movieVms, loadMore);
                    page = movieList.getPage() + 1;
                } else {
                    view.onGetMoviesFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMoviesFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void getTopRatedMovies() {
        view.showLoadingBar();
        getTopRatedMovies.setPage(page);
        getTopRatedMovies.execute(new DisposableObserver<MovieList>() {

            @Override
            public void onNext(MovieList movieList) {
                List<MovieVM> movieVms = movieMapper.transform(movieList.getMovies());
                view.hideLoadingBar();
                if (movieVms != null) {
                    boolean loadMore = true;
                    if (movieList.getPage() > MAX_PAGE) {
                        loadMore = false;
                    }
                    view.onGetMoviesSuccess(movieVms, loadMore);
                    page = movieList.getPage() + 1;
                } else {
                    view.onGetMoviesFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMoviesFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void getFavoriteMovies() {
        view.showLoadingBar();
        getFavoriteMovies.setPage(page);
        getFavoriteMovies.execute(new DisposableObserver<MovieList>() {

            @Override
            public void onNext(MovieList movieList) {
                List<MovieVM> movieVms = movieMapper.transform(movieList.getMovies());
                view.hideLoadingBar();
                if (movieVms != null) {
                    boolean loadMore = true;
                    if (movieList.getPage() > MAX_PAGE) {
                        loadMore = false;
                    }
                    view.onGetMoviesSuccess(movieVms, loadMore);
                    page = movieList.getPage() + 1;
                } else {
                    view.onGetMoviesFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMoviesFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void resetPage() {
        page = DEFAULT_PAGE;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getTopRatedMovies.unsubscribe();
        getPopularMovies.unsubscribe();
        getFavoriteMovies.unsubscribe();
    }
}
