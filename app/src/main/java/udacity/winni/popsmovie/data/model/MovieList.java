package udacity.winni.popsmovie.data.model;

import java.util.List;

/**
 * Created by winniseptiani on 7/2/17.
 */

public class MovieList {

    private int page;

    private List<Movie> movies;

    public MovieList(int page, List<Movie> result) {
        this.page = page;
        this.movies = result;
    }



    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
