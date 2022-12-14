/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.HandiworkDetailDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class HandiworkDetailManager {
    
    private HandiworkDetailDAO HandiworkDetailDAO;
    
    public HandiworkDetailManager(HandiworkDetailDAO HandiworkDetailDAO){
        try {
            this.HandiworkDetailDAO = HandiworkDetailDAO;
            HandiworkDetailDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int AddHandiworkDetail(int TypeItemId, int HandiworkId, String EntryDate, String Detail, String AddDetail, double Cost, String DeliveryDeadline, String PayStatus, String State) {
        try{
        HandiworkDetail HandiworkDetail = new HandiworkDetail(0,TypeItemId,  HandiworkId,  EntryDate,  Detail,  AddDetail,  Cost,  DeliveryDeadline,  PayStatus,  State);
        return HandiworkDetailDAO.InsertHandiworkDetail(HandiworkDetail);
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    
    public void UpdateHandiworkDetail(HandiworkDetail HandiworkDetail){
        HandiworkDetailDAO.UpdateHandiworkDetail(HandiworkDetail);
    }
    
    public void DeleteHandiworkDetail(int HandiworkId,int HandiworkDetailId){
        HandiworkDetailDAO.DeleteHandiworkDetail(HandiworkId,HandiworkDetailId);
    }
    
    public List<HandiworkDetail> searchByProperty(HandiworkDetailSearchType searchType , String value){
        return HandiworkDetailDAO.findProjectByProperty(searchType, value);
    }
    
    public List<HandiworkDetail> findAll(){
        return HandiworkDetailDAO.findAll();
    }
    
    public List<HandiworkDetail> ListItemsForTableView(int IdHandiwork){
        return HandiworkDetailDAO.ListItemsForTable(IdHandiwork);
    }
    
    public void close(){
        try {
            HandiworkDetailDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
