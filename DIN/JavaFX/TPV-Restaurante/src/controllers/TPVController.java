package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import model.ProductoComanda; // Necesitas importar el modelo

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TPVController {

    // === REFERENCIAS FXML ===
    @FXML private TabPane tab;
    @FXML private ImageView mesa1;
    @FXML private ImageView mesa2;
    @FXML private ImageView mesa3;
    @FXML private ImageView mesa4;
    @FXML private AnchorPane comandaContainer; 

    // === GESTIÓN DE ESTADO ===
    private ComandaController comandaController;
    // Mapa central: "1" -> { "AGUA": ProductoComanda(agua), ... }
    // Almacena el estado de la comanda de cada mesa.
    private final Map<String, Map<String, ProductoComanda>> comandasAbiertas = new HashMap<>();
    private String mesaActiva; // Rastrea la mesa que se está mostrando actualmente

    @FXML
    public void initialize() {
        cargarVistaComanda();
    }
    
    private void cargarVistaComanda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ComandaView.fxml"));
            AnchorPane comandaView = loader.load();
            comandaController = loader.getController();

            comandaContainer.getChildren().add(comandaView);
            
            // Anclaje para ajuste 100%
            AnchorPane.setTopAnchor(comandaView, 0.0);
            AnchorPane.setBottomAnchor(comandaView, 0.0);
            AnchorPane.setLeftAnchor(comandaView, 0.0);
            AnchorPane.setRightAnchor(comandaView, 0.0);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR FATAL: No se pudo cargar ComandaView.fxml.");
        }
    }

    @FXML
    private void mesaClick(MouseEvent event) {
        if (comandaController != null) {
            ImageView mesaPulsada = (ImageView) event.getSource();
            String idMesa = mesaPulsada.getId();
            String numeroMesa = idMesa.replace("mesa", "");
            
            // 1. GUARDAR: Si hay una mesa activa, guardamos su comanda.
            if (mesaActiva != null) {
                // Obtenemos el estado actual del mapa del ComandaController
                Map<String, ProductoComanda> comandaGuardar = comandaController.getItemsComandaMesa();
                // Guardamos el mapa en nuestro almacén central usando el número de mesa como clave
                comandasAbiertas.put(mesaActiva, comandaGuardar);
            }

            // 2. CARGAR: Obtenemos el estado de la nueva mesa. Si no existe, creamos uno vacío.
            Map<String, ProductoComanda> comandaCargada = comandasAbiertas.getOrDefault(numeroMesa, new HashMap<>());

            // 3. ACTUALIZAR: Establecemos la nueva mesa activa
            this.mesaActiva = numeroMesa;
            
            // 4. INYECTAR DATOS: Llamamos al método actualizado del ComandaController
            comandaController.cargarDatosMesa(numeroMesa, comandaCargada);
            
        } else {
            System.err.println("Error: ComandaController no inicializado.");
        }
    }
}