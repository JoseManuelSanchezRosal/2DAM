package controllers;

import model.Producto;
import model.ProductoComanda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandaController {

    // === REFERENCIAS FXML ===
    @FXML private Label labelMesaActual;
    // ⚠️ Corrección: Cambiamos el genérico a nuestro modelo ProductoComanda
    @FXML private TableView<ProductoComanda> tablaComanda;
    @FXML private TableColumn<ProductoComanda, String> columnaProducto;
    @FXML private TableColumn<ProductoComanda, Integer> columnaCantidad;
    @FXML private TableColumn<ProductoComanda, Double> columnaSubtotal;
    @FXML private Label labelTotalComanda;
    @FXML private TilePane panelProductos;

    // === ESTADO INTERNO Y DATOS ===
    private String mesaActual;
    private List<Producto> catalogo;
    // Mapa: String (Nombre del producto) -> Objeto ProductoComanda (para búsqueda rápida)
    private Map<String, ProductoComanda> itemsComandaMesa = new HashMap<>();
    // Lista observable que alimenta la TableView
    private ObservableList<ProductoComanda> datosTabla = FXCollections.observableArrayList();

    // =========================================================
    // 1. INICIALIZACIÓN Y CARGA DE DATOS
    // =========================================================

    @FXML
    public void initialize() {
        // 1. Configurar la TableView para usar las propiedades del modelo
        configurarTabla();
        
        // 2. Cargar la lista estática del menú
        inicializarCatalogo(); 
        
        // 3. Crear los VBoxes con las fotos y el texto en el TilePane
        cargarBotonesProducto();
    }
    
    /**
     * Permite a TPVController acceder al mapa interno de la comanda para guardar su estado.
     */
    public Map<String, ProductoComanda> getItemsComandaMesa() {
        return itemsComandaMesa;
    }
    
    
    
    private void configurarTabla() {
        // Enlaza las columnas a las propiedades observables de ProductoComanda
        columnaProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        
        // Asignar la lista observable a la tabla
        tablaComanda.setItems(datosTabla);
    }
    
    private void inicializarCatalogo() {
        catalogo = new ArrayList<>();
        // Añadir los 18 productos con sus rutas exactas (según el árbol de proyecto)
        catalogo.add(new Producto("AGUA", 1.00, "/sources/AGUA.jpg"));
        catalogo.add(new Producto("CERVEZA", 2.50, "/sources/CERVEZA.jpeg"));
        catalogo.add(new Producto("ZUMO", 2.00, "/sources/ZUMO.jpg"));
        catalogo.add(new Producto("RIBERA", 4.00, "/sources/RIBERA.jpg"));
        catalogo.add(new Producto("REFRESCO", 2.00, "/sources/REFRESCO.jpg"));
        catalogo.add(new Producto("TINTO", 2.00, "/sources/TINTO.png"));

        catalogo.add(new Producto("ENSALADILLA", 4.00, "/sources/ENSALADILLA.jpg"));
        catalogo.add(new Producto("GAMBAS", 5.00, "/sources/GAMBAS.jpeg"));
        catalogo.add(new Producto("JAMON", 5.00, "/sources/JAMON.jpg"));
        catalogo.add(new Producto("BRAVAS", 1.00, "/sources/BRAVAS.jpg"));
        catalogo.add(new Producto("QUESO", 5.00, "/sources/QUESO.jpg"));
        catalogo.add(new Producto("CROQUETAS", 6.00, "/sources/CROQUETAS.jpg"));

        catalogo.add(new Producto("SOLOMILLO", 14.00, "/sources/SOLOMILLO.jpg"));
        catalogo.add(new Producto("POLLO", 15.00, "/sources/POLLO.jpg"));
        catalogo.add(new Producto("PLUMA", 12.00, "/sources/PLUMA.jpg"));
        catalogo.add(new Producto("CHIPIRONES", 12.00, "/sources/CHIPIRONES.jpg"));
        catalogo.add(new Producto("PULPO", 14.00, "/sources/PULPO.jpg"));
        catalogo.add(new Producto("LUBINA", 18.00, "/sources/LUBINA.jpg"));
    }
    
    private void cargarBotonesProducto() {
        for (Producto prod : catalogo) {
            VBox productoBox = new VBox();
            productoBox.setStyle("-fx-alignment: center; -fx-padding: 5;");
            
            // Creación de ImageView
            ImageView imgView = new ImageView();
            try {
                // Carga la imagen desde el classpath (la carpeta 'sources')
                Image image = new Image(getClass().getResourceAsStream(prod.getImagenUrl()));
                imgView.setImage(image);
            } catch (Exception e) {
                System.err.println("❌ ERROR: Imagen no encontrada para " + prod.getNombre() + ". Ruta: " + prod.getImagenUrl());
            }
            imgView.setFitHeight(80);
            imgView.setFitWidth(80);
            
            // Etiqueta del nombre del producto
            Label nombreLabel = new Label(prod.getNombre());
            
            // Asignar el objeto Producto al VBox para recuperarlo en el clic (KEY!)
            productoBox.setUserData(prod); 
            productoBox.setOnMouseClicked(this::añadirProductoAClick); // Enlazar el evento
            
            productoBox.getChildren().addAll(imgView, nombreLabel);
            panelProductos.getChildren().add(productoBox);
        }
    }

    /**
     * Método requerido para recibir el ID de la mesa y la comanda guardada asociada a esa mesa.
     */
    public void cargarDatosMesa(String numeroMesa, Map<String, ProductoComanda> comanda) {
        this.mesaActual = numeroMesa;
        labelMesaActual.setText("Mesa: " + numeroMesa);

        // 1. Reemplazar el estado interno con la comanda cargada
        this.itemsComandaMesa = comanda; 
        
        // 2. Cargar el ObservableList de la tabla con los valores del nuevo HashMap
        // setAll() limpia el ObservableList y añade todos los valores nuevos.
        datosTabla.setAll(itemsComandaMesa.values()); 
        
        // 3. Actualizar el total con los datos cargados
        actualizarTotalComanda();

        System.out.println("Cargando comanda para la Mesa: " + numeroMesa + ". Items cargados: " + datosTabla.size());
    }
    
    // =========================================================
    // 2. MANEJO DEL CLIC (Añadir Producto)
    // =========================================================

    private void añadirProductoAClick(MouseEvent event) {
        Producto productoSeleccionado = (Producto) ((VBox) event.getSource()).getUserData();
        
        if (itemsComandaMesa.containsKey(productoSeleccionado.getNombre())) {
            // Producto existente: Aumentar la cantidad
            ProductoComanda item = itemsComandaMesa.get(productoSeleccionado.getNombre());
            item.setCantidad(item.getCantidad() + 1);
            tablaComanda.refresh(); // Forzar la actualización visual
        } else {
            // Producto nuevo: Crear nuevo item y añadirlo a las listas
            ProductoComanda nuevoItem = new ProductoComanda(
                productoSeleccionado.getNombre(), 
                1, 
                productoSeleccionado.getPrecio()
            );
            itemsComandaMesa.put(productoSeleccionado.getNombre(), nuevoItem);
            datosTabla.add(nuevoItem);
        }
        
        actualizarTotalComanda();
    }
    
    private void actualizarTotalComanda() {
        double total = datosTabla.stream()
            .mapToDouble(item -> item.subtotalProperty().get())
            .sum();
        labelTotalComanda.setText(String.format("%.2f €", total));
    }

    // =========================================================
    // 3. IMPLEMENTACIÓN DE BOTONES
    // =========================================================
    
    @FXML 
    private void eliminarProducto(ActionEvent event) {
        ProductoComanda seleccionado = tablaComanda.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (seleccionado.getCantidad() > 1) {
                // Reducir la cantidad en 1
                seleccionado.setCantidad(seleccionado.getCantidad() - 1);
                tablaComanda.refresh();
            } else {
                // Eliminar completamente
                itemsComandaMesa.remove(seleccionado.nombreProperty().get());
                datosTabla.remove(seleccionado);
            }
            actualizarTotalComanda();
        }
    }

    @FXML 
    private void guardarComanda(ActionEvent event) {
        System.out.println("💾 Comanda de Mesa " + mesaActual + " guardada en el sistema.");
    }

    @FXML 
    private void reiniciarMesa(ActionEvent event) {
        itemsComandaMesa.clear();
        datosTabla.clear();
        labelTotalComanda.setText("0.00 €");
        System.out.println("❌ Mesa " + mesaActual + " reiniciada.");
    }

    @FXML 
    private void sacarTicket(ActionEvent event) {
        System.out.println("🧾 Generando ticket para Mesa " + mesaActual + ". Total: " + labelTotalComanda.getText());
        // Después de generar el ticket y procesar el pago, podrías llamar a reiniciarMesa();
    }

    @FXML 
    private void cerrarComanda(ActionEvent event) {
        System.out.println("🚪 Acción de Salir de Comanda.");
    }
}