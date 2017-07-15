package udacity.winni.popsmovie.presentation.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import udacity.winni.popsmovie.data.model.MovieReview;
import udacity.winni.popsmovie.presentation.model.MovieReviewVM;

/**
 * Created by winniseptiani on 7/11/17.
 */

public class MovieReviewMapper {

    public static MovieReviewVM transform(MovieReview movieReview) {
        MovieReviewVM movieReviewVM = null;

        if (movieReview != null) {
            movieReviewVM = new MovieReviewVM();
            movieReviewVM.setId(movieReview.getId());
            movieReviewVM.setAuthor(movieReview.getAuthor());
            movieReviewVM.setContent(movieReview.getContent());
            movieReviewVM.setUrl(movieReview.getUrl());
        }

        return movieReviewVM;
    }

    public static List<MovieReviewVM> transform(List<MovieReview> movieReviews) {
        List<MovieReviewVM> movieReviewVMs = null;

        if (movieReviews != null) {
            movieReviewVMs = new ArrayList<>();

            for (MovieReview movieReview : movieReviews) {
                movieReviewVMs.add(transform(movieReview));
            }
        } else {
            movieReviewVMs = Collections.emptyList();
        }

        return movieReviewVMs;
    }
}
