package udacity.winni.popsmovie.data.local;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.http.DELETE;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class MovieContract {

    public Cursor getFavoriteMovies(SQLiteDatabase db, @Nullable String[] projection,
        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_MOVIE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);

        return cursor;
    }

    public long addFavoriteMovie(ContentValues contentValues,
        SQLiteDatabase db) {
        long rowId = 0;
        try {
            db.beginTransaction();
            rowId = db.insert(MovieContract.MovieEntry.TABLE_MOVIE, null, contentValues);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            return 0L;
        } finally {
            db.endTransaction();
        }
        return rowId;
    }

    public int deleteFavoriteMovie(String movieId, SQLiteDatabase db) {
        try {
            return db.delete(MovieContract.MovieEntry.TABLE_MOVIE, MovieEntry.COLUMN_ID + "=?",
                new String[]{movieId});
        } catch (SQLException e) {
            return 0;
        }
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

    public static final String AUTHORITY = "udacity.winni.popsmovie";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITE_MOVIES = "favoritemovies";

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_MOVIE = "movie";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

    }
}
