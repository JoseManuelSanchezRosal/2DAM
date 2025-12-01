package com.josemanuel.tpv.repository;

import com.josemanuel.tpv.dto.ProductoDTO;
import com.josemanuel.tpv.models.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductoRepository {
    private Connection connection;

    public ProductoRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtener todos los productos de la base de datos
     * @return ArrayList con todos los productos
     */
    public ArrayList<Producto> obtener() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(
                    "SELECT id, nombre, precio, imagen, stock FROM Producto"
            );
            Producto producto;
            while (resultSet.next()) {
                producto = new Producto();
                producto.setId(resultSet.getInt(1));
                producto.setNombre(resultSet.getString(2));
                producto.setPrecio(resultSet.getDouble(3));
                producto.setImagen(resultSet.getString(4));
                producto.setStock(resultSet.getInt(5));
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Actualiza la información de un producto del inventario
     * @param id ID del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param stock Stock del producto
     * @return Devuelve true si lo actualizó correctamente y false si no
     */
    public boolean actualizarInventario(int id, String nombre, double precio, int stock) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "UPDATE Producto SET nombre = ?, precio = ?, stock = ? WHERE id = ?"
            );
            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setInt(3, stock);
            preparedStatement.setInt(4, id);
            boolean esCorrecto = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return esCorrecto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtener todas los productos asociados a una categoría
     * @param idCategoria ID de la categoría a filtrar
     * @return ArrayList con todos los productos de una categoría
     */
    public ArrayList<ProductoDTO> obtenerProductosCategoria(int idCategoria) {
        ArrayList<ProductoDTO> productos = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT p.id, p.nombre, p.precio, p.imagen, IFNULL(p.stock > SUM(pm.cantidad), 1) AS tiene_stock FROM Producto p LEFT JOIN Producto_Mesa pm ON p.id = pm.id_producto WHERE p.id_categoria = ? GROUP BY p.id"
            );
            preparedStatement.setInt(1, idCategoria);
            ResultSet resultSet = preparedStatement.executeQuery();
            ProductoDTO producto;
            while (resultSet.next()) {
                producto = new ProductoDTO();
                producto.setId(resultSet.getInt(1));
                producto.setNombre(resultSet.getString(2));
                producto.setPrecio(resultSet.getDouble(3));
                producto.setImagen(resultSet.getString(4));
                producto.setTieneStock(resultSet.getBoolean(5));
                productos.add(producto);
            }
            preparedStatement.close();
            return productos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }
}