package leonardo.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by unity on 19/04/17.
 */

public class MoviesContract {

    public static final class MoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_FAVORITE = "favorite";
    }

}
