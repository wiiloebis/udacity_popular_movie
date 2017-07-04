package udacity.winni.popsmovie.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import udacity.winni.popsmovie.data.model.Movie;

/**
 * Created by winniseptiani on 6/23/17.
 */

public class GetPopularMovieResponse {

    private int page;

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_page")
    private int totalPages;

    private List<Movie> results;

    public long getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
