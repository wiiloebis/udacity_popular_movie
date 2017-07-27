package udacity.winni.popsmovie.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;

/**
 * Created by winniseptiani on 6/26/17.
 */

public interface MovieRepository {

    Observable<Movie> getMovieDetail(long id);

    Observable<MovieList> getTopRatedMovies(int page);

    Observable<MovieList> getPopularMovies(int page);

    Observable<List<Video>> getMovieTrailers(long id);

    Observable<MovieReviewList> getMoviewReviews(long id, int page);

    Observable<MovieList> getFavoriteMovies(int page);

    Observable<Boolean> addFavoriteMovie(Movie movie);

    Observable<Boolean> removeFavoriteMovie(long id);

    Observable<Boolean> isMovieFavorited(long id);
}
