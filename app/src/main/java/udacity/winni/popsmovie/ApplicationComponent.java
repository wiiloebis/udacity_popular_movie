package udacity.winni.popsmovie;

import udacity.winni.popsmovie.data.source.factory.MovieDataSourceFactory;
import udacity.winni.popsmovie.domain.repository.MovieDataRepository;
import udacity.winni.popsmovie.domain.repository.MovieRepository;
import udacity.winni.popsmovie.domain.usecase.GetMovieDetail;
import udacity.winni.popsmovie.domain.usecase.GetPopularMovies;
import udacity.winni.popsmovie.domain.usecase.GetTopRatedMovies;

/**
 * Created by winniseptiani on 6/27/17.
 */

public class ApplicationComponent {

    private static MovieRepository provideMovieRepository() {
        MovieDataSourceFactory movieDataSourceFactory = new
            MovieDataSourceFactory();
        return new MovieDataRepository(movieDataSourceFactory.createCloudDataSource());
    }

    public static GetPopularMovies provideGetPopularMovies() {
        return new GetPopularMovies(provideMovieRepository());
    }

    public static GetTopRatedMovies provideGetHighRatedMovies() {
        return new GetTopRatedMovies(provideMovieRepository());
    }

    public static GetMovieDetail provideMovieDetail() {
        return new GetMovieDetail(provideMovieRepository());
    }
}
