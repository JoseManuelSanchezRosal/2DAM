/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button alimentos;
    @FXML
    private Button ajustes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void butonAlimentos(MouseEvent event) {
        Stage nuevaV = (Stage) alimentos.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/alimentos.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("alimentos");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(AlimentosController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }   
    @FXML
    private void butonAjustes(MouseEvent event) {
        Stage nuevaV = (Stage) ajustes.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/ajustes.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("ajustes");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(AjustesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }   
}