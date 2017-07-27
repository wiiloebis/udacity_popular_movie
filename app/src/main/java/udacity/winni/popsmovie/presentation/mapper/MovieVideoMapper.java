package udacity.winni.popsmovie.presentation.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import udacity.winni.popsmovie.data.model.Video;
import udacity.winni.popsmovie.presentation.model.MovieTrailerVM;

/**
 * Created by winniseptiani on 7/11/17.
 */

public class MovieVideoMapper {

    public static MovieTrailerVM transform(Video video) {
        MovieTrailerVM movieTrailerVM = null;

        if (video != null) {
            movieTrailerVM = new MovieTrailerVM();
            movieTrailerVM.setId(video.getId());
            movieTrailerVM.setIso_639_1(video.getIso_639_1());
            movieTrailerVM.setIso_3166_1(video.getIso_3166_1());
            movieTrailerVM.setKey(video.getKey());
            movieTrailerVM.setName(video.getName());
            movieTrailerVM.setSite(video.getSite());
            movieTrailerVM.setSize(video.getSize());
        }

        return movieTrailerVM;
    }

    public static List<MovieTrailerVM> transform(List<Video> videos) {
        List<MovieTrailerVM> movieTrailerVMs = null;

        if (videos != null) {
            movieTrailerVMs = new ArrayList<>();
            for (Video video : videos) {
                if (video.getType().equalsIgnoreCase(Video.TRAILER)) {
                    movieTrailerVMs.add(transform(video));
                }
            }
        } else {
            movieTrailerVMs = Collections.emptyList();
        }

        return movieTrailerVMs;
    }
}
