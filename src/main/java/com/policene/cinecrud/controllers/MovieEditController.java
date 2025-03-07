package com.policene.cinecrud.controllers;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Gender;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.service.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class MovieEditController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Movie movieToEdit;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonList;

    @FXML
    private Button buttonRegister;

    @FXML
    private TextField directorField;

    @FXML
    private Label directorWarning;

    @FXML
    private Button editButton;

    @FXML
    private ChoiceBox<Gender> genderField;

    @FXML
    private AnchorPane lateralbar;

    @FXML
    private TextField ratingField;

    @FXML
    private Label ratingWarning;

    @FXML
    private TextField titleField;

    @FXML
    private Label titleWarning;

    @FXML
    private TextField yearField;

    @FXML
    private Label yearWarning;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void setMovieToEdit(Movie filme) {
        this.movieToEdit = filme;
        preencherCampos();
    }

    private void preencherCampos() {
        titleField.setText(movieToEdit.getTitle());
        directorField.setText(movieToEdit.getDirector());
        Gender selectedGender = Gender.verifyGender(movieToEdit.getGender());
        genderField.setValue(selectedGender);
        yearField.setText(String.valueOf(movieToEdit.getYear()));
        ratingField.setText(String.valueOf(movieToEdit.getRating()));
    }

    public boolean isNull () {
        return titleField.getText().isEmpty() && directorField.getText().isEmpty() && yearField.getText().isEmpty() && ratingField.getText().isEmpty() && genderField.getValue() == null;
    }


    @FXML
    void update(ActionEvent event) throws IOException {
        if (isNull()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("");
            a.setContentText("Preencha todos os campos antes de prosseguir.");
            a.showAndWait();
        } else {
            movieToEdit.setTitle(titleField.getText());
            movieToEdit.setDirector(directorField.getText());
            movieToEdit.setYear(yearField.getText());
            movieToEdit.setRating(ratingField.getText());
            movieToEdit.setGender(genderField.getValue().getDescription());

            MovieService service = new MovieService(new MovieDAO());
            service.updateMovie(movieToEdit);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void moveToList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void moveToRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/movieRegister.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
