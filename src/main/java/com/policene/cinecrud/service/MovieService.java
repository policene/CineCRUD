package com.policene.cinecrud.service;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.exceptions.ExistentMovieException;
import com.policene.cinecrud.exceptions.InvalidMovieException;
import com.policene.cinecrud.exceptions.MovieNotFoundException;

import java.util.List;

public class MovieService {

    private MovieDAO dao;

    public MovieService(MovieDAO dao){
        this.dao = dao;
    }

    public void registerMovie (Movie movie) {

        if (movie.getTitle().isEmpty() || movie.getTitle() == null) {
            throw new InvalidMovieException("ERROR: The title can't be null.");
        }

        if (dao.findByTitle(movie.getTitle()) != null){
            throw new ExistentMovieException("ERROR: The movie already exists on system.");
        }

        dao.insert(movie);
    }

    public void deleteMovie (Integer id) {

        if (dao.findById(id) == null) {
            throw new MovieNotFoundException("ERROR: There's no movie with ID " + id + ".");
        }

        dao.delete(id);

    }

    public void updateMovie (Movie movie) {

        if (movie.getTitle().isEmpty() || movie.getTitle() == null) {
            throw new InvalidMovieException("ERROR: The title can't be null.");
        }

        if (dao.findByTitle(movie.getTitle()) != null && dao.findByTitle(movie.getTitle()).getId() != movie.getId()){
            throw new ExistentMovieException("ERROR: The movie already exists on system.");
        }

        dao.update(movie);

    }

    public Movie findMovieById (Integer id) {

        if (dao.findById(id) == null) {
            throw new MovieNotFoundException("ERROR: There's no movie with ID " + id + ".");
        }

        return dao.findById(id);

    }

    public List<Movie> listAllMovies () {
        if (dao.listAll().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.listAll();
    }

    public List<Movie> listByTitle (String title) {
        if (dao.listByTitle(title).isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered with that title.");
        }
        return dao.listByTitle(title);
    }

    public List<Movie> listByDirector(String director) {
        if (dao.listByDirector(director).isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered with that director.");
        }
        return dao.listByDirector(director);
    }

    public List<Movie> listByYear (Integer year) {
        if (dao.listByYear(year).isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered with that year.");
        }
        return dao.listByYear(year);
    }

    public List<Movie> orderByRatingAsc () {
        if (dao.orderByRatingAsc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByRatingAsc();
    }

    public List<Movie> orderByRatingDesc () {
        if (dao.orderByRatingDesc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByRatingDesc();
    }

    public List<Movie> orderByTitleAsc () {
        if (dao.orderByTitleAsc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByTitleAsc();
    }

    public List<Movie> orderByTitleDesc () {
        if (dao.orderByTitleDesc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByTitleDesc();
    }

    public List<Movie> orderByYearAsc () {
        if (dao.orderByYearAsc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByYearAsc();
    }

    public List<Movie> orderByYearDesc () {
        if (dao.orderByYearDesc().isEmpty()) {
            throw new MovieNotFoundException("ERROR: There's no movies registered in database.");
        }
        return dao.orderByYearDesc();
    }

}
