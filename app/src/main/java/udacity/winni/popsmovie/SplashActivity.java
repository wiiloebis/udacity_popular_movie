package udacity.winni.popsmovie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.winni.popsmovie.presentation.moviegallery.MovieGalleryActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, SPLASH_DISPLAY_LENGTH);

        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startMovieGalleryActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivIcon.startAnimation(slideDown);
    }

    private void startMovieGalleryActivity() {
        Intent intent = new Intent(this, MovieGalleryActivity.class);
        startActivity(intent);
        finish();
    }
}
