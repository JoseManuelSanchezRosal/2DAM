package com.josemanuel.tpv.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProductoMesaComponent extends VBox {
    @FXML
    private ImageView imageViewProducto;

    @FXML
    private Label labelNombre;

    public ProductoMesaComponent(String imagen, String nombre, boolean tieneStock) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("producto-mesa.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setup(imagen, nombre, tieneStock);
    }

    private void setup(String imagen, String nombre, boolean tieneStock) {
        this.setCursor(Cursor.HAND);
        this.imageViewProducto.setImage(new Image(getClass().getResource("/com/josemanuel/tpv/images/" + imagen).toExternalForm()));
        this.labelNombre.setText(nombre);
        this.setDisable(!tieneStock);
    }
}