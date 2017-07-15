package udacity.winni.popsmovie.data;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import udacity.winni.popsmovie.BuildConfig;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;

/**
 * Created by winniseptiani on 6/21/17.
 */

public interface RestApi {

    String SERVER_URL = BuildConfig.SERVER_URL;

    String ACCEPTED_LANGUAGE = "Accept-Language";

    int CONNECTION_TIMEOUT = 60;

    int READ_TIMEOUT = 60;

    Observable<MovieList> getPopularMovies(String url, Map<String, String> query);

    Observable<MovieList> getTopRatedMovies(String url, Map<String, String> query);

    Observable<Movie> getMovieDetail(String url, Map<String, String> query);

    Observable<MovieReviewList> getMovieReviews(String url, Map<String, String> query);

    Observable<List<Video>> getMovieTrailers(String url, Map<String, String> query);
}
