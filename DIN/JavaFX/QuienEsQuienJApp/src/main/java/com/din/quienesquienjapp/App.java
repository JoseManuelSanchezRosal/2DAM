package com.din.quienesquienjapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase principal que lanza la aplicación JavaFX
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // CORRECCIÓN: Aquí cargamos "game" en lugar de "primary"
        // También definimos un tamaño inicial de ventana (900x600)
        scene = new Scene(loadFXML("game"), 900, 600);
        stage.setTitle("Quién es Quién - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Esto busca el archivo .fxml en la carpeta de recursos correspondiente
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}