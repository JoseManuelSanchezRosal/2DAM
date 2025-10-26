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
    @FXML
    private Label avisos;
    @FXML
    private Label displayTemp1;
    

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
        if (model.datosCompartidos.temperatura <= -2)
            return;
        else{
            model.datosCompartidos.temperatura--;
            displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
        }
        
    }

    @FXML
    private void fSumarTemp(MouseEvent event) {
        if(model.datosCompartidos.temperatura >=8)
            return;
        else{
            model.datosCompartidos.temperatura++;
            displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
        }
    }

    @FXML
    
    // MÉTODO PARA AGREGAR ALIMENTO A LA TABLA:
    private void agregarNuevoAlimento(MouseEvent event) {
        String nombreAlimento = alimentoCrud.getText();
        String cantidadTexto = cantidadCrud.getText();

        // Quitar espacios antes de validar
        nombreAlimento = nombreAlimento.trim();
        cantidadTexto = cantidadTexto.trim();

        // Validar campos vacíos o solo con espacios
        if (nombreAlimento.isBlank() && cantidadTexto.isBlank()) {
            avisos.setText("Ingrese alimento y su cantidad por favor...");
            return;
        }
        if (nombreAlimento.isBlank()){
            avisos.setText("Ingrese nombre del alimento por favor...");
            return;
        }
        if (cantidadTexto.isBlank()){
            avisos.setText("Ingrese cantidad del alimento por favor...");
            return;
        }

        int cantidadAlimento;

        // Validar que la cantidad sea numérica
        try {
            cantidadAlimento = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            avisos.setText("La cantidad debe ser un número válido.");
            return;
        }

        // Validar cantidad negativa
        if (cantidadAlimento < 0) {
            avisos.setText("La cantidad no puede ser negativa.");
            return;
        }

        // Crear alimento e insertar en la lista
        model.Alimento alimentoInsertar = new Alimento(nombreAlimento, cantidadAlimento);
        model.datosCompartidos.getAlimentosFrigo().add(alimentoInsertar);

        // Limpiar campos y avisos
        alimentoCrud.setText("");
        cantidadCrud.setText("");
        avisos.setText("Alimento agregado correctamente.");
    }


    // MÉTODO PARA MODIFICAR UN ALIMENTO DE LA TABLA:
    @FXML
    private void modificarAlimentoLista(MouseEvent event) {
        // Obtener el nombre del alimento que se quiere modificar
        String nombreBuscar = alimentoAModificar.getText().trim();

        // Validar que se haya indicado el alimento a modificar
        if (nombreBuscar.isBlank()) {
            avisos.setText("Ingrese nombre del alimento a modificar.");
            return;
        }
        // Buscar el alimento en la lista
        model.Alimento alimentoModificar = null;
        for (model.Alimento alimento : model.datosCompartidos.getAlimentosFrigo()) {
            if (alimento.getNombre().equalsIgnoreCase(nombreBuscar)) {
                alimentoModificar = alimento;
                break;
            }
        }
        // Si no se encontró el alimento
        if (alimentoModificar == null) {
            avisos.setText("No se encontró un alimento con ese nombre.");
            return;
        }
        // Recoger los nuevos valores de los textfields
        String nuevoNombre = alimentoCrud.getText().trim();
        String cantidadTexto = cantidadCrud.getText().trim();

        // Validar campos vacíos o en blanco
        if (nuevoNombre.isBlank() && cantidadTexto.isBlank()) {
            avisos.setText("Ingrese nombre y cantidad para modificar.");
            return;
        }
        if (nuevoNombre.isBlank()) {
            avisos.setText("Ingrese el nuevo nombre del alimento.");
            return;
        }
        if (cantidadTexto.isBlank()) {
            avisos.setText("Ingrese la nueva cantidad.");
            return;
        }
        
        int nuevaCantidad;
        
        // Validar que la cantidad sea numérica
        try {
            nuevaCantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            avisos.setText("La cantidad debe ser un número válido.");
            return;
        }

        // Validar cantidad mayor o igual a 1
        if (nuevaCantidad < 1) {
            avisos.setText("La cantidad no puede ser negativa.");
            return;
        }

        // Modificar el alimento
        alimentoModificar.setNombre(nuevoNombre);
        alimentoModificar.setCantidad(nuevaCantidad);

        // Actualizar la tabla
        tablaAlimentos.refresh();

        // Limpiar campos y avisos
        alimentoCrud.setText("");
        cantidadCrud.setText("");
        alimentoAModificar.setText("");
        avisos.setText("Alimento modificado correctamente.");
    }

    // MÉTODO PARA ELIMINAR ALIMENTO DE LA TABLA (OBSERVABLELIST:
    @FXML
    private void eliminarAlimentoLista(MouseEvent event) {
        // Verificar si hay alimentos en la lista
        if (model.datosCompartidos.getAlimentosFrigo().isEmpty()) {
            avisos.setText("No hay alimentos en la lista para eliminar.");
            return;
        }

        // Obtener el alimento seleccionado
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();

        // Verificar que haya una selección
        if (alimentoSeleccionado == null) {
            avisos.setText("Seleccione un alimento de la tabla para eliminarlo.");
            return;
        }

        try {
            // Eliminar el alimento de la lista
            model.datosCompartidos.getAlimentosFrigo().remove(alimentoSeleccionado);

            // Actualizar la tabla
            tablaAlimentos.refresh();

            // Limpiar campos
            alimentoCrud.setText("");
            cantidadCrud.setText("");
            alimentoAModificar.setText("");

            // Mostrar confirmación
            avisos.setText("Alimento eliminado correctamente.");
        } catch (Exception e) {
            avisos.setText("Error al eliminar el alimento: " + e.getMessage());
        }
    }

    // MÉTODO PARA OBTENER EL ALIMENTO SELECCIONADO DE LA TABLA:
    private void obtenerAlimentoTabla(){
        
        // Obtener el alimento seleccionado de la tabla
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        
        if (alimentoSeleccionado == null) {
            return;
        }
        
        // Guardar el alimento en los textFields
        alimentoCrud.setText(alimentoSeleccionado.getNombre());
        cantidadCrud.setText(String.valueOf(alimentoSeleccionado.getCantidad()));
    }
}