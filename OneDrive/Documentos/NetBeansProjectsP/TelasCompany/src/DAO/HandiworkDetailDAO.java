/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.HandiworkDetail;
import Model.HandiworkDetailSearchType;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface HandiworkDetailDAO extends DAO{
    
    public int InsertHandiworkDetail(HandiworkDetail HandiworkDetail);
    public boolean UpdateHandiworkDetail(HandiworkDetail HandiworkDetail);
    public boolean DeleteHandiworkDetail(HandiworkDetail HandiworkDetail);
    
    public List<HandiworkDetail> findProjectByProperty(HandiworkDetailSearchType searchType, Object value);
    public List<HandiworkDetail> findAll();
    
    public List<HandiworkDetail> ListItemsForTable(int IdHandiwork);
    
    
}
