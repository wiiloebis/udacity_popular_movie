package udacity.winni.popsmovie.domain.repository;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 6/26/17.
 */

public interface MovieRepository {

    Observable<Movie> getMovieDetail(long id);

    Observable<MovieList> getTopRatedMovies(int page);

    Observable<MovieList> getPopularMovies(int page);
}
