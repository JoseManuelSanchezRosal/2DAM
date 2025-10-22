/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class VentanaPrincipalController implements Initializable {

    private Label texto1;
    @FXML
    private Button punto;
    @FXML
    private Button cero;
    @FXML
    private Button igual;
    @FXML
    private Button division;
    @FXML
    private Button uno;
    @FXML
    private Button dos;
    @FXML
    private Button tres;
    @FXML
    private Button producto;
    @FXML
    private Button cuatro;
    @FXML
    private Button cinco;
    @FXML
    private Button seis;
    @FXML
    private Button resta;
    @FXML
    private Button siete;
    @FXML
    private Button ocho;
    @FXML
    private Button nueve;
    @FXML
    private Button reset;
    @FXML
    private TextField entrada;
    @FXML
    private TextField salida;
    
    //Creamos las variables. En realidad el numero2 es donde volcamos el total de la operacion.
    int numero1, numero2, operador, total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickCero(ActionEvent event) {
        entrada.setText(entrada.getText() + "0");
    }

    @FXML
    private void clickIgual(ActionEvent event) {
        if (operador == 1){
            numero2 = numero1 + Integer.parseInt(entrada.getText());
            salida.setText(numero2 + "");
            entrada.setText("");
        }else if(operador == 2){
            numero2 = numero1 - Integer.parseInt(entrada.getText());
            salida.setText(numero2 + "");
            entrada.setText("");            
        }else if(operador == 3){
            numero2 = numero1 * Integer.parseInt(entrada.getText());
            salida.setText(numero2 + "");
            entrada.setText("");
        }else{
            numero2 = numero1 / Integer.parseInt(entrada.getText());
            salida.setText(numero2 + "");
            entrada.setText(""); 
        }
    }
    @FXML
    private void clickDivision(ActionEvent event) {
        numero1 = Integer.parseInt(entrada.getText());
        salida.setText(entrada.getText()+" /");
        entrada.setText("");
        operador = 4;
    }

    @FXML
    private void clickUno(ActionEvent event) {
        entrada.setText(entrada.getText() + "1");
    }

    @FXML
    private void clickDos(ActionEvent event) {
        entrada.setText(entrada.getText() + "2");
    }

    @FXML
    private void clickTres(ActionEvent event) {
        entrada.setText(entrada.getText() + "3");
    }

    @FXML
    private void clickProducto(ActionEvent event) {
        numero1 = Integer.parseInt(entrada.getText());
        salida.setText(entrada.getText()+" *");
        entrada.setText("");
        operador = 3;
    }

    @FXML
    private void clickCuatro(ActionEvent event) {
        entrada.setText(entrada.getText() + "4");
    }

    @FXML
    private void clickCinco(ActionEvent event) {
        entrada.setText(entrada.getText() + "5");
    }

    @FXML
    private void clickSeis(ActionEvent event) {
        entrada.setText(entrada.getText() + "6");
    }

    @FXML
    private void clickResta(ActionEvent event) {
        numero1 = Integer.parseInt(entrada.getText());
        salida.setText(entrada.getText()+" -");
        entrada.setText("");
        operador = 2;
    }

    @FXML
    private void clickSiete(ActionEvent event) {
        entrada.setText(entrada.getText() + "7");
    }

    @FXML
    private void clickOcho(ActionEvent event) {
        entrada.setText(entrada.getText() + "8");
    }

    @FXML
    private void clickNueve(ActionEvent event) {
        entrada.setText(entrada.getText() + "9");
    }

    @FXML
    private void clickSuma(ActionEvent event) {
        numero1 = Integer.parseInt(entrada.getText());
        salida.setText(entrada.getText()+" +");
        entrada.setText("");
        operador = 1;
    }

    @FXML
    private void clickReset(ActionEvent event) {
        entrada.setText("");
        salida.setText("");
    }
    
    
    
}
