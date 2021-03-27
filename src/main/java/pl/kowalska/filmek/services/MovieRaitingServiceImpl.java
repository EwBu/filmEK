package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.repository.MovieRatingRepository;

@Service
public class MovieRaitingServiceImpl implements MovieRaitingService{

    @Autowired
    private MovieRatingRepository movieRatingRepository;


    @Override
    public void save(MovieRaiting movieRaiting) {
        movieRatingRepository.save(movieRaiting);
    }
}
