/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface CustomerDAO extends DAO{

    public int insertCustomer(Customer customer);
    public boolean updateCustomer(Customer customer);
    public boolean deleteCustomer(Customer customer);
    public List searchByCiRuc(String ciRuc);
    public List listCustomers();

    public List<Customer> searchByHAndiworkId(int Handiwork);
}

