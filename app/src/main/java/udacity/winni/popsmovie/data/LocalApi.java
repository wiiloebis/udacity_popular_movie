package udacity.winni.popsmovie.data;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public interface LocalApi {

    Observable<MovieList> getFavoriteMovies(int page);

    Observable<Boolean> addFavoriteMovie(Movie movie);

    Observable<Boolean> removeFavoriteMovie(long id);

    Observable<Boolean> isMovieFavorited(long id);
}
