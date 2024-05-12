package com.company.furye;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Fourier series simulator");
        primaryStage.setScene(new Scene(root, 320, 240));

        // CSS dosyası
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
