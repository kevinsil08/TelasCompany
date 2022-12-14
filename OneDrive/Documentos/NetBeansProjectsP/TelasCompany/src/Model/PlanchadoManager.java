/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.PlanchadoDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natha
 */
public class PlanchadoManager {
    private PlanchadoDAO planchadoDAO;

    public PlanchadoManager(PlanchadoDAO planchadoDAO) {
        this.planchadoDAO = planchadoDAO;
        try {
            planchadoDAO.connect();
        } catch (Exception ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Planchado> listPlanchados(){
        return planchadoDAO.listPlanchados();
    }

    public int insertPlanchado(String toUpperCase, double parseDouble) {
        Planchado planchado = new Planchado(-1, toUpperCase, parseDouble, -1);
        return planchadoDAO.insertPlanchado( planchado);
    }
    
    public int insertPlanchadoToHanDetail(Planchado planchado){
        return planchadoDAO.insertPlanchadoToDetail(planchado);
    }

    public List<Planchado> listPlanchadosByHanDetail(int id) {
        return planchadoDAO.listPlanchadosByHandId(id);
    }

    public int deletePlanchadosOnDetail(int id) {
        return planchadoDAO.deletePlanchadosOnDetail(id);
    }
    
    
}
