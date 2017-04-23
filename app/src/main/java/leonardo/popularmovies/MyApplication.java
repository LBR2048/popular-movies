package leonardo.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by unity on 23/04/17.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
