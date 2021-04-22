package pl.kowalska.filmek.services;

import pl.kowalska.filmek.model.GenreEntity;

import java.util.List;

public interface GenreService {

    List<GenreEntity> findAll();
}
