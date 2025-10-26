/*
 * Controlador FXML para la vista Home.fxml
 * Se encarga de gestionar los eventos, el reloj en pantalla
 * y el control de temperatura del frigorífico virtual.
 */

package controller;

// Importaciones necesarias
import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador principal de la pantalla Home.
 * Implementa Initializable para ejecutar código al inicializar la vista.
 * 
 * @author Jose
 */
public class HomeController implements Initializable {

    // Referencias a elementos del FXML (vinculados mediante fx:id)
    @FXML
    private ImageView pFood;         // Icono o botón que lleva a la vista de alimentos
    @FXML
    private ImageView pHome;         // Icono del menú principal (actual)
    @FXML
    private ImageView pSettings;     // Icono o botón que lleva a la vista de ajustes
    @FXML
    private Label reloj;             // Etiqueta donde se muestra la hora actual
    @FXML
    private Label displayTemp;       // Etiqueta donde se muestra la temperatura actual
    @FXML
    private ImageView imgFrigo;      // Imagen del frigorífico
    @FXML
    private ImageView restar;        // Icono o botón para bajar la temperatura
    @FXML
    private ImageView sumar;         // Icono o botón para subir la temperatura
    @FXML
    private Label displayTemp1;      // (Parece no usarse, quizás etiqueta auxiliar)

    /**
     * Método para inicializar y actualizar el reloj en pantalla.
     * Crea un Timeline que actualiza la hora cada milisegundo.
     */
    public void inicializarReloj(){
        // Formato de hora (ejemplo: 17:22:53)
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Timeline que se ejecuta continuamente
        Timeline reloj = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            // Obtiene la hora actual del sistema
            LocalDateTime ahora = LocalDateTime.now();
            // Actualiza el texto del Label 'reloj' con la hora formateada
            this.reloj.setText(formatoHora.format(ahora));
        }));

        // Se repite de forma indefinida
        reloj.setCycleCount(Timeline.INDEFINITE);
        // Inicia el reloj
        reloj.play();
    }

    /**
     * Método que se ejecuta automáticamente al cargar la vista Home.fxml.
     * Se usa para inicializar componentes o datos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();       // Inicia el reloj digital
        actualizarTemperatura();  // Muestra la temperatura inicial
    }    
 
    /**
     * Evento que se ejecuta al hacer clic en el icono de alimentos.
     * Cambia la escena actual a la vista Food.fxml.
     */
    @FXML
    private void pGoFood(MouseEvent event) {
        // Obtiene la ventana actual
        Stage nuevaV = (Stage) pFood.getScene().getWindow();
        
        try {
            // Carga el nuevo archivo FXML
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Food.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Food");
            
            // Establece la nueva escena en la ventana y la muestra
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            // Captura errores de carga del FXML y los muestra en consola
            System.getLogger(FoodController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /**
     * Evento que se ejecuta al hacer clic en el icono de ajustes.
     * Cambia la escena actual a la vista Settings.fxml.
     */
    @FXML
    private void pGoSettings(MouseEvent event) {
        // Obtiene la ventana actual
        Stage nuevaV = (Stage) pSettings.getScene().getWindow();
        
        try {
            // Carga el nuevo archivo FXML
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Settings.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Settings");
            
            // Establece la nueva escena en la ventana y la muestra
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            // Captura errores de carga del FXML
            System.getLogger(SettingsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /**
     * Muestra en pantalla la temperatura actual almacenada en la clase DatosCompartidos.
     */
    private void actualizarTemperatura(){
        int tempActual = model.DatosCompartidos.getTemperatura();
        displayTemp.setText(String.valueOf(tempActual));
    }

    /**
     * Evento que se ejecuta al hacer clic en el icono para bajar la temperatura.
     * Disminuye la temperatura en 1 unidad, con límite mínimo de -2°C.
     */
    @FXML
    private void pRestarTemp(MouseEvent event) {
        if (model.DatosCompartidos.temperatura <= -2)
            return; // No baja más si ya está en el mínimo
        else {
            model.DatosCompartidos.temperatura--;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
    }

    /**
     * Evento que se ejecuta al hacer clic en el icono para subir la temperatura.
     * Aumenta la temperatura en 1 unidad, con límite máximo de 8°C.
     */
    @FXML
    private void pSumarTemp(MouseEvent event) {
        if (model.DatosCompartidos.temperatura >= 8)
            return; // No sube más si ya está en el máximo
        else {
            model.DatosCompartidos.temperatura++;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
    }
}