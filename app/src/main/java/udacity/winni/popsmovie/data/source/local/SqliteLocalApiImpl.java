package udacity.winni.popsmovie.data.source.local;


import io.reactivex.Observable;
import udacity.winni.popsmovie.PopsMovieApplication;
import udacity.winni.popsmovie.data.LocalApi;
import udacity.winni.popsmovie.data.local.DatabaseHelper;
import udacity.winni.popsmovie.data.local.MovieContract;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class SqliteLocalApiImpl implements LocalApi {

    private MovieContract movieTable;

    private DatabaseHelper databaseHelper;

    public SqliteLocalApiImpl() {
        movieTable = new MovieContract();
        databaseHelper = new DatabaseHelper(PopsMovieApplication.getAppContext(), movieTable);
    }

    @Override
    public Observable<MovieList> getFavoriteMovies(int page) {
        return databaseHelper.getFavoriteMovies(page);
    }

    @Override
    public Observable<Boolean> addFavoriteMovie(Movie movie) {
        return databaseHelper.addFavoriteMovie(movie);
    }

    @Override
    public Observable<Boolean> removeFavoriteMovie(long id) {
        return databaseHelper.removeFavoriteMovie(id);
    }

    @Override
    public Observable<Boolean> isMovieFavorited(long id) {
        return databaseHelper.isMovieFavorited(id);
    }
}
