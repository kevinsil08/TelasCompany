/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;


import Model.Customer;

import DAO.ItemDAO;
import DAO.MeasurementDAO;

import Model.CustomerManager;
import Model.Handiwork;
import Model.HandiworkManager;

import Model.ItemManager;
import Model.MeasurementManager;
import Model.SQLItemDAOImpl;
import Model.SQLMeasurementDAOImpl;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author kevin
 */
public class PrincipalHomeController implements Initializable {

    private Stage stage;


    private double StageWidth, StageHeight;
    
    @FXML
    private MenuBar menuBarHandiworks ;
    
    @FXML
    private TableView<Handiwork> TbvHandiworks;

    private CustomerManager modelCustomer;
    private HandiworkManager modelHandiwork;
    private boolean showAllHandiworks;

    public PrincipalHomeController(CustomerManager modelCustomer, HandiworkManager modelHandiwork, Stage stage , boolean showAllHandiworks) {
        this.modelCustomer = modelCustomer;
        this.modelHandiwork = modelHandiwork;
        this.stage = stage;
        this.showAllHandiworks = showAllHandiworks;
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
    @FXML
    private TextField TxfSearch;
    //Arreglos
    @FXML
    private Button BtnArreglos;
    @FXML
    private Tab ArreglosTab;
    @FXML
    private Button btnListAllHandiworks;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label lblPrincipalTitle;

    @FXML
    private TableColumn<Handiwork, String> TcCiRuc, TcHanState, TcNames, TcPaymentStatus;
    @FXML
    private HBox hboxButton;
    @FXML
    private TableColumn<Handiwork, String> TcHanDate;

    @FXML
    private TableColumn<Handiwork, Double> TcPaymentLeft, TcTotalHanCost;
    @FXML
    private MenuItem MitAddCustomer;
    @FXML
    private MenuItem MitModifyCustomer;
    @FXML
    private HBox hboxFiltro;
    
    

    private Tab elaboracionTab, obrasPendientesTab;

    private final String[] Paths = {"/View/TableOrders.fxml", "/View/Elaboracion.fxml", "/View/AddCustomer.fxml",
        "/View/ModifyCustomer.fxml", "/View/DeleteCustomer.fxml","/View/Arreglos.fxml","/View/AddHandiwork.fxml",
        "/View/AddNewItem.fxml","/View/ModifyItem.fxml","/View/AddMeasurement.fxml","/View/ModifyMeasurement.fxml",
        "/View/AssignMeasurementToItem.fxml"};

    

    private AddCustomerController buildAddCustomerController() {
        Customer customer = new Customer();
        customer.setDocCiRuc("");
        return new AddCustomerController(modelCustomer, customer, false);
    }

    private ModifyCustomerController buildModifyCustomerController() {
        Customer customer = new Customer();
        customer.setDocCiRuc("");
        return new ModifyCustomerController(modelCustomer, customer, true);
    }

    private DeleteCustomerController buildDeleteCustomerController() {
        return new DeleteCustomerController();
    }

    private ArreglosController buildArreglosController() {
        return new ArreglosController();
    }

    @FXML
    private void dashboardButtons(MouseEvent event) throws Exception {

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
            loader.setControllerFactory(t -> buildHandiworkController(-1 , false));
            Stage stageDialog = new Stage();
            stageDialog.initModality(Modality.APPLICATION_MODAL);
            stageDialog.setScene(new Scene(loader.load()));
            stageDialog.setTitle("Obra");
            stageDialog.showAndWait();
            refreshTable();

            return;
        }
        NUMBERTAB++;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        verifyModal();
        /*int NUMBERTAB = 0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
        loader.setControllerFactory(t -> buildTableOrdersController());

        NUMBERTAB++;
        loader = new FXMLLoader(getClass().getResource(Paths[NUMBERTAB]));
        loader.setControllerFactory(t -> buildElaboracionController());*/

        try {
            TbvHandiworks.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    openSelected();
                }
            });
        } catch (Exception e) {
            showError("Error ingreso", "Seleccione una obra de la tabla", "");
        }
    }

    @FXML
    void addCustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddCustomer.fxml"));
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

    @FXML
    void modifyCustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomer.fxml"));
        loader.setControllerFactory(t -> buildModifyCustomerController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Buscar cliente");
        stageDialog.showAndWait();
    }

    private AddHandiworkController buildHandiworkController(int id_handiwork , boolean  modify) {
        return new AddHandiworkController(modelHandiwork, modelCustomer, id_handiwork, modify);
    }

    public void refreshTable() {
        TcCiRuc.setCellValueFactory(new PropertyValueFactory<>("ciCustomer"));
        TcNames.setCellValueFactory(new PropertyValueFactory<>("namesCustomer"));
        TcHanDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        TcPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("payStatus"));
        TcHanState.setCellValueFactory(new PropertyValueFactory<>("stateString"));
        TcTotalHanCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        TcPaymentLeft.setCellValueFactory(new PropertyValueFactory<>("leftPayment"));
        ObservableList<Handiwork> obListHandiwork = showAllHandiworks ?  FXCollections.observableList(modelHandiwork.getAllHandiworks()) : FXCollections.observableList(modelHandiwork.getPendingHandworks()) ;
        TbvHandiworks.setItems(obListHandiwork);
        filterTable(obListHandiwork);
    }

    private void filterTable(ObservableList<Handiwork> onbsListHandiworks) {
        FilteredList<Handiwork> filteredListHandiworks = new FilteredList<>(onbsListHandiworks, b -> true);
        TxfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListHandiworks.setPredicate(handiwork -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchedString = newValue.toLowerCase();
                if (handiwork.getEntryDate().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (handiwork.getNamesCustomer().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (handiwork.getCiCustomer().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (handiwork.getPayStatus().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (handiwork.getStateString().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Handiwork> sortedList = new SortedList<>(filteredListHandiworks);
        sortedList.comparatorProperty().bind(TbvHandiworks.comparatorProperty());
        TbvHandiworks.setItems(sortedList);
    }

    private void showError(String title, String header, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void openSelected() {
        try {
            Handiwork tableItemSelected = TbvHandiworks.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddHandiwork.fxml"));
            loader.setControllerFactory(t -> buildHandiworkController(tableItemSelected.getHandiWorkID(), true));
            Stage stageDialog = new Stage();
            stageDialog.initModality(Modality.APPLICATION_MODAL);
            stageDialog.setScene(new Scene(loader.load()));
            stageDialog.setTitle("Obra");
            stageDialog.showAndWait();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifyModal() {
        if (showAllHandiworks) {
            menuBarHandiworks.setVisible(false);
            hboxButton.getChildren().clear();
            insertListButtons();
        } 
    }
    
    @FXML
    void showAllHandiworks(ActionEvent event) throws IOException {
        showPrincipalStage(true);
    }
    
    private PrincipalHomeController buildController( Stage stage , boolean modalWindow) {
        return new PrincipalHomeController(  modelCustomer  , modelHandiwork, stage ,  modalWindow);
    }

    private void insertListButtons() {
        lblPrincipalTitle.setText("Listado de todas las Obras");
        Button BtnBack = new Button("Regresar");
        BtnBack.getStylesheets().add("/css/styles.css");
        BtnBack.getStyleClass().add("sale");
        BtnBack.getStyleClass().add("cancelButton");
        BtnBack.setPadding(new Insets(0, 20, 0, 10));
        BtnBack.setPrefSize(240, 60);
        BtnBack.maxWidth(60);
//        DatePicker dpInitial = new DatePicker();
//        dpInitial.setPromptText("Fecha inicial");
        hboxFiltro.setSpacing(10);
//        hboxFiltro.getChildren().add(dpInitial);
        hboxButton.getChildren().addAll(BtnBack);
        
        BtnBack.setOnAction((ActionEvent event) -> {
            try {
                showPrincipalStage(false);
            } catch (IOException ex) {
                System.out.println(" Error carga de vista : PrincipalHome.fxml");
                Logger.getLogger(PrincipalHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void showPrincipalStage(boolean modalWindow) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PrincipalHome.fxml"));
        stage.setTitle("Telas Company");
        loader.setControllerFactory(t -> buildController(stage,modalWindow ));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

}
