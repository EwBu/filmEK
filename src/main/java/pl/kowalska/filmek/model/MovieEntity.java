
package pl.kowalska.filmek.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "movies")
public class MovieEntity {


    @Id
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String posterPath;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    private String originalLanguage;

    @Lob
    private String overview;

    private double popularity;

    private String releaseDate;

//    @Column(nullable = false)
//    private long runtime;

    private double voteAverage;

    private long voteCount;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "movies_genres",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "genreId") }
    )
    List<GenreEntity> genres = new ArrayList<>();


    public MovieEntity() {
    }

    public MovieEntity(Integer id,
                       String posterPath,
                       String title,
                       String originalTitle,
                       String originalLanguage,
                       String overview,
                       double popularity,
                       String releaseDate,
                       double voteAverage,
                       long voteCount,
                       List<GenreEntity> genres) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
//        this.runtime = runtime;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

//    public long getRuntime() {
//        return runtime;
//    }
//
//    public void setRuntime(long runtime) {
//        this.runtime = runtime;
//    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }
}
