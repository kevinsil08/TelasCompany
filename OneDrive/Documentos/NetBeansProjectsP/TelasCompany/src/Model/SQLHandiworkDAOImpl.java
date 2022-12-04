/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.HandiworkDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natha
 */
public class SQLHandiworkDAOImpl implements HandiworkDAO {

    private Connection connection;
    private static final List<Handiwork> EMPTY = new ArrayList<>();

    @Override
    public int insertHandiwork(Handiwork handiwork) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_insert_Handiwork(?,?,?,?,?,?,?)}");
            statement.setInt(1, handiwork.getCustomerID());
            statement.setString(2, handiwork.getEntryDate());
            statement.setDouble(3, handiwork.getTotalCost());
            statement.setInt(4, handiwork.getNumberGarments());
            statement.setBoolean(5, handiwork.isState());
            statement.setDouble(6, handiwork.getLeftPayment());
            statement.setString(7, handiwork.getPayStatus());
            
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
    public List searchHanById(int idHandiwork) {
        List<Handiwork> listProjects = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call pr_get_handiwork_by_id(?)}");
            callableStatement.setInt(1, idHandiwork);
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                Handiwork handiwork = new Handiwork();
                handiwork.setHandiWorkID(rs.getInt("han_id"));
                handiwork.setCustomerID(rs.getInt("cus_id"));
                handiwork.setEntryDate(rs.getString("han_entry_date"));
                handiwork.setTotalCost(rs.getDouble("han_total_cost"));
                handiwork.setNumberGarments(rs.getInt("han_num_grmt"));
                handiwork.setState(rs.getBoolean("han_state"));
                String stsString = rs.getBoolean("han_state") ? "Finalizado" : "Pendiente" ;
                handiwork.setStateString(stsString);
                handiwork.setLeftPayment(rs.getDouble("han_left_payment"));
                handiwork.setPayStatus(rs.getString("han_pay_status"));
                listProjects.add(handiwork);
            }
            return listProjects;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return EMPTY;
    }

    @Override
    public boolean updateHandiwork(Handiwork handiwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteHandiwork(Handiwork handiwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double totalPayments(Handiwork handiwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setState(Handiwork handiwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateNumGarmentsTotalCost(Handiwork handiwork) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Handiwork> findAll() {
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

    @Override
    public List<Handiwork> findPendingHandiworks() {
        List<Handiwork> handiworkList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call pr_get_pending_handiworks()}");
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                Handiwork handiwork = new Handiwork();
                handiwork.setCiCustomer(rs.getString("cus_ced"));
                handiwork.setNamesCustomer(rs.getString("cus_names") + " " + rs.getString("cus_surnames"));
                handiwork.setHandiWorkID(rs.getInt("han_id"));
                handiwork.setPayStatus(rs.getString("han_pay_status"));
                String stsString = rs.getBoolean("han_state") ? "Finalizado" : "Pendiente" ;
                handiwork.setStateString(stsString);
                handiwork.setTotalCost(rs.getDouble("han_total_cost"));
                handiwork.setLeftPayment(rs.getDouble("han_left_payment"));
                handiworkList.add(handiwork);
            }
            return handiworkList;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return EMPTY;
    }

}
