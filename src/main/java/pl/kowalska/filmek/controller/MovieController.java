package pl.kowalska.filmek.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kowalska.filmek.OmdbWebServiceClient;
import pl.kowalska.filmek.model.Movie;

import javax.websocket.server.PathParam;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class MovieController {

//    @GetMapping(value = "/search_movies/{title}", produces = {"application/json"})
//    public Collection<Movie> searchMoviesByTitle(@PathParam("title") String searchTitle){
//        ArrayList movies = new ArrayList<Movie>();
//
//        String jsonString = OmdbWebServiceClient.searchMovieByTitle(searchTitle);
//        JSONPObject moviesJson = new JSONPObject(jsonString);
//        JsonArray search = moviesJson.getJSONArray("Search");
//
//
//        return movies;
//    }

}
