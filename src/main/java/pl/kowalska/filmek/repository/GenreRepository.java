package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.moviePojo.Genre;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    @Query("from GenreEntity ge where ge.name=?1")
    GenreEntity findByName(String name);

}
