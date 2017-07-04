package udacity.winni.popsmovie.presentation.moviegallery;

import java.util.List;

import udacity.winni.popsmovie.base.BasePresenter;
import udacity.winni.popsmovie.base.BaseView;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public interface MovieGalleryContract {

    interface View extends BaseView {

        void onGetMoviesSuccess(List<MovieVM> movies);

//        void onGetTopRatedMoviesSuccess(List<MovieVM> highRatedMovies);

        void onGetMoviesFailed();

//        void onGetPopularMoviesFailed();

        void showLoadingBar();

        void hideLoadingBar();
    }

    interface Presenter extends BasePresenter {

        void getPopularMovies();

        void getTopRatedMovies();

        void resetPage();

        void setPage(int page);

        int getCurrentPage();
    }
}
