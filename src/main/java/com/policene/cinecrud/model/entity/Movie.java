package com.policene.cinecrud.model.entity;

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
        this.director = director;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public Movie(String title, String gender, String director, Integer year, Integer rating) {
        this.director = director;
        this.rating = rating;
        this.title = title;
        this.year = year;
    }

    @Override
    public String toString() {
        return "============================================\n" +
                " - " + title + " | " + year + " | Rating: " + rating +
                "\n - Gênero: " + gender +
                "\n - Diretor: " + getDirector();
    }
}
