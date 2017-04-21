package leonardo.popularmovies.model;

/**
 * Created by unity on 21/04/17.
 */

public class Video {

    public Video(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
