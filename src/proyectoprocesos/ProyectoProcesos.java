/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectoprocesos;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author yusgu
 */
public class ProyectoProcesos extends Application{

    private static Scene scene; 
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("/mx/uv/fei/GUI/Login"));
        stage.setScene(scene);
        stage.show();    
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProyectoProcesos.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void changeView(String url, int width, int height) throws IOException {
        Stage currentStage = (Stage) scene.getWindow();
        configureStage(currentStage, width, height);
        ProyectoProcesos.setRoot(url);
    }
    
    private static void configureStage(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
        stage.setResizable(false);
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
}
