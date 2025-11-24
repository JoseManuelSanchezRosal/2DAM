package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import java.io.IOException;

public class TPVController {

    // Referencias FXML declaradas correctamente
    @FXML private TabPane tab;
    @FXML private ImageView mesa1;
    @FXML private ImageView mesa2;
    @FXML private ImageView mesa3;
    @FXML private ImageView mesa4;
    @FXML private AnchorPane comandaContainer; // Contenedor para inyectar ComandaView.fxml

    // Referencia al controlador de la comanda (vista reutilizable)
    private ComandaController comandaController;

    // Se ejecuta al inicio, carga la vista de la comanda y la inyecta.
    @FXML
    public void initialize() {
        cargarVistaComanda();
    }
    
    // 1. Método para cargar e inyectar ComandaView.fxml
    private void cargarVistaComanda() {
        try {
            // Cargar el FXML de la comanda
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ComandaView.fxml"));
            
            // Cargar el nodo raíz de ComandaView.fxml (será un AnchorPane o Pane)
            AnchorPane comandaView = loader.load(); 
            
            // Guardar la referencia al controlador de la comanda
            comandaController = loader.getController();

            // Insertar la vista cargada dentro del contenedor en TPV.fxml
            comandaContainer.getChildren().add(comandaView);
            
            // Anclar el contenido para que se ajuste 100% al tamaño del comandaContainer.
            javafx.scene.layout.AnchorPane.setTopAnchor(comandaView, 0.0);
            javafx.scene.layout.AnchorPane.setBottomAnchor(comandaView, 0.0);
            javafx.scene.layout.AnchorPane.setLeftAnchor(comandaView, 0.0);
            javafx.scene.layout.AnchorPane.setRightAnchor(comandaView, 0.0);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR FATAL: No se pudo cargar ComandaView.fxml. Revise la ruta y el archivo.");
        }
    }

    // 2. Manejador de evento (solo actualiza el contenido de la comanda).
    @FXML
    private void mesaClick(MouseEvent event) {
        if (comandaController != null) {
            // Identificar qué mesa fue pulsada usando el fx:id
            ImageView mesaPulsada = (ImageView) event.getSource();
            String idMesa = mesaPulsada.getId(); // Devuelve "mesa1", "mesa2", etc.
            String numeroMesa = idMesa.replace("mesa", ""); 
            
            // Llama al método del controlador de la comanda para cambiar los datos
            comandaController.cargarDatosMesa(numeroMesa);
            
            // Opcional: Cambiar automáticamente a la pestaña "restaurante" si no lo está
            // tab.getSelectionModel().select(tab.getTabs().get(2)); 
            
        } else {
            System.err.println("Error: ComandaController no inicializado. ¿Se cargó ComandaView.fxml?");
        }
    }
}