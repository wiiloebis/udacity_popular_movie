package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import udacity.winni.popsmovie.domain.repository.MovieRepository;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class GetMovieReviews extends UseCase {

    private MovieRepository movieRepository;

    private int page;

    private long id;

    public GetMovieReviews(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return movieRepository.getMoviewReviews(id, page);
    }
}