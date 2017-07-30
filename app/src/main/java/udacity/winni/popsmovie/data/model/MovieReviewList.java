package udacity.winni.popsmovie.data.model;

import java.util.List;

/**
 * Created by winniseptiani on 7/2/17.
 */

public class MovieReviewList {

    private int id;

    private int page;

    private int totalPage;

    private List<MovieReview> movieReviews;

    public MovieReviewList(int page, int totalPage, List<MovieReview> result) {
        this.page = page;
        this.totalPage = totalPage;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
