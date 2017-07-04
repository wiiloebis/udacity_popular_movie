package udacity.winni.popsmovie.presentation.moviedetail;

import io.reactivex.observers.DisposableObserver;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.domain.usecase.GetMovieDetail;
import udacity.winni.popsmovie.presentation.mapper.MovieMapper;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View view;

    private GetMovieDetail getMovieDetail;

    private MovieMapper movieMapper;

    public MovieDetailPresenter(MovieDetailContract.View view, GetMovieDetail getMovieDetail,
        MovieMapper movieMapper) {
        this.view = view;
        this.getMovieDetail = getMovieDetail;
        this.movieMapper = movieMapper;
    }

    @Override
    public void getMovieDetail(long movieId) {
        view.showLoadingBar();
        getMovieDetail.setMovieId(movieId);
        getMovieDetail.execute(new DisposableObserver<Movie>() {

            @Override
            public void onNext(Movie movie) {
                MovieVM movieVM = movieMapper.transform(movie);
                view.hideLoadingBar();
                if (movieVM != null) {
                    view.onGetMovieDetailSuccess(movieVM);
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
    }
}
