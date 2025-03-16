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

    // Função de inicialização da cena.
    private Stage stage;
    private Scene scene;
    private Parent root;

    // O filme que foi selecionado da TableView na tela inicial.
    public Movie movieToEdit;

    // Elementos FXML dos campos de registro obrigatórios.
    @FXML
    private TextField titleField;
    @FXML
    private TextField directorField;
    @FXML
    private ChoiceBox<Gender> genderField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField ratingField;


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

    // Função que pega o filme escolhido e executa o preencherCampos.
    public void setMovieToEdit(Movie filme) {
        this.movieToEdit = filme;
        preencherCampos();
    }

    // Função que pré-define os campos de edição com os valores do filme escolhido.
    private void preencherCampos() {
        titleField.setText(movieToEdit.getTitle());
        directorField.setText(movieToEdit.getDirector());
        Gender selectedGender = Gender.verifyGender(movieToEdit.getGender());
        genderField.setValue(selectedGender);
        yearField.setText(String.valueOf(movieToEdit.getYear()));
        ratingField.setText(String.valueOf(movieToEdit.getRating()));
    }


    // Função que faz o update do filme no banco de dados.
    @FXML
    void update(ActionEvent event) throws IOException {
        // Se houver algum campo nulo, irá emitir um erro na tela.
        if (isNull()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("");
            a.setContentText("Preencha todos os campos antes de prosseguir.");
            a.showAndWait();
        } else {
            movieToEdit.setTitle(titleField.getText());
            movieToEdit.setDirector(directorField.getText());
            movieToEdit.setYear(Integer.valueOf(yearField.getText()));
            movieToEdit.setRating(Integer.valueOf(ratingField.getText()));
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


    // Função que retorna true se algum dos campos for nulo.
    public boolean isNull () {
        return titleField.getText().isEmpty() && directorField.getText().isEmpty() && yearField.getText().isEmpty() && ratingField.getText().isEmpty() && genderField.getValue() == null;
    }


    // Função para o botão 'Minha Lista', que leva para outra tela.
    @FXML
    void moveToList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/app.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // Função para o botão 'Novo Filme', que leva para outra tela.
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
