package com.josemanuel.tpv.controllers;

import com.josemanuel.tpv.components.MesaComandaComponent;
import com.josemanuel.tpv.components.ProductoInventarioComponent;
import com.josemanuel.tpv.components.ProductoMesaComponent;
import com.josemanuel.tpv.dto.ProductoComandaDTO;
import com.josemanuel.tpv.dto.ProductoDTO;
import com.josemanuel.tpv.models.Categoria;
import com.josemanuel.tpv.models.Mesa;
import com.josemanuel.tpv.models.Producto;
import com.josemanuel.tpv.models.ProductoMesa;
import com.josemanuel.tpv.repository.CategoriaRepository;
import com.josemanuel.tpv.repository.MesaRepository;
import com.josemanuel.tpv.repository.ProductoRepository;
import com.josemanuel.tpv.utils.Database;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private ProductoRepository productoRepository;
    private MesaRepository mesaRepository;
    private CategoriaRepository categoriaRepository;

    public MainController() {
        Connection connection = Database.createConnection().getConnection();
        this.productoRepository = new ProductoRepository(connection);
        this.mesaRepository = new MesaRepository(connection);
        this.categoriaRepository = new CategoriaRepository(connection);
    }

    @FXML
    private Label labelMesa;

    @FXML
    private TilePane tilePaneMesas;

    @FXML
    private TableView<ProductoComandaDTO> tableViewProductos;

    @FXML
    private TableColumn<ProductoComandaDTO, String> tableColumnNombre;

    @FXML
    private TableColumn<ProductoComandaDTO, Integer> tableColumnCantidad;

    @FXML
    private TableColumn<ProductoComandaDTO, Double> tableColumnSubtotal;

    @FXML
    private TabPane tabPaneCategorias;

    @FXML
    private Button buttonRestarCantidad;

    @FXML
    private Label labelTotalMesa;

    @FXML
    private Button buttonReiniciarMesa;

    @FXML
    private Button buttonSacarTicket;

    @FXML
    private Tab tabInventario;

    @FXML
    private TilePane tilePaneInventarioItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.configurarVistaComanda();
        this.configurarVistaInventario();
    }

    private void configurarVistaComanda() {
        this.cargarProductosMesa();
        this.configurarTabla();
        this.configurarProductosCategoria();
        this.configurarEventos();
    }

    private void configurarVistaInventario() {
        this.tilePaneInventarioItems.setPrefColumns(3);
        this.tilePaneInventarioItems.setHgap(100);
        this.tilePaneInventarioItems.setVgap(10);

        this.tabInventario.setOnSelectionChanged(_ -> {
            if (this.tabInventario.isSelected()) {
                this.tilePaneInventarioItems.getChildren().clear();

                ArrayList<Producto> productos = productoRepository.obtener();
                ProductoInventarioComponent productoInventarioComponent;
                for (Producto producto : productos) {
                    productoInventarioComponent = new ProductoInventarioComponent(
                            producto.getId(),
                            producto.getNombre(),
                            producto.getPrecio(),
                            producto.getStock(),
                            producto.getImagen()
                    );

                    ProductoInventarioComponent finalProductoInventarioComponent = productoInventarioComponent;
                    productoInventarioComponent.getButtonDecrementarCantidad().setOnMouseClicked(_ -> {
                        int cantidadRestado = Integer.parseInt(finalProductoInventarioComponent.getTextFieldStock().getText()) - 1;
                        if (cantidadRestado >= 0) {
                            finalProductoInventarioComponent.getTextFieldStock().setText(String.valueOf(cantidadRestado));
                        }
                        this.productoRepository.actualizarInventario(
                                producto.getId(),
                                finalProductoInventarioComponent.getTextFieldNombre().getText(),
                                Double.parseDouble(finalProductoInventarioComponent.getTextFieldPrecio().getText()),
                                Integer.parseInt(finalProductoInventarioComponent.getTextFieldStock().getText())
                        );
                        this.cargarProductosCategoria(this.tabPaneCategorias.getSelectionModel().getSelectedItem());
                    });

                    productoInventarioComponent.getTextFieldStock().focusedProperty().addListener((_, _, newValue) -> {
                        if (!newValue) {
                            this.productoRepository.actualizarInventario(
                                    producto.getId(),
                                    finalProductoInventarioComponent.getTextFieldNombre().getText(),
                                    Double.parseDouble(finalProductoInventarioComponent.getTextFieldPrecio().getText()),
                                    Integer.parseInt(finalProductoInventarioComponent.getTextFieldStock().getText())
                            );
                            this.cargarProductosCategoria(this.tabPaneCategorias.getSelectionModel().getSelectedItem());
                        }
                    });

                    productoInventarioComponent.getButtonIncrementarCantidad().setOnMouseClicked(_ -> {
                        int cantidadSumado = Integer.parseInt(finalProductoInventarioComponent.getTextFieldStock().getText()) + 1;
                        finalProductoInventarioComponent.getTextFieldStock().setText(String.valueOf(cantidadSumado));
                        this.productoRepository.actualizarInventario(
                                producto.getId(),
                                finalProductoInventarioComponent.getTextFieldNombre().getText(),
                                Double.parseDouble(finalProductoInventarioComponent.getTextFieldPrecio().getText()),
                                Integer.parseInt(finalProductoInventarioComponent.getTextFieldStock().getText())
                        );
                        this.cargarProductosCategoria(this.tabPaneCategorias.getSelectionModel().getSelectedItem());
                    });
                    this.tilePaneInventarioItems.getChildren().add(productoInventarioComponent);
                }
            }
        });
    }

    private void configurarTabla() {
        this.tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        this.tableColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.tableColumnSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    private void cargarProductosMesa() {
        try {
            MesaComandaComponent mesaComandaComponent;
            ArrayList<Mesa> mesas = this.mesaRepository.obtener();
            for (Mesa mesa : mesas) {
                int numeroMesa = mesa.getNumero();
                mesaComandaComponent = new MesaComandaComponent(numeroMesa);
                mesaComandaComponent.setOnMouseClicked(_ -> {
                    this.cargarProductosMesa(numeroMesa);
                    this.calcularTotal();
                });
                // Establecer el label de la mesa elegida en la primera
                int numeroPrimeraMesa = mesas.get(0).getNumero();
                this.labelMesa.setText(String.valueOf(numeroPrimeraMesa));
                this.cargarProductosMesa(numeroPrimeraMesa);
                this.calcularTotal();
                this.tilePaneMesas.getChildren().add(mesaComandaComponent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarProductosCategoria() {
        ArrayList<Tab> tabs = new ArrayList<>();
        Tab tab;
        for (Categoria categoria : this.categoriaRepository.obtener()) {
            tab = new Tab(categoria.getNombre());
            tab.setId(String.valueOf(categoria.getId()));
            tab.setClosable(false);
            Tab finalTab = tab;
            tab.setOnSelectionChanged(_ -> {
                if (finalTab.isSelected()) {
                    this.cargarProductosCategoria(finalTab);
                }
            });
            tabs.add(tab);
        }
        this.tabPaneCategorias.getTabs().addAll(tabs);
    }

    private void configurarEventos() {
        this.buttonRestarCantidad.setOnMouseClicked(_ -> {
            ProductoComandaDTO productoComandaExistente = this.tableViewProductos.getSelectionModel().getSelectedItem();
            if (productoComandaExistente == null) {
                return;
            }

            if (productoComandaExistente.getCantidad() == 1) {
                this.tableViewProductos.getItems().remove(productoComandaExistente);
            } else {
                productoComandaExistente.setCantidad(productoComandaExistente.getCantidad() - 1);
                this.tableViewProductos.refresh();
            }

            // Volver a cargar los productos para deshabilitar aquellos que no tengan stock
            this.guardarProductos();
            // Establecer foco en la fila
            int filaSeleccionada = this.tableViewProductos.getSelectionModel().getSelectedIndex();
            this.cargarProductosMesa(Integer.parseInt(this.labelMesa.getText()));
            this.tableViewProductos.getSelectionModel().select(filaSeleccionada);
            this.calcularTotal();
            Tab tabSeleccionado = this.tabPaneCategorias.getSelectionModel().getSelectedItem();
            this.cargarProductosCategoria(tabSeleccionado);
        });

        this.buttonReiniciarMesa.setOnMouseClicked(_ -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que desea reiniciar la mesa?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                this.mesaRepository.reiniciar(Integer.parseInt(this.labelMesa.getText()));
                this.cargarProductosCategoria(this.tabPaneCategorias.getSelectionModel().getSelectedItem());
                this.tableViewProductos.getItems().clear();
                this.calcularTotal();
            }
        });

        this.buttonSacarTicket.setOnMouseClicked(_ -> {
            this.guardarProductos();
            this.mesaRepository.guardarTicket(Integer.parseInt(this.labelMesa.getText()));
            this.cargarProductosCategoria(this.tabPaneCategorias.getSelectionModel().getSelectedItem());
            this.tableViewProductos.getItems().clear();
            this.calcularTotal();
        });
    }

    private void cargarProductosMesa(int numeroMesa) {
        this.tableViewProductos.getItems().clear();
        this.tableViewProductos.getItems().addAll(this.mesaRepository.obtenerProductosMesa(numeroMesa));
        this.labelMesa.setText(String.valueOf(numeroMesa));
    }

    private void cargarProductosCategoria(Tab tabSeleccionado) {
        ArrayList<ProductoDTO> productos = this.productoRepository.obtenerProductosCategoria(Integer.parseInt(tabSeleccionado.getId()));
        TilePane tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        ProductoMesaComponent productoMesa;
        // Introducir productos en la categoría
        for (ProductoDTO producto : productos) {
            productoMesa = new ProductoMesaComponent(producto.getImagen(), producto.getNombre(), producto.isTieneStock());
            productoMesa.setOnMouseClicked(_ -> {
                boolean productoEncontrado = false;
                for (ProductoComandaDTO productoComanda : this.tableViewProductos.getItems()) {
                    if (productoComanda.getIdProducto() == producto.getId()) {
                        productoComanda.setCantidad(productoComanda.getCantidad() + 1);
                        productoComanda.setSubtotal(productoComanda.getCantidad() * producto.getPrecio());
                        this.tableViewProductos.refresh();
                        productoEncontrado = true;
                        break;
                    }
                }

                if (!productoEncontrado) {
                    ProductoComandaDTO productoComandaNuevo = new ProductoComandaDTO();
                    productoComandaNuevo.setIdProducto(producto.getId());
                    productoComandaNuevo.setNombreProducto(producto.getNombre());
                    productoComandaNuevo.setIdMesa(Integer.parseInt(this.labelMesa.getText()));
                    productoComandaNuevo.setCantidad(1);
                    productoComandaNuevo.setSubtotal(producto.getId());
                    this.tableViewProductos.getItems().add(productoComandaNuevo);
                }
                this.guardarProductos();
                this.calcularTotal();
                this.cargarProductosCategoria(tabSeleccionado);
            });
            tilePane.getChildren().add(productoMesa);
        }
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setPadding(new Insets(10));
        tabSeleccionado.setContent(scrollPane);
    }

    private void guardarProductos() {
        ObservableList<ProductoComandaDTO> observableListProductosMesa = this.tableViewProductos.getItems();
        ArrayList<ProductoComandaDTO> productosComanda = new ArrayList<>(observableListProductosMesa);
        ArrayList<ProductoMesa> productosMesa = new ArrayList<>();
        ProductoMesa productoMesa;
        for (ProductoComandaDTO productoComanda : productosComanda) {
            productoMesa = new ProductoMesa();
            productoMesa.setIdProducto(productoComanda.getIdProducto());
            productoMesa.setIdMesa(productoComanda.getIdMesa());
            productoMesa.setCantidad(productoComanda.getCantidad());
            productosMesa.add(productoMesa);
        }

        boolean esCorrecto = this.mesaRepository.guardar(productosMesa);
        if (!esCorrecto) {
            new Alert(Alert.AlertType.ERROR, "No se pudo guardar la mesa. Inténtelo más tarde").showAndWait();
        }
    }

    private void calcularTotal() {
        double total = 0;
        for (ProductoComandaDTO producto : this.tableViewProductos.getItems()) {
            total += producto.getSubtotal();
        }
        this.labelTotalMesa.setText(String.valueOf(total));
    }
}