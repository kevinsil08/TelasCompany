/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telascompany;


import Controller_Pages.PrincipalHomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kevin
 */
public class TelasCompany extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PrincipalHome.fxml"));
        stage.setTitle("Telas Company");
        loader.setControllerFactory(t -> buildController(stage));
        stage.setScene(new Scene(loader.load()));
        stage.show();
        
        
    }
    

    public static void main(String[] args) {
        launch(args);
    }

    private PrincipalHomeController buildController(Stage stage) {
        return new PrincipalHomeController(stage);
    }
    
}
