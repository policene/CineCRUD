package com.policene.cinecrud.controllers;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.service.MovieService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.kordamp.ikonli.javafx.FontIcon;


import javax.swing.event.ChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isSelected = false;

    private MovieService service = new MovieService(new MovieDAO());

    @FXML
    private FontIcon iconFilter;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField titleField;

    @FXML
    private TextField directorField;

    @FXML
    private AnchorPane filterPanel;

    @FXML
    private TableView<Movie> table = new TableView<>();

    @FXML
    private TableColumn<Movie, Integer> col_id = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> col_title = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> col_director = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> col_gender = new TableColumn<>();

    @FXML
    private TableColumn<Movie, Integer> col_year = new TableColumn<>();

    @FXML
    private TableColumn<Movie, Integer> col_rating_bar;

    @FXML
    private Slider minRatingField;

    @FXML
    private Slider maxRatingField;


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        configureTableSelection();
        loadData();
        setupSearchFilters();
    }

//    public void adjustRatingField () {
//
//
//        minRatingField.valueProperty().addListener(new ChangeListener<Number>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                int minRating = (int) minRatingField.getValue();
//                int maxRating = (int) maxRatingField.getValue();
//                List<Movie> results = new MovieService(new MovieDAO()).listByRating(minRating, maxRating);
//                ObservableList<Movie> observableResults = FXCollections.observableArrayList(results);
//                table.setItems(observableResults);
//            }
//        });
//
//        maxRatingField.valueProperty().addListener(new ChangeListener<Number>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                int maxRating = (int) maxRatingField.getValue();
//                int minRating = (int) minRatingField.getValue();
//                List<Movie> results = new MovieService(new MovieDAO()).listByRating(minRating, maxRating);
//                ObservableList<Movie> observableResults = FXCollections.observableArrayList(results);
//                table.setItems(observableResults);
//            }
//        });
//    }

    public void setupSearchFilters() {
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });

        directorField.textProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });

        minRatingField.valueProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });

        maxRatingField.valueProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });
    }

    public void performCombinedSearch() {
        String titleQuery = titleField.getText().trim();
        String directorQuery = directorField.getText().trim();
        int minRating = (int) minRatingField.getValue();
        int maxRating = (int) maxRatingField.getValue();

        List<Movie> results = new MovieDAO().searchMovies(titleQuery, directorQuery, minRating, maxRating);

        ObservableList<Movie> observableResults = FXCollections.observableArrayList(results);
        table.setItems(observableResults);
    }

    @FXML
    void moveToRegister(ActionEvent ev) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/movieRegister.fxml"));

        root = loader.load();
        stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void moveToEdit(ActionEvent ev) throws IOException {
        Movie movieSelected = table.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/movieEdit.fxml"));
        root = loader.load();

        MovieEditController movieEditController = loader.getController();
        movieEditController.setMovieToEdit(movieSelected);

        stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void configureTableColumns(){
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_title.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTitle()));
        col_director.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDirector()));
        col_gender.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGender()));
        col_year.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getYear()));
        col_rating_bar.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRating()));
        col_rating_bar.setCellFactory(param -> new TableCell<Movie, Integer>() {
            private final ProgressBar progressBar = new ProgressBar();

            @Override
            protected void updateItem(Integer rating, boolean empty) {
                super.updateItem(rating, empty);

                if (empty || rating == null) {
                    setGraphic(null);
                } else {
                    progressBar.setPrefWidth(80);
                    progressBar.setPrefHeight(11);


                    double progress = rating / 100.0;
                    String colorStyle;

                    if (rating <= 10) {
                        colorStyle = "-fx-accent: #8B0000;"; // Vermelho escuro
                    } else if (rating <= 20) {
                        colorStyle = "-fx-accent: #FF0000;"; // Vermelho
                    } else if (rating <= 30) {
                        colorStyle = "-fx-accent: #FF4500;"; // Laranja avermelhado
                    } else if (rating <= 40) {
                        colorStyle = "-fx-accent: #FF8C00;"; // Laranja escuro
                    } else if (rating <= 50) {
                        colorStyle = "-fx-accent: #FFA500;"; // Laranja
                    } else if (rating <= 60) {
                        colorStyle = "-fx-accent: #FFD700;"; // Amarelo dourado
                    } else if (rating <= 70) {
                        colorStyle = "-fx-accent: #ADFF2F;"; // Verde amarelado
                    } else if (rating <= 80) {
                        colorStyle = "-fx-accent: #32CD32;"; // Verde limão
                    } else if (rating <= 90) {
                        colorStyle = "-fx-accent: #008000;"; // Verde
                    } else {
                        colorStyle = "-fx-accent: #006400;"; // Verde escuro
                    }

                    progressBar.setStyle(colorStyle);
                    progressBar.setProgress(progress);
                    setGraphic(progressBar);
                }
            }
        });


    }

    @FXML
    void loadData(){
        table.setItems(service.showAllOrderingByTitle());
    }


    @FXML
    void deleteMovie(ActionEvent ev){
        Movie selectedMovie = table.getSelectionModel().getSelectedItem();
        if (selectedMovie != null){
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Exclusão");
            confirmacao.setHeaderText("Tem certeza que deseja excluir o filme?");
            confirmacao.setContentText(selectedMovie.getTitle() + " será removido permanentemente.");
            if (confirmacao.showAndWait().get() == ButtonType.OK){
                new MovieService(new MovieDAO()).deleteMovie(selectedMovie.getId());
                loadData();
            }
        }

    }

    private void configureTableSelection() {
        // Listener para quando um item é selecionado
        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        buttonEdit.setVisible(true);
                        buttonDelete.setVisible(true);
                    }
                }
        );
    }


    @FXML
    private void showAllFilters(ActionEvent ev) {
        if (isSelected) {
            table.setLayoutY(130);
            filterPanel.setVisible(false);
            directorField.visibleProperty().setValue(false);
            directorField.setVisible(false);
            iconFilter.rotateProperty().setValue(0);
            isSelected = false;
        } else {
            table.setLayoutY(200);
            filterPanel.setVisible(true);
            directorField.setVisible(true);
            iconFilter.rotateProperty().setValue(180);
            isSelected = true;
        }
    }






}

