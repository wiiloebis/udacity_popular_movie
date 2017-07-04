package udacity.winni.popsmovie;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by winniseptiani on 6/23/17.
 */

public class PopsMovieApplication extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return PopsMovieApplication.context;
    }
}
