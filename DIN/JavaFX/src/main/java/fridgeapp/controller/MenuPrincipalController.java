package fridgeapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuPrincipalController {

    @FXML
    private void irAjustes(ActionEvent event) throws Exception {
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene s = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/Ajustes.fxml")));
        s.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(s);
    }

    @FXML
    private void irAlimentos(ActionEvent event) throws Exception {
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene s = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/Alimentos.fxml")));
        s.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(s);
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}
