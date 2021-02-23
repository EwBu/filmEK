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

    private boolean toWach;

    public MovieRaiting() {
    }

    public MovieRaiting(MovieRaitingKey raitingId, int rating, boolean toWach) {
        this.raitingId = raitingId;
        this.rating = rating;
        this.toWach = toWach;
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

    public boolean isToWach() {
        return toWach;
    }

    public void setToWach(boolean toWach) {
        this.toWach = toWach;
    }
}
