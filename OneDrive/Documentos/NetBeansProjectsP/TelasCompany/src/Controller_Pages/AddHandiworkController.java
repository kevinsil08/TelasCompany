/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import DAO.HandiworkDetailDAO;
import DAO.ItemDAO;
import DAO.MeasurementDAO;
import DAO.PaymentDAO;
import Model.Customer;
import Model.CustomerManager;
import Model.Handiwork;
import Model.HandiworkDetail;
import Model.HandiworkDetailManager;
import Model.HandiworkManager;
import Model.HandiworkPayment;
import Model.HandiworkPaymentManager;
import Model.Item;
import Model.ItemManager;
import Model.MeasurementManager;
import Model.SQLHandiworkDetailDAOImpl;
import Model.SQLHandiworkPaymentDAOImpl;
import Model.SQLItemDAOImpl;
import Model.SQLMeasurementDAOImpl;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
    Button btnAddItem, btnCancel, btnSave, btnShowDetailItem, btnPaymentsItem;

    @FXML
    private TableView<HandiworkDetail> Table = new TableView<HandiworkDetail>();
    @FXML
    private TableColumn NameItem = new TableColumn("Tipo de Item");
    ;
    @FXML
    private TableColumn DeliveryDate = new TableColumn("Fecha de Entrega");
    ;
    @FXML
    private TableColumn Payment = new TableColumn("Abonado");
    ;
    @FXML
    private TableColumn TotalCost = new TableColumn("Total");
    ;
    @FXML
    private TableColumn StatusPayment = new TableColumn("Estado de Abono");
    ;
    @FXML
    private TableColumn StatusHandiworkDetail = new TableColumn("Estado");

    @FXML
    private TextField txfCedRuc;

    @FXML
    private TextField txfNames;

    @FXML
    private TextField txfSurnames;

    @FXML
    private TextField txfCostoTotal;

    ObservableList<HandiworkDetail> ObservableList;

    private HandiworkDetailManager HandiworkDetailManager;
    private CustomerManager modelCustomer;
    private HandiworkManager modelHandiwork;
    private HandiworkPayment HandiworkPayment;

    public AddHandiworkController(HandiworkManager modelHandiwork, CustomerManager modelCustomer) {
        this.modelHandiwork = modelHandiwork;
        this.modelCustomer = modelCustomer;
    }

    @FXML
    private void showAddItem(MouseEvent event) throws IOException {
        try {

            if (event.getSource() == btnAddItem) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddItem.fxml"));
                loader.setControllerFactory(t -> buildAddItemController());
                loadStage(loader, "Agregar Item");
            }

        } catch (Exception e) {
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

    private void loadStage(FXMLLoader loader, String title) throws Exception {
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
        Table.setItems(ObservableList);
    }

    @FXML
    private void showModifyDeleteItem(MouseEvent mouseEvent) {
        try {
            HandiworkDetail tableItemSelected = Table.getSelectionModel().getSelectedItem();

            if (tableItemSelected != null) {
                btnPaymentsItem.setDisable(false);
                btnShowDetailItem.setDisable(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyEliminateItemAdded.fxml"));
                loader.setControllerFactory(t -> buildModifyCustomerController(tableItemSelected, buildHandiworkDetailManager(), buildMeasurementManager(), buildHandiworkPaymentManager()));
                loadStage(loader, "Modificar/Eliminar item ingresado");
            } else {
                showError("Error debe seleccionar un item", "Error debe seleccionar un item");
                btnPaymentsItem.setDisable(true);
                btnShowDetailItem.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPaymentsItem(MouseEvent mouseEvent) {

        try {
            HandiworkDetail tableItemSelected = Table.getSelectionModel().getSelectedItem();

            if (tableItemSelected != null) {
                btnPaymentsItem.setDisable(false);
                btnShowDetailItem.setDisable(false);
                HandiworkPayment = new HandiworkPayment();
                HandiworkPayment.setId(tableItemSelected.getId());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShowPaymentsItem.fxml"));
                loader.setControllerFactory(t -> buildShowPaymentsItemController(buildHandiworkPaymentManager()));
                loadStage(loader, "Pagos del item");

            } else {
                showError("Error debe seleccionar un item", "Error debe seleccionar un item");
                btnPaymentsItem.setDisable(true);
                btnShowDetailItem.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameItem.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
        DeliveryDate.setCellValueFactory(new PropertyValueFactory<>("DeliveryDeadline"));
        Payment.setCellValueFactory(new PropertyValueFactory<>("Payment"));
        TotalCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        StatusPayment.setCellValueFactory(new PropertyValueFactory<>("PayStatus"));
        StatusHandiworkDetail.setCellValueFactory(new PropertyValueFactory<>("State"));
        Table.getColumns().addAll(NameItem, DeliveryDate, Payment, TotalCost, StatusPayment, StatusHandiworkDetail);
        setItemstable();

        Table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                createItem();
            }
        });
    }

    private void createItem() {
        try {
            HandiworkDetail tableItemSelected = Table.getSelectionModel().getSelectedItem();

            if (tableItemSelected != null) {
                btnPaymentsItem.setDisable(false);
                btnShowDetailItem.setDisable(false);
                HandiworkPayment = new HandiworkPayment();
                HandiworkPayment.setId(tableItemSelected.getId());

            } else {
                showError("Error debe seleccionar un item", "Error debe seleccionar un item");
                btnPaymentsItem.setDisable(true);
                btnShowDetailItem.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchCustomerCI(ActionEvent event) throws IOException, Exception {
        // validar que previamente sea una cedula valida
        String ciRuc = txfCedRuc.getText();
        List<Customer> listCustomer = modelCustomer.findCustomerByCiRuc(ciRuc);
        if (!listCustomer.isEmpty()) {
            fillCustomerNames(listCustomer);
            createNewHandiwork(listCustomer);
        } else {
            newCustomerDialog(ciRuc);
        }
    }

    private void newCustomerDialog(String ciRuc) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddCustomer.fxml"));
        loader.setControllerFactory(t -> buildAddCustomerDialog(modelCustomer, ciRuc));
        loadStage(loader, "Agregar cliente");
        loadCustomerFields(ciRuc);
    }

    private void loadCustomerFields(String ciRuc) {
        List<Customer> listCustomer = modelCustomer.findCustomerByCiRuc(ciRuc);
        if (!listCustomer.isEmpty()) {
            fillCustomerNames(listCustomer);
            int idHandiwork = createNewHandiwork(listCustomer);
            if (idHandiwork != -1) {
                fillHandiworkFields(idHandiwork);
            } else {
                System.out.println("Error al ingresar la obra en la base de datos");
            }
        } else {
            // mostar mensaje de que no se registro el cliente
            System.out.println(" no se creo cliente nuevo");
        }
    }

    private int createNewHandiwork(List<Customer> listCustomer) {
        LocalDate systemDate = java.time.LocalDate.now();
        int customerId = listCustomer.get(0).getId();
        return modelHandiwork.addHandiwork(customerId, systemDate.toString(), 0., 0, false);
    }

    private void fillCustomerNames(List<Customer> listCustomer) {
        txfNames.setText(listCustomer.get(0).getFirstName());
        txfSurnames.setText(listCustomer.get(0).getLastName());
        txfNames.setDisable(true);
        txfSurnames.setDisable(true);
    }

    private void fillHandiworkFields(int idHandiwork) {
        List<Handiwork> listHandiwork = modelHandiwork.getById(idHandiwork);
        txfCostoTotal.setText(String.format("%.2f", listHandiwork.get(0).getTotalCost()));
    }

    private AddCustomerController buildAddCustomerDialog(CustomerManager modelCustomer, String ciRuc) {
        return new AddCustomerController(modelCustomer, ciRuc);
    }

    private HandiworkDetailDAO buildHandiworkDetailDao() {
        return new SQLHandiworkDetailDAOImpl();
    }

    private HandiworkDetailManager buildHandiworkDetailManager() {
        return new HandiworkDetailManager(buildHandiworkDetailDao());
    }

    private ItemDAO buildItemDAO() {
        return new SQLItemDAOImpl();
    }

    private ItemManager buildItemManager() {
        return new ItemManager(buildItemDAO());
    }

    private MeasurementDAO buildMeasurementDAO() {
        return new SQLMeasurementDAOImpl();
    }

    private MeasurementManager buildMeasurementManager() {
        return new MeasurementManager(buildMeasurementDAO());
    }

    private PaymentDAO buildPaymentDAO() {
        return new SQLHandiworkPaymentDAOImpl();
    }

    private HandiworkPaymentManager buildHandiworkPaymentManager() {
        return new HandiworkPaymentManager(buildPaymentDAO());
    }

    private AddItemController buildAddItemController() {
        return new AddItemController(buildHandiworkDetailManager(), buildItemManager(), buildMeasurementManager(), buildHandiworkPaymentManager());
    }

    private ModifyEliminateItemAddedController buildModifyCustomerController(HandiworkDetail itemSelected, HandiworkDetailManager HandiworkDtlModel, MeasurementManager MeasurementManagerModel, HandiworkPaymentManager HandiworkPaymentManagerModel) {
        return new ModifyEliminateItemAddedController(itemSelected, HandiworkDtlModel, MeasurementManagerModel, HandiworkPaymentManagerModel);
    }

    private ShowPaymentsItemController buildShowPaymentsItemController(HandiworkPaymentManager HandiworkPaymentManagerModel){
        return new ShowPaymentsItemController(HandiworkPaymentManagerModel,HandiworkPayment);
    }
    private void showError(String title, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

}
