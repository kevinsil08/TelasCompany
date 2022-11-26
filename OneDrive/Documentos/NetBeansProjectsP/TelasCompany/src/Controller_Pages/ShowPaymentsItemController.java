/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.HandiworkPayment;
import Model.HandiworkPaymentManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TextField TxtFTotalCost,TxtFPayment;
    
    @FXML
    private Button BtnCancel,BtnAddPayment,BtnUpdatePayment,BtnDeletePayment;
    
    @FXML
    private Label PaymentDone, ActualDate;
    
    @FXML
    private TableView<HandiworkPayment> TablePayments;
    
    @FXML
    private  TableColumn Value = new TableColumn("Valor");;
    @FXML
    private  TableColumn DatePayment = new TableColumn("Fecha de Pago");;
    @FXML
    private  TableColumn NamePayment = new TableColumn("Nombre del Item");
    
    ObservableList<HandiworkPayment> ObservableList;
    
    private HandiworkPaymentManager HandiworkPaymentManagerModel;
    private HandiworkPayment HandiworkPayment;
    
    public ShowPaymentsItemController(HandiworkPaymentManager HandiworkPaymentManagerModel,HandiworkPayment HandiworkPayment){
        this.HandiworkPaymentManagerModel = HandiworkPaymentManagerModel;
        this.HandiworkPayment = HandiworkPayment;
    }
    
    @FXML
    private void InsertPayment(){
        try{
            LocalDate StartDate = LocalDate.now();
            HandiworkPaymentManagerModel.AddHandiworkPayment(HandiworkPayment.getId(), StartDate.toString(), HandiworkPayment.getTotalPayment());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate StartDate = LocalDate.now();
        ActualDate.setText("Fecha Actual: "+StartDate.toString());
        
        List<HandiworkPayment> ListPayments = HandiworkPaymentManagerModel.ListPaymentWithHandiworkId(HandiworkPayment.getId());
        if(ListPayments.size()>0){
            //HandiworkPayment = (HandiworkPayment)ListPayments.get(0);
            //PaymentDone.setText("Abonado: "+HandiworkPayment.getTotalPayment()+" $");
            NamePayment.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
            DatePayment.setCellValueFactory(new PropertyValueFactory<>("Date"));
            Value.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            TablePayments.getColumns().addAll(NamePayment, DatePayment, Value);

            ObservableList = FXCollections.observableList(ListPayments);
            setItemstable();
        }
    }    
    
    public void setItemstable() {
        TablePayments.setItems(null);
        TablePayments.setItems(ObservableList);
    }
    
}
