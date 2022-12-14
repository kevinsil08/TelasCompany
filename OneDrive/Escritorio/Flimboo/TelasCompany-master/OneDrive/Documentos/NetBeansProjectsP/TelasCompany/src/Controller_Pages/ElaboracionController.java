/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Pages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class ElaboracionController implements Initializable {

    @FXML
    private Label lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7;
    
    @FXML
    private RadioButton blusa_radioBtn,pollera_radioBtn,bolsicon_radioBtn,
            panio_radioBtn,joyeria_radioBtn,planchado_radioBtn;
    
    @FXML
    private TextField txtF1,txtF2,txtF3,txtF4,txtF5,txtF6,txtF7;
    
    @FXML
    void dashBoardClicksPrincipal(MouseEvent event) throws IOException {
        if (event.getSource() == blusa_radioBtn) {
            lbl1.setVisible(true);lbl2.setVisible(true);lbl3.setVisible(true);lbl4.setVisible(true);lbl5.setVisible(true);
            lbl6.setVisible(true);lbl7.setVisible(true);
            
            txtF1.setVisible(true); txtF2.setVisible(true); txtF3.setVisible(true); txtF4.setVisible(true); txtF5.setVisible(true);
            txtF6.setVisible(true); txtF7.setVisible(true);
            
            lbl1.setText("Espalda");
            lbl2.setText("Busto");
            lbl3.setText("Largo de frente");
            lbl4.setText("Ancho de estómago");
            lbl5.setText("Largo de manga");
            lbl6.setText("Ancho de puño");
            lbl7.setText("Escote");
            
        }else if (event.getSource() == pollera_radioBtn) {
            lbl1.setVisible(true); lbl2.setVisible(true); lbl3.setVisible(true); lbl4.setVisible(false);
            lbl5.setVisible(false); lbl6.setVisible(false); lbl7.setVisible(false);
            
            lbl1.setText("Alto");
            lbl2.setText("Cintura");
            lbl3.setText("Alforza");
            
            txtF1.setVisible(true); txtF2.setVisible(true); txtF3.setVisible(true); txtF4.setVisible(false); txtF5.setVisible(false);
            txtF6.setVisible(false); txtF7.setVisible(false);
            
        }else if (event.getSource() == bolsicon_radioBtn) {
            lbl1.setVisible(true); lbl2.setVisible(true); lbl3.setVisible(true); lbl4.setVisible(false);
            lbl5.setVisible(false); lbl6.setVisible(false); lbl7.setVisible(false);
            
            lbl1.setText("Alto");
            lbl2.setText("Cintura");
            lbl3.setText("Alforza");
            
            txtF1.setVisible(true); txtF2.setVisible(true); txtF3.setVisible(true); txtF4.setVisible(false); txtF5.setVisible(false);
            txtF6.setVisible(false); txtF7.setVisible(false);
            
        }else if (event.getSource() == panio_radioBtn) {
            lbl1.setVisible(false); lbl2.setVisible(false); lbl3.setVisible(false); lbl4.setVisible(false); lbl5.setVisible(false);
            lbl6.setVisible(false); lbl7.setVisible(false);
            
            txtF1.setVisible(false); txtF2.setVisible(false); txtF3.setVisible(false); txtF4.setVisible(false); txtF5.setVisible(false);
            txtF6.setVisible(false); txtF7.setVisible(false);
            
        }else if (event.getSource() == joyeria_radioBtn) {
            lbl1.setVisible(true);
            lbl2.setVisible(false);
            lbl3.setVisible(false);
            lbl4.setVisible(false);
            lbl5.setVisible(false);
            lbl6.setVisible(false);
            lbl7.setVisible(false);
            
            lbl1.setText("Largo");
            
            txtF1.setVisible(true); txtF2.setVisible(false); txtF3.setVisible(false); txtF4.setVisible(false); txtF5.setVisible(false);
            txtF6.setVisible(false); txtF7.setVisible(false);
          
        }else if (event.getSource() == planchado_radioBtn) {
           lbl1.setVisible(false); lbl2.setVisible(false); lbl3.setVisible(false); lbl4.setVisible(false); lbl5.setVisible(false);
           lbl6.setVisible(false); lbl7.setVisible(false);
           
           txtF1.setVisible(false); txtF2.setVisible(false); txtF3.setVisible(false); txtF4.setVisible(false); txtF5.setVisible(false);
           txtF6.setVisible(false); txtF7.setVisible(false);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
