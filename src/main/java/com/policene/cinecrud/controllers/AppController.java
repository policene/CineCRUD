package com.policene.cinecrud.controllers;

import com.policene.cinecrud.dao.MovieDAO;
import com.policene.cinecrud.entities.Movie;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button searchButton;

    @FXML
    private TextField titleField;

    @FXML
    private AnchorPane lateralbar;

    @FXML
    private TableView<Movie> table = new TableView<Movie>();

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
    private TableColumn<Movie, Integer> col_rating = new TableColumn<>();


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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        col_title.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTitle()));
        col_director.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDirector()));
        col_gender.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGender()));
        col_year.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getYear()));
        col_rating.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRating()));
        table.setItems(new MovieDAO().showAll());
    }

    @FXML
    void searchByTitle(ActionEvent event) {
        String termoBusca = titleField.getText();
        List<Movie> resultados = new MovieDAO().listByTitle(termoBusca);

        // Converte a lista para ObservableList e atualiza a TableView
        ObservableList<Movie> resultadosObservaveis = FXCollections.observableArrayList(resultados);
        table.setItems(resultadosObservaveis);
    }


}

