/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.PaymentDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin
 */
public class SQLHandiworkPaymentDAOImpl implements PaymentDAO{
    
    private  Connection connection;
    private static final List<HandiworkPayment> EMPTY = new ArrayList<>();

    @Override
    public int InsertPayment(HandiworkPayment HandiworkPayment) {
        try {
            CallableStatement statement = connection.prepareCall("{call pr_add_payment (?,?,?)}");
            
            statement.setInt(1, HandiworkPayment.getId());
            statement.setString(2, HandiworkPayment.getDate());
            statement.setDouble(3, HandiworkPayment.getAmount());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("last_insert_id");
            }
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return -1;
    }

    @Override
    public boolean UpdatePayment(HandiworkPayment typeItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean DeletePayment(HandiworkPayment typeItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HandiworkPayment> ListPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
