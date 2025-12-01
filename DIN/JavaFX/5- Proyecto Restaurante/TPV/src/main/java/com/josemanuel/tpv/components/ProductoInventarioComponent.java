package com.josemanuel.tpv.components;

import com.josemanuel.tpv.repository.ProductoRepository;
import com.josemanuel.tpv.utils.Database;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProductoInventarioComponent extends HBox {
    private ProductoRepository productoRepository;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldPrecio;

    @FXML
    private TextField textFieldStock;

    @FXML
    private ImageView imageViewProducto;

    @FXML
    private Button buttonDecrementarCantidad;

    @FXML
    private Button buttonIncrementarCantidad;

    public ProductoInventarioComponent(int id, String textFieldNombre, double textFieldPrecio, int textFieldStock, String imagen) {
        this.productoRepository = new ProductoRepository(Database.createConnection().getConnection());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("producto-inventario.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.setup(id, textFieldNombre, textFieldPrecio, textFieldStock, imagen);
    }

    private void setup(int id, String nombre, double precio, int stock, String imagen) {
        // Actualizar base de datos al quitar el foco
        ChangeListener<? super Boolean> cambiarEstadoDb = (_, _, newValue) -> {
            if (!newValue) {
                this.actualizarEstado(id);
            }
        };

        this.textFieldNombre.setText(nombre);
        this.textFieldNombre.focusedProperty().addListener(cambiarEstadoDb);
        this.textFieldPrecio.focusedProperty().addListener(cambiarEstadoDb);
        this.textFieldStock.focusedProperty().addListener(cambiarEstadoDb);

        this.textFieldPrecio.setText(String.valueOf(precio));
        this.textFieldStock.setText(String.valueOf(stock));
        this.imageViewProducto.setImage(new Image(getClass().getResource("/com/josemanuel/tpv/images/" + imagen).toExternalForm()));
    }

    private void actualizarEstado(int idProducto) {
        this.productoRepository.actualizarInventario(
                idProducto,
                this.textFieldNombre.getText(),
                Double.parseDouble(this.textFieldPrecio.getText()),
                Integer.parseInt(this.textFieldStock.getText())
        );
    }

    public TextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public TextField getTextFieldPrecio() {
        return textFieldPrecio;
    }

    public TextField getTextFieldStock() {
        return textFieldStock;
    }

    public Button getButtonDecrementarCantidad() {
        return buttonDecrementarCantidad;
    }

    public Button getButtonIncrementarCantidad() {
        return buttonIncrementarCantidad;
    }
}