package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.SearchMovies;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private GenreRepository genreRepo;


    final RestTemplate restTemplate = new RestTemplate();
    @Value("${api.key}")
    private String key;

    public Page<MovieEntity> findPaginatedMovies(Pageable pageable){
        List<MovieEntity> allMovies = movieRepo.findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<MovieEntity> list;

        if(allMovies.size() < startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem + pageSize, allMovies.size());
            list = allMovies.subList(startItem, toIndex);
        }
        Page<MovieEntity> moviesPage = new PageImpl<MovieEntity>(list, PageRequest.of(currentPage, pageSize), allMovies.size());

        return moviesPage;
    }

    @Override
    public Page<MovieEntity> findAll(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }


    @Override
    public MovieEntity findSingleMovieInDatabase(Long id) {
        Optional<MovieEntity> optionalMovieFromEntity = movieRepo.findById(id);
        return optionalMovieFromEntity.get();
    }

    @Override
    public MovieObject findSingleMovieInTmdb(String id) {
        String movieDetailsUrl = String.format("https://api.themoviedb.org/3/movie/%s?api_key=%s&language=pl", id, key);
        return restTemplate.getForObject(movieDetailsUrl, MovieObject.class);
    }

    @Override
    public List<MovieEntity> findMoviesByQuery(String genre, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, Integer yearMin, Integer yearMax) {
        List<MovieEntity> moviesByQuery;
        LocalDate minYear = LocalDate.of(yearMin, 01,01);
        LocalDate maxYear = LocalDate.of(yearMax, 12,31);
        if(genre == null || genre =="") {
            moviesByQuery = movieRepo.findMoviesByQueryWithoutGenre(voteMin, voteMax, popularityMin, popularityMax, minYear, maxYear);
        }else {
            GenreEntity genreEntity = genreRepo.findByName(genre);
            moviesByQuery = movieRepo.findMoviesByQueryWithGenre(genreEntity, voteMin, voteMax, popularityMin, popularityMax, minYear, maxYear);

        }
        return moviesByQuery;
    }

}
