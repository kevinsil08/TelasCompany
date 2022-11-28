/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Customer;
import Model.CustomerManager;
import Model.Handiwork;
import Model.HandiworkManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
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

    @FXML
    private TableView<Handiwork> TbvHandiworks;

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
    @FXML
    private TextField TxfSearch;
    //Arreglos
    @FXML
    private Button BtnArreglos;

    private Tab ArreglosTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<Handiwork, String> TcCiRuc, TcHanState, TcNames, TcPaymentStatus;

    @FXML
    private TableColumn<Handiwork, Integer> TcHanId;

    @FXML
    private TableColumn<Handiwork, Double> TcPaymentLeft, TcTotalHanCost;

    @FXML
    private MenuItem MitAddCustomer;
    @FXML
    private MenuItem MitModifyCustomer;

    private Tab elaboracionTab, obrasPendientesTab;

    private final String[] Paths = {"/View/TableOrders.fxml", "/View/Elaboracion.fxml", "/View/AddCustomer.fxml",
        "/View/ModifyCustomer.fxml", "/View/DeleteCustomer.fxml", "/View/Arreglos.fxml", "/View/AddHandiwork.fxml"};

    private TableOrdersController buildTableOrdersController() {
        return new TableOrdersController();
    }

    private ElaboracionController buildElaboracionController() {
        return new ElaboracionController();
    }

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
            loader.setControllerFactory(t -> buildHandiworkController(-1));
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
    void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddCustomer.fxml"));
        loader.setControllerFactory(t -> buildAddCustomerController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Agregar cliente");
        stageDialog.showAndWait();
    }

    @FXML
    void modifyCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomer.fxml"));
        loader.setControllerFactory(t -> buildModifyCustomerController());
        Stage stageDialog = new Stage();
        stageDialog.initModality(Modality.APPLICATION_MODAL);
        stageDialog.setScene(new Scene(loader.load()));
        stageDialog.setTitle("Buscar cliente");
        stageDialog.showAndWait();
    }

    private AddHandiworkController buildHandiworkController(int id_handiwork) {
        return new AddHandiworkController(modelHandiwork, modelCustomer, id_handiwork, false);
    }

    private void refreshTable() {
        TcCiRuc.setCellValueFactory(new PropertyValueFactory<>("ciCustomer"));
        TcNames.setCellValueFactory(new PropertyValueFactory<>("namesCustomer"));
        TcHanId.setCellValueFactory(new PropertyValueFactory<>("handiWorkID"));
        TcPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("payStatus"));
        TcHanState.setCellValueFactory(new PropertyValueFactory<>("stateString"));
        TcTotalHanCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        TcPaymentLeft.setCellValueFactory(new PropertyValueFactory<>("leftPayment"));
        ObservableList<Handiwork> obListHandiwork = FXCollections.observableList(modelHandiwork.getPendingHandworks());
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
                if (Integer.toString(handiwork.getHandiWorkID()).indexOf(searchedString) > -1) {
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
            loader.setControllerFactory(t -> buildHandiworkController(tableItemSelected.getHandiWorkID()));
            Stage stageDialog = new Stage();
            stageDialog.initModality(Modality.APPLICATION_MODAL);
            stageDialog.setScene(new Scene(loader.load()));
            stageDialog.setTitle("Agregar cliente");
            stageDialog.showAndWait();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
