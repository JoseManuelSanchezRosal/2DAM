/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pedro
 */
public class Vista1Controller implements Initializable {

    @FXML
    private Button Bpincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cambiov2(ActionEvent event) {
        Stage nuevaV = (Stage) Bpincipal.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/vista2.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Vista 2");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(Vista1Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
    }
    
}
