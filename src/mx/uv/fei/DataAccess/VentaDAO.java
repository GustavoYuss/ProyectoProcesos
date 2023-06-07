/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.uv.fei.Logic.Producto;
import mx.uv.fei.Logic.Sucursal;
import mx.uv.fei.Logic.Venta;

/**
 *
 * @author yusgu
 */
public class VentaDAO implements IVenta{

    @Override
    public ArrayList<Producto> getProductBybranch(int idBranch) throws SQLException {
        ArrayList<Producto> productos = new ArrayList<>();
        String query = "select * from producto T1 inner join inventario T2 on T1.idproducto = T2.idproducto where T2.idsucursal = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, idBranch);
        
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            Producto producto = new Producto();
            producto.setIdProducto(resultSet.getInt("T1.idproducto"));
            producto.setNombre(resultSet.getString("T1.nombre"));
            producto.setPrecioUnitario(resultSet.getFloat("T1.precio_unitario"));
            producto.setUnidad(resultSet.getInt("T2.cantidad"));
            productos.add(producto);
        }
        connection.close();
        
        return productos;
    }

    @Override
    public int addSale(Venta venta) throws SQLException {
        int result;
        
        String query = "insert into venta(venta_total) values (?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setFloat(1, venta.getTotal());
       
        result = statement.executeUpdate();
        connection.close();
        
        return result;
    }

    @Override
    public int getIdLastSale() throws SQLException {
        int idLastSale = 0;
        String query = "select * from venta order by idventa DESC limit 1";
        DataBaseManager dataBase = new DataBaseManager();
        Connection connection = dataBase.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while(resultSet.next()){
            idLastSale = resultSet.getInt("idventa");
        }
        connection.close();
        
        return idLastSale;
    }

    @Override
    public int updateProduct(Producto producto) throws SQLException {
        int result;
        String query = "update inventario set cantidad = cantidad - ? where idproducto = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setFloat(1, producto.getUnidad());
        statement.setInt(2, producto.getIdProducto());
        result = statement.executeUpdate();
        connection.close();
        
        return result;
    } 
    
    public ArrayList<Sucursal> getAllBranchs() throws SQLException{
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        String query = "select * from sucursal";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while(resultSet.next()){
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(resultSet.getInt("idsucursal"));
            sucursal.setSucursal(resultSet.getString("ubicacion"));
            sucursales.add(sucursal);
        }
        connection.close();
        
        return sucursales;        
    }
    
    public int addProducts(Producto producto, int venta) throws SQLException {
        int result;
        
        String query = "insert into venta_de_productos(cantidad_de_productos,idventa,idproducto) values (?,?,?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, producto.getUnidad());
        statement.setInt(2, venta);
        statement.setInt(3, producto.getIdProducto());
       
        result = statement.executeUpdate();
        connection.close();
        
        return result;
    }
}
