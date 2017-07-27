package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import udacity.winni.popsmovie.domain.repository.MovieRepository;

/**
 * Created by winniseptiani on 7/19/17.
 */

public class RemoveFavoriteMovie extends UseCase {

    private MovieRepository movieRepository;

    private long movieId;

    public RemoveFavoriteMovie(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return movieRepository.removeFavoriteMovie(movieId);
    }
}
