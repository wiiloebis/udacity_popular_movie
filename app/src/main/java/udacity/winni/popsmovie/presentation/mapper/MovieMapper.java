package udacity.winni.popsmovie.presentation.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import udacity.winni.popsmovie.Utils;
import udacity.winni.popsmovie.data.model.Movie;
import udacity.winni.popsmovie.presentation.model.MovieVM;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class MovieMapper {

    public static MovieVM transform(Movie movie) {
        MovieVM movieVM = null;

        if (movie != null) {
            movieVM = new MovieVM();
            movieVM.setId(movie.getId());
            movieVM.setOriginalTitle(movie.getOriginalTitle());
            movieVM.setPosterPath(movie.getPosterPath());
            movieVM.setRuntime(movie.getRuntime());
            movieVM.setVoteAverage(movie.getVoteAverage());
            movieVM.setOverview(movie.getOverview());
            movieVM.setReleaseYear(Utils.getYearFromDate(movie.getReleaseDate()));
            movieVM.setReleaseDate(Utils.formatReleaseDate(movie.getReleaseDate()));
        }

        return movieVM;
    }

    public static List<MovieVM> transform(List<Movie> movies) {
        List<MovieVM> movieVMs = null;

        if (movies != null) {
            movieVMs = new ArrayList<>();

            for (Movie movie : movies) {
                movieVMs.add(transform(movie));
            }
        } else {
            movieVMs = Collections.emptyList();
        }

        return movieVMs;
    }
}
