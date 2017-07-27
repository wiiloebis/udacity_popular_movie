package udacity.winni.popsmovie;

import udacity.winni.popsmovie.data.source.factory.MovieDataSourceFactory;
import udacity.winni.popsmovie.domain.repository.MovieDataRepository;
import udacity.winni.popsmovie.domain.repository.MovieRepository;
import udacity.winni.popsmovie.domain.usecase.AddFavoriteMovie;
import udacity.winni.popsmovie.domain.usecase.CheckIfMovieFavorited;
import udacity.winni.popsmovie.domain.usecase.GetFavoriteMovies;
import udacity.winni.popsmovie.domain.usecase.GetMovieDetail;
import udacity.winni.popsmovie.domain.usecase.GetMovieReviews;
import udacity.winni.popsmovie.domain.usecase.GetMovieTrailers;
import udacity.winni.popsmovie.domain.usecase.GetPopularMovies;
import udacity.winni.popsmovie.domain.usecase.GetTopRatedMovies;
import udacity.winni.popsmovie.domain.usecase.RemoveFavoriteMovie;

/**
 * Created by winniseptiani on 6/27/17.
 */

public class ApplicationComponent {

    private static MovieRepository provideMovieRepository() {
        MovieDataSourceFactory movieDataSourceFactory = new
            MovieDataSourceFactory();
        return new MovieDataRepository(movieDataSourceFactory.createCloudDataSource(), movieDataSourceFactory.createLocalDataSource());
    }

    public static GetPopularMovies provideGetPopularMovies() {
        return new GetPopularMovies(provideMovieRepository());
    }

    public static GetTopRatedMovies provideGetHighRatedMovies() {
        return new GetTopRatedMovies(provideMovieRepository());
    }

    public static GetMovieDetail provideGetMovieDetail() {
        return new GetMovieDetail(provideMovieRepository());
    }

    public static GetMovieTrailers provideGetMovieTrailers() {
        return new GetMovieTrailers(provideMovieRepository());
    }

    public static GetMovieReviews provideGetMovieReviews() {
        return new GetMovieReviews(provideMovieRepository());
    }

    public static GetFavoriteMovies provideGetFavoriteMovies() {
        return new GetFavoriteMovies(provideMovieRepository());
    }

    public static AddFavoriteMovie provideAddFavoriteMovie() {
        return new AddFavoriteMovie(provideMovieRepository());
    }

    public static RemoveFavoriteMovie provideRemoveFavoriteMovie() {
        return new RemoveFavoriteMovie(provideMovieRepository());
    }

    public static CheckIfMovieFavorited provideCheckIfMovieFavorited() {
        return new CheckIfMovieFavorited(provideMovieRepository());
    }
}
