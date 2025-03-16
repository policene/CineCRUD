package com.policene.cinecrud.dao;

import com.policene.cinecrud.config.DatabaseConnection;
import com.policene.cinecrud.entities.Movie;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public Connection connection;

    public MovieDAO(){
        connection = new DatabaseConnection().getConnection();
    }

    // Função para inserir um filme.
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

    // Função para atualizar um filme.
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

    // Função para deletar um filme.
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

    // Função para buscar um filme pelo nome.
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
                movie.setYear(resultSet.getInt("year"));
                movie.setRating((resultSet.getInt("rating")));
                movie.setGender(resultSet.getString("gender"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    // Função para listar todos os filmes em ordem alfabética.
    public ObservableList<Movie> listAll () {
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
                movie.setYear(rs.getInt("year"));
                movie.setRating(rs.getInt("rating"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }


    public List<Movie> searchMovies(String title, String director, int minRating, int maxRating, int minYear, int maxYear, String gender) {

        // Cria o array de filmes que deve ser retornado.
        List<Movie> registers = new ArrayList<>();

        try {
            // A query 1=1 é uma query básica apenas para que a primeira validação seja verdadeira,
            // e a query não quebre.

            // Usamos StringBuilder para adicionar mais dados para a nossa query se preciso.
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
                params.add(minRating);
            }

            if (maxRating < 100) {
                sqlBuilder.append(" AND rating <= ?");
                params.add(maxRating);
            }

            if (minYear > 1895) {
                sqlBuilder.append(" AND year >= ?");
                params.add(minYear);
            }

            if (maxYear < Year.now().getValue()) {
                sqlBuilder.append(" AND year <= ?");
                params.add(maxYear);
            }

            if (gender != null && !gender.isEmpty()) {
                sqlBuilder.append(" AND gender == ?");
                params.add(gender);
            }

            sqlBuilder.append(" ORDER BY title ASC");

            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

            // Definir os parâmetros.
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
                movie.setYear(resultSet.getInt("year"));
                movie.setRating(resultSet.getInt("rating"));
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
