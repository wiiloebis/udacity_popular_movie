package udacity.winni.popsmovie.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import udacity.winni.popsmovie.data.model.MovieReview;

/**
 * Created by winniseptiani on 7/6/17.
 */

public class GetMovieReviewResponse {

    private int page;

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_page")
    private int totalPages;

    private List<MovieReview> results;

    public long getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MovieReview> getResults() {
        return results;
    }
}
