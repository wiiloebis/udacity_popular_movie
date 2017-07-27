package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.domain.repository.MovieRepository;

/**
 * Created by winniseptiani on 7/19/17.
 */

public class AddFavoriteMovie extends UseCase {

    private MovieRepository movieRepository;

    private Movie movie;

    public AddFavoriteMovie(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setMovieData(Movie movie) {
        this.movie = movie;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return movieRepository.addFavoriteMovie(movie);
    }
}
