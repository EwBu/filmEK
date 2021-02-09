
package pl.kowalska.filmek.model;

import java.util.List;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@SuppressWarnings("unused")
@Entity(name = "movie")
public class MovieEntity {


    @Id
    private String imdbId;

    @Column(nullable = false)
    private String posterPath;

    @Column(nullable = false)
    private String title;


}
