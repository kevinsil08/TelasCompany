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
    private static final List<Measurement> EMPTY = new ArrayList<>();

    @Override
    public int InsertMeasurement(Measurement measurement) {
        try {
            CallableStatement statement = connection.prepareCall("{call pr_add_measurement (?)}");
            statement.setString(1, measurement.getName());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("last_insert_id()");
            }
            return 1;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return -1;
    }

    @Override
    public boolean UpdateMeasurement(Measurement measurement) {
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall("{call pr_update_measurement(?,?)}");
            statement.setString(1, measurement.getName());
            statement.setInt(2, measurement.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return false;
    }

    @Override
    public boolean DeleteMeasurement(Measurement measurement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Measurement> ListMeasurementWithItem(int ItemId) {
        List<Measurement> ListMeasurementWithItem = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement statement = null;
        
        try {
            statement = connection.prepareCall("{call pr_measurement_of_item(?)}");
            statement.setInt(1, ItemId);
            rs = statement.executeQuery();
            while (rs.next()) {
                Measurement measurement = new Measurement();
                measurement.setId((rs.getInt("mea_id")));
                measurement.setName((rs.getString("mea_name")));
                ListMeasurementWithItem.add(measurement);
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

    @Override
    public int InsertValuesMeasurement(Measurement measurement) {
        try {
            CallableStatement statement = connection.prepareCall("{call pr_add_item_measurement (?,?,?)}");
            statement.setInt(1, measurement.getIdHandDetail());
            statement.setInt(2, measurement.getId());
            statement.setDouble(3, measurement.getValue());
            statement.execute();
            return measurement.getId();
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return -1;
    }

    @Override
    public List<Measurement> ListValuesMeasurementOfItem(int HandDetailId) {
        List<Measurement> ListMeasurementWithItem = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement statement = null;
        
        try {
            statement = connection.prepareCall("{call pr_values_measurement_item(?)}");
            statement.setInt(1, HandDetailId);
            rs = statement.executeQuery();
            while (rs.next()) {
                Measurement Measurement = new Measurement();
                        
                Measurement.setValue((rs.getDouble("value")));
                Measurement.setName((rs.getString("mea_name")));
                Measurement.setId((rs.getInt("mea_id")));
                Measurement.setIdHandDetail((rs.getInt("ite_id")));
                ListMeasurementWithItem.add(Measurement);
            }
            return ListMeasurementWithItem;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return EMPTY;
    }

    @Override
    public boolean UpdateValuesItemMeasurement(Measurement measurement) {
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall("{call pr_update_item_measurement(?,?,?)}");
            statement.setString(1, String.valueOf(measurement.getValue()));
            statement.setInt(2, measurement.getIdHandDetail());
            statement.setInt(3, measurement.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return false;
    }
    //

    @Override
    public int InsertItemMeasurement(int ItemId, int MeasurementId) {
        try {
            CallableStatement statement = connection.prepareCall("{call pr_insert_item_measurement (?,?)}");
            statement.setInt(1, ItemId);
            statement.setInt(2, MeasurementId);
            statement.execute();
            return 1;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return -1;
    }

    @Override
    public List<Measurement> ListMeasurements() {
        List<Measurement> ListMeasurements = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement statement = null;
        
        try {
            statement = connection.prepareCall("{call pr_list_measurement()}");
            rs = statement.executeQuery();
            while (rs.next()) {
                Measurement measurement = new Measurement();
                measurement.setName(((rs.getString("mea_name"))));
                measurement.setId((rs.getInt("mea_id")));
                ListMeasurements.add(measurement);
            }
            return ListMeasurements;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return EMPTY;
    }
    
}
