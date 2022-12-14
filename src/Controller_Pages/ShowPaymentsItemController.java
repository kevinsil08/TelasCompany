/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.HandiworkDetail;
import Model.HandiworkDetailManager;
import Model.HandiworkPayment;
import Model.HandiworkPaymentManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class ShowPaymentsItemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField TxtFPayment;

    @FXML
    private Button BtnCancel, BtnAddPayment;

    @FXML
    private Label PaymentDone, ActualDate, TotalCost, StatePayment;

    @FXML
    private TableView<HandiworkPayment> TablePayments;

    @FXML
    private TableColumn Value = new TableColumn("Valor");
    ;
    @FXML
    private TableColumn DatePayment = new TableColumn("Fecha de Pago");
    ;
    @FXML
    private TableColumn NamePayment = new TableColumn("Nombre del Item");

    ObservableList<HandiworkPayment> ObservableList;

    private HandiworkDetailManager HandiworkDtlModel;
    private HandiworkPaymentManager HandiworkPaymentManagerModel;
    private HandiworkPayment HandiworkPayment;
    private HandiworkDetail HandiworkDetail;

    public ShowPaymentsItemController(HandiworkPaymentManager HandiworkPaymentManagerModel, HandiworkPayment HandiworkPayment, HandiworkDetail HandiworkDetail, HandiworkDetailManager HandiworkDtlModel) {
        this.HandiworkPaymentManagerModel = HandiworkPaymentManagerModel;
        this.HandiworkDtlModel = HandiworkDtlModel;
        this.HandiworkPayment = HandiworkPayment;
        this.HandiworkDetail = HandiworkDetail;
    }

    @FXML
    void closeDialog(MouseEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void InsertPayment() {
        String ErrorTitle = "Error de ingreso";
        
         if (HandiworkDetail.getCost() == HandiworkPayment.getTotalPayment()) {
             showError(ErrorTitle, "No puede ingresar m??s abonos, el item ha sido pagado");
             return;
         }

        if (showConfirmation()) {
            
            try {
                LocalDate StartDate = LocalDate.now();

                double Payment = Double.parseDouble(TxtFPayment.getText());

                if (Payment + HandiworkPayment.getTotalPayment() > HandiworkDetail.getCost()) {
                    showError(ErrorTitle, "El Abono m??s lo abonado no debe ser mayor al Precio Total");
                    return;
                }
                double totalPayment = Payment + HandiworkPayment.getTotalPayment();

                if (totalPayment == HandiworkDetail.getCost()) {
                    String payStatus = "1";
                    HandiworkDetail = new HandiworkDetail(HandiworkDetail.getId(), HandiworkDetail.getTypeItemId(), 1, StartDate.toString(), HandiworkDetail.getDetail(), HandiworkDetail.getAddDetail(), HandiworkDetail.getCost(), HandiworkDetail.getDeliveryDeadline(), payStatus, HandiworkDetail.getState());
                    StatePayment.setText("Estado de Pago: Pagado");
                }

                HandiworkDtlModel.UpdateHandiworkDetail(HandiworkDetail);

                HandiworkPaymentManagerModel.AddHandiworkPayment(HandiworkPayment.getId(), StartDate.toString(), Payment);
                ObservableList = setInformationPayments();
                setItemstable();
            } catch (NumberFormatException e) {
                showError(ErrorTitle, "Solo debe ingresar n??meros en Precio Total y/o Abono");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate StartDate = LocalDate.now();
        ActualDate.setText("Fecha Actual: " + StartDate.toString());
        NamePayment.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
        DatePayment.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Value.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        TablePayments.getColumns().addAll(NamePayment, DatePayment, Value);
        ObservableList = setInformationPayments();
        setItemstable();
    }

    private ObservableList setInformationPayments() {
        List<HandiworkPayment> ListPayments = HandiworkPaymentManagerModel.ListPaymentWithHandiworkId(HandiworkPayment.getId());
        if (ListPayments.size() > 0) {
            TotalCost.setText("Precio Total: " + HandiworkDetail.getCost() + " $");
            HandiworkPayment = (HandiworkPayment) ListPayments.get(0);
            HandiworkPayment.setTotalPayment(HandiworkPaymentManagerModel.TotalPaymentDone(HandiworkPayment.getId()));
            PaymentDone.setText("Abonado: " + HandiworkPayment.getTotalPayment() + " $");

            if (HandiworkDetail.getCost() == HandiworkPayment.getTotalPayment()) {
                TxtFPayment.setDisable(true);
                StatePayment.setText("Estado de Pago: Pagado");
            }

            return ObservableList = FXCollections.observableList(ListPayments);
        }

        return null;
    }

    public void setItemstable() {
        TablePayments.setItems(null);
        TablePayments.setItems(ObservableList);
    }

    private void showError(String title, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        alert.setTitle("Confirmaci??n");
        alert.setContentText("??Desea ingresar un abono?");
        Optional<ButtonType> action = alert.showAndWait();
        boolean OptionChoosed = true;
        if (action.get() != ButtonType.OK) {
            OptionChoosed = false;
        }

        return OptionChoosed;
    }
}
