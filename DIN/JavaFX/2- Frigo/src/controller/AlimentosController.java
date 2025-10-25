/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Alimento;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class AlimentosController implements Initializable {

    @FXML
    private Label reloj;
    @FXML
    private Label displayTemp;
    @FXML
    private Button agregarCrud;
    @FXML
    private Button modificarCrud;
    @FXML
    private Button eliminarCrud;
    @FXML
    private TextField alimentoCrud;
    @FXML
    private TextField cantidadCrud;
    @FXML
    private ImageView fFood;
    @FXML
    private ImageView fHome;
    @FXML
    private ImageView fSettings;
    @FXML
    private ImageView restar;
    @FXML
    private ImageView sumar;
    @FXML
    private TableView<model.Alimento> tablaAlimentos;
    @FXML
    private TableColumn<?, ?> colAlimento;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TextField alimentoAModificar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();
        actualizarTemperatura();
        
        // Vincular columnas de la tabla a los atributos de tu clase Alimento
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        // Enlazar la tabla con la lista observable
        tablaAlimentos.setItems(model.datosCompartidos.getAlimentosFrigo());
            }    
    
    public void inicializarReloj(){
        // Formato de hora y fecha
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        
        // Timeline que se ejecuta cada segundo
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime ahora = LocalDateTime.now();
            this.reloj.setText(formatoHora.format(ahora));
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    private void cambiarVistaPrincipal(MouseEvent event) {
        
    }

    @FXML
    private void fGoHome(MouseEvent event) {
        Stage nuevaV = (Stage) fHome.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/principal.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Principal");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(AjustesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }

    @FXML
    private void fGoSettings(MouseEvent event) {
        Stage nuevaV = (Stage) fSettings.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/ajustes.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Ajustes");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(AjustesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void actualizarTemperatura(){
        int tempActual = model.datosCompartidos.getTemperatura();
        displayTemp.setText(String.valueOf(tempActual));
    }

    @FXML
    private void fRestarTemp(MouseEvent event) {
        model.datosCompartidos.temperatura--;
        displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
    }

    @FXML
    private void fSumarTemp(MouseEvent event) {
        model.datosCompartidos.temperatura++;
        displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
    }

    @FXML
    private void agregarNuevoAlimento(MouseEvent event) {
        
        // Recoger nombre y cantidad introducida en los textFields
        String nombreAlimento = alimentoCrud.getText();
        int cantidadAlimento = Integer.parseInt(cantidadCrud.getText());
        
        // Comprobar que no esté vacío ninguno de los dos
        if (nombreAlimento.equals("") || cantidadAlimento < 0) {
            return;
        }
        
        // Crear alimento e insertar en la lista
        model.Alimento alimentoInsertar = new Alimento(nombreAlimento, cantidadAlimento);
        model.datosCompartidos.getAlimentosFrigo().add(alimentoInsertar);
        
        // Una vez introducido, resetear los textFields
        alimentoCrud.setText("");
        cantidadCrud.setText("");
    }

    @FXML
    private void modificarAlimentoLista(MouseEvent event) {
        
        // Alimento en null para rellenarlo si se encuentra en la lista
        model.Alimento alimentoModificar = null;
        
        // Buscarlo y rellenarlo si está en la lista.
        for(model.Alimento alimento : model.datosCompartidos.getAlimentosFrigo()){
            if (alimentoAModificar.getText().equals(alimento.getNombre())) {
                alimentoModificar = alimento;
            }
        }
        
        // Recoger nombre y cantidad introducida en los textFields 
        String nuevoNombre = alimentoCrud.getText();
        int nuevaCantidad = Integer.parseInt(cantidadCrud.getText());
                
        // Comprobar que no esté vacío ninguno de los dos
        if (nuevoNombre.equals("") || nuevaCantidad < 0) {
            return;
        }
        
        // Comprobar que el alimento se ha encontrado
        if (alimentoModificar == null) {
            return;
        }
        
        // Modificarle el nombre y cantidad
        alimentoModificar.setNombre(nuevoNombre);
        alimentoModificar.setCantidad(nuevaCantidad);
        
        // Actualizar la tabla
        tablaAlimentos.refresh();
        
        alimentoCrud.setText("");
        cantidadCrud.setText("");
        alimentoAModificar.setText("");
        
    }

    @FXML
    private void eliminarAlimentoLista(MouseEvent event) {
        
        // Obtener el alimento seleccionado
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        
        if (alimentoSeleccionado != null) {
            // Eliminarlo de la lista
            model.datosCompartidos.getAlimentosFrigo().remove(alimentoSeleccionado);
            
            tablaAlimentos.refresh();
        }else{
            return;
        }
        
    }
    
    private void obtenerAlimentoTabla(){
        // Obtener el alimento seleccionado de la tabla
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        
        if (alimentoSeleccionado == null) {
            return;
        }
        
        // Guardar el alimenmto en los textFields
        alimentoCrud.setText(alimentoSeleccionado.getNombre());
        cantidadCrud.setText(String.valueOf(alimentoSeleccionado.getCantidad()));
    }
    
}