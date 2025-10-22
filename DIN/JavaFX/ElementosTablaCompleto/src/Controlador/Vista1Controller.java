/*
 * Controlador JavaFX para gestionar una tabla de personas (nombre, apellidos, edad)
 * Permite agregar, modificar, eliminar y seleccionar personas dentro de una TableView.
 */

package Controlador;

// Importaciones necesarias
import Modelo.persona; // Importa la clase 'persona' desde el paquete Modelo
import static java.lang.Integer.parseInt; // Permite usar 'parseInt' sin escribir Integer.parseInt
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Controlador FXML para la vista Vista1.fxml
 * Gestiona las acciones sobre la interfaz (botones, campos de texto y tabla)
 * 
 * @author pedro
 */
public class Vista1Controller implements Initializable {

    // Campos de texto del formulario, enlazados con el FXML mediante @FXML
    @FXML
    private TextField tNombre;     // Campo para introducir el nombre
    @FXML
    private TextField tEdad;       // Campo para introducir la edad
    @FXML
    private TextField tApellidos;  // Campo para introducir los apellidos

    // Tabla donde se mostrarán los objetos 'persona'
    @FXML
    private TableView<persona> tablaPers;

    // Columnas de la tabla
    @FXML
    private TableColumn<?, ?> colNombre;     // Columna para el nombre
    @FXML
    private TableColumn<?, ?> colApellidos;  // Columna para los apellidos
    @FXML
    private TableColumn<?, ?> colEdad;       // Columna para la edad

    // Botones de acción
    @FXML
    private Button btn;       // Botón para agregar una persona
    @FXML
    private Button btnMod;    // Botón para modificar una persona
    @FXML
    private Button btnEliminar; // Botón para eliminar una persona

    // Lista observable que almacena los objetos persona (sirve como modelo de datos de la tabla)
    private ObservableList<persona> gpersonas;

    /**
     * Método que se ejecuta automáticamente al iniciar la vista (cuando se carga el FXML)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se inicializa la lista observable vacía
        gpersonas = FXCollections.observableArrayList();
        
        // Enlazamos las columnas de la tabla con los atributos de la clase 'persona'
        // Cada columna mostrará el valor correspondiente a su propiedad
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
    }    

    /**
     * Método para agregar una nueva persona a la tabla.
     * Se ejecuta al hacer clic en el botón "Agregar"
     */
    @FXML
    private void agregar(MouseEvent event) {
        // Se obtienen los valores introducidos en los campos de texto
        String aNombre = tNombre.getText();
        String aApellidos = tApellidos.getText();
        int aEdad = parseInt(tEdad.getText()); // Convierte el texto a número entero
        
        // Se crea un nuevo objeto persona con los datos introducidos
        persona elemento = new persona(aNombre, aApellidos, aEdad);
        
        // Se añade la persona a la lista observable
        gpersonas.add(elemento);
        
        // Se actualiza la tabla con la lista actualizada
        tablaPers.setItems(gpersonas);
    }

    /**
     * Método para modificar los datos de una persona seleccionada en la tabla.
     * Se ejecuta al hacer clic en el botón "Modificar"
     */
    @FXML
    private void modificarPers(MouseEvent event) {
        
        // Se obtiene la persona actualmente seleccionada en la tabla
        persona aux = tablaPers.getSelectionModel().getSelectedItem();
        
        // Si no hay ninguna persona seleccionada, se muestra un mensaje de error
        if(aux==null){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("La persona seleccionada no existe en la tabla");
            alerta.showAndWait();
        }else{
            // Si hay una persona seleccionada, se obtienen los nuevos valores de los campos
            String aNombre = tNombre.getText();
            String aApellidos = tApellidos.getText();
            int aEdad = parseInt(tEdad.getText());

            // Se crea un nuevo objeto persona con los nuevos datos
            persona elemento = new persona(aNombre, aApellidos, aEdad);
            
            // Si la lista no contiene una persona igual (para evitar duplicados)
            if(!gpersonas.contains(elemento)){
                // Se actualizan los datos de la persona seleccionada
                aux.setNombre(aNombre);
                aux.setApellidos(aApellidos);
                aux.setEdad(aEdad);
                
                // Se actualiza visualmente la tabla
                tablaPers.refresh();
            }
        }
    }

    /**
     * Método para eliminar una persona seleccionada de la tabla.
     * Se ejecuta al hacer clic en el botón "Eliminar"
     */
    @FXML
    private void eliminarPers(MouseEvent event) {
        // Se obtiene la persona seleccionada
        persona aux = tablaPers.getSelectionModel().getSelectedItem();
        
        // Si no hay persona seleccionada, se muestra un mensaje de error
        if(aux==null){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("La persona seleccionada no existe en la tabla");
            alerta.showAndWait();
        }else{
            // Si hay una persona seleccionada, se elimina de la lista
            gpersonas.remove(aux);
            
            // Se actualiza la tabla visualmente
            tablaPers.refresh();
        }
    }

    /**
     * Método para seleccionar una persona de la tabla y mostrar sus datos en los TextFields.
     * Se ejecuta cuando se hace clic sobre una fila de la tabla.
     */
    @FXML
    private void seleccionarPers(MouseEvent event) {
        // Se obtiene la persona seleccionada en la tabla
        persona aux = tablaPers.getSelectionModel().getSelectedItem();
        
        // Si se ha seleccionado una persona, se muestran sus datos en los campos de texto
        if(aux!=null){
            tNombre.setText(aux.getNombre());
            tApellidos.setText(aux.getApellidos());
            tEdad.setText(aux.getEdad()+""); // Se convierte la edad a texto
        }
    }
}