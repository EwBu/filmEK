package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("FROM MovieEntity me where me.voteAverage between ?1 and ?2")
    List<MovieEntity> findMoviesBasedOnRating(Double minRating, Double maxRating);

    @Query("Select me from MovieEntity me where ?1 member of me.genres AND not ?1 is null AND me.voteAverage between ?2 and ?3 AND me.popularity between ?4 and ?5 order by me.voteAverage DESC")
    List<MovieEntity> findMoviesByGenre(GenreEntity genre, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax);

}
