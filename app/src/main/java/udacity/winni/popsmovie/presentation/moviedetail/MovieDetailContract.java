package udacity.winni.popsmovie.presentation.moviedetail;

import java.util.List;

import udacity.winni.popsmovie.base.BasePresenter;
import udacity.winni.popsmovie.base.BaseView;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.presentation.model.MovieVM;
import udacity.winni.popsmovie.presentation.model.MovieTrailerVM;

/**
 * Created by winniseptiani on 6/16/17.
 */

public interface MovieDetailContract {

    interface View extends BaseView {

        void onGetMovieDetailSuccess(MovieVM movieVM);

        void onGetMovieDetailFailed();

        void onGetMovieTrailersSuccess(List<MovieTrailerVM> movieTrailerVMs);

        void onGetMovieTrailersFailed();

        void onAddFavoriteMovieSuccess(boolean success);

        void onAddFavoriteMovieFailed();

        void onRemoveFavoriteMovieSuccess(boolean success);

        void onRemoveFavoriteMovieFailed();

        void onCheckIfMovieFavoritedSuccess(boolean success);

        void onCheckIfMovieFavoritedFailed();
    }

    interface Presenter extends BasePresenter {

        void getMovieDetail(long id);

        void getMovieTrailer(long id);

        void addFavoriteMovie(MovieVM movie);

        void removeFavoriteMovie(long id);

        void checkIfMovieFavorited(long id);
    }
}
