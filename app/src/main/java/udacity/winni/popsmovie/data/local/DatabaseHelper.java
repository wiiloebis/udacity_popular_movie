package udacity.winni.popsmovie.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import udacity.winni.popsmovie.data.local.MovieContract.*;
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
        db.execSQL(
            "ALTER TABLE " + MovieContract.MovieEntry.TABLE_MOVIE + " ADD COLUMN " +
                MovieContract.MovieEntry
                .COLUMN_OVERVIEW + " string;");
    }

    public Cursor getFavoriteMovies(@Nullable String[] projection,
        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return movieContract
            .getFavoriteMovies(getWritableDatabase(), projection, selection, selectionArgs,
                sortOrder);
    }

    public long addFavoriteMovie(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        return movieContract.addFavoriteMovie(contentValues, db);
    }

    public int deleteFavoriteMovie(String movieId) {
        return movieContract.deleteFavoriteMovie(movieId, getWritableDatabase());
    }
}
