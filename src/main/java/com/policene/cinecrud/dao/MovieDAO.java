package com.policene.cinecrud.dao;

import com.policene.cinecrud.model.Movie;
import com.policene.cinecrud.sql.ConnectionFabric;

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
            String sql = "INSERT INTO movies (title, director, year, rating) VALUE (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setInt(3, movie.getYear());
            statement.setInt(4, movie.getRating());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                movie.setYear(resultSet.getInt("year"));
                movie.setRating(resultSet.getInt("rating"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
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
                movie.setYear(resultSet.getInt("year"));
                movie.setRating(resultSet.getInt("rating"));
                registers.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return registers;
    }

    public void update (Movie movie) {

        if (movie == null || movie.getId() == null) {
            throw new IllegalArgumentException("O filme ou ID do filme não pode ser nulo.");
        }

        try {
            String sql = "UPDATE movies SET title = ?, director = ?, year = ?, rating = ? WHERE idmovies = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDirector());
            statement.setInt(3, movie.getYear());
            statement.setInt(4, movie.getRating());
            statement.setInt(5, movie.getId());
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
}
