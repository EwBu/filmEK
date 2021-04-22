package pl.kowalska.filmek.model;

import javax.persistence.*;

@Entity
public class MovieRaiting {

    @EmbeddedId
    MovieRaitingKey raitingId;

//    @ManyToOne
//    @MapsId("userId")
//    @JoinColumn(name = "user_id")
//    User user;
//
//    @ManyToOne
//    @MapsId("id")
//    @JoinColumn(name = "id")
//    MovieEntity movieEntity;

    private int rating;

    private boolean toWatch;

    public MovieRaiting() {
    }

    public MovieRaiting(MovieRaitingKey raitingId, int rating, boolean toWatch) {
        this.raitingId = raitingId;
        this.rating = rating;
        this.toWatch = toWatch;
    }

    public MovieRaitingKey getRaitingId() {
        return raitingId;
    }

    public void setRaitingId(MovieRaitingKey raitingId) {
        this.raitingId = raitingId;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isToWatch() {
        return toWatch;
    }

    public void setToWatch(boolean toWatch) {
        this.toWatch = toWatch;
    }
}
