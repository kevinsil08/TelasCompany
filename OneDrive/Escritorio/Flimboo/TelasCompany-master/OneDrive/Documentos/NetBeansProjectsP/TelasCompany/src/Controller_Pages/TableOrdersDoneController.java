/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.Handiwork;
import Model.HandiworkManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class TableOrdersDoneController implements Initializable {
    
    @FXML
    private TableColumn<Handiwork, String> TcCiRuc, TcHanState, TcNames, TcPaymentStatus;

    @FXML
    private TableColumn<Handiwork, String> TcHanDate;

    @FXML
    private TableColumn<Handiwork, Double> TcPaymentLeft, TcTotalHanCost;
    
    
    private Stage stage;
    private HandiworkManager modelHandiwork;

    public TableOrdersDoneController(HandiworkManager modelHandiwork , Stage stage) {
        this.modelHandiwork = modelHandiwork;
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            modelHandiwork.close();
        });
    }   
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
