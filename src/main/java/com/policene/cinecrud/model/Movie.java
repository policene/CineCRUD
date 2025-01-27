package com.policene.cinecrud.model;

import java.util.List;
import java.util.stream.Collectors;

public class Movie {

    private Integer id;
    private String title;
    private String director;
//    private List<Gender> genders;
    private Integer year;
    private Integer rating;

    public Movie() {
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

//    public List<Gender> getGenders() {
//        return genders;
//    }
//
//    public void setGenders(List<Gender> genders) {
//        this.genders = genders;
//    }

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
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Movie(String director, Integer rating, String title, Integer year) {
        this.director = director;
        this.rating = rating;
        this.title = title;
        this.year = year;
    }

    @Override
    public String toString() {
//        String generosFormatados = genders.stream()
//                .map(Gender::getNome)
//                .collect(Collectors.joining(", "));

        return "============================================\n" +
                " - " + title + " | " + year + " | Rating: " + rating +
//                "\n - Gêneros: " + generosFormatados +
                "\n - Diretor: " + getDirector();
    }
}
