package com.policene.cinecrud.controllers;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Gender;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.exceptions.ExistentMovieException;
import com.policene.cinecrud.service.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class MovieRegisterController implements Initializable {

    // Variáveis padrão para trocar de tela.
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Elementos FXML dos campos de registro obrigatórios.
    @FXML
    private TextField directorField;
    @FXML
    private ChoiceBox<Gender> genderField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;

    // Elementos FXML dos avisos de validações dos campos.
    @FXML
    private Label titleWarning;
    @FXML
    private Label directorWarning;
    @FXML
    private Label yearWarning;
    @FXML
    private Label ratingWarning;


    // Função de inicialização da cena.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Os códigos abaixo fazem a validação em tempo real conforme o usuário digita o nome.
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {

            String valor = newValue;

            if (valor.length() < 3) {
                titleField.setStyle("-fx-border-color: red;");
                titleWarning.setVisible(true);
            } else {
                titleField.setStyle("");
                titleWarning.setVisible(false);
            }
        });

        directorField.textProperty().addListener((observable, oldValue, newValue) -> {

            String valor = newValue;

            if (valor.length() < 4) {
                directorField.setStyle("-fx-border-color: red;");
                directorWarning.setVisible(true);
            } else {
                directorField.setStyle("");
                directorWarning.setVisible(false);
            }
        });

        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(!newValue.isEmpty()) {
                    int valor = Integer.parseInt(newValue);
                    if (valor > Year.now().getValue() || valor < 1895) {
                        yearField.setStyle("-fx-border-color: red;");
                        yearWarning.setVisible(true);
                    } else {
                        yearField.setStyle("");
                        yearWarning.setVisible(false);
                    }
                }
            } catch (Exception ex){
                yearField.setText(oldValue);
            }
        });

        ratingField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(!newValue.isEmpty()){
                    int valor = Integer.parseInt(newValue);
                    if (0 > valor || valor > 100) {
                        ratingField.setStyle("-fx-border-color: red;");
                        ratingWarning.setVisible(true);
                    } else {
                        ratingField.setStyle("");
                        ratingWarning.setVisible(false);
                    }
                }
            } catch (Exception ex) {
                ratingField.setText(oldValue);
            }
        });

        // Popula o Choice Box de Gender Field com os gêneros.
        genderField.getItems().addAll(Gender.values());
        genderField.setConverter(new StringConverter<Gender>() {
            @Override
            public String toString(Gender gender) {
                return gender != null ? gender.getDescription() : "";
            }

            @Override
            public Gender fromString(String string) {
                return Gender.verifyGender(string);
            }
        });
    }

    // Função para o botão 'Prosseguir', faz o envio do filme para o banco.
    public void prosseguir (ActionEvent ev){
        // Se houver algum campo nulo, irá emitir um erro na tela.
        if (isNull()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("");
            a.setContentText("Preencha todos os campos antes de prosseguir.");
            a.showAndWait();
        } else {

            try {
                Movie movie = new Movie();
                movie.setTitle(titleField.getText());
                movie.setDirector(directorField.getText());
                movie.setYear(Integer.valueOf(yearField.getText()));
                movie.setRating(Integer.valueOf(ratingField.getText()));
                movie.setGender(genderField.getValue().getDescription());

                MovieService service = new MovieService(new MovieDAO());
                service.registerMovie(movie);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Filme inserido com sucesso.");
                alert.showAndWait();

                // Retorna para a tela inicial após a inserção.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
                root = loader.load();
                stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (ExistentMovieException | IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Título de filme já existe no sistema.");
                alert.showAndWait();
            } catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Informações inválidas.");
                alert.showAndWait();
            }
        }
    }


    // Função para o botão de 'Minha Lista', que retorna para a tela inicial.
    public void retornar(ActionEvent ev) throws IOException {
        if (isNull()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
            root = loader.load();
            stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Se houver algum campo preenchido, ele vai emitir um alerta de confirmação para o usuário.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Retornar");
            alert.setHeaderText("");
            alert.setContentText("Você deseja retornar? Todas as informações do formulário serão perdidas.");
            if (alert.showAndWait().get() == ButtonType.OK){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
                root = loader.load();
                stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }


    // Função que retorna true se algum dos campos for nulo.
    public boolean isNull () {
        return titleField.getText().isEmpty() && directorField.getText().isEmpty() && yearField.getText().isEmpty() && ratingField.getText().isEmpty() && genderField.getValue() == null;
    }






}
