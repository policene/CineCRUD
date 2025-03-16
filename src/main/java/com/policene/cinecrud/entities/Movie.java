package com.policene.cinecrud.entities;

import java.time.Year;
import java.util.regex.Pattern;

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
        if (director == null || director.length() <= 3) {
            throw new IllegalArgumentException("ERROR: Director's name must have at least 4 characters.");
        }


        // Regex para não aceitar número.
        Pattern pattern = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñ\\- ]+$");

        if (!pattern.matcher(director).find()) {
            throw new IllegalArgumentException("ERROR: Director's name can't have a number.");
        }

        this.director = director;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        Gender genderFound = Gender.verifyGender(gender);
        this.gender = genderFound.getDescription();
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
        if (title == null || title.length() <= 1) {
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


    @Override
    public String toString() {
        return "============================================\n" +
                "ID: " + id + " - " + title + " | " + year + " | Rating: " + rating +
                "\n - Gênero: " + gender +
                "\n - Diretor: " + director;
    }
}