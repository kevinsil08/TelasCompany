/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.HandiworkPayment;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface PaymentDAO extends DAO{
    
    public int InsertPayment(HandiworkPayment typeItem);
    public boolean UpdatePayment(HandiworkPayment typeItem);
    public boolean DeletePayment(HandiworkPayment typeItem);
    public List<HandiworkPayment> ListPayment();
    
}
