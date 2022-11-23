/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.HandiworkDetailManager;
import Model.Item;
import Model.ItemManager;
import Model.MeasurementManager;
import Model.ValidateInput;
import com.sun.webkit.graphics.WCGraphicsManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class AddItemController implements Initializable {

    @FXML
    private ComboBox ComboItems;

    @FXML
    private HBox HBoxMeasurement, HBoxMeasurement2;

    @FXML
    private TextArea TxtAreaDetail, TxtAreaAddDetail;

    @FXML
    private TextField TxtFTotalCost, TxtFPayment;

    @FXML
    private DatePicker DateDelivery;

    @FXML
    private Button BtnAddItem,BtnCancel;

    private Label[] NameMeasurement;
    private TextField[] TxtMeasurement;
    private List<Item> ListItems;
    private Item itemSelected;

    private HandiworkDetailManager HandiworkDtlModel;
    private ItemManager ItemManagerModel;
    private MeasurementManager MeasurementManagerModel;

    public AddItemController(HandiworkDetailManager HandiworkDtlModel, ItemManager ItemManagerModel, MeasurementManager MeasurementManagerModel) {
        this.HandiworkDtlModel = HandiworkDtlModel;
        this.ItemManagerModel = ItemManagerModel;
        this.MeasurementManagerModel = MeasurementManagerModel;
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
    private void onAddHandiworkDetail(MouseEvent event) throws Exception {
        String ErrorTitle = "Error de ingreso";
        String Detail = TxtAreaDetail.getText();
        String AddDetail = (TxtAreaAddDetail.getText().isEmpty())
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
        
        if(Payment>TotalCost){
            showError(ErrorTitle , "El Abono no debe ser mayor al Costo Total");
            return;
        }

        //Handiwork PrincipalHandiwork = Handiwork
        HandiworkDtlModel.AddHandiworkDetail(itemSelected.getId(), 1, StartDate.toString(), Detail, AddDetail, TotalCost, DeliveryDate.toString(), "f", "p");
        closeStage();
        
        }catch(NumberFormatException e){
            showError(ErrorTitle , "Solo debe ingresar números en Precio Total y/o Abono");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListItems = ItemManagerModel.ListItems();

        ArrayList<String> ItemsNames = new ArrayList(ListItems.size());
        for (int i = 0; i < ListItems.size(); i++) {
            Item item = (Item) ListItems.get(i);
            ItemsNames.add(item.getName());
        }

        ObservableList<String> observableListItems = FXCollections.observableList(ItemsNames);
        setItemsComboBox(observableListItems);

        ComboItems.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String FirstValue, String LastValue) {
                Item item;
                for (int i = 0; i < ListItems.size(); i++) {
                    item = (Item) ListItems.get(i);
                    if (item.getName().equals((String) ComboItems.getSelectionModel().getSelectedItem())) {
                        onClickItem(item.getId());
                        EnableElements();
                        itemSelected = item;
                        break;
                    }
                }
            }
        });
    }

    private void EnableElements() {
        TxtAreaDetail.setDisable(false);
        TxtAreaAddDetail.setDisable(false);
        TxtFTotalCost.setDisable(false);
        TxtFPayment.setDisable(false);
        DateDelivery.setDisable(false);
        BtnAddItem.setDisable(false);
    }

    private void onClickItem(int ItemId) {
        removeLabelsTextFields();

        int MAXVALUETOHBOX = 4;
        List<String> listMeasurement = MeasurementManagerModel.ListMeasurement(ItemId);
        NameMeasurement = new Label[listMeasurement.size()];
        TxtMeasurement = new TextField[listMeasurement.size()];
        int ITERATOR = 0;
        while (ITERATOR < listMeasurement.size()) {

            NameMeasurement[ITERATOR] = new Label((String) listMeasurement.get(ITERATOR));
            NameMeasurement[ITERATOR].setPadding(new Insets(0.0, 20.0, 0.0, 20.0));
            NameMeasurement[ITERATOR].setFont(new Font("Roboto", 22.0));
            TxtMeasurement[ITERATOR] = new TextField();
            TxtMeasurement[ITERATOR].setPrefSize(78, 26);
            
            if (ITERATOR > MAXVALUETOHBOX) {
                HBoxMeasurement2.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
            } else {
                HBoxMeasurement.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
            }
            ITERATOR++;
        }
    }

    private void setItemsComboBox(ObservableList<String> observableListItems) {
        ComboItems.setItems(observableListItems);
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