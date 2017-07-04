package udacity.winni.popsmovie.data.network.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.network.response.GetHighRatedMovieResponse;
import udacity.winni.popsmovie.data.network.response.GetPopularMovieResponse;

import static udacity.winni.popsmovie.data.network.retrofit.RetrofitService.CONTENT_TYPE_JSON;

/**
 * Created by winniseptiani on 6/23/17.
 */

public interface RetrofitService {

    String CONTENT_TYPE_JSON = "Content-Type: application/vnd.api+json";

    @Headers({CONTENT_TYPE_JSON})
    @GET
    Observable<GetPopularMovieResponse> getPopularMovie(@Url String url,
        @QueryMap(encoded = true) Map<String, String> optionsQuery);

    @Headers({CONTENT_TYPE_JSON})
    @GET
    Observable<GetHighRatedMovieResponse> getTopRatedMovie(@Url String url,
        @QueryMap(encoded = true) Map<String, String> optionsQuery);

    @Headers({CONTENT_TYPE_JSON})
    @GET
    Observable<Movie> getMovieDetail(@Url String url,
        @QueryMap(encoded = true) Map<String, String> optionsQuery);
}
