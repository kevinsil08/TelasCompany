/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Item;
import Model.ItemManager;
import Model.Measurement;
import Model.MeasurementManager;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
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
public class AssignMeasurementToItemController implements Initializable {

    @FXML
    private Button BtnCancel;
    
    @FXML
    private ComboBox ComboItems;    
    
    @FXML
    private ComboBox ComboMeasurements;
    
    private List<Measurement> ListMeasurements;
    private Measurement MeasurementSelected;
    private MeasurementManager MeasurementManagerModel;

    private List<Item> ListItems;
    private Item itemSelected;
    private ItemManager ItemManagerModel;
    
    ObservableList<Measurement> ObservableList;
    
    @FXML
    private TableView<Measurement> Table = new TableView<Measurement>();
    
    @FXML
    private TableColumn NameMeasurement = new TableColumn("Nombre de la Medida");
    
    public AssignMeasurementToItemController(ItemManager ItemManagerModel,MeasurementManager MeasurementManagerModel) {
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
    private void InsertMeasurementOfAItem(MouseEvent event){
        String ErrorTitle = "Error de ingreso";
        try{
            
            if(ComboItems.getSelectionModel().getSelectedIndex()== -1){
                showError(ErrorTitle, "Debe seleccionar un item");
                return;
            }
            
            if(ComboMeasurements.getSelectionModel().getSelectedIndex()== -1){
                showError(ErrorTitle, "Debe seleccionar una medida");
                return;
            }
            
            if(showConfirmation()){
                int Confirmation=MeasurementManagerModel.InsertItemMeasurement(itemSelected.getId(), MeasurementSelected.getId());
                
                if(Confirmation==-1){
                    showError(ErrorTitle, "La medida seleccionada ya se encuentra en el item");
                }else{
                    closeStage();
                }
                
            }
            
        }catch(Exception SQLException){
           showError(ErrorTitle, "La medida seleccionada ya se encuentra en el item");
            SQLException.printStackTrace();
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameMeasurement.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Table.getColumns().addAll( NameMeasurement);
        initializeMeasurement();
        initializeItem();
        
    }    
    
    private void initializeMeasurement(){
        ListMeasurements = MeasurementManagerModel.ListMeasurements();

        ArrayList<String> MeasurementsNames = new ArrayList(ListMeasurements.size());
        for (int i = 0; i < ListMeasurements.size(); i++) {
            Measurement measurement = (Measurement) ListMeasurements.get(i);
            MeasurementsNames.add(measurement.getName());
        }
        ObservableList<String> observableListMeasurements = FXCollections.observableList(MeasurementsNames);
        setMeasurementsComboBox(observableListMeasurements);
        
        ComboMeasurements.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String FirstValue, String LastValue) {
                Measurement measurement;
                for (int i = 0; i < ListMeasurements.size(); i++) {
                    measurement = (Measurement) ListMeasurements.get(i);
                    if (measurement.getName().equals((String) ComboMeasurements.getSelectionModel().getSelectedItem())) {
                        MeasurementSelected = measurement;
                        break;
                    }
                }
            }
        });
    }
    
    private void initializeItem(){
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
                        insertItemMeasurementInTable(itemSelected.getId());
                        break;
                    }
                }
            }
        });
    }
    
    private void insertItemMeasurementInTable(int ItemId){
        List<Measurement> listMeasurement = MeasurementManagerModel.ListMeasurement(ItemId);
        ObservableList = FXCollections.observableList(listMeasurement);
        Table.setItems(null);
        Table.setItems(ObservableList);
    }
    
    private void setItemsComboBox(ObservableList<String> observableListItems) {
        ComboItems.setItems(observableListItems);
    }
    
    private void setMeasurementsComboBox(ObservableList<String> observableListItems) {
        ComboMeasurements.setItems(observableListItems);
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
        alert.setContentText("¿Desea que la medida seleccionada esté en el item seleccionado?");
        Optional<ButtonType> action = alert.showAndWait();
        boolean OptionChoosed = true;
        if (action.get() != ButtonType.OK) {
            OptionChoosed = false;
        }

        return OptionChoosed;
    }
    
}
