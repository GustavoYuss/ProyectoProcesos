/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.DataAccess.Promocion;
import mx.uv.fei.DataAccess.PromocionDAO;
import proyectoprocesos.ProyectoProcesos;
/**
 * FXML Controller class
 *
 * @author ferdy
 */
public class AdministrarPromocionesController implements Initializable {
    
    int idPromocion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboBox();
        try{
            llenarTablaConPromociones();
        } catch(SQLException exception){
            
        }
    }  
    
    @FXML
    private Button btnModificarPromocion;

    @FXML
    private Button btnRegistrarPromocion;

    @FXML
    private ComboBox<Float> cmbDescuento;

    @FXML
    private ComboBox<String> cmbDiaActivo;

    @FXML
    private TableColumn<ModeloDeTablaDePromociones, Float> tblClmnDescuento;

    @FXML
    private TableColumn<ModeloDeTablaDePromociones, String> tblClmnDiaActivo;

    @FXML
    private TableColumn<ModeloDeTablaDePromociones, String> tblClmnProducto;

    @FXML
    private TableView<ModeloDeTablaDePromociones> tblPromociones;

    @FXML
    private TextField txtEspecificacion;

    @FXML
    private TextField txtProducto;

    @FXML
    void botonDeRegistroDePromocion(ActionEvent event) throws SQLException {
       Promocion promocion = new Promocion();
       PromocionDAO promociondao = new PromocionDAO();
       promocion.setProducto(txtProducto.getText());
       promocion.setDescuento(cmbDescuento.getValue());
       promocion.setDiaActivo(cmbDiaActivo.getValue());
       promocion.setEspecificacion(txtEspecificacion.getText());
       promociondao.subirNuevaPromocion(promocion);
       llenarTablaConPromociones();
    }
    
    @FXML
    public void botonDeModificarPromocion(ActionEvent event) throws SQLException{
        Promocion promocion = new Promocion();
        PromocionDAO promociondao = new PromocionDAO();      
        promocion.setIdPromocion(idPromocion);
        promocion.setProducto(txtProducto.getText());
        promocion.setDescuento(cmbDescuento.getValue());
        promocion.setDiaActivo(cmbDiaActivo.getValue());
        promocion.setEspecificacion(txtEspecificacion.getText());
        promociondao.modificarPromocionExistente(promocion);
        llenarTablaConPromociones();
    }
    
    @FXML
    public void mostrarPromocionSeleccionada(){
        tblPromociones.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<ModeloDeTablaDePromociones>() {
            @Override
            public void changed(ObservableValue<? extends ModeloDeTablaDePromociones> arg0,
                ModeloDeTablaDePromociones valorAnterior, ModeloDeTablaDePromociones valorSeleccionado) {
                    if (valorSeleccionado!=null){
                        idPromocion = valorSeleccionado.getIdPromocion();
                        txtProducto.setText(valorSeleccionado.getProducto());                                                   
                        cmbDescuento.setValue(valorSeleccionado.getDescuento());
                        cmbDiaActivo.setValue(valorSeleccionado.getDiaActivo());
                        txtEspecificacion.setText(valorSeleccionado.getEspecificacion());                       
                    }
                }
            }
        );
    }
    
    @FXML
    private void llenarTabla(ObservableList<ModeloDeTablaDePromociones> listaPromociones){
        this.tblClmnProducto.setCellValueFactory(new PropertyValueFactory("producto"));
        this.tblClmnDiaActivo.setCellValueFactory(new PropertyValueFactory("diaActivo"));
        this.tblClmnDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        tblPromociones.setItems(listaPromociones);    
    }
    
    @FXML
    void llenarTablaConPromociones() throws SQLException {
        ObservableList<ModeloDeTablaDePromociones> lista = null;
        ModeloDeTablaDePromociones listaPromociones = new ModeloDeTablaDePromociones();
        lista = listaPromociones.getPromociones();
        llenarTabla(lista);
        mostrarPromocionSeleccionada();
    }

    private void llenarComboBox(){
        ArrayList<String> dias = new ArrayList<>();
        dias.add("Domingo");
        dias.add("Lunes");
        dias.add("Martes");
        dias.add("Miercoles");
        dias.add("Jueves");
        dias.add("Viernes");
        dias.add("Sabado");
        this.cmbDiaActivo.getItems().addAll(dias);
        ArrayList<Float> descuentos = new ArrayList<>();
        for(float i = 1; i <= 10; i++){
            float descuento = i/10;
            descuentos.add(descuento);
        }
        this.cmbDescuento.getItems().addAll(descuentos);
    }
    
    public void vistaVenta() throws IOException{
        ProyectoProcesos.changeView("/mx/uv/fei/GUI/Ventas", 750, 550);
    }
    
    public void vistaPromociones() throws IOException{
        ProyectoProcesos.changeView("/mx/uv/fei/GUI/AdministrarPromociones", 600, 400);
    }
    
    public void cerrarSesion() throws IOException{
        ProyectoProcesos.changeView("/mx/uv/fei/GUI/Login", 600, 400);
    }
}
