package com.policene.cinecrud.dao;

import com.policene.cinecrud.config.DatabaseConnection;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public Connection connection;

    public MovieDAO(){
        connection = new DatabaseConnection().getConnection();
    }

    public void insert (Movie movie) {
        try {
            String sql = "INSERT INTO movies (title, director, year, rating, gender) VALUES (?, ?, ?, ?, ?)";
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update (Movie movie) {

        try {
            String sql = "UPDATE movies SET title = ?, director = ?, year = ?, rating = ?, gender = ? WHERE id = ?";

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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Integer id) {
        try {
            String sql = "DELETE FROM movies WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The movie was deleted successfully!");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> listByTitle(String title) {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies WHERE title LIKE ? ORDER BY title ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + title + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
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
            String sql = "SELECT * FROM movies WHERE director LIKE ? ORDER BY title ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + director + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }

    public List<Movie> listByRating(Integer minRating, Integer maxRating) {
        List<Movie> registers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM movies WHERE rating >= ? AND rating <= ? ORDER BY title ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minRating);
            statement.setInt(2, maxRating);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
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

    public Movie findByTitle(String title) {
        Movie movie = null;
        try {
            String sql = "SELECT * from movies WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setId(resultSet.getInt("id"));
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

    public ObservableList<Movie> showAllOrderingByTitle () {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        String sql = "SELECT * from movies ORDER by title ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setGender(rs.getString("gender"));
                movie.setYear(String.valueOf(rs.getInt("year")));
                movie.setRating(String.valueOf(rs.getInt("rating")));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }


    public List<Movie> searchMovies(String title, String director, int minRating, int maxRating) {

        // Cria o array de filmes que deve ser retornado.
        List<Movie> registers = new ArrayList<>();

        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM movies WHERE 1=1");
            List<Object> params = new ArrayList<>();

            // Adicionar condições apenas para os campos preenchidos
            if (title != null && !title.isEmpty()) {
                sqlBuilder.append(" AND title LIKE ?");
                params.add("%" + title + "%");
            }

            if (director != null && !director.isEmpty()) {
                sqlBuilder.append(" AND director LIKE ?");
                params.add("%" + director + "%");
            }

            if (minRating > 0) {
                sqlBuilder.append(" AND rating >= ?");
                params.add(minRating); // Adiciona o valor à lista de parâmetros
            }

            if (maxRating < 100) {
                sqlBuilder.append(" AND rating <= ?");
                params.add(maxRating); // Adiciona o valor à lista de parâmetros
            }

            sqlBuilder.append(" ORDER BY title ASC");

            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

            // Definir os parâmetros
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof String) {
                    statement.setString(i + 1, (String) params.get(i));
                } else if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                }
                // Adicione outros tipos conforme necessário
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setYear(String.valueOf(resultSet.getInt("year")));
                movie.setRating(String.valueOf(resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
                registers.add(movie);
            }
            resultSet.close();
            statement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return registers;
    }






}
