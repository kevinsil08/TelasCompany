/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import DAO.HandiworkDetailDAO;
import DAO.ItemDAO;
import DAO.MeasurementDAO;
import Model.HandiworkDetail;
import Model.HandiworkDetailManager;
import Model.Item;
import Model.ItemManager;
import Model.MeasurementManager;
import Model.SQLHandiworkDetailDAOImpl;
import Model.SQLItemDAOImpl;
import Model.SQLMeasurementDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class AddHandiworkController implements Initializable {

    @FXML
    Button btnAddItem, btnCancel, btnSave;
    
    @FXML
    private  TableView<HandiworkDetail> Table = new TableView<HandiworkDetail>();
    @FXML
    private TableColumn NameItem = new TableColumn("Tipo de Item");;
    @FXML
    private TableColumn DeliveryDate = new TableColumn("Fecha de Entrega");;
    @FXML
    private TableColumn Payment = new TableColumn("Abonado");;
    @FXML
    private TableColumn TotalCost = new TableColumn("Total");;
    @FXML
    private TableColumn StatusPayment = new TableColumn("Estado de Abono");;
    @FXML
    private TableColumn StatusHandiworkDetail = new TableColumn("Estado");;
    
    ObservableList<HandiworkDetail> ObservableList;
    
    private HandiworkDetailManager HandiworkDetailManager;

    @FXML
    private void showAddItem(MouseEvent event) throws IOException {
        try{
            
            if (event.getSource() == btnAddItem) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddItem.fxml"));
                loader.setControllerFactory(t -> buildAddItemController());
                loadStage(loader, "Agregar Item");
            }
            
        }catch(Exception e){
                e.printStackTrace();
        }
    }

    @FXML
    void closeDialog(MouseEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void loadStage(FXMLLoader loader, String title) throws Exception{
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle(title);
        stageDialog.showAndWait();
        
        HandiworkDetailManager = new HandiworkDetailManager(buildHandiworkDetailDao());
        List<HandiworkDetail> ListItems = HandiworkDetailManager.ListItemsForTableView(1);
        ObservableList = FXCollections.observableList(ListItems);
        setItemstable();
    }
    
    public void setItemstable() {
        Table.setItems(null);
        
        NameItem.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
        DeliveryDate.setCellValueFactory(new PropertyValueFactory<>("DeliveryDeadline"));
        Payment.setCellValueFactory(new PropertyValueFactory<>("Payment"));
        TotalCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        StatusPayment.setCellValueFactory(new PropertyValueFactory<>("PayStatus"));
        StatusHandiworkDetail.setCellValueFactory(new PropertyValueFactory<>("State"));

        Table.setItems(ObservableList);
        Table.getColumns().addAll(NameItem, DeliveryDate, Payment, TotalCost, StatusPayment, StatusHandiworkDetail);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private HandiworkDetailDAO buildHandiworkDetailDao() {
        return new SQLHandiworkDetailDAOImpl();
    }
    
    private HandiworkDetailManager buildHandiworkDetailManager (){
        return new HandiworkDetailManager (buildHandiworkDetailDao());
    }
    
    private ItemDAO buildItemDAO(){
        return new SQLItemDAOImpl();
    }
    
    private ItemManager buildItemManager(){
        return new ItemManager(buildItemDAO());
    }
    
    private MeasurementDAO buildMeasurementDAO(){
        return new SQLMeasurementDAOImpl();
    }
    
    private MeasurementManager buildMeasurementManager(){
        return new MeasurementManager(buildMeasurementDAO());
    }
    

    private AddItemController buildAddItemController() {
        return new AddItemController(buildHandiworkDetailManager(),buildItemManager(),buildMeasurementManager());
    }

}
