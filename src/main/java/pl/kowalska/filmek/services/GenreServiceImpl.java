package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.repository.GenreRepository;

import java.util.List;

public class GenreServiceImpl implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreEntity> findAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        return genreEntities;
    }
}
