package com.policene.cinecrud.dao;

import com.policene.cinecrud.config.ConnectionFabric;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.entities.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private Connection connection;

    public MovieDAO(){
        connection = ConnectionFabric.createConnection();
    }

    public void insert (Movie movie) {
        try {
            String sql = "INSERT INTO movies (title, director, year, rating, gender) VALUE (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setInt(3, movie.getYear());
            statement.setInt(4, movie.getRating());
            statement.setString(5, movie.getGender());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update (Movie movie) {

        try {
            String sql = "UPDATE movies SET title = ?, director = ?, year = ?, rating = ?, gender = ? WHERE idmovies = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setInt(3, movie.getYear());
            statement.setInt(4, movie.getRating());
            statement.setString(5, movie.getGender());
            statement.setInt(6, movie.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The movie was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Integer id) {
        try {
            String sql = "DELETE FROM movies WHERE idmovies = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The movie was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> listAll () {
        List<Movie> registers = new ArrayList<>();
        try {
            String sql = "SELECT * from movies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registers;
    }

    public List<Movie> listByTitle(String title) {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies WHERE title LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> listByDirector(String director) {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies WHERE director LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, director + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> listByYear(Integer year) {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies WHERE year LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, year + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }


    public List<Movie> orderByRatingDesc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by rating DESC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> orderByRatingAsc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by rating ASC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }



    public List<Movie> orderByYearDesc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by year DESC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }


    public List<Movie> orderByYearAsc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by year ASC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> orderByTitleDesc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by title DESC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> orderByTitleAsc () {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies ORDER by title ASC";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }


    public Movie findById(Integer id) {
        Movie movie = null;
        try {
            String sql = "SELECT * from movies WHERE idmovies = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }

    public Movie findByTitle(String title) {
        Movie movie = null;
        try {
            String sql = "SELECT * from movies WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setId(resultSet.getInt("idmovies"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }







}
