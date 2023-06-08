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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.DataAccess.VentaDAO;
import mx.uv.fei.Logic.Producto;
import mx.uv.fei.Logic.Sucursal;
import mx.uv.fei.Logic.Venta;
import proyectoprocesos.ProyectoProcesos;

/**
 * FXML Controller class
 *
 * @author yusgu
 */
public class VentasController implements Initializable {

    @FXML
    private ComboBox<Sucursal> cmbLugares; 
    
    @FXML
    private TableColumn<Producto, Integer> tblDisponibles;

    @FXML
    private TableColumn<Producto, Integer> tblID;

    @FXML
    private TableColumn<Producto, String> tblNombre;

    @FXML
    private TableColumn<Producto, Float> tblPrecio;

    @FXML
    private TableView<Producto> tblProductos;
    
    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtDisponibles;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtPrecioTotal;
    
    @FXML
    private TextField txtComprar;
    
    @FXML
    private Button btnAgregarCarrito;
    
    @FXML
    private TableColumn<Producto, Integer> tblCantidadCarrito;

    @FXML
    private TableView<Producto> tblCarrito;

    @FXML
    private TableColumn<Producto, Integer> tblIdCarrito;
    
    @FXML
    private TableColumn<Producto, String> tblNombreCarrito;
    
    @FXML
    private TableColumn<Producto, Float> tblPrecioTotal;

    @FXML
    private TableColumn<Producto, Float> tblPrecioUniCa;
    
    private final ArrayList<Producto> carrito = new ArrayList<>();
    
    @FXML
    private Label lblTotal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBox();
        bloqueBoton();
    }    
    
    public void fillComboBox(){
        VentaDAO ventaDao = new VentaDAO();
        try {
            ArrayList<Sucursal> sucursales = ventaDao.getAllBranchs();
            this.cmbLugares.getItems().addAll(sucursales);
        } catch (SQLException ex) {
            Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillTable(){
        VentaDAO ventaDao = new VentaDAO();
        ArrayList<Producto> productos = new ArrayList<>();
        this.tblProductos.getItems().clear();
        this.tblID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        this.tblNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tblPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        this.tblDisponibles.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        
        try {
            productos = ventaDao.getProductBybranch(this.cmbLugares.getSelectionModel().getSelectedItem().getIdSucursal());
        } catch (SQLException ex) {
            Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cleanFields();
        ObservableList<Producto> listaTabla = FXCollections.observableArrayList(productos);
        this.tblProductos.setItems(listaTabla);
    }
    
    public void fillData(){
        if(this.tblProductos.getSelectionModel().getSelectedItem() != null){
            this.txtPrecio.setText(Float.toString(this.tblProductos.getSelectionModel().getSelectedItem().getPrecio()));
            this.txtNombre.setText(this.tblProductos.getSelectionModel().getSelectedItem().getNombreP());
            this.txtId.setText(Integer.toString(this.tblProductos.getSelectionModel().getSelectedItem().getProductoId()));
            this.txtDisponibles.setText(Integer.toString(this.tblProductos.getSelectionModel().getSelectedItem().getUnidad()));
            this.txtComprar.setEditable(true);
            
            int indice = alredyShoppingCart(this.tblProductos.getSelectionModel().getSelectedItem().getProductoId());
            System.out.println(indice);
            if(indice > -1){
                this.txtComprar.setText(Integer.toString(carrito.get(indice).getUnidad()));
                fillPrice();
            }
        }
    }
    
    public void fillPrice(){
        float precioTotal;
        
            try{
                if(validarCantidad(Integer.parseInt(this.txtComprar.getText()))){
                    precioTotal = this.tblProductos.getSelectionModel().getSelectedItem().getPrecio() * Integer.parseInt(this.txtComprar.getText());  
                    this.txtPrecioTotal.setText(Float.toString(precioTotal));
                }
            }catch(IllegalArgumentException exception){
                this.txtPrecioTotal.setText("");
            }
    }
    
    public boolean validarCantidad(int cantidad){
        boolean result = true;
        
        if(cantidad > this.tblProductos.getSelectionModel().getSelectedItem().getUnidad() || cantidad <= 0){
            this.txtPrecioTotal.setText("Cantidad invalida");
            result = false;
        }
        return result;
    }
    
    public void bloqueBoton(){
        BooleanBinding booleanBinding = this.txtPrecioTotal.textProperty().isEmpty().or(this.txtPrecioTotal.textProperty().isEqualTo("Cantidad invalida"));
        this.btnAgregarCarrito.disableProperty().bind(booleanBinding);
    }
    
    public void agregarCarrito(){
        Producto producto = new Producto();
        int indice = alredyShoppingCart(this.tblProductos.getSelectionModel().getSelectedItem().getProductoId());
        if(indice == -1){
            producto.setProductoId(this.tblProductos.getSelectionModel().getSelectedItem().getProductoId());
            producto.setNombreP(this.tblProductos.getSelectionModel().getSelectedItem().getNombreP());
            producto.setPrecio(this.tblProductos.getSelectionModel().getSelectedItem().getPrecio());
            producto.setUnidad(Integer.parseInt(this.txtComprar.getText()));
            producto.setPrecioTotal(Float.parseFloat(this.txtPrecioTotal.getText()));
            carrito.add(producto);
        }else{
            producto = carrito.get(indice);
            producto.setUnidad(Integer.parseInt(this.txtComprar.getText()));
            producto.setPrecioTotal(Float.parseFloat(this.txtPrecioTotal.getText()));
        }
        fillCarrito(carrito);
        cleanFields();
    }
    
    public void fillCarrito(ArrayList<Producto> productos){
        this.tblCarrito.getItems().clear();
        this.tblIdCarrito.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        this.tblNombreCarrito.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tblPrecioUniCa.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        this.tblCantidadCarrito.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        this.tblPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        
        ObservableList<Producto> carritoCompra = FXCollections.observableArrayList();
        this.lblTotal.setText("Total: "  + Float.toString(calcularTotal()));
        this.tblCarrito.setItems(carritoCompra);
        carritoCompra.addAll(productos);
    }
    
    public void Comprar(){
        Venta venta = new Venta();
        VentaDAO ventaDAO = new VentaDAO();
        venta.setTotal(calcularTotal());
        venta.setProductos(carrito);
        
        try {
            ventaDAO.addSale(venta);
            venta.setIdVenta(ventaDAO.getIdLastSale());
            for(Producto producto : venta.getProductos()){
                ventaDAO.addProducts(producto, venta.getIdVenta());
                ventaDAO.updateProduct(producto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fillTable();
        this.tblCarrito.getItems().clear();
        this.carrito.clear();
        this.lblTotal.setText("Total: 0.0");
    }
    
    private float calcularTotal(){
        float precioTotal = 0;
        
        for(Producto producto : carrito){
            precioTotal += producto.getPrecioTotal();
        }
        
        return precioTotal;
    }
    
    @FXML
    private void cleanFields(){
        this.tblProductos.getSelectionModel().clearSelection();
        this.txtComprar.clear();
        this.txtDisponibles.clear();
        this.txtId.clear(); 
        this.txtNombre.clear(); 
        this.txtPrecio.clear();
        this.txtPrecioTotal.clear();
        this.txtComprar.setEditable(false);
    }
    
    public void cleanCarrito(){
        carrito.clear();
        fillCarrito(carrito);
    }
    
    private int alredyShoppingCart(int id){
        int result = -1;
        for(Producto producto : carrito){
            if(producto.getProductoId() == id){
                result = carrito.indexOf(producto);
            }
        }
        return result;
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
