package pl.kowalska.filmek.model;

import javax.persistence.*;

@Entity
public class MovieRaiting {

    @EmbeddedId
    MovieRaitingKey raitingId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    MovieEntity movieEntity;

    private int rating;

    private boolean toWach;

    public MovieRaiting(MovieRaitingKey raitingId, User user, MovieEntity movieEntity, int rating, boolean toWach) {
        this.raitingId = raitingId;
        this.user = user;
        this.movieEntity = movieEntity;
        this.rating = rating;
        this.toWach = toWach;
    }

    public MovieRaitingKey getRaitingId() {
        return raitingId;
    }

    public void setRaitingId(MovieRaitingKey raitingId) {
        this.raitingId = raitingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MovieEntity getMovieEntity() {
        return movieEntity;
    }

    public void setMovieEntity(MovieEntity movieEntity) {
        this.movieEntity = movieEntity;
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
