/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.HandiworkDetail;
import Model.HandiworkDetailManager;
import Model.HandiworkPaymentManager;
import Model.Item;
import Model.Measurement;
import Model.MeasurementManager;
import Model.ValidateInput;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class ModifyEliminateItemAddedController implements Initializable {

    @FXML
    private HBox HBoxMeasurement, HBoxMeasurement2;
    
    @FXML
    private Button BtnModifyItem,BtnCancel,BtnDelete;
    
    @FXML
    private Label nameItem, PaymentDone,PayStatus;
    
    @FXML
    private ComboBox ComboStateItem;
    
    @FXML
    private TextArea TxtAreaDetail,TxtAreaAddDetail;
    
    @FXML
    private TextField TxtFTotalCost,TxtFPayment;
    
    @FXML
    private DatePicker DateDelivery;
    
    private HandiworkDetail itemSelected;
    private HandiworkDetailManager HandiworkDtlModel;
    private MeasurementManager MeasurementManagerModel;
    private HandiworkPaymentManager HandiworkPaymentManagerModel;

    public ModifyEliminateItemAddedController(HandiworkDetail itemSelected, HandiworkDetailManager HandiworkDtlModel, MeasurementManager MeasurementManagerModel, HandiworkPaymentManager HandiworkPaymentManagerModel) {
        this.itemSelected = itemSelected;
        this.HandiworkDtlModel = HandiworkDtlModel;
        this.MeasurementManagerModel = MeasurementManagerModel;
        this.HandiworkPaymentManagerModel = HandiworkPaymentManagerModel;
    }
    
    private Label[] NameMeasurement;
    private TextField[] TxtMeasurement;
    
    @FXML
    void closeDialog(MouseEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML
    private void onUpdateHandiworkDetail(MouseEvent event) throws Exception {
        String ErrorTitle = "Error de ingreso";
        String Detail = TxtAreaDetail.getText();
        String AddDetail = (itemSelected.getAddDetail() == null)
                ? null : TxtAreaAddDetail.getText();

        try{
            
        double Payment = Double.parseDouble(TxtFPayment.getText());
        double TotalCost = Double.parseDouble(TxtFTotalCost.getText());
        LocalDate DeliveryDate = DateDelivery.getValue();
        LocalDate StartDate = LocalDate.now();
        ValidateInput validateInput = new ValidateInput();
        
        if (!validateInput.firstDateBeforeSecondDate(StartDate.toString(), DeliveryDate.toString())) {
            showError(ErrorTitle , "La fecha de entrega debe ser mayor a la actual");
            return;
        }
        
        if(Payment+itemSelected.getPayment()>TotalCost){
            showError(ErrorTitle , "El Abono más lo abonado no debe ser mayor al Costo Total");
            return;
        }
        
        String state="p"; 
        if(ComboStateItem.getSelectionModel().getSelectedIndex()==0){
            state="f";
        }
        
        String payStatus = "0";
        
        double totalPayment = Payment+itemSelected.getPayment();
        
        if(totalPayment==TotalCost){
            payStatus = "1";
        }
        
        HandiworkDetail HandiworkDetail = new HandiworkDetail(itemSelected.getId(),itemSelected.getTypeItemId(), 1, StartDate.toString(), Detail, AddDetail, TotalCost, DeliveryDate.toString(), payStatus, state);
        
        HandiworkDtlModel.UpdateHandiworkDetail(HandiworkDetail);
        HandiworkPaymentManagerModel.AddHandiworkPayment(itemSelected.getId(), StartDate.toString(), Payment);
        
            /*for (int i = 0; i < TxtMeasurement.length; i++) {
                MeasurementManagerModel.AddMeasurementValues(Integer.parseInt(TxtMeasurement[i].getId()), lastId,Double.parseDouble(TxtMeasurement[i].getText()));
            }*/
        closeStage();
        
        }catch(NumberFormatException e){
            showError(ErrorTitle , "Solo debe ingresar números en Precio Total y/o Abono");
        }
    }
    
    @FXML
    private void onDeleteHandiworkDetail(MouseEvent event) throws Exception {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameItem.setText("Tipo de Item: "+itemSelected.getNameItem());
        TxtAreaDetail.setText(itemSelected.getDetail());
        
        System.out.println("aaa "+itemSelected.getAddDetail());
        
        String AddDetail = (itemSelected.getAddDetail() == null)
                ? null : TxtAreaAddDetail.getText();
        
        TxtAreaAddDetail.setText(AddDetail);
        TxtFTotalCost.setText(itemSelected.getCost()+"");
        DateDelivery.setValue(LOCAL_DATE(itemSelected.getDeliveryDeadline()));
        PaymentDone.setText("Abonado: "+itemSelected.getPayment()+" $");
        String valor = (itemSelected.getPayStatus().equals("0")?"No Pagado":"Pagado");
        PayStatus.setText("Estado de Pago: "+valor);
        
        String[] StateItem = {"Realizado","Pendiente"};
        ComboStateItem.setItems(FXCollections.observableArrayList(StateItem));
        onClickItem();
        
    }    
    
    public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
}
    
    private void onClickItem() {
        removeLabelsTextFields();

        int MAXVALUETOHBOX = 4;
        List<Measurement> listMeasurement = MeasurementManagerModel.ListValuesMeasurementOfItem(itemSelected.getId());
        
        System.out.println(listMeasurement.size());
        NameMeasurement = new Label[listMeasurement.size()];
        TxtMeasurement = new TextField[listMeasurement.size()];
        int ITERATOR = 0;
        while (ITERATOR < listMeasurement.size()) {

            NameMeasurement[ITERATOR] = new Label((String) listMeasurement.get(ITERATOR).getName());
            NameMeasurement[ITERATOR].setPadding(new Insets(0.0, 20.0, 0.0, 20.0));
            NameMeasurement[ITERATOR].setFont(new Font("Roboto", 22.0));
            TxtMeasurement[ITERATOR] = new TextField((Double) listMeasurement.get(ITERATOR).getValue()+"");
            TxtMeasurement[ITERATOR].setPrefSize(78, 26);
            TxtMeasurement[ITERATOR].setId(""+listMeasurement.get(ITERATOR).getId());
            
            if (ITERATOR > MAXVALUETOHBOX) {
                HBoxMeasurement2.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
            } else {
                HBoxMeasurement.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
            }
            ITERATOR++;
        }
    }
    
    private void removeLabelsTextFields() {

        if (NameMeasurement != null) {
            HBoxMeasurement.getChildren().removeAll(NameMeasurement);
            HBoxMeasurement.getChildren().removeAll(TxtMeasurement);
            HBoxMeasurement2.getChildren().removeAll(NameMeasurement);
            HBoxMeasurement2.getChildren().removeAll(TxtMeasurement);
        }

    }
    
    private void showError(String title , String error ){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
    
}
