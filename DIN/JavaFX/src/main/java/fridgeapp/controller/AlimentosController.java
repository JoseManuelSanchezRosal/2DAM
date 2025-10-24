package fridgeapp.controller;

import fridgeapp.model.Alimento;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AlimentosController implements Initializable {

    @FXML private TableView<Alimento> tablaAlimentos;
    @FXML private TableColumn<Alimento, String> colNombre;
    @FXML private TableColumn<Alimento, String> colTipo;
    @FXML private TableColumn<Alimento, Number> colCantidad;
    @FXML private TableColumn<Alimento, String> colUnidad;

    private static ObservableList<Alimento> alimentos = FXCollections.observableArrayList();

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colTipo.setCellValueFactory(cell -> cell.getValue().tipoProperty());
        colCantidad.setCellValueFactory(cell -> cell.getValue().cantidadProperty());
        colUnidad.setCellValueFactory(cell -> cell.getValue().unidadProperty());
        tablaAlimentos.setItems(alimentos);

        // example data
        if (alimentos.isEmpty()) {
            alimentos.addAll(
                new Alimento("Leche", "Lácteo", 1.0, "L"),
                new Alimento("Huevos", "Proteína", 6, "unidades"),
                new Alimento("Manzanas", "Fruta", 1.2, "kg")
            );
        }
    }

    @FXML
    private void agregarAlimento(ActionEvent event) throws Exception {
        abrirFormulario(event, null);
    }

    @FXML
    private void modificarAlimento(ActionEvent event) throws Exception {
        Alimento seleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            abrirFormulario(event, seleccionado);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Selecciona un alimento para modificar.", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void eliminarAlimento() {
        Alimento seleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) alimentos.remove(seleccionado);
    }

    @FXML
    private void volverMenu(ActionEvent event) throws Exception {
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene s = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/MenuPrincipal.fxml")));
        s.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(s);
    }

    private void abrirFormulario(ActionEvent event, Alimento alimento) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fridgeapp/view/AlimentoForm.fxml"));
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        AlimentoFormController controller = loader.getController();
        controller.setAlimento(alimento, alimentos);
        stage.setScene(scene);
    }
}
