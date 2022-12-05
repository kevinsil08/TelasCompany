/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.HandiworkDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natha
 */
public class HandiworkManager {
    private HandiworkDAO handiworkDAO;

    public HandiworkManager(HandiworkDAO handiworkDAO) {
        this.handiworkDAO = handiworkDAO;
        try {
            handiworkDAO.connect();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addHandiwork( int customerID, String entryDate, double totalCost, int numberGarments, boolean state , double leftPayment , String payStatus){
        Handiwork handiwork = new Handiwork(0, customerID, entryDate, totalCost, numberGarments, state , leftPayment  , payStatus);
        return handiworkDAO.insertHandiwork(handiwork);
    }
    
    public List getById(int idHandiwork){
        return handiworkDAO.searchHanById(idHandiwork);
    }
    
    public List getPendingHandworks(){
        return handiworkDAO.findPendingHandiworks();
    }
    
    public void close(){
        try {
            handiworkDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int updateHanState(Handiwork handiwork) {
        return handiworkDAO.updateHanState(handiwork);
    }

    public int setStateOfHanDetail(Handiwork handiwork) {
        return handiworkDAO.setStateOfHanDetails(handiwork);
    }

    public int updateCosts(int idHandiwork) {
        return handiworkDAO.updateHandiworkCosts(idHandiwork);
    }
    
    public int updateLeftCost(int idHandiwork){
        return handiworkDAO.updateLeftCost(idHandiwork);
    }
}
