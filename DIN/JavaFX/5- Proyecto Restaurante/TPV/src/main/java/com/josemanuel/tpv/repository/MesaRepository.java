package com.josemanuel.tpv.repository;

import com.josemanuel.tpv.dto.ProductoComandaDTO;
import com.josemanuel.tpv.dto.ProductoStockDTO;
import com.josemanuel.tpv.models.Mesa;
import com.josemanuel.tpv.models.ProductoMesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MesaRepository {
    private Connection connection;

    public MesaRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtener todas las mesas de la base de datos
     * @return ArrayList con todas las mesas
     */
    public ArrayList<Mesa> obtener() {
        ArrayList<Mesa> mesas = new ArrayList<>();

        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(
                    "SELECT id, numero FROM Mesa"
            );
            Mesa mesa;
            while (resultSet.next()) {
                mesa = new Mesa();
                mesa.setId(resultSet.getInt(1));
                mesa.setNumero(resultSet.getInt(2));
                mesas.add(mesa);
            }
            return mesas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesas;
    }

    /**
     * Obtener todos los productos asignados a una mesa de la base de datos
     * @param numeroMesa Número de la mesa a obtener
     * @return ArrayList con todos los productos de una mesa
     */
    public ArrayList<ProductoComandaDTO> obtenerProductosMesa(int numeroMesa) {
        ArrayList<ProductoComandaDTO> productosComanda = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT p.id AS id_producto, m.id AS id_mesa, p.nombre, pm.cantidad, p.precio * pm.cantidad AS subtotal FROM Producto p JOIN Producto_Mesa pm ON p.id = pm.id_producto JOIN Mesa m ON pm.id_mesa = m.id WHERE m.numero = ?"
            );
            preparedStatement.setInt(1, numeroMesa);
            ResultSet resultSet = preparedStatement.executeQuery();
            ProductoComandaDTO productoComanda;
            while (resultSet.next()) {
                productoComanda = new ProductoComandaDTO();
                productoComanda.setIdProducto(resultSet.getInt(1));
                productoComanda.setIdMesa(resultSet.getInt(2));
                productoComanda.setNombreProducto(resultSet.getString(3));
                productoComanda.setCantidad(resultSet.getInt(4));
                productoComanda.setSubtotal(resultSet.getDouble(5));
                productosComanda.add(productoComanda);
            }
            return productosComanda;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosComanda;
    }

    /**
     * Guarda en la base de datos los productos de una mesa
     * @param productosMesa Productos de la mesa a guardar
     * @return Devuelve true si lo ha hecho correctamente y false si no
     */
    public boolean guardar(ArrayList<ProductoMesa> productosMesa) {
        if (productosMesa.isEmpty()) {
            return true;
        }

        // Obtener el número de mesa para reiniciar antes
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT numero FROM Mesa WHERE id = ?"
            );
            preparedStatement.setInt(1, productosMesa.get(0).getIdMesa());
            ResultSet resultSet = preparedStatement.executeQuery();
            int numMesa = 0;
            while (resultSet.next()) {
                numMesa = resultSet.getInt(1);
            }
            preparedStatement.close();

            this.reiniciar(numMesa);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder(
                "INSERT INTO Producto_Mesa (id_producto, id_mesa, cantidad) VALUES "
        );
        stringBuilder.repeat("(?,?,?),", productosMesa.size());
        String sentenciaSql = stringBuilder.substring(0, stringBuilder.length() - 1);

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sentenciaSql);
            ProductoMesa productoMesa;
            int numProductosMesas = productosMesa.size();
            for (int i = 1, j = i; i <= numProductosMesas; i++) {
                productoMesa = productosMesa.get(i - 1);
                preparedStatement.setInt(j++, productoMesa.getIdProducto());
                preparedStatement.setInt(j++, productoMesa.getIdMesa());
                preparedStatement.setInt(j++, productoMesa.getCantidad());
            }
            boolean esCorrecto = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return esCorrecto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Resta del stock los productos guardados en una comanda
     * @param numeroMesa Número de la mesa a guardar
     * @return Devuelve true si lo ha hecho correctamente y false si no
     */
    public boolean guardarTicket(int numeroMesa) {
        try {
            // Obtener nuevo stock restando la cantidad de cada producto consumida en la comanda
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT pm.id_producto, p.stock - pm.cantidad AS stock FROM Producto p JOIN Producto_Mesa pm ON p.id = pm.id_mesa JOIN Mesa m ON pm.id_mesa = m.id WHERE m.numero = ?"
            );
            preparedStatement.setInt(1, numeroMesa);
            ArrayList<ProductoStockDTO> productosStock = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            ProductoStockDTO productoStock;
            while (resultSet.next()) {
                productoStock = new ProductoStockDTO();
                productoStock.setIdProducto(resultSet.getInt(1));
                productoStock.setStock(resultSet.getInt(2));
                productosStock.add(productoStock);
            }
            preparedStatement.close();

            if (productosStock.isEmpty()) {
                return true;
            }

            // Guardar nuevo stock en la base de datos
            for (ProductoStockDTO productoStockTemp : productosStock) {
                preparedStatement = this.connection.prepareStatement(
                        "UPDATE Producto SET stock = ? WHERE id = ?"
                );
                preparedStatement.setInt(1, productoStockTemp.getStock());
                preparedStatement.setInt(2, productoStockTemp.getIdProducto());
                preparedStatement.execute();
                preparedStatement.close();
            }

            preparedStatement = this.connection.prepareStatement(
                    "DELETE pm FROM Producto_Mesa pm JOIN Mesa m ON pm.id_mesa = m.id WHERE m.numero = ?"
            );
            preparedStatement.setInt(1, numeroMesa);
            boolean esCorrecto = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return esCorrecto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina todos los productos asignados a una mesa
     * @param numeroMesa Número de la mesa a reiniciar
     * @return Devuelve true si lo ha hecho correctamente y false si no
     */
    public boolean reiniciar(int numeroMesa) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "DELETE pm FROM Producto_Mesa pm JOIN Mesa m ON pm.id_mesa = m.id WHERE m.numero = ?"
            );
            preparedStatement.setInt(1, numeroMesa);
            boolean esCorrecto = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return esCorrecto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}