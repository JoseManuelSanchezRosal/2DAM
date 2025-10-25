/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class PrincipalController implements Initializable {

    @FXML
    private ImageView pFood;
    @FXML
    private ImageView pHome;
    @FXML
    private ImageView pSettings;
    @FXML
    private Label reloj;
    @FXML
    private Label displayTemp;
    @FXML
    private ImageView imgFrigo;
    @FXML
    private ImageView restar;
    @FXML
    private ImageView sumar;
    
    public void inicializarReloj(){
        // Formato de hora y fecha
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        
        // Timeline que se ejecuta cada segundo
        Timeline reloj = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            LocalDateTime ahora = LocalDateTime.now();
            this.reloj.setText(formatoHora.format(ahora));
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();
        actualizarTemperatura();
        // TODO
    }    
 
    @FXML
    private void pGoFood(MouseEvent event) {
        Stage nuevaV = (Stage) pFood.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/alimentos.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Alimentos");
            
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show(
            
            
            
            
            );
            
        } catch (IOException ex) {
            System.getLogger(AlimentosController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }


    @FXML
    private void pGoSettings(MouseEvent event) {
        Stage nuevaV = (Stage) pSettings.getScene().getWindow();
        
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
    private void pRestarTemp(MouseEvent event) {
        model.datosCompartidos.temperatura--;
        displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
    }

    @FXML
    private void pSumarTemp(MouseEvent event) {
        model.datosCompartidos.temperatura++;
        displayTemp.setText(String.valueOf(model.datosCompartidos.temperatura));
    }
}