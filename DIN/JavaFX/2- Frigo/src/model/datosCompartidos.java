/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author Jose
 */
public class datosCompartidos {
    public static int temperatura = 3;
    
    private static ObservableList<Alimento> alimentosFrigo = FXCollections.observableArrayList();
    
    static {
        alimentosFrigo.addAll(
            new Alimento("Peras", 3),
            new Alimento("Manzanas", 3)
        );
    }

    public static int getTemperatura() {
        return temperatura;
    }

    public static void setTemperatura(int temperatura) {
        datosCompartidos.temperatura = temperatura;
    }

    public static ObservableList<Alimento> getAlimentosFrigo() {
        return alimentosFrigo;
    }
    
    
}
