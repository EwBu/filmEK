package pl.kowalska.filmek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;
import pl.kowalska.filmek.model.User;

import java.util.List;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRaiting, MovieRaitingKey> {

    @Query("from MovieRaiting mr where mr.raitingId.userId=?1")
    List<MovieRaiting> findAllRatingByFollowingUser(Long userId);
}
