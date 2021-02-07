package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByImdbId(String imdbId);

}
