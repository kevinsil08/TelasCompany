/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Measurement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class ModifyMeasurementController implements Initializable {
@FXML
    private Button BtnCancel;
    
    @FXML
    private ComboBox ComboMeasurements;
    
    @FXML
    private Label NameMeasurement;
    
    @FXML
    private TextField TxtNewName;

    private List<Measurement> ListMeasurements;
    private Measurement MeasurementSelected;
    
    private MeasurementManager MeasurementManagerModel;
    
    public ModifyMeasurementController(MeasurementManager MeasurementManagerModel) {
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
    private void UpdateMeasurement(MouseEvent event) throws Exception {
        String ErrorTitle = "Error de ingreso";
        String NewNameItem = TxtNewName.getText();
        try{
            if(NewNameItem.isEmpty()){
                showError(ErrorTitle, "Debe ingresar el nuevo nombre del item");
                return;
            }
            
            if(ComboMeasurements.getSelectionModel().getSelectedIndex()== -1){
                showError(ErrorTitle, "Debe seleccionar un item");
                return;
            }
            
            if(showConfirmation()){
                MeasurementSelected.setName(NewNameItem);
                MeasurementManagerModel.UpdateMeasurement(MeasurementSelected);
                closeStage();
            }
            
        }catch(Exception e){
                showError(ErrorTitle, "Error en la Base de Datos");
                e.printStackTrace();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                        NameMeasurement.setText("Nombre de la Medida: "+MeasurementSelected.getName());
                        break;
                    }
                }
            }
        });
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
        alert.setContentText("¿Desea actualizar el nombre de la medida?");
        Optional<ButtonType> action = alert.showAndWait();
        boolean OptionChoosed = true;
        if (action.get() != ButtonType.OK) {
            OptionChoosed = false;
        }

        return OptionChoosed;
    }
}