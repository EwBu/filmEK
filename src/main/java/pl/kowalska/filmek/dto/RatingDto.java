package pl.kowalska.filmek.dto;

public class RatingDto {

    private Long id;
    private Long userId;
    private int rating;
    private boolean toWatch;


    public RatingDto() {
    }

    public RatingDto(Long id, Long userId, Integer rating, Boolean toWatch) {
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.toWatch = toWatch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getToWatch() {
        return toWatch;
    }

    public void setToWatch(boolean toWatch) {
        this.toWatch = toWatch;
    }
}
