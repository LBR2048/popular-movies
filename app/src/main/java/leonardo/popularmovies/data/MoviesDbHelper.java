package leonardo.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by unity on 19/04/17.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "videos.db";

    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE =
                "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                        MoviesContract.MoviesEntry._ID +
                        MoviesContract.MoviesEntry.COLUMN_POSTER +
                        MoviesContract.MoviesEntry.COLUMN_OVERVIEW +
                        MoviesContract.MoviesEntry.COLUMN_RATING +
                        MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE +
                        MoviesContract.MoviesEntry.COLUMN_TITLE +
                        ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
