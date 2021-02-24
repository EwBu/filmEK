package pl.kowalska.filmek.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.MovieEntity;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {




    @Query("FROM MovieEntity me where me.voteAverage between ?1 and ?2")
    List<MovieEntity> findMoviesBasedOnRating(Double minRating, Double maxRating);

//    @Query("SELECT m from MoviesGenres mg JOIN MovieEntity m ON m.id=mg.id JOIN GenreEntity g ON g.genreId=mg.genreId where g.name=?1")
//    List<MovieEntity> findMoviesByQuery(String genre);

}
