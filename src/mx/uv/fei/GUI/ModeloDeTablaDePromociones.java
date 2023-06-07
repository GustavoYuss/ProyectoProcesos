/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.GUI;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.DataAccess.DataBaseManager;
import mx.uv.fei.DataAccess.PromocionDAO;
/**
 *
 * @author ferdy
 */
public class ModeloDeTablaDePromociones {
    private int idPromocion;
    private float descuento;
    private String diaActivo, especificacion, producto;
    
    public ModeloDeTablaDePromociones(){  
    }
    
    public ModeloDeTablaDePromociones(int idPromocion, float descuento, String diaActivo, String especificacion, String producto){  
        this.idPromocion = idPromocion;
        this.descuento = descuento;
        this.diaActivo = diaActivo;
        this.especificacion = especificacion;
        this.producto = producto;
    }
    

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public String getDiaActivo() {
        return diaActivo;
    }

    public void setDiaActivo(String diaActivo) {
        this.diaActivo = diaActivo;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public ObservableList<ModeloDeTablaDePromociones> getPromociones() throws SQLException {
        
        ObservableList<ModeloDeTablaDePromociones> listaPromociones = FXCollections.observableArrayList();
        PromocionDAO promocionDAO = new PromocionDAO();        
        try {
            ArrayList<ModeloDeTablaDePromociones> promociones = promocionDAO.getPromociones();
            for (ModeloDeTablaDePromociones promocion : promociones) {
                listaPromociones.add(new ModeloDeTablaDePromociones(promocion.getIdPromocion(),promocion.getDescuento(), promocion.getDiaActivo(), promocion.getEspecificacion(), promocion.getProducto()));
            }
        } catch (SQLException exception) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
        }

        return listaPromociones;
    }
}
