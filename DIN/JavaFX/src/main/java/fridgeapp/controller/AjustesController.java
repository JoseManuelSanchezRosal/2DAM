package fridgeapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class AjustesController {

    @FXML private TextField txtTemperatura;
    @FXML private ChoiceBox<String> cbModo;

    @FXML
    private void initialize() {
        cbModo.getItems().addAll("Normal", "Eco", "Vacaciones");
        cbModo.setValue("Normal");
        txtTemperatura.setText("4");
    }

    @FXML
    private void guardarAjustes() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajustes guardados");
        alert.setHeaderText(null);
        alert.setContentText("Ajustes guardados correctamente");
        alert.showAndWait();
    }

    @FXML
    private void volverMenu(ActionEvent event) throws Exception {
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene s = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/MenuPrincipal.fxml")));
        s.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(s);
    }
}
