/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.MeasurementDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin
 */
public class SQLMeasurementDAOImpl implements MeasurementDAO{
    
    private  Connection connection;
    private static final List<String> EMPTY = new ArrayList<>();

    @Override
    public int InsertMeasurement(Measurement measurement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean UpdateMeasurement(Measurement measurement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean DeleteMeasurement(Measurement measurement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> ListMeasurementWithItem(int ItemId) {
        List<String> ListMeasurementWithItem = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement statement = null;
        
        try {
            statement = connection.prepareCall("{call pr_measurement_of_item(?)}");
            statement.setInt(1, ItemId);
            rs = statement.executeQuery();
            while (rs.next()) {
                String name = ((rs.getString("mea_name")));
                ListMeasurementWithItem.add(name);
            }
            return ListMeasurementWithItem;
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
