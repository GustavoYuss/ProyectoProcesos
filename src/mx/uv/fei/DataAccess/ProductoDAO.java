/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.Logic.Producto;

/**
 *
 * @author edmun
 */
public class ProductoDAO implements iProducto {

    @Override
    public int guardarProducto(Producto producto) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        DataBaseManager databasemanager = new DataBaseManager();
        int resultado = 0;
        try {
            connection = databasemanager.getConnection();
            String query = "INSERT INTO producto (nombre, precio_unitario, unidad) VALUES ( ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, producto.getNombreP());
            statement.setFloat(2, producto.getPrecio());
            statement.setString(3, producto.getUnidadMedida());
            resultado = statement.executeUpdate();
            
            System.out.println("Producto creado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear el producto: " + e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Producto> obtenerproductosGeneral() throws SQLException {
     String query = "SELECT * FROM producto";
        ArrayList<Producto> productos = new ArrayList();
        DataBaseManager databasemanager = new DataBaseManager();
        PreparedStatement statement = null; 
        ResultSet listaDeProductos = null;
        Connection connection = null;
        try{
            connection = databasemanager.getConnection();
            statement = connection.prepareStatement(query);
            listaDeProductos = statement.executeQuery();
            while(listaDeProductos.next()){
                Producto producto = new Producto();
                producto.setNombreP(listaDeProductos.getString("nombre"));
                producto.setPrecio(listaDeProductos.getFloat("precio_unitario"));
                producto.setUnidadMedida(listaDeProductos.getString("unidad"));
                producto.setProductoId(listaDeProductos.getInt("idProducto"));
                productos.add(producto);
            }
        } catch(SQLException e){
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error al obtener anteproyectos", e);
        } finally{
            connection.close();
        }
        return productos; 
    }

    @Override
    public int editarProducto(Producto producto) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminarProducto(int idproducto) throws SQLException {
        String slqDelete = "DELETE FROM producto WHERE idProducto = ?"; 
        DataBaseManager databasemanager = new DataBaseManager();
        PreparedStatement statement;
        Connection connection = null;
        int result = 0;
        try{
            connection = databasemanager.getConnection();
            statement = connection.prepareStatement(slqDelete);
            statement.setInt(1, idproducto);
            result = statement.executeUpdate();
            statement.close();
        } catch (SQLException e){
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error de conección al eliminarr", e);
        } finally {
            connection.close();
        }
        return result;
    }

    @Override
    public Producto obtenerproductoporID(int id) throws SQLException {
        String query = "SELECT * FROM producto WHERE idProducto = ?";
        Producto producto = new Producto();
        DataBaseManager databasemanager = new DataBaseManager();
        PreparedStatement statement = null; 
        ResultSet listaDeProductos = null;
        Connection connection = null;
        try{
            connection = databasemanager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            listaDeProductos = statement.executeQuery();
            while(listaDeProductos.next()){
                producto.setNombreP(listaDeProductos.getString("nombre"));
                producto.setPrecio(listaDeProductos.getFloat("precio_unitario"));
                producto.setUnidadMedida(listaDeProductos.getString("unidad"));
                producto.setProductoId(listaDeProductos.getInt("idProducto"));
            }
        } catch(SQLException e){
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error al obtener anteproyectos", e);
        } finally{
            connection.close();
        }
        return producto; 
    }
    
    public int getLastId() throws SQLException{
        int idLastSale = 0;
        String query = "select * from producto order by idproducto DESC limit 1";
        DataBaseManager dataBase = new DataBaseManager();
        Connection connection = dataBase.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while(resultSet.next()){
            idLastSale = resultSet.getInt("idproducto");
        }
        connection.close();
        
        return idLastSale;
    }
    
    public int actualizarInventario(Producto producto, int sucursal) throws SQLException{
        int result;
        
        String query = "insert into inventario(cantidad,idproducto,idsucursal) values (?,?,?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, producto.getUnidad());
        statement.setInt(2, producto.getProductoId());
        statement.setInt(3, sucursal);
       
        result = statement.executeUpdate();
        connection.close();
        
        return result;
    }
}


