/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.Logic.Producto;
import mx.uv.fei.Logic.Venta;

/**
 *
 * @author yusgu
 */
public interface IVenta {
    public ArrayList<Producto> getProductBybranch(int idBranch) throws SQLException;
    public int addSale(Venta venta) throws SQLException; 
    public int getIdLastSale() throws SQLException;
    public int updateProduct(Producto producto) throws SQLException;
}
