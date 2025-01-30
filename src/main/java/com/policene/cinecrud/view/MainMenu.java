package com.policene.cinecrud.view;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entity.Movie;
import com.policene.cinecrud.exceptions.MovieNotFoundException;
import com.policene.cinecrud.service.MovieService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner read = new Scanner(System.in);

    public static void showMenu () {
        System.out.println();
        System.out.println("------- CINECRUD -------");
        System.out.println("1. Register a new movie.");
        System.out.println("2. Delete an existing movie.");
        System.out.println("3. Update an existing movie.");
        System.out.println("4. Listing options.");
        System.out.println("5. Ordering options.");
        System.out.println("6. Leave.");
        System.out.println();

        boolean optionChosen = false;

        while (!optionChosen) {

            try {
                System.out.println("Choose an option.");
                int option = read.nextInt();
                read.nextLine();
                optionChosen = true;

                switch (option) {
                    case 1 -> registerMovie();
                    case 2 -> deleteMovie();
                    case 3 -> updateMovie();
                    case 4 -> listingOptions();
                    case 5 -> orderingOptions();
                    default -> optionChosen = false;
                }

            } catch (InputMismatchException ex) {
                System.out.println("ERROR: Invalid option.");
                read.nextLine();
                optionChosen = false;
            }
        }




    }

    private static void registerMovie() {
        Movie movie = new Movie();
        System.out.println();
        System.out.println("---- REGISTER A MOVIE ----");
        try{
            System.out.println("Enter the movie's name: ");
            movie.setTitle(read.nextLine());
            System.out.println("Enter the director's name: ");
            movie.setDirector(read.nextLine());
            System.out.println("Enter the movie's gender: ");
            movie.setGender(read.nextLine());
            System.out.println("Enter the movie's year: ");
            movie.setYear(read.nextInt());
            System.out.println("Enter your movie's rating: ");
            movie.setRating(read.nextInt());

            MovieService service = new MovieService(new MovieDAO());
            service.registerMovie(movie);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteMovie() {

        System.out.println();
        System.out.println("---- DELETE A MOVIE ----");
        try {
            System.out.println("Enter the ID of the movie you want to delete: ");
            int id = read.nextInt();

            MovieService service = new MovieService(new MovieDAO());
            service.deleteMovie(id);
        } catch (MovieNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void updateMovie() {
        System.out.println();
        System.out.println("---- UPDATE A MOVIE ----");
        try {
            System.out.println("Enter the ID of the movie you want to update: ");
            int id = read.nextInt();
            read.nextLine();


            MovieService service = new MovieService(new MovieDAO());

            Movie movieFound = service.findMovieById(id);
            System.out.println();
            System.out.println(movieFound);

            System.out.println();
            System.out.println("Is this the movie you want to update? (Y/N)");
            String answer = read.nextLine().toUpperCase();

            if (answer.equals("Y")) {
                try{
                    System.out.println("Enter the movie's updated name (blank for mantain): ");
                    String newTitle = read.nextLine();
                    movieFound.setTitle(newTitle.isEmpty() ? movieFound.getTitle() : newTitle);

                    System.out.println("Enter the director's updated name (blank for mantain): ");
                    String newDirector = read.nextLine();
                    movieFound.setDirector(newDirector.isEmpty() ? movieFound.getDirector() : newDirector);

                    System.out.println("Enter the movie's updated gender (blank for mantain): ");
                    String newGender = read.nextLine();
                    movieFound.setGender(newGender.isEmpty() ? movieFound.getGender() : newGender);

                    System.out.println("Enter the movie's updated year (blank for mantain): ");
                    String newYear = read.nextLine();
                    if (newYear.isEmpty()) {
                        movieFound.setYear(movieFound.getYear());
                    } else {
                        movieFound.setYear(Integer.parseInt(newYear));
                    }


                    System.out.println("Enter your movie's updated rating (blank for mantain): ");
                    String newRating = read.nextLine();
                    if (newRating.isEmpty()) {
                        movieFound.setRating(movieFound.getRating());
                    } else {
                        movieFound.setRating(Integer.parseInt(newRating));
                    }

                    service.updateMovie(movieFound);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (MovieNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void listingOptions() {
        System.out.println();
        System.out.println("---- LISTING OPTIONS ----");
        System.out.println("1. List all movies.");
        System.out.println("2. List movies by title.");
        System.out.println("3. List movies by director.");
        System.out.println("4. List movies by gender.");
        System.out.println("5. List movies by year.");
        System.out.println("6. Leave.");
        System.out.println();

        boolean optionChosen = false;

        while (!optionChosen) {

            try {
                System.out.println("Choose an option.");
                int option = read.nextInt();
                read.nextLine();
                optionChosen = true;

                switch (option) {
                    case 1 -> listAllMovies();
                    case 2 -> listByTitle();
                    case 3 -> listByDirector();
//                    case 4 -> listByGender();
                    case 5 -> listByYear();
                }

            } catch (InputMismatchException ex) {
                System.out.println("ERROR: Invalid option.");
                read.nextLine();
                optionChosen = false;
            }
        }
    }

    private static void listAllMovies(){
        System.out.println();
        System.out.println("---- ALL MOVIES ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.listAllMovies();
        result.forEach(System.out::println);
    }

    private static void listByTitle(){
        System.out.println();
        System.out.println("---- LIST BY TITLE ----");

        System.out.println("Enter the name of the movie: ");
        String title = read.nextLine();

        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.listByTitle(title);
        result.forEach(System.out::println);
    }

    private static void listByDirector(){
        System.out.println();
        System.out.println("---- LIST BY DIRECTOR  ----");

        System.out.println("Enter the name of the director: ");
        String director = read.nextLine();

        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.listByDirector(director);
        result.forEach(System.out::println);
    }

    private static void listByYear(){
        System.out.println();
        System.out.println("---- LIST BY YEAR  ----");

        System.out.println("Enter the year of the movie: ");
        int year = read.nextInt();
        read.nextLine();

        MovieService service = new MovieService(new MovieDAO());
        try {
            List<Movie> result = service.listByYear(year);
            result.forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void orderingOptions() {
        System.out.println();
        System.out.println("---- ORDERING OPTIONS ----");
        System.out.println("1. Order by rating (lowest to highest).");
        System.out.println("2. Order by rating (highest to lowest).");
        System.out.println("3. Order by title (A to Z).");
        System.out.println("4. Order by title (Z to A).");
        System.out.println("5. Order by year (oldest to latest).");
        System.out.println("6. Order by year (latest to oldest).");
        System.out.println("7. Leave.");
        System.out.println();

        boolean optionChosen = false;

        while (!optionChosen) {

            try {
                System.out.println("Choose an option.");
                int option = read.nextInt();
                read.nextLine();
                optionChosen = true;

                switch (option) {
                    case 1 -> orderMoviesByLowestRating();
                    case 2 -> orderMoviesByHighestRating();
                    case 3 -> orderMoviesByAlphabeticAsc();
                    case 4 -> orderMoviesByAlphabeticDesc();
                    case 5 -> orderMoviesByOldest();
                    case 6 -> orderMoviesByLatest();
                    default -> System.out.println("ERROR: Out of range option.");
                }

            } catch (InputMismatchException ex) {
                System.out.println("ERROR: Invalid option.");
                read.nextLine();
                optionChosen = false;
            }
        }
    }

    private static void orderMoviesByLowestRating() {
        System.out.println();
        System.out.println("---- ORDER BY LOWEST RATING ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByRatingAsc();
        result.forEach(System.out::println);
    }

    private static void orderMoviesByHighestRating() {
        System.out.println();
        System.out.println("---- ORDER BY HIGHEST RATING ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByRatingDesc();
        result.forEach(System.out::println);
    }

    private static void orderMoviesByAlphabeticAsc() {
        System.out.println();
        System.out.println("---- ORDER BY TITLE (A-Z) ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByTitleAsc();
        result.forEach(System.out::println);
    }

    private static void orderMoviesByAlphabeticDesc() {
        System.out.println();
        System.out.println("---- ORDER BY TITLE (Z-A) ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByTitleDesc();
        result.forEach(System.out::println);
    }

    private static void orderMoviesByOldest() {
        System.out.println();
        System.out.println("---- ORDER BY OLDEST ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByYearAsc();
        result.forEach(System.out::println);
    }

    private static void orderMoviesByLatest() {
        System.out.println();
        System.out.println("---- ORDER BY LATEST ----");
        MovieService service = new MovieService(new MovieDAO());
        List<Movie> result = service.orderByYearDesc();
        result.forEach(System.out::println);
    }

}
