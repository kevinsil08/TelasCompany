/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.CustomerDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natha
 */
public class CustomerManager {
    private CustomerDAO customerDAO;

    public CustomerManager(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        try {
            customerDAO.connect();
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addCustomer( String firstName, String lastName, String docCiRuc , String direction ,String email){
        Customer customer = new Customer(0, firstName, lastName, docCiRuc, direction, email);
        return customerDAO.insertCustomer(customer);
    }
    
    public List<Customer> findCustomerByCiRuc(String ciRuc){
        return customerDAO.searchByCiRuc(ciRuc);
    }
    
    
    public void close(){
        try {
            customerDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
