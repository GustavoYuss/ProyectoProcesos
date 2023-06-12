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


    private boolean guardarCambiosFR(){
        boolean confirmation = false;
            Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationMessage.setTitle("Confirmación");
            confirmationMessage.setHeaderText("¿Guardar cambiios?");
            confirmationMessage.setContentText("¿Desea guarda los cambios de actividad con la información del formulario?");
            ButtonType btnYes = new ButtonType("Sí");
            ButtonType btnNo = new ButtonType("No");
            confirmationMessage.getButtonTypes().setAll(btnYes, btnNo);
            Optional<ButtonType> option = confirmationMessage.showAndWait();
            if(option.get() == btnYes){
                confirmation = true;
            } else if(option.get() == btnNo){
            }
            return confirmation;
    }

    public boolean todoOk(){
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
    public void malLlenado(){
        Alert message= new Alert (Alert.AlertType.WARNING) ;
        message.setTitle ("Advertencia!");
        message.setContentText ("Por favor, llene de manera correcta los campos marcados en rojo.");
        message.showAndWait();
    }
    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        boolean camposOK = false;
        camposOK = todoOk();
        if(camposOK == true){
            boolean resultado = guardarCambiosFR();
            if(resultado == true){
                setearGuardar();
                mandarCambiosBD();
                Stage stage = (Stage) this.btnGuardarcambio.getScene().getWindow();
                stage.close();
            }
        } else{
            malLlenado();
        }
    }
    
    private void setearGuardar(){
        this.productoCambio.setNombreP(txtNombreProducto.getText());
        this.productoCambio.setPrecio(Float.parseFloat((txtPrecioProducto.getText())));
        this.productoCambio.setUnidadMedida(txtDosisProducto.getText());
        this.productoCambio.setProductoId(idproducto);
    }
    
    private void mandarCambiosBD(){
        ProductoDAO dao = new ProductoDAO();
        setearGuardar();
        try{
            dao.editarProducto(productoCambio);
        }catch(SQLException e){
            Logger.getLogger(EditarProductoController.class.getName()).log(Level.SEVERE, "No funcionó el editar en el controlador", e);
        }
    }
    
}
