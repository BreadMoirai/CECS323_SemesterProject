package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("HelO wOrLD");
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
