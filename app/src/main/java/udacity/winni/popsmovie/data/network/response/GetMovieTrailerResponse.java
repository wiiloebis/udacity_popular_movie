package udacity.winni.popsmovie.data.network.response;

import java.util.List;

import udacity.winni.popsmovie.data.model.Video;

/**
 * Created by winniseptiani on 7/6/17.
 */

public class GetMovieTrailerResponse {

    private int id;

    private List<Video> results;

    public int getId() {
        return id;
    }

    public List<Video> getResults() {
        return results;
    }
}
