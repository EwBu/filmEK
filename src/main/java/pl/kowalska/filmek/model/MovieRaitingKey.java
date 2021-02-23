package pl.kowalska.filmek.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MovieRaitingKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "id")
    Long id;

    public MovieRaitingKey() {
    }

    public MovieRaitingKey(Long userId, Long id) {
        this.userId = userId;
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
