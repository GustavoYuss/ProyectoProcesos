/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.GUI.ModeloDeTablaDePromociones;
/**
 *
 * @author ferdy
 */
public interface IPromocion {
    int subirNuevaPromocion(Promocion promocion) throws SQLException;
    int modificarPromocionExistente(Promocion promocion)throws SQLException;
    int verificarDiaDisponible(String producto, String diaActivo) throws SQLException;
    ArrayList<ModeloDeTablaDePromociones> getPromociones() throws SQLException;
}
