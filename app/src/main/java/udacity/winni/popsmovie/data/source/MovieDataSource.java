package udacity.winni.popsmovie.data.source;

import java.util.List;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 6/26/17.
 */

public interface MovieDataSource {

    Observable<MovieList> getPopularMovies(int page);

    Observable<MovieList> getTopRatedMovies(int page);

    Observable<Movie> getMovieDetail(long id);

}
