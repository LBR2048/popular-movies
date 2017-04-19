package leonardo.popularmovies;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy item representing a piece of overview.
 */
@Parcel
public class Movie {
    String title;
    String poster;
    String overview;
    String rating;
    String releaseDate;
    boolean favorite;

    public Movie() {
        // Required for Parcelable
    }

    public Movie(String title, String poster, String overview, String rating, String releaseDate) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return title;
    }

    public static List<Movie> getDummyMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("title 1", "poster 1", "overview 1", "rating 1", "date 1"));
        movies.add(new Movie("title 2", "poster 2", "overview 2", "rating 2", "date 2"));

        return movies;
    }
}
