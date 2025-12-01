package com.josemanuel.tpv.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MesaComandaComponent extends VBox {
    @FXML
    private Label labelMesa;

    public MesaComandaComponent(int numero) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mesa.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setup(numero);
    }

    private void setup(int numero) {
        this.setCursor(Cursor.HAND);
        this.labelMesa.setText(String.valueOf(numero));
    }
}