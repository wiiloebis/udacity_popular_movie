package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import udacity.winni.popsmovie.domain.repository.MovieRepository;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class GetTopRatedMovies extends UseCase {

    private MovieRepository movieRepository;

    private int page;

    public GetTopRatedMovies(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return movieRepository.getTopRatedMovies(page);
    }
}
