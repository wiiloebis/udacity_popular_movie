package udacity.winni.popsmovie.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;

/**
 * Created by winniseptiani on 7/18/17.
 */

public interface LocalApi {

    Cursor getFavoriteMovies(@Nullable String[] projection,
        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder);

    long insertFavoriteMovie(ContentValues contentValues);

    int deleteFavoriteMovie(String movieId);
}
