package com.policene.cinecrud.service;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entity.Movie;
import com.policene.cinecrud.exceptions.ExistentMovieException;
import com.policene.cinecrud.exceptions.InvalidMovieException;

public class MovieService {

    private MovieDAO dao;

    public MovieService (MovieDAO dao){
        this.dao = dao;
    }

    public void registerMovie (Movie movie) {

        if (movie.getTitle().isEmpty() || movie.getTitle() == null) {
            throw new InvalidMovieException("The title can't be null.");
        }

        if (dao.findByTitle(movie.getTitle()) != null){
            throw new ExistentMovieException("The movie already exists on system.");
        }
        dao.insert(movie);
    }

}
