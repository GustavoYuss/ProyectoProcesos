/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yusgu
 */
public class DataBaseManager {
   
    
    private Connection connection; 
    private final String DATABASE_NAME = "jdbc:mysql://127.0.0.1/procesos";
    private final String DATABASE_USER = "root" ;
    private final String DATABASE_PASSWORD = "rimuruxshuna";
   
    
    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }
    
    private void connect() throws SQLException{
        try{
            connection = DriverManager.getConnection(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
        }catch(SQLException exception){
            //logger.fatal(exception);
            throw new SQLException("Fallo al conectarse con la base de datos");
        }
    }
    
    public void closeConnection(){
        if(connection!=null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException exception){
                   //logger.fatal(exception);
            }
         }
    }
}
