/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class PrincipalHomeController implements Initializable {

    private Stage stage;

    private double StageWidth, StageHeight;

    public PrincipalHomeController(Stage stage) {
        this.stage = stage;
    }

    //Cliente
    @FXML
    private Button BtnAddCustomer, BtnModifyCustomer, BtnDeleteCustomer;
    
    private Tab AddCustomerTab, ModifyCustomerTab, DeleteCustomerTab;
    
    @FXML
    private Button AddHandiwork;
    
    //Arreglos
    @FXML
    private Button BtnArreglos;
    
    private Tab ArreglosTab;

    @FXML
    private TabPane tabPane;

    private Tab elaboracionTab, obrasPendientesTab;

    

    private final String[] NameTabs = {"Pedidos Pendientes", "Elaboración y Planchado", "Ingresar Cliente",
        "Modificar Cliente", "Eliminar Cliente", "Arreglos","Añadir Pedido"};

    private final String[] Paths = {"/View/TableOrders.fxml", "/View/Elaboracion.fxml", "/View/AddCustomer.fxml",
        "/View/ModifyCustomer.fxml", "/View/DeleteCustomer.fxml","/View/Arreglos.fxml","/View/AddHandiwork.fxml"};

    private TableOrdersController buildTableOrdersController() {
        return new TableOrdersController();
    }

    private ElaboracionController buildElaboracionController() {
        return new ElaboracionController();
    }

    private AddCustomerController buildAddCustomerController() {
        return new AddCustomerController();
    }

    private ModifyCustomerController buildModifyCustomerController() {
        return new ModifyCustomerController();
    }

    private DeleteCustomerController buildDeleteCustomerController() {
        return new DeleteCustomerController();
    }
    
    private ArreglosController buildArreglosController() {
        return new ArreglosController();
    }

    @FXML
    private void dashboardButtons(MouseEvent event) throws Exception{

        
        int NUMBERTAB = 2;

        if (event.getSource() == BtnAddCustomer) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
            loader.setControllerFactory(t -> buildAddCustomerController());
            
            return;
        }
        NUMBERTAB++;

        if (event.getSource() == BtnModifyCustomer) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
            loader.setControllerFactory(t -> buildModifyCustomerController());
            
            return;
        }
        NUMBERTAB++;

        if (event.getSource() == BtnDeleteCustomer) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
            loader.setControllerFactory(t -> buildDeleteCustomerController());
            
            return;
        }
        NUMBERTAB++;
        
        if (event.getSource() == BtnArreglos) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
            loader.setControllerFactory(t -> buildArreglosController());
            return;
        }
        NUMBERTAB++;
        
        
        if (event.getSource() == AddHandiwork) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
            loader.setControllerFactory(t -> buildHandiworkController());
            Stage stageDialog = new Stage();
            stageDialog.initModality(Modality.APPLICATION_MODAL);
            stageDialog.setScene(new Scene(loader.load()));
            stageDialog.setTitle("Personas sin proyectos asignados");
            stageDialog.showAndWait();  
            return;
        }
        NUMBERTAB++;        
         
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*int NUMBERTAB = 0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
        loader.setControllerFactory(t -> buildTableOrdersController());

        NUMBERTAB++;
        loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
        loader.setControllerFactory(t -> buildElaboracionController());*/
        

    }

    

    private AddHandiworkController buildHandiworkController() {
        return new AddHandiworkController();
    }

    

}
