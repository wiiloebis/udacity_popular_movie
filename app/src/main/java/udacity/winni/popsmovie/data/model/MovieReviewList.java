package udacity.winni.popsmovie.data.model;

import java.util.List;

/**
 * Created by winniseptiani on 7/2/17.
 */

public class MovieReviewList {

    private int id;

    private int page;

    private List<MovieReview> movieReviews;

    public MovieReviewList(int page, List<MovieReview> result) {
        this.page = page;
        this.movieReviews = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public int getId() {
        return id;
    }
}
