package com.policene.cinecrud.view;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entity.Movie;
import com.policene.cinecrud.service.MovieService;

import java.util.InputMismatchException;
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

        while (!optionChosen){

            try {
                System.out.println("Choose an option.");
                int option = read.nextInt();
                read.nextLine();
                optionChosen = true;

                switch (option) {
                    case 1 -> registerMovie();
                    case 2 -> deleteMovie();
                    case 3 -> updateMovie();
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

    }

    private static void updateMovie() {

    }

}
