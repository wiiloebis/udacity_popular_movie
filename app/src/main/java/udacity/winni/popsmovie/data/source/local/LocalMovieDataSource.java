package udacity.winni.popsmovie.data.source.local;

import java.util.List;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.LocalApi;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.data.source.MovieDataSource;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class LocalMovieDataSource implements MovieDataSource {

    private LocalApi localApi;

    public LocalMovieDataSource(LocalApi localApi) {
        this.localApi = localApi;
    }

    @Override
    public Observable<MovieList> getPopularMovies(int page) {
        return null;
    }

    @Override
    public Observable<MovieList> getTopRatedMovies(int page) {
        return null;
    }

    @Override
    public Observable<Movie> getMovieDetail(long id) {
        return null;
    }

    @Override
    public Observable<List<Video>> getMovieTrailers(long id) {
        return null;
    }

    @Override
    public Observable<MovieReviewList> getMovieReviews(long id, int page) {
        return null;
    }

    @Override
    public Observable<MovieList> getFavoriteMovies(int page) {
        return localApi.getFavoriteMovies(page);
    }

    @Override
    public Observable<Boolean> addFavoritMovie(Movie movie) {
        return localApi.addFavoriteMovie(movie);
    }

    @Override
    public Observable<Boolean> removeFavoriteMovie(long id) {
        return localApi.removeFavoriteMovie(id);
    }

    @Override
    public Observable<Boolean> isMovieFavorited(long id) {
        return localApi.isMovieFavorited(id);
    }
}
