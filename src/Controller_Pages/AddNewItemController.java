/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.ItemManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class AddNewItemController implements Initializable {

    @FXML
    private TextField TxtItem;
    
    @FXML
    private Button BtnIngresar,BtnCancelar;
    
    private ItemManager ItemManager;

    public AddNewItemController(ItemManager ItemManager) {
        this.ItemManager = ItemManager;
    }
    
    @FXML
    private void AddItem(MouseEvent event) throws Exception {
        String ErrorTitle = "Error de ingreso";
        String NameItem = TxtItem.getText();
        try{
            if(NameItem.isEmpty()){
                showError(ErrorTitle, "Debe ingresar el nombre del Item");
                return;
            }
            
            if(showConfirmation()){
                ItemManager.AddItem(NameItem);
                closeStage();
            }
            
            
        }catch(Exception e){
                showError(ErrorTitle, "Error en la Base de Datos");
        }
        
    }
    
    @FXML
    void closeDialog(MouseEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) BtnCancelar.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        alert.setTitle("Confirmaci??n");
        alert.setContentText("??Desea ingresar un nuevo item?");
        Optional<ButtonType> action = alert.showAndWait();
        boolean OptionChoosed = true;
        if (action.get() != ButtonType.OK) {
            OptionChoosed = false;
        }

        return OptionChoosed;
    }
    
    
    
}
