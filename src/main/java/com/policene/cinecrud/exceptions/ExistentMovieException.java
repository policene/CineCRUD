package com.policene.cinecrud.exceptions;

public class ExistentMovieException extends RuntimeException {
    public ExistentMovieException(String message) {
        super(message);
    }
}
