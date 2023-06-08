/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.fei.Logic.Usuario;

/**
 *
 * @author yusgu
 */
public class UsuarioDAO {
    public int checkUsuario(Usuario usuario) throws SQLException{
        int result = 0;
        String query = "select count(*) As cuenta from encargado where nombre = ? and contrasena = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getContraseña());
        ResultSet resultSet = statement.executeQuery();
        
        while(resultSet.next()){
            result = resultSet.getInt("cuenta");
        }
        connection.close();
        return result;
    }
    
    public int recuperarTipo(Usuario usuario) throws SQLException{
        int result = 0;
        String query = "select * from encargado where nombre = ? and contrasena = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, usuario.getNombre());
        statement.setString(2, usuario.getContraseña());
        ResultSet resultSet = statement.executeQuery();
        
        while(resultSet.next()){
            result = resultSet.getInt("tipo");
        }
        connection.close();
        return result;
    }
}
