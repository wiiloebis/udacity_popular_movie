package udacity.winni.popsmovie.data.source.factory;

import udacity.winni.popsmovie.data.LocalApi;
import udacity.winni.popsmovie.data.RestApi;
import udacity.winni.popsmovie.data.network.retrofit.RetrofitRestApiImpl;
import udacity.winni.popsmovie.data.source.MovieDataSource;
import udacity.winni.popsmovie.data.source.cloud.CloudMovieDataSource;
import udacity.winni.popsmovie.data.source.local.LocalMovieDataSource;
import udacity.winni.popsmovie.data.source.local.SqliteLocalApiImpl;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class MovieDataSourceFactory {

    public MovieDataSource createCloudDataSource() {
        RestApi restApi = new RetrofitRestApiImpl();
        return new CloudMovieDataSource(restApi);
    }

    public MovieDataSource createLocalDataSource() {
        LocalApi localApi = new SqliteLocalApiImpl();
        return new LocalMovieDataSource(localApi);
    }
}
