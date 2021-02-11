package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {





}
