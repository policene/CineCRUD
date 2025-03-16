package com.policene.cinecrud.controllers;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Gender;
import com.policene.cinecrud.entities.Movie;
import com.policene.cinecrud.service.MovieService;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    // Variáveis padrão para trocar de tela.
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isSelected = false;

    // Service.
    private final MovieService service = new MovieService(new MovieDAO());

    // Elementos FXML da TableView.
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
    private TableColumn<Movie, Integer> col_rating_bar = new TableColumn<>();

    // Elementos FXML da parte inferior.
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;

    // Elementos FXML do espaço de filtros.
    @FXML
    private AnchorPane filterPanel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField directorField;
    @FXML
    private TextField minRatingField;
    @FXML
    private TextField maxRatingField;
    @FXML
    private TextField minYearField;
    @FXML
    private TextField maxYearField;
    @FXML
    private ChoiceBox<Gender> genderField;


    // Elementos FXML da parte superior.
    @FXML
    private FontIcon iconButtonFilters;
    @FXML
    private Button buttonFilters;
    @FXML
    private Button buttonResetFilters;


    // Função de inicialização da cena.
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        configureTableSelection();
        loadData();
        configureToolTips();
        setupSearchFilters();

    }


    // Função que configura a Table View, colocando todas as colunas e relacionando com os atributos do objeto Movie.
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

                // Se houver um valor inválido, o gráfico não existe.
                if (empty || rating == null) {
                    setGraphic(null);
                } else {
                    // Caso contrário, ele cria a progress bar.
                    progressBar.setPrefWidth(80);
                    progressBar.setPrefHeight(11);


                    double progress = rating / 100.0;
                    String colorStyle;

                    if (rating <= 10) {
                        colorStyle = "-fx-accent: #8B0000;";
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


    // Função para exibir os botões de 'Editar' e 'Deletar' quando um filme for selecionado.
    private void configureTableSelection() {
        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        buttonEdit.setVisible(true);
                        buttonDelete.setVisible(true);
                    }
                }
        );
    }


    // Função para carregar os dados na TableView inicialmente.
    @FXML
    void loadData(){
        table.setItems(service.listAll());
    }


    // Função que configura as 'tips' de botões ao passar o mouse sobre.
    public void configureToolTips () {
        Tooltip tipButtonFilters = new Tooltip("Exibir mais filtros de pesquisa.");
        buttonFilters.setTooltip(tipButtonFilters);

        Tooltip tipResetButtonFilters = new Tooltip("Remover filtros de pesquisa.");
        buttonResetFilters.setTooltip(tipResetButtonFilters);
    }


    // Função que configura os fields de filtros.
    public void setupSearchFilters() {
        // A cada caractére digitado, ele vai chamar a função de performar uma busca combinada.
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });

        directorField.textProperty().addListener((observable, oldValue, newValue) -> {
            performCombinedSearch();
        });

        // Nos campos abaixo, além da chamada do performCombinedSearch(), os campos
        // são validados caso o 'min' seja maior que o 'max', vice-versa.
        minRatingField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                minRatingField.setStyle("");
                maxRatingField.setStyle("");
                if (newValue.isEmpty()){
                    minRatingField.setStyle("-fx-border-color: red;");
                } else {
                    int valor = Integer.parseInt(newValue);
                    int valorMaxRating = Integer.parseInt(maxRatingField.getText());
                    if (valor > valorMaxRating){
                        minRatingField.setStyle("-fx-border-color: red;");
                        maxRatingField.setStyle("-fx-border-color: red;");
                    } else {
                        performCombinedSearch();
                    }
                }
            } catch (Exception ex) {
                minRatingField.setText(oldValue);
            }
        });

        maxRatingField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                minRatingField.setStyle("");
                maxRatingField.setStyle("");
                if (newValue.isEmpty()){
                    maxRatingField.setStyle("-fx-border-color: red;");
                } else {
                    int valor = Integer.parseInt(newValue);
                    int valorMinRating = Integer.parseInt(minRatingField.getText());
                    if (valor < valorMinRating){
                        minRatingField.setStyle("-fx-border-color: red;");
                        maxRatingField.setStyle("-fx-border-color: red;");
                    } else {
                        performCombinedSearch();
                    }
                }
            } catch (Exception ex) {
                maxRatingField.setText(oldValue);
            }

        });


        minYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                minYearField.setStyle("");
                maxYearField.setStyle("");
                if (newValue.isEmpty() || Integer.parseInt(newValue) < 1895){
                    minYearField.setStyle("-fx-border-color: red;");
                } else {
                    int year = Integer.parseInt(newValue);
                    int maxYear = Integer.parseInt(maxYearField.getText());
                    if (year > maxYear){
                        minYearField.setStyle("-fx-border-color: red;");
                        maxYearField.setStyle("-fx-border-color: red;");
                    } else {
                        performCombinedSearch();
                    }
                }
            } catch (Exception ex) {
                minYearField.setText(oldValue);
            }

        });

        maxYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                minYearField.setStyle("");
                maxYearField.setStyle("");
                if (newValue.isEmpty() || Integer.parseInt(newValue) > Year.now().getValue()){
                    maxYearField.setStyle("-fx-border-color: red;");
                } else {
                    int year = Integer.parseInt(newValue);
                    int minYear = Integer.parseInt(minYearField.getText());
                    if (year < minYear){
                        minYearField.setStyle("-fx-border-color: red;");
                        maxYearField.setStyle("-fx-border-color: red;");
                    } else {
                        performCombinedSearch();
                    }
                }
            } catch (Exception ex) {
                maxYearField.setText(oldValue);
            }

        });

        // Aqui o ChoiceBox do Gender Field é populado, colocando as descrições de cada gênero.
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

        genderField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                performCombinedSearch();
            }
        });
    }


    // Função que faz a query contendo todas as informações presentes nos campos de busca.
    public void performCombinedSearch() {
        String titleQuery = titleField.getText().trim();
        String directorQuery = directorField.getText().trim();
        int minRating = Integer.parseInt(minRatingField.getText().trim());
        int maxRating = Integer.parseInt(maxRatingField.getText().trim());
        int minYear = Integer.parseInt(minYearField.getText().trim());
        int maxYear = Integer.parseInt(maxYearField.getText().trim());
        String gender = null;
        if (genderField.getValue() != null){
            gender = genderField.getValue().getDescription();
        }

        List<Movie> results = service.searchMovies(titleQuery, directorQuery, minRating, maxRating, minYear, maxYear, gender);

        ObservableList<Movie> observableResults = FXCollections.observableArrayList(results);
        table.setItems(observableResults);
    }


    // Função para o botão de 'Novo Filme'. Leva para outra janela.
    @FXML
    void moveToRegister(ActionEvent ev) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/movieRegister.fxml"));
        root = loader.load();
        stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    // Função para o botão de 'Editar Filme'. Leva para outra janela.
    @FXML
    void editMovie(ActionEvent ev) throws IOException {
        Movie movieSelected = table.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/policene/cinecrud/movieEdit.fxml"));
        root = loader.load();

        // Logo após carregar a página, ele usa a função setMovieToEdit
        // para preparar os campos de edição com os dados do filme.
        MovieEditController movieEditController = loader.getController();
        movieEditController.setMovieToEdit(movieSelected);

        stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    // Função para o botão de deletar um filme selecionado.
    @FXML
    void deleteMovie(){
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


    // Função para o botão que abre a tela com todos os filtros de pesquisa disponíveis.
    @FXML
    private void showAllFilters() {
        if (isSelected) {
            buttonFilters.setStyle("-fx-background-insets: 0;");
            table.setLayoutY(130);
            filterPanel.setVisible(false);
            directorField.visibleProperty().setValue(false);
            directorField.setVisible(false);
            iconButtonFilters.rotateProperty().setValue(0);
            buttonFilters.setStyle("-fx-background-color:  #d30101");
            isSelected = false;
        } else {
            table.setLayoutY(180);
            filterPanel.setVisible(true);
            directorField.setVisible(true);
            iconButtonFilters.rotateProperty().setValue(180);
            buttonFilters.setStyle("-fx-background-insets: 0;");
            buttonFilters.setStyle("-fx-background-color:  #8a0108");
            isSelected = true;
        }
    }


    // Função para o botão que limpa os filtros de pesquisa para o padrão.
    @FXML
    private void clearAllFilters() {
        titleField.setText("");
        directorField.setText("");
        minRatingField.setText("0");
        maxRatingField.setText("100");
        minYearField.setText("1895");
        maxYearField.setText(Year.now().toString());
        genderField.setValue(null);
        loadData();
    }


}

