package udacity.winni.popsmovie.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.data.model.MovieList;
import udacity.winni.popsmovie.data.model.MovieReviewList;
import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.data.network.EndpointAddress;
import udacity.winni.popsmovie.data.source.MovieDataSource;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class MovieDataRepository implements MovieRepository {

    private MovieDataSource movieDataSource;

    public MovieDataRepository(MovieDataSource movieDataSource) {
        this.movieDataSource = movieDataSource;
    }

    @Override
    public Observable<Movie> getMovieDetail(long id) {
        return movieDataSource.getMovieDetail(id)
            .map(new Function<Movie, Movie>() {
                @Override
                public Movie apply(Movie movie) throws Exception {
                    movie.setPosterPath(EndpointAddress
                        .getImageUrl(EndpointAddress.IMAGE_SIZE_185 + movie.getPosterPath()));
                    return movie;
                }
            });
    }

    @Override
    public Observable<MovieList> getTopRatedMovies(int page) {
        return movieDataSource.getTopRatedMovies(page)
            .map(new Function<MovieList, MovieList>() {
                @Override
                public MovieList apply(MovieList moviList) throws Exception {
                    for(Movie movie: moviList.getMovies()) {
                        movie.setPosterPath(EndpointAddress
                            .getImageUrl(EndpointAddress.IMAGE_SIZE_185 + movie.getPosterPath()));
                    }
                    return moviList;
                }
            });
    }

    @Override
    public Observable<MovieList> getPopularMovies(int page) {
        return movieDataSource.getPopularMovies(page).map(new Function<MovieList, MovieList>() {
            @Override
            public MovieList apply(MovieList moviList) throws Exception {
                for(Movie movie: moviList.getMovies()) {
                    movie.setPosterPath(EndpointAddress
                        .getImageUrl(EndpointAddress.IMAGE_SIZE_185 + movie.getPosterPath()));
                }
                return moviList;
            }
        });
    }

    @Override
    public Observable<List<Video>> getMovieTrailers(long id) {
        return movieDataSource.getMovieTrailers(id);
    }

    @Override
    public Observable<MovieReviewList> getMoviewReviews(long id, int page) {
        return movieDataSource.getMovieReviews(id, page);
    }
}
