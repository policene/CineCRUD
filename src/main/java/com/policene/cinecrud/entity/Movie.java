package com.policene.cinecrud.entity;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class Movie {

    private Integer id;
    private String title;
    private String director;
    private String gender;
    private Integer year;
    private Integer rating;

    public Movie() {
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director.length() <= 3) {
            throw new IllegalArgumentException("ERROR: Director's name must have at least 4 characters.");
        }
        this.director = director;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("ERROR: Gender can't be null.");
        }
        if (gender.matches("\\d")){
            throw new IllegalArgumentException("ERROR: Gender can't have numbers.");
        }
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if (rating > 100 || rating < 0) {
            throw new IllegalArgumentException("ERROR: Rating must be a number between 0 and 100.");
        }
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.length() <= 1) {
            throw new IllegalArgumentException("ERROR: Title must have at least 2 characters.");
        }
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        if (year > Year.now().getValue() || year < 1895) {
            throw new IllegalArgumentException("ERROR: Invalid year.");
        }
        this.year = year;
    }

    public Movie(String title, String gender, String director, Integer year, Integer rating) {
        this.director = director;
        this.rating = rating;
        this.title = title;
        this.year = year;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "============================================\n" +
                " - " + title + " | " + year + " | Rating: " + rating +
                "\n - Gênero: " + gender +
                "\n - Diretor: " + getDirector();
    }
}
