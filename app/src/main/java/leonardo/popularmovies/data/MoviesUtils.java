package leonardo.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import leonardo.popularmovies.model.Movie;

/**
 * Created by unity on 23/04/17.
 */

public class MoviesUtils {
    public static void addFavoriteMovie(Context context, Movie movie) {
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_TMDB_ID, movie.getId());
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverview());
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER, movie.getPoster());
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_RATING, movie.getRating());
        mNewValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());

        context.getContentResolver().insert(
                MoviesContract.MoviesEntry.CONTENT_URI,
                mNewValues
        );
    }

    public static void deleteFavoriteMovie(Context context, Movie movie) {
        String selectionClause = MoviesContract.MoviesEntry.COLUMN_TMDB_ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(movie.getId())};

        context.getContentResolver().delete(
                MoviesContract.MoviesEntry.CONTENT_URI,
                selectionClause,
                selectionArgs
        );
    }

    public static boolean checkIfMovieIsFavorite(Context context, Movie movie) {
        boolean movieIsFavorite = false;

        String selectionClause = MoviesContract.MoviesEntry.COLUMN_TMDB_ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(movie.getId())};

        Cursor cursor = context.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                selectionClause,
                selectionArgs,
                null
        );

        if (null == cursor) {
            // Error
        } else  if (cursor.getCount() < 1) {
            movieIsFavorite = false;
        } else {
            movieIsFavorite = true;
        }
        return movieIsFavorite;
    }

    public static List<Movie> loadFavoriteMovies(Context context) {
        List<Movie> favoriteMovies = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            // http://stackoverflow.com/questions/9009480/convert-results-of-sqllite-cursor-to-my-object
            while (cursor.moveToNext()) {
                try {
                    int idIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_TMDB_ID);
                    int titleIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_TITLE);
                    int overviewIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_OVERVIEW);
                    int posterIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_POSTER);
                    int ratingIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_RATING);
                    int releaseDateIndex = cursor.getColumnIndexOrThrow(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE);

                    int id = cursor.getInt(idIndex);
                    String title = cursor.getString(titleIndex);
                    String overview = cursor.getString(overviewIndex);
                    String poster = cursor.getString(posterIndex);
                    String rating = cursor.getString(ratingIndex);
                    String releaseDate = cursor.getString(releaseDateIndex);

                    favoriteMovies.add(new Movie(id, title, poster, overview, rating, releaseDate));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
//                    cursor.close();
                }
            }
        } else {
            // Insert code here to report an error if the cursor is null or the provider threw an exception.
        }

        return favoriteMovies;
    }
}
