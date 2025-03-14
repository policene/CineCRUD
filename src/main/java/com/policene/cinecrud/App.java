package com.policene.cinecrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CineCRUD");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2 );
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2 );
    }

    public static void main(String[] args) {
        launch();
    }

}