
package pl.kowalska.filmek.model;

import java.util.List;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@SuppressWarnings("unused")
@Entity(name = "movie")
public class Movie {

//    private Boolean adult;
//    private String backdropPath;
//    private Object belongsToCollection;
//    private long budget;
//    private List<Genre> genres;
//    private String homepage;
//    private long id;
    @Id
    private String imdbId;
//    private String originalLanguage;
//    private String originalTitle;
//    private String overview;
//    private double popularity;
    @Column(nullable = false)
    private String posterPath;
//    private List<ProductionCompany> productionCompanies;
//    private List<ProductionCountry> productionCountries;
//    private String releaseDate;
//    private long revenue;
//    private long runtime;
//    private List<SpokenLanguage> spokenLanguages;
//    private String status;
//    private String tagline;
    @Column(nullable = false)
    private String title;
//    private Boolean video;
//    private double voteAverage;
//    private long voteCount;

}
