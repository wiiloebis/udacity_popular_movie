package udacity.winni.popsmovie.presentation.moviedetail;

import udacity.winni.popsmovie.base.BasePresenter;
import udacity.winni.popsmovie.base.BaseView;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public interface MovieDetailContract {

    interface View extends BaseView {

        void onGetMovieDetailSuccess(MovieVM movieVM);

        void onGetMovieDetailFailed();
    }

    interface Presenter extends BasePresenter {

        void getMovieDetail(long id);
    }
}
