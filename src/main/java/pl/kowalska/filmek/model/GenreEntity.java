package pl.kowalska.filmek.model;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class GenreEntity {

    @Id
    private Long genreId;

    private String name;

    public GenreEntity() {
    }

    public GenreEntity(Long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public Long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }
}
