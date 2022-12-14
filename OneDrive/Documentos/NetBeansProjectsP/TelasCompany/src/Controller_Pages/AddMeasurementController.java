/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Item;
import Model.ItemManager;
import Model.MeasurementManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class AddMeasurementController implements Initializable {
    
    @FXML
    private Button BtnCancel;
    
    @FXML
    private ComboBox ComboItems;
    
    @FXML
    private TextField TxtName;

    private List<Item> ListItems;
    private Item itemSelected;
    
    private ItemManager ItemManagerModel;
    private MeasurementManager MeasurementManagerModel;
    

    public AddMeasurementController(ItemManager ItemManagerModel, MeasurementManager MeasurementManagerModel) {
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
    private void AddMeasurement(MouseEvent event) throws Exception {
        String ErrorTitle = "Error de ingreso";
        String NameItem = TxtName.getText();
        try{
            if(NameItem.isEmpty()){
                showError(ErrorTitle, "Debe ingresar el nombre de la Medida");
                return;
            }
            
            if(ComboItems.getSelectionModel().getSelectedIndex()== -1){
                showError(ErrorTitle, "Debe seleccionar un item");
                return;
            }
            
            if(showConfirmation()){
                int LastId = MeasurementManagerModel.AddMeasurement(NameItem);
                MeasurementManagerModel.InsertItemMeasurement(itemSelected.getId(), LastId);
                closeStage();
            }
            
            
        }catch(Exception e){
                showError(ErrorTitle, "Error en la Base de Datos");
                e.printStackTrace();
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
                        itemSelected = item;
                        break;
                    }
                }
            }
        });
    }    
    
    private void setItemsComboBox(ObservableList<String> observableListItems) {
        ComboItems.setItems(observableListItems);
    }
    
    private void showError(String title , String error ){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
    
    private boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Desea ingresar una nueva medida?");
        Optional<ButtonType> action = alert.showAndWait();
        boolean OptionChoosed = true;
        if (action.get() != ButtonType.OK) {
            OptionChoosed = false;
        }

        return OptionChoosed;
    }
    
}
