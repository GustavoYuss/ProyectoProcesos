/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.DataAccess.ProductoDAO;
import mx.uv.fei.Logic.Producto;
import proyectoprocesos.ProyectoProcesos;

/**
 * FXML Controller class
 *
 * @author edmun
 */
public class VistaAdministradorController implements Initializable {

    @FXML
    private Tab tabInventarioGlobal;
    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, Integer> clmIDProducto;
    @FXML
    private TableColumn<Producto, String> clmNombreProducto;
    @FXML
    private TableColumn<Producto, Float> clmPrecioProducto;
    @FXML
    private TableColumn<Producto, String> clmDosisProducto;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private Button btnEditarProducto;
    @FXML
    private Button btnEliminarProducto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEditarProducto.setDisable(true);
        btnEliminarProducto.setDisable(true);
        setBotones();
        try {
            llenarTabla(setTablaDeProductos());
        } catch (SQLException ex) {
            Logger.getLogger(VistaAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public ObservableList<Producto> setTablaDeProductos() throws SQLException{
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        ProductoDAO dao = new ProductoDAO();
        try {
            ArrayList<Producto> productos = dao.obtenerproductosGeneral();
            for (Producto productoX : productos) {
                 listaProductos.add(new Producto(productoX.getProductoId() ,productoX.getNombreP(), productoX.getPrecio(), productoX.getUnidadMedida()));
            }
            System.out.print("Esta llenando la tabla");
        }catch(SQLException e){
             System.out.print("Algo fallo al obtener lo de la lista");
        }
        return listaProductos;
    }
    
    private void llenarTabla(ObservableList<Producto> listaDeProductos){
        this.clmIDProducto.setCellValueFactory(new PropertyValueFactory("productoId"));
        this.clmNombreProducto.setCellValueFactory(new PropertyValueFactory("nombreP"));
        this.clmPrecioProducto.setCellValueFactory(new PropertyValueFactory("precio"));
        this.clmDosisProducto.setCellValueFactory(new PropertyValueFactory("unidadMedida"));
        this.tblProductos.setItems(listaDeProductos);
    }
    
    private void refresh() throws SQLException{
        llenarTabla(setTablaDeProductos());
    }
    
    private void setBotones(){
        tblProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnEditarProducto.setDisable(newValue == null);
            btnEliminarProducto.setDisable(newValue == null);
        });
        tblProductos.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == -1) { // No se seleccionó ninguna fila
                btnEditarProducto.setDisable(true);
                btnEliminarProducto.setDisable(true);
            }
        });
    }
    
    @FXML
    private void llamarAgregarProdcuto(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/GUI/AgregarProductoAlInventario.fxml"));
            Parent root  = loader.load();
            AgregarProductoAlInventarioController controller = loader.getController();
            Scene scene  = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            try{
                refresh();
            }catch(SQLException e){
                 Logger.getLogger(VistaAdministradorController.class.getName()).log(Level.SEVERE, "No refresco jasjaj", e);
            }
        } catch (IOException e){
            Logger.getLogger(VistaAdministradorController.class.getName()).log(Level.SEVERE, "No jalo la vista jaja", e);
        }      
    }

    @FXML
    private void llamarEditarProducto(ActionEvent event) throws SQLException, IOException{
        Producto elegido = tblProductos.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/GUI/EditarProducto.fxml"));
        Parent root;
        try {
            EditarProductoController controladorParaEditar = new EditarProductoController();
            controladorParaEditar.setinfo(elegido.getProductoId()); // Pasar la información antes de cargar el archivo FXML
            loader.setController(controladorParaEditar);

            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            try {
                refresh();
            } catch (SQLException e) {
                Logger.getLogger(VistaAdministradorController.class.getName()).log(Level.SEVERE, "No refresco jasjaj", e);
            }
        } catch (IOException e) {
            Logger.getLogger(VistaAdministradorController.class.getName()).log(Level.SEVERE, "No jalo la vista jaja", e);
        }
    }

    @FXML
    private void confirmareliminarProducto(ActionEvent event) throws SQLException {
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setTitle("Confirmación");
        confirmationMessage.setHeaderText("¿Desea Eliminar?");
        confirmationMessage.setContentText("¿Desea actividad la actividad seleccionada en formulario?");
        ButtonType btnYes = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");
        confirmationMessage.getButtonTypes().setAll(btnYes, btnNo);
        Optional<ButtonType> option = confirmationMessage.showAndWait();
        if(option.get() == btnYes){
            Producto selected = tblProductos.getSelectionModel().getSelectedItem();
            System.out.print(selected.getProductoId());
            ProductoDAO dao = new ProductoDAO();
            dao.eliminarProducto(selected.getProductoId());
            refresh();
        } else if(option.get() == btnNo){

        }
    }
    
    public void cerrarSesion() throws IOException{
        ProyectoProcesos.changeView("/mx/uv/fei/GUI/Login", 600, 400);
    }
}
