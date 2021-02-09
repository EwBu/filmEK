package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {

    MovieEntity findByImdbId(String imdbId);

}
