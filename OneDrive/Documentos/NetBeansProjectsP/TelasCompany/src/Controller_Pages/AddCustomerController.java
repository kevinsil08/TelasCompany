package Controller_Pages;

import Model.CustomerManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddCustomerController implements Initializable {

    private CustomerManager modelCustomer;
    private String givenCiRuc;

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

    public AddCustomerController(CustomerManager modelCustomer, String givenCiRuc) {
        this.modelCustomer = modelCustomer;
        this.givenCiRuc = givenCiRuc;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!givenCiRuc.equals("")) {
            // previously seted ci/ruc
            txfCiRuc.setText(givenCiRuc);
            txfCiRuc.setDisable(true);
        } else {
            // create with empty ci / ruc

        }
    }

    @FXML
    void addCustomer(MouseEvent event) {
        String names = txfNames.getText();
        String surnames = txfSurnames.getText();
        String ciRuc = txfCiRuc.getText();
        String direction = txfDirection.getText();
        String email = txfEmail.getText();
        modelCustomer.addCustomer(names, surnames, ciRuc, direction, email);
        closeStage(btnGuardar);
    }

    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
