package udacity.winni.popsmovie.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import udacity.winni.popsmovie.PopsMovieApplication;
import udacity.winni.popsmovie.data.local.MovieContract;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.data.source.MovieDataSource;

/**
 * Created by winniseptiani on 7/18/17.
 */

public class LocalMovieDataSource implements MovieDataSource {

    @Override
    public Observable<MovieList> getPopularMovies(int page) {
        return null;
    }

    @Override
    public Observable<MovieList> getTopRatedMovies(int page) {
        return null;
    }

    @Override
    public Observable<Movie> getMovieDetail(long id) {
        return null;
    }

    @Override
    public Observable<List<Video>> getMovieTrailers(long id) {
        return null;
    }

    @Override
    public Observable<MovieReviewList> getMovieReviews(long id, int page) {
        return null;
    }

    @Override
    public Observable<MovieList> getFavoriteMovies(int page) {
        return makeObservable(new Callable<MovieList>() {
            @Override
            public MovieList call() {
                try {
                    Cursor cursor = PopsMovieApplication.getAppContext().getContentResolver().query(
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);

                    List<Movie> movies = new ArrayList<>();
                    Movie movie;

                    while (cursor.moveToNext()) {
                        movie = new Movie();
                        int id = cursor
                            .getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID));
                        String title = cursor.getString(cursor.getColumnIndex(
                            MovieContract.MovieEntry
                                .COLUMN_TITLE));
                        String originalTitle = cursor
                            .getString(cursor
                                .getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
                        String posterPath = cursor
                            .getString(
                                cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
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
    public Observable<Boolean> addFavoritMovie(Movie movie) {
        return makeObservable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                ContentValues cv = new ContentValues();
                cv.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
                cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
                cv.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
                cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                PopsMovieApplication.getAppContext().getContentResolver()
                    .insert(MovieContract.MovieEntry.CONTENT_URI, cv);
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
                        MovieContract.MovieEntry.COLUMN_ID + "=" + id, null);

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
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        MovieContract.MovieEntry.COLUMN_ID + "=?",
                        new String[]{"" + id},
                        MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
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
