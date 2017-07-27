package udacity.winni.popsmovie.presentation.moviedetail;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.domain.usecase.AddFavoriteMovie;
import udacity.winni.popsmovie.domain.usecase.CheckIfMovieFavorited;
import udacity.winni.popsmovie.domain.usecase.GetMovieDetail;
import udacity.winni.popsmovie.domain.usecase.GetMovieTrailers;
import udacity.winni.popsmovie.domain.usecase.RemoveFavoriteMovie;
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.mapper.MovieVideoMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;
import udacity.winni.popsmovie.presentation.model.MovieTrailerVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View view;

    private GetMovieDetail getMovieDetail;

    private GetMovieTrailers getMovieTrailers;

    private AddFavoriteMovie addFavoriteMovie;

    private CheckIfMovieFavorited checkIfMovieFavorited;

    private RemoveFavoriteMovie removeFavoriteMovie;

    private MovieMapper movieMapper;

    private MovieVideoMapper movieVideoMapper;

    public MovieDetailPresenter(MovieDetailContract.View view,
        GetMovieDetail getMovieDetail,
        GetMovieTrailers getMovieTrailers,
        AddFavoriteMovie addFavoriteMovie,
        RemoveFavoriteMovie removeFavoriteMovie,
        CheckIfMovieFavorited checkIfMovieFavorited,
        MovieMapper movieMapper,
        MovieVideoMapper movieVideoMapper) {
        this.view = view;
        this.getMovieDetail = getMovieDetail;
        this.getMovieTrailers = getMovieTrailers;
        this.addFavoriteMovie = addFavoriteMovie;
        this.removeFavoriteMovie = removeFavoriteMovie;
        this.checkIfMovieFavorited = checkIfMovieFavorited;
        this.movieMapper = movieMapper;
        this.movieVideoMapper = movieVideoMapper;
    }

    @Override
    public void getMovieDetail(long movieId) {
        view.showLoadingBar();
        getMovieDetail.setMovieId(movieId);
        getMovieDetail.execute(new DisposableObserver<Movie>() {

            @Override
            public void onNext(Movie movie) {
                MovieVM movieVM = movieMapper.transform(movie);
                if (movieVM != null) {
                    view.onGetMovieDetailSuccess(movieVM);
                    getMovieTrailer(movieId);
                } else {
                    view.onGetMovieDetailFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMovieDetailFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void getMovieTrailer(long id) {
        getMovieTrailers.setMovieId(id);
        getMovieTrailers.execute(new DisposableObserver<List<Video>>() {

            @Override
            public void onNext(List<Video> videos) {
                List<MovieTrailerVM> movieTrailerVMs = movieVideoMapper.transform(videos);
                if (movieTrailerVMs != null) {
                    view.onGetMovieTrailersSuccess(movieTrailerVMs);
                } else {
                    view.onGetMovieTrailersFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMovieTrailersFailed();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void addFavoriteMovie(MovieVM movieVM) {
        view.showLoadingBar();
        Movie movie = new Movie();
        movie.setId(movieVM.getId());
        movie.setTitle(movieVM.getTitle());
        movie.setOriginalTitle(movieVM.getOriginalTitle());
        movie.setPosterPath(movieVM.getPoster());
        addFavoriteMovie.setMovieData(movie);
        addFavoriteMovie.execute(new DisposableObserver<Boolean>() {

            @Override
            public void onNext(Boolean success) {
                view.onAddFavoriteMovieSuccess(success);
            }

            @Override
            public void onError(Throwable e) {
                view.onAddFavoriteMovieFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void removeFavoriteMovie(long movieId) {
        view.showLoadingBar();
        removeFavoriteMovie.setMovieId(movieId);
        removeFavoriteMovie.execute(new DisposableObserver<Boolean>() {

            @Override
            public void onNext(Boolean success) {
                view.onRemoveFavoriteMovieSuccess(success);
            }

            @Override
            public void onError(Throwable e) {
                view.onRemoveFavoriteMovieFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void checkIfMovieFavorited(long movieId) {
        view.showLoadingBar();
        checkIfMovieFavorited.setMovieId(movieId);
        checkIfMovieFavorited.execute(new DisposableObserver<Boolean>() {

            @Override
            public void onNext(Boolean success) {
                view.onCheckIfMovieFavoritedSuccess(success);
            }

            @Override
            public void onError(Throwable e) {
                view.onCheckIfMovieFavoritedFailed();
                view.hideLoadingBar();
            }

            @Override
            public void onComplete() {
                view.hideLoadingBar();
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getMovieDetail.unsubscribe();
        getMovieTrailers.unsubscribe();
        addFavoriteMovie.unsubscribe();
        removeFavoriteMovie.unsubscribe();
        checkIfMovieFavorited.unsubscribe();
    }
}
