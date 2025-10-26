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
public class DatosCompartidos {
    public static int temperatura = 3;
    
    private static ObservableList<Alimento> alimentosFrigo = FXCollections.observableArrayList();
    
    static {
        alimentosFrigo.addAll(
            new Alimento("Peras", 3),
            new Alimento("Manzanas", 7),
            new Alimento("Platanos",5),
            new Alimento("Zumos", 6),
            new Alimento("Leche", 12),
            new Alimento("Hamburguesas",4),
            new Alimento("Cerveza 1906", 6),
            new Alimento("Melón", 1),
            new Alimento("Sandía", 1),
            new Alimento("Pizza",2)
            
        );
    }

    public static int getTemperatura() {
        return temperatura;
    }

    public static void setTemperatura(int temperatura) {
        DatosCompartidos.temperatura = temperatura;
    }

    public static ObservableList<Alimento> getAlimentosFrigo() {
        return alimentosFrigo;
    }
}