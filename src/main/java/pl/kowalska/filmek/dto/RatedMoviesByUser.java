package pl.kowalska.filmek.dto;

import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;

public class RatedMoviesByUser {


    private final MovieEntity movie;
    private final MovieRaiting rating;

    public RatedMoviesByUser(MovieEntity movie, MovieRaiting rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public MovieRaiting getRating() {
        return rating;
    }

    //    private Long userId;
//    private Long id;
//    private String posterPath;
//    private String title;
//    private String originalTitle;
//    private LocalDate releaseDate;
//    private double voteAverage;
//
//    public RatedMoviesByUser(Long userId, Long id, String posterPath, String title, String originalTitle, LocalDate releaseDate, double voteAverage) {
//        this.userId = userId;
//        this.id = id;
//        this.posterPath = posterPath;
//        this.title = title;
//        this.originalTitle = originalTitle;
//        this.releaseDate = releaseDate;
//        this.voteAverage = voteAverage;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPosterPath() {
//        return posterPath;
//    }
//
//    public void setPosterPath(String posterPath) {
//        this.posterPath = posterPath;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getOriginalTitle() {
//        return originalTitle;
//    }
//
//    public void setOriginalTitle(String originalTitle) {
//        this.originalTitle = originalTitle;
//    }
//
//    public LocalDate getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(LocalDate releaseDate) {
//        this.releaseDate = releaseDate;
//    }
//
//    public double getVoteAverage() {
//        return voteAverage;
//    }
//
//    public void setVoteAverage(double voteAverage) {
//        this.voteAverage = voteAverage;
//    }


}
