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
    
    public int AddMeasurement(String nameMeasurement) throws Exception{
        Measurement measurement = new Measurement(0,nameMeasurement);
        return measurementDAO.InsertMeasurement(measurement);
    }
    
    public void UpdateMeasurement(Measurement measurement){
        measurementDAO.UpdateMeasurement(measurement);
    }
    
    public void DeleteMeasurement(Measurement measurement){
        measurementDAO.DeleteMeasurement(measurement);
    }
    
    public List<Measurement> ListMeasurement(int ItemId){
        return measurementDAO.ListMeasurementWithItem(ItemId);
    }
    
    public int InsertItemMeasurement(int ItemId, int MeasurementId){
        return measurementDAO.InsertItemMeasurement(ItemId,MeasurementId);
    }
    
    public void AddMeasurementValues(int Id, int IdHandDetail, String Value ) throws Exception{
        Measurement measurement = new Measurement(Id, IdHandDetail, Value);
        measurementDAO.InsertValuesMeasurement(measurement);
    }
    
    public List<Measurement> ListMeasurements(){
        return measurementDAO.ListMeasurements();
    }
    
    public List<Measurement> ListValuesMeasurementOfItem(int HandiDetailId){
        return measurementDAO.ListValuesMeasurementOfItem(HandiDetailId);
    }
    
    public void UpdateValuesItemMeasurement(Measurement measurement){
        measurementDAO.UpdateValuesItemMeasurement(measurement);
    }
    
    public void close(){
        try {
            measurementDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(MeasurementManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
