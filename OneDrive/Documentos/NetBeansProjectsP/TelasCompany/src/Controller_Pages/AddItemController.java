/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import Model.HandiworkDetailManager;
import Model.HandiworkPaymentManager;
import Model.Item;
import Model.ItemManager;
import Model.Measurement;
import Model.MeasurementManager;
import Model.Planchado;
import Model.PlanchadoManager;
import Model.ValidateInput;
import com.jfoenix.controls.JFXAutoCompletePopup;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private Button BtnAddItem, BtnCancel;

    private Label[] NameMeasurement;
    private TextField[] TxtMeasurement;
    private List<Item> ListItems;
    private Item itemSelected;
    private int idHandiwork;
    private ObservableList<Planchado> obsListPlanchados;
    private HandiworkDetailManager HandiworkDtlModel;
    private ItemManager ItemManagerModel;
    private MeasurementManager MeasurementManagerModel;
    private HandiworkPaymentManager HandiworkPaymentManagerModel;
    private PlanchadoManager modelPlanchado;

    public AddItemController(HandiworkDetailManager HandiworkDtlModel, ItemManager ItemManagerModel, MeasurementManager MeasurementManagerModel, HandiworkPaymentManager HandiworkPaymentManagerModel, PlanchadoManager modelPlanchado, int idHandiwork) {

        this.HandiworkDtlModel = HandiworkDtlModel;
        this.ItemManagerModel = ItemManagerModel;
        this.MeasurementManagerModel = MeasurementManagerModel;
        this.HandiworkPaymentManagerModel = HandiworkPaymentManagerModel;

        this.modelPlanchado = modelPlanchado;
        this.idHandiwork = idHandiwork;
        this.obsListPlanchados = FXCollections.observableArrayList();
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
        int lastId=HandiworkDtlModel.AddHandiworkDetail(itemSelected.getId(), 1, StartDate.toString(), Detail, AddDetail, TotalCost, DeliveryDate.toString(), "0", "p");
        HandiworkPaymentManagerModel.AddHandiworkPayment(lastId, StartDate.toString(), Payment);
        
            for (int i = 0; i < TxtMeasurement.length; i++) {
                MeasurementManagerModel.AddMeasurementValues(Integer.parseInt(TxtMeasurement[i].getId()), lastId,Double.parseDouble(TxtMeasurement[i].getText()));

            }

            if (Payment > TotalCost) {
                showError(ErrorTitle, "El Abono no debe ser mayor al Costo Total");
                return;
            }
            //Handiwork PrincipalHandiwork = Handiwork
            int lastId = 0;
            if (itemSelected.getId() == 4) {
                if (obsListPlanchados.isEmpty()) {
                    showError("Error de registro", "Ingrese planchados");
                    return;
                }
                // add handiworkDetail to db
                lastId = HandiworkDtlModel.AddHandiworkDetail(itemSelected.getId(), idHandiwork, StartDate.toString(), Detail, AddDetail, TotalCost, DeliveryDate.toString(), "0", "p", obsListPlanchados.size());
                //add each planchado asosiated to handiworkDetail to db
                for (Planchado planchado : obsListPlanchados) {
                    planchado.setHanDetailID(lastId);
                    modelPlanchado.insertPlanchadoToHanDetail(planchado);
                }
               
            } else {
                lastId = HandiworkDtlModel.AddHandiworkDetail(itemSelected.getId(), idHandiwork, StartDate.toString(), Detail, AddDetail, TotalCost, DeliveryDate.toString(), "0", "p", 1);
                for (int i = 0; i < TxtMeasurement.length; i++) {
                    MeasurementManagerModel.AddMeasurementValues(Integer.parseInt(TxtMeasurement[i].getId()), lastId, Double.parseDouble(TxtMeasurement[i].getText()));
                }
                
            }
            HandiworkPaymentManagerModel.AddHandiworkPayment(lastId, StartDate.toString(), Payment);
            closeStage();
        } catch (NumberFormatException e) {
            showError(ErrorTitle, "Solo debe ingresar números en Precio Total y/o Abono");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TxtFTotalCost.setText("0");

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
        clearHboxContents();
        // item 4 refers to planchados
        if (ItemId == 4) {
            TxtFTotalCost.setEditable(false);
            addMultySubItemsFields(ItemId);

        } else {
            // rest of  items (arreglos, polleras , bolsicones)
            int MAXVALUETOHBOX = 4;
            List<Measurement> listMeasurement = MeasurementManagerModel.ListMeasurement(ItemId);
            NameMeasurement = new Label[listMeasurement.size()];
            TxtMeasurement = new TextField[listMeasurement.size()];
            int ITERATOR = 0;
            while (ITERATOR < listMeasurement.size()) {
                NameMeasurement[ITERATOR] = new Label((String) listMeasurement.get(ITERATOR).getName());
                NameMeasurement[ITERATOR].setPadding(new Insets(0.0, 20.0, 0.0, 20.0));
                NameMeasurement[ITERATOR].setFont(new Font("Roboto", 22.0));
                TxtMeasurement[ITERATOR] = new TextField();
                TxtMeasurement[ITERATOR].setPrefSize(78, 26);
                TxtMeasurement[ITERATOR].setId("" + listMeasurement.get(ITERATOR).getId());
                if (ITERATOR > MAXVALUETOHBOX) {
                    HBoxMeasurement2.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
                } else {
                    HBoxMeasurement.getChildren().addAll(NameMeasurement[ITERATOR], TxtMeasurement[ITERATOR]);
                }
                ITERATOR++;
            }

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

    private void showError(String title, String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }

    private void addMultySubItemsFields(int itemID) {

        Label LblPlanchadoDescr = new Label("Descripción Planchado");
        LblPlanchadoDescr.setPadding(new Insets(0, 20, 0, 20));
        TextField TxfPanchadoDescr = new TextField();
        TxfPanchadoDescr.setPadding(new Insets(0, 20, 0, 20));
        Label LblPlanchadoCost = new Label("Costo");
        LblPlanchadoCost.setPadding(new Insets(0, 20, 0, 20));
        TextField TxfPanchadoCost = new TextField();
        TxfPanchadoCost.setPadding(new Insets(0, 20, 0, 20));
        Button BtnAddPlanchado = new Button("+");
        BtnAddPlanchado.setPadding(new Insets(0, 20, 0, 10));
        BtnAddPlanchado.maxWidth(10);
        HBox HboxInputPlanchado = new HBox();
        HboxInputPlanchado.getChildren().addAll(LblPlanchadoDescr, TxfPanchadoDescr, LblPlanchadoCost, TxfPanchadoCost, BtnAddPlanchado);
        HBoxMeasurement.getChildren().addAll(HboxInputPlanchado);
        // table to store cutomer input planchado 
        TableView<Planchado> TblPlanchado = new TableView<Planchado>();
        TableColumn descCol = new TableColumn("Descripción");
        TableColumn costCol = new TableColumn("Costo");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        // lista de planchados para colocar en tableView
        List<Planchado> listPlanchados = new ArrayList<>();
        obsListPlanchados = FXCollections.observableList(listPlanchados);

        // Columna con el boton de quitar planchados de la tabla
    TableColumn actionCol = new TableColumn("Eliminar");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("Delete"));

        Callback<TableColumn<Planchado, String>, TableCell<Planchado, String>> cellFactory
                = //
                new Callback<TableColumn<Planchado, String>, TableCell<Planchado, String>>() {
            @Override
            public TableCell call(final TableColumn<Planchado, String> param) {
                final TableCell<Planchado, String> cell = new TableCell<Planchado, String>() {

                    final Button btn = new Button("Eliminar");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Planchado planchado = getTableView().getItems().get(getIndex());
                                obsListPlanchados.remove(planchado);
                                updateTblView(TblPlanchado, obsListPlanchados);
                                updateTotalCostField(obsListPlanchados);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);

        TblPlanchado.getColumns().addAll(descCol, costCol, actionCol);
        TblPlanchado.setMaxSize(800, 300);
        TblPlanchado.setMinSize(600, 250);
        HBoxMeasurement2.getChildren().addAll(TblPlanchado);
        Planchado planchadoSeleccion = new Planchado();
        planchadoSeleccion.setPlanchadoID(-1);
        autoCompleteSearch(TxfPanchadoDescr, TxfPanchadoCost, planchadoSeleccion);
        // add ingresed planchado from txf fields
        BtnAddPlanchado.setOnAction((ActionEvent event) -> {
            ValidateInput validateInput = new ValidateInput(TxfPanchadoCost.getText());
            if (!TxfPanchadoDescr.getText().isEmpty()) {
                if (validateInput.moneyNumber()) {
                    String selectedCost = String.format("%.2f", planchadoSeleccion.getCost());
                    if (planchadoSeleccion.getPlanchadoID() != -1 && planchadoSeleccion.getDescription().equals(TxfPanchadoDescr.getText()) && selectedCost.equals(TxfPanchadoCost.getText())) {
                        // planchado selected from database
                        Planchado nuevoPlanchado = new Planchado(planchadoSeleccion);
                        listPlanchados.add(nuevoPlanchado);
                        updateTblView(TblPlanchado, obsListPlanchados);
                    } else {
                        // planchado different from database / new insert
                        Planchado nuevoPlanchado = new Planchado(-1, TxfPanchadoDescr.getText().toUpperCase(), Double.parseDouble(TxfPanchadoCost.getText()), -1);
                        obsListPlanchados.add(nuevoPlanchado);
                        updateTblView(TblPlanchado, obsListPlanchados);
                        // isnert new planchado on db
                        modelPlanchado.insertPlanchado(TxfPanchadoDescr.getText().toUpperCase(), Double.parseDouble(TxfPanchadoCost.getText()));
                        autoCompleteSearch(TxfPanchadoDescr, TxfPanchadoCost, planchadoSeleccion);
                    }
                    updateTotalCostField(obsListPlanchados);
                    resetTextFields(TxfPanchadoCost, TxfPanchadoDescr);
                } else {
                    showError("Error de ingreso", "Ingrese cifra numérica correspondiente al costo");
                    TxfPanchadoCost.setText("");
                    TxfPanchadoCost.requestFocus();
                }

            } else {
                showError("Error de ingreso", "Ingrese descripción del planchado");
                TxfPanchadoDescr.setText("");
                TxfPanchadoDescr.requestFocus();
            }
        });

    }

    private void updateTblView(TableView<Planchado> TblPlanchado, ObservableList obsListPlanchados) {
        TblPlanchado.setItems(null);
        TblPlanchado.setItems(obsListPlanchados);
    }

    // function to suggest planchados from database
    private void autoCompleteSearch(TextField TxfPanchadoDescr, TextField TxfPanchadoCost, Planchado planchadoSeleccion) {
        Thread newThread = new Thread() {
            public void run() {
                JFXAutoCompletePopup<Planchado> autoCompletePopup = new JFXAutoCompletePopup<>();
                autoCompletePopup.getSuggestions().addAll(modelPlanchado.listPlanchados());
                autoCompletePopup.setSelectionHandler(event -> {
                    TxfPanchadoDescr.setText(event.getObject().getDescription());
                    TxfPanchadoCost.setText(String.format("%.2f", event.getObject().getCost()));
                    planchadoSeleccion.setPlanchadoID(event.getObject().getPlanchadoID());
                    planchadoSeleccion.setDescription(event.getObject().getDescription());
                    planchadoSeleccion.setCost(event.getObject().getCost());
                });
                // filter planchados 
                TxfPanchadoDescr.textProperty().addListener(observable -> {
                    autoCompletePopup.filter(string -> string.getDescription().toLowerCase().contains(TxfPanchadoDescr.getText().toLowerCase()));
                    if (autoCompletePopup.getFilteredSuggestions().isEmpty() || TxfPanchadoDescr.getText().isEmpty()) {
                        autoCompletePopup.hide();
                        // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                    } else {
                        autoCompletePopup.show(TxfPanchadoDescr);
                    }
                });
            }
        };
        newThread.start();
    }

    private void resetTextFields(TextField TxfPanchadoCost, TextField TxfPanchadoDescr) {
        TxfPanchadoCost.setText("");
        TxfPanchadoDescr.setText("");
        TxfPanchadoDescr.requestFocus();
    }

    private void updateTotalCostField(ObservableList<Planchado> obsListPlanchados) {
        Double totalCost = 0.;
        for (Planchado planchado : obsListPlanchados) {
            totalCost += planchado.getCost();
        }
        TxtFTotalCost.setText(String.format("%.2f", totalCost));
    }

    private void clearHboxContents() {
        HBoxMeasurement.getChildren().clear();
        HBoxMeasurement2.getChildren().clear();
        TxtFTotalCost.setEditable(true);
        TxtFTotalCost.setText("");
        TxtFPayment.setText("");
        TxtAreaAddDetail.setText("");
        TxtAreaDetail.setText("");
        obsListPlanchados.clear();
    }

}