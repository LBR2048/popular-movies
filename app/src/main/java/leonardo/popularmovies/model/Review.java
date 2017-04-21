package leonardo.popularmovies.model;

/**
 * Created by unity on 21/04/17.
 */

public class Review {

    private String content;

    public Review(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
