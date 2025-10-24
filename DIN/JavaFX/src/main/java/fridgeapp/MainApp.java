package fridgeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Frigorífico Inteligente");
        Image icon = new Image(getClass().getResourceAsStream("/fridgeapp/view/icon.png"));
        if (icon != null) stage.getIcons().add(icon);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/MenuPrincipal.fxml")));
        scene.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setWidth(720);
        stage.setHeight(520);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
