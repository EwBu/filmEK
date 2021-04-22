package pl.kowalska.filmek.moviePojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MovieObjectList {

    private List<MovieObject> movieObjects;

    public MovieObjectList() {
        this.movieObjects = new ArrayList<>();
    }

    public MovieObjectList(List<MovieObject> movieObjects) {
        this.movieObjects = movieObjects;
    }

    public List<MovieObject> getMovieObjects() {
        return movieObjects;
    }

    public void setMovieObjects(List<MovieObject> movieObjects) {
        this.movieObjects = movieObjects;
    }
}
