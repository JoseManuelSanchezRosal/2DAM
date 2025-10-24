package fridgeapp.controller;

import fridgeapp.model.Alimento;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlimentoFormController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtTipo;
    @FXML private TextField txtCantidad;
    @FXML private ChoiceBox<String> cbUnidad;

    private Alimento alimento;
    private ObservableList<Alimento> lista;

    @FXML
    private void initialize() {
        cbUnidad.getItems().addAll("kg","g","unidades","L");
        cbUnidad.setValue("kg");
    }

    public void setAlimento(Alimento alimento, ObservableList<Alimento> lista) {
        this.alimento = alimento;
        this.lista = lista;
        if (alimento != null) {
            txtNombre.setText(alimento.getNombre());
            txtTipo.setText(alimento.getTipo());
            txtCantidad.setText(String.valueOf(alimento.getCantidad()));
            cbUnidad.setValue(alimento.getUnidad());
        }
    }

    @FXML
    private void guardar(ActionEvent event) throws Exception {
        String nombre = txtNombre.getText().trim();
        String tipo = txtTipo.getText().trim();
        double cantidad = 0;
        try {
            cantidad = Double.parseDouble(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Cantidad no válida.", ButtonType.OK);
            a.showAndWait();
            return;
        }
        String unidad = cbUnidad.getValue();

        if (nombre.isEmpty() || tipo.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Rellena todos los campos.", ButtonType.OK);
            a.showAndWait();
            return;
        }

        if (alimento == null) {
            lista.add(new Alimento(nombre, tipo, cantidad, unidad));
        } else {
            alimento.setNombre(nombre);
            alimento.setTipo(tipo);
            alimento.setCantidad(cantidad);
            alimento.setUnidad(unidad);
        }

        volver(event);
    }

    @FXML
    private void cancelar(ActionEvent event) throws Exception {
        volver(event);
    }

    private void volver(ActionEvent event) throws Exception {
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene s = new Scene(FXMLLoader.load(getClass().getResource("/fridgeapp/view/Alimentos.fxml")));
        s.getStylesheets().add(getClass().getResource("/fridgeapp/view/style.css").toExternalForm());
        stage.setScene(s);
    }
}
