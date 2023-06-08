/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.Logic.Producto;

/**
 *
 * @author edmun
 */
public interface iProducto {
    int guardarProducto(Producto producto) throws SQLException;
    ArrayList<Producto> obtenerproductosGeneral() throws SQLException;
    int editarProducto(Producto producto) throws SQLException;
    int eliminarProducto(int idproducto) throws SQLException;
    Producto obtenerproductoporID(int id) throws SQLException;
}
