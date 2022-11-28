/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.CustomerDAO;
import FileConnect.FileConnect;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natha
 */
public class SQLCustomerDAOImpl implements CustomerDAO {

    private Connection connection;
    private static final List<Customer> EMPTY = new ArrayList<>();

    @Override
    public int insertCustomer(Customer customer) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_insert_customer(?,?,?,?,?)}");
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getDocCiRuc());
            statement.setString(4, customer.getDirection());
            statement.setString(5, customer.getEmail());
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return -1;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_update_customer_by_id(?,?,?,?,?)}");
            statement.setInt(1, customer.getId());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getDirection());
            statement.setString(5, customer.getEmail());
            rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_delete_customer(?)}");
            statement.setInt(1, customer.getId());
            rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public List searchByCiRuc(String ciRuc) {
        List<Customer> listProjects = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call get_customer_by_ced(?)}");
            callableStatement.setString(1, ciRuc);
            rs = callableStatement.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("cus_id"));
                customer.setFirstName(rs.getString("cus_names"));
                customer.setLastName(rs.getString("cus_surnames"));
                customer.setDocCiRuc(rs.getString("cus_ced"));
                customer.setDirection(rs.getString("cus_direction"));
                customer.setEmail(rs.getString("cus_email"));
                listProjects.add(customer);
            }
            return listProjects;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return EMPTY;
    }

    @Override
    public List listCustomers() {
        List<Customer> customersList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call pr_get_customers()}");
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("cus_id"));
                customer.setFirstName(rs.getString("cus_names"));
                customer.setLastName(rs.getString("cus_surnames"));
                customer.setDocCiRuc(rs.getString("cus_ced"));
                customer.setDirection(rs.getString("cus_direction"));
                customer.setEmail(rs.getString("cus_email"));
                customersList.add(customer);
            }
            return customersList;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return EMPTY;
    }

    @Override
    public void setup() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connect() throws Exception {
        FileConnect fileConnect = new FileConnect();
        connection = fileConnect.getConnection();
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
