package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("Select me from MovieEntity me where ?1 member of me.genres AND not ?1 is null AND me.voteAverage between ?2 and ?3 AND me.popularity between ?4 and ?5 AND me.releaseDate between ?6 and ?7 order by me.voteAverage DESC")
    List<MovieEntity> findMoviesByQueryWithGenre(GenreEntity genre, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, LocalDate yearMin, LocalDate yearMax);

    @Query("Select me from MovieEntity me where me.voteAverage between ?1 and ?2 AND me.popularity between ?3 and ?4 AND me.releaseDate between ?5 and ?6 order by me.voteAverage DESC")
    List<MovieEntity> findMoviesByQueryWithoutGenre(Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, LocalDate yearMin, LocalDate yearMax);

}
