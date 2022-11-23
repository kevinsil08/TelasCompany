/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField txt_cedula,txt_nombres,txt_apellidos,txt_tlfn,txt_celular,txt_direc,txt_correo;
    
    @FXML
    private Button btn_Ingresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
