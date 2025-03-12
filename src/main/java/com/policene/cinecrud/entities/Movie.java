package com.policene.cinecrud.entities;

import com.policene.cinecrud.exceptions.GenderNotFoundException;
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


        // Não aceitar número.
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

        if (genderFound == null) {
            throw new GenderNotFoundException("ERROR: Gender not found.");
        } else {
            this.gender = genderFound.getDescription();
        }


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

    public void setRating(String stringRating) {

        Pattern pattern = Pattern.compile("^\\d{1,3}$");
        if (!pattern.matcher(stringRating).find()) {
            throw new IllegalArgumentException("ERROR: Rating must contain only numbers.");
        }

        int integerRating = Integer.parseInt(stringRating);

        if (integerRating > 100 || integerRating < 0) {
            throw new IllegalArgumentException("ERROR: Rating must be a number between 0 and 100.");
        }

        this.rating = integerRating;
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

    public void setYear(String stringYear) {


        Pattern pattern = Pattern.compile("^\\d{4}$");

        if (!pattern.matcher(stringYear).find()) {
            throw new IllegalArgumentException("ERROR: Year must be a sequence of four numbers.");
        }

        int integerYear = Integer.parseInt(stringYear);


        if (integerYear > Year.now().getValue() || integerYear < 1895) {
            throw new IllegalArgumentException("ERROR: Invalid year.");
        }
        this.year = integerYear;
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
                "ID: " + id + " - " + title + " | " + year + " | Rating: " + rating +
                "\n - Gênero: " + gender +
                "\n - Diretor: " + director;
    }
}