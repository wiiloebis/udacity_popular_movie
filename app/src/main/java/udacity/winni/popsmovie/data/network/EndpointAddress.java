package udacity.winni.popsmovie.data.network;

import udacity.winni.popsmovie.BuildConfig;

/**
 * Created by winniseptiani on 6/23/17.
 */

public class EndpointAddress {

    public static final String POPULAR = "popular";

    public static final String TOP_RATED = "top_rated";

    public static final String VIDEOS = "%d/videos";

    public static final String REVIEWS = "%d/reviews";

    public static final String IMAGE_SIZE_92 = "w92/";

    public static final String IMAGE_SIZE_154 = "w154/";

    public static final String IMAGE_SIZE_185 = "w185/";

    public static final String IMAGE_SIZE_342 = "w342/";

    public static final String IMAGE_SIZE_500 = "w500/";

    public static final String IMAGE_SIZE_780 = "w780/";

    public static final String IMAGE_SIZE_ORIGINAL = "original/";

    public static String getMovieDbUrl(String endpointUrl) {
        return BuildConfig.SERVER_URL + endpointUrl;
    }

    public static String getImageUrl(String endpointUrl) {
        return BuildConfig.IMAGE_SERVER_URL + endpointUrl;
    }
}
