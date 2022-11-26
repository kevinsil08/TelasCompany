/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Measurement;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface MeasurementDAO extends DAO{
    
    public int InsertMeasurement(Measurement measurement);
    public boolean UpdateMeasurement(Measurement measurement);
    public boolean DeleteMeasurement(Measurement measurement);
    public List<Measurement> ListMeasurementWithItem(int ItemId);
    
    public int InsertValuesMeasurement(Measurement measurement);
    public List<Measurement> ListValuesMeasurementOfItem(int HandDetailId);
    
}
