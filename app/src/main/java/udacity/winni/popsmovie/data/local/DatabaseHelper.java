package udacity.winni.popsmovie.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import udacity.winni.popsmovie.data.local.MovieContract.*;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movieDb";

    private MovieContract movieContract;

    public DatabaseHelper(Context context, MovieContract movieContract) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.movieContract = movieContract;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_MOVIE + "("
            + MovieEntry.COLUMN_ID + " TEXT PRIMARY KEY," + MovieContract.MovieEntry.COLUMN_TITLE
            + " TEXT," +
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT," + MovieEntry
            .COLUMN_POSTER_PATH + " TEXT)";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_MOVIE);
        onCreate(db);
    }

    public Observable<MovieList> getFavoriteMovies(int page) {
        return makeObservable(movieContract.getFavoriteMovies(page, getWritableDatabase()))
            .subscribeOn(Schedulers.computation());
    }

    public Observable<Boolean> addFavoriteMovie(Movie movie) {
        return makeObservable(movieContract.addFavoriteMovie(movie, getWritableDatabase()))
            .subscribeOn(Schedulers.computation());
    }

    public Observable<Boolean> removeFavoriteMovie(long movieId) {
        return makeObservable(movieContract.removeFavoriteMovie(movieId, getWritableDatabase()))
            .subscribeOn(Schedulers.computation());
    }

    public Observable<Boolean> isMovieFavorited(long movieId) {
        return makeObservable(movieContract.isMovieFavorited(movieId, getWritableDatabase()))
            .subscribeOn(Schedulers.computation());
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(func.call());
                    emitter.onComplete();
                } catch (Exception ex) {
                    emitter.onError(ex);
                }

            }
        });
    }
}
