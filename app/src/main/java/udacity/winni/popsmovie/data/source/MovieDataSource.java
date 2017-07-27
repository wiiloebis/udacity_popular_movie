package udacity.winni.popsmovie.data.source;

import java.util.List;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;

/**
 * Created by winniseptiani on 6/26/17.
 */

public interface MovieDataSource {

    Observable<MovieList> getPopularMovies(int page);

    Observable<MovieList> getTopRatedMovies(int page);

    Observable<Movie> getMovieDetail(long id);

    Observable<List<Video>> getMovieTrailers(long id);

    Observable<MovieReviewList> getMovieReviews(long id, int page);

    Observable<MovieList> getFavoriteMovies(int page);

    Observable<Boolean> addFavoritMovie(Movie movie);

    Observable<Boolean> removeFavoriteMovie(long id);

    Observable<Boolean> isMovieFavorited(long id);
}
