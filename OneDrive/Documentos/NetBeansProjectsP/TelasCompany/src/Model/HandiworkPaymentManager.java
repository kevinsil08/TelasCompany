/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.PaymentDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class HandiworkPaymentManager {
    
    private PaymentDAO PaymentDAO;
    
    public HandiworkPaymentManager(PaymentDAO PaymentDAO){
        try {
            this.PaymentDAO = PaymentDAO;
            PaymentDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void AddHandiworkPayment(int Id, String Date, double Amount){
        try{
        HandiworkPayment HandiworkPayment = new HandiworkPayment(Id,Date,  Amount);
        PaymentDAO.InsertPayment(HandiworkPayment);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void UpdateHandiworkPayment(HandiworkPayment HandiworkPayment){
        PaymentDAO.UpdatePayment(HandiworkPayment);
    }
    
    public void DeleteHandiworkPayment(HandiworkPayment HandiworkPayment){
        PaymentDAO.DeletePayment(HandiworkPayment);
    }
    
    public List<HandiworkPayment> ListPaymentWithHandiworkId(int HandiworkDetailId){
        return PaymentDAO.ListPaymentWithHandiworkId(HandiworkDetailId);
    }
    
    
    public void close(){
        try {
            PaymentDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
