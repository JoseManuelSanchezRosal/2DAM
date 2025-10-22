/*
 * Clase principal de una aplicación JavaFX.
 * Se encarga de cargar la vista principal (vista1.fxml) y mostrar la ventana.
 */

package controlador;

// Importaciones necesarias para trabajar con JavaFX
import java.io.IOException;
import javafx.application.Application; // Clase base de toda aplicación JavaFX
import static javafx.application.Application.launch; // Permite usar 'launch' sin escribir Application.launch
import javafx.fxml.FXMLLoader; // Permite cargar archivos FXML (interfaces gráficas)
import javafx.scene.Parent;   // Nodo raíz de una escena
import javafx.scene.Scene;    // Contenedor principal de los elementos visuales
import javafx.stage.Stage;    // Representa una ventana en JavaFX

/**
 * Clase principal de la aplicación JavaFX
 * Extiende la clase Application, por lo que puede ejecutar una interfaz gráfica
 * 
 * @author Pedro
 */
public class Main extends Application {
    
    /**
     * Método 'start' que se ejecuta automáticamente cuando arranca la aplicación.
     * Recibe un objeto Stage (ventana principal) donde se montará la interfaz.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Carga el archivo FXML que contiene la interfaz gráfica
        // getResource("/Vista/vista1.fxml") busca el archivo dentro del paquete 'Vista'
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/vista1.fxml"));

        // Crea una escena con el contenido del archivo FXML cargado
        Scene scene = new Scene(root);
        
        // Establece el título de la ventana
        primaryStage.setTitle("Vista 1");
        
        // Asigna la escena (contenido) a la ventana principal
        primaryStage.setScene(scene);
        
        // Muestra la ventana en pantalla
        primaryStage.show();
    }

    /**
     * Método principal 'main' — punto de entrada del programa.
     * Llama al método 'launch', que se encarga de iniciar el entorno JavaFX.
     */
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }
    
}
