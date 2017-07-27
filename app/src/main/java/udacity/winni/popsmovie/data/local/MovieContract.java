package udacity.winni.popsmovie.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class MovieContract {

    public Callable<MovieList> getFavoriteMovies(int page, SQLiteDatabase db) {
        return new Callable<MovieList>() {
            @Override
            public MovieList call() {
                Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + MovieContract.MovieEntry.TABLE_MOVIE + " ORDER BY " +
                        MovieEntry.COLUMN_ORIGINAL_TITLE,
                    null);

                List<Movie> movies = new ArrayList<>();
                Movie movie;

                while (cursor.moveToNext()) {
                    movie = new Movie();
                    int id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
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
            }
        };
    }

    public Callable<Boolean> addFavoriteMovie(Movie movie, SQLiteDatabase db) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {

                ContentValues cv = new ContentValues();
                cv.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
                cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
                cv.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
                cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());

                try {
                    db.beginTransaction();
                    db.insert(MovieContract.MovieEntry.TABLE_MOVIE, null, cv);
                    db.setTransactionSuccessful();
                } catch (SQLException e) {
                    return false;
                } finally {
                    db.endTransaction();
                }
                return true;
            }
        };
    }

    public Callable<Boolean> isMovieFavorited(long movieId, SQLiteDatabase db) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {

                Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + MovieContract.MovieEntry.TABLE_MOVIE + " WHERE " +
                        MovieEntry.COLUMN_ID + " = " + movieId, null);

                if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    public Callable<Boolean> removeFavoriteMovie(long movieId, SQLiteDatabase db) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {

                try {
                    db.execSQL(
                        "delete from " + MovieContract.MovieEntry.TABLE_MOVIE + " where " +
                            MovieContract.MovieEntry.COLUMN_ID + " ='" +
                            movieId + "'");
                } catch (SQLException e) {
                    return false;
                }
                return true;
            }
        };
    }

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_MOVIE = "movie";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_TITLE = "title";

    }
}
