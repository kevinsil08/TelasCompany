/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.MeasurementDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class MeasurementManager {
    
    private MeasurementDAO measurementDAO;
    
    public MeasurementManager(MeasurementDAO measurementDAO){
        try {
            this.measurementDAO = measurementDAO;
            measurementDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void AddMeasurement(String Name) throws Exception{
        Measurement measurement = new Measurement(0,Name);
        measurementDAO.InsertMeasurement(measurement);
    }
    
    public void UpdateMeasurement(Measurement measurement){
        measurementDAO.UpdateMeasurement(measurement);
    }
    
    public void DeleteMeasurement(Measurement measurement){
        measurementDAO.DeleteMeasurement(measurement);
    }
    
    public List<String> ListMeasurement(int ItemId){
        return measurementDAO.ListMeasurementWithItem(ItemId);
    }
    
    public void close(){
        try {
            measurementDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
