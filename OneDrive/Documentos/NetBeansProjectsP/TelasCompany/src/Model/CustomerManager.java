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

    public int addCustomer(String firstName, String lastName, String docCiRuc, String direction, String email) {
        Customer customer = new Customer(0, firstName, lastName, docCiRuc, direction, email);
        return customerDAO.insertCustomer(customer);
    }

    public List<Customer> findCustomerByCiRuc(String ciRuc) {
        return customerDAO.searchByCiRuc(ciRuc);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.listCustomers();
    }

    public boolean updateCustomer(int cusId, String firstName, String lastName, String direction, String email) {
        Customer customer = new Customer(cusId, firstName, lastName, "", direction, email);
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int cusId) {
        Customer customer = new Customer();
        customer.setId(cusId);
        return customerDAO.deleteCustomer(customer);
    }

    public void close() {
        try {
            customerDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Customer> findCustomerByHandiworkId(int handiworkID) {
        return customerDAO.searchByHAndiworkId(handiworkID);
    }
}
