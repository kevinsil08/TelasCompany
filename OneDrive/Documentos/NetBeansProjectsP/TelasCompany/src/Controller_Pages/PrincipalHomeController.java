/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import DAO.ItemDAO;
import DAO.MeasurementDAO;
import Model.CustomerManager;
import Model.HandiworkManager;
import Model.ItemManager;
import Model.MeasurementManager;
import Model.SQLItemDAOImpl;
import Model.SQLMeasurementDAOImpl;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author kevin
 */
public class PrincipalHomeController implements Initializable {

    private Stage stage;
    private CustomerManager modelCustomer;
    private HandiworkManager modelHandiwork;

    public PrincipalHomeController(CustomerManager modelCustomer, HandiworkManager modelHandiwork, Stage stage) {
        this.modelCustomer = modelCustomer;
        this.modelHandiwork = modelHandiwork;
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            modelCustomer.close();
            modelHandiwork.close();
        });
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

    private final String[] Paths = {"/View/TableOrders.fxml", "/View/Elaboracion.fxml", "/View/AddCustomer.fxml",
        "/View/ModifyCustomer.fxml", "/View/DeleteCustomer.fxml","/View/Arreglos.fxml","/View/AddHandiwork.fxml",
        "/View/AddNewItem.fxml","/View/ModifyItem.fxml","/View/AddMeasurement.fxml","/View/ModifyMeasurement.fxml",
        "/View/AssignMeasurementToItem.fxml"};

    
    private AddCustomerController buildAddCustomerController() {
        return new AddCustomerController(modelCustomer, "");
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
            stageDialog.setTitle("Agregar Pedidos");
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
    
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[2]));
        loader.setControllerFactory(t -> buildAddCustomerController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Agregar cliente");
        stageDialog.showAndWait();
    }
    
    @FXML
    private void AddItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[7]));
        loader.setControllerFactory(t -> buildAddNewItemController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Agregar nuevo Item");
        stageDialog.showAndWait();
    }
    
    @FXML
    private void UpdateItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[8]));
        loader.setControllerFactory(t -> buildModifyItemController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Actualizar Item");
        stageDialog.showAndWait();
    }
    
    @FXML
    private void AddMeasurement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[9]));
        loader.setControllerFactory(t -> buildAddMeasurementController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Agregar nueva medida");
        stageDialog.showAndWait();
    }
    
    @FXML
    private void UpdateMeasurement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[10]));
        loader.setControllerFactory(t -> buildModifyMeasurementController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Actualizar Item");
        stageDialog.showAndWait();
    }
    
    @FXML
    private void AssignMeasurementItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[11]));
        loader.setControllerFactory(t -> buildAssignMeasurementToItemController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Asignar Medidas a un Item");
        stageDialog.showAndWait();
    }
    
    private ItemDAO buildItemDAO() {
        return new SQLItemDAOImpl();
    }

    private ItemManager buildItemManager() {
        return new ItemManager(buildItemDAO());
    }
    
    private AddNewItemController buildAddNewItemController(){
        return new AddNewItemController(buildItemManager());
    }
    
    private ModifyItemController buildModifyItemController(){
        return new ModifyItemController(buildItemManager());
    }
    
    private MeasurementDAO buildMeasurementDAO() {
        return new SQLMeasurementDAOImpl();
    }

    private MeasurementManager buildMeasurementManager() {
        return new MeasurementManager(buildMeasurementDAO());
    }
    
    private AddMeasurementController buildAddMeasurementController(){
        return  new AddMeasurementController(buildItemManager(), buildMeasurementManager());
    }
    
    private ModifyMeasurementController buildModifyMeasurementController(){
        return  new ModifyMeasurementController(buildMeasurementManager());
    }
    
    private AssignMeasurementToItemController buildAssignMeasurementToItemController(){
        return new AssignMeasurementToItemController(buildItemManager(), buildMeasurementManager());
    }

    private AddHandiworkController buildHandiworkController() {
        return new AddHandiworkController(modelHandiwork, modelCustomer);
    }

    

}
