package udacity.winni.popsmovie.data.source.local;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import udacity.winni.popsmovie.PopsMovieApplication;
import udacity.winni.popsmovie.data.LocalApi;
import udacity.winni.popsmovie.data.local.DatabaseHelper;
import udacity.winni.popsmovie.data.local.MovieContract;
import udacity.winni.popsmovie.data.local.MovieContract.MovieEntry;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class SqliteLocalApiImpl implements LocalApi {

    @Override
    public Observable<MovieList> getFavoriteMovies(int page) {
        return makeObservable(new Callable<MovieList>() {
            @Override
            public MovieList call() {
                try {
                    Cursor cursor = PopsMovieApplication.getAppContext().getContentResolver().query(
                        MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieEntry.COLUMN_ORIGINAL_TITLE);

                    List<Movie> movies = new ArrayList<>();
                    Movie movie;

                    while (cursor.moveToNext()) {
                        movie = new Movie();
                        int id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                        String title = cursor.getString(cursor.getColumnIndex(MovieEntry
                            .COLUMN_TITLE));
                        String originalTitle = cursor
                            .getString(cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_TITLE));
                        String posterPath = cursor
                            .getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setOriginalTitle(originalTitle);
                        movie.setPosterPath(posterPath);
                        movies.add(movie);
                    }

                    return new MovieList(1001, movies);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override
    public Observable<Boolean> addFavoriteMovie(Movie movie) {
        return makeObservable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                ContentValues cv = new ContentValues();
                cv.put(MovieEntry.COLUMN_ID, movie.getId());
                cv.put(MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
                cv.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
                cv.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                PopsMovieApplication.getAppContext().getContentResolver()
                    .insert(MovieEntry.CONTENT_URI, cv);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> removeFavoriteMovie(long id) {
        return makeObservable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(Long.toString(id)).build();

                int rowDeleted = PopsMovieApplication.getAppContext().getContentResolver()
                    .delete(uri,
                        MovieEntry.COLUMN_ID + "=" + id, null);

                if (rowDeleted > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    @Override
    public Observable<Boolean> isMovieFavorited(long id) {
        return makeObservable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    Cursor cursor = PopsMovieApplication.getAppContext().getContentResolver().query(
                        MovieEntry.CONTENT_URI,
                        null,
                        MovieEntry.COLUMN_ID + "=?",
                        new String[]{"" + id},
                        MovieEntry.COLUMN_ORIGINAL_TITLE);
                    if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
                        return false;
                    } else {
                        return true;
                    }

                } catch (Exception e) {
                    return false;
                }
            }
        });
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
