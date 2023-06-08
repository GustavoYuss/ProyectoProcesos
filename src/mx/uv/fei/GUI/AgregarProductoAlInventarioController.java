/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import mx.uv.fei.DataAccess.ProductoDAO;
import mx.uv.fei.DataAccess.VentaDAO;
import mx.uv.fei.Logic.Producto;
import mx.uv.fei.Logic.Sucursal;


/**
 * FXML Controller class
 *
 * @author edmun
 */
public class AgregarProductoAlInventarioController implements Initializable {
    
    @FXML
    private ComboBox<Sucursal> cmbLugares; 
    
    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private TextField txtDosisProducto;  
    @FXML
    private TextField txtcantidad;
    @FXML
    private Button btnGuardarProducto;
    @FXML
    private Button btnCancelar;
    
    private static final int longitudMaximaDosis = 45;
    private static final int longitudMaximaNombre = 45;
    
    private Producto productoNuevo = new Producto();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTextFields();
        fillComboBox();
    }

    public void fillComboBox(){
        VentaDAO ventaDao = new VentaDAO();
        try {
            ArrayList<Sucursal> sucursales = ventaDao.getAllBranchs();
            this.cmbLugares.getItems().addAll(sucursales);
        } catch (SQLException ex) {
            Logger.getLogger(AgregarProductoAlInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void guardarProducto(ActionEvent event) throws SQLException {
        boolean todoOk = camposOk();
        ProductoDAO dao = new ProductoDAO();
        if(todoOk == true && this.cmbLugares.getSelectionModel().getSelectedItem() != null){
            boolean estoySeguro = confirmacionAgregarProducto();
            if(estoySeguro == true){
                setProducto();
                dao.guardarProducto(productoNuevo);
                productoNuevo.setProductoId(dao.getLastId());
                dao.actualizarInventario(productoNuevo, this.cmbLugares.getSelectionModel().getSelectedItem().getIdSucursal());
                Stage stage = (Stage) this.btnGuardarProducto.getScene().getWindow();
                stage.close();
            } 
        }else{
            advertenciaCamposvaciosAgregarProducto();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) this.btnGuardarProducto.getScene().getWindow();
        stage.close();
    }
    
    
    public boolean camposOk(){
        boolean ok = true;
        if (txtNombreProducto.getText() == null || txtNombreProducto.getText().trim().isEmpty()){
            txtNombreProducto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok = false; 
        }
        if (txtPrecioProducto.getText() == null || txtPrecioProducto.getText().trim().isEmpty()){
            txtPrecioProducto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok = false; 
        }
        if (txtDosisProducto.getText() == null || txtDosisProducto.getText().trim().isEmpty()){
            txtDosisProducto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok = false; 
        }
        return ok;
    }
    
    private void configurarTextFields(){
        txtPrecioProducto.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        
        txtDosisProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > longitudMaximaDosis) {
                txtDosisProducto.setText(oldValue); // Si se excede el límite, se restaura el valor anterior
            }
        });
        
        txtNombreProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > longitudMaximaNombre) {
                txtNombreProducto.setText(oldValue); // Si se excede el límite, se restaura el valor anterior
            }
        });
    }
    
    public boolean confirmacionAgregarProducto(){
        boolean eleccion = false;
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setTitle("Confirmación");
        confirmationMessage.setHeaderText("¿Guardar Nuevo producto?");
        confirmationMessage.setContentText("¿Desea Guardar el nuevo producto con la información del formulario?");
        ButtonType btnYes = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");
        confirmationMessage.getButtonTypes().setAll(btnYes, btnNo);
        Optional<ButtonType> option = confirmationMessage.showAndWait();
        if(option.get() == btnYes){
            eleccion = true;
        } else if(option.get() == btnNo){
        }
        return eleccion;
    }
    
    public void advertenciaCamposvaciosAgregarProducto(){
        Alert message= new Alert (Alert.AlertType.WARNING) ;
        message.setTitle ("Advertencia!");
        message.setContentText ("Por favor, llene de manera correcta los campos marcados en rojo.");
        message.showAndWait();
    }
    
    private void setProducto(){
        this.productoNuevo.setNombreP(txtNombreProducto.getText());
        this.productoNuevo.setPrecio(Float.parseFloat(txtPrecioProducto.getText()));
        this.productoNuevo.setUnidadMedida(txtDosisProducto.getText());
        this.productoNuevo.setProductoId(123);
        this.productoNuevo.setUnidad(Integer.parseInt(this.txtcantidad.getText()));
    }
   
}
