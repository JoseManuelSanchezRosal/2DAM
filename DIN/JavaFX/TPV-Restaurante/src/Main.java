/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jose
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Cargar el FXML.
        // Asumiendo que TPV.fxml está en el mismo nivel que Main.java
        // Si Main.java no tiene paquete, y TPV.fxml está en una carpeta 'views', 
        // la ruta relativa debería ser "./views/TPV.fxml"
        // Según tu estructura de proyecto, probaremos con la ruta relativa:
        
        // La URL de carga debe ser relativa a la ubicación de tu Main.java (o la clase que lo carga)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TPV.fxml"));
        Parent root = loader.load(); // Esto carga el nodo raíz del FXML (el AnchorPane)
        
        // Opcional: Si necesitas acceder al controlador para inicialización, hazlo aquí:
        // TPVController controller = loader.getController();
        // controller.inicializar(); 
        
        // 2. Crear la escena con el nodo raíz del FXML.
        Scene scene = new Scene(root);
        
        // 3. Configurar y mostrar el Stage.
        primaryStage.setTitle("TPV Restaurante");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}