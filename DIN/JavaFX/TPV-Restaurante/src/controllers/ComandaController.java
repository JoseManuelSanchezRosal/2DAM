package controllers;


import model.Producto;
import model.ProductoComanda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.TilePane;

public class ComandaController {

    @FXML 
    private Label labelMesaActual; 
    // Añadir aquí otras referencias FXML de ComandaView.fxml (TableView, TilePane, botones)
    @FXML
    private TableView<?> tablaComanda;
    @FXML
    private TableColumn<?, ?> columnaProducto;
    @FXML
    private TableColumn<?, ?> columnaCantidad;
    @FXML
    private TableColumn<?, ?> columnaSubtotal;
    @FXML
    private Label labelTotalComanda;
    @FXML
    private TilePane panelProductos;

    // Método requerido para recibir el ID de la mesa
    public void cargarDatosMesa(String numeroMesa) {
        // Actualizar la etiqueta de la mesa
        if (labelMesaActual != null) {
            labelMesaActual.setText("Mesa: " + numeroMesa);
        }
        
        // Aquí irá toda la lógica para cargar los productos de la Mesa 'numeroMesa'
        System.out.println("Cargando comanda para la Mesa: " + numeroMesa);
    }
    
    // Debes añadir aquí todos los métodos de acción (@FXML) que enlazaste en ComandaView.fxml:
    // sacarTicket(), reiniciarMesa(), guardarComanda(), etc.

    @FXML
    private void eliminarProducto(ActionEvent event) {
    }

    @FXML
    private void guardarComanda(ActionEvent event) {
    }

    @FXML
    private void reiniciarMesa(ActionEvent event) {
    }

    @FXML
    private void sacarTicket(ActionEvent event) {
    }

    @FXML
    private void cerrarComanda(ActionEvent event) {
    }
}