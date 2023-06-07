/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.GUI.ModeloDeTablaDePromociones;

/**
 *
 * @author ferdy
 */
public class PromocionDAO implements IPromocion{

    @Override
    public int subirNuevaPromocion(Promocion promocion) throws SQLException {
        int resultadoRegistro = 0;
        if(verificarDiaDisponible(promocion.getProducto(),promocion.getDiaActivo()) == -1){
            String query = "INSERT INTO Promocion (`dia_activo`, `descuento`, `especificacion`, `producto`) VALUES (?, ?, ?, ?);";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, promocion.getDiaActivo());
            statement.setFloat(2, promocion.getDescuento());
            statement.setString(3, promocion.getEspecificacion());
            statement.setString(4, promocion.getProducto());
            resultadoRegistro = statement.executeUpdate();   
        }     
        return resultadoRegistro;
    }

    @Override
    public int modificarPromocionExistente(Promocion promocion) throws SQLException {
        int resultadoRegistro = 0;
        String query = "UPDATE promocion SET `dia_activo` = ?, `descuento` = ?, `especificacion` = ?, `producto` = ? WHERE (`idPromocion` = ?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, promocion.getDiaActivo());
        statement.setFloat(2, promocion.getDescuento());
        statement.setString(3, promocion.getEspecificacion());
        statement.setString(4, promocion.getProducto());
        statement.setInt(5, promocion.getIdPromocion());
        resultadoRegistro = statement.executeUpdate();
       
       return resultadoRegistro;
    }

    @Override
    public int verificarDiaDisponible(String producto, String diaActivo) throws SQLException {
        int registerResult = 0;
        String query = "select count(*) as verify from promocion where producto = ? and dia_Activo = ?;";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, producto); 
        statement.setString(2, diaActivo); 
        ResultSet resultSet = statement.executeQuery(); 
        if(resultSet.next()){
            if(resultSet.getInt("verify") == 0){
                    registerResult = -1;
                } else{
                registerResult= 1;
            }
        }
        return registerResult;  
    }

    @Override
    public ArrayList<ModeloDeTablaDePromociones> getPromociones() throws SQLException {
        ArrayList<ModeloDeTablaDePromociones> promociones = new ArrayList();
        String query = "select * from Promocion";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()){
            ModeloDeTablaDePromociones promocion = new ModeloDeTablaDePromociones();
            promocion.setIdPromocion(resultSet.getInt("idpromocion"));
            promocion.setDiaActivo(resultSet.getString("dia_activo"));
            promocion.setDescuento(resultSet.getFloat("descuento"));
            promocion.setEspecificacion(resultSet.getString("especificacion"));
            promocion.setProducto(resultSet.getString("producto"));
            promociones.add(promocion);
        }
        connection.close();
        
        return promociones;
    }
    
}
