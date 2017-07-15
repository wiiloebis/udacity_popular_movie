package udacity.winni.popsmovie.presentation.moviereview;

import java.util.List;

import udacity.winni.popsmovie.base.BasePresenter;
import udacity.winni.popsmovie.base.BaseView;
import udacity.winni.popsmovie.presentation.model.MovieReviewVM;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 7/11/17.
 */

public interface MovieReviewContract {

    interface View extends BaseView {

        void onGetMovieReviewsSuccess(List<MovieReviewVM> movieReviews);

        void onGetMovieReviewsFailed();

        void showLoadingBar();

        void hideLoadingBar();
    }

    interface Presenter extends BasePresenter {

        void getMovieReviews(long id);

        void resetPage();

        void setPage(int page);

        int getCurrentPage();
    }
}
