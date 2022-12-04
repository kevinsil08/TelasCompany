/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Customer;
import Model.CustomerManager;
import com.sun.org.apache.xerces.internal.impl.dv.xs.AnyURIDV;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class ModifyCustomerController implements Initializable {

    ObservableList<Customer> ObservableList;
    private CustomerManager customerModel;
    private boolean modify;
    private Customer customer;

    public ModifyCustomerController(CustomerManager customerModel, Customer customer, boolean modify) {
        this.customerModel = customerModel;
        this.customer = customer;
        this.modify = modify;
    }

    @FXML
    private TableView<Customer> TableCustomers;

    @FXML
    private TableColumn<Customer, Integer> TcCed;

    @FXML
    private TableColumn<Customer, String> TcDir, TcEmail, TcNames, TcSurnames;

    @FXML
    private TextField TxfSearch;

    @FXML
    private Button btnCancelar;
    @FXML
    private Button BtnSearch;

    @FXML
    void cancel(MouseEvent event) {
        closeStage(btnCancelar);
    }
    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        if (modify) {
            // open dialog with customer to be modified
            TableCustomers.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    modififySelected();
                }
            });
        } else {
            //get ciRuc of selected customer
            TableCustomers.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    returnSelected();
                }
            });
        }

    }
    
    private void filterTable(ObservableList<Customer> obersvableListPersons) {
        FilteredList<Customer> filteredListPersons = new FilteredList<>(obersvableListPersons, b -> true);
        TxfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListPersons.setPredicate(person -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchedString = newValue.toLowerCase();
                if (Integer.toString(person.getId()).indexOf(searchedString) > -1) {
                    return true;
                }
                if (person.getFirstName().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (person.getLastName().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (person.getEmail().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                if (person.getDirection().toLowerCase().indexOf(searchedString) > -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Customer> sortedList = new SortedList<>(filteredListPersons);
        sortedList.comparatorProperty().bind(TableCustomers.comparatorProperty());
        TableCustomers.setItems(sortedList);
    }

    private void modififySelected() {
        try {
            Customer tableItemSelected = TableCustomers.getSelectionModel().getSelectedItem();
            // ventana con los campos llenos y modificables, ademas boton eliminar por si 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddCustomer.fxml"));
            loader.setControllerFactory(t -> buildAddCustomerController(tableItemSelected));
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

    private void returnSelected() {
        try {
            Customer tableItemSelected = TableCustomers.getSelectionModel().getSelectedItem();
            if (tableItemSelected != null) {
                customer.setDocCiRuc( tableItemSelected.getDocCiRuc());
                closeStage(btnCancelar);
            } else {
                showError("Error debe seleccionar un item", "Error debe seleccionar un item");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private AddCustomerController buildAddCustomerController(Customer customer) {
        return new AddCustomerController(customerModel, customer, true);
    }

    private void refreshTable() {
        TcCed.setCellValueFactory(new PropertyValueFactory<>("docCiRuc"));
        TcNames.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TcSurnames.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TcDir.setCellValueFactory(new PropertyValueFactory<>("direction"));
        TcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ObservableList<Customer> observableListCustomers = FXCollections.observableList(customerModel.getAllCustomers());
        TableCustomers.setItems(observableListCustomers);
        filterTable(observableListCustomers);
    }
    
    private void showError(String title, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

}
