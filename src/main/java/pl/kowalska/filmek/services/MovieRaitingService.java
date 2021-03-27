package pl.kowalska.filmek.services;

import org.springframework.stereotype.Service;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.MovieRaiting;

@Service
public interface MovieRaitingService {

    void save(MovieRaiting movieRaiting);

}
