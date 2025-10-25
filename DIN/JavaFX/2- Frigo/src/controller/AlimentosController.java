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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarReloj();
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

    private void cambiarVistaPrincipal(MouseEvent event) {
        
    }

    @FXML
    private void fGoHome(MouseEvent event) {
        Stage nuevaV = (Stage) fHome.getScene().getWindow();
        
        try {
            Parent nroot = FXMLLoader.load(getClass().getResource("/vista/principal.fxml"));
            Scene scene = new Scene(nroot);
            nuevaV.setTitle("ajustes");
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
            nuevaV.setTitle("ajustes");
            // Seteo la scene y la muestro
            nuevaV.setScene(scene);
            nuevaV.show();
            
        } catch (IOException ex) {
            System.getLogger(AjustesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @FXML
    private void fRestarTemp(MouseEvent event) {
        int temp = Integer.parseInt(displayTemp.getText());
        temp -=1;
        displayTemp.setText(String.valueOf(temp));
    }

    @FXML
    private void fSumarTemp(MouseEvent event) {
        int temp = Integer.parseInt(displayTemp.getText());
        temp +=1;
        displayTemp.setText(String.valueOf(temp));
    }
}