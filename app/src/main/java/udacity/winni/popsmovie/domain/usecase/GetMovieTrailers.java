package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import udacity.winni.popsmovie.domain.repository.MovieRepository;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class GetMovieTrailers extends UseCase {

    private MovieRepository movieRepository;

    private long movieId;

    public GetMovieTrailers(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return movieRepository.getMovieTrailers(movieId);
    }
}