package com.policene.cinecrud.service;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.exceptions.ExistentMovieException;
import com.policene.cinecrud.exceptions.InvalidMovieException;
import com.policene.cinecrud.exceptions.MovieNotFoundException;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieService {

    private MovieDAO dao;

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

        if (dao.findByTitle(movie.getTitle()) != null && dao.findByTitle(movie.getTitle()).getId() != movie.getId()){
            throw new ExistentMovieException("ERROR: The movie already exists on system.");
        }

        dao.update(movie);

    }
//
//    public Movie findMovieById (Integer id) {
//
//        if (dao.findById(id) == null) {
//            throw new MovieNotFoundException("ERROR: There's no movie with ID " + id + ".");
//        }
//
//        return dao.findById(id);
//
//    }
//
//    public List<Movie> listAllMovies () {
//        if (dao.listAll().isEmpty()) {
//            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
//        }
//        return dao.listAll();
//    }
//
    public List<Movie> listByTitle (String title) {
        return dao.listByTitle(title);
    }

    public List<Movie> listByDirector(String director) {
        return dao.listByDirector(director);
    }

    public List<Movie> searchMovies(String title, String director, int minRating, int maxRating) {
        return dao.searchMovies(title, director, minRating, maxRating);
    }

    public ObservableList<Movie> showAllOrderingByTitle () {
        return dao.showAllOrderingByTitle();
    }

    public List<Movie> listByRating(Integer minRating, Integer maxRating) {
        return dao.listByRating(minRating, maxRating);
    }

//
//    public List<Movie> listByYear (Integer year) {
//        if (dao.listByYear(year).isEmpty()) {
//            throw new MovieNotFoundException("ERROR: There's no movies registered with that year.");
//        }
//        return dao.listByYear(year);
//    }


}
