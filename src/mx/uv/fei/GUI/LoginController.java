/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.uv.fei.DataAccess.UsuarioDAO;
import mx.uv.fei.Logic.Usuario;
import proyectoprocesos.ProyectoProcesos;

/**
 * FXML Controller class
 *
 * @author yusgu
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private Label lblMensaje;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void login() throws IOException{
        int result = 0;
        int tipo = 0;
        String vista = "";
        int weidth = 0, heigth = 0;
        Usuario usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario.setNombre(this.txtUsuario.getText());
        usuario.setContraseña(this.txtPassword.getText());
        try {
            result = usuarioDAO.checkUsuario(usuario);
            tipo = usuarioDAO.recuperarTipo(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result == 1){
            switch(tipo){
                case 1: vista = "Ventas"; weidth = 750; heigth = 550; break;
                case 2: vista = "VistaAdministrador"; weidth = 850; heigth = 600; break;
            }
            ProyectoProcesos.changeView("/mx/uv/fei/GUI/" + vista, weidth, heigth);
        }else{
            this.lblMensaje.setText("Contraseña o usuario incorrecto, vuelva a intentarlo");
        }
    }
}
