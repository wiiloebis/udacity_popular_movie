package udacity.winni.popsmovie.data.source.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import udacity.winni.popsmovie.data.LocalApi;
import udacity.winni.popsmovie.data.local.DatabaseHelper;
import udacity.winni.popsmovie.data.local.MovieContract;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class SqliteLocalApiImpl implements LocalApi {

    private DatabaseHelper dbHelper;

    public SqliteLocalApiImpl(Context context) {
        dbHelper = new DatabaseHelper(context, new MovieContract());
    }

    @Override
    public Cursor getFavoriteMovies(@Nullable String[] projection,
        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return dbHelper
            .getFavoriteMovies(projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public long insertFavoriteMovie(ContentValues contentValues) {
        return dbHelper.addFavoriteMovie(contentValues);
    }

    @Override
    public int deleteFavoriteMovie(String movieId) {
        return dbHelper.deleteFavoriteMovie(movieId);
    }
}
