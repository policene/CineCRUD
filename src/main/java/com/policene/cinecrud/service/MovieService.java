package com.policene.cinecrud.service;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.exceptions.ExistentMovieException;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieService {

    private final MovieDAO dao;

    public MovieService(MovieDAO dao){
        this.dao = dao;
    }

    public void registerMovie (Movie movie) {
        if (dao.findByTitle(movie.getTitle()) != null){
            throw new ExistentMovieException("ERROR: The movie already exists on system.");
        }
        dao.insert(movie);
    }

    public void deleteMovie (Integer id) {
        dao.delete(id);
    }

    public void updateMovie (Movie movie) {
        if (dao.findByTitle(movie.getTitle()) != null && dao.findByTitle(movie.getTitle()).getId() != movie.getId()) {
            throw new ExistentMovieException("ERROR: The movie already exists on system.");
        }
        dao.update(movie);
    }

    public ObservableList<Movie> listAll () {
        return dao.listAll();
    }

    public List<Movie> searchMovies(String title, String director, int minRating, int maxRating, int minYear, int maxYear, String gender) {
        return dao.searchMovies(title, director, minRating, maxRating, minYear, maxYear, gender);
    }

}
