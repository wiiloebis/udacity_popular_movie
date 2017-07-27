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

    private MovieDataSource cloudMovieDataSource;

    private MovieDataSource localMovieDataSource;

    public MovieDataRepository(MovieDataSource cloudMovieDataSource,
        MovieDataSource localMovieDataSource) {
        this.cloudMovieDataSource = cloudMovieDataSource;
        this.localMovieDataSource = localMovieDataSource;
    }

    @Override
    public Observable<Movie> getMovieDetail(long id) {
        return cloudMovieDataSource.getMovieDetail(id)
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
        return cloudMovieDataSource.getTopRatedMovies(page)
            .map(new Function<MovieList, MovieList>() {
                @Override
                public MovieList apply(MovieList moviList) throws Exception {
                    for (Movie movie : moviList.getMovies()) {
                        movie.setPosterPath(EndpointAddress
                            .getImageUrl(EndpointAddress.IMAGE_SIZE_185 + movie.getPosterPath()));
                    }
                    return moviList;
                }
            });
    }

    @Override
    public Observable<MovieList> getPopularMovies(int page) {
        return cloudMovieDataSource.getPopularMovies(page)
            .map(new Function<MovieList, MovieList>() {
                @Override
                public MovieList apply(MovieList moviList) throws Exception {
                    for (Movie movie : moviList.getMovies()) {
                        movie.setPosterPath(EndpointAddress
                            .getImageUrl(EndpointAddress.IMAGE_SIZE_185 + movie.getPosterPath()));
                    }
                    return moviList;
                }
            });
    }

    @Override
    public Observable<List<Video>> getMovieTrailers(long id) {
        return cloudMovieDataSource.getMovieTrailers(id);
    }

    @Override
    public Observable<MovieReviewList> getMoviewReviews(long id, int page) {
        return cloudMovieDataSource.getMovieReviews(id, page);
    }

    @Override
    public Observable<MovieList> getFavoriteMovies(int page) {
        return localMovieDataSource.getFavoriteMovies(page);
    }

    @Override
    public Observable<Boolean> addFavoriteMovie(Movie movie) {
        return localMovieDataSource.addFavoritMovie(movie);
    }

    @Override
    public Observable<Boolean> removeFavoriteMovie(long id) {
        return localMovieDataSource.removeFavoriteMovie(id);
    }

    @Override
    public Observable<Boolean> isMovieFavorited(long id) {
        return localMovieDataSource.isMovieFavorited(id);
    }
}
