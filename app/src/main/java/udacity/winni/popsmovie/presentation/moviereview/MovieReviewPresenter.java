package udacity.winni.popsmovie.presentation.moviereview;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.domain.usecase.GetMovieReviews;
import udacity.winni.popsmovie.presentation.mapper.MovieReviewMapper;
import udacity.winni.popsmovie.presentation.model.MovieReviewVM;

/**
 * Created by winniseptiani on 7/11/17.
 */

public class MovieReviewPresenter implements MovieReviewContract.Presenter {

    private static final int DEFAULT_PAGE = 1;

    private MovieReviewContract.View view;

    private MovieReviewMapper movieReviewMapper;

    private GetMovieReviews getMovieReviews;

    private int page = DEFAULT_PAGE;

    public MovieReviewPresenter(MovieReviewContract.View view, GetMovieReviews getMovieReviews,
        MovieReviewMapper movieReviewMapper) {
        this.view = view;
        this.getMovieReviews = getMovieReviews;
        this.movieReviewMapper = movieReviewMapper;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getMovieReviews.unsubscribe();
    }

    @Override
    public void getMovieReviews(long id) {
        view.showLoadingBar();
        getMovieReviews.setId(id);
        getMovieReviews.setPage(page);
        getMovieReviews.execute(new DisposableObserver<MovieReviewList>() {

            @Override
            public void onNext(MovieReviewList movieReviewList) {
                List<MovieReviewVM> movieReviewVms = movieReviewMapper
                    .transform(movieReviewList.getMovieReviews());
                view.hideLoadingBar();
                if (movieReviewVms != null) {
                    boolean loadMore = true;
                    if (movieReviewList.getPage() < movieReviewList.getTotalPage()) {
                        loadMore = false;
                    }

                    view.onGetMovieReviewsSuccess(movieReviewVms, loadMore);
                    page = movieReviewList.getPage() + 1;
                } else {
                    view.onGetMovieReviewsFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onGetMovieReviewsFailed();
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

}
