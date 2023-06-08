/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mx.uv.fei.DataAccess.ProductoDAO;
import mx.uv.fei.Logic.Producto;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class EditarProductoController implements Initializable {

    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private TextField txtDosisProducto;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardarcambio;
    
    private int idproducto;
    Producto elegido = new Producto();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setProducto();
        } catch (SQLException ex) {
            Logger.getLogger(EditarProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetTextFields();
    }    
    public void setinfo(int id){
        this.idproducto = id;
    }
    
    private void setProducto() throws SQLException{
        ProductoDAO dao = new ProductoDAO();
        elegido = dao.obtenerproductoporID(idproducto);
    }
    
    private void SetTextFields(){
        this.txtNombreProducto.setText(elegido.getNombreP());
        this.txtPrecioProducto.setText(Float.toString(elegido.getPrecio()));
        this.txtDosisProducto.setText(elegido.getUnidadMedida());
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
    }
    
}
