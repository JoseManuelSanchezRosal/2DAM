/*
 * Controlador JavaFX que gestiona la vista de alimentos (CRUD de un frigorífico).
 * Permite agregar, modificar, eliminar alimentos y controlar la temperatura.
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

public class FoodController implements Initializable {
    // Elementos visuales de la interfaz, vinculados con el FXML
    @FXML
    private Label reloj;                  // Muestra la hora actual
    @FXML
    private Label displayTemp;            // Muestra la temperatura actual del frigorífico
    @FXML
    private Button agregarCrud;           // Botón para agregar alimento
    @FXML
    private Button modificarCrud;         // Botón para modificar alimento
    @FXML
    private Button eliminarCrud;          // Botón para eliminar alimento
    @FXML
    private TextField alimentoCrud;       // Campo de texto para el nombre del alimento
    @FXML
    private TextField cantidadCrud;       // Campo de texto para la cantidad del alimento
    @FXML
    private ImageView fFood;              // Icono del apartado de alimentos
    @FXML
    private ImageView fHome;              // Icono para volver a la vista principal
    @FXML
    private ImageView fSettings;          // Icono para ir a ajustes
    @FXML
    private ImageView restar;             // Botón para disminuir temperatura
    @FXML
    private ImageView sumar;              // Botón para aumentar temperatura
    @FXML
    private TableView<model.Alimento> tablaAlimentos; // Tabla que muestra los alimentos
    @FXML
    private TableColumn<?, ?> colAlimento;            // Columna para nombre del alimento
    @FXML
    private TableColumn<?, ?> colCantidad;            // Columna para cantidad del alimento
    @FXML
    private TextField alimentoAModificar;             // Campo para indicar alimento a modificar
    @FXML
    private Label avisos;                             // Muestra mensajes de validación o éxito
    @FXML
    private Label displayTemp1;                       // (Probablemente no se usa)

    // Se ejecuta automáticamente al cargar la vista FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();       // Inicia el reloj en pantalla
        actualizarTemperatura();  // Muestra temperatura actual del frigorífico
        
        // Asocia las columnas de la tabla con los atributos de la clase Alimento
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        // Carga la lista observable de alimentos (compartida entre controladores)
        tablaAlimentos.setItems(model.DatosCompartidos.getAlimentosFrigo());
    }    
    
    // Crea un reloj digital que se actualiza cada segundo
    public void inicializarReloj() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Timeline ejecuta una acción repetidamente cada segundo
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime ahora = LocalDateTime.now();   // Obtiene hora actual
            this.reloj.setText(dtf.format(ahora));       // Actualiza el label
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);  // Se repite indefinidamente
        reloj.play();                              // Inicia el reloj
    }
    
    // Método que cambia a la escena principal (Home)
    @FXML
    private void fGoHome(MouseEvent event) {
        Stage nuevaV = (Stage) fHome.getScene().getWindow(); // Obtiene ventana actual
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Home.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Home");
            nuevaV.setScene(scene);
            nuevaV.show(); // Muestra la nueva escena
        } catch (IOException ex) {
            System.getLogger(SettingsController.class.getName())
                .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    // Cambia a la vista de Ajustes
    @FXML
    private void fGoSettings(MouseEvent event) {
        Stage nuevaV = (Stage) fSettings.getScene().getWindow();
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Settings.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Settings");
            nuevaV.setScene(scene);
            nuevaV.show();
        } catch (IOException ex) {
            System.getLogger(SettingsController.class.getName())
                .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    // Muestra la temperatura actual del frigorífico en pantalla
    private void actualizarTemperatura() {
        int tempActual = model.DatosCompartidos.getTemperatura();
        displayTemp.setText(String.valueOf(tempActual));
    }

    // Disminuye la temperatura (mínimo -2 °C)
    @FXML
    private void fRestarTemp(MouseEvent event) {
        if (model.DatosCompartidos.temperatura <= -2)
            return; // No baja más de -2
        else {
            model.DatosCompartidos.temperatura--;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
    }

    // Aumenta la temperatura (máximo 8 °C)
    @FXML
    private void fSumarTemp(MouseEvent event) {
        if (model.DatosCompartidos.temperatura >= 8)
            return; // No sube más de 8
        else {
            model.DatosCompartidos.temperatura++;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
    }
    // ============================ CRUD DE ALIMENTOS ============================

    // MÉTODO PARA AGREGAR UN NUEVO ALIMENTO A LA LISTA Y TABLA
    @FXML
    private void agregarNuevoAlimento(MouseEvent event) {
        String nombreAlimento = alimentoCrud.getText().trim();// Elimina espacios
        String cantidadTexto = cantidadCrud.getText().trim();

        // Validaciones de campos vacíos
        if (nombreAlimento.isBlank() && cantidadTexto.isBlank()) {
            avisos.setText("Ingrese alimento y su cantidad por favor...");
            return;
        }
        if (nombreAlimento.isBlank()) {
            avisos.setText("Ingrese nombre del alimento por favor...");
            return;
        }
        if (cantidadTexto.isBlank()) {
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
        // No se permite cantidad negativa
        if (cantidadAlimento < 0) {
            avisos.setText("La cantidad no puede ser negativa.");
            return;
        }
        // Crea el objeto Alimento y lo agrega a la lista observable
        model.Alimento alimentoInsertar = new Alimento(nombreAlimento, cantidadAlimento);
        model.DatosCompartidos.getAlimentosFrigo().add(alimentoInsertar);

        // Limpia campos y muestra mensaje de éxito
        alimentoCrud.setText("");
        cantidadCrud.setText("");
        avisos.setText("Alimento agregado correctamente.");
    }

    // MÉTODO PARA MODIFICAR UN ALIMENTO EXISTENTE
    @FXML
    private void modificarAlimentoLista(MouseEvent event) {
        String nombreBuscar = alimentoAModificar.getText().trim(); // Nombre del alimento a buscar

        if (nombreBuscar.isBlank()) {
            avisos.setText("Ingrese nombre del alimento a modificar.");
            return;
        }
        // Buscar alimento por nombre (ignorando mayúsculas)
        model.Alimento alimentoModificar = null;
        for (model.Alimento alimento : model.DatosCompartidos.getAlimentosFrigo()) {
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
        // Obtener nuevos valores
        String nuevoNombre = alimentoCrud.getText().trim();
        String cantidadTexto = cantidadCrud.getText().trim();

        // Validar campos vacíos
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
        
        // Comprobamos que la cantidad sea un número válido
        try {
            nuevaCantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            avisos.setText("La cantidad debe ser un número válido.");
            return;
        }
        if (nuevaCantidad < 1) {
            avisos.setText("La cantidad no puede ser negativa.");
            return;
        }
        
        // Actualiza los datos del alimento
        alimentoModificar.setNombre(nuevoNombre);
        alimentoModificar.setCantidad(nuevaCantidad);

        // Refresca la tabla para mostrar cambios
        tablaAlimentos.refresh();

        // Limpia campos
        alimentoCrud.setText("");
        cantidadCrud.setText("");
        alimentoAModificar.setText("");
        avisos.setText("Alimento modificado correctamente.");
    }

    // MÉTODO PARA ELIMINAR UN ALIMENTO SELECCIONADO EN LA TABLA
    @FXML
    private void eliminarAlimentoLista(MouseEvent event) {
        // Si no hay alimentos
        if (model.DatosCompartidos.getAlimentosFrigo().isEmpty()) {
            avisos.setText("No hay alimentos en la lista para eliminar.");
            return;
        }
        // Obtiene el alimento seleccionado
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();

        if (alimentoSeleccionado == null) {
            avisos.setText("Seleccione un alimento de la tabla para eliminarlo.");
            return;
        }
        try {
            // Elimina el alimento de la lista observable
            model.DatosCompartidos.getAlimentosFrigo().remove(alimentoSeleccionado);
            tablaAlimentos.refresh(); // Refresca tabla

            // Limpia campos
            alimentoCrud.setText("");
            cantidadCrud.setText("");
            alimentoAModificar.setText("");

            avisos.setText("Alimento eliminado correctamente.");
        } catch (Exception e) {
            avisos.setText("Error al eliminar el alimento: " + e.getMessage());
        }
    }
    // MÉTODO PARA RELLENAR LOS CAMPOS CON EL ALIMENTO SELECCIONADO DE LA TABLA
    private void obtenerAlimentoTabla() {
        model.Alimento alimentoSeleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();

        if (alimentoSeleccionado == null) {
            return; // Si no hay selección, no hace nada
        }
        // Carga los datos del alimento en los textfields para editarlos
        alimentoCrud.setText(alimentoSeleccionado.getNombre());
        cantidadCrud.setText(String.valueOf(alimentoSeleccionado.getCantidad()));
    }
}