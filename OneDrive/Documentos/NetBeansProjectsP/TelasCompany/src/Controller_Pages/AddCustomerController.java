package Controller_Pages;

import Model.Customer;
import Model.CustomerManager;
import Model.ValidateInput;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddCustomerController implements Initializable {

    private CustomerManager modelCustomer;
    private Customer customer;
    private boolean modify;

    @FXML
    private TextField txfCiRuc;

    @FXML
    private TextField txfDirection;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfNames;

    @FXML
    private TextField txfSurnames;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModificar;
    @FXML
    private Label LblErrorCed;
    @FXML
    private Label LblErrorDir;
    @FXML
    private Label LblErrorNombres;
    @FXML
    private Label LblErrorSurn;
    @FXML
    private Label LblTitle;

    public AddCustomerController(CustomerManager modelCustomer, Customer customer, boolean modify) {
        this.modelCustomer = modelCustomer;
        this.customer = customer;
        this.modify = modify;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (modify) {
            LblTitle.setText("Modificar Cliente");
            showHidenFields();
            loadCustomerFields();
            setCi();

        } else {
            LblTitle.setText("Registrar Cliente");
            if (!customer.getDocCiRuc().equals("")) {
                setCi();
            }
        }

    }

    @FXML
    void addCustomer(MouseEvent event) {
        String names = txfNames.getText();
        String surnames = txfSurnames.getText();
        String ciRuc = txfCiRuc.getText();
        String direction = txfDirection.getText();
        String email = txfEmail.getText();
        if (validateFields(names, surnames, ciRuc, direction, email)) {
            int idInserted = modelCustomer.addCustomer(names.toUpperCase(), surnames.toUpperCase(), ciRuc, direction, email);
            if (idInserted != -1) {
                infoDial("Información", "Registro nuevo cliente", "Se guaradó cliente exitosamente");
            } else {
                showError("Error registro", "No se guardó registro de cliente en la base de datos", "");
            }
            customer.setDocCiRuc(ciRuc);
            closeStage(btnGuardar);
        }
    }

    @FXML
    void deleteCustomer(MouseEvent event) {
        if (modelCustomer.deleteCustomer(customer.getId())) {
             infoDial("Información", "Actualización cliente", "Se borró cliente : " + customer.getFirstName() + " " + customer.getLastName());
        } else {
            showError("Error registro", "No se borró registro de cliente en la base de datos", "El cliente actual tiene pedidos registrados");
        }
    }

    @FXML
    void updateCustomer(MouseEvent event) {
        String names = txfNames.getText();
        String surnames = txfSurnames.getText();
        String ciRuc = txfCiRuc.getText();
        String direction = txfDirection.getText();
        String email = txfEmail.getText();
        if (validateUpdatedFields(names, surnames, direction, email)) {
            if (modelCustomer.updateCustomer(customer.getId(), names, surnames, direction, email)) {
                infoDial("Información", "Actualización cliente", "Se actualizó cliente ");
                closeStage(btnGuardar);
            } else {
                showError("Error registro", "No se actualizó registro de cliente en la base de datos", "");
            }

        }
    }

    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String header, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void infoDial(String title, String header, String msg) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(header);
        infoAlert.setContentText(msg);
        infoAlert.showAndWait();
    }

    private boolean validateFields(String names, String surnames, String ciRuc, String direction, String email) {
        clearErrorLbls();
        boolean verify = true;
        ValidateInput validateInput = new ValidateInput(ciRuc);
        List<Customer> listCustomer = modelCustomer.findCustomerByCiRuc(ciRuc);
        if (ciRuc.equals("")) {
            LblErrorCed.setText("Ingrese número de cédula o RUC");
            verify = false;

        } else if (!listCustomer.isEmpty()) {
            LblErrorCed.setText("Cédula/RUC actualmente registrados");
            verify = false;
        }
        
        if (names.equals("")) {
            LblErrorNombres.setText("Ingrese dos nombres / Nombreuno Nombredos");
            verify = false;
        }
        validateInput.setText(surnames);
        if (surnames.equals("")) {
            LblErrorSurn.setText("Ingrese dos apellidos/ Apellidouno Apellidodos");
            verify = false;
        }
        validateInput.setText(direction);
        if (direction.equals("")) {
            LblErrorDir.setText("Ingrese direccion de domicilio");
            verify = false;
        }
        return verify;
    }

    private boolean validateUpdatedFields(String names, String surnames, String direction, String email) {
        clearErrorLbls();
        boolean verify = true;
        ValidateInput validateInput = new ValidateInput(names);
        if (!validateInput.names()) {
            LblErrorNombres.setText("Ingrese dos nombres / Nombreuno Nombredos");
            verify = false;
        }
        validateInput.setText(surnames);
        if (!validateInput.names()) {
            LblErrorSurn.setText("Ingrese dos apellidos/ Apellidouno Apellidodos");
            verify = false;
        }
        validateInput.setText(direction);
        if (direction.equals("")) {
            LblErrorDir.setText("Ingrese direccion de domicilio");
            verify = false;
        }
        return verify;
    }

    private void clearErrorLbls() {
        LblErrorCed.setText("");
        LblErrorDir.setText("");
        LblErrorSurn.setText("");
        LblErrorNombres.setText("");
    }

    private void showHidenFields() {
        btnDelete.setVisible(true);
        btnModificar.setVisible(true);
        btnGuardar.setVisible(false);
    }

    private void setCi() {
        txfCiRuc.setText(customer.getDocCiRuc());
        txfCiRuc.setDisable(true);
    }

    private void loadCustomerFields() {
        txfNames.setText(customer.getFirstName());
        txfSurnames.setText(customer.getLastName());
        txfDirection.setText(customer.getDirection());
        txfEmail.setText(customer.getEmail());
    }

}
