package udacity.winni.popsmovie.data.source.cloud;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import udacity.winni.popsmovie.BuildConfig;
import udacity.winni.popsmovie.data.RestApi;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.data.network.EndpointAddress;
import udacity.winni.popsmovie.data.network.Param;
import udacity.winni.popsmovie.data.source.MovieDataSource;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class CloudMovieDataSource implements MovieDataSource {

    private final RestApi restApi;

    public CloudMovieDataSource(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<MovieList> getPopularMovies(int page) {
        String fullUrl = EndpointAddress.getMovieDbUrl(EndpointAddress.POPULAR);
        HashMap params = new HashMap<>();
        params.put(Param.API_KEY, BuildConfig.API_KEY);
        params.put(Param.PAGE, page);
        return restApi.getPopularMovies(fullUrl, params);
    }

    @Override
    public Observable<MovieList> getTopRatedMovies(int page) {
        String fullUrl = EndpointAddress.getMovieDbUrl(EndpointAddress.TOP_RATED);
        HashMap params = new HashMap<>();
        params.put(Param.API_KEY, BuildConfig.API_KEY);
        params.put(Param.PAGE, page);
        return restApi.getTopRatedMovies(fullUrl, params);
    }

    @Override
    public Observable<Movie> getMovieDetail(long id) {
        String fullUrl = EndpointAddress.getMovieDbUrl(String.valueOf(id));
        HashMap params = new HashMap<>();
        params.put(Param.API_KEY, BuildConfig.API_KEY);
        return restApi.getMovieDetail(fullUrl, params);
    }

    @Override
    public Observable<List<Video>> getMovieTrailers(long id) {
        String fullUrl = EndpointAddress.getMovieDbUrl(String.format(EndpointAddress.VIDEOS, id));
        HashMap params = new HashMap<>();
        params.put(Param.API_KEY, BuildConfig.API_KEY);
        return restApi.getMovieTrailers(fullUrl, params);
    }

    @Override
    public Observable<MovieReviewList> getMovieReviews(long id, int page) {
        String fullUrl = EndpointAddress.getMovieDbUrl(String.format(EndpointAddress.REVIEWS, id));
        HashMap params = new HashMap<>();
        params.put(Param.API_KEY, BuildConfig.API_KEY);
        params.put(Param.PAGE, page);
        return restApi.getMovieReviews(fullUrl, params);
    }
}
