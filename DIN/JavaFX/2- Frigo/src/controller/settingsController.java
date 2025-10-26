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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class SettingsController implements Initializable {

    @FXML
    private ImageView sFood;
    @FXML
    private Label reloj;
    @FXML
    private Label displayTemp;
    @FXML
    private ImageView restar;
    @FXML
    private ImageView sumar;
    @FXML
    private ImageView sHome;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();
        actualizarTemperatura();
        // TODO
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

    @FXML
    private void sGoFood(MouseEvent event) {
        Stage nuevaV = (Stage) sFood.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Food.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Food");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(SettingsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @FXML
    private void sGohome(MouseEvent event) {
        Stage nuevaV = (Stage) sHome.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/Home.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("Home");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(SettingsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void actualizarTemperatura(){
        int tempActual = model.DatosCompartidos.getTemperatura();
        displayTemp.setText(String.valueOf(tempActual));
    }

    @FXML
    private void sRestarTemp(MouseEvent event) {
        if (model.DatosCompartidos.temperatura <= -2)
            return;
        else{
            model.DatosCompartidos.temperatura--;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
        
    }

    @FXML
    private void sSumarTemp(MouseEvent event) {
        if(model.DatosCompartidos.temperatura >=8)
            return;
        else{
            model.DatosCompartidos.temperatura++;
            displayTemp.setText(String.valueOf(model.DatosCompartidos.temperatura));
        }
    }
}
